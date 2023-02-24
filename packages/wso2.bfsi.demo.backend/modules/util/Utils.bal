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
import ballerina/random;
import ballerina/time;
import ballerina/uuid;

# + return - current date and time.
public isolated function getDateTime() returns string {
    return time:utcToString(time:utcNow());
}

# + return - future date and time.
public isolated function getFutureDateTime() returns string {    
    return time:utcToString(time:utcAddSeconds(time:utcNow(), generateRandomSeconds()));
}

# + return - past date and time.
public isolated function getPastDateTime() returns string {
    return time:utcToString(time:utcAddSeconds(time:utcNow(), generateRandomSeconds(true)));
}

# + isNegative - if true, return a negative random time in seconds.
# + return - a random time in seconds.
isolated function generateRandomSeconds(boolean isNegative=false) returns time:Seconds {
    int randomSeconds;
    do {
	    randomSeconds = check random:createIntInRange(86400, 864000);
    } on fail var e {
        log:printDebug("failed to generate a random integer. Caused by, ", e);
    	randomSeconds = 86400;
    }

    return isNegative ? <time:Seconds>(randomSeconds * -1) : <time:Seconds>randomSeconds;
}

# + return - a random amount.
public isolated function getRandomAmount() returns string {
    return (random:createDecimal() * 1000).toFixedString(2);
}

# + return - a random UUID.
public isolated function getRandomId() returns string {
    return uuid:createType4AsString();
}

# + return - a domestic payment initiation payload.
public isolated function getDomesticPaymentInitiation() returns string {
    string DOMESTIC_PAYMENT_INITIATION = "{ " +
    "   \"InstructionIdentification\": \"ACME412\", " +
    "   \"EndToEndIdentification\": \"FRESCO.21302.GFX.20\", " +
    "   \"InstructedAmount\": { " +
    "       \"Amount\": \"165.88\", " +
    "       \"Currency\": \"GBP\" " +
    "   }, " +
    "   \"CreditorAccount\": {  " +
    "       \"SchemeName\": \"UK.OBIE.SortCodeAccountNumber\",  " +
    "       \"Identification\": \"08080021325698\",  " +
    "       \"Name\": \"ACME Inc\",  " +
    "       \"SecondaryIdentification\": \"0002\"   " +
    "   },  " +
    "   \"RemittanceInformation\": {   " +
    "       \"Reference\": \"FRESCO-101\",  " +
    "       \"Unstructured\": \"Internal ops code 5120101\"  " +
    "   }  " +
    "}";

    return DOMESTIC_PAYMENT_INITIATION;
}

# + return - a domestic scheduled payment initiation payload.
public isolated function getDomesticScheduledPaymentInitiation() returns string {
    string DOMESTIC_SCHEDULED_PAYMENT_INITIATION = "{ " +
    "   \"InstructionIdentification\": \"89f0a53a91ee47f6a383536f851d6b5a\", " +
    "   \"RequestedExecutionDateTime\": \"2018-08-06T00:00:00+00:00\", " +
    "   \"InstructedAmount\": { " +
    "       \"Amount\": \"200.00\", " +
    "       \"Currency\": \"GBP\" " +
    "   }, " +
    "   \"DebtorAccount\": { " +
    "       \"SchemeName\": \"UK.OBIE.SortCodeAccountNumber\", " +
    "       \"Identification\": \"11280001234567\", " +
    "       \"Name\": \"Andrea Frost\" " +
    "   }, " +
    "   \"CreditorAccount\": { " +
    "       \"SchemeName\": \"UK.OBIE.SortCodeAccountNumber\", " +
    "       \"Identification\": \"08080021325698\", " +
    "       \"Name\": \"Tom Kirkman\" " +
    "   }, " +
    "   \"RemittanceInformation\": { " +
    "       \"Reference\": \"DSR-037\", " +
    "       \"Unstructured\": \"Internal ops code 5120103\" " +
    "   } " +
    "}";

    return DOMESTIC_SCHEDULED_PAYMENT_INITIATION;
}

# + return - a domestic standing order payment initiation payload.
public isolated function getDomesticStandingOrderPaymentInitiation() returns string {
    string DOMESTIC_STANDING_ORDER_INITIATION = "{\n" +
    "   \"Frequency\": \"EvryDay\",\n" +
    "   \"Reference\": \"Pocket money for Damien\",\n" +
    "   \"FirstPaymentDateTime\": \"2023-06-06T06:06:06+00:00\",\n" +
    "   \"FirstPaymentAmount\": {\n" +
    "       \"Amount\": \"6.66\",\n" +
    "       \"Currency\": \"GBP\"\n" +
    "   },\n" +
    "   \"RecurringPaymentAmount\": {\n" +
    "       \"Amount\": \"7.00\",\n" +
    "       \"Currency\": \"GBP\"\n" +
    "   },\n" +
    "   \"FinalPaymentDateTime\": \"2025-06-06T06:06:06+00:00\",\n" +
    "   \"FinalPaymentAmount\": {\n" +
    "       \"Amount\": \"7.00\",\n" +
    "       \"Currency\": \"GBP\"\n" +
    "   },\n" +
    "   \"DebtorAccount\": {\n" +
    "       \"SchemeName\": \"UK.OBIE.SortCodeAccountNumber\",\n" +
    "       \"Identification\": \"11280001234567\",\n" +
    "       \"Name\": \"Andrea Smith\"\n" +
    "   },\n" +
    "   \"CreditorAccount\": {\n" +
    "       \"SchemeName\": \"UK.OBIE.SortCodeAccountNumber\",\n" +
    "       \"Identification\": \"08080021325698\",\n" +
    "       \"Name\": \"Bob Clements\"\n" +
    "   }\n" +
    "}";

    return DOMESTIC_STANDING_ORDER_INITIATION;
}

# + return - a file payment initiation payload.
public isolated function getFilePaymentInitiation() returns string {
    string FILE_PAYMENT_INITIATION = "{\n" +
    "   \"FileType\": \"UK.OBIE.pain.001.001.08\",\n" +
    "   \"FileHash\": \"m5ah/h1UjLvJYMxqAoZmj9dKdjZnsGNm+yMkJp/KuqQ\",\n" +
    "   \"FileReference\": \"GB2OK238\",\n" +
    "   \"NumberOfTransactions\": \"100\",\n" +
    "   \"ControlSum\": 3459.30\n" +
    "}";

    return FILE_PAYMENT_INITIATION;
}

# + return - an international payment initiation payload.
public isolated function getInternatioanlPaymentInitiation() returns string {
    string INTERNATIONAL_PAYMENT_INITIATION = "{\n" +
    "   \"InstructionIdentification\": \"ACME412\",\n" +
    "   \"EndToEndIdentification\": \"FRESCO.21302.GFX.20\",\n" +
    "   \"InstructionPriority\": \"Normal\",\n" +
    "   \"CurrencyOfTransfer\": \"USD\",\n" +
    "   \"InstructedAmount\": {\n" +
    "       \"Amount\": \"165.88\",\n" +
    "       \"Currency\": \"GBP\"\n" +
    "   },\n" +
    "   \"CreditorAccount\": {\n" +
    "       \"SchemeName\": \"UK.OBIE.SortCodeAccountNumber\",\n" +
    "       \"Identification\": \"08080021325698\",\n" +
    "       \"Name\": \"ACME Inc\",\n" +
    "       \"SecondaryIdentification\": \"0002\"\n" +
    "   },\n" +
    "   \"RemittanceInformation\": {\n" +
    "       \"Reference\": \"FRESCO-101\",\n" +
    "       \"Unstructured\": \"Internal ops code 5120101\"\n" +
    "   },\n" +
    "   \"ExchangeRateInformation\": {\n" +
    "       \"UnitCurrency\": \"GBP\",\n" +
    "       \"RateType\": \"Actual\"\n" +
    "   }\n" +
    "}";

    return INTERNATIONAL_PAYMENT_INITIATION;
}

# + return - an international scheduled payment initiation payload.
public isolated function getInternatioanlScheduledPaymentInitiation() returns string {
    string INTERNATIONAL_SCHEDULED_INITIATION = "{\n" +
    "   \"InstructionIdentification\": \"ACME412\",\n" +
    "   \"EndToEndIdentification\": \"FRESCO.21302.GFX.20\",\n" +
    "   \"RequestedExecutionDateTime\": \"2023-06-06T06:06:06+00:00\",\n" +
    "   \"InstructedAmount\": {\n" +
    "       \"Amount\": \"165.88\",\n" +
    "       \"Currency\": \"USD\"\n" +
    "   },\n" +
    "   \"CurrencyOfTransfer\":\"USD\",\n" +
    "   \"CreditorAccount\": {\n" +
    "       \"SchemeName\": \"UK.OBIE.SortCodeAccountNumber\",\n" +
    "       \"Identification\": \"08080021325698\",\n" +
    "       \"Name\": \"ACME Inc\",\n" +
    "       \"SecondaryIdentification\": \"0002\"\n" +
    "   },\n" +
    "   \"RemittanceInformation\": {\n" +
    "       \"Reference\": \"FRESCO-101\",\n" +
    "       \"Unstructured\": \"Internal ops code 5120101\"\n" +
    "   },\n" +
    "   \"ExchangeRateInformation\": {\n" +
    "       \"UnitCurrency\": \"GBP\",\n" +
    "       \"RateType\": \"Actual\"\n" +
    "   }\n" +
    "}";

    return INTERNATIONAL_SCHEDULED_INITIATION;
}

# + return - an international standing order payment initiation payload.
public isolated function getInternatioanlStandingOrderPaymentInitiation() returns string {
    string INTERNATIONAL_STANDING_ORDER_INITIATION = "{\n" +
    "   \"Frequency\": \"EvryWorkgDay\",\n" +
    "   \"FirstPaymentDateTime\": \"2023-06-06T06:06:06+00:00\",\n" +
    "   \"FinalPaymentDateTime\": \"2025-06-06T06:06:06+00:00\",\n" +
    "   \"DebtorAccount\": {\n" +
    "       \"SchemeName\": \"UK.OBIE.SortCodeAccountNumber\",\n" +
    "       \"Identification\": \"11280001234567\",\n" +
    "       \"Name\": \"Andrea Frost\"\n" +
    "   },\n" +
    "   \"CreditorAccount\": {\n" +
    "       \"SchemeName\": \"UK.OBIE.IBAN\",\n" +
    "       \"Identification\": \"DE89370400440532013000\",\n" +
    "       \"Name\": \"Tom Kirkman\"\n" +
    "   },\n" +
    "   \"InstructedAmount\": {\n" +
    "       \"Amount\": \"20\",\n" +
    "       \"Currency\": \"EUR\"\n" +
    "   },\n" +
    "   \"CurrencyOfTransfer\":\"EUR\"\n" +
    "}";

    return INTERNATIONAL_STANDING_ORDER_INITIATION;
}
