package org.infinispan.books;

import io.quarkus.infinispan.client.Remote;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.infinispan.client.hotrod.RemoteCache;

@ApplicationScoped
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
