package io.devtab.popspot.global.exception;

public class CompanyErrorException extends GlobalErrorException {

    private final CompanyErrorCode companyErrorCode;

    public CompanyErrorException(CompanyErrorCode companyErrorCode) {
        super(companyErrorCode);
        this.companyErrorCode = companyErrorCode;
    }

    public ErrorCausedBy causedBy() {
        return companyErrorCode.causedBy();
    }

    public String getExplainError() {
        return companyErrorCode.getExplainError();
    }
}
