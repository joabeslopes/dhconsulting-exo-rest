package br.com.dhconsulting.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import org.exoplatform.services.rest.resource.ResourceContainer;

@Path("/dhsprints")
public class UltimaSprint implements ResourceContainer {
	@GET
    @Path("/ultima")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUltimoProjetoId() {
		
		
		String jsonResult = "{\"id\": null}";
        String sql = "SELECT PROJECT_ID FROM TASK_PROJECTS ORDER BY PROJECT_ID DESC LIMIT 1"; 

        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/exo-jpa_portal");

            try (Connection conn = ds.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                 
                if (rs.next()) {
                    jsonResult = "{\"id\": " + rs.getInt("PROJECT_ID") + "}";
                }
            }
        } catch (Exception e) {
            return "{\"erro\": \"" + e.getMessage() + "\"}";
        }
        return jsonResult;
    }
}
