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
-d '{"username":"Inacio","email":"inacio@email.com","password":"123456"}'

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
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWyiwuVrJSysxLTM7MT0stKkosyszL1EvOz1XSUcpMLFGyMjQzMzSwsARiHaXS4tSivMTcVKAOT7AOpVoAY-WClEIAAAA.xrRLFtjjYu6gjQYGjZdMb1C5nj-ti_3tbxcCJWDtEug'

# Usage


## Creates User


## Creates User



# External References

[RESTful API in Spring with Security Example](https://www.infoworld.com/article/3630107/how-to-secure-rest-with-spring-security.html)
