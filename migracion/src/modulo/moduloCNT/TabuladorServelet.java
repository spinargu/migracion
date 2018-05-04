package modulo.moduloCNT;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import modulo_pool.ObjectRegistry;
import modulo_pool.Pool;


public class TabuladorServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected Connection con = null;   


    
    public TabuladorServelet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		
		JSONArray jsonArray = sendCataLocalidades(request);
		response.setContentType("application/json");
		response.getWriter().print(jsonArray);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private JSONArray sendData(HttpServletRequest request){
		JSONArray jsonArray = new JSONArray();
		JSONObject object = null;
		try {
		object = new JSONObject();
		object.put("id",1);
		object.put("name","billy Bob");
		object.put("age",12);
		object.put("gender","male");
		object.put("height",1);
		object.put("col","red");
		object.put("dob","28/03/2018");
		object.put("cheese",1);
    	jsonArray.put(object);
		}
	      catch(Exception e) { e.getMessage();}  
		return jsonArray;
	}
	
	
	private JSONArray sendCataLocalidades(HttpServletRequest request){
		connectBD();
		
		JSONArray jsonArray = new JSONArray();
		
		JSONObject object = null;
		String sqlTxt = "SELECT id_nom_localidad, nom_localidad, clave_localidad, cat.id_nom_ambito_localidad, nom_ambito_localidad, " +
				"cat.id_nom_municipio, CONCAT(nom_municipio,' ( ' ,clave_municipio,' )') AS nom_municipio, cat.clave_entidad," +
				"latitud, longitud, altitud" +
				" FROM portalin_exptec.cg_nom_localidad  cat" +
				" LEFT JOIN portalin_exptec.cg_nom_municipio rel ON rel.id_nom_municipio = cat.id_nom_municipio" +
				" LEFT JOIN portalin_exptec.cg_nom_ambito_localidad sub ON sub.id_nom_ambito_localidad = cat.id_nom_ambito_localidad"+
				" ORDER BY cat.id_nom_municipio,clave_localidad,nom_localidad ";
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlTxt);
	        while(rs.next()){
	        	object = new JSONObject();
	        	object.put("id_nom_catalogo",rs.getInt("id_nom_localidad"));
	        	object.put("nom_catalogo",rs.getString("nom_localidad"));
	        	object.put("clave_catalogo",rs.getString("clave_localidad"));
	        	object.put("id_rel_sub_catalogo",rs.getInt("id_nom_ambito_localidad"));
	        	object.put("rel_sub_catalogo",rs.getString("nom_ambito_localidad"));
	        	object.put("id_rel_catalogo",rs.getInt("id_nom_municipio"));
	        	object.put("rel_catalogo",rs.getString("nom_municipio"));
	        	object.put("clave_sub_catalogo",rs.getInt("clave_entidad"));
	        	object.put("latitud",rs.getDouble("latitud"));
	        	object.put("longitud",rs.getDouble("longitud"));
	        	object.put("altitud",rs.getInt("altitud"));
	        	jsonArray.put(object);
		    	}
		    stmt.close();
			
		 }
	      catch(Exception e) {msg("ContentServlet sendCataLocalidades error:"+ e.getMessage());}  
		
		disconnectBD();
	
		return jsonArray;
	}
	
	private void msg(String text){System.out.println(text);}
	
	protected void connectBD(){
		Pool  pool = (Pool) ObjectRegistry.getInstance().getDataAccessObject();;
		try {
			con = pool.dataSource.getConnection();
		} catch (SQLException e) {
		e.printStackTrace();
		}
  	}
	
	protected void disconnectBD(){
		try {
			if(con != null ) {
				con.close();
				}
		} catch (SQLException e) {e.printStackTrace();}
	}

	
	public void destroy() {
		disconnectBD();
	
		//msg(this.getClass().getName() +" destroy");
		super.destroy();
	}

}
