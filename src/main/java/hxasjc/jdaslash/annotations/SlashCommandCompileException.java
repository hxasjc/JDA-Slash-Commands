package hxasjc.jdaslash.annotations;

public class SlashCommandCompileException extends RuntimeException {
    public SlashCommandCompileException() {
        super();
    }

    public SlashCommandCompileException(String message) {
        super(message);
    }

    public SlashCommandCompileException(String message, Throwable cause) {
        super(message, cause);
    }

    public SlashCommandCompileException(Throwable cause) {
        super(cause);
    }

    protected SlashCommandCompileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
