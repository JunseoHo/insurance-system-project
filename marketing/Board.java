package marketing;

import java.io.Serializable;

public class Board implements Serializable {

    private static final long serialVersionUID = 1L;
    private String statementId;
    private String title;
    private String content;
    private String customerId;
    private String answer;
    private int processed;

    public Board(String statementId, String title, String content, String customerId, String answer, int processed) {

        this.statementId = statementId;
        this.title = title;
        this.content = content;
        this.customerId = customerId;
        this.answer = answer;
        this.processed = processed;
    }

    public String getStatementId() {
        return statementId;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getProcessed() {
        return processed;
    }

    public void setProcessed(int processed) {
        this.processed = processed;
    }
}