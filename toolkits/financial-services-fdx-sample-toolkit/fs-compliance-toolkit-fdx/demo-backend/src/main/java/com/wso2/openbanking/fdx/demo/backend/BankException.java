/*
 * Copyright (c) 2026, WSO2 LLC. (https://www.wso2.com). All Rights Reserved.
 *
 * This software is the property of WSO2 LLC. and its suppliers, if any.
 * Dissemination of any information or reproduction of any material contained
 * herein in any form is strictly forbidden, unless permitted by WSO2 expressly.
 * You may not alter or remove any copyright or other notice from copies of this content.
 */

package com.wso2.openbanking.fdx.demo.backend;

/**
 * BankException class.
 */
public class BankException extends Exception {

    public BankException(String msg) {
        super(msg);
    }

    public BankException(String msg, Throwable e) {
        super(msg, e);
    }

    public BankException(Throwable throwable) {
        super(throwable);
    }
}
