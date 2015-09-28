package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import core.Display;
import core.User;

public class DatabaseManager {
    private static final String CONNECTION_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USERNAME = "employee_user";
    private static final String PASSWORD = "systemsss";

    private static final String SQL_GET_ALL_USER = "SELECT emp_id,emp_pass,emp_name,gender,address,birthday,e.dept_id,dept_name FROM EMP_TABLE e INNER JOIN DEPT_TABLE d ON e.dept_id = d.dept_id ";
    private static final String SQL_SEARCH_BY_ID_POSTFIX = "WHERE emp_id = ?";
    private static final String SQL_SEARCH_BY_NAME_POSTFIX = "WHERE emp_name LIKE ?";
    private static final String SQL_SEARCH_BY_GENDER_POSTFIX = "WHERE gender = ?";
    private static final String SQL_SEARCH_BY_DEPARTMENT_POSTFIX = "WHERE e.dept_id = ?";
    private static final String SQL_INSERT_NEW_EMP = "INSERT INTO EMP_TABLE (emp_id,emp_pass, emp_name, gender, address, birthday, dept_id) values (empID_sq.nextval,?,?,?,?,?,?)";
    private static final String SQL_DELETE_EMP = "DELETE FROM EMP_TABLE WHERE EMP_ID= ?";
    private static final String SQL_CHECK_EXISTED = "SELECT COUNT(*) AS total FROM emp_table WHERE emp_id = ?";
    private static final String SQL_UPDATE_EXISTED_USER = "UPDATE EMP_TABLE SET emp_pass = ?, emp_name = ?, gender = ?, address = ?, birthday = ?, dept_id = ? WHERE emp_id = ?";

    private static final String EMP_ID = "emp_id";
    private static final String EMP_PASS = "emp_pass";
    private static final String EMP_NAME = "emp_name";
    private static final String GENDER = "gender";
    private static final String ADDRESS = "address";
    private static final String BIRTHDAY = "birthday";
    private static final String DEPT_ID = "dept_id";
    private static final String DEPT_NAME = "dept_name";
    private static final String TOTAL = "total";

    private Connection conn = null;

    public DatabaseManager() {
        init();
    }

    protected boolean init() {
        if (conn == null) {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection(CONNECTION_URL, USERNAME,
                        PASSWORD);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        if (conn != null) {
            return true;
        } else {
            return false;
        }
    }

    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(1);
            }
        } else {
            Display.showError(Display.ERROR_CANNOT_CLOSE_DATABASE);
            System.exit(1);
        }
    }

    public List<User> getAllUser() {
        Statement statement;
        List<User> usersList = null;
        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_GET_ALL_USER);
            usersList = convertToListUsers(resultSet);
            resultSet.close();
            statement.close();

        } catch (SQLException e1) {
            Display.showError(Display.ERROR_ON_SEARCH);
            System.exit(1);
        }
        return usersList;
    }

    public List<User> searchByID(int ID) {
        PreparedStatement preparedStatement;
        List<User> usersList = null;
        try {
            preparedStatement = conn.prepareStatement(SQL_GET_ALL_USER
                    + SQL_SEARCH_BY_ID_POSTFIX);
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            usersList = convertToListUsers(resultSet);
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            Display.showError(Display.ERROR_ON_SEARCH);
            System.exit(1);
        }
        return usersList;
    }

    public List<User> searchByName(String name) {
        PreparedStatement preparedStatement;
        List<User> usersList = null;
        try {
            preparedStatement = conn.prepareStatement(SQL_GET_ALL_USER
                    + SQL_SEARCH_BY_NAME_POSTFIX);
            String likeState = "%" + name + "%";
            preparedStatement.setString(1, likeState);
            ResultSet resultSet = preparedStatement.executeQuery();
            usersList = convertToListUsers(resultSet);
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            Display.showError(Display.ERROR_ON_SEARCH);
            System.exit(1);
        }
        return usersList;
    }

    public List<User> searchByGender(int gender) {
        PreparedStatement preparedStatement;
        List<User> usersList = null;
        try {
            preparedStatement = conn.prepareStatement(SQL_GET_ALL_USER
                    + SQL_SEARCH_BY_GENDER_POSTFIX);
            preparedStatement.setInt(1, gender);
            ResultSet resultSet = preparedStatement.executeQuery();
            usersList = convertToListUsers(resultSet);
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            Display.showError(Display.ERROR_ON_SEARCH);
            System.exit(1);
        }
        return usersList;
    }

    public List<User> searchByDepartmentID(int departmentID) {
        PreparedStatement preparedStatement;
        List<User> usersList = null;
        try {
            preparedStatement = conn.prepareStatement(SQL_GET_ALL_USER
                    + SQL_SEARCH_BY_DEPARTMENT_POSTFIX);
            preparedStatement.setInt(1, departmentID);
            ResultSet resultSet = preparedStatement.executeQuery();
            usersList = convertToListUsers(resultSet);
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            Display.showError(Display.ERROR_ON_SEARCH);
            System.exit(1);
        }
        return usersList;
    }

    private List<User> convertToListUsers(ResultSet resultSet) {
        List<User> usersList = new ArrayList<User>();
        try {
            while (resultSet.next()) {
                User user = new User();
                user.setAddress(resultSet.getString(ADDRESS));
                user.setBirthday(resultSet.getDate(BIRTHDAY));
                user.setDeptID(resultSet.getInt(DEPT_ID));
                user.setDeptName(resultSet.getString(EMP_NAME));
                user.setGender(resultSet.getInt(GENDER));
                user.setID(resultSet.getInt(EMP_ID));
                user.setName(resultSet.getString(EMP_NAME));
                user.setPass(resultSet.getString(EMP_PASS));
                user.setDeptName(resultSet.getString(DEPT_NAME));
                usersList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public boolean isUserExisted(int ID) {
        PreparedStatement preparedStatement;

        try {
            preparedStatement = conn.prepareStatement(SQL_CHECK_EXISTED);
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if (resultSet.getInt(TOTAL) == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public int updateExistedEmployee(User user) {
        PreparedStatement preparedStatement;
        int affectedCount = 0;
        try {
            preparedStatement = conn.prepareStatement(SQL_UPDATE_EXISTED_USER);
            preparedStatement.setString(1, user.getPass());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setInt(3, user.getGender());
            preparedStatement.setString(4, user.getAddress());
            preparedStatement.setDate(5, user.getBirthday());
            preparedStatement.setInt(6, user.getDeptID());
            preparedStatement.setInt(7, user.getID());
            affectedCount = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Display.showError(Display.ERROR_ON_UPDATE);
            System.exit(1);
        }
        return affectedCount;

    }

    public int insertNewEmployee(User user) {
        PreparedStatement preparedStatement;
        int affectedCount = 0;
        try {
            preparedStatement = conn.prepareStatement(SQL_INSERT_NEW_EMP);
            preparedStatement.setString(1, user.getPass());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setInt(3, user.getGender());
            preparedStatement.setString(4, user.getAddress());
            preparedStatement.setDate(5, user.getBirthday());
            preparedStatement.setInt(6, user.getDeptID());
            affectedCount = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Display.showError(Display.ERROR_ON_INSERT);
            System.exit(1);
        }
        return affectedCount;
    }

    public int deleteEmployee(int empID) {
        PreparedStatement preparedStatement;
        int affectedCount = 0;
        try {
            preparedStatement = conn.prepareStatement(SQL_DELETE_EMP);
            preparedStatement.setInt(1, empID);
            affectedCount = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Display.showError(Display.ERROR_ON_DELETE);
            System.exit(1);
        }
        return affectedCount;
    }
}
