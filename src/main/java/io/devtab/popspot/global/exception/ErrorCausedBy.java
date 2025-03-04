package io.devtab.popspot.global.exception;

public record ErrorCausedBy (
    HttpStatusCode httpStatusCode,
    ReasonCode reasonCode
) {

    public static ErrorCausedBy of(HttpStatusCode statusCode, ReasonCode reasonCode) {
        return new ErrorCausedBy(statusCode, reasonCode);
    }

    public String getCode() {
        return String.valueOf(httpStatusCode.getCode());
    }

    public int getIntegerCode() {
        return httpStatusCode.getCode();
    }

    public String getReason() {
        return reasonCode.name();
    }

    public HttpStatusCode getStatusCode(){
        return httpStatusCode;
    }
}
