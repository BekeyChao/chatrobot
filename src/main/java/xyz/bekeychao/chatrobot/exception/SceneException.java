package xyz.bekeychao.chatrobot.exception;

/**
 * @author BekeyChao@github.com
 */
public class SceneException extends Exception {
    public SceneException(Throwable cause) {
        super(cause);
    }

    public SceneException() {
        super();
    }

    protected SceneException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SceneException(String message) {
        super(message);
    }

    public SceneException(String message, Throwable cause) {
        super(message, cause);
    }
}
