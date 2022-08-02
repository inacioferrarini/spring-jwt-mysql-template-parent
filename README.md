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
-d '{"username":"Inacio","password":"123456"}'

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
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWyiwuVrJSysxLyy_PL8pJUdJRykwsUbIyNDO1NDY1sDQw1VEqLU4tykvMTQWq88xLTM7MV6oFAPWXgEc4AAAA.O2wdeHBT-LoqwltgnJJflXcbgoQLg5tavbHzzmGcbVE'

# Usage


## Creates User


## Creates User



# External References

[RESTful API in Spring with Security Example](https://www.infoworld.com/article/3630107/how-to-secure-rest-with-spring-security.html)
