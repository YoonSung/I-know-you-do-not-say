package gaongil.dto.cloud;

/**
 * Created by yoon on 15. 7. 7..
 */
public class DialogForm {

    private String question;
    private String deny;
    private String confirm;
    private String comment;
    private String returnUrl;

    public DialogForm(String question, String deny, String confirm, String comment, String returnUrl) {
        this.question = question;
        this.deny = deny;
        this.confirm = confirm;
        this.comment = comment;
        this.returnUrl = returnUrl;
    }
}
