package org.infinispan.developers;

import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;

@AutoProtoSchemaBuilder(includeClasses = { Developer.class },
      schemaFileName = "developers-schema.proto",
      schemaPackageName = "insights")
public interface DeveloperSchema extends GeneratedSchema {
}
