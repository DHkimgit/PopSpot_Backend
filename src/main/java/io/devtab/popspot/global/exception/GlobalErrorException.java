package io.devtab.popspot.global.exception;

import lombok.Getter;

@Getter
public class GlobalErrorException extends RuntimeException {
    private final BaseErrorCode baseErrorCode;

    public GlobalErrorException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode.causedBy().reasonCode().name());
        this.baseErrorCode = baseErrorCode;
    }

    public ErrorCausedBy causedBy() {
        return baseErrorCode.causedBy();
    }

    @Override
    public String toString() {
        return "GlobalErrorException(code=" + baseErrorCode.causedBy().getCode()
            + ", message=" + baseErrorCode.getExplainError() + ")";
    }
}
