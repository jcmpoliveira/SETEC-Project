package restResources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import dataAccess.Permit;
import dataAccess.Tecnico;
//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
//import com.sun.jersey.api.NotFoundException;

public class Permit_Resource {
		@Context
		UriInfo uriInfo;
		@Context
		Request request;
		@Context
		HttpServletRequest header;
		int permit_id;
	
		public Permit_Resource(UriInfo uriInfo, Request request, int permit, HttpServletRequest header) {
			this.uriInfo = uriInfo;
			this.request = request;
			this.permit_id = permit;
			this.header= header;
		}
     
	
        @GET
    	@Produces(MediaType.APPLICATION_XML)
    	public Permit getPermit() {
        	System.out.println("GET Permit id=" + permit_id);
        	
        	if(header.getHeader("Authorization") != null) {
        		
	        	String authHeader = header.getHeader("Authorization");
	        	String [] creds=authHeader.split(" "); 
	        	System.out.println(creds[1]);
	        	Tecnico aux = new Tecnico(creds[1]);
	        	String code = aux.selectCodigoTable();
	        	
	        	if(code.equalsIgnoreCase(creds[1])) {
	        		Permit permit = Permit.getPermitFromId(permit_id);
	        		if(permit != null){
	        			Response.status(200).entity("OK").build();
	        			return permit;
	        		}
	        		else {
	        			Response.status(404).entity("Permit não existe.").build();
	        		}
	        	}
	        	else {
	        		Response.status(401).entity("Utilizador ou password errados.").build();
	        	}	        	
        	}
        	else {
        		Response.status(401).entity("Sem autorização.").build();
        	}    		
        	return null;
        }                
        
        @PUT
        @Consumes(MediaType.APPLICATION_XML)
        public Response putBox(JAXBElement<Permit> jaxbPermit) {
        	
        	Response res;
        	Permit permit = jaxbPermit.getValue();
        	System.out.println("PUT Permit id =" + permit.getId());
        	
        	if(header.getHeader("Authorization") != null)
        	{
	        	String authHeader = header.getHeader("Authorization");
	        	String [] creds=authHeader.split(" "); 
	        	System.out.println(creds[1]);
	        	Tecnico aux = new Tecnico(creds[1]);
	        	String code = aux.selectCodigoTable();	        	
	        	
	        	if(code.equalsIgnoreCase(creds[1]))
	        	{
	        		Permit temp = Permit.getPermitFromId(permit.getId());
	        		if(temp != null)
	        		{
	        			permit.updatePermit();
	        			res=Response.created(uriInfo.getAbsolutePath()).build();	        			
	        		}
	        		else {
	        			res = Response.status(403).entity("Permit não existe.").build();
	        		}       			        			        		
	        	}
	        	else {
	        		res = Response.status(401).entity("Utilizador ou password errados.").build();
	        	}        	        		        		
        	}
        	else {
        		res = Response.status(401).entity("Sem autorização").build();
        	}
        	return res;
        }

        @DELETE
        public Response deletePermit() {
        	
        	Response res;
        	System.out.println("DELETE permit id=" + permit_id);
        	if(header.getHeader("Authorization") != null)
        	{
	        	String authHeader = header.getHeader("Authorization");
	        	String [] creds=authHeader.split(" "); 
	        	System.out.println(creds[1]);
	        	Tecnico aux = new Tecnico(creds[1]);
	        	String code = aux.selectCodigoTable();
	        		        	
	        	if(code.equalsIgnoreCase(creds[1]))
	        	{
	        		Permit temp = Permit.getPermitFromId(permit_id);
	        		if(temp != null)
	        		{
	        			Permit.deletePermit(permit_id);
	        			res=Response.ok().build();	        			
	        		}
	        		else {
	        			res = Response.status(403).entity("Permit não existe.").build();
	        		}       			        			        		
	        	}
	        	else {
	        		res = Response.status(401).entity("Utilizador ou password errados.").build();
	        	}        	        		        		
        	}
        	else {
        		res = Response.status(401).entity("Sem autorização").build();
        	}
        	
        	return res;
        }
}
