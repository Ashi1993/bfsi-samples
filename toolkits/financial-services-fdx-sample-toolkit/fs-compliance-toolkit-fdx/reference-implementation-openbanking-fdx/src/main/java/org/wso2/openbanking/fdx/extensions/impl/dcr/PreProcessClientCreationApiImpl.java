/*
 * Copyright (c) 2026, WSO2 LLC. (https://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package org.wso2.openbanking.fdx.extensions.impl.dcr;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.wso2.openbanking.fdx.extensions.model.ClientProcessData;
import org.wso2.openbanking.fdx.extensions.model.ClientProcessRequestBody;
import org.wso2.openbanking.fdx.extensions.model.FailedResponseClientProcess;
import org.wso2.openbanking.fdx.extensions.model.SuccessResponseClientProcess;
import org.wso2.openbanking.fdx.extensions.utils.FDXCommonConstants;
import org.wso2.openbanking.fdx.extensions.utils.FDXDCRUtils;

import java.util.Map;

import javax.ws.rs.core.Response;

/**
 * Implementation class for pre-processing client creation API.
 */
public class PreProcessClientCreationApiImpl {

    private static final Log log = LogFactory.getLog(PreProcessClientCreationApiImpl.class);

    /**
     * Handles the DCR client creation process including validations and constructing responses.
     *
     * @param clientProcessRequestBody The request body containing client data and software statement.
     * @return Response object containing either success or error information.
     */
    public static Response handleDCRClientCreation(ClientProcessRequestBody clientProcessRequestBody) {

        ClientProcessData data = clientProcessRequestBody.getData();

        JSONObject validationResponse = FDXDCRUtils.validateDCRPayload(data.getClientData(),
                data.getSoftwareStatement());

        if (validationResponse.getBoolean(FDXCommonConstants.DCR_IS_ERROR)) {
            log.error(String.format("DCR app creation validation failed. Error: %s",
                    validationResponse.getString("errorDescription").replaceAll("\r\n", " ")));
            FailedResponseClientProcess errorResponse = FDXDCRUtils.constructErrorResponse(validationResponse);
            return Response.ok().entity(errorResponse).build();
        }

        Map<String, Object> attributesToReturn = FDXDCRUtils.getDCRPayloadAttributes(data.getClientData());

        SuccessResponseClientProcess successResponseDCRValidation = FDXDCRUtils.constructSuccessResponse(
                clientProcessRequestBody.getRequestId(), attributesToReturn);
        log.debug("Returning success response for DCR app creation validation.response: " +
                successResponseDCRValidation);
        return Response.ok().entity(successResponseDCRValidation).build();
    }

}
