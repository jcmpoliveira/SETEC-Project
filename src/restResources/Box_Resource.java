package restResources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import javax.xml.bind.JAXBElement;
//import com.sun.jersey.api.NotFoundException;


import dataAccess.Box;
import dataAccess.Tecnico;

public class Box_Resource {
		@Context
		UriInfo uriInfo;
		@Context
		Request request;
		@Context
		HttpServletRequest header;
		String Box_id;
	
		public Box_Resource(UriInfo uriInfo, Request request, String Box, HttpServletRequest header) {
			this.uriInfo = uriInfo;
			this.request = request;
			this.Box_id = Box;
			this.header= header;
		}
     
	
        @GET
    	@Produces(MediaType.APPLICATION_XML)
    	public Box getBox() {
        	System.out.println("GET Box id=" + Box_id);
        	Box box;
        	
        	if(header.getHeader("Authorization") != null) {
        		
	        	String authHeader = header.getHeader("Authorization");
	        	String [] creds=authHeader.split(" "); 
	        	System.out.println(creds[1]);
	        	Tecnico aux = new Tecnico(creds[1]);
	        	String code = aux.selectCodigoTable();
	        	
	        	if(code.equalsIgnoreCase(creds[1])) {
	        		box = Box.getBoxFromId(Box_id);
	        		if(box != null){
	        			Response.status(200).entity("OK").build();
	        			return box;
	        		}
	        		else {
	        			Response.status(404).entity("Box não existe.").build();
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
        
        
        @POST
        @Consumes(MediaType.APPLICATION_XML)
        public Response postBox(JAXBElement<Box> jaxbBox) {        	        	       
        	Box box = jaxbBox.getValue();        	
        	System.out.println("POST box id " + box.getId());
        	Response res;        	        	        	        	        	        	                       	
        	
        	if(header.getHeader("Authorization") != null) {
	        	String authHeader = header.getHeader("Authorization");
	        	String [] creds=authHeader.split(" "); 
	        	System.out.println(creds[1]);
	        	Tecnico aux = new Tecnico(creds[1]);
	        	String code = aux.selectCodigoTable();
	        	
	        	if(code.equalsIgnoreCase(creds[1])) {
	        		Box temp = Box.getBoxFromId(box.getId());
	        		if(temp == null){	        			
	        			box.insertBox();
	        			res = Response.created(uriInfo.getAbsolutePath()).build();   	        			
	        		}
	        		else {
	        			res = Response.status(400).entity("Box já existe.").build();
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
        
        
        
        @PUT
        @Consumes(MediaType.APPLICATION_XML)
        public Response putBox(JAXBElement<Box> jaxbBox) {
        	
        	Response res;
        	Box box = jaxbBox.getValue();
        	System.out.println("PUT box id=" + box.getId());
        	
        	if(header.getHeader("Authorization") != null)
        	{
	        	String authHeader = header.getHeader("Authorization");
	        	String [] creds=authHeader.split(" "); 
	        	System.out.println(creds[1]);
	        	Tecnico aux = new Tecnico(creds[1]);
	        	String code = aux.selectCodigoTable();
	        	
	        	
	        	if(code.equalsIgnoreCase(creds[1]))
	        	{
	        		Box temp = Box.getBoxFromId(box.getId());
	        		if(temp != null)
	        		{
	        			box.updateBox();
	        			res=Response.created(uriInfo.getAbsolutePath()).build();	        			
	        		}
	        		else
	        		{
	        			res = Response.status(403).entity("Box não existe.").build();
	        		}       		
	        			        		
	        	}
	        	else
	        	{
	        		res = Response.status(401).entity("Utilizador ou password errados.").build();
	        	}        	
        		        		
        	}
        	else
        	{
        		res = Response.status(401).entity("Sem autorização").build();
        	}

        	return res;
        }


}
