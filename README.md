# Bank-ATM-Server-Client
Simple ATM Client that communicates with a bank server, using different implementations. 
The atm client sends a request to the server and then the server responds based on the input from the client. The backend communicates with a MySQL Database where the data is stored

## Main functionalities of the application
* Authenticate user from the database, using a PIN
* Change user's PIN
* Get customer's current balance
* Deposit an amount to customer's account
* Withdraw an amount to customer's account

## Tools and Technologies used
* Java 8
* Spring Boot and Hibernate (for the Rest implementation)
* MySQL 
* Rabbit MQ
* Apache Thrift
* RMI
