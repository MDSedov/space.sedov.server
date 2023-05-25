package space.sedov.server.service.email;

public interface EmailServiceInterface {
    public void sendEmail(String to, String subject, String message);
}
