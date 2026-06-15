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
import org.json.JSONArray;
import org.json.JSONObject;
import org.wso2.openbanking.fdx.extensions.model.Account;
import org.wso2.openbanking.fdx.extensions.model.Authorization;
import org.wso2.openbanking.fdx.extensions.model.AuthorizedResources;
import org.wso2.openbanking.fdx.extensions.model.AuthorizedResourcesAuthorizedDataInner;
import org.wso2.openbanking.fdx.extensions.model.DetailedConsentResourceDataWithAmendments;
import org.wso2.openbanking.fdx.extensions.model.PersistAuthorizedConsent;
import org.wso2.openbanking.fdx.extensions.model.PersistAuthorizedConsentRequestBody;
import org.wso2.openbanking.fdx.extensions.model.Resource;
import org.wso2.openbanking.fdx.extensions.model.SuccessResponsePersistAuthorizedConsentData;
import org.wso2.openbanking.fdx.extensions.model.UserGrantedData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Consent Persist Utilw
 */
public class FDXConsentPersistUtils {

    public static SuccessResponsePersistAuthorizedConsentData consentPersist(
            PersistAuthorizedConsentRequestBody requestBody) throws JsonProcessingException {

        PersistAuthorizedConsent authorizedConsent = requestBody.getData();
        UserGrantedData userGrantedData = authorizedConsent.getUserGrantedData();
        AuthorizedResources authorizedResources = userGrantedData.getAuthorizedResources();
        String status;
        String mappingStatus;
        if (authorizedResources.getApproval()) {
            status = FDXCommonConstants.FDX_CONSENT_AUTHORISED;
            mappingStatus = "active";
        } else {
            status = FDXCommonConstants.FDX_CONSENT_REJECTED;
            mappingStatus = "inactive";
        }

        JSONObject requestParameters = FDXCommonUtils.convertObjectToJson(userGrantedData.getRequestParameters());
        JSONArray authDetails = requestParameters.getJSONArray("authorization_details");
        SuccessResponsePersistAuthorizedConsentData data = new SuccessResponsePersistAuthorizedConsentData();

        JSONObject consentRequestObj = authDetails.getJSONObject(0)
                .getJSONObject(FDXCommonConstants.CONSENT_REQUEST);
        long sharingDuration = FDXCommonUtils.constructSharingDuration(consentRequestObj);

        // Adding resourceId to the resources in auth detail object
        JSONObject authDetail = authDetails.getJSONObject(0);
        modifyAuthDetailObject(authDetail);

        DetailedConsentResourceDataWithAmendments consentResource = new DetailedConsentResourceDataWithAmendments();
        consentResource.setType(FDXCommonConstants.FDX_TYPE);
        consentResource.setStatus(status);
        consentResource.setValidityTime(sharingDuration);
        consentResource.setReceipt(authDetail);
        consentResource.setFrequency(1);
        consentResource.setRecurringIndicator(true);

        Authorization authorization = new Authorization();
        authorization.setStatus("Authorised");
        authorization.setType("authorization");
        authorization.setUserId(userGrantedData.getUserId());

        List<AuthorizedResourcesAuthorizedDataInner> authorizedResourceData = authorizedResources.getAuthorizedData();
        List<Resource> resources = new ArrayList<>();

        for (AuthorizedResourcesAuthorizedDataInner inner : authorizedResourceData) {
            for (Account account : inner.getAccounts()) {
                String displayName = account.getDisplayName().split(" ").length > 1 ?
                        account.getDisplayName().split(" - ")[0] : "";
                Resource resource = new Resource();
                resource.setStatus(mappingStatus);
                resource.setAccountId(FDXCommonConstants.ACCOUNTS.get(displayName));
                resource.setPermission("primary");

                resources.add(resource);
            }

        }
        authorization.setResources(resources);
        consentResource.setAuthorizations(Collections.singletonList(authorization));

        data.setConsentResource(consentResource);
        return data;
    }

    /**
     * Adds a UUID resourceId to every resource inside each auth detail's consentRequest.
     */
    private static void modifyAuthDetailObject(JSONObject authDetail) {
        JSONObject consentRequest = authDetail.getJSONObject(FDXCommonConstants.CONSENT_REQUEST);
        JSONArray permissions = new JSONArray();
        if (consentRequest.has(FDXCommonConstants.RESOURCES)) {
            JSONArray resources = consentRequest.getJSONArray(FDXCommonConstants.RESOURCES);
            for (int j = 0; j < resources.length(); j++) {
                resources.getJSONObject(j).put("resourceId", UUID.randomUUID().toString());
                permissions = resources.getJSONObject(j).getJSONArray("dataClusters");
            }
        }
        authDetail.put("permissions", permissions);
        if (consentRequest.has("durationPeriod")) {
            long sharingDuration = FDXCommonUtils.constructSharingDuration(consentRequest);
            authDetail.put("expiryDate", FDXCommonUtils.getConsentExpiryDateTime(sharingDuration));
        }
    }
}
