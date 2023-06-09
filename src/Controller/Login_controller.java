package Controller;

import DataModel.HighLevelDB.DBUtil;
import DataModel.Login_model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login_controller {
    private static final String SELECT_QUERY = "SELECT * FROM login WHERE user_name = ? and password = ?";
    private static final String SELECT_QUERYAll = "SELECT * FROM login";
    private static final String SELECT_QUERY1 = "SELECT roll FROM login WHERE user_name = ? and roll = ?";

    private static final String INSERT_QUERY = "INSERT INTO login (user_name, password,roll) VALUES (?,?,?)";
    private static final String SELECT_QUERYUserName = "SELECT * FROM login WHERE user_name = ? ";//UserName exist
    // checker.
    private static final String DELETE_QUERY = "DELETE FROM login WHERE login user_name = ?";
    private static final String UPDATE_QUERY = "UPDATE login SET user_name=? WHERE user_name=?";
    private static final String UPDATE_PASSWORD = "UPDATE login SET password=? WHERE user_name=?";

    public static Login_model createLoginInfo(ResultSet resultSet) {
        Login_model login_model = new Login_model();
        try {
            login_model.setUserName(resultSet.getString("user_name"));
            login_model.setPassword(resultSet.getString("password"));
            login_model.setRole(resultSet.getString("roll"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return login_model;//
    }

    public static ObservableList<Login_model> getUserInfo() {
        ObservableList<Login_model> login_models = FXCollections.observableArrayList();
        try {
            Connection connection = DBUtil.dbConnect();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERYAll);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Login_model login = createLoginInfo(resultSet);
                login_models.add(login);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return login_models;
    }

    public static boolean searchPassword(String userName, String password) throws SQLException, ClassNotFoundException {
        try {
            Connection connection = DBUtil.dbConnect();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("error occurred while searching password  " + e);/// what is error.
            throw e;
        }
        return false;
    }

    public static boolean isAdmin(String userName, String admin) throws SQLException, ClassNotFoundException {

        try {
            Connection connection = DBUtil.dbConnect();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY1);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, admin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("error occurred while serching roll type;  " + e);/// what is error.
            throw e;
        }
        return false;

    }

    public static void updateUserName(String oldUserName, String newUserName) throws SQLException, ClassNotFoundException {
        try {
            Connection connection = DBUtil.dbConnect();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, newUserName);
            preparedStatement.setString(2, oldUserName);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            System.out.println("Error occurred while Modifying UsserName");
        }
    }

    public static void updateUserPassword(String userName, String password) throws SQLException, ClassNotFoundException {
        try {
            // UPDATE login SET password='userName' WHERE user_name='password'
            Connection connection = DBUtil.dbConnect();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, userName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.print("Error occurred while perform modifiying password");
        }
    }

    public static void deleteUser(String userName) throws SQLException, ClassNotFoundException {

        try {
            Connection connection = DBUtil.dbConnect();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setString(1, userName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error occurred while Perform delete user name ");
        }
    }

    public static void insertUsers(String userName, String password, String roll) throws SQLException,
            ClassNotFoundException {
        // private static final String INSERT_QUERY = "INSERT INTO login (user_name, password,roll) VALUES (?,?,?)";
        try {
            Connection connection = DBUtil.dbConnect();//Establish connection
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, roll);
            // System.out.println(preparedStatement);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean UserNameExistanceCheker(String user_name) {
        try {
            Connection connection = DBUtil.dbConnect();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERYUserName);
            preparedStatement.setString(1, user_name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {// This execute if there is  data should be Selected
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }
}
