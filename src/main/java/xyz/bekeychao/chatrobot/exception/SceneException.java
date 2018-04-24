package xyz.bekeychao.chatrobot.exception;

public class SceneException extends Exception {
    public SceneException(Throwable cause) {
        super(cause);
    }

    public SceneException(String message) {
        super(message);
    }

    public SceneException(String message, Throwable cause) {
        super(message, cause);
    }
}
