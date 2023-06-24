package space.sedov.server.entity;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name="tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "reason")
    private String reason;

    @Column(name = "email")
    private String email;

    @Column(name = "token")
    private String token;

    @Column(name = "is_valid")
    private boolean isValid;

    @Column(name = "expiration_date")
    private ZonedDateTime expirationDate;

    public Token() {
    }

    public Token(int userId, String email, String reason, String token) {
        setUserId(userId);
        setToken(token);
        setReason(reason);
        setEmail(email);
    }

    @PrePersist
    public void beforePersist() {
        this.calculateExpirationDate();
        this.setValid(true);
    }

    private void calculateExpirationDate() {
        ZonedDateTime currentTime = ZonedDateTime.now();
        this.expirationDate = currentTime.plusHours(24);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ZonedDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(ZonedDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
