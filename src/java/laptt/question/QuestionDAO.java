package laptt.question;

import laptt.answer.AnswerDAO;
import laptt.answer.AnswerDTO;
import laptt.utilities.DBUtilities;

import javax.naming.NamingException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Set;

public class QuestionDAO implements Serializable {

    private List<QuestionDTO> questionDTOList = new ArrayList<>();

    public List<QuestionDTO> getQuestionDTOList() {
        return questionDTOList;
    }

    public void setQuestionDTOList(List<QuestionDTO> questionDTOList) {
        this.questionDTOList = questionDTOList;
    }

    public void setQuestionDTOList(String questionContent, String idSubject, String status) throws SQLException, NamingException, ParseException {

        questionDTOList = new ArrayList<>();

        Connection connection = DBUtilities.getSQLConnection();
        if (connection != null) {
            String sql = "SELECT id, question_content, id_answer_correct, create_date FROM question WHERE question_content LIKE ? AND id_subject = ? AND status = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, questionContent);
            preparedStatement.setString(2, idSubject);
            preparedStatement.setString(3, status);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id =  resultSet.getString("id");

                AnswerDAO answerDAO = new AnswerDAO();
                List<AnswerDTO> answerDTOList = answerDAO.getAnswerDTOList(id);

                DateFormat formatter = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
                Date createDate = (Date) formatter.parse(resultSet.getString("create_date"));

                questionDTOList.add(new QuestionDTO(
                        id,
                        resultSet.getString("question_content"),
                        resultSet.getString("id_answer_correct"),
                        createDate,
                        idSubject,
                        status,
                        answerDTOList
                ));
            }

            DBUtilities.closeResultSet(resultSet);
            DBUtilities.closePreparedStatement(preparedStatement);
            DBUtilities.closeConnection(connection);
        }

    }

    public void setQuestionDTOList(String idSubject, int questionQuantity) throws SQLException, NamingException, ParseException {

        questionDTOList = new ArrayList<>();

        Connection connection = DBUtilities.getSQLConnection();
        if (connection != null) {

            String sql = "SELECT TOP " + questionQuantity +
                    " id, question_content, id_answer_correct, create_date FROM question WHERE id_subject = ? AND status = ? ORDER BY NEWID()";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, idSubject);
            preparedStatement.setString(2, "Activate");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id =  resultSet.getString("id");

                AnswerDAO answerDAO = new AnswerDAO();
                List<AnswerDTO> answerDTOList = answerDAO.getAnswerDTOList(id);

                DateFormat formatter = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
                Date createDate = (Date) formatter.parse(resultSet.getString("create_date"));

                questionDTOList.add(new QuestionDTO(
                        id,
                        resultSet.getString("question_content"),
                        resultSet.getString("id_answer_correct"),
                        createDate,
                        idSubject,
                        "Activate",
                        answerDTOList
                ));
            }

            DBUtilities.closeResultSet(resultSet);
            DBUtilities.closePreparedStatement(preparedStatement);
            DBUtilities.closeConnection(connection);
        }

    }

    public boolean insertQuestionToDB(String questionContent, String answer1, String answer2, String answer3, String answer4, String idSubject, String correctAnswer) throws SQLException, NamingException, SQLException, NamingException {

        boolean isInsert = false;

        List<String> newIdList = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            newIdList.add(DBUtilities.takeNewId());
        }

        if (newIdList.size() == 5) {
            Connection connection = DBUtilities.getSQLConnection();
            if (connection != null) {
                String idAnswerCorrect = newIdList.get(Integer.parseInt(correctAnswer.substring(correctAnswer.length() - 1))  - 1);
                String idQuestion = newIdList.get(4);
                String sql = "INSERT INTO question(id, question_content, id_answer_correct, create_date, id_subject, status) VALUES(?, ?, ?, CURRENT_TIMESTAMP, ?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, idQuestion);
                preparedStatement.setString(2, questionContent);
                preparedStatement.setString(3, idAnswerCorrect);
                preparedStatement.setString(4, idSubject);
                preparedStatement.setString(5, "Activate");
                isInsert = preparedStatement.executeUpdate() > 0;

                DBUtilities.closePreparedStatement(preparedStatement);
                DBUtilities.closeConnection(connection);
            }
        }

        if (isInsert) {
            List<String> answerList = new ArrayList<>();
            answerList.add(answer1);
            answerList.add(answer2);
            answerList.add(answer3);
            answerList.add(answer4);

            String idQuestion = newIdList.get(4);

            AnswerDAO answerDAO = new AnswerDAO();

            for (int i = 0; i <= 3; i++) {
                isInsert = answerDAO.insertAnswerToDB(newIdList.get(i), answerList.get(i), idQuestion);
                if (!isInsert) {
                    break;
                }
            }
        }

        return isInsert;
    }

    public boolean updateQuestionToDB(String questionContent, String answer1, String answer2, String answer3, String answer4, String idAnswer1, String idAnswer2, String idAnswer3, String idAnswer4, String idSubject, String status, String idCorrectAnswer, String idQuestion) throws SQLException, NamingException, SQLException, NamingException {

        boolean isUpdate = false;

        Connection connection = DBUtilities.getSQLConnection();
        if (connection != null) {
            String sql = "UPDATE question SET question_content = ?, id_answer_correct = ?, id_subject = ?, status = ? WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, questionContent);
            preparedStatement.setString(2, idCorrectAnswer);
            preparedStatement.setString(3, idSubject);
            preparedStatement.setString(4, status);
            preparedStatement.setString(5, idQuestion);
            isUpdate = preparedStatement.executeUpdate() > 0;

            DBUtilities.closePreparedStatement(preparedStatement);
            DBUtilities.closeConnection(connection);
        }

        if (isUpdate) {
            HashMap<String, String> answerHashMap = new HashMap<>();
            answerHashMap.put(idAnswer1, answer1);
            answerHashMap.put(idAnswer2, answer2);
            answerHashMap.put(idAnswer3, answer3);
            answerHashMap.put(idAnswer4, answer4);

            AnswerDAO answerDAO = new AnswerDAO();

           Set<String> idAnswerSet = answerHashMap.keySet();
           for (String idAnswer : idAnswerSet) {
               isUpdate = answerDAO.updateAnswerToDB(idAnswer, answerHashMap.get(idAnswer), idQuestion);
               if (!isUpdate) {
                   break;
               }
           }
        }

        return isUpdate;
    }
}
