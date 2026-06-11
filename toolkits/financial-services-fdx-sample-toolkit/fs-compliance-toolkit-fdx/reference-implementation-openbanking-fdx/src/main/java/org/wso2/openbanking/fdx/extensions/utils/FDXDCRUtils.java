package org.wso2.openbanking.fdx.extensions.utils;

import org.json.JSONObject;
import org.wso2.openbanking.fdx.extensions.model.FailedResponseClientProcess;
import org.wso2.openbanking.fdx.extensions.model.FailedResponseClientProcessData;
import org.wso2.openbanking.fdx.extensions.model.SuccessResponseClientProcess;
import org.wso2.openbanking.fdx.extensions.model.SuccessResponseClientProcessData;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Utility class for DCR related operations such as payload validation and response construction.
 */
public class FDXDCRUtils {

    public static JSONObject validateDCRPayload(Object appRegistrationRequest, Object ssaParams) {
        // Validate the payload attributes
        JSONObject response = new JSONObject();

        response.put(FDXCommonConstants.DCR_IS_ERROR, false);
        return response;
    }

    public static Map<String, Object> getDCRPayloadAttributes(Object appRegistrationRequest) {

        Map<String, Object> appRegistrationRequestMap = (Map<String, Object>) appRegistrationRequest;

        LinkedHashMap<String, Object> additionalAttributes = (LinkedHashMap<String, Object>)
                appRegistrationRequestMap.get("additionalAttributes");

        return new HashMap<>(additionalAttributes);
    }

    /**
     * Construct a success response for DCR validation.
     * @param requestId           Request ID
     * @param attributesToReturn  Attributes to return in the response
     * @return SuccessResponseDCRValidation object
     */
    public static SuccessResponseClientProcess constructSuccessResponse(String requestId,
                                                                        Map<String, Object> attributesToReturn) {

        SuccessResponseClientProcessData data = new SuccessResponseClientProcessData();
        data.setClientData(attributesToReturn);

        SuccessResponseClientProcess successResponseDCRValidation = new SuccessResponseClientProcess();
        successResponseDCRValidation.setResponseId(requestId);
        successResponseDCRValidation.setData(data);
        successResponseDCRValidation.setStatus(SuccessResponseClientProcess.StatusEnum.SUCCESS);
        return successResponseDCRValidation;
    }

    /**
     * Construct a success response for DCR validation.
     *
     * @param errorResponse  Error response from DCR
     * @return FailedResponseDCRValidation object
     */
    public static FailedResponseClientProcess constructErrorResponse(JSONObject errorResponse) {

        FailedResponseClientProcessData data = new FailedResponseClientProcessData();
        data.setError((FailedResponseClientProcessData.ErrorEnum) errorResponse
                .get(FDXCommonConstants.DCR_ERROR_MSG));
        data.setErrorDescription(errorResponse.getString(FDXCommonConstants.DCR_ERROR_DESCRIPTION));

        FailedResponseClientProcess failedResponseDCRValidation = new FailedResponseClientProcess();
        failedResponseDCRValidation.setData(data);
        failedResponseDCRValidation.setErrorCode(errorResponse.getInt(FDXCommonConstants.DCR_ERROR_CODE));
        failedResponseDCRValidation.setStatus(FailedResponseClientProcess.StatusEnum.ERROR);
        return failedResponseDCRValidation;
    }
}
