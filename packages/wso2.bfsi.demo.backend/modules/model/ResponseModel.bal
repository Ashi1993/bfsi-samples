// Copyright (c) 2023, WSO2 LLC. (http://www.wso2.com). All Rights Reserved.

// This software is the property of WSO2 LLC. and its suppliers, if any.
// Dissemination of any information or reproduction of any material contained
// herein is strictly forbidden, unless permitted by WSO2 in accordance with
// the WSO2 Software License available at: https://wso2.com/licenses/eula/3.2
// For specific language governing the permissions and limitations under
// this license, please see the license as well as any agreement you’ve
// entered into with WSO2 governing the purchase of this software and any
// associated services.

# An object of detail error codes, and messages, and URLs to documentation to help remediation.
public type Response record {
    # Represent a data object
    Object[]|Object Data?;
    # Represent an error object
    Error 'Error?;
};

# Represent an empty object.
public type Object record {
};
