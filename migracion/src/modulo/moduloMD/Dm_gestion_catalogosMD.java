package modulo.moduloMD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

public class Dm_gestion_catalogosMD {
	private Connection con;
	
	public Dm_gestion_catalogosMD() {
		// TODO Auto-generated constructor stub
	}
	
	public JSONArray qryCatalogoLocalidad(int inireg, int noreg){ 
		JSONArray jsonArray = new JSONArray();
		JSONObject object = null;
		String sqlTxt = "SELECT id_nom_localidad, nom_localidad, clave_localidad, cat.id_nom_ambito_localidad, nom_ambito_localidad, " +
				"cat.id_nom_municipio, CONCAT(nom_municipio,' ( ' ,clave_municipio,' )') AS nom_municipio, cat.clave_entidad," +
				"latitud, longitud, altitud" +
				" FROM portalin_exptec.cg_nom_localidad  cat" +
				" LEFT JOIN portalin_exptec.cg_nom_municipio rel ON rel.id_nom_municipio = cat.id_nom_municipio" +
				" LEFT JOIN portalin_exptec.cg_nom_ambito_localidad sub ON sub.id_nom_ambito_localidad = cat.id_nom_ambito_localidad" +
				" ORDER BY cat.id_nom_municipio,clave_localidad,nom_localidad " +
				" LIMIT "+inireg+", "+noreg;
		//msg( "sqlTxt:" +sqlTxt);
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlTxt);
	        while(rs.next()){
	        	object = new JSONObject();
	        	object.put("id_nom_localidad",rs.getInt("id_nom_localidad"));
	        	object.put("nom_localidad",rs.getString("nom_localidad"));
	        	object.put("clave_localidad",rs.getString("clave_localidad"));
	        	object.put("id_nom_ambito_localidad",rs.getInt("id_nom_ambito_localidad"));
	        	object.put("nom_ambito_localidad",rs.getString("nom_ambito_localidad"));
	        	object.put("id_nom_municipio",rs.getInt("id_nom_municipio"));
	        	object.put("nom_municipio",rs.getString("nom_municipio"));
	        	object.put("clave_entidad",rs.getInt("clave_entidad"));
	        	object.put("latitud",rs.getDouble("latitud"));
	        	object.put("longitud",rs.getDouble("longitud"));
	        	object.put("altitud",rs.getInt("altitud"));
	        	jsonArray.put(object);
		    	}
		    stmt.close();
			
		 }
	      catch(Exception e) {msg("Dm_gestion_catalogosMD qryCatalogoLocalidad error:"+ e.getMessage());}  

		return jsonArray;
	}
	
	public void setCon(Connection con) {
		this.con = con;
	}

	private void msg(String text){System.out.println(text); 	}

}
