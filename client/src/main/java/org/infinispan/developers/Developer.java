package org.infinispan.developers;

import org.infinispan.api.annotations.indexing.Indexed;
import org.infinispan.api.annotations.indexing.Text;
import org.infinispan.protostream.annotations.Proto;

@Proto
@Indexed
public record Developer(@Text String firstName,
                        @Text String lastName,
                        @Text String project) {
}
