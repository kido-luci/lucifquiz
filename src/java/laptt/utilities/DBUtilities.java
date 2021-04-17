package laptt.utilities;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtilities implements Serializable {

    public static Connection getSQLConnection() throws NamingException, SQLException {
        Context context = new InitialContext();
        Context tomcatContext = (Context) context.lookup("java:comp/env");

        DataSource dataSource = (DataSource) tomcatContext.lookup("Lab2DataSource");

        return dataSource.getConnection();
    }

    public static void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public static void closeResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
    }

    public static void closePreparedStatement(PreparedStatement preparedStatement) throws SQLException {
        if(preparedStatement != null){
            preparedStatement.close();
        }
    }

    public static String takeNewId() throws SQLException, NamingException {

        String newId = null;

        Connection connection = getSQLConnection();
        if (connection != null) {
            String sql1 = "DECLARE @id uniqueidentifier; SET @id = NEWID(); SELECT @id;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql1);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                newId = resultSet.getString("");
            }

            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
            closeConnection(connection);
        }

        return newId;
    }
}
