package org.infinispan.schema;

import org.infinispan.api.annotations.indexing.Indexed;
import org.infinispan.api.annotations.indexing.Text;
import org.infinispan.protostream.annotations.Proto;

@Proto
@Indexed
public record Developer( String firstName, String lastName, @Text String project) {
}
