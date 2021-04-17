package laptt.subject;

import laptt.utilities.DBUtilities;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO implements Serializable {

    private List<SubjectDTO> subjectDTOList;

    public List<SubjectDTO> getSubjectDTOList() {
        return subjectDTOList;
    }

    public void setSubjectDTOList(List<SubjectDTO> subjectDTOList) {
        this.subjectDTOList = subjectDTOList;
    }

    public void setSubjectDTOList() throws SQLException, NamingException {

        Connection connection = DBUtilities.getSQLConnection();
        if (connection != null) {
            subjectDTOList = new ArrayList<>();

            String sql = "SELECT id, name FROM subject";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                subjectDTOList.add(new SubjectDTO(
                        resultSet.getString("id"),
                        resultSet.getString("name")
                ));
            }

            DBUtilities.closeResultSet(resultSet);
            DBUtilities.closePreparedStatement(preparedStatement);
            DBUtilities.closeConnection(connection);
        }
    }
}
