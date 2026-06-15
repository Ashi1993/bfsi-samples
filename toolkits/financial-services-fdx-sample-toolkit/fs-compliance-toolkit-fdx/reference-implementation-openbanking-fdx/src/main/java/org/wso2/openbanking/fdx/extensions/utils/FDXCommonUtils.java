/*
 * Copyright (c) 2026, WSO2 LLC. (https://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package org.wso2.openbanking.fdx.extensions.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.wso2.openbanking.fdx.extensions.model.FailedResponse;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * FDX Common Utils
 */
public class FDXCommonUtils {

    /**
     * Converts a generic Java object to a {@link JSONObject}.
     *
     * @param object the Java object to be converted to JSON
     * @return a {@link JSONObject} representation of the given object
     * @throws JsonProcessingException if the object cannot be serialized to a JSON string
     */
    public static JSONObject convertObjectToJson(Object object) throws JsonProcessingException {
        // Convert Object to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(object);

        // Parse JSON string to JSONObject
        return new JSONObject(jsonString);
    }

    /**
     * Method to build a universal failed response
     *
     * @param errorCode
     * @param data
     * @return
     */
    public static JSONObject buildFailedResponse(Integer errorCode, JSONObject data) {
        FailedResponse failedResponse = new FailedResponse();
        failedResponse.setStatus(FailedResponse.StatusEnum.ERROR);
        failedResponse.setErrorCode(errorCode);
        failedResponse.setData(data);

        return new JSONObject(failedResponse);
    }

    /**
     * Method to construct the error response.
     *
     * @param errorMessage Error Message
     * @return
     */
    public static JSONObject getErrorResponse(Integer errorCode, String errorMessage, String errorDescription) {
        return buildFailedResponse(errorCode, getErrorDataObject(errorMessage, errorDescription));
    }

    /**
     * Method to construct the error data object.
     *
     * @param errorMessage     Error Message
     * @param errorDescription Error Description
     * @return
     */
    public static JSONObject getErrorDataObject(String errorMessage, String errorDescription) {

        JSONObject data = new JSONObject();
        data.put("errorMessage", errorMessage);
        data.put("errorDescription", errorDescription);

        return data;
    }

    /**
     * Calculates the consent expiry date and time based on the sharing duration.
     *
     * @param sharingDuration The duration in days for which the consent is valid.
     * @return The calculated expiry date and time.
     */
    public static String getConsentExpiryDateTime(long sharingDuration) {
        OffsetDateTime currentTime = OffsetDateTime.now(ZoneOffset.UTC);
        return currentTime.plusDays(sharingDuration).toString();
    }

    public static long constructSharingDuration(JSONObject consentRequestObj) {

        long sharingDuration = 0;
        if (consentRequestObj.has(FDXCommonConstants.DURATION_TYPE) &&
                !consentRequestObj.optString(FDXCommonConstants.DURATION_TYPE)
                        .equals(FDXCommonConstants.ONE_TIME_CONSENT)) {
            String sharingDurationStr = consentRequestObj.optString
                    (FDXCommonConstants.DURATION_PERIOD, "");
            sharingDuration = sharingDurationStr.isEmpty() ? 0 : Long.parseLong(sharingDurationStr);
        }
        return sharingDuration;
    }
}
