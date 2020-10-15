package com.prism.reportingDDB.util;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.*;

import java.io.IOException;
import java.util.List;

public class Utils {

    public static void createTable(Class<?> domainClass, AmazonDynamoDB dynamoDB) {
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(dynamoDB);

        CreateTableRequest createTableRequest
                = dynamoDBMapper.generateCreateTableRequest(domainClass);
        createTableRequest.withProvisionedThroughput(
                new ProvisionedThroughput(
                        1L,
                        1L
                )
        );

        if (createTableRequest.getGlobalSecondaryIndexes() != null)
            for (GlobalSecondaryIndex gsi : createTableRequest.getGlobalSecondaryIndexes()) {
                gsi.withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
                gsi.withProjection(new Projection().withProjectionType("ALL"));
            }

        if (createTableRequest.getLocalSecondaryIndexes() != null)
            for (LocalSecondaryIndex lsi : createTableRequest.getLocalSecondaryIndexes()) {
                lsi.withProjection(new Projection().withProjectionType("ALL"));
            }


        if (tableExists(dynamoDB, createTableRequest.getTableName())) {
            System.out.println(
                    String.format(
                            "Table for class %s already exists.",
                            domainClass.getName()
                    )
            );
            return;
        }

        System.out.println(
                String.format(
                        "Table for class %s does not exist. Creating.",
                        domainClass.getName()
                )
        );
        dynamoDB.createTable(createTableRequest);
        waitForTableCreated(createTableRequest.getTableName(), dynamoDB);
    }

    private static void waitForTableCreated(String tableName, AmazonDynamoDB dynamoDB) {
        while (true) {
            try {
                System.out.println("Table not created yet. Waiting 2000 ms");
                Thread.sleep(2000);
                TableDescription table = dynamoDB.describeTable(tableName).getTable();
                if (table == null)
                    continue;

                String tableStatus = table.getTableStatus();
                if (tableStatus.equals(TableStatus.ACTIVE.toString())) {
                    System.out.println("Table is created and active");
                    return;
                }

            } catch (ResourceNotFoundException ex) {
                System.out.print("Table still not created. Waiting.");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private static boolean tableExists(AmazonDynamoDB dynamoDB, String tableName) {
        try {
            dynamoDB.describeTable(tableName);
            return true;
        } catch (ResourceNotFoundException ex) {
            return false;
        }
    }

    public static void print(List<?> items) {
        items.forEach(item -> System.out.println(item));
    }

    public static void pause() {
        System.out.print("Please press Enter to continue: ");
        System.out.flush();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

