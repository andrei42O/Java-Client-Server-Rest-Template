package objprotocol.Responses;

import objprotocol.Response;

public class ExceptionResponse implements Response {
    private String message;

    public ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
