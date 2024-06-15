## Infinispan Client with Infinispan 15

# New Protostream API

## Annotations based
```java
@Proto
@Indexed
public record Developer( String firstName, String lastName, @Text String project) {
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
# Search API

## Indirection removal

## Continuous Query

## Search on Keys


# Mock Infinispan
## Disable dev services for testing

```properties
%test.quarkus.infinispan-client.devservices.enabled=false
```

## Add a Unit test

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

# Named Connections
Having a named connections, having 2 connections.


# Dev Services 

## Cross Site Replication

```properties
quarkus.infinispan-client.devservices.site=LOL
quarkus.infinispan-client.devservices.mcast-port=46656
```

## Providing server conf on Dev Services

```properties
quarkus.infinispan-client.devservices.config-files=infinispan-config.xml
```



