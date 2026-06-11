/*
 * Copyright (c) 2026, WSO2 LLC. (https://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.openbanking.fdx.demo.backend;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * BankExceptionHandler class.
 */
public class BankExceptionHandler implements ExceptionMapper<BankException> {

    public Response toResponse(BankException exception) {
        return Response.status(Status.BAD_REQUEST).entity(exception.getMessage())
                .type(MediaType.APPLICATION_JSON).build();
    }
}
