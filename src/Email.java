
public class Email {

    Email(boolean isNew,String sender,String receiver,String subject,String mainBody){
        this.setIsNew(isNew);
        this.setMainBody(mainBody);
        this.setReceiver(receiver);
        this.setSender(sender);
        this.setSubject(subject);
    }

    private boolean isNew;
    private String sender;
    private String receiver;
    private String subject;
    private String mainBody;

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public String getMainBody() {
        return mainBody;
    }

    public String getSubject() {
        return subject;
    }

    public boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) { this.isNew = isNew; }

    public void setMainBody(String mainBody) {
        this.mainBody = mainBody;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}

