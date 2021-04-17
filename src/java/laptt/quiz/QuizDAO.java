package laptt.quiz;

import laptt.utilities.DBUtilities;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO implements Serializable {

    private List<QuizDTO> quizDTOList;

    public QuizDAO() {
        this.quizDTOList = new ArrayList<>();
    }

    public List<QuizDTO> getQuizDTOList() {
        return quizDTOList;
    }

    public void setQuizDTOList(List<QuizDTO> quizDTOList) {
        this.quizDTOList = quizDTOList;
    }

    public void setQuizDTOList(String accountEmail, String idSubject) throws SQLException, NamingException {
        this.quizDTOList = new ArrayList<>();

        Connection connection = DBUtilities.getSQLConnection();
        if (connection != null) {
            String sql = "SELECT id, start_date, quantity_quiz_question, quantity_correct_answer FROM quiz WHERE account_email = ? AND id_subject = ? AND status = ? ORDER BY start_date";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, accountEmail);
            preparedStatement.setString(2, idSubject);
            preparedStatement.setString(3, "Submitted");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                QuizDTO quizDTO = new QuizDTO(
                        resultSet.getString("id"),
                        "Submitted",
                        resultSet.getDate("start_date"),
                        accountEmail,
                        idSubject,
                        resultSet.getInt("quantity_quiz_question"),
                        resultSet.getInt("quantity_correct_answer")
                );

                this.quizDTOList.add(quizDTO);
            }

            DBUtilities.closeResultSet(resultSet);
            DBUtilities.closePreparedStatement(preparedStatement);
            DBUtilities.closeConnection(connection);
        }
    }

    public boolean insertQuizToDB(String id, String accountEmail, String idSubject, int quantityQuizQuestion) throws SQLException, NamingException {

        boolean isInsert = false;

        Connection connection = DBUtilities.getSQLConnection();
        if (connection != null) {
            String sql = "INSERT INTO quiz(id, status, start_date, account_email, id_subject, quantity_quiz_question, quantity_correct_answer) VALUES(?, ?, CURRENT_TIMESTAMP, ?, ?, ?, NULL)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, "NotYetSubmitted");
            preparedStatement.setString(3, accountEmail);
            preparedStatement.setString(4, idSubject);
            preparedStatement.setInt(5, quantityQuizQuestion);
            isInsert = preparedStatement.executeUpdate() > 0;

            DBUtilities.closePreparedStatement(preparedStatement);
            DBUtilities.closeConnection(connection);
        }

        return isInsert;
    }

    public boolean updateQuizToDB(String id, String accountEmail, int quantityAnswerCorrect) throws SQLException, NamingException {

        boolean isUpdate = false;

        Connection connection = DBUtilities.getSQLConnection();
        if (connection != null) {
            String sql = "UPDATE quiz SET status = ?, quantity_correct_answer = ? WHERE id = ? AND account_email = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "Submitted");
            preparedStatement.setInt(2, quantityAnswerCorrect);
            preparedStatement.setString(3, id);
            preparedStatement.setString(4, accountEmail);
            isUpdate = preparedStatement.executeUpdate() > 0;

            DBUtilities.closePreparedStatement(preparedStatement);
            DBUtilities.closeConnection(connection);
        }

        return isUpdate;
    }
}
