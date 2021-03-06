# Sample project for Reporting using DynamoDB

### Start the local DynamoDB instance running in Docker
`sudo docker run -d -p 8000:8000 amazon/dynamodb-local`

Further information about running DynamoDB can be found [here](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.DownloadingAndRunning.html).
### Populate the Customer Data
For populating data in Customer:

* Use Rest API to populate data by using request body below on this [Endpoint](http://localhost:8080/customer-list)

`{
     "enterpriseId": "99991",
     "customerName": "Test Customer Name - 2",
     "description": "Description =  1"
 }`
* Other way is to run `PopulateQueryCustomerList`

### Getting data for Customer
These are the endpoints to get data:

* [Get all the customers](http://localhost:8080/customer-list/)
* [Get all customer for Enterprise](http://localhost:8080/customer-list/99991)
* [Get page of customer for Enterprise](http://localhost:8080/customer-list/99991/page?pageSize=25)### Populate the Customer Data

### Populate the SupportMessage Data
For populating data in SupportMessage:

* Use Rest API to populate data by using request body below on this [Endpoint](http://localhost:8080/support-message?min=45)

`{
     "department": "payments",
     "urgency": 10,
     "message": "Test 2"
 }`
* Other way is to run `PopulateQueryMessages`

### Getting data for SupportMessage
These are the endpoints to get data:

* [Get all the SupportMessage with respect to department](http://localhost:8080/support-message/payments)

