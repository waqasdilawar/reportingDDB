package com.prism.reportingDDB.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.prism.reportingDDB.domain.SupportMessage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class SupportMessageDao {
    private final DynamoDBMapper mapper;

    public SupportMessageDao(AmazonDynamoDB dynamoDb) {
        this.mapper = new DynamoDBMapper(dynamoDb);
    }

    public SupportMessage put(SupportMessage message) {
        mapper.save(message);
        return message;
    }

    public SupportMessage get(String department, String time) {
        return mapper.load(SupportMessage.class, department, time);
    }

    public List<SupportMessage> getAllForDepartment(String department) {
        DynamoDBQueryExpression<SupportMessage> queryExpression =
                new DynamoDBQueryExpression<SupportMessage>()
                        .withScanIndexForward(false)
                        .withKeyConditionExpression("department = :department")
                        .withExpressionAttributeValues(Map.of(
                                ":department", new AttributeValue().withS(department)
                        ));

        return mapper.query(SupportMessage.class, queryExpression);
    }

    public List<SupportMessage> getAllForDepartmentOnDay(String department, String day) {
        DynamoDBQueryExpression<SupportMessage> queryExpression = new DynamoDBQueryExpression<SupportMessage>()
                .withKeyConditionExpression("department = :department AND begins_with(#time, :day)")
                .withExpressionAttributeNames(Map.of(
                        "#time", "time"
                ))
                .withExpressionAttributeValues(Map.of(
                        ":department", new AttributeValue().withS(department),
                        ":day", new AttributeValue().withS(day)
                ));
        return mapper.query(SupportMessage.class, queryExpression);
    }

    public List<SupportMessage> getAllUrgentMessages(String department) {
        DynamoDBQueryExpression<SupportMessage> queryExpression =
                new DynamoDBQueryExpression<SupportMessage>()
                        .withKeyConditionExpression("department = :department")
                        .withFilterExpression(
                                "urgency > :minUrgency OR contains(message, :substr)"
                        )
                        .withExpressionAttributeValues(Map.of(
                                ":department", new AttributeValue().withS(department),
                                ":minUrgency", new AttributeValue().withN("4"),
                                ":substr", new AttributeValue().withS("card")
                        ));
        return mapper.query(SupportMessage.class, queryExpression);
    }

    public void delete(String department, LocalDateTime time) {
        SupportMessage message = new SupportMessage();
        message.setDepartment(department);
        message.setTime(time);

        mapper.delete(message);
    }

    public List<SupportMessage> getAll() {
        return mapper.scan(SupportMessage.class, new DynamoDBScanExpression());
    }
}
