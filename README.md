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
-d '{"username":"Inacio2","email":"inacio4@email.com","password":"123456"}'

------------------------------------------------------------------------------
-- Security: Login
------------------------------------------------------------------------------
curl --location -i --request POST 'http://localhost:8080/api/security/login' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
-d '{"username":"Inacio","password":"123456"}'

------------------------------------------------------------------------------
-- Find All Books
------------------------------------------------------------------------------
curl --location -i --request GET 'http://localhost:8080/api/sample/books' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWyiwuVrJSysxLTM7MT0stKkosyszL1EvOz1XSUcpMLFGyMjQzMzQ0t7AwM9ZRKi1OLcpLzE0F6vAE61CqBQAVmLbsQgAAAA.ONo-bgXxV2C8qvjlggtxUQblGD65WGecellLUVGKJws'

curl --location -i --request GET 'http://localhost:8080/api/sample/books' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWyiwuVrJSysxLTM7MT0stKkosyszL1EvOz1XSUcpMLFGyMjQzMzS0MLQwNdVRKi1OLcpLzE0F6vAE6zBSqgUA4ZI1p0MAAAA.nj9da72Ni0HSq3j4AZ8AUmw0xZTlU-tm9w-E23MpuC4'

------------------------------------------------------------------------------
-- Find By Id
------------------------------------------------------------------------------
curl --location -i --request GET 'http://localhost:8080/api/sample/books/5' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWyiwuVrJSysxLTM7MT0stKkosyszL1EvOz1XSUcpMLFGyMjQzMzQ0t7AwM9ZRKi1OLcpLzE0F6vAE61CqBQAVmLbsQgAAAA.ONo-bgXxV2C8qvjlggtxUQblGD65WGecellLUVGKJws'

curl --location -i --request GET 'http://localhost:8080/api/sample/books/5' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWyiwuVrJSysxLTM7MT0stKkosyszL1EvOz1XSUcpMLFGyMjQzMzS0MLQwNdVRKi1OLcpLzE0F6vAE6zBSqgUA4ZI1p0MAAAA.nj9da72Ni0HSq3j4AZ8AUmw0xZTlU-tm9w-E23MpuC4'

------------------------------------------------------------------------------
-- Create Book
------------------------------------------------------------------------------
curl --location -i --request POST 'http://localhost:8080/api/sample/books' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWyiwuVrJSysxLTM7MT0stKkosyszL1EvOz1XSUcpMLFGyMjQzMzQ0t7AwM9ZRKi1OLcpLzE0F6vAE61CqBQAVmLbsQgAAAA.ONo-bgXxV2C8qvjlggtxUQblGD65WGecellLUVGKJws' \
-d '{"name":"Book Name","author":"Book Author Name","price":10.50}'

------------------------------------------------------------------------------
-- Update Book
------------------------------------------------------------------------------
curl --location -i --request PUT 'http://localhost:8080/api/sample/books/5' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWyiwuVrJSysxLTM7MT0stKkosyszL1EvOz1XSUcpMLFGyMjQzMzQ0t7AwM9ZRKi1OLcpLzE0F6vAE61CqBQAVmLbsQgAAAA.ONo-bgXxV2C8qvjlggtxUQblGD65WGecellLUVGKJws' \
-d '{"name":"Updated Book Name","author":"Updated Book Author Name","price":20.50}'

curl --location -i --request PUT 'http://localhost:8080/api/sample/books/10' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWyiwuVrJSysxLTM7MT0stKkosyszL1EvOz1XSUcpMLFGyMjQzMzS0MLQwNdVRKi1OLcpLzE0F6vAE6zBSqgUA4ZI1p0MAAAA.nj9da72Ni0HSq3j4AZ8AUmw0xZTlU-tm9w-E23MpuC4' \
-d '{"name":"Updated Book Name","author":"Updated Book Author Name","price":20.50}'

------------------------------------------------------------------------------
-- Delete Book
------------------------------------------------------------------------------
curl --location -i --request DELETE 'http://localhost:8080/api/sample/books/10' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWyiwuVrJSysxLTM7MT0stKkosyszL1EvOz1XSUcpMLFGyMjQzMzQ0t7AwM9ZRKi1OLcpLzE0F6vAE61CqBQAVmLbsQgAAAA.ONo-bgXxV2C8qvjlggtxUQblGD65WGecellLUVGKJws'

curl --location -i --request DELETE 'http://localhost:8080/api/sample/books/10' \
--header 'Content-Type: application/json' \
--header 'Accept: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWyiwuVrJSysxLTM7MT0stKkosyszL1EvOz1XSUcpMLFGyMjQzMzS0MLQwNdVRKi1OLcpLzE0F6vAE6zBSqgUA4ZI1p0MAAAA.nj9da72Ni0HSq3j4AZ8AUmw0xZTlU-tm9w-E23MpuC4'



# Usage


## Creates User


## Creates User



# External References

[RESTful API in Spring with Security Example](https://www.infoworld.com/article/3630107/how-to-secure-rest-with-spring-security.html)
