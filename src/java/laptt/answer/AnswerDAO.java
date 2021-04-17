package laptt.answer;

import laptt.utilities.DBUtilities;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnswerDAO implements Serializable {

    public boolean insertAnswerToDB(String idAnswer, String answerContent, String idQuestion) throws SQLException, NamingException {

        boolean isInsert = false;

        Connection connection = DBUtilities.getSQLConnection();
        if (connection != null) {
            String sql = "INSERT INTO answer(id, answer_content, id_question) VALUES(?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, idAnswer);
            preparedStatement.setString(2, answerContent);
            preparedStatement.setString(3, idQuestion);
            isInsert = preparedStatement.executeUpdate() > 0;

            DBUtilities.closePreparedStatement(preparedStatement);
            DBUtilities.closeConnection(connection);
        }

        return isInsert;
    }

    public boolean updateAnswerToDB(String idAnswer, String answerContent, String idQuestion) throws SQLException, NamingException {

        boolean isUpdate = false;

        Connection connection = DBUtilities.getSQLConnection();
        if (connection != null) {
            String sql = "UPDATE answer SET answer_content = ? WHERE id = ? AND id_question = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, answerContent);
            preparedStatement.setString(2, idAnswer);
            preparedStatement.setString(3, idQuestion);
            isUpdate = preparedStatement.executeUpdate() > 0;

            DBUtilities.closePreparedStatement(preparedStatement);
            DBUtilities.closeConnection(connection);
        }

        return isUpdate;
    }

    public List<AnswerDTO> getAnswerDTOList(String idQuestion) throws SQLException, NamingException {

        List<AnswerDTO> answerDTOList = new ArrayList<>();

        Connection connection = DBUtilities.getSQLConnection();
        if (connection != null) {
            String sql = "SELECT id, answer_content FROM answer WHERE id_question = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, idQuestion);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AnswerDTO answerDTO = new AnswerDTO();
                answerDTO.setId(resultSet.getString("id"));
                answerDTO.setContent(resultSet.getString("answer_content"));
                answerDTO.setIdQuestion(idQuestion);
                answerDTOList.add(answerDTO);
            }

            DBUtilities.closeResultSet(resultSet);
            DBUtilities.closePreparedStatement(preparedStatement);
            DBUtilities.closeConnection(connection);
        }

        return answerDTOList;
    }
}
