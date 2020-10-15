package com.prism.reportingDDB.service;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.prism.reportingDDB.CustomerListDao;
import com.prism.reportingDDB.domain.CustomerList;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: waqasdilawar
 * Date: 10/15/20
 * Time: 6:16 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CustomerListServiceImpl implements CustomerListService {

    @Override
    public List<CustomerList> getAll() {
        /*
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder
                .standard()
                .build();
*/

        //itemDao.getAllUrgentMessages("2").getResults().forEach(shopItem -> System.out.println(shopItem));
        // itemDao.getAllForEnterpriseId("9999").forEach(shopItem -> System.out.println(shopItem));
        //  saveItems(itemDao);
        // readItem(itemDao);

        // itemDao.getAll().forEach(shopItem -> System.out.println(shopItem));
        return customerListDao().getAll();
    }

    @Override
    public void post(CustomerList customerList) {
        System.out.println(                LocalDateTime.of(2019, 9, 15, 15, 53));
        customerListDao().post(customerList);
    }

    @Override
    public List<CustomerList> getAllForEnterpriseId(String enterpriseId) {
        return customerListDao().getAllForEnterpriseId(enterpriseId);
    }


    private static void readItem(CustomerListDao itemDao) {
        CustomerList item = itemDao.get("1");
        System.out.println(item);
    }

    private static void saveItems(CustomerListDao itemDao) {
        for (int i = 500; i > 0; i--) {
            CustomerList shopItem = new CustomerList();
            shopItem.setEnterpriseId("9999");
            shopItem.setCustomerName("Test Customer Name - " + i);
            shopItem.setDescription("Powerful gaming laptop - " + i);
            shopItem.setCustomerId("CustomerId =" + i);

            System.out.println(itemDao.post(shopItem));
            System.out.println("Item was stored");
        }
    }

    CustomerListDao customerListDao() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                .build();

//        createTable(CustomerList .class, client);

        CustomerListDao itemDao = new CustomerListDao(client);
        return itemDao;
    }
}
