# About

## Register / Success Response:
HTTP/1.1 201 Created
{"name":"Inacio","email":"inacio2@email.com","token":<token>}

## Register / Already Exists:
HTTP/1.1 409 Conflict
{"error":"Email is already being used."}

## Login / Success Response:
HTTP/1.1 200 OK
{"email":"inacio@email.com","token":<token>}

## Login / Credentials Error:
HTTP/1.1 401 Unauthorized
No request body

## Error: Protected Request without Authorization
HTTP/1.1 401 Unauthorized
{"message":"Unauthenticated."}

## CRUD - All / Success:
HTTP/1.1 200 OK
[{"id":1,"broker":{"id":1,"name":"NU INVEST"},"ticker":"TICKER","operation":"BUY","operation_date":"2020-12-10 11:45:55","shares":1,"share_price":0.01}]

## CRUD - All / Empty Result Success:
HTTP/1.1 200 OK
[]

## CRUD - Get {id} / Success:
HTTP/1.1 200 OK
{"id":1,"broker":{"id":1,"name":"NU INVEST"},"ticker":"TICKER","operation":"BUY","operation_date":"2020-12-10 11:45:55","shares":1,"share_price":0.01}

## CRUD - Get {id} / Not Found:
HTTP/1.1 404 Not Found
Without Body

## CRUD - POST / Success:
HTTP/1.1 201 Created
{"id":1,"broker":{"id":1,"name":"NU INVEST"},"ticker":"TICKER","operation":"BUY","operation_date":"2020-12-10 11:45:55","shares":1,"share_price":0.01}

## CRUD - POST / Duplicated:
{"error":"There is already a record for the same userDTO, broker, ticker and operation_date."}

## CRUD - POST / Validation Error:
HTTP/1.1 422 Unprocessable Content
{"broker_id":["The broker id field is required."]}

## CRUD - PUT / Success:
HTTP/1.1 200 OK
{"id":1,"broker":{"id":1,"name":"NU INVEST"},"ticker":"TICKER","operation":"BUY","operation_date":"2020-12-10 11:45:55","shares":2,"share_price":1.01}

## CRUD - PUT / Not Found:
HTTP/1.1 404 Not Found
Without Body

## CRUD - DELETE / Success:
HTTP/1.1 204 No Content
Without Body

## CRUD - DELETE / Not Found:
HTTP/1.1 404 Not Found
Without Body
