package org.infinispan.schema;

import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.annotations.ProtoSchema;

@ProtoSchema(includeClasses = { Developer.class },
      schemaFileName = "developers-schema.proto",
      schemaPackageName = "insights")
public interface DeveloperSchema extends GeneratedSchema {
}
