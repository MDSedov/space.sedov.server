package space.sedov.server.service.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class UserRequest {

    //Поля
    private String username;
    @Size(message = "PASSWORD_MIN_SIZE", min = 8)
    private String password;
    @Size(message = "PASSWORD_MIN_SIZE", min = 8)
    private String confirmationPassword;
    @Email(message = "EMAIL_HAS_INCORRECT_FORMAT")
    private String email;
    @Email(message = "EMAIL_HAS_INCORRECT_FORMAT")
    private String confirmationEmail;
    private String firstName;
    private String lastName;
    private String patronymicName;

    //Конструкторы
    public UserRequest() {
    }

    public UserRequest(String username,
                       String password,
                       String confirmationPassword,
                       String email,
                       String confirmationEmail,
                       String firstName,
                       String lastName,
                       String patronymicName) {
        setUsername(username);
        setPassword(password);
        setConfirmationPassword(confirmationPassword);
        setEmail(email);
        setConfirmationEmail(confirmationEmail);
        setFirstName(firstName);
        setLastName(lastName);
        setPatronymicName(patronymicName);
    }

//    public User toUser(PasswordEncoder passwordEncoder) {
//        return new User(username, passwordEncoder.encode(password), email);
//    }

    //Получаем значения полей формы
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getConfirmationPassword() {
        return confirmationPassword;
    }
    public String getEmail() {
        return email;
    }
    public String getConfirmationEmail() {
        return confirmationEmail;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPatronymicName() {
        return patronymicName;
    }

    //Устанавливаем значения полей формы
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setConfirmationPassword(String confirmPassword) {
        this.confirmationPassword = confirmPassword;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setConfirmationEmail(String confirmationEmail) {
        this.confirmationEmail = confirmationEmail;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setPatronymicName(String patronymicName) {
        this.patronymicName = patronymicName;
    }
}
