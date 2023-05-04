package space.sedov.server.service.authentication;

public enum MessageResponseService {

    NO_USER_WITH_USERNAME("Не удалось найти пользователя с указанным именем"),
    USER_EMAIL_ALREADY_EXIST("Указанный адрес электронной почты уже используется"),
    USER_USERNAME_ALREADY_EXIST("Указанное имя пользователя уже занято"),
    NEW_PASSWORD_IS_THE_SAME("New password is the same as old one"),
    NEW_PASSWORD_MISMATCHED("Пароль и подтверждения пароля не совпадают"),
    FORBIDDEN_ACTION("The action is forbidden for current user"),
    TRANSACTION_PROBLEM("Transaction is failed."),
    EMAIL_SENDING_PROBLEM("Sending email failed."),
    UKNOWN_PROBLEM("Неизвестная проблема"),
    OK("Успешно"),
    ERROR("Ошибка");

    private final String msg;

    MessageResponseService(final String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
