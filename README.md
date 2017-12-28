# kc-resource
Keycloak, Resource Server, Resource Client collaborate examples

# Quick start
First read the following.
http://blog.yo1000.com/2017/12/18/keycloak-example-resource-server-client.html

Next set up Keycloak, get and set `keycloak.json`.

```console
$ cp keycloak.json kc-resource-client/src/main/webapp/WEB-INF/keycloak.json
```

When ready, Packaging and Run!

```console
$ ./mvnw clean package
$ java -jar ./kc-resource-server/target/kc-resource-server-1.0.0-SNAPSHOT.jar
$ java -jar ./kc-resource-client/target/kc-resource-client-1.0.0-SNAPSHOT.jar
```

# Endpoint assumptions

- Keycloak: http://127.0.0.1/auth
- Resource Server: http://localhost:18080/
- Resource Client: http://localhost:28080/
  - Admin Role: http://localhost:28080/kc/resource/client/admin
  - User Role: http://localhost:28080/kc/resource/client/user

# Try testing one of the submodules

```
$ ./mvnw clean test -pl kc-resource-server
```
