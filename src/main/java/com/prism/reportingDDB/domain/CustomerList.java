package com.prism.reportingDDB.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "CustomerList")
public class CustomerList {

    @DynamoDBHashKey
    private String enterpriseId;

    @DynamoDBRangeKey
    private String customerId;

    private String customerName;

    private String description;



    public CustomerList() {

    }

    public CustomerList(String enterpriseId,  String customerId, String customerName, String description) {
        this.enterpriseId = enterpriseId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


    @Override
    public String toString() {
        return "CustomerList{" +
                "enterpriseId='" + enterpriseId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

