Bruno project
=============

Todo:
-----


Dev:
----

>mvn spring-boot:run

On change, the server will restart automatically using https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-devtools.html#using-boot-devtools-restart


OAuth conf:
-----------

in application.yaml:

```
github:
  client:
    clientId: CLIENT_ID_HERE
    clientSecret: CLIENT_SECRET_HERE
    accessTokenUri: https://github.com/login/oauth/access_token
    userAuthorizationUri: https://github.com/login/oauth/authorize
    clientAuthenticationScheme: form
  resource:
    userInfoUri: https://api.github.com/user
google:
  client:
    clientId: CLIENT_ID_HERE
    clientSecret: CLIENT_SECRET_HERE
    accessTokenUri: https://accounts.google.com/o/oauth2/token
    userAuthorizationUri: https://accounts.google.com/o/oauth2/auth
    clientAuthenticationScheme: form
    scope: ['openid', 'email']
  resource:
    userInfoUri: https://www.googleapis.com/plus/v1/people/me/openIdConnect
facebook:
  client:
    clientId: CLIENT_ID_HERE
    clientSecret: CLIENT_SECRET_HERE
    accessTokenUri: https://graph.facebook.com/v2.3/oauth/access_token
    userAuthorizationUri: https://www.facebook.com/dialog/oauth
    tokenName: oauth_token
    authenticationScheme: query
    clientAuthenticationScheme: form
    scope: ['public_profile']
  resource:
    userInfoUri: https://graph.facebook.com/me
```