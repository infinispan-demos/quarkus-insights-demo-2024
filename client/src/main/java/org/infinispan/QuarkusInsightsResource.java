package org.infinispan;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.infinispan.developers.Developer;
import org.infinispan.developers.DevelopersService;
import org.infinispan.developers.Project;
import org.jboss.resteasy.reactive.RestQuery;

import java.util.List;


@Path("/developers")
public class QuarkusInsightsResource {

   @GET
   @Produces(MediaType.TEXT_PLAIN)
   public String hello() {
      return "Hello Quarkus";
   }

   @Inject
   DevelopersService developersService;

   @GET
   @Produces(MediaType.TEXT_PLAIN)
   @Path("/{nickname}")
   public Response getDeveloper(@PathParam("nickname") String nickname) {
      Developer developer = developersService.getDeveloper(nickname);
      if (developer == null) {
         return Response.status(Response.Status.NOT_FOUND).build();
      }
      return Response.ok(developersService.getDeveloper(nickname)).build();
   }

   @GET
   @Produces(MediaType.TEXT_PLAIN)
   @Path("/search/")
   public List<Developer> findDeveloperOnValues(@RestQuery("term") String term) {
      return developersService.searchOnValues(term);
   }

   @GET
   @Produces(MediaType.TEXT_PLAIN)
   @Path("/search2/")
   public List<ProjectDTO> findProjets(@RestQuery("term") String term) {
      return developersService.searchKeysAndValues(term);
   }

   @DELETE
   @Produces(MediaType.TEXT_PLAIN)
   @Path("/{nickname}")
   public Response removeDeveloper(@PathParam("nickname") String nickname) {
      developersService.removeDeveloper(nickname);
      return Response.ok().build();
   }
}
