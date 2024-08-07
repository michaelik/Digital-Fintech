## Author
#### Name: Ikechukwu Michael, Email: mikeikechi3@gmail.com
## Introduction🖖

### Specification

- Users are able to sign up using their email and password.
- Upon successful profile creation, users are automatically assigned an account.
- Users are able to log in using a username and password.
- Users are able to transfer money from their account to another account, regardless of the currencies.
- Users are able to view their account balance and transaction history.
- Users are able to get a list of bill categories.
- Users are able to get a list of billers for a particular category.
- Users are able to get a list of products for a particular biller.
- 

---

### Step One - Tools and Technologies used 🎼

- Spring Boot(Latest Version)
- Spring Data JPA
- Lombok Library
- JDK 18
- Embedded Tomcat
- Mysql Database(Mysql Workbench)
- Maven
- Java IDE (IntelliJ)
- Postman Client

---

### Step Two - Steps to Run the project Locally ⚙️

[MySQL Workbench](https://www.mysql.com/products/workbench) was used to run the database locally. Navigate to the project application.properties file and modify the database credential per your database server requirement such as `username` and `password`
```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/maCash?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
    username: #Database-username
    password: #Database-password
```

## Installation

* Clone this repo:

```bash
git clone https://github.com/michaelik/meCash-TC.git
```
* Navigate to the root directory of the project.
* Build the application
```bash
./mvnw clean install
```
* Run the application
```bash
./mvnw spring-boot:run
```
* Run the test
```bash
./mvnw test
```
* Navigate to http://localhost:8080/swagger-ui.html to view and interact with the Swagger UI

## Usage 🧨

**Baseurl: http://localhost:8080**

>REST APIs for Drone Service Resource

| HTTP METHOD |                ROUTE                | STATUS CODE |          DESCRIPTION          |
|:------------|:-----------------------------------:|:------------|:-----------------------------:|
| POST        |       `/api/v1/auth/register`       | 201         |        Create new user        |
| POST        |        `/api/v1/auth/login`         | 201         |         Login a user          |
| GET         |         `/api/v1/user/{id}`         | 200         |        Get user by id         |
| GET         |     `/api/v1/account/{userId}`      | 200         |    Get account by user Id     |
| POST        |         `/api/v1/account/4`         | 201         |         Fund account          |
| POST        | `/api/v1/transactions/transferFund` | 201         | Transfer fund between Account |
| GET         |       `/api/v1/transactions`        | 200         |    Get transaction History    |

---

### The Client should be able to:

**SignUp**

Request

```
curl -X POST http://localhost:8080/api/auth/register \
-H 'Content-type: application/json' \
-d '{
    "firstName": "Daniel",
    "lastName": "Michael",
    "email": "daniel@gmail.com",
    "password": "daniel",
    "age": 15,
    "gender": "M",
    "bvn":"12350678901"
}'
```
**SignIn**

Request

```
curl -X POST http://localhost:8080/api/auth/register \
-H 'Content-type: application/json' \
-d '{
    "username": "mikeikechi3@gmail.com",
    "password": "12345"
}'
```
**Get user by id**

Request

```
curl -X GET http://localhost:8080/api/v1/user/4 \
-H 'Content-type: application/json' \
-H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzY29wZXMiOlsiUk9MRV9VU0VSIl0sInN1YiI6Im1pa2Vpa2VjaGkzQGdtYWlsLmNvbSIsImlhdCI6MTY5MjIwOTQ2MiwiZXhwIjoxNjkzNTA1NDYyfQ.tWbASUsAzxtOjVCGWB9_dNn6qmm35IATzoNT9QQmmUY' \
```

**Get account by user Id**

Request

```
curl -X GET http://localhost:8080/api/v1/account/4 \
-H 'Content-type: application/json' \
-H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzY29wZXMiOlsiUk9MRV9VU0VSIl0sInN1YiI6Im1pa2Vpa2VjaGkzQGdtYWlsLmNvbSIsImlhdCI6MTY5MjIwOTQ2MiwiZXhwIjoxNjkzNTA1NDYyfQ.tWbASUsAzxtOjVCGWB9_dNn6qmm35IATzoNT9QQmmUY' \
```

**Fund account**

Request

```
curl -X POST http://localhost:8080/api/v1/account/4 \
-H 'Content-type: application/json' \
-H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzY29wZXMiOlsiUk9MRV9VU0VSIl0sInN1YiI6Im1pa2Vpa2VjaGkzQGdtYWlsLmNvbSIsImlhdCI6MTY5MjIwOTQ2MiwiZXhwIjoxNjkzNTA1NDYyfQ.tWbASUsAzxtOjVCGWB9_dNn6qmm35IATzoNT9QQmmUY' \
-d '{
    "amount": 200
}'
```

**Transfer fund between Account**
Request

```
curl -X POST http://localhost:8080/api/v1/transactions/transferFund \
-H 'Content-type: application/json' \
-H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzY29wZXMiOlsiUk9MRV9VU0VSIl0sInN1YiI6Im1pa2Vpa2VjaGkzQGdtYWlsLmNvbSIsImlhdCI6MTY5MjIwOTQ2MiwiZXhwIjoxNjkzNTA1NDYyfQ.tWbASUsAzxtOjVCGWB9_dNn6qmm35IATzoNT9QQmmUY' \
-d '{
  "accountNumber": "0146639233",
  "amount": 10
}'
```

**Get transaction History**

Request

```
curl -X GET http://localhost:8080/api/v1/transactions \
-H 'Content-type: application/json' \
-H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzY29wZXMiOlsiUk9MRV9VU0VSIl0sInN1YiI6Im1pa2Vpa2VjaGkzQGdtYWlsLmNvbSIsImlhdCI6MTY5MjIwOTQ2MiwiZXhwIjoxNjkzNTA1NDYyfQ.tWbASUsAzxtOjVCGWB9_dNn6qmm35IATzoNT9QQmmUY' \
```