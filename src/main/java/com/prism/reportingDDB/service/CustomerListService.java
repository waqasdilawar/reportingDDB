package com.prism.reportingDDB.service;

import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.prism.reportingDDB.domain.CustomerList;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: waqasdilawar
 * Date: 10/15/20
 * Time: 6:15 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CustomerListService {

    List<CustomerList> getAll();

    void post(CustomerList customerList);

    List<CustomerList> getAllForEnterpriseId(String enterpriseId);

    QueryResultPage<CustomerList> getPageForEnterpriseId(String enterpriseId, Integer pageSize);
}
