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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.openbanking.fdx.extensions.exceptions.FDXConsentException;
import org.wso2.openbanking.fdx.extensions.model.ErrorResponse;
import org.wso2.openbanking.fdx.extensions.model.PopulateConsentAuthorizeScreenRequestBody;
import org.wso2.openbanking.fdx.extensions.model.SuccessResponsePopulateConsentAuthorizeScreen;
import org.wso2.openbanking.fdx.extensions.model.SuccessResponsePopulateConsentAuthorizeScreenData;
import org.wso2.openbanking.fdx.extensions.model.SuccessResponsePopulateConsentAuthorizeScreenDataConsentData;
import org.wso2.openbanking.fdx.extensions.model.SuccessResponsePopulateConsentAuthorizeScreenDataConsumerData;
import org.wso2.openbanking.fdx.extensions.utils.FDXCommonConstants;
import org.wso2.openbanking.fdx.extensions.utils.FDXCommonUtils;
import org.wso2.openbanking.fdx.extensions.utils.FDXConsentRetrievalUtils;

import javax.ws.rs.core.Response;

/**
 * This class handles the FDX specific authorization flow for consent management.
 */
public class PopulateConsentAuthorizeScreenApiImpl {

    private static final Log log = LogFactory.getLog(PopulateConsentAuthorizeScreenApiImpl.class);

    /**
     * Handles the population of consent authorize screen data.
     *
     * @param requestBody  the request body containing necessary parameters for consent screen population
     * @return Response containing the consent and account data for the authorize screen
     */
    public static Response handlePopulateConsentAuthorizeScreen(PopulateConsentAuthorizeScreenRequestBody requestBody) {
        try {

            // FDX consent retrieval step
            SuccessResponsePopulateConsentAuthorizeScreenDataConsentData consentData = consentRetrieval(requestBody);

            // FDX account list retrieval step
            SuccessResponsePopulateConsentAuthorizeScreenDataConsumerData consumerData = accountListRetrieval();

            SuccessResponsePopulateConsentAuthorizeScreenData data =
                    new SuccessResponsePopulateConsentAuthorizeScreenData();
            data.setConsentData(consentData);
            data.setConsumerData(consumerData);

            SuccessResponsePopulateConsentAuthorizeScreen response =
                    new SuccessResponsePopulateConsentAuthorizeScreen();
            response.setResponseId(requestBody.getRequestId());
            response.setStatus(SuccessResponsePopulateConsentAuthorizeScreen.StatusEnum.SUCCESS);
            response.setData(data);

            return Response.status(Response.Status.OK).entity(new JSONObject(response).toString()).build();

        } catch (FDXConsentException ex) {
            log.error(String.format("Error while retrieving consent and account data for authorize screen: %s",
                    ex.getMessage().replaceAll("[\r\n]", "")), ex);
            return Response.status(Response.Status.OK).entity(ex.getFormattedError(requestBody.getRequestId())).build();
        } catch (JSONException | JsonProcessingException e) {
            log.error(String.format("Error while processing JSON for consent authorize screen: %s",
                    e.getMessage().replaceAll("[\r\n]", "")), e);
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setStatus(ErrorResponse.StatusEnum.ERROR);
            errorResponse.setData(FDXCommonUtils.getErrorDataObject(FDXCommonConstants.INVALID_REQUEST_MSG,
                    e.getMessage()));
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new JSONObject(errorResponse).toString()).build();
        }
    }

    /**
     * Basic consent retrieval step by FDX specs.
     *
     * @param requestBody
     */
    private static SuccessResponsePopulateConsentAuthorizeScreenDataConsentData consentRetrieval(
            PopulateConsentAuthorizeScreenRequestBody requestBody)
            throws FDXConsentException, JsonProcessingException {

        SuccessResponsePopulateConsentAuthorizeScreenDataConsentData consentData =
                new SuccessResponsePopulateConsentAuthorizeScreenDataConsentData();

        JSONArray authDetails = FDXConsentRetrievalUtils.extractAuthorizationDetails(requestBody);
        if (!authDetails.isEmpty()) {
            consentData.setBasicConsentData(FDXConsentRetrievalUtils.appendConsentDataToResponse(authDetails));
            consentData.setPermissions(FDXConsentRetrievalUtils.appendDataClusters(authDetails));
        }

        consentData.setType(FDXCommonConstants.FDX_TYPE);
        consentData.setAllowMultipleAccounts(true);
        consentData.setHandleAccountSelectionSeparately(true);
        return consentData;
    }

    /**
     * FDX Account List Retrieval step.
     */
    private static SuccessResponsePopulateConsentAuthorizeScreenDataConsumerData accountListRetrieval() {

        SuccessResponsePopulateConsentAuthorizeScreenDataConsumerData consumerData =
                new SuccessResponsePopulateConsentAuthorizeScreenDataConsumerData();
        consumerData.setAccounts(FDXConsentRetrievalUtils.retrieveAccounts());
        return consumerData;
    }
}
