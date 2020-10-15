package com.prism.reportingDDB.data;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.prism.reportingDDB.CustomerListDao;
import com.prism.reportingDDB.domain.CustomerList;

import java.time.LocalDateTime;

import static com.prism.reportingDDB.util.Utils.createTable;

public class PopulateQueryCustomerList {

    public static void main(String... args) {
/*
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .build();
*/
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                .build();
        createTable(CustomerList.class, client);
        createSupportMessages(client);
    }

    private static void createSupportMessages(AmazonDynamoDB client) {
        CustomerListDao messageDao = new CustomerListDao(client);

        messageDao.put(new CustomerList(
                "payments",
                "3",
                "Test Customer",
                "My card was blocked"
        ));

        messageDao.put(new CustomerList(
                "payments",
                "3",
                "Test Customer",
                "Unrecognized transactions"
        ));

        messageDao.put(new CustomerList(
                "payments",
                "3",
                "Test Customer",
                "How to add a new card?"
        ));

        messageDao.put(new CustomerList(
                "payments",
                "3",
                "Test Customer",
                "Price mistake on item 345"
        ));

        messageDao.put(new CustomerList(
                "delivery",
                "3",
                "Test Customer",
                "Items are still missing for order 123"
        ));

        messageDao.put(new CustomerList(
                "delivery",
                "3",
                "Test Customer",
                "Delivery car broke down"
        ));

        messageDao.put(new CustomerList(
                "business",
                "3",
                "Test Customer",
                "Would like to sell those items on your website"
        ));

        System.out.println("Stored support messages");
    }
}
