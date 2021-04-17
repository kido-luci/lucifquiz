package laptt.answer;

import java.io.Serializable;

public class AnswerDTO implements Serializable {

    private String id;
    private String content;
    private String idQuestion;

    public AnswerDTO() {
        this.id = "";
        this.content = "";
        this.idQuestion = "";
    }

    public AnswerDTO(String id, String content, String idQuestion) {
        this.id = id;
        this.content = content;
        this.idQuestion = idQuestion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(String idQuestion) {
        this.idQuestion = idQuestion;
    }
}
