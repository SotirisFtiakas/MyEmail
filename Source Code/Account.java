import java.util.ArrayList;
import java.util.List;

class Account {

    private String username;
    private String password;
    private List<Email> mailbox = new ArrayList<>();

    Account(String username,String password){
        this.setUsername(username);
        this.setPassword(password);
    }

    String getPassword() {
        return password;
    }

    String getUsername() {
        return username;
    }

    List<Email> getMailbox() { return mailbox; }

    private void setPassword(String password) {
        this.password = password;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    void addEmail(boolean isNew, String sender, String receiver, String subject, String mainBody){
        Email email= new Email(isNew, sender, receiver, subject, mainBody);
        mailbox.add(email);
    }

}
