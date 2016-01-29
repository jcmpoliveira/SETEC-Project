package restResources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;
import javax.ws.rs.POST;

import measuresProcessing.Measurement;
//import javax.ws.rs.Produces;

//import dataAccess.Box;
import dataAccess.Tecnico;

@Path("/measurement")
public class Medicao_Resource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	@Context
	HttpServletRequest header;

	public Medicao_Resource(UriInfo uriInfo, Request request, HttpServletRequest header) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.header = header;
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response postMedicao(JAXBElement<Measurement> jaxbMedicao) {
		Measurement medicao = jaxbMedicao.getValue();
		System.out.println("POST medicao ");
		Response res;

		if (header.getHeader("Authorization") != null) {
			String authHeader = header.getHeader("Authorization");
			String[] creds = authHeader.split(" ");
			System.out.println(creds[1]);
			Tecnico aux = new Tecnico(creds[1]);
			String code = aux.selectCodigoTable();

			if (code.equalsIgnoreCase(creds[1])) {
				new Measurement (medicao.getmBoxId(), medicao.getMeasurementTimestamp(), medicao.getValues()).start();
				res = Response.created(uriInfo.getAbsolutePath()).build();

			} else {
				res = Response.status(401)
						.entity("Utilizador ou password errados.").build();
			}
		} else {
			res = Response.status(401).entity("Sem autorização.").build();
		}

		return res;

	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Measurement getMeasurement() {

		Measurement measu;
		float[] vals = new float[3];
		vals[0] = (float) 40.2;
		vals[1] = (float) 50.3;
		vals[2] = (float) 45.9;
		measu = new Measurement("12345", "data qualquer", vals);

		return measu;
	}

}
