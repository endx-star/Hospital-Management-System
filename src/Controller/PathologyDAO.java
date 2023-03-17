package Controller;

import DataModel.HighLevelDB.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PathologyDAO {
    private static String INSERT_QUERY = "INSERT INTO pathology values(?,?,?,?)";
    private static String SELECT_QUERYTESTID = "SELECT * FROM pathology WHERE test_id = ? ";

    public static void insertPathology(int id, int pt_id, String testName, String description) {
        try {
            Connection connection = DBUtil.dbConnect();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, pt_id);
            preparedStatement.setString(3, testName);
            preparedStatement.setString(4, description);
            preparedStatement.executeUpdate();


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean testIDChecker(int testId) {
        try {
            Connection connection = DBUtil.dbConnect();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERYTESTID);
            preparedStatement.setInt(1, testId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {// This execute if there is  data should be Selected
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

}
