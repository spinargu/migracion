package modulo.moduloCNT;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import modulo.moduloMD.Dm_gestion_catalogosMD;
import modulo_control.Functions;
import modulo_pool.ObjectRegistry;
import modulo_pool.Pool;


public class ContentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con;  
	private Functions fun  = new Functions();       
  
    public ContentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void connectBD(){
		Pool pool = (Pool) ObjectRegistry.getInstance().getDataAccessObject();;
		try {
			con = pool.dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
  	}
	
	private void disconnectBD(){
		try {
			if(con != null){
				con.close();
				}
		} catch (SQLException e) {System.out.println(e.toString());}
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Enumeration eIds = request.getParameterNames();
		while (eIds.hasMoreElements()){
			String nombre = (String) eIds.nextElement();
		    String namParam = nombre.toLowerCase();
		    msg("ContentServlet doGet namParam:"+namParam +" value :"+request.getParameter(namParam));
		   
		   
			} 
		
		JSONArray jsonArray =  sendArray(request);
		response.setContentType("application/json");
		response.getWriter().print(jsonArray);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private JSONArray sendArray(HttpServletRequest request){
		String params = fun.getParam(request, "params", null);
		int inireg = 0;
		int noreg = 30;
		try {
			JSONObject object = new JSONObject(params);
			msg("ContentServlet sendArray inireg="+object.getInt("inireg"));
			inireg = object.getInt("inireg");
			noreg = object.getInt("noreg");
		} catch (JSONException e) {	e.printStackTrace();}
		 
		msg("ContentServlet sendArray inicia inireg="+inireg+" noreg="+noreg);
		connectBD();
		Dm_gestion_catalogosMD gcm = new Dm_gestion_catalogosMD();
		gcm.setCon(con);
		JSONArray jsonArray = gcm.qryCatalogoLocalidad(inireg, noreg);
		disconnectBD();
		//msg("ContentServlet sendArray jsonArray="+jsonArray.toString());
		return jsonArray;
	}
	
	private void msg(String text){System.out.println(text);}

}
