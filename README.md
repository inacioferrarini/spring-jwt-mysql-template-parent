# spring-jwt-mysql-template-parent
A multimodule SpringBoot App with JWT Security and MySQL Database

Provides the basic configuration and code required to secure a RESTful API using a JWT token.

A RESTful endpoint is provided as sample.

# Project Structure



Start Service:
mvn -e -U clean spring-boot:run

------------------------------------------------------------------------------
-- Security: Register
------------------------------------------------------------------------------
curl --location -i --request POST 'http://localhost:8080/api/security/register' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
-d '{"username":"Inacio 2","email":"inacio2@email.com","password":"1234567"}'

------------------------------------------------------------------------------
-- Security: Login
------------------------------------------------------------------------------
curl --location -i --request POST 'http://localhost:8080/api/security/login' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
-d '{"username":"Inacio","password":"123456"}'

------------------------------------------------------------------------------
-- Protected Resource
------------------------------------------------------------------------------
curl --location -i --request GET 'http://localhost:8080/protected' \
--header 'Accept: application/json' \
--header 'Authorization: Bearer <token>'



curl --location -i --request GET 'http://localhost:8080/api/sample/books' \
--header 'Accept: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWyiwuVrJSysxLTM7MT0stKkosyszL1EvOz1XSUcpMLFGyMjQzMzQ0MDCzNNFRKi1OLcpLzE0F6vAE61CqBQCi-5dpQgAAAA.ThdlrMk6YbM9GmqoCbnJ9vQ7Ymit42F1MY445KJU2JE'

# Usage


## Creates User


## Creates User



# External References

[RESTful API in Spring with Security Example](https://www.infoworld.com/article/3630107/how-to-secure-rest-with-spring-security.html)
