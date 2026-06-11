/*
 * Copyright (c) 2026, WSO2 LLC. (https://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.openbanking.fdx.demo.backend.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wso2.openbanking.fdx.demo.backend.BankException;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * FundsConfirmationService class.
 */
@Path("/fundsconfirmationservice/")
public class FundsConfirmationService {

    private static final Log log = LogFactory.getLog(FundsConfirmationService.class);
    private ObjectMapper mapper = new ObjectMapper();

    @POST
    @Path("/funds-confirmations")
    @Produces("application/json; charset=utf-8")
    public Response getAccountBalance(String requestString,
                                      @HeaderParam("x-fapi-interaction-id") String xFapiInteractionId)
            throws BankException {

        JSONObject request;
        try {
            JSONParser parser = new JSONParser(JSONParser.MODE_PERMISSIVE);
            request = (JSONObject) parser.parse(requestString);
        } catch (ParseException e) {
            log.error("Error in casting JSON body " + e.toString());
            throw new BankException("Error in casting JSON body " + e);
        }

        String consentId = ((JSONObject) request.get("Data")).getAsString("ConsentId");

        String response = "{\n" +
                "  \"Data\": {\n" +
                "    \"FundsConfirmationId\": \"836403\",\n" +
                "    \"ConsentId\": \"" + consentId + "\",\n" +
                "    \"CreationDateTime\": \"2017-06-02T00:00:00+00:00\",\n" +
                "    \"FundsAvailable\": true,\n" +
                "    \"Reference\": \"Purchase02\",\n" +
                "    \"InstructedAmount\": {\n" +
                "       \"Amount\": \"20.00\",\n" +
                "       \"Currency\": \"USD\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"Links\": {\n" +
                "    \"Self\": \"https://api.alphabank.com/open-banking/v3.0/funds-confirmations/836403\"\n" +
                "  },\n" +
                "  \"Meta\": {\n" +
                "  }\n" +
                "}";
        return Response.status(201).entity(response)
                .header("x-fapi-interaction-id", xFapiInteractionId).build();
    }
}
