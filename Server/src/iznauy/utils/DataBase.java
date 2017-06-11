package iznauy.utils;

import iznauy.request.LoginRequest;
import iznauy.request.RegisterRequest;
import iznauy.response.LoginResponse;
import iznauy.response.RegisterResponse;
import iznauy.response.Response;

import java.sql.*;
import java.io.File;

/**
 * Created by iznauy on 2017/6/7.
 */
public abstract class DataBase {

    public synchronized static boolean init() {
        Connection c;
        Statement statement;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:users.db");
            statement = c.createStatement();
            String createUserTable = "create table if not exists users" +
                    "(userName varchar(50) primary key, " +
                    "password varchar(50))";
            statement.execute(createUserTable);
            statement.close();
            c.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public synchronized static Response login(LoginRequest loginRequest) {
        String userName = loginRequest.getUserName();
        String password = loginRequest.getPassword();
        Connection c;
        Statement statement;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:users.db");
            c.setAutoCommit(true);
            statement = c.createStatement();
            ResultSet rs1 = statement.executeQuery("select * from users where userName = '" + userName + "';");
            if (!rs1.next()) {
                return new LoginResponse(LoginResponse.NON_EXIST);
            }
            ResultSet rs2 = statement.executeQuery("select * from users where userName = '" + userName
                        + "' and password = '" + password + "';");
            if (!rs2.next()) {
                return new LoginResponse(LoginResponse.WRONG_PASSWORD);
            }
            return new LoginResponse(LoginResponse.SUCCESS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new LoginResponse(Response.UNKNOWN_REASON);
        } catch (SQLException e) {
            e.printStackTrace();
            return new LoginResponse(Response.UNKNOWN_REASON);
        }
    }

    public synchronized static Response register(RegisterRequest registerRequest) {
        String userName = registerRequest.getUserName();
        String password = registerRequest.getPassword();
        Connection c;
        Statement statement;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:users.db");
            c.setAutoCommit(true);
            statement = c.createStatement();
            ResultSet rs1 = statement.executeQuery("select * from users where userName = '" + userName + "';");
            if (rs1.next()) {
                return new RegisterResponse(RegisterResponse.HAS_REGISTER);
            }
            String insert = "insert into users (userName, password) values ('" + userName +
                    "', '" + password + "');";
            statement.executeUpdate(insert);
            statement.close();
            c.close();
            try {
                File bf = new File(userName + "/bf/");
                bf.mkdirs();
                File ook = new File(userName + "/ook/");
                ook.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return new RegisterResponse(Response.UNKNOWN_REASON);
            }
            return new RegisterResponse(Response.SUCCESS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new RegisterResponse(Response.UNKNOWN_REASON);
        } catch (SQLException e) {
            e.printStackTrace();
            return new RegisterResponse(Response.UNKNOWN_REASON);
        }
    }

}
