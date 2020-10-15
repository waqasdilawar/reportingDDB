package com.prism.reportingDDB;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.prism.reportingDDB.domain.CustomerList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerListDao {

    private final AmazonDynamoDB dynamoDb;

    private final DynamoDBMapper mapper;

    public CustomerListDao(AmazonDynamoDB dynamoDb1) {
        this.dynamoDb = dynamoDb1;
        this.mapper = new DynamoDBMapper(dynamoDb);
    }


    public void put(CustomerList customerList) {
        Map<String, AttributeValue> itemMap = new HashMap<>();

        itemMap.put(
                "enterpriseId",
                new AttributeValue().withS(customerList.getEnterpriseId())
        );

        itemMap.put(
                "customerName",
                new AttributeValue().withS(customerList.getCustomerName())
        );

        itemMap.put(
                "description",
                new AttributeValue().withS(customerList.getDescription())
        );

        itemMap.put(
                "customerId",
                new AttributeValue().withS(
                        customerList.getCustomerId()
                )
        );

        PutItemRequest putItemRequest = new PutItemRequest(
                "CustomerList",
                itemMap
        );

        dynamoDb.putItem(putItemRequest);

    }

    public CustomerList post(CustomerList customerList) {
        mapper.save(customerList);
        return customerList;
    }

    public CustomerList get(String id) {
        Map<String, AttributeValue> itemKey = new HashMap<>();
        itemKey.put(
                "enterpriseId",
                new AttributeValue().withS(id)
        );

        GetItemRequest getItemRequest = new GetItemRequest()
                .withTableName("CustomerList")
                .withKey(itemKey)
                .withConsistentRead(true);

        GetItemResult getItemResult = dynamoDb.getItem(getItemRequest);
        Map<String, AttributeValue> dynamoDbItem = getItemResult.getItem();

        CustomerList resultItem = new CustomerList();

        resultItem.setEnterpriseId(
                dynamoDbItem.get("enterpriseId").getS()
        );

        resultItem.setCustomerName(
                dynamoDbItem.get("customerName").getS()
        );

        resultItem.setDescription(
                dynamoDbItem.get("description").getS()
        );

        resultItem.setCustomerId(
                dynamoDbItem.get("customerId").getS()
        );

        return resultItem;
    }

    public QueryResultPage<CustomerList> getAllUrgentMessages(String department) {
        DynamoDBQueryExpression<CustomerList> queryExpression =
                new DynamoDBQueryExpression<CustomerList>()
                        .withKeyConditionExpression("id = :department")
/*                        .withFilterExpression(
                                "amount > :minUrgency OR contains(description, :substr)"
                        )*/
                        .withExpressionAttributeValues(Map.of(
                                ":department", new AttributeValue().withS(department)
/*                                ":minUrgency", new AttributeValue().withN("500"),
                                ":substr", new AttributeValue().withS("gaming")*/
                        ));
        //TODO Pagination for DIS
//        queryExpression.withLimit(10);
        return mapper.queryPage(CustomerList.class, queryExpression);
        //  return mapper.query(SupportMessage.class, queryExpression);
    }

    public List<CustomerList> getAll() {
        return mapper.scan(CustomerList.class, new DynamoDBScanExpression());
    }


    public List<CustomerList> getAllForEnterpriseId(String enterpriseId) {
        List<CustomerList> scanResult = mapper.scan(CustomerList.class, new DynamoDBScanExpression());
        System.out.println(scanResult.size());
        DynamoDBQueryExpression<CustomerList> queryExpression =
                new DynamoDBQueryExpression<CustomerList>()
                        .withScanIndexForward(false)
                        .withKeyConditionExpression("enterpriseId = :enterpriseId")
                        .withExpressionAttributeValues(Map.of(
                                ":enterpriseId", new AttributeValue().withS(enterpriseId)
                        ));

        return mapper.query(CustomerList.class, queryExpression);
    }

}

