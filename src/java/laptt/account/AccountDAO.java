package laptt.account;

import laptt.utilities.DBUtilities;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO implements Serializable {

    public AccountDTO getAccount(String email, String password) throws SQLException, NamingException {

        AccountDTO accountDTO = null;

        Connection connection = DBUtilities.getSQLConnection();
        if (connection != null) {
            String sql = "SELECT name, status, role FROM account WHERE email = ? AND password = ? AND (status = 'new' OR status = 'activate')";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                accountDTO = new AccountDTO(
                        email,
                        resultSet.getString("name"),
                        password,
                        resultSet.getString("role"),
                        resultSet.getString("status")
                );
            }

            DBUtilities.closeResultSet(resultSet);
            DBUtilities.closePreparedStatement(preparedStatement);
            DBUtilities.closeConnection(connection);
        }

        return accountDTO;
    }

    public AccountDTO getAccount(String idFacebook) throws SQLException, NamingException {

        AccountDTO accountDTO = null;

        Connection connection = DBUtilities.getSQLConnection();
        if (connection != null) {
            String sql = "SELECT name, status, role, password, email FROM account WHERE fb_id = ? AND (status = 'new' OR status = 'activate')";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, idFacebook);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                accountDTO = new AccountDTO(
                        resultSet.getString("email"),
                        resultSet.getString("name"),
                        resultSet.getString("password"),
                        resultSet.getString("role"),
                        resultSet.getString("status")
                );
            }

            DBUtilities.closeResultSet(resultSet);
            DBUtilities.closePreparedStatement(preparedStatement);
            DBUtilities.closeConnection(connection);
        }

        return accountDTO;
    }

    public boolean insertAccountToDB(String email, String name, String password) throws SQLException, NamingException {

        boolean isInsert = false;

        Connection connection = DBUtilities.getSQLConnection();
        if (connection != null) {
            String sql = "INSERT INTO account(email, name, password, role, status) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, "student");
            preparedStatement.setString(5, "new");

            isInsert = preparedStatement.executeUpdate() > 0;

            DBUtilities.closePreparedStatement(preparedStatement);
            DBUtilities.closeConnection(connection);
        }

        return isInsert;
    }
}
