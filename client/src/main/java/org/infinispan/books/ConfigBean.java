package org.infinispan.books;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.infinispan.protostream.MessageMarshaller;
import org.infinispan.protostream.schema.Schema;
import org.infinispan.protostream.schema.Type;

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
