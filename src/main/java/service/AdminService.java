package service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import facade.AdminFacade;
import model.Admin;

@Path("/admins")
public class AdminService {
	
    @EJB 
    AdminFacade adminFacadeEJB;
	
    Logger logger = Logger.getLogger(AdminService.class.getName());
	
    @GET
    @Produces({"application/xml", "application/json"})
    public List<Admin> findAll(){
    	return adminFacadeEJB.findAll();
    }
	
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Admin find(@PathParam("id") Integer id) {
        return adminFacadeEJB.find(id);
    }
	
    @GET
    @Path("verify/{username}/{password}")
    @Produces({"application/xml", "application/json"})
    /*
    @Return 
        302 - Found - Usuario valido
        404 - Not Found - Usuario no valido
    */
    public Response verify(@PathParam("username") String username, @PathParam("password") String password){
    	List<Admin> admins = adminFacadeEJB.findAll();
            for (Admin admin : admins) {
                if(admin.verify(username, password)){
                    return Response.status(Response.Status.FOUND).build();
                }
            }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
	
    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Admin entity) {
        adminFacadeEJB.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Admin entity) {
    	entity.setId(id.intValue());
        adminFacadeEJB.edit(entity);
    }
	
    @DELETE
    @Path("{id}")
    public void remove(Admin entity) {
        adminFacadeEJB.remove(entity);
    }
}
