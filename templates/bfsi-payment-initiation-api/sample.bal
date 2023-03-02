// Copyright (c) 2023, WSO2 LLC. (http://www.wso2.com). All Rights Reserved.

// This software is the property of WSO2 LLC. and its suppliers, if any.
// Dissemination of any information or reproduction of any material contained
// herein is strictly forbidden, unless permitted by WSO2 in accordance with
// the WSO2 Software License available at: https://wso2.com/licenses/eula/3.2
// For specific language governing the permissions and limitations under
// this license, please see the license as well as any agreement you’ve
// entered into with WSO2 governing the purchase of this software and any
// associated services.

import ballerina/http;
import ballerina/log;
import bfsi_payment_initiation_api.interceptor;
import bfsi_payment_initiation_api.payload.validator;

//This import represents the backend service of your bank
import wso2bfsi/wso2.bfsi.demo.backend as bfsi;
import wso2bfsi/wso2.bfsi.demo.backend.model;

// Request interceptors handle HTTP requests globally 
interceptor:RequestInterceptor requestInterceptor = new;
interceptor:RequestErrorInterceptor requestErrorInterceptor = new;

http:ListenerConfiguration config = {
    host: "localhost",
    interceptors: [requestInterceptor, requestErrorInterceptor]
};
listener http:Listener interceptorListener = new (9090, config);

service / on interceptorListener {

    private bfsi:PaymentService 'paymentService = new();
    
    # Create a domestic payment
    # 
    # + return - DomesticPaymentResponse object if successful else returns error
    isolated resource function post 'domestic\-payments(@http:Header string? 'x\-fapi\-auth\-date, @http:Header string? 'x\-fapi\-customer\-ip\-address, 
            @http:Header string? 'x\-fapi\-interaction\-id, @http:Header string authorization, @http:Header string 'x\-idempotency\-key, 
            @http:Header string 'x\-jws\-signature, @http:Header string? 'x\-customer\-user\-agent, 
            @http:Payload {
                mediaType: ["application/json", "application/jose+jwe", "application/json; charset=utf-8"]
            } json payload
        ) 
        returns model:DomesticPaymentResponse|error {

        do {    
            log:printInfo("Initiating a domestic payment");
            ()|error result = self.validatePayload(payload, "domestic-payments");
            if result is error {
                return error(result.message(), ErrorCode=result.detail().get("ErrorCode"));
            }
            model:DomesticPaymentRequest domesticPaymentRequest = check payload.cloneWithType(model:DomesticPaymentRequest);
            model:DomesticPaymentResponse|error response = self.'paymentService.createDomesticPayment(domesticPaymentRequest).clone();
            return response;
        
        } on fail var e {
            log:printError("Failed to invoke bank backend.", e);
            return error("Failed to invoke bank backend.");
        }
    }

    # Get Domestic Payment by payment ID
    # 
    # + domesticPaymentId - the input payment ID 
    # + return - Domestic Payment
    isolated resource function get 'domestic\-payments/[string domesticPaymentId](@http:Header string? 'x\-fapi\-auth\-date, 
            @http:Header string? 'x\-fapi\-customer\-ip\-address, @http:Header string? 'x\-fapi\-interaction\-id, 
            @http:Header string authorization, @http:Header string? 'x\-customer\-user\-agent) 
        returns model:DomesticPaymentResponse|error {

        do {    
            log:printInfo("Retriveing Domestic Payment for payment ID: " + domesticPaymentId);
            model:DomesticPaymentResponse|error response = self.'paymentService.getDomesticPayments(domesticPaymentId);
            return response;
        } on fail var e {
            log:printError("Failed to invoke bank backend.", e);
            return error("Failed to invoke bank backend.");
        }
            
    }

    # Get Domestic Payment Details by payment ID
    # 
    # + domesticPaymentId - the input payment ID 
    # + return - Domestic Payment
    isolated resource function get 'domestic\-payments/[string domesticPaymentId]/'payment\-details(
            @http:Header string? 'x\-fapi\-auth\-date, @http:Header string? 'x\-fapi\-customer\-ip\-address, 
            @http:Header string? 'x\-fapi\-interaction\-id, @http:Header string authorization, 
            @http:Header string? 'x\-customer\-user\-agent) 
        returns model:PaymentDetailsResponse|error {

        do {    
            log:printInfo("Retriveing Domestic Payment Details for payment ID: " + domesticPaymentId);
            model:PaymentDetailsResponse|error response = 
                        self.'paymentService.getPaymentsDetails("domestic-payments", domesticPaymentId).clone();
            return response;
        } on fail var e {
            log:printError("Failed to invoke bank backend.", e);
            return error("Failed to invoke bank backend.");
        }
    }

    # Create a domestic scheduled payment
    # 
    # + return - DomesticScheduledPaymentResponse object if successful else returns error
    isolated resource function post 'domestic\-scheduled\-payments(@http:Header string? 'x\-fapi\-auth\-date, 
            @http:Header string? 'x\-fapi\-customer\-ip\-address, @http:Header string? 'x\-fapi\-interaction\-id, 
            @http:Header string authorization, @http:Header string 'x\-idempotency\-key, 
            @http:Header string 'x\-jws\-signature, @http:Header string? 'x\-customer\-user\-agent, 
            @http:Payload {
                mediaType: ["application/json", "application/jose+jwe", "application/json; charset=utf-8" ]
            } json payload
        ) 
        returns model:DomesticScheduledPaymentResponse|error {

        do {    
            log:printInfo("Initiating a domestic scheduled payment");
            ()|error result = self.validatePayload(payload, "domestic-scheduled-payments");
            if result is error {
                return error(result.message(), ErrorCode=result.detail().get("ErrorCode"));
            }
            model:DomesticScheduledPaymentRequest request = check payload.cloneWithType(model:DomesticScheduledPaymentRequest);
            model:DomesticScheduledPaymentResponse|error response = self.'paymentService.createDomesticScheduledPayment(request);
            return response;        
        } on fail var e {
            log:printError("Failed to invoke bank backend.", e);
            return error("Failed to invoke bank backend.");
        }
    }

    # Get Domestic Scheduled Payment by payment ID
    # 
    # + domesticScheduledPaymentId - the input payment ID
    # + return - Domestic Scheduled Payment
    isolated resource function get 'domestic\-scheduled\-payments/[string domesticScheduledPaymentId](
            @http:Header string? 'x\-fapi\-auth\-date, @http:Header string? 'x\-fapi\-customer\-ip\-address, 
            @http:Header string? 'x\-fapi\-interaction\-id, @http:Header string authorization, 
            @http:Header string? 'x\-customer\-user\-agent) 
        returns model:DomesticScheduledPaymentResponse|error {

        do {    
            log:printInfo("Retriveing Domestic Scheduled Payment for payment ID: " + domesticScheduledPaymentId);
            model:DomesticScheduledPaymentResponse|error response = 
                        self.'paymentService.getDomesticScheduledPayments(domesticScheduledPaymentId);
            return response;           
        } on fail var e {
            log:printError("Failed to invoke bank backend.", e);
            return error("Failed to invoke bank backend.");
        }
    }

    # Get Domestic Scheduled Payment Details by payment ID
    # 
    # + domesticScheduledPaymentId - the input payment ID
    # + return - Domestic Scheduled Payment
    isolated resource function get 'domestic\-scheduled\-payments/[string domesticScheduledPaymentId]/'payment\-details(
            @http:Header string? 'x\-fapi\-auth\-date, @http:Header string? 'x\-fapi\-customer\-ip\-address, 
            @http:Header string? 'x\-fapi\-interaction\-id, @http:Header string authorization, 
            @http:Header string? 'x\-customer\-user\-agent) 
        returns model:PaymentDetailsResponse|error {

        do {    
            log:printInfo("Retriveing Domestic Scheduled Payment Details for payment ID: " + domesticScheduledPaymentId);
            model:PaymentDetailsResponse|error response = 
                        self.'paymentService.getPaymentDetails("domestic-scheduled-payments", domesticScheduledPaymentId);
            return response;   
        } on fail var e {
            log:printError("Failed to invoke bank backend.", e);
            return error("Failed to invoke bank backend.");
        }
    }
    
    # Create a domestic standing order
    # 
    # + return - DomesticStandingOrderResponse object if successful else returns error
    isolated resource function post 'domestic\-standing\-orders(@http:Header string? 'x\-fapi\-auth\-date, 
            @http:Header string? 'x\-fapi\-customer\-ip\-address, @http:Header string? 'x\-fapi\-interaction\-id, 
            @http:Header string authorization, @http:Header string 'x\-idempotency\-key, 
            @http:Header string 'x\-jws\-signature, @http:Header string? 'x\-customer\-user\-agent, 
            @http:Payload {
                mediaType: ["application/json; charset=utf-8", "application/json", "application/jose+jwe"]
            } json payload) 
        returns model:DomesticStandingOrderResponse|error {

        do {    
            log:printInfo("Initiating a domestic scheduled payment");
            ()|error result = self.validatePayload(payload, "domestic-standing-orders");
            if result is error {
                return error(result.message(), ErrorCode=result.detail().get("ErrorCode"));
            }
            model:DomesticStandingOrderRequest request = check payload.cloneWithType(model:DomesticStandingOrderRequest);
            model:DomesticStandingOrderResponse|error response = 
                        self.'paymentService.createDomesticStandingOrderPayment(request);
            return response;   
        } on fail var e {
            log:printError("Failed to invoke bank backend.", e);
            return error("Failed to invoke bank backend.");
        }
    }

    # Get Domestic Standing Order by payment ID
    # 
    # + domesticStandingOrderId - the input payment ID
    # + return - Domestic Standing Order
    isolated resource function get 'domestic\-standing\-orders/[string domesticStandingOrderId](
            @http:Header string? 'x\-fapi\-auth\-date, @http:Header string? 'x\-fapi\-customer\-ip\-address, 
            @http:Header string? 'x\-fapi\-interaction\-id, @http:Header string authorization, 
            @http:Header string? 'x\-customer\-user\-agent) 
        returns model:DomesticStandingOrderResponse|error {

        do {    
            log:printInfo("Retriveing Domestic Standing Order for payment ID: " + domesticStandingOrderId);
            model:DomesticStandingOrderResponse|error response = 
                        self.'paymentService.getDomesticStandingOrderPayments(domesticStandingOrderId);
            return response;   
        } on fail var e {
            log:printError("Failed to invoke bank backend.", e);
            return error("Failed to invoke bank backend.");
        }
    }

    # Get Domestic Standing Order Details by payment ID
    # 
    # + domesticStandingOrderId - the input payment ID
    # + return - Domestic Standing Order
    isolated resource function get 'domestic\-standing\-orders/[string domesticStandingOrderId]/'payment\-details(
            @http:Header string? 'x\-fapi\-auth\-date, @http:Header string? 'x\-fapi\-customer\-ip\-address, 
            @http:Header string? 'x\-fapi\-interaction\-id, @http:Header string authorization, 
            @http:Header string? 'x\-customer\-user\-agent) 
        returns model:PaymentDetailsResponse|error {

        do {    
            log:printInfo("Retriveing Domestic Standing Order Details for payment ID: " + domesticStandingOrderId);
            model:PaymentDetailsResponse|error response = 
                        self.'paymentService.getPaymentDetails("domestic-standing-orders", domesticStandingOrderId);
            return response;   
        } on fail var e {
            log:printError("Failed to invoke bank backend.", e);
            return error("Failed to invoke bank backend.");
        }
    }

    # Create a file payment
    # 
    # + return - FilePaymentResponse object if successful else returns error
    isolated resource function post 'file\-payments(@http:Header string? 'x\-fapi\-auth\-date, @http:Header string? 'x\-fapi\-customer\-ip\-address, 
            @http:Header string? 'x\-fapi\-interaction\-id, @http:Header string authorization, @http:Header string 'x\-idempotency\-key, 
            @http:Header string 'x\-jws\-signature, @http:Header string? 'x\-customer\-user\-agent, 
            @http:Payload {
                mediaType: ["application/json; charset=utf-8", "application/json", "application/jose+jwe"]
            } json payload) 
        returns model:FilePaymentResponse|error {

        do {    
            log:printInfo("Initiating a domestic scheduled payment");
            ()|error result = self.validatePayload(payload, "file-payments");
            if result is error {
                return error(result.message(), ErrorCode=result.detail().get("ErrorCode"));
            }
            model:FilePaymentRequest request = check payload.cloneWithType(model:FilePaymentRequest);
            model:FilePaymentResponse|error response = 
                        self.'paymentService.createFilePayment(request);
            return response;   
        } on fail var e {
            log:printError("Failed to invoke bank backend.", e);
            return error("Failed to invoke bank backend.");
        }
    }

    # Get File Payment by payment ID
    # 
    # + filePaymentId - the input payment ID
    # + return - File Payment
    isolated resource function get 'file\-payments/[string filePaymentId](@http:Header string? 'x\-fapi\-auth\-date, 
            @http:Header string? 'x\-fapi\-customer\-ip\-address, @http:Header string? 'x\-fapi\-interaction\-id, 
            @http:Header string authorization, @http:Header string? 'x\-customer\-user\-agent) 
        returns model:FilePaymentResponse|error {

        do {    
            log:printInfo("Retriveing File Payment for payment ID: " + filePaymentId);
            model:FilePaymentResponse|error response = self.'paymentService.getFilePayments(filePaymentId);
            return response;   
        } on fail var e {
            log:printError("Failed to invoke bank backend.", e);
            return error("Failed to invoke bank backend.");
        }
    }

    # Get File Payment Details by payment ID
    #   
    # + filePaymentId - the input payment ID
    # + return - File Payment
    isolated resource function get 'file\-payments/[string filePaymentId]/'payment\-details(@http:Header string? 'x\-fapi\-auth\-date, 
            @http:Header string? 'x\-fapi\-customer\-ip\-address, @http:Header string? 'x\-fapi\-interaction\-id, 
            @http:Header string authorization, @http:Header string? 'x\-customer\-user\-agent) 
        returns model:PaymentDetailsResponse|error {

        do {    
            log:printInfo("Retriveing File Payment Details for payment ID: " + filePaymentId);
            model:PaymentDetailsResponse|error response = 
                        self.'paymentService.getPaymentDetails("file-payments", filePaymentId);
            return response;   
        } on fail var e {
            log:printError("Failed to invoke bank backend.", e);
            return error("Failed to invoke bank backend.");
        }
    }

    # Create an international payment
    # 
    # + return - InternationalPaymentResponse object if successful else returns error
    isolated resource function post 'international\-payments(@http:Header string? 'x\-fapi\-auth\-date, 
            @http:Header string? 'x\-fapi\-customer\-ip\-address, @http:Header string? 'x\-fapi\-interaction\-id, 
            @http:Header string authorization, @http:Header string 'x\-idempotency\-key, 
            @http:Header string 'x\-jws\-signature, @http:Header string? 'x\-customer\-user\-agent, 
            @http:Payload {
                mediaType: ["application/json; charset=utf-8", "application/json", "application/jose+jwe"]
            } json payload) 
        returns model:InternationalPaymentResponse|error {

        do {    
            log:printInfo("Initiating a domestic scheduled payment");
            ()|error result = self.validatePayload(payload, "international-payments");
            if result is error {
                return error(result.message(), ErrorCode=result.detail().get("ErrorCode"));
            }
            model:InternationalPaymentRequest request = check payload.cloneWithType(model:InternationalPaymentRequest);
            model:InternationalPaymentResponse|error response = 
                        self.'paymentService.createInternationalPayment(request);
            return response;   
        } on fail var e {
            log:printError("Failed to invoke bank backend.", e);
            return error("Failed to invoke bank backend.");
        }
    }

    # Get International Payment by payment ID
    # 
    # + internationalPaymentId - the input payment ID
    # + return - International Payment
    isolated resource function get 'international\-payments/[string internationalPaymentId](@http:Header string? 'x\-fapi\-auth\-date, 
            @http:Header string? 'x\-fapi\-customer\-ip\-address, @http:Header string? 'x\-fapi\-interaction\-id, 
            @http:Header string authorization, @http:Header string? 'x\-customer\-user\-agent) 
        returns model:InternationalPaymentResponse|error {
            
        do {    
            log:printInfo("Retriveing International Payment for payment ID: " + internationalPaymentId);
            model:InternationalPaymentResponse|error response = 
                        self.'paymentService.getInternationalPayments(internationalPaymentId);
            return response;   
        } on fail var e {
            log:printError("Failed to invoke bank backend.", e);
            return error("Failed to invoke bank backend.");
        }
    }

    # Get International Payment Details by payment ID
    # 
    # + internationalPaymentId - the input payment ID
    # + return - International Payment
    isolated resource function get 'international\-payments/[string internationalPaymentId]/'payment\-details(
            @http:Header string? 'x\-fapi\-auth\-date, @http:Header string? 'x\-fapi\-customer\-ip\-address, 
            @http:Header string? 'x\-fapi\-interaction\-id, @http:Header string authorization, 
            @http:Header string? 'x\-customer\-user\-agent) 
        returns model:PaymentDetailsResponse|error {

        do {    
            log:printInfo("Retriveing International Payment Details for payment ID: " + internationalPaymentId);
            model:PaymentDetailsResponse|error response = 
                        self.'paymentService.getPaymentDetails("international-payments", internationalPaymentId);
            return response;   
        } on fail var e {
            log:printError("Failed to invoke bank backend.", e);
            return error("Failed to invoke bank backend.");
        }
    }
    
    # Create an international scheduled payment
    # 
    # + return - InternationalScheduledPaymentResponse object if successful else returns error
    isolated resource function post 'international\-scheduled\-payments(@http:Header string? 'x\-fapi\-auth\-date, 
            @http:Header string? 'x\-fapi\-customer\-ip\-address, @http:Header string? 'x\-fapi\-interaction\-id, 
            @http:Header string authorization, @http:Header string 'x\-idempotency\-key, 
            @http:Header string 'x\-jws\-signature, @http:Header string? 'x\-customer\-user\-agent, 
            @http:Payload {
                mediaType: ["application/json", "application/jose+jwe", "application/json; charset=utf-8"]
            } json payload) 
        returns model:InternationalScheduledPaymentResponse|error {

        do {    
            log:printInfo("Initiating a domestic scheduled payment");
            ()|error result = self.validatePayload(payload, "international-scheduled-payments");
            if result is error {
                return error(result.message(), ErrorCode=result.detail().get("ErrorCode"));
            }
            model:InternationalScheduledPaymentRequest request = check payload.cloneWithType(model:InternationalScheduledPaymentRequest);
            model:InternationalScheduledPaymentResponse|error response = 
                        self.'paymentService.createInternationalScheduledPayment(request);
            return response;   
        } on fail var e {
            log:printError("Failed to invoke bank backend.", e);
            return error("Failed to invoke bank backend.");
        }
    }

    # Get International Scheduled Payment by payment ID
    # 
    # + internationalScheduledPaymentId - the input payment ID
    # + return - International Scheduled Payment
    isolated resource function get 'international\-scheduled\-payments/[string internationalScheduledPaymentId](
            @http:Header string? 'x\-fapi\-auth\-date, @http:Header string? 'x\-fapi\-customer\-ip\-address, 
            @http:Header string? 'x\-fapi\-interaction\-id, @http:Header string authorization, 
            @http:Header string? 'x\-customer\-user\-agent) 
        returns model:InternationalScheduledPaymentResponse|error {

        do {    
            log:printInfo("Retriveing International Scheduled Payment for payment ID: " + internationalScheduledPaymentId);
            model:InternationalScheduledPaymentResponse|error response = 
                        self.'paymentService.getInternationalScheduledPayments(internationalScheduledPaymentId);
            return response;
        } on fail var e {
            log:printError("Failed to invoke bank backend.", e);
            return error("Failed to invoke bank backend.");
        }
    }

    # Get International Scheduled Payment Details by payment ID
    # 
    # + internationalScheduledPaymentId - the input payment ID
    # + return - International Scheduled Payment
    isolated resource function get 'international\-scheduled\-payments/[string internationalScheduledPaymentId]/'payment\-details(
            @http:Header string? 'x\-fapi\-auth\-date, @http:Header string? 'x\-fapi\-customer\-ip\-address, 
            @http:Header string? 'x\-fapi\-interaction\-id, @http:Header string authorization, 
            @http:Header string? 'x\-customer\-user\-agent) 
        returns model:InternationalScheduledPaymentResponse|error {

        do {    
            log:printInfo("Retriveing International Scheduled Payment Details for payment ID: " + internationalScheduledPaymentId);
            model:PaymentDetailsResponse|error response = 
                        self.'paymentService.getPaymentDetails("international-scheduled-payments", internationalScheduledPaymentId);
            return response;   
        } on fail var e {
            log:printError("Failed to invoke bank backend.", e);
            return error("Failed to invoke bank backend.");
        }
    }

    # Create an international standing order
    # 
    # + return - InternationalStandingOrderResponse object if successful else returns error
    # + payload - the request payload
    isolated resource function post 'international\-standing\-orders(@http:Header string? 'x\-fapi\-auth\-date, 
            @http:Header string? 'x\-fapi\-customer\-ip\-address, @http:Header string? 'x\-fapi\-interaction\-id, 
            @http:Header string authorization, @http:Header string 'x\-idempotency\-key, 
            @http:Header string 'x\-jws\-signature, @http:Header string? 'x\-customer\-user\-agent, 
            @http:Payload {
                mediaType: ["application/json", "application/jose+jwe", "application/json; charset=utf-8"]
            } json payload) 
        returns model:InternationalStandingOrderResponse|error {

        do {    
            log:printInfo("Initiating a domestic scheduled payment");
            ()|error result = self.validatePayload(payload, "international-standing-orders");
            if result is error {
                return error(result.message(), ErrorCode=result.detail().get("ErrorCode"));
            }
            model:InternationalStandingOrderRequest request = check payload.cloneWithType(model:InternationalStandingOrderRequest);
            model:InternationalStandingOrderResponse|error response = 
                        self.'paymentService.createInternationalStandingOrderPayment(request);
            return response;   
        } on fail var e {
            log:printError("Failed to invoke bank backend.", e);
            return error("Failed to invoke bank backend.");
        }
    }

    # Get International Standing Order by payment ID
    # 
    # + internationalStandingOrderPaymentId - the input payment ID
    # + return - International Standing Order
    isolated resource function get 'international\-standing\-orders/[string internationalStandingOrderPaymentId](
            @http:Header string? 'x\-fapi\-auth\-date, @http:Header string? 'x\-fapi\-customer\-ip\-address, 
            @http:Header string? 'x\-fapi\-interaction\-id, @http:Header string authorization, 
            @http:Header string? 'x\-customer\-user\-agent) 
        returns  model:InternationalStandingOrderResponse|error {

        do {    
            log:printInfo("Retriveing International Standing Order for payment ID: " + internationalStandingOrderPaymentId);
            model:InternationalStandingOrderResponse|error response = 
                        self.'paymentService.getInternationalStandingOrderPayments(internationalStandingOrderPaymentId);
            return response;
        } on fail var e {
            log:printError("Failed to invoke bank backend.", e);
            return error("Failed to invoke bank backend.");
        }
    }

    # Get International Standing Order Details by payment ID
    #  
    # + internationalStandingOrderPaymentId - the input payment ID
    # + return - International Standing Order
    isolated resource function get 'international\-standing\-orders/[string internationalStandingOrderPaymentId]/'payment\-details(
            @http:Header string? 'x\-fapi\-auth\-date, @http:Header string? 'x\-fapi\-customer\-ip\-address, 
            @http:Header string? 'x\-fapi\-interaction\-id, @http:Header string authorization, 
            @http:Header string? 'x\-customer\-user\-agent) 
        returns model:PaymentDetailsResponse|error  {

        do {    
            log:printInfo("Retriveing International Standing Order Details for payment ID: " + internationalStandingOrderPaymentId);
            model:PaymentDetailsResponse|error response = 
                        self.'paymentService.getPaymentDetails("international-standing-orders", internationalStandingOrderPaymentId);
            return response;   
        } on fail var e {
            log:printError("Failed to invoke bank backend.", e);
            return error("Failed to invoke bank backend.");
        }
    }

    # Generate Payment initiation Response
    # 
    # + data - the data object
    # + return - http:Response
    private isolated function generatePaymentResponse(anydata data) returns http:Created {
        return <http:Created>{
            mediaType: "application/org+json", 
            body:  data
        };
    }

    # Generate Retrieval Response
    # 
    # + data - the data object
    # + return - http:Response
    private isolated function generateRetrievalResponse(anydata data) returns http:Ok {
        return <http:Ok>{
            mediaType: "application/org+json", 
            body:  data
        };
    }

    # Generate Bad Request Response
    # 
    # + message - the error message
    # + return - http:BadRequest
    private isolated function generateBadRequestError(string message) returns http:BadRequest {
        return <http:BadRequest>{
            mediaType: "application/org+json", 
            body:  {Message: message, ErrorCode: "E004"}
        };
    }

    # Generate Server Error Response
    # 
    # + e - the error object
    # + return - http:InternalServerError
    private isolated function generateServerError(error e) returns http:InternalServerError {
        return <http:InternalServerError>{body:  {Message: e.message(), ErrorCode: "E005"}};
    }

    # Validate the payload
    # 
    # + payload - the payload object
    # + path - the pathy
    # + return - boolean
    private isolated function validatePayload(json payload, string path) returns ()|error {

        log:printInfo("Validate the payload");
        validator:PayloadValidator payloadValidator = new();
        ()|error payloadValidatorResult = payloadValidator
            .add(new validator:PaymentRequestBodyValidator(payload, path))
            .add(new validator:CreditorAccountValidator(payload, path))
            .add(new validator:DebtorAccountValidator(payload, path))
            .validate();

        return payloadValidatorResult;
    }
}
