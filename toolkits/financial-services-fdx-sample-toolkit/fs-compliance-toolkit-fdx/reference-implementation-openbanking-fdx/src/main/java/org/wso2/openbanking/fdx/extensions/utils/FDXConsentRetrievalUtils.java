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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.wso2.openbanking.fdx.extensions.model.PopulateConsentAuthorizeScreenData;
import org.wso2.openbanking.fdx.extensions.model.PopulateConsentAuthorizeScreenRequestBody;
import org.wso2.openbanking.fdx.extensions.model.SuccessResponsePopulateConsentAuthorizeScreenDataConsentDataPermissionsInner;
import org.wso2.openbanking.fdx.extensions.model.SuccessResponsePopulateConsentAuthorizeScreenDataConsumerDataAccountsInner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Utility class for FDX consent retrieval.
 */
public class FDXConsentRetrievalUtils {

    private static final Log log = LogFactory.getLog(FDXConsentRetrievalUtils.class);

    public static JSONArray extractAuthorizationDetails(PopulateConsentAuthorizeScreenRequestBody
                                                                 populateConsentAuthorizeScreenRequestBody)
            throws JsonProcessingException {

        PopulateConsentAuthorizeScreenData data = populateConsentAuthorizeScreenRequestBody.getData();
        Object requestParamsObj = data.getRequestParameters();

        JSONObject requestParameters = FDXCommonUtils.convertObjectToJson(requestParamsObj);
        if (requestParameters.has(FDXCommonConstants.AUTHORIZATION_DETAILS)) {
            return requestParameters.getJSONArray(FDXCommonConstants.AUTHORIZATION_DETAILS);
        }
        return new JSONArray();
    }
    /**
     * Appends consent data to the response object.
     *
     */
    public static Map<String, List<String>> appendConsentDataToResponse(JSONArray authorizationDetails) {

        Map<String, List<String>> consentDataObject = new HashMap<>();
        for (int i = 0; i < authorizationDetails.length(); i++) {
            JSONObject authorizationDetail = authorizationDetails.getJSONObject(i);
            if (authorizationDetail.has(FDXCommonConstants.CONSENT_REQUEST)) {
                JSONObject consentRequestObj = authorizationDetail.getJSONObject(FDXCommonConstants.CONSENT_REQUEST);
                long sharingDuration = FDXCommonUtils.constructSharingDuration(consentRequestObj);

                consentDataObject.put(FDXCommonConstants.DURATION_TYPE_TITLE, Collections.singletonList(
                        consentRequestObj.getString(FDXCommonConstants.DURATION_TYPE)));
                consentDataObject.put(FDXCommonConstants.EXPIRATION_DATE_TIME,
                        Collections.singletonList(FDXCommonUtils.getConsentExpiryDateTime(sharingDuration)));
                consentDataObject.put(FDXCommonConstants.DURATION_PERIOD_TITLE,
                        Collections.singletonList(String.valueOf(sharingDuration)));
                consentDataObject.put(FDXCommonConstants.LOOKBACK_PERIOD_TITLE, Collections.singletonList(
                        String.valueOf(consentRequestObj.getInt(FDXCommonConstants.LOOKBACK_PERIOD))));

                if (consentRequestObj.has(FDXCommonConstants.RESOURCES)) {
                    JSONArray resources = consentRequestObj.getJSONArray(FDXCommonConstants.RESOURCES);
                    for (int j = 0; j < resources.length(); j++) {
                        JSONObject resource = resources.getJSONObject(j);
                        if (FDXCommonConstants.PAYMENT_RESOURCE_TYPE.equals(
                                resource.optString(FDXCommonConstants.RESOURCE_TYPE))
                                && resource.has(FDXCommonConstants.PAYMENT_INFO)) {
                            JSONObject paymentInfo = resource.getJSONObject(FDXCommonConstants.PAYMENT_INFO);
                            List<String> paymentInfoList = new ArrayList<>(
                                    FDXCommonConstants.PAYMENT_INFO_DISPLAY_NAMES.entrySet().stream()
                                            .filter(e -> paymentInfo.has(e.getKey()))
                                            .map(e -> e.getValue() + " - " + paymentInfo.get(e.getKey()))
                                            .collect(Collectors.toList()));
                            if (paymentInfo.has(FDXCommonConstants.DURATION)) {
                                JSONObject duration = paymentInfo.getJSONObject(FDXCommonConstants.DURATION);
                                if (duration.has(FDXCommonConstants.NUMBER_OF_TIMES)) {
                                    paymentInfoList.add(FDXCommonConstants.NUMBER_OF_TIMES_TITLE
                                            + " - " + duration.getInt(FDXCommonConstants.NUMBER_OF_TIMES));
                                }
                            }
                            consentDataObject.put(FDXCommonConstants.PAYMENT_INFO_TITLE, paymentInfoList);
                        }
                    }
                }
            }
        }

        return consentDataObject;
    }

    public static List<SuccessResponsePopulateConsentAuthorizeScreenDataConsentDataPermissionsInner> appendDataClusters(
            JSONArray authorizationDetails) {

        List<SuccessResponsePopulateConsentAuthorizeScreenDataConsentDataPermissionsInner> permissionsInnerList =
                new ArrayList<>();
        for (int i = 0; i < authorizationDetails.length(); i++) {
            JSONObject authorizationDetail = authorizationDetails.getJSONObject(i);
            SuccessResponsePopulateConsentAuthorizeScreenDataConsentDataPermissionsInner inner =
                    new SuccessResponsePopulateConsentAuthorizeScreenDataConsentDataPermissionsInner();
            if (authorizationDetail.has(FDXCommonConstants.CONSENT_REQUEST)) {
                JSONObject consentRequest =
                        authorizationDetail.getJSONObject(FDXCommonConstants.CONSENT_REQUEST);
                if (consentRequest.has(FDXCommonConstants.RESOURCES)) {
                    JSONArray resources = consentRequest.getJSONArray(FDXCommonConstants.RESOURCES);
                    for (Object resource : resources) {
                        JSONObject resourceOBj = new JSONObject(resource.toString());
                        JSONArray dataClusters = resourceOBj.getJSONArray(FDXCommonConstants.DATA_CLUSTERS_TITLE);
                        List<String> permissions = IntStream.range(0, dataClusters.length())
                                .mapToObj(dataClusters::getString)
                                .collect(Collectors.toList());
                        inner.setUid(String.valueOf(UUID.randomUUID()));
                        inner.setDisplayValues(permissions);
                        permissionsInnerList.add(inner);
                    }
                }
            }
        }

        return permissionsInnerList;
    }

    /**
     * Retrieves account data from the specified endpoint and populates the response.
     */
    public static List<SuccessResponsePopulateConsentAuthorizeScreenDataConsumerDataAccountsInner> retrieveAccounts() {
        List<SuccessResponsePopulateConsentAuthorizeScreenDataConsumerDataAccountsInner> accountsJSON =
                new ArrayList<>();

        for (Map.Entry<String, String> entry : FDXCommonConstants.ACCOUNTS.entrySet()) {
            SuccessResponsePopulateConsentAuthorizeScreenDataConsumerDataAccountsInner inner =
                    new SuccessResponsePopulateConsentAuthorizeScreenDataConsumerDataAccountsInner();
            inner.setSelected(false);
            inner.setDisplayName(getAccountDisplayName(entry.getKey(), entry.getValue()));

            accountsJSON.add(inner);
        }

        return accountsJSON;

    }

    private static String getAccountDisplayName(String displayKey, String accountId) {
        return displayKey + " - " + getMaskedAccountNumber(accountId);
    }

    /**
     * Masks the account number based on its length.
     *
     * @param accountId The account ID to be masked.
     * @return The masked account number.
     */
    public static String getMaskedAccountNumber(String accountId) {
        int accountIdLength = accountId.length();
        if (accountIdLength > 1) {
            if (accountIdLength < 4) {
                // If the length is less than 4, mask all but the last character
                String maskedPart = StringUtils.repeat('*', accountIdLength - 1);
                String visiblePart = StringUtils.right(accountId, 1);
                return maskedPart + visiblePart;
            } else if (accountIdLength == 4) {
                // If the length is exactly 4, mask all but the last two characters
                return "**" + StringUtils.right(accountId, 2);
            } else {
                // If the length is greater than 4, mask all but the last 4 characters
                String maskedPart = StringUtils.repeat('*', accountIdLength - 4);
                String visiblePart = StringUtils.right(accountId, 4);
                return maskedPart + visiblePart;
            }
        }
        return accountId;
    }

    /**
     * Retrieves data cluster data from the consent retrieval response.
     *
     * @param consentRetrievalResponse The consent retrieval response object.
     */
    public static void retrieveDataClusterData(JSONObject consentRetrievalResponse) {

        for (Object item : consentRetrievalResponse.getJSONArray(FDXCommonConstants.CONSENT_DATA)) {
            Map<String, Object> consentDataItem = ((JSONObject) item).toMap();
            Map<String, List<String>> dataClusterMapping = new HashMap<>();

            List<Map<String, Object>> authDetailArray =
                    (List<Map<String, Object>>) consentDataItem.get(FDXCommonConstants.AUTHORIZATION_DETAILS);

            for (Map<String, Object> authorizationDetail : authDetailArray) {
                if (authorizationDetail.containsKey(FDXCommonConstants.RESOURCES)) {
                    Map<String, List<String>> resources =
                            (Map<String, List<String>>) authorizationDetail.get(FDXCommonConstants.RESOURCES);

                    for (List<String> dataClusterList : resources.values()) {
                        for (String dataCluster : dataClusterList) {
                            Map<String, List<String>> permissionData =
                                    FDXCommonConstants.DATA_CLUSTERS.get(dataCluster);

                            if (permissionData != null && !permissionData.isEmpty()) {
                                Map.Entry<String, List<String>> firstEntry =
                                        permissionData.entrySet().iterator().next();
                                dataClusterMapping.put(firstEntry.getKey(), firstEntry.getValue());
                            }
                        }
                    }
                }

            }

            // Add the final dataClusterMapping back into the consentDataItem
            ((JSONObject) item).put(FDXCommonConstants.DATA_REQUESTED, dataClusterMapping);
        }
    }
}
