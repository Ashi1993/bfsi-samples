/*
 * Copyright (c) 2026, WSO2 LLC. (https://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package org.wso2.openbanking.fdx.extensions.impl.consent;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.openbanking.fdx.extensions.model.ErrorResponse;
import org.wso2.openbanking.fdx.extensions.model.PersistAuthorizedConsentRequestBody;
import org.wso2.openbanking.fdx.extensions.model.SuccessResponsePersistAuthorizedConsent;
import org.wso2.openbanking.fdx.extensions.model.SuccessResponsePersistAuthorizedConsentData;
import org.wso2.openbanking.fdx.extensions.utils.FDXCommonConstants;
import org.wso2.openbanking.fdx.extensions.utils.FDXCommonUtils;
import org.wso2.openbanking.fdx.extensions.utils.FDXConsentPersistUtils;

import javax.ws.rs.core.Response;

/**
 * This class handles the FDX specific authorization flow for consent management.
 */
public class PersistAuthorizedConsentApiImpl {

    private static final Log log = LogFactory.getLog(PersistAuthorizedConsentApiImpl.class);

    /**
     * Handles the persistence of authorized consent data.
     *
     * @param requestBody  the request body containing the consent data to be persisted
     * @return Response containing the status of the persistence operation
     */
    public static Response handlePersistAuthorizedConsent(PersistAuthorizedConsentRequestBody requestBody) {
        try {
            // Consent persist step
            SuccessResponsePersistAuthorizedConsentData persistConsentData = FDXConsentPersistUtils
                    .consentPersist(requestBody);

            SuccessResponsePersistAuthorizedConsent response = new SuccessResponsePersistAuthorizedConsent();
            response.setResponseId(requestBody.getRequestId());
            response.setStatus(SuccessResponsePersistAuthorizedConsent.StatusEnum.SUCCESS);
            response.setData(persistConsentData);

            return Response.status(Response.Status.OK).entity(new JSONObject(response).toString()).build();

//        } catch (FDXConsentException ex) {
//            log.error(String.format("Error while persisting the consent: %s",
//                    ex.getMessage().replaceAll("[\r\n]", "")), ex);
//            return Response.status(Response.Status.OK).entity(ex.getFormattedError(requestBody.getRequestId()))
//            .build();
        } catch (JSONException | JsonProcessingException e) {
            log.error(String.format("Error while processing JSON for consent persistence: %s",
                    e.getMessage().replaceAll("[\r\n]", "")), e);
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setStatus(ErrorResponse.StatusEnum.ERROR);
            errorResponse.setData(FDXCommonUtils.getErrorDataObject(FDXCommonConstants.INVALID_REQUEST_MSG,
                    e.getMessage()));
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new JSONObject(errorResponse).toString()).build();
        }
    }
}
