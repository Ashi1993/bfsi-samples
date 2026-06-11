package org.wso2.openbanking.fdx.extensions.impl.consent;

import org.json.JSONObject;
import org.wso2.openbanking.fdx.extensions.model.SuccessResponse;
import org.wso2.openbanking.fdx.extensions.model.ValidateConsentAccessRequestBody;

import javax.ws.rs.core.Response;

/**
 * Validate Consent Access API Impl
 */
public class ValidateConsentAccessApiImpl {

    public static Response handleConsentAccessValidation(ValidateConsentAccessRequestBody consentAccessRequestBody) {

        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setResponseId(consentAccessRequestBody.getRequestId());
        successResponse.setStatus(SuccessResponse.StatusEnum.SUCCESS);

        return Response.status(Response.Status.OK).entity(new JSONObject(successResponse).toString()).build();
    }
}
