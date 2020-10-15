package com.prism.reportingDDB.controller;

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

    @PostMapping
    void post(@RequestBody CustomerList customerList) {
        customerListService.post(customerList);
    }
}
