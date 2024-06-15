package org.infinispan;

import io.quarkus.infinispan.client.Remote;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.commons.api.query.Query;
import org.infinispan.schema.Developer;

import java.util.List;


@Path("/search")
public class QuarkusSearch {

   @GET
   @Produces(MediaType.TEXT_PLAIN)
   public String hello() {
      return "Hello Search";
   }

   @Inject
   @Remote("developers")
   RemoteCache<String, Developer> developers;

   @GET
   @Produces(MediaType.TEXT_PLAIN)
   @Path("/{nickname}")
   public List<Developer> getDeveloper(@PathParam("nickname") String nickname) {
      Query<Developer> query = developers.query("from insights.Developer where firstName=:p1");
      query.setParameter("p1", nickname);
      return query.execute().list();
   }


   // Do query by key
}
