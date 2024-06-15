## Infinispan Client Hot Replacement Redis

```bash
http  localhost:8080/increments
http post localhost:8080/increments key="e1" value=23
http post localhost:8080/increments key="e2" value=18
http  localhost:8080/increments
http  localhost:8080/increments/e2
http put "localhost:8080/increments/e2?value=2"
http delete localhost:8080/increments/e1
http  localhost:8080/increments
```

To use Infinispan:

* Run the server locally

```shell
podman run -it -p 11222:11222 -e USER="admin" -e PASS="password" quay.io/infinispan-test/server:main
```

* Change the config

```properties
quarkus.redis.devservices.enabled=false
quarkus.redis.hosts=redis://admin:password@localhost:11222
```

Restart