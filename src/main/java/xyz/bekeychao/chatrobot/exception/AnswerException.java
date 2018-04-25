package xyz.bekeychao.chatrobot.exception;

/**
 * @author BekeyChao@github.com
 */
public class AnswerException extends Exception{
    public AnswerException() {
    }

    public AnswerException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnswerException(Throwable cause) {
        super(cause);
    }

    protected AnswerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AnswerException(String message) {
        super(message);
    }
}
