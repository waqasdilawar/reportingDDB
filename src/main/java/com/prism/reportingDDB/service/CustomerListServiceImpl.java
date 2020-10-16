package com.prism.reportingDDB.service;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.prism.reportingDDB.dao.CustomerListDao;
import com.prism.reportingDDB.domain.CustomerList;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.prism.reportingDDB.util.Utils.createTable;

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
        return customerListDao().getAll();
    }

    @Override
    public void post(CustomerList customerList) {
        customerListDao().post(customerList);
    }

    @Override
    public List<CustomerList> getAllForEnterpriseId(String enterpriseId) {
        return customerListDao().getAllForEnterpriseId(enterpriseId);
    }

    public QueryResultPage<CustomerList> getPageForEnterpriseId(String enterpriseId, Integer pageSize) {
        return customerListDao().getPageForEnterpriseId(enterpriseId,pageSize);
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

        createTable(CustomerList .class, client);

        CustomerListDao itemDao = new CustomerListDao(client);
        return itemDao;
    }
}
