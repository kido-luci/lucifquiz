package laptt.quiz;

import java.io.Serializable;
import java.util.Date;

public class QuizDTO implements Serializable {
    private String id;
    private String status;
    private Date startDate;
    private String accountEmail;
    private String idSubject;
    private int quantityQuizQuestion;
    private int quantityAnswerCorrect;

    public QuizDTO(String id, String status, Date startDate, String accountEmail, String idSubject, int quantityQuizQuestion, int quantityAnswerCorrect) {
        this.id = id;
        this.status = status;
        this.startDate = startDate;
        this.accountEmail = accountEmail;
        this.idSubject = idSubject;
        this.quantityQuizQuestion = quantityQuizQuestion;
        this.quantityAnswerCorrect = quantityAnswerCorrect;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public String getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(String idSubject) {
        this.idSubject = idSubject;
    }

    public int getQuantityQuizQuestion() {
        return quantityQuizQuestion;
    }

    public void setQuantityQuizQuestion(int quantityQuizQuestion) {
        this.quantityQuizQuestion = quantityQuizQuestion;
    }

    public int getQuantityAnswerCorrect() {
        return quantityAnswerCorrect;
    }

    public void setQuantityAnswerCorrect(int quantityAnswerCorrect) {
        this.quantityAnswerCorrect = quantityAnswerCorrect;
    }
}
