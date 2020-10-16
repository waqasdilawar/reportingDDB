# Sample project for Reporting using DynamoDB

### Populate the Customer Data
For populating data in Customer:

* Use Rest API to populate data by using request body below on this [Endpoint](http://localhost:8080/customer-list)
`{
     "enterpriseId": "99991",
     "customerName": "Test Customer Name - 2",
     "description": "Powerful gaming laptop - 1"
 }`
* Other way is to run `PopulateQueryCustomerList`

### Getting data for Customer
These are the endpoints to get data:

* [Get all the customers](http://localhost:8080/customer-list/)
* [Get all customer for Enterprise](http://localhost:8080/customer-list/99991)
* [Get page of customer for Enterprise](http://localhost:8080/customer-list/99991/page?pageSize=25)
