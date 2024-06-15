package org.infinispan.books;

import org.infinispan.protostream.MessageMarshaller;

import java.io.IOException;

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
