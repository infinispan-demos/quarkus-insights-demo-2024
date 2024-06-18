package org.cache;

import org.infinispan.protostream.annotations.ProtoAdapter;
import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;

import java.time.LocalDate;

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
