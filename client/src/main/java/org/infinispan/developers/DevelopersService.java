package org.infinispan.developers;

import io.quarkus.infinispan.client.Remote;
import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.infinispan.ProjectDTO;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.commons.api.query.Query;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class DevelopersService {

   static final Map<String, Developer> REF_DATA = Map.ofEntries(
         new SimpleEntry("wburns", new Developer("Will", "Burns", "Infinispan")),
         new SimpleEntry("fax2dev", new Developer("Fabio Massimo", "Ercoli", "Infinispan")),
         new SimpleEntry("ttarrant", new Developer("Tristan", "Tarrant", "Infinispan"))
   );

   static final Map<Developer, Project> REF_DATA2 = Map.ofEntries(
           new SimpleEntry(new Developer("Will", "Burns", "Infinispan"), new Project("Infinispan", "Core")),
           new SimpleEntry(new Developer("Fabio Massimo", "Ercoli", "Infinispan"), new Project("Infinispan", "Search")),
           new SimpleEntry(new Developer("Tristan", "Tarrant", "Infinispan"), new Project("Infinispan", "Lead"))
   );

   @Inject
   @Remote("developers")
   RemoteCache<String, Developer> developers;

   @Inject
   @Remote("developers2")
   RemoteCache<Developer, Project> developers2;

   @ConfigProperty(name = "load", defaultValue = "false")
   boolean load;

   public Developer getDeveloper(String nickname) {
      Log.info("Getting from service call " + nickname);
      return developers.get(nickname);
   }

   public List<Developer> searchOnValues(String term) {
      Query<Developer> query = developers.query("from insights.Developer d where d.firstName: :t1 or  d.lastName: :t2");
      query.setParameter("t1", term);
      query.setParameter("t2", term);
      return query.execute().list();
   }

   public List<Project> pepepe(String term) {
      Query<Project> query = developers2.query("from insights.Project p where p.project : :t1 or  p.key.firstName : :t2");
      query.setParameter("t1", term);
      query.setParameter("t2", term);
      return query.execute().list();
   }

   public List<ProjectDTO> searchKeysAndValues(String term) {
      Query<Object[]> query = developers2.query("select p.key.firstName,  p.key.lastName, p.project, p.role from insights.Project p where p.project : :t1 or  p.key.firstName : :t2");
      query.setParameter("t1", term);
      query.setParameter("t2", term);
      return query.execute().list().stream().map(r -> new ProjectDTO(r[0] + " " + r[1], r[2] + "", r[3] +"")
      ).collect(Collectors.toList());
   }

   public void addDeveloper(String nickname, Developer developer) {
      Log.info(developer);
      developers.put(nickname, developer);
   }

   public void removeDeveloper(String nickname) {
      Log.info("Remove " + nickname);
      developers.remove(nickname);
   }

   public void removeAll() {
      Log.info("Clear all");
      developers.clear();
   }

   public void start(@Observes StartupEvent event) {
      if (load) {
         developers.putAll(REF_DATA);
         developers2.putAll(REF_DATA2);
      }
   }
}
