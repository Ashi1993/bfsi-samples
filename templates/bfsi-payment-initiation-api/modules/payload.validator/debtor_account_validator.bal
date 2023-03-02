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
import wso2bfsi/wso2.bfsi.demo.backend.model;

# Validates Debtor Account
public class DebtorAccountValidator {
    *IPayloadValidator;

    #Initiate the Debtor Account Validator
    # 
    # + payload - Payload
    # + path - Path
    public isolated function init(json? payload, string path) {
        self.payload = payload ?: "";
        self.path = path;
    }

    # Validates the Debtor Account
    # 
    # + return - Returns error if validation fails
    isolated function validate() returns ()|error {
        log:printInfo("Executing DebtorAccountValidator");

        if (self.payload == "") {
            return error("Payload is missing");
        }
        if (self.path == "") {
            log:printError("Payload is missing");
            return error("Path is missing");
        }
        do {
	        model:DebtorAccount|error|() debtorAccount = check extractDebtorAccount(self.payload, self.path);

            if (debtorAccount is ()) {
                return ();
            }
            if (debtorAccount is error) {
                return error("Debtor Account is missing");
            }

            if (debtorAccount.SchemeName == "") {
                return error("Debtor Account SchemeName is missing");
            } else {
                if (debtorAccount.SchemeName != "UK.OBIE.IBAN" && 
                debtorAccount.SchemeName != "UK.OBIE.SortCodeAccountNumber") {
                    return error("Debtor Account SchemeName is invalid");
                }
            }

            if (debtorAccount.Identification == "") {
                return error("Debtor Account Identification is missing");
            } else {
                if (debtorAccount.Identification.length() > 256) {
                    return error("Debtor Account Identification is invalid");
                }
            }
        } on fail var e {
        	return error(e.message());
        }
        return ();
    }
}
