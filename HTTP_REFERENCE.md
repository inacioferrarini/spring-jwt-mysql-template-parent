# About

## Register / Success Response:
HTTP/1.1 201 Created
{"name":"Inacio","email":"inacio2@email.com","token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiYWE5NmM4ZTRmZTYzZjI3OTAwNWM2YzdhN2IzMDNjZTU5NDAyYmViY2UzOWQ1NTliY2Q0MDEwYzU0M2NmMGZmZDNiM2IwMWM3ZmIyMDZhODEiLCJpYXQiOjE2NTk1MTU1NjQuNjg1OTA5LCJuYmYiOjE2NTk1MTU1NjQuNjg1OTExLCJleHAiOjE2OTEwNTE1NjQuNjU2MDU0LCJzdWIiOiI4Iiwic2NvcGVzIjpbXX0.fdTA8Dm53Gfs_M_uyCW-N6OyCtH4dvtInPNcbgFYYl59WOvTPA4z2zZNIWYLBgScz7h4j9Kwznn5uUbvQvlmRbQ7pPIMHQfK1SM0BPUqDRxjhk0Jg-NUwJ9CNS3AH37g7uc26mcdMnEzyzaKhpaySRKKqyQkm4ba3NqXSkeoTyRWao_hk7XUERReu16ci_HFpW4v5VZq_k-JHamr9CxtfBTiV7VuXCffrBAeaxJB90VxokBo2QrL6HM2Pl_SdrxNmD2zvCdTV8ZZvplWlHNGDLfEKkidwviKPSi5yLwsqzth8PeG3jvO26t-wzSmZGTZClX7J-Y4JWugb5cXdcWlflelamJ6XXPsBR7r2bwPGTo-9386-m9tsymNIP97s3Em6mqHrbTewvibmpE8JiD6JxooDg6tZHQwapHLF78aQHLTMyvO7FZ3l0lNRwWntQtUSjwsGyLdoXSLIhyn01huUnUVDjm1rhAJGttiPf3F9AmwJ4hELkOP0Zvc1VVqqRYxtmdjyXun5KqJCR4sQYF9sbB2_B3zgGYC0lE4kyCDwBUTFelSd32b1dmMWd7_8DbbCONdOZBfgeoAf-pd_WVWY6gYkyXCIocNfI3kvkdArO6Pe88kb81HbABM16X4X99jOWR6vxDLodvWpdfzuwo-C-ZN8YkcbO8nLUuPraUB8ok"}

## Register / Already Exists:
HTTP/1.1 409 Conflict
{"error":"Email is already being used."}

## Login / Success Response:
HTTP/1.1 200 OK
{"email":"inacio@email.com","token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiODI4ZDBkZTc5ZmU3N2VkNzg0OWViMDE1Y2E2YTQ1MzIyNWM5MDQ3NmY3ZDIyN2RiODNjNTdlMDA2ZWQwYTgyMDlmNDBhNThlOGY1YjAxMGUiLCJpYXQiOjE2NTk1MTU2OTAuNzg3NjgyLCJuYmYiOjE2NTk1MTU2OTAuNzg3Njg1LCJleHAiOjE2OTEwNTE2OTAuNzc4ODM3LCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.OnzYkBenGxAFywu5lx_VbEl6e2YzM4djdrF5i4AMBCmAqoBoeVoGPG4Z5SCSzDzBtzlgu2dDzOdP7-UwU75isr0Wl_HU-dzASvIwCtNJSmJQNb-AKVXtcj-5jfjtExTtvHvJWCknZBzrEE15mQ59h9EP-oyA1DK2HqCWc6DFdpWNCXkCgKm277JlyeogiOg7E0ldivUWziXLf1-OsCdsVzqgh_uwCdLgsqPx6dbDY1I3ZZ4mYZi7-kRd0-13XytdgeZy5VrFQvcNcJJjrp0OzhyUb6pCvJQnGJIDpUw_-hwPZIUNZExPO6ANIyIXcJZtiyI6kDTQfT3joPZzHHVsJj0TN7t0urvri7EfPnogIB0v79TMG-ZDbS-F0n45aLG4skT2B3b8SBTjldt064mwXh7w0FNiOqQk10oYO4bjClS6NAkL_v-O2Ibub-gZ6OEt26mBr3gaaLNmWCw-8-HoUF7DXsWElJkqhsjfUbvbUOiAK8LxY32yvhaYIpO0Fun-fttYTXn_qqiJftt1beQg8xt60PcMfM9GxDdNPKwzwAlkm01hl1k5msloChV3alA9t4FUCHdck7vLOCFX5TINfwVE_3uB-0XenTbfMbho7G2EIzd4tjHt9spsXpXWd-b5_l9HsuX6SoQWF2_INgJNnmdd66kq2BMefHzd1k8vzGE"}

## Login / Credentials Error:
HTTP/1.1 401 Unauthorized
No request body

## Error: Protected Request without Authorization
HTTP/1.1 401 Unauthorized
{"message":"Unauthenticated."}

## CRUD - All / Success:
HTTP/1.1 200 OK
[{"id":1,"broker":{"id":1,"name":"NU INVEST"},"ticker":"MCCI11","operation":"BUY","operation_date":"2020-12-10 11:45:55","shares":1,"share_price":0.01}]

## CRUD - All / Empty Result Success:
HTTP/1.1 200 OK
[]

## CRUD - Get {id} / Success:
HTTP/1.1 200 OK
{"id":1,"broker":{"id":1,"name":"NU INVEST"},"ticker":"MCCI11","operation":"BUY","operation_date":"2020-12-10 11:45:55","shares":1,"share_price":0.01}

## CRUD - Get {id} / Not Found:
HTTP/1.1 404 Not Found
No Body

## CRUD - POST / Success:
HTTP/1.1 201 Created
{"id":1,"broker":{"id":1,"name":"NU INVEST"},"ticker":"MCCI11","operation":"BUY","operation_date":"2020-12-10 11:45:55","shares":1,"share_price":0.01}

## CRUD - POST / Duplicated:
{"error":"There is already a record for the same userDTO, broker, ticker and operation_date."}

## CRUD - POST / Validation Error:
HTTP/1.1 422 Unprocessable Content
{"broker_id":["The broker id field is required."]}

## CRUD - PUT / Success:
HTTP/1.1 200 OK
{"id":1,"broker":{"id":1,"name":"NU INVEST"},"ticker":"MCCI11","operation":"BUY","operation_date":"2020-12-10 11:45:55","shares":2,"share_price":1.01}

## CRUD - PUT / Not Found:
HTTP/1.1 404 Not Found
No Body

## CRUD - DELETE / Success:
HTTP/1.1 204 No Content
No Body

## CRUD - DELETE / Not Found:
HTTP/1.1 404 Not Found
No Body
