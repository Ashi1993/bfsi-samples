// Copyright (c) 2023, WSO2 LLC. (http://www.wso2.com). All Rights Reserved.

// This software is the property of WSO2 LLC. and its suppliers, if any.
// Dissemination of any information or reproduction of any material contained
// herein is strictly forbidden, unless permitted by WSO2 in accordance with
// the WSO2 Software License available at: https://wso2.com/licenses/eula/3.2
// For specific language governing the permissions and limitations under
// this license, please see the license as well as any agreement you’ve
// entered into with WSO2 governing the purchase of this software and any
// associated services.

import wso2bfsi/wso2.bfsi.demo.backend.model;

isolated function extractCreditorAccount(json payload, string path) returns model:CreditorAccount|error {

    if (path.includes("domestic-payment")) {
        model:DomesticPaymentInitiation initiation = check extractDomesticPaymentInitiation(payload);
        model:CreditorAccount creditorAcc = check initiation.CreditorAccount.cloneWithType(model:CreditorAccount);
        return creditorAcc;

    } else if (path.includes("domestic-scheduled-payment")) {
        model:DomesticScheduledPaymentInitiation initiation = check extractDomesticScheduledPaymentInitiation(payload);
        model:CreditorAccount creditorAcc = check initiation.CreditorAccount.cloneWithType(model:CreditorAccount);
        return creditorAcc;

    } else if (path.includes("domestic-standing-order")) {
        model:DomesticStandingOrderInitiation initiation = check extractDomesticStandingOrderInitiation(payload);
        model:CreditorAccount creditorAcc = check initiation.CreditorAccount.cloneWithType(model:CreditorAccount);
        return creditorAcc;

    } else if (path.includes("international-payment")) {
        model:InternationalPaymentInitiation initiation = check extractInternationalPaymentInitiation(payload);
        model:CreditorAccount creditorAcc = check initiation.CreditorAccount.cloneWithType(model:CreditorAccount);
        return creditorAcc;

    } else if (path.includes("international-scheduled-payment")) {
        model:InternationalScheduledPaymentInitiation initiation = check extractInternationalScheduledPaymentInitiation(payload);
        model:CreditorAccount creditorAcc = check initiation.CreditorAccount.cloneWithType(model:CreditorAccount);
        return creditorAcc;

    } else if (path.includes("international-standing-order")) {
        model:InternationalStandingOrderInitiation initiation = check extractInternationalStandingOrderInitiation(payload);
        model:CreditorAccount creditorAcc = check initiation.CreditorAccount.cloneWithType(model:CreditorAccount);
        return creditorAcc;

    } else {
        return error("Invalid path");
    }
}

isolated function extractDebtorAccount(json payload, string path) returns model:DebtorAccount|error|() {

    if (path.includes("domestic-payment")) {
        model:DomesticPaymentInitiation initiation = check extractDomesticPaymentInitiation(payload);
        if (initiation.DebtorAccount is ()) {
            return ();
        }
        model:DebtorAccount debtorAccount = check initiation.DebtorAccount.cloneWithType(model:DebtorAccount);
        return debtorAccount;

    } else if (path.includes("domestic-scheduled-payment")) {
        model:DomesticScheduledPaymentInitiation initiation = check extractDomesticScheduledPaymentInitiation(payload);
        if (initiation.DebtorAccount is ()) {
            return ();
        }
        model:DebtorAccount debtorAccount = check initiation.DebtorAccount.cloneWithType(model:DebtorAccount);
        return debtorAccount;

    } else if (path.includes("domestic-standing-order")) {
        model:DomesticStandingOrderInitiation initiation = check extractDomesticStandingOrderInitiation(payload);
        if (initiation.DebtorAccount is ()) {
            return ();
        }
        model:DebtorAccount debtorAccount = check initiation.DebtorAccount.cloneWithType(model:DebtorAccount);
        return debtorAccount;

    } else if (path.includes("international-payment")) {
        model:InternationalPaymentInitiation initiation = check extractInternationalPaymentInitiation(payload);
        if (initiation.DebtorAccount is ()) {
            return ();
        }
        model:DebtorAccount debtorAccount = check initiation.DebtorAccount.cloneWithType(model:DebtorAccount);
        return debtorAccount;

    } else if (path.includes("international-scheduled-payment")) {
        model:InternationalScheduledPaymentInitiation initiation = check extractInternationalScheduledPaymentInitiation(payload);
        if (initiation.DebtorAccount is ()) {
            return ();
        }
        model:DebtorAccount debtorAccount = check initiation.DebtorAccount.cloneWithType(model:DebtorAccount);
        return debtorAccount;

    } else if (path.includes("international-standing-order")) {
        model:InternationalStandingOrderInitiation initiation = check extractInternationalStandingOrderInitiation(payload);
        if (initiation.DebtorAccount is ()) {
            return ();
        }
        model:DebtorAccount debtorAccount = check initiation.DebtorAccount.cloneWithType(model:DebtorAccount);
        return debtorAccount;

    } else if (path.includes("file-payment")) {
        model:FilePaymentInitiation initiation = check extractFilePaymentInitiation(payload);
        if (initiation.DebtorAccount is ()) {
            return ();
        }
        model:DebtorAccount debtorAccount = check initiation.DebtorAccount.cloneWithType(model:DebtorAccount);
        return debtorAccount;
        
    } else {
        return error("Invalid path");
    }
}

isolated function extractDomesticPaymentInitiation(json payload) returns model:DomesticPaymentInitiation|error {
    model:DomesticPaymentRequest request = check payload.cloneWithType(model:DomesticPaymentRequest);
    model:DomesticPaymentRequestData data = check request.Data.cloneWithType(model:DomesticPaymentRequestData);
    model:DomesticPaymentInitiation initiation = check data.Initiation.cloneWithType(model:DomesticPaymentInitiation);
    
    return initiation;
}

isolated function extractDomesticScheduledPaymentInitiation(json payload) returns model:DomesticScheduledPaymentInitiation|error {
    model:DomesticScheduledPaymentRequest request = check payload.cloneWithType(model:DomesticScheduledPaymentRequest);
    model:DomesticScheduledPaymentData data = check request.Data.cloneWithType(model:DomesticScheduledPaymentData);
    model:DomesticScheduledPaymentInitiation initiation = check data.Initiation.cloneWithType(model:DomesticScheduledPaymentInitiation);
        
    return initiation;
}

isolated function extractDomesticStandingOrderInitiation(json payload) returns model:DomesticStandingOrderInitiation|error {
    model:DomesticStandingOrderRequest request = check payload.cloneWithType(model:DomesticStandingOrderRequest);
    model:DomesticStandingOrderData data = check request.Data.cloneWithType(model:DomesticStandingOrderData);
    model:DomesticStandingOrderInitiation initiation = check data.Initiation.cloneWithType(model:DomesticStandingOrderInitiation);
        
    return initiation;
}

isolated function extractInternationalPaymentInitiation(json payload) returns model:InternationalPaymentInitiation|error {
    model:InternationalPaymentRequest request = check payload.cloneWithType(model:InternationalPaymentRequest);
    model:InternationalPaymentData data = check request.Data.cloneWithType(model:InternationalPaymentData);
    model:InternationalPaymentInitiation initiation = check data.Initiation.cloneWithType(model:InternationalPaymentInitiation);
        
    return initiation;
}

isolated function extractInternationalScheduledPaymentInitiation(json payload) returns model:InternationalScheduledPaymentInitiation|error {
    model:InternationalScheduledPaymentRequest request = check payload.cloneWithType(model:InternationalScheduledPaymentRequest);
    model:InternationalScheduledPaymentData data = check request.Data.cloneWithType(model:InternationalScheduledPaymentData);
    model:InternationalScheduledPaymentInitiation initiation = check data.Initiation.cloneWithType(model:InternationalScheduledPaymentInitiation);
        
    return initiation;
}

isolated function extractInternationalStandingOrderInitiation(json payload) returns model:InternationalStandingOrderInitiation|error {
    model:InternationalStandingOrderRequest request = check payload.cloneWithType(model:InternationalStandingOrderRequest);
    model:InternationalStandingOrderResponseData data = check request.Data.cloneWithType(model:InternationalStandingOrderResponseData);
    model:InternationalStandingOrderInitiation initiation = check data.Initiation.cloneWithType(model:InternationalStandingOrderInitiation);
           
    return initiation;
}

isolated function extractFilePaymentInitiation(json payload) returns model:FilePaymentInitiation|error {
    model:FilePaymentRequest request = check payload.cloneWithType(model:FilePaymentRequest);
    model:FilePaymentData data = check request.Data.cloneWithType(model:FilePaymentData);
    model:FilePaymentInitiation initiation = check data.Initiation.cloneWithType(model:FilePaymentInitiation);
    
    return initiation;
}
