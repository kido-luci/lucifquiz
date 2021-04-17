package laptt.question;

import laptt.answer.AnswerDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class QuestionDTO implements Serializable {

    private String id;
    private String questionContent;
    private String idAnswerCorrect;
    private Date createDate;
    private String idSubject;
    private String status;
    private List<AnswerDTO> answerDTOList;

    public QuestionDTO(String id, String questionContent, String idAnswerCorrect, Date createDate, String idSubject, String status, List<AnswerDTO> answerDTOList) {
        this.id = id;
        this.questionContent = questionContent;
        this.idAnswerCorrect = idAnswerCorrect;
        this.createDate = createDate;
        this.idSubject = idSubject;
        this.status = status;
        this.answerDTOList = answerDTOList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getIdAnswerCorrect() {
        return idAnswerCorrect;
    }

    public void setIdAnswerCorrect(String idAnswerCorrect) {
        this.idAnswerCorrect = idAnswerCorrect;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(String idSubject) {
        this.idSubject = idSubject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AnswerDTO> getAnswerDTOList() {
        return answerDTOList;
    }

    public void setAnswerDTOList(List<AnswerDTO> answerDTOList) {
        this.answerDTOList = answerDTOList;
    }
}
