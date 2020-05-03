
public class Email {

    private boolean isNew;
    private String sender;
    private String receiver;
    private String subject;
    private String mainBody;

    Email(boolean isNew,String sender,String receiver,String subject,String mainBody){
        this.setIsNew(isNew);
        this.setMainBody(mainBody);
        this.setReceiver(receiver);
        this.setSender(sender);
        this.setSubject(subject);
    }



    String getReceiver() {
        return receiver;
    }

    String getSender() {
        return sender;
    }

    String getMainBody() {
        return mainBody;
    }

    String getSubject() {
        return subject;
    }

    boolean getIsNew() {
        return isNew;
    }

    void setIsNew(boolean isNew) { this.isNew = isNew; }

    private void setMainBody(String mainBody) {
        this.mainBody = mainBody;
    }

    private void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    private void setSender(String sender) {
        this.sender = sender;
    }

    private void setSubject(String subject) {
        this.subject = subject;
    }

}

