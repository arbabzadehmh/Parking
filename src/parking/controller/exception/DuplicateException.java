package parking.controller.exception;

public class DuplicateException extends Exception{
    private String message;

    public DuplicateException() {
        super();
        message = "Duplicate User Name";
    }

    public DuplicateException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
