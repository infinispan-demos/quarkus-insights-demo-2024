package org.infinispan.developers;

import org.infinispan.api.annotations.indexing.Indexed;
import org.infinispan.api.annotations.indexing.Text;
import org.infinispan.protostream.annotations.Proto;

@Proto
@Indexed(keyEntity = "insights.Developer")
public record Project(@Text(projectable = true) String project,
                      @Text(projectable = true) String role) {
}
