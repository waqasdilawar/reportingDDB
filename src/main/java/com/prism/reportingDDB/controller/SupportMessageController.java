package com.prism.reportingDDB.controller;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.prism.reportingDDB.dao.SupportMessageDao;
import com.prism.reportingDDB.domain.SupportMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
@RequestMapping("support-message")
public class SupportMessageController {

    @GetMapping("{department}")
    List<SupportMessage> get(@PathVariable String department) {
        return supportMessageDao().getAllForDepartment(department);
    }

    @PostMapping
    void post(@RequestBody SupportMessage supportMessage,@RequestParam Integer min) {
        supportMessage.setTime(LocalDateTime.of(2019, 9, 15, 15, min));
        supportMessageDao().put(supportMessage);
    }

    SupportMessageDao supportMessageDao() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                .build();
        SupportMessageDao messageDao = new SupportMessageDao(client);
        return messageDao;
    }
}
