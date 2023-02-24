// Copyright (c) 2023, WSO2 LLC. (http://www.wso2.com). All Rights Reserved.

// This software is the property of WSO2 LLC. and its suppliers, if any.
// Dissemination of any information or reproduction of any material contained
// herein is strictly forbidden, unless permitted by WSO2 in accordance with
// the WSO2 Software License available at: https://wso2.com/licenses/eula/3.2
// For specific language governing the permissions and limitations under
// this license, please see the license as well as any agreement you’ve
// entered into with WSO2 governing the purchase of this software and any
// associated services.

import ballerina/log;

import wso2.bfsi.demo.backend.model;
import wso2.bfsi.demo.backend.repository;
import wso2.bfsi.demo.backend.util;

# Represents payments API service methods.
public class PaymentService {

    private model:Error EMPTY_REQUEST_BODY = {ErrorCode: util:CODE_INVALID_REQUEST_BODY, Message: util:EMPTY_REQUEST_BODY};
    private model:Error EMPTY_PAYMENT_ID = {ErrorCode: util:CODE_EMPTY_PAYMENT_ID, Message: util:EMPTY_PAYMENT_ID};

    private repository:PaymentsRepository repository = new();

    # Store a domestic payment object.
    # 
    # + request - Domestic payment payload
    # + return - Domestic payment with ID or error
    public isolated function createDomesticPayment(model:DomesticPaymentRequest request) 
                                                    returns model:DomesticPaymentResponse|model:Error {
        if request.length() == 0 {
            
            log:printDebug(util:EMPTY_REQUEST_BODY);
            return self.EMPTY_REQUEST_BODY;
        } else {
            
            final string domesticPaymentId = self.repository.insertDomesticPaymentsData(
                    request.Data.ConsentId, 
                    request.Data.Initiation, 
                    request.Risk
                );
            
            model:DomesticPaymentResponseData data = {
                DomesticPaymentId: domesticPaymentId, 
                ConsentId: request?.Data?.ConsentId, 
                Status: "AcceptedSettlementInProcess", 
                Initiation: request?.Data?.Initiation
            };
            
            model:DomesticPaymentResponse response = {
                Data: data,
                Links: self.getLinks("/domestic-payments/", domesticPaymentId),
                Meta: {
                    TotalPages: 1
                }
            };
            
            return response;
        }
    }

    # Get a domestic payment by Id.
    # 
    # + domesticPaymentId - Domestic payment Id
    # + return - Domestic payment or error
    public isolated function getDomesticPayments(string domesticPaymentId) returns model:DomesticPaymentResponse|model:Error {
        
        if domesticPaymentId == "" {

            log:printDebug(util:EMPTY_PAYMENT_ID);
            return self.EMPTY_PAYMENT_ID;
        } else {

            do {
	            final json initiation =  check util:getDomesticPaymentInitiation().fromJsonString();

                model:DomesticPaymentResponseData data = {
                    DomesticPaymentId: domesticPaymentId, 
                    ConsentId: util:getRandomId(), 
                    Status: "AcceptedSettlementInProcess", 
                    Initiation: check initiation.fromJsonWithType(model:DomesticPaymentInitiation)
                };

                model:DomesticPaymentResponse response = {
                    Data: data,
                    Links: self.getLinks("/domestic-payments/", domesticPaymentId),
                    Meta: {
                        TotalPages: 1
                    }
                };

                return response;
            } on fail var e {
            	return self.handleError(e);
            }
        }
    }

    # Store a domestic scheduled payment object.
    # 
    # + request - Domestic scheduled payment payload
    # + return - Domestic scheduled payment with ID or error
    public isolated function createDomesticScheduledPayment(model:DomesticScheduledPaymentRequest request) 
                                                    returns model:DomesticScheduledPaymentResponse|model:Error {
        if request.length() == 0 {
            
            log:printDebug(util:EMPTY_REQUEST_BODY);
            return self.EMPTY_REQUEST_BODY;
        } else {

            final string domesticScheduledPaymentId = self.repository.insertDomesticScheduledPaymentsData(
                request.Data.ConsentId, 
                request.Data.Initiation, 
                request.Risk
            );

            model:DomesticScheduledPaymentsResponseData data = {
                DomesticScheduledPaymentId: domesticScheduledPaymentId, 
                ConsentId: request.Data.ConsentId, 
                Status: "AcceptedSettlementInProcess", 
                Initiation: request.Data.Initiation
            };

            model:DomesticScheduledPaymentResponse response = {
                Data: data,
                Links: self.getLinks("/domestic-scheduled-payments/", domesticScheduledPaymentId),
                Meta: {
                    TotalPages: 1
                }
            };
            
            return response;
        }
    }

    # Get a domestic scheduled payment by Id.
    # 
    # + domesticScheduledPaymentId - Domestic scheduled payment Id
    # + return - Domestic scheduled payment or error
    public isolated function getDomesticScheduledPayments(string domesticScheduledPaymentId) 
                                                        returns model:DomesticScheduledPaymentResponse|model:Error {
        if domesticScheduledPaymentId == "" {
            
            log:printDebug(util:EMPTY_PAYMENT_ID);
            return self.EMPTY_PAYMENT_ID;
        } else {
            
            do {
                final json initiation =  check util:getDomesticScheduledPaymentInitiation().fromJsonString();

                model:DomesticScheduledPaymentsResponseData data = {
                    DomesticScheduledPaymentId: domesticScheduledPaymentId, 
                    ConsentId: util:getRandomId(), 
                    Status: "AcceptedSettlementInProcess", 
                    Initiation: check initiation.fromJsonWithType(model:DomesticScheduledPaymentInitiation)
                };

                model:DomesticScheduledPaymentResponse response = {
                    Data: data,
                    Links: self.getLinks("/domestic-scheduled-payments/", domesticScheduledPaymentId),
                    Meta: {
                        TotalPages: 1
                    }
                };

                return response;
            } on fail var e {
            	return self.handleError(e);
            }
        }
    }

    # Store a domestic standing order payment object.
    # 
    # + request - Domestic standing order payment payload
    # + return - Domestic standing order payment with ID or error
    public isolated function createDomesticStandingOrderPayment(model:DomesticStandingOrderRequest request) 
                                                    returns model:DomesticStandingOrderResponse|model:Error {
        if request.length() == 0 {

            log:printDebug(util:EMPTY_REQUEST_BODY);
            return self.EMPTY_REQUEST_BODY;
        } else {
            final string domesticStandingOrderId = self.repository.insertDomesticStandingOrderPaymentsData(
                request.Data.ConsentId, 
                request.Data.Initiation, 
                request.Risk
            );

            model:DomesticStandingOrderResponseData data = {
                DomesticStandingOrderId: domesticStandingOrderId, 
                ConsentId: request.Data.ConsentId, 
                Status: "AcceptedSettlementInProcess", 
                Initiation: request.Data.Initiation
            };

            model:DomesticStandingOrderResponse response = {
                Data: data,
                Links: self.getLinks("/domestic-standing-orders/", domesticStandingOrderId),
                Meta: {
                    TotalPages: 1
                }
            };

            return response;
        }
    }

    # Get a domestic standing order payment by Id.
    # 
    # + domesticStandingOrderId - Domestic standing order payment Id
    # + return - Domestic standing order payment or error
    public isolated function getDomesticStandingOrderPayments(string domesticStandingOrderId) 
                                                        returns model:DomesticStandingOrderResponse|model:Error {
        if domesticStandingOrderId == "" {
            
            log:printDebug(util:EMPTY_PAYMENT_ID);
            return self.EMPTY_PAYMENT_ID;
        } else {        
            do {
                final json initiation =  check util:getDomesticStandingOrderPaymentInitiation().fromJsonString();

                model:DomesticStandingOrderResponseData data = {
                    DomesticStandingOrderId: domesticStandingOrderId, 
                    ConsentId: util:getRandomId(), 
                    Status: "AcceptedSettlementInProcess", 
                    Initiation: check initiation.fromJsonWithType(model:DomesticStandingOrderInitiation)
                };

                model:DomesticStandingOrderResponse response = {
                    Data: data,
                    Links: self.getLinks("/domestic-standing-orders/", domesticStandingOrderId),
                    Meta: {
                        TotalPages: 1
                    }
                };
                return response;
            } on fail var e {
            	return self.handleError(e);
            }
        }
    }

    # Store a file payment object.
    # 
    # + request - File payment payload
    # + return - File payment with ID or error
    public isolated function createFilePayment(model:FilePaymentRequest request) 
                                                    returns model:FilePaymentResponse|model:Error {
        
        if request.length() == 0 {

            log:printDebug(util:EMPTY_REQUEST_BODY);
            return self.EMPTY_REQUEST_BODY;
        } else {
             final string filePaymentId = self.repository.insertFilePaymentsData(
                request.Data.ConsentId, 
                request.Data.Initiation
            );

            model:FilePaymentsResponseData data = {
                FilePaymentId: filePaymentId, 
                ConsentId: request.Data.ConsentId, 
                Status: "AcceptedSettlementInProcess", 
                Initiation: request.Data.Initiation
            };

            model:FilePaymentResponse response = {
                Data: data,
                Links: self.getLinks("/file-payments/", filePaymentId),
                Meta: {
                    TotalPages: 1
                }
            };
            return response;
        }
    }

    # Get a file payment by Id.
    # 
    # + filePaymentId - File payment Id
    # + return - File payment or error
    public isolated function getFilePayments(string filePaymentId) returns model:FilePaymentResponse|model:Error {
        
        if filePaymentId == "" {

            log:printDebug(util:EMPTY_PAYMENT_ID);
            return self.EMPTY_PAYMENT_ID;
        } else {
            do {
                json initiation =  check util:getFilePaymentInitiation().fromJsonString();

                model:FilePaymentsResponseData data = {
                    FilePaymentId: filePaymentId, 
                    ConsentId: util:getRandomId(), 
                    Status: "AcceptedSettlementInProcess", 
                    Initiation: check initiation.fromJsonWithType(model:FilePaymentInitiation)
                };

                model:FilePaymentResponse response = {
                    Data: data,
                    Links: self.getLinks("/file-payments/", filePaymentId),
                    Meta: {
                        TotalPages: 1
                    }
                };

                return response;
            } on fail var e {
            	return self.handleError(e);
            }
        }
    }

    # Store an international payment object.
    # 
    # + request - International payment payload
    # + return - International payment with ID or error
    public isolated function createInternationalPayment(model:InternationalPaymentRequest request) 
                                                    returns model:InternationalPaymentResponse|model:Error {
        if request.length() == 0 {
            
            log:printDebug(util:EMPTY_REQUEST_BODY);
            return self.EMPTY_REQUEST_BODY;
        } else {
            do {
                string internationalPaymentId = self.repository.insertInternationalPaymentsData(
                    request.Data.ConsentId, 
                    request.Data.Initiation, 
                    request.Risk
                );
                model:InternationalPaymentResponseData data = {
                    InternationalPaymentId: internationalPaymentId, 
                    ConsentId: request.Data.ConsentId, 
                    Status: "AcceptedSettlementInProcess", 
                    Initiation: request.Data.Initiation
                };
                model:InternationalPaymentResponse response = {
                    Data: data,
                    Links: self.getLinks("/international-payments/", internationalPaymentId),
                    Meta: {
                        TotalPages: 1
                    }
                };
                return response;
            } on fail var e {
            	return self.handleError(e);
            }
        }
    }

    # Get an international payment by Id.
    # 
    # + internationalPaymentId - International payment Id
    # + return - International payment or error
    public isolated function getInternationalPayments(string internationalPaymentId) 
                                                    returns model:InternationalPaymentResponse|model:Error {
        if internationalPaymentId == "" {
            
            log:printDebug(util:EMPTY_PAYMENT_ID);
            return self.EMPTY_PAYMENT_ID;
        } else {
            do { 
                final json initiation =  check util:getInternatioanlPaymentInitiation().fromJsonString();

                model:InternationalPaymentResponseData data = {
                    InternationalPaymentId: internationalPaymentId, 
                    ConsentId: util:getRandomId(), 
                    Status: "AcceptedSettlementInProcess", 
                    Initiation: check initiation.fromJsonWithType(model:InternationalPaymentInitiation)
                };

                model:InternationalPaymentResponse response = {
                    Data: data,
                    Links: self.getLinks("/international-payments/", internationalPaymentId),
                    Meta: {
                        TotalPages: 1
                    }
                };
                
                return response;
            } on fail var e {
            	return self.handleError(e);
            }
        }
    }

    # Store an international scheduled payment object.
    # 
    # + request - International scheduled payment payload
    # + return - International scheduled payment with ID or error
    public isolated function createInternationalScheduledPayment(model:InternationalScheduledPaymentRequest request) 
                                                    returns model:InternationalScheduledPaymentResponse|model:Error {
        if request.length() == 0 {
            
            log:printDebug(util:EMPTY_REQUEST_BODY);
            return self.EMPTY_REQUEST_BODY;
        } else {
            final string internationalScheduledPaymentId = self.repository.insertInternationalScheduledPaymentsData(
                request.Data.ConsentId, 
                request.Data.Initiation, 
                request.Risk
            );

            model:InternationalScheduledResponseData data = {
                InternationalScheduledPaymentId: internationalScheduledPaymentId, 
                ConsentId: request.Data.ConsentId, 
                Status: "AcceptedSettlementInProcess", 
                Initiation: request.Data.Initiation
            };

            model:InternationalScheduledPaymentResponse response = {
                Data: data,
                Links: self.getLinks("/international-schedules-payments/", internationalScheduledPaymentId),
                Meta: {
                    TotalPages: 1
                }
            };

            return response;
        }
    }

    # Get an international scheduled payment by Id.
    # 
    # + internationalScheduledPaymentId - International scheduled payment Id
    # + return - International scheduled payment or error
    public isolated function getInternationalScheduledPayments(string internationalScheduledPaymentId) 
                                                        returns model:InternationalScheduledPaymentResponse|model:Error {
        if internationalScheduledPaymentId == "" {
            
            log:printDebug(util:EMPTY_PAYMENT_ID);
            return self.EMPTY_PAYMENT_ID;
        } else {
            do {
                final json initiation =  check util:getInternatioanlScheduledPaymentInitiation().fromJsonString();

                model:InternationalScheduledResponseData data = {
                    InternationalScheduledPaymentId: internationalScheduledPaymentId, 
                    ConsentId: util:getRandomId(), 
                    Status: "AcceptedSettlementInProcess", 
                    Initiation: check initiation.fromJsonWithType(model:InternationalScheduledPaymentInitiation)
                };

                model:InternationalScheduledPaymentResponse response = {
                    Data: data,
                    Links: self.getLinks("/international-schedules-payments/", internationalScheduledPaymentId),
                    Meta: {
                        TotalPages: 1
                    }
                };

                return response;
            } on fail var e {
            	return self.handleError(e);
            }
        }
    }

    # Store an international standing order payment object.
    # 
    # + request - International standing order payment payload
    # + return - International standing order payment with ID or error
    public isolated function createInternationalStandingOrderPayment(model:InternationalStandingOrderRequest request) 
                                                    returns model:InternationalStandingOrderResponse|model:Error {
        if request.length() == 0 {
            
            log:printDebug(util:EMPTY_REQUEST_BODY);
            return self.EMPTY_REQUEST_BODY;
        } else {
            final string internationalStandingorderId = self.repository.insertInternationalStandingOrderPaymentsData(
                request.Data.ConsentId, 
                request.Data.Initiation, 
                request.Risk
            );

            model:InternationalStandingOrderResponseData data = {
                InternationalStandingOrderId: internationalStandingorderId, 
                ConsentId: request.Data.ConsentId, 
                Status: "AcceptedSettlementInProcess", 
                Initiation: request.Data.Initiation
            };
            model:InternationalStandingOrderResponse response = {
                Data: data,
                Links: self.getLinks("/international-standing-orders/", internationalStandingorderId),
                Meta: {
                    TotalPages: 1
                }
            };
            return response;
        }
    }

    # Get an international standing order payment by Id.
    # 
    # + internationalStandingOrderId - International standing order payment Id
    # + return - International standing order payment or error
    public isolated function getInternationalStandingOrderPayments(string internationalStandingOrderId) 
                                                        returns model:InternationalStandingOrderResponse|model:Error {
        if internationalStandingOrderId == "" {
            
            log:printDebug(util:EMPTY_PAYMENT_ID);
            return self.EMPTY_PAYMENT_ID;
        } else {
            do {
                final json initiation =  check util:getInternatioanlStandingOrderPaymentInitiation().fromJsonString();
                
                model:InternationalStandingOrderResponseData data = {
                    InternationalStandingOrderId: internationalStandingOrderId, 
                    ConsentId: util:getRandomId(), 
                    Status: "AcceptedSettlementInProcess", 
                    Initiation: check initiation.fromJsonWithType(model:InternationalStandingOrderInitiation)
                };
                
                model:InternationalStandingOrderResponse response = {
                    Data: data,
                    Links: self.getLinks("/international-standing-orders/", internationalStandingOrderId),
                    Meta: {
                        TotalPages: 1
                    }
                };
                return response;
            } on fail var e {
            	return self.handleError(e);
            }
        }
    }

    private isolated function getPaymentsDetails(string path, string domesticPaymentId) returns model:PaymentDetailsResponse|model:Error {
        
        if domesticPaymentId == "" {

            log:printDebug(util:EMPTY_PAYMENT_ID);
            return self.EMPTY_PAYMENT_ID;
        } else {

            do {
	            model:PaymentDetailsResponseData data = self.getPaymentDetailsData(domesticPaymentId);

                model:PaymentDetailsResponse response = {
                    Data: data,
                    Links: self.getLinks(path, domesticPaymentId),
                    Meta: {
                        TotalPages: 1
                    }
                };

                return response;
            } on fail var e {
            	return self.handleError(e);
            }
        }
    }

    private isolated function getLinks(string path, string id) returns model:Links {
        return { Self: "https://api.alphabank.com/open-banking/v3.1/pisp" + path + id };
    }

    private isolated function getPaymentDetailsData(string id) returns model:PaymentDetailsResponseData {
        model:PaymentDetailsResponseDataStatusDetail statusDetail = {
            LocalInstrument:"UK.OBIE.BACS",
            Status: "Accepted",
            StatusReason: "PendingFailingSettlement",
            StatusReasonDescription: "Enough amount is in your account to complete the transaction."
        };

        model:PaymentDetailsResponseDataPaymentStatus paymentStatus = {
            PaymentTransactionId: id,
            Status: "Accepted",
            StatusDetail: statusDetail, 
            StatusUpdateDateTime: util:getDateTime()
        };

        model:PaymentDetailsResponseDataPaymentStatus paymentStatus1 = {
            PaymentTransactionId: id,
            Status: "AcceptedCustomerProfile",
            StatusDetail: statusDetail, 
            StatusUpdateDateTime: util:getDateTime()
        };

        model:PaymentDetailsResponseDataPaymentStatus[] PaymentStatus = [paymentStatus, paymentStatus1];

        model:PaymentDetailsResponseData data = {
            PaymentStatus: PaymentStatus
        };
        return data;
    }

    private isolated function handleError(error e) returns model:Error {
        log:printError("Failed to generate payments json. Caused by, ", e);
        return {ErrorCode: util:CODE_INTERNAL_SERVER_ERROR, Message: "Try Again! Failed to generate payments response"};
    }

}
