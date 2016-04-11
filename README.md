Bruno project
=============

Todo:
-----


Dev:
----

> mvn spring-boot:run

On change, the server will restart automatically using https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-devtools.html#using-boot-devtools-restart


OAuth conf:
-----------

As an environment variable, add (on *nix):

```
BRUNO_OAUTH_GITHUB="{\"clientId\" : \"....\", \"clientSecret\" : \"...\"}"
export BRUNO_OAUTH_GITHUB

BRUNO_OAUTH_GOOGLE="{\"clientId\" : \"....\", \"clientSecret\" : \"...\"}"
export BRUNO_OAUTH_GOOGLE

BRUNO_OAUTH_FACEBOOK="{\"clientId\" : \"....\", \"clientSecret\" : \"...\"}"
export BRUNO_OAUTH_FACEBOOK
```

with windows:

```
set BRUNO_OAUTH_GITHUB={"clientId":"...", "clientSecret" : "..."}
set BRUNO_OAUTH_GOOGLE={"clientId":"...", "clientSecret" : "..."}
set BRUNO_OAUTH_FACEBOOK={"clientId":"...", "clientSecret" : "..."}
``

Admin users:
------------

As an environment variable, add (it's a list, so more than one user can be admin).
Available providers are:

 - google, where username is the _email_
 - github, where the username is the github username
 - facebook, where the username is the user _ID_
 
On *nix

```
BRUNO_ADMIN_USERS="[{\"provider\" : \"google\", \"username\": \"...\"}]"
export BRUNO_ADMIN_USERS
```

With windows:

```
set BRUNO_ADMIN_USERS=[{"provider" : "google", "username" : "..."}]
```

Deploy on CF:
-------------

If not logged:

>cf login -a https://api.lyra-836.appcloud.swisscom.com -u USERNAME

> mvn clean install
> cf push bruno -p target/bruno-1.0-SNAPSHOT.jar