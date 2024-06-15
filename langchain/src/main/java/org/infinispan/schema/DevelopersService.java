package org.infinispan.schema;

//import io.quarkus.cache.CacheInvalidate;
//import io.quarkus.cache.CacheInvalidateAll;
//import io.quarkus.cache.CacheResult;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class DevelopersService {

   static final Map<String, Developer> REF_DATA = Map.ofEntries(
         new AbstractMap.SimpleEntry("wburns", new Developer("Will", "Burns", "Infinispan")),
         new AbstractMap.SimpleEntry("fax2dev", new Developer("Fabio Massimo", "Ercoli", "Infinispan")),
         new AbstractMap.SimpleEntry("ttarrant", new Developer("Tristan", "Tarrant", "Infinispan"))
   );

   Map<String, Developer> data = new HashMap<>(REF_DATA);

   public void addDeveloper(String nickname, Developer developer) {
      Log.info(developer);
      data.put(nickname, developer);
   }

//   @CacheResult(cacheName = "developers")
   public Developer getDeveloper(String nickname) {
      Log.info("Getting from service call " + nickname);
      return data.get(nickname);
   }

//   @CacheInvalidate(cacheName = "developers")
   public void removeDeveloper(String nickname) {
      Log.info("Remove " + nickname);
      data.remove(nickname);
   }

//   @CacheInvalidateAll(cacheName = "developers" )
   public void removeAll() {
      Log.info("Clear all");
      data.clear();
      data.putAll(REF_DATA);
   }
}