package org.infinispan.books;

import io.quarkus.infinispan.client.Remote;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.infinispan.client.hotrod.RemoteCache;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

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