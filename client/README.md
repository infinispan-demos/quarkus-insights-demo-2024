## Infinispan Client with Infinispan 15

Start with :

```bash

http localhost:8080/developers/ttarrant 

```

# New Protostream API

## Annotations based

```java
@Proto
public record Developer( String firstName, String lastName, String project) {
}
```

```java
@ProtoSchema(includeClasses = { Developer.class },
        schemaFileName = "developers-schema.proto",
        schemaPackageName = "insights")
public interface DeveloperSchema extends GeneratedSchema {
}
```

## Programmatically based

```java
@ApplicationScoped
public class ConfigBean {

    @Produces
    MessageMarshaller bookMarshaller() {
        return new BookMarshaller();
    }

    @Produces
    Schema bookSchema() {
        return new Schema.Builder("book.proto")
                .packageName("insights")
                .addMessage("Book")
                .addField(Type.Scalar.STRING, "title", 1)
                .addField(Type.Scalar.STRING, "description", 2)
                .build();
    }
}
```

```java
public class BookMarshaller implements MessageMarshaller<Book> {
    @Override
    public Book readFrom(ProtoStreamReader reader) throws IOException {
        String title = reader.readString("title");
        String description = reader.readString("description");
        return new Book(title, description);
    }

    @Override
    public void writeTo(ProtoStreamWriter writer, Book book) throws IOException {
        writer.writeString("title", book.title());
        writer.writeString("description", book.description());
    }

    @Override
    public Class<? extends Book> getJavaClass() {
        return Book.class;
    }

    @Override
    public String getTypeName() {
        return "insights.Book";
    }
}
```

```json
{
  "_type": "insights.Book",
  "title": "Quarkus 4.0",
  "description" : "The next generation for Java Apps"
}
```

# Named Connections

```properties
quarkus.infinispan-client.devservices.enabled=false
quarkus.infinispan-client.insights.devservices.image-name=infinispan-test/server:main
quarkus.infinispan-client.insights.devservices.port=11222
```

```shell
http localhost:8080/developers/ttarrant
```

```java
@InfinispanClientName("insights")
```

# Dev Services

## Cross Site Replication

## Providing server conf on Dev Services

```properties
quarkus.infinispan-client.devservices.config-files=infinispan-config.xml
```

# Search API

## Search on values

```bash
http 'localhost:8080/developers/search?term=tristan'
```

```java
@Proto
@Indexed
public record Developer(@Text(projectable = true) String firstName,
                        @Text(projectable = true) String lastName,
                        @Text(projectable = true) String project) {
}
```

## Search on Keys

```java

@Proto
@Indexed(keyEntity = "insights.Developer")
public record Project(@Text(projectable = true) String project, @Text(projectable = true) String role) {
}

```

```java
@ProtoSchema(includeClasses = { Developer.class, Project.class },
      schemaFileName = "developers-schema.proto",
      schemaPackageName = "insights")
public interface DeveloperSchema extends GeneratedSchema {
}
```

```java
   @Inject
   @Remote("developers2")
   RemoteCache<Developer, Project> developers2;
```

```java
   @ConfigProperty(name = "load", defaultValue = "false")
   boolean load;

   public void start(@Observes StartupEvent event) {
      if (load) {
         developers.putAll(REF_DATA);
         developers2.putAll(REF_DATA2);
      }
   }
```

```xml
 <distributed-cache name="developers2" owners="2" mode="SYNC" statistics="true">
            <encoding media-type="application/x-protostream"/>
            <indexing enabled="true" storage="filesystem" startup-mode="AUTO" indexing-mode="AUTO">
                <indexed-entities>
                    <indexed-entity>insights.Project</indexed-entity>
                    <indexed-entity>insights.Developer</indexed-entity>
                </indexed-entities>
            </indexing>
        </distributed-cache>
```

```java
public List<ProjectDTO> searchKeysAndValues(String term) {
      Query<Object[]> query = developers2.query("select p.key.firstName,  p.key.lastName, p.project, p.role from insights.Project p where p.project : :t1 or  p.key.firstName : :t2");
      query.setParameter("t1", term);
      query.setParameter("t2", term);
      return query.execute().list().stream().map(r -> new ProjectDTO(r[0] + " " + r[1], r[2] + "", r[3] +"")
      ).collect(Collectors.toList());
}
```


# Mock Infinispan
## Disable dev services for testing

```properties
%test.quarkus.infinispan-client.devservices.enabled=false
```

## Add a Unit test

```java
public class BookService {

    @Inject
    @Remote("books")
    RemoteCache<String, Book> books;

    public String getBookDescriptionById(String id) {
        Book book = books.get(id);
        if (book == null) {
            return "default";
        }

        return book.description();
    }

}
```

```java
@QuarkusTest
public class BookServiceTest {
    @InjectMock
    @Remote("books")
    RemoteCache<String, Book> bookRemoteCache;

    @Inject
    BookService bookService;

    @Test
    public void mockRemoteCache() {
        String bookId = "harry_potter_infinispan";
        String expectedDescription = "Harry, Hermione, and Ron use Infinispan " +
                "to revolutionize data sharing in the wizarding world, " +
                "merging magic with cutting-edge technology.";

        Mockito.when(bookRemoteCache.get(bookId))
                .thenReturn(new Book("Harry Potter and the Infinite Cache", expectedDescription));

        String retrievedDescription = bookService.getBookDescriptionById(bookId);

        assertEquals(expectedDescription, retrievedDescription);
    }
}
```





