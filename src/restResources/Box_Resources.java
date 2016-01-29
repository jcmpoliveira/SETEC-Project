package restResources;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import javax.ws.rs.Path;
import java.util.List;

import dataAccess.Box;



@Path("/mbox")
public class Box_Resources {
		@Context
		UriInfo uriInfo;
		@Context
		Request request;
		@Context 
		HttpServletRequest header;
		
		
	
        @GET
    	@Produces(MediaType.APPLICATION_XML)
    	public List<Box> retriveBoxAsText() 
    	{
        	
        	List<Box> boxes = Box.getAllBoxes();
        	return boxes;
    	   	
        }
       
        @Path("{box_id}")
    	public Box_Resource getBox(@PathParam("box_id") String box_id) 
        {
    		return new Box_Resource(uriInfo, request, box_id, header);
    	}
        
      
}
