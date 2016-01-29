package restResources;

//import dataAccess.Box;
import dataAccess.Configuracao;
import dataAccess.Tecnico;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.GET;
//import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;



@Path("/conf")
public class Configuracao_Resource {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	@Context
	HttpServletRequest header;
	
	
	//???????????????????????????
	public Configuracao_Resource(UriInfo uriInfo, Request request,  HttpServletRequest header) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.header= header;
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Configuracao getConfiguracao()
	{
		System.out.println("GET Configuracao");
		Configuracao config;
		
		
    	if(header.getHeader("Authorization") != null) {
    		
        	String authHeader = header.getHeader("Authorization");
        	String [] creds=authHeader.split(" "); 
        	System.out.println(creds[1]);
        	Tecnico aux = new Tecnico(creds[1]);
        	String code = aux.selectCodigoTable();
        	
        	if(code.equalsIgnoreCase(creds[1])) 
        	{
        		//definir funcao que vai a base de dados buscar a configuracao!!!!!
        		//config = getConfig();
        		
        		config = new Configuracao(10,60,"10:00|16:00|21:00",5,7); //configuracao para teste
        		
        		Response.status(200).entity("OK").build();
        		return config;
        		        		
        	}
        	else 
        	{
        		Response.status(401).entity("Utilizador ou password errados.").build();
        	}	        	
    	}
    	else {
    		Response.status(401).entity("Sem autorização.").build();
    	}    		
    	return null;		
		
	}
	

}
