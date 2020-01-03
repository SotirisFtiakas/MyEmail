import java.util.ArrayList;
import java.util.List;

public class Account {

    private String username;
    private String password;
    private List<Email> mailbox = new ArrayList<>(); /**List or ArrayList**/

    Account(String username,String password){
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void addEmail(boolean isNew,String sender,String receiver,String subject,String mainBody){
        Email email= new Email(isNew, sender, receiver, subject, mainBody);
        mailbox.add(email);
    }

}
