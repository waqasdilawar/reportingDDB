package com.prism.reportingDDB.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.prism.reportingDDB.util.LocalDateTimeConverter;

import java.time.LocalDateTime;

@DynamoDBTable(tableName = "SupportMessages")
public class SupportMessage {

    @DynamoDBHashKey
    private String department;

    @DynamoDBRangeKey
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    private LocalDateTime time;

    private int urgency;

    private String message;

    public SupportMessage() {

    }

    public SupportMessage(String department, LocalDateTime time, int urgency, String message) {
        this.department = department;
        this.time = time;
        this.urgency = urgency;
        this.message = message;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getUrgency() {
        return urgency;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SupportMessage{" +
                "department='" + department + '\'' +
                ", time='" + time + '\'' +
                ", urgency=" + urgency +
                ", message='" + message + '\'' +
                '}';
    }
}
