package com.prism.reportingDDB.data;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.prism.reportingDDB.dao.SupportMessageDao;
import com.prism.reportingDDB.domain.SupportMessage;

import java.time.LocalDateTime;

import static com.prism.reportingDDB.util.Utils.createTable;


public class PopulateQueryMessages {

    public static void main(String... args) {
/*
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .build();
*/
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                .build();
        createTable(SupportMessage.class, client);
        createSupportMessages(client);
    }

    private static void createSupportMessages(AmazonDynamoDB client) {
        SupportMessageDao messageDao = new SupportMessageDao(client);

        messageDao.put(new SupportMessage(
                "payments",
                LocalDateTime.of(2019, 9, 15, 15, 53),
                3,
                "My card was blocked"
        ));

        messageDao.put(new SupportMessage(
                "payments",
                LocalDateTime.of(2019, 10, 17, 10, 3),
                5,
                "Unrecognized transactions"
        ));

        messageDao.put(new SupportMessage(
                "payments",
                LocalDateTime.of(2019, 11, 3, 4, 11),
                3,
                "How to add a new card?"
        ));

        messageDao.put(new SupportMessage(
                "payments",
                LocalDateTime.of(2019, 8, 13, 21, 56),
                2,
                "Price mistake on item 345"
        ));

        messageDao.put(new SupportMessage(
                "delivery",
                LocalDateTime.of(2019, 11, 1, 19, 37),
                2,
                "Items are still missing for order 123"
        ));

        messageDao.put(new SupportMessage(
                "delivery",
                LocalDateTime.of(2019, 11, 10, 9, 15),
                4,
                "Delivery car broke down"
        ));

        messageDao.put(new SupportMessage(
                "business",
                LocalDateTime.of(2019, 10, 25, 16, 28),
                3,
                "Would like to sell those items on your website"
        ));

        System.out.println("Stored support messages");
    }
}
