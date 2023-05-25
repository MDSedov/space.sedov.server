package space.sedov.server.service.response;

public enum MessageService {
    USER_USERNAME_ALREADY_EXIST("Указанное имя пользователя уже занято"),
    EMAIL_HAS_INCORRECT_FORMAT("Адрес электронный почты должен иметь корректный формат"),
    EMAIL_IS_NOT_CONFIRM("Необходимо подтвердить адрес электронной почты"),
    EMAIL_MISMATCHED("Новый адрес электронной почты и его потдверждение не совпадают"),
    EMAIL_IS_ALREADY_USE("Указанный адрес электронной почты уже используется"),
    EMAIL_CONFIRMED("Адрес электронной почты успешно подтвержден"),
    NEW_EMAIL_IS_THE_SAME("Новый адрес электронный почты не должен совпадать с текущим"),
    EMAIL_SENT_SUCCESSFULLY("Письмо для подтверждения адреса вашей электронной почты успешно отправлено"),
    PASSWORDS_MISMATCHED("Пароль и подтверждения пароля не совпадают"),
    PASSWORD_MIN_SIZE("Пароль должен состоять из 8 или более символов"),
    NEW_PASSWORD_IS_THE_SAME("Новый пароль не должен совпадать с текущим паролем"),
    PASSWORD_CHANGED("Пароль успешно обновлен"),
    TOKEN_NOT_FOUND("Некорректная ссылка или код-подтверждения адреса электронной почты"),
    TOKEN_INVALID("Использован некорректный код-подтверждения"),
    TOKEN_EXPIRED("Истек срок действия ссылки или кода-подтверждения адреса электронной почты"),
    PERSONAL_DATA_UPDATED("Персональные данные пользователя успешно обновлены"),
    UNKNOWN_PROBLEM("Неизвестная проблема"),
    OK("Успешно"),
    USER_FOUND("true"),
    USER_NOT_FOUND("Не удалось найти пользователя с указанным именем");

    private final String msg;

    MessageService(final String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
