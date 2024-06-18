package org.cache;

import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.annotations.ProtoSchema;

@ProtoSchema(includeClasses = LocalDateAdapter.class, schemaPackageName = "quarkus")
public interface Schema extends GeneratedSchema {

}
