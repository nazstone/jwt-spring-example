# jwt-spring-example

## Login
curl -i http://localhost:8080/auth -d '{"username":"tada", "password": "tada"}' -H 'Content-type: application/json'

curl -i http://localhost:8080/auth -d '{"username":"admin", "password": "admin"}' -H 'Content-type: application/json'

curl -i http://localhost:8080/auth -d '{"username":"patate", "password": "patate"}' -H 'Content-type: application/json'

## Access page
### No role

Curl no auth

curl -i http://localhost:8080/open

curl -i http://localhost:8080/tada

### Role patate
Curl with role patate

curl -i http://localhost:8080/patate

curl -i http://localhost:8080/patate -H "Authorization: Bearer XX.YY.ZZ"

### Role admin
Curl with role admin

curl -i http://localhost:8080/superadmin
curl -i http://localhost:8080/superadmin -H "Authorization: Bearer XX.YY.ZZ"

### Refresh token
Refresh token if expired
curl -i http://localhost:8080/auth/refresh -H "Authorization: Bearer XX.YY.ZZ"