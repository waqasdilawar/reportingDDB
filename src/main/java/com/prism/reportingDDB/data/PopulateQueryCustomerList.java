package com.prism.reportingDDB.data;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.prism.reportingDDB.dao.CustomerListDao;
import com.prism.reportingDDB.domain.CustomerList;

import static com.prism.reportingDDB.util.Utils.createTable;

public class PopulateQueryCustomerList {

    public static void main(String... args) {
/*        IN case when working in prodution
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .build();*/
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
                "1",
                "Test Customer 1",
                "My card was blocked"
        ));

        messageDao.put(new CustomerList(
                "payments",
                "2",
                "Test Customer 2",
                "Unrecognized transactions"
        ));

        messageDao.put(new CustomerList(
                "payments",
                "3",
                "Test Customer 3",
                "How to add a new card?"
        ));

        messageDao.put(new CustomerList(
                "payments",
                "4",
                "Test Customer 4",
                "Price mistake on item 345"
        ));

        messageDao.put(new CustomerList(
                "delivery",
                "5",
                "Test Customer 5",
                "Items are still missing for order 123"
        ));

        messageDao.put(new CustomerList(
                "delivery",
                "6",
                "Test Customer 6",
                "Delivery car broke down"
        ));

        messageDao.put(new CustomerList(
                "business",
                "7",
                "Test Customer 7",
                "Would like to sell those items on your website"
        ));

        System.out.println("Stored support messages");
    }
}
