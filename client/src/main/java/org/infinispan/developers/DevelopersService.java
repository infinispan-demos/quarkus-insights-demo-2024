package org.infinispan.developers;

import io.quarkus.infinispan.client.Remote;
import io.quarkus.logging.Log;
import io.quarkus.runtime.LaunchMode;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.infinispan.client.hotrod.RemoteCache;

import java.util.AbstractMap;
import java.util.Map;

@ApplicationScoped
public class DevelopersService {

   static final Map<String, Developer> REF_DATA = Map.ofEntries(
         new AbstractMap.SimpleEntry("wburns", new Developer("Will", "Burns", "Infinispan")),
         new AbstractMap.SimpleEntry("fax2dev", new Developer("Fabio Massimo", "Ercoli", "Infinispan")),
         new AbstractMap.SimpleEntry("ttarrant", new Developer("Tristan", "Tarrant", "Infinispan"))
   );

   @Inject
   @Remote("developers")
   RemoteCache<String, Developer> developers;

   public void start(@Observes StartupEvent event) {
      if (ProfileManager.getLaunchMode().equals(LaunchMode.DEVELOPMENT)) {
         developers.putAll(REF_DATA);
      }
   }

   public void addDeveloper(String nickname, Developer developer) {
      Log.info(developer);
      developers.put(nickname, developer);
   }

   public Developer getDeveloper(String nickname) {
      Log.info("Getting from service call " + nickname);
      return developers.get(nickname);
   }

   public void removeDeveloper(String nickname) {
      Log.info("Remove " + nickname);
      developers.remove(nickname);
   }

   public void removeAll() {
      Log.info("Clear all");
      developers.clear();
   }
}
