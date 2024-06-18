package org.infinispan.developers;

import org.infinispan.api.annotations.indexing.Indexed;
import org.infinispan.api.annotations.indexing.Keyword;
import org.infinispan.api.annotations.indexing.Text;
import org.infinispan.protostream.annotations.Proto;

@Indexed
@Proto
public record Developer(@Text(projectable = true) String firstName,
                        @Text(projectable = true) String lastName,
                        @Text(projectable = true) String project) {
}

