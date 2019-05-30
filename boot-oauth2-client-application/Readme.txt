1.Browser visit below url to test Spring Security OAuth2 Client
http://127.0.0.1:8090/hello

2.Stop Spring Security OAuth2 Client Application. Browser visit below url to get the Authorization Code 
http://localhost:8080/oauth/authorize?response_type=code&client_id=myclient&redirect_uri=http://127.0.0.1:8090/login/oauth2/code/callback&scope=resource-server-read
return:
http://127.0.0.1:8090/login/oauth2/code/callback?code=ed3tkd

3.Use Authorization Code to request Access Token, you also can use Postman to send the request
http://localhost:8080/oauth/token
|----------------------------- Http Header ----------------------------- 
| POST /oauth/token HTTP/1.1
| Host: localhost:8080
| Authorization: Basic bXljbGllbnQ6bXlzZWNyZXQ=
| Content-Type: application/x-www-form-urlencoded
| Cache-Control: no-cache
|
| grant_type=authorization_code&code=ed3tkd&redirect_uri=http%3A%2F%2F127.0.0.1%3A8090%2Flogin%2Foauth2%2Fcode%2Fcallback&client_id=myclient
|-----------------------------------------------------------------------

4.Use Access Token to request resource
http://localhost:9090/user
|----------------------------- Http Header -----------------------------
| GET /test HTTP/1.1
| Host: localhost:9090
| Authorization: bearer cc5df29d-5dd7-4566-8310-119742210dbe
| Cache-Control: no-cache
|-----------------------------------------------------------------------

5.(option) Check Access Token
http://localhost:8080/oauth/check_token
|----------------------------- Http Header -----------------------------
| POST /oauth/check_token HTTP/1.1
| Host: localhost:8080
| Authorization: Basic UmVzb3VyY2VTZXJ2ZXI6UmVzb3VyY2VTZXJ2ZXJTZWNyZXQ=
| Content-Type: application/x-www-form-urlencoded
| Cache-Control: no-cache
| 
| token=cc5df29d-5dd7-4566-8310-119742210dbe
|-----------------------------------------------------------------------


