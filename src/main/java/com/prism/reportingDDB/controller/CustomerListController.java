package com.prism.reportingDDB.controller;

import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.prism.reportingDDB.domain.CustomerList;
import com.prism.reportingDDB.service.CustomerListService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: waqasdilawar
 * Date: 10/15/20
 * Time: 6:12 PM
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("customer-list")
public class CustomerListController {
    @NonNull
    private CustomerListService customerListService;

    @GetMapping
    List<CustomerList> get() {
        return customerListService.getAll();
    }

    @GetMapping("{enterpriseId}")
    List<CustomerList> get(@PathVariable String enterpriseId) {
        return customerListService.getAllForEnterpriseId(enterpriseId);
    }

    @GetMapping("{enterpriseId}/page")
    QueryResultPage<CustomerList> getPage(@PathVariable String enterpriseId, @RequestParam Integer pageSize) {
        return customerListService.getPageForEnterpriseId(enterpriseId, pageSize);
    }

    @PostMapping
    void post(@RequestBody CustomerList customerList) {
        for (int i = 0; i < 5000; i++) {
            customerList.setCustomerId("CustomerId =" + i);
            customerList.setDescription("Description =" + i);
            customerListService.post(customerList);
        }
    }
}
