/*
 * Copyright (c) 2026, WSO2 LLC. (https://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package org.wso2.openbanking.fdx.demo.backend.services;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * AccountService class.
 */
@Path("/accountservice/")
public class AccountService {

    @GET
    @Path("/accounts")
    @Produces("application/json; charset=utf-8")
    public Response getAccounts(@HeaderParam("x-fapi-interaction-id") String xFapiInteractionId,
                                @HeaderParam("Account-Request-Information") String accountRequestInfo)
            throws ParseException {

        JSONObject accountRequestInformation = getRequest(accountRequestInfo);
        List<String> accountRequestIds = getAccountIds(accountRequestInformation);

        StringBuilder builder = new StringBuilder();
        for (String accountId : accountRequestIds) {
            String temp = "    {\n" +
                    "      \"accountCategory\": \"DEPOSIT_ACCOUNT\",\n" +
                    "      \"accountId\": \"" + accountId + "\",\n" +
                    "      \"accountType\": \"CHECKING\",\n" +
                    "      \"accountNumberDisplay\": \"XXXX4443\",\n" +
                    "      \"nickname\": \"My Checking Acc XXXX4443\",\n" +
                    "      \"status\": \"OPEN\",\n" +
                    "      \"balanceType\": \"ASSET\",\n" +
                    "      \"currency\": {\n" +
                    "        \"currencyCode\": \"USD\"\n" +
                    "      },\n" +
                    "      \"balanceAsOf\": \"2017-11-05T13:15:30.751Z\",\n" +
                    "      \"currentBalance\": 332.22,\n" +
                    "      \"openingDayBalance\": 100.0,\n" +
                    "      \"availableBalance\": 332.22\n" +
                    "    }\n";
            if (builder.length() > 0 && temp.length() > 0) {
                builder.append(",\n").append(temp);
            } else {
                builder.append(temp);
            }
        }

        String response = "{\n" +
                "  \"page\": {\n" +
                "    \"nextOffset\": \"2\",\n" +
                "    \"totalElements\": 3\n" +
                "  },\n" +
                "  \"links\": {\n" +
                "    \"next\": {\n" +
                "      \"href\": \"/accounts?offSet=2&limit=10\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"accounts\": [\n" +
                builder.toString() +  "\n" +
                "  ]\n" +
                "}";
        return Response.status(200).entity(response)
                    .header("x-fapi-interaction-id", xFapiInteractionId).build();
    }

    @GET
    @Path("/accounts/{AccountId}")
    @Produces("application/json; charset=utf-8")
    public Response getOneAccount(@PathParam("AccountId") String accountId,
                                  @HeaderParam("x-fapi-interaction-id") String xFapiInteractionId,
                                  @HeaderParam("Account-Request-Information") String accountRequestInfo)
            throws ParseException {

        JSONObject accountRequestInformation = getRequest(accountRequestInfo);
        JSONArray permissions = getPermissions(accountRequestInformation);
        // API v4 updated parameters.
        String accountType = "AccountType";
        String accountSubType = "AccountSubType";
        String accountSubTypeValue = "CurrentAccount";
        String sortCodeAccountNumber = "SortCodeAccountNumber";

        if (permissions.contains(OBExternalPermissions1Code.ReadAccountsDetail.name())) {
            String response = "{\n" +
                "  \"Data\": {\n" +
                "    \"Account\": [\n" +
                "      {\n" +
                "        \"AccountId\": \"" + accountId + "\",\n" +
                "        \"Status\": \"Enabled\",\n" +
                "        \"StatusUpdateDateTime\": \"2020-04-16T06:06:06+00:00\",\n" +
                "        \"Currency\": \"GBP\",\n" +
                "        \"" + accountType + "\": \"Personal\",\n" +
                "        \"" + accountSubType + "\": \"" + accountSubTypeValue + "\",\n" +
                "        \"Nickname\": \"Bills\",\n" +
                "        \"OpeningDate\": \"2020-01-16T06:06:06+00:00\",\n" +
                "        \"MaturityDate\": \"2025-04-16T06:06:06+00:00\",\n" +
                "        \"Account\": [{\n" +
                "          \"SchemeName\": \"" + sortCodeAccountNumber + "\",\n" +
                "          \"Identification\": \"" + accountId + "\",\n" +
                "          \"Name\": \"Mr Kevin\",\n" +
                "          \"SecondaryIdentification\": \"00021\"\n" +
                "        }]\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"Links\": {\n" +
                "    \"Self\": \"https://api.alphabank.com/open-banking/v3.0/accounts/" + accountId +
                "\"\n" +
                "  },\n" +
                "  \"Meta\": {\n" +
                "    \"TotalPages\": 1\n" +
                "  }\n" +
                "}";
            return Response.status(200).entity(response)
                    .header("x-fapi-interaction-id", xFapiInteractionId).build();
        } else if (permissions.contains(OBExternalPermissions1Code.ReadAccountsBasic.name())) {
            String response = "{\n" +
                "  \"Data\": {\n" +
                "    \"Account\": [\n" +
                "      {\n" +
                "        \"AccountId\": \"" + accountId + "\",\n" +
                "        \"Status\": \"Enabled\",\n" +
                "        \"StatusUpdateDateTime\": \"2020-04-16T06:06:06+00:00\",\n" +
                "        \"Currency\": \"GBP\",\n" +
                "        \"" + accountType + "\": \"Personal\",\n" +
                "        \"" + accountSubType + "\": \"" + accountSubTypeValue + "\",\n" +
                "        \"Nickname\": \"Bills\",\n" +
                "        \"OpeningDate\": \"2020-01-16T06:06:06+00:00\",\n" +
                "        \"MaturityDate\": \"2025-04-16T06:06:06+00:00\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"Links\": {\n" +
                "    \"Self\": \"https://api.alphabank.com/open-banking/v3.0/accounts/" + accountId +
                "\"\n" +
                "  },\n" +
                "  \"Meta\": {\n" +
                "    \"TotalPages\": 1\n" +
                "  }\n" +
                "}";
            return Response.status(200).entity(response)
                    .header("x-fapi-interaction-id", xFapiInteractionId).build();
        }

        return Response.status(403).build();
    }

    /**
     * OBExternalPermissions1Code enum.
     */
    public enum OBExternalPermissions1Code {
        ReadAccountsBasic("Allow access to read basic account information."),
        //"Permission to read basic account information."),
        ReadAccountsDetail("Allow access to additional elements in the account payload"),
        //("Access to additional elements in the account payload."),
        ReadBalances("Allow access to read all balance information."),
        //("Permission to read all balance information."),
        ReadBeneficiariesBasic("Allow access to read basic beneficiary details"),
        //("Permission to read basic beneficiary details."),
        ReadBeneficiariesDetail("Allow access to additional elements in the beneficiaries payload"),
        //("Access to additional elements in the beneficiaries payload."),
        ReadDirectDebits("Allow access to read all direct debit information"),
        //("Permission to read all direct debit information."),
        ReadStandingOrdersBasic("Allow access to read basic standing order information."),
        //("Permission to read standing order information."),
        ReadStandingOrdersDetail("Allow access to read detailed standing order information."),
        //("Access to additional elements in the standing-orders payload."),
        ReadTransactionsBasic("Allow access to read basic transactions information."),
        //("Permission to read basic transaction information."),
        ReadTransactionsDetail("Allow access to read detailed transactions information."),
        //("Access to additional elements in the transactions payload."),
        ReadTransactionsCredits("Allow access to read credit transactions information."),
        //("Access to only credit transactions."),
        ReadTransactionsDebits("Allow access to read debit transactions information."),
        //("Access to only debit transactions.")
        ReadProducts("Allow access to read product information."),
        //"Permission to read all product information."

        ReadStatementsBasic("Allow access to read basic statement details."),

        ReadStatementsDetail("Allow access to read detailed statement details."),

        ReadOffers("Allow access to read all offer information."),

        ReadParty("Allow access to read party information on the account owner."),

        ReadPartyPSU("Allow access to read party information on the PSU logged in."),

        ReadScheduledPaymentsBasic("Allow access to read basic scheduled payments details."),

        ReadScheduledPaymentsDetail("Allow access to read detailed scheduled payments details."),

        ReadPAN("Request to access PAN in the clear across the available endpoints.");

        private String oBExternalPermissions1Code;

        OBExternalPermissions1Code(String desc) {
            this.oBExternalPermissions1Code = desc;
        }

        public String getoBExternalPermissions1Code() {
            return this.oBExternalPermissions1Code;
        }
    }


    private static JSONObject getRequest(String json) throws ParseException {
        String[] splitString = json.split("\\.");
        String base64EncodedBody = splitString[1];
        String decodedString = new String(Base64.getDecoder()
                .decode(base64EncodedBody.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        JSONParser parser = new JSONParser(JSONParser.MODE_PERMISSIVE);
        JSONObject jsonObject = (JSONObject) parser.parse(decodedString);
        return jsonObject;
    }

    private static List<String> getAccountIds(JSONObject json) {
        List<String> accountIds = new ArrayList<>();
        JSONArray mappingResources = (JSONArray) json.get("consentMappingResources");
        for (int i = 0; i < mappingResources.size(); i++) {
            JSONObject resource = (JSONObject) mappingResources.get(i);
            accountIds.add((String) resource.get("account_id"));
        }
        return accountIds;
    }

    private static JSONArray getPermissions(JSONObject json) throws ParseException {
        JSONObject receipt = (JSONObject) json.get("receipt");
        JSONObject data = (JSONObject) receipt.get("Data");
        return (JSONArray) data.get("Permissions");

    }

}
