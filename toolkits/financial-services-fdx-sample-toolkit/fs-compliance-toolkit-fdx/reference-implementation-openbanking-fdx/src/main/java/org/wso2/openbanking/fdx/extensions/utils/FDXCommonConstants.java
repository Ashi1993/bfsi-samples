/*
 * Copyright (c) 2026, WSO2 LLC. (https://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package org.wso2.openbanking.fdx.extensions.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This class contains common constants used in the FDX extension.
 */
public class FDXCommonConstants {
    public static final String STATUS = "status";
    public static final String TYPE = "type";
    public static final String RECEIPT = "receipt";
    public static final String CONSENT_DATA = "consentData";
    public static final String CONSUMER_DATA = "consumerData";
    public static final String DATA = "data";
    public static final String CONSENT_REQUEST = "consentRequest";
    public static final String RESPONSE_STATUS = "responseStatus";
    public static final String INVALID_REQUEST_MSG = "invalid_request";
    public static final String USER_ID_KEY_NAME = "userId";
    public static final String DISPLAY_NAME = "displayName";
    public static final String ACCOUNT_ID = "accountId";
    public static final String SELECTED = "selected";
    public static final String ONE_TIME_CONSENT = "ONE_TIME";
    public static final String ACCOUNT_TYPE = "type";
    public static final String ACCOUNT_ID_DISPLAYABLE = "accountIdToDisplay";
    public static final String AUTHORIZATION_DETAILS = "authorization_details";
    public static final String DURATION_PERIOD = "durationPeriod";
    public static final String DURATION_PERIOD_TITLE = "Duration Period";
    public static final String EXPIRATION_DATE_TIME = "Expiry Date";
    public static final String RESOURCES = "resources";
    public static final String RESOURCE_TYPE = "resourceType";
    public static final Map<String, Map<String, List<String>>> DATA_CLUSTERS;
    public static final Map<String, String> ACCOUNTS;
    public static final String REDIRECT_URL = "redirect_uri";
    public static final String DATA_REQUESTED = "data_requested";
    public static final String ACCOUNT_IDS = "accountIds";
    public static final String COMMON_AUTH_ID = "commonAuthId";
    public static final String IS_RECURRING = "isRecurring";
    public static final String AUTHORIZATION_RESOURCES_KEY = "authorizationResources";
    public static final String SERVER_ERROR_MSG = "server_error";
    public static final String DURATION_TYPE = "durationType";
    public static final String DURATION_TYPE_TITLE = "Duration Type";
    public static final String LOOKBACK_PERIOD = "lookbackPeriod";
    public static final String LOOKBACK_PERIOD_TITLE = "Lookback Period";
    public static final String FDX_TYPE = "accounts";
    public static final String FREQUENCY_SIMPLE = "frequency";
    public static final String COOKIES = "cookies";
    public static final String ATTRIBUTES = "commonAuthId";
    public static final String DATA_CLUSTERS_TITLE = "dataClusters";
    public static final String FDX_CONSENT_AUTHORISED = "Authorised";
    public static final String FDX_CONSENT_REJECTED = "Rejected";
    public static final String FDX_CONSENT_STATUS = "consentStatus";
    public static final String ERROR = "error";
    public static final Integer BAD_REQUEST = 400;
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String ERROR_DESCRIPTION = "errorDescription";
    public static final Integer INTERNAL_SERVER_ERROR = 500;
    public static final String PAYMENT_RESOURCE_TYPE = "PAYMENT";
    public static final String PAYMENT_INFO = "paymentInfo";
    public static final String PAYMENT_INFO_TITLE = "Payment Info";
    public static final String DURATION = "duration";
    public static final String NUMBER_OF_TIMES = "numberOfTimes";
    public static final String NUMBER_OF_TIMES_TITLE = "No of Months";
    public static final Map<String, String> PAYMENT_INFO_DISPLAY_NAMES;

    public static final String DCR_IS_ERROR = "isError";
    public static final String DCR_ERROR_CODE = "errorCode";
    public static final String DCR_ERROR_MSG = "errorMessage";
    public static final String DCR_ERROR_DESCRIPTION = "errorDescription";

    static {
        Map<String, Map<String, List<String>>> dataCluster = new HashMap<>();
        dataCluster.put("ACCOUNT_BASIC", createPermissionLanguage(
                "Account Information - Basic",
                "Account display name", "Masked account number", "Account type and Description"));
        dataCluster.put("ACCOUNT_DETAILED", createPermissionLanguage(
                "Account Information - Details",
                "Account display name", "Masked account number", "Account type and Description",
                "Account balances", "Credit limits", "Due dates and Interest rates"));
        dataCluster.put("ACCOUNT_PAYMENTS", createPermissionLanguage(
                "Account Information - Payments",
                "Full account and routing number", "SWIFT or IBAN numbers"));
        dataCluster.put("TRANSACTIONS", createPermissionLanguage(
                "Transactions",
                "Historical and current transactions", "Transaction types", "Amounts",
                "Dates and descriptions"));
        dataCluster.put("INVESTMENTS", createPermissionLanguage(
                "Investments",
                "Investment contributions", "Investment loans", "Pension data",
                "Vesting and account holding details"));
        dataCluster.put("PAYMENT_SUPPORT", createPermissionLanguage(
                "Payments IDs",
                "Full account number and bank routing number"));
        dataCluster.put("CUSTOMER_CONTACT", createPermissionLanguage(
                "Customer and Account Contact Information",
                "Your Name, Email, Address and Phone on file with this institution.",
                "Name, Email, Address and Phone of any other account holders."));
        dataCluster.put("CUSTOMER_PERSONAL", createPermissionLanguage(
                "Sensitive personal Information",
                "Your Name, Email, Address and Phone on file with this institution.",
                "Name, Email, Address and Phone of any other account holders.", "Your Date of Birth", "Tax ID",
                "SSN (Social Security Number)"));
        dataCluster.put("STATEMENTS", createPermissionLanguage(
                "Statements",
                "Periodic PDF statement showing personal information",
                "Account and transaction details. May contain PII such as name, address."));
        dataCluster.put("BILLS", createPermissionLanguage(
                "Bills",
                ""));
        dataCluster.put("TAX", createPermissionLanguage(
                "Tax",
                "All tax form entities (both JSON and PDF)"));
        dataCluster.put("REWARDS", createPermissionLanguage(
                "Rewards",
                ""));
        dataCluster.put("IMAGES", createPermissionLanguage(
                "Images",
                "Images of checks and receipts, which may include PII such as name, " +
                        "full account and routing number."));

        DATA_CLUSTERS = Collections.unmodifiableMap(dataCluster);
    }

    private static Map<String, List<String>> createPermissionLanguage(String uxName, String... uxDescription) {
        Map<String, List<String>> permissionLanguage = new LinkedHashMap<>();
        permissionLanguage.put(uxName, Arrays.asList(uxDescription));
        return permissionLanguage;
    }

    static {
        Map<String, String> paymentInfoDisplayNames = new LinkedHashMap<>();
        paymentInfoDisplayNames.put("fromAccountId", "Debtor Account Id");
        paymentInfoDisplayNames.put("toPayeeId", "Payee ID");
        paymentInfoDisplayNames.put("amount", "Amount");
        paymentInfoDisplayNames.put("merchantAccountId", "Merchant Account ID");
        paymentInfoDisplayNames.put("dueDate", "Due Date");
        paymentInfoDisplayNames.put("frequency", "Frequency");
        PAYMENT_INFO_DISPLAY_NAMES = Collections.unmodifiableMap(paymentInfoDisplayNames);
    }

    static {
        Map<String, String> accounts = new HashMap<>();
        accounts.put("Salary Saver Account", "30080012343456");
        accounts.put("Max Bonus Account", "30080098763459");
        accounts.put("multi_auth_account", "30080098971337");
        accounts.put("Extra_account", "650-000 N1232");
        ACCOUNTS = Collections.unmodifiableMap(accounts);
    }
}
