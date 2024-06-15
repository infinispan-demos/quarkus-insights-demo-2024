## Quarkus Cache baked with Infinispan

```shell
http "localhost:8080/weather?city=Paris"
```

# Change the dependency

```xml
 <dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-infinispan-cache</artifactId>
</dependency>
```

# LocalDate

```java
@ProtoAdapter(LocalDate.class)
public class LocalDateAdapter {
    @ProtoFactory
    LocalDate create(String localDate) {
        return LocalDate.parse(localDate);
    }

    @ProtoField(1)
    String getLocalDate(LocalDate localDate) {
        return localDate.toString();
    }
}
```

```java
@ProtoSchema(includeClasses = LocalDateAdapter.class, schemaPackageName = "quarkus")
public interface WeatherSchema extends GeneratedSchema {
    
}
```

# Test lifespan

```properties
quarkus.cache.infinispan.weather-cache.lifespan=30s
```