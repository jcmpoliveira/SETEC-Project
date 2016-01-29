package restResources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;


import dataAccess.Tecnico;
//import dataAccess.Box;
import dataAccess.audioData;

public class AudioData_Resource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	@Context
	HttpServletRequest header;
	
	
	@POST
    @Consumes(MediaType.APPLICATION_XML)
	public Response postAudioData(JAXBElement<audioData> jaxbAudioData)
	{
		audioData audio = jaxbAudioData.getValue();        	
    	System.out.println("POST audio ");
    	Response res;         	
    	

    	if(header.getHeader("Authorization") != null) {
        	String authHeader = header.getHeader("Authorization");
        	String [] creds=authHeader.split(" "); 
        	System.out.println(creds[1]);
        	Tecnico aux = new Tecnico(creds[1]);
        	String code = aux.selectCodigoTable();
        	
        	if(code.equalsIgnoreCase(creds[1])) 
        	{        		        		
        			//funcao para gravar som na base de dados
        			audio.insertAudioData();
        			res = Response.created(uriInfo.getAbsolutePath()).build();   	        			
        		
        	}
        	else 
        	{
        		res = Response.status(401).entity("Utilizador ou password errados.").build();
        	}	        	
    	}
    	else 
    	{
    		res = Response.status(401).entity("Sem autorização.").build();
    	}    		
    	return res;
    	
    	
    	
    	
	}
	
	
}
