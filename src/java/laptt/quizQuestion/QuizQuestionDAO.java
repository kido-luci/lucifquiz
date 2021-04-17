package laptt.quizQuestion;

import laptt.utilities.DBUtilities;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QuizQuestionDAO implements Serializable {

    public boolean insertQuizQuestionToDB(String idQuiz, String idQuestion, String idAnswerCorrect) throws SQLException, NamingException {

        boolean isInsert = false;

        Connection connection = DBUtilities.getSQLConnection();
        if (connection != null) {
            String sql = "INSERT INTO quizQuestion(id_quiz, id_question, id_answer_correct, id_answer_chosen) VALUES(?, ?, ?, NULL)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, idQuiz);
            preparedStatement.setString(2, idQuestion);
            preparedStatement.setString(3, idAnswerCorrect);
            isInsert = preparedStatement.executeUpdate() > 0;

            DBUtilities.closePreparedStatement(preparedStatement);
            DBUtilities.closeConnection(connection);
        }

        return isInsert;
    }

    public boolean updateQuizToDB(String idQuiz, String idQuestion, String idAnswerChosen) throws SQLException, NamingException {

        boolean isUpdate = false;

        Connection connection = DBUtilities.getSQLConnection();
        if (connection != null) {
            String sql = "UPDATE quizQuestion SET id_answer_chosen = ? WHERE id_quiz = ? AND id_question = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, idAnswerChosen);
            preparedStatement.setString(2, idQuiz);
            preparedStatement.setString(3, idQuestion);
            isUpdate = preparedStatement.executeUpdate() > 0;

            DBUtilities.closePreparedStatement(preparedStatement);
            DBUtilities.closeConnection(connection);
        }

        return isUpdate;
    }
}
