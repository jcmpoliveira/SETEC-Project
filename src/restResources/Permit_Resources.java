package restResources;

import java.util.List;

import dataAccess.Permit;
import dataAccess.Tecnico;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.GET;
//import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;
import javax.xml.bind.JAXBElement;



@Path("/permit")
public class Permit_Resources {
		@Context
		UriInfo uriInfo;
		@Context
		Request request;
		@Context 
		HttpServletRequest header;
     				
        @GET
    	@Produces(MediaType.APPLICATION_XML)
    	public List<Permit> retrivePermits() {        	
        	List<Permit> permits = Permit.getAllPermits();
        	return permits;
    	   	
        }
   
        @POST
        @Consumes(MediaType.APPLICATION_XML)
        public Response postPermit(JAXBElement<Permit> jaxbPermit) {        	        	       
        	Permit permit = jaxbPermit.getValue();        	
        	System.out.println("POST permit id " + permit.getId());
        	Response res;        	        	        	        	        	        	                       	
        	
        	if(header.getHeader("Authorization") != null) {
	        	String authHeader = header.getHeader("Authorization");
	        	String [] creds=authHeader.split(" "); 
	        	System.out.println(creds[1]);
	        	Tecnico aux = new Tecnico(creds[1]);
	        	String code = aux.selectCodigoTable();
	        	
	        	if(code.equalsIgnoreCase(creds[1])) {
	        		Permit temp = Permit.getPermitFromId(permit.getId());
	        		if(temp == null){	        			
	        			permit.insertPermit();
	        			res = Response.created(uriInfo.getAbsolutePath()).build();   	        			
	        		}
	        		else {
	        			res = Response.status(400).entity("Permit já existe.").build();
	        		}
	        	}
	        	else {
	        		res = Response.status(401).entity("Utilizador ou password errados.").build();
	        	}	        	
        	}
        	else {
        		res = Response.status(401).entity("Sem autorização.").build();
        	}    		
        	return res;
        }
        
        
        @Path("{permit_id}")
    	public Permit_Resource getPermit(@PathParam("permit_id") int permit_id) 
        {
    		return new Permit_Resource(uriInfo, request, permit_id, header);
    	}


        
      
}