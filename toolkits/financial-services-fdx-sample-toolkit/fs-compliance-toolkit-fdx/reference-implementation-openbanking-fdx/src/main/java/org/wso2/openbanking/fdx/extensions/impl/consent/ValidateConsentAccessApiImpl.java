/*
 * Copyright (c) 2026, WSO2 LLC. (https://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

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
