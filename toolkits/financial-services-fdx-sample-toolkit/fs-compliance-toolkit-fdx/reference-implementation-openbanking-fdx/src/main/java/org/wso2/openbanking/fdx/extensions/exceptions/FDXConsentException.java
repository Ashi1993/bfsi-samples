package org.wso2.openbanking.fdx.extensions.exceptions;

import org.json.JSONObject;
import org.wso2.openbanking.fdx.extensions.model.FailedResponseInConsentAuthorize;
import org.wso2.openbanking.fdx.extensions.model.FailedResponseInConsentAuthorizeData;

/**
 * Exception class to build and return authorisation errors
 */
public class FDXConsentException extends Exception {

    /**
     * Enum representing the error codes for UK consent exceptions.
     */
    public enum ErrorCode {
        BAD_REQUEST(400),
        INTERNAL_SERVER_ERROR(500);

        private final int code;

        ErrorCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    private final ErrorCode errorCode;
    private String newConsentStatus;

    public FDXConsentException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public FDXConsentException(ErrorCode errorCode, String message, String newConsentStatus) {
        super(message);
        this.errorCode = errorCode;
        this.newConsentStatus = newConsentStatus;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getFormattedError(String responseId) {
        FailedResponseInConsentAuthorizeData responseData = new FailedResponseInConsentAuthorizeData();
        responseData.setErrorMessage(this.getMessage());
        if (newConsentStatus != null && !newConsentStatus.isEmpty()) {
            responseData.setNewConsentStatus(newConsentStatus);
        }

        FailedResponseInConsentAuthorize failedResponse = new FailedResponseInConsentAuthorize();
        failedResponse.setResponseId(responseId);
        failedResponse.setStatus(FailedResponseInConsentAuthorize.StatusEnum.ERROR);
        failedResponse.setData(responseData);

        return new JSONObject(failedResponse).toString();
    }
}
