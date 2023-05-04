package space.sedov.server.form;

public class SigninForm {

    private String username;

    private String password;

    private String email;

    public SigninForm() {
    }

    public SigninForm(String username, String password, String matchingPassword, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
