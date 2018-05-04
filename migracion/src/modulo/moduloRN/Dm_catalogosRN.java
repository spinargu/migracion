package modulo.moduloRN;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modulo.moduloDP.Dm_catalogoDP;

import modulo_control.Functions;
import modulo_pool.ObjectRegistry;
import modulo_pool.Pool;

public class Dm_catalogosRN {
	private Functions fun = new Functions();
	private Connection con = null;
	private ArrayList <Dm_catalogoDP>relInegiLocalidad = null;
	private ArrayList <Dm_catalogoDP>relExpTecLocalidad = null;
	
	public Dm_catalogosRN() {
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
	public void active_rutina(){
		//termina  iIns=283  iUpd=5075 14/03.2018
	}
	
	public void actualiza_exptec(){
		msg("inicia active_rutina()");
		int iIns = 0, iUpd = 0;
		boolean bFind= false;
		ArrayList <Dm_catalogoDP>relInegiLocalidad = null;
		ArrayList <Dm_catalogoDP>relExpTecLocalidad = null;
		Dm_catalogoDP bdInegi = null;
		Dm_catalogoDP bdExp = null;
		connectBD();
		
		for(int id_nom_municipio=1; id_nom_municipio < 85; id_nom_municipio++){
			relInegiLocalidad = qryInegiLocalidad(id_nom_municipio);
			relExpTecLocalidad = qryExpTecLocalidad(id_nom_municipio);
			if(relInegiLocalidad != null && relInegiLocalidad.size() > 0){
				for(int index = 0; index < relInegiLocalidad.size(); index++){
					bdInegi = relInegiLocalidad.get(index);
					bFind= false;
					if(relExpTecLocalidad != null && relExpTecLocalidad.size() > 0){
						for(int item = 0; item < relExpTecLocalidad.size(); item++){
							 bdExp = relExpTecLocalidad.get(item);
							 
							 if( bdInegi.getClave_localidad().equals(bdExp.getClave_localidad()) ){
								 bFind= true;
								 if( bdInegi.getNom_localidad().equals(bdExp.getNom_localidad()) ){
									 iUpd = iUpd + qryUpdate( bdInegi, bdExp.getId_nom_localidad() );
								 	}
								 else{//problemas
									 iUpd = iUpd + qryUpdate_loc( bdInegi, bdExp.getId_nom_localidad() );
									// msg("id_nom_municipo:"+id_nom_municipio+" problemas: "+ bdInegi.getClave_localidad()+ " == "+bdExp.getClave_localidad() +" "+bdInegi.getNom_localidad()+" != "+bdExp.getNom_localidad() +"  bdInegiIdlocalidad:"+bdInegi.getId_nom_localidad()+" "+bdExp.getId_nom_localidad());
								 	}
							 	}
							}//for
						if(bFind == false){
							iIns = iIns + qryInsert(bdInegi);
							}
						}
					else{
						//Aqui serian solo inserciones
						iIns = iIns + qryInsert(bdInegi);
						}
					
					}//relInegiLocalidad
				
				}//if(relInegiLocalidad
			}//for id_nom_municipio
		
		disconnectBD();
		msg("termina  active_rutina()");
		msg("termina  iIns="+iIns +"  iUpd="+iUpd);
	}
	
	private int qryUpdate(Dm_catalogoDP bd, int id_nom_localidad){
		int val = 0;
		String sqlTxt = "UPDATE portalin_exptec.cg_nom_localidad " +
				" SET " +
				" latitud = ?," +
				" longitud = ?," +
				" altitud = ?" +
			    " WHERE id_nom_localidad = ?";
		try {
			PreparedStatement prepStmt = con.prepareStatement(sqlTxt);
			prepStmt.setDouble(1, bd.getLatitud() );
			prepStmt.setDouble(2, bd.getLongitud() );
			prepStmt.setInt(3, bd.getAltitud() );
			prepStmt.setInt(4, id_nom_localidad );
			val = prepStmt.executeUpdate();
			prepStmt.close();
			
	
			
	 		}
		catch(Exception e) {
			msg("Error en insercion: " + e.getMessage());
		  }  
		return val;
	}
	
	private int qryUpdate_loc(Dm_catalogoDP bd, int id_nom_localidad){
		int val = 0;
		String sqlTxt = "UPDATE portalin_exptec.cg_nom_localidad " +
				" SET " +
				" nom_localidad = ?," +
				" latitud = ?," +
				" longitud = ?," +
				" altitud = ?" +
			    " WHERE id_nom_localidad = ?";
		try {
			PreparedStatement prepStmt = con.prepareStatement(sqlTxt);
			prepStmt.setString(1, bd.getNom_localidad() );
			prepStmt.setDouble(2, bd.getLatitud() );
			prepStmt.setDouble(3, bd.getLongitud() );
			prepStmt.setInt(4, bd.getAltitud() );
			prepStmt.setInt(5, id_nom_localidad );
			val = prepStmt.executeUpdate();
			prepStmt.close();
			
	
			
	 		}
		catch(Exception e) {
			msg("Error en insercion: " + e.getMessage());
		  }  
		return val;
	}
	
	private int qryInsert(Dm_catalogoDP bd){
		int val = 0;
		String sqlTxt = "INSERT INTO portalin_exptec.cg_nom_localidad " +
				"( nom_localidad, clave_localidad, ambito, id_nom_municipio, clave_entidad, latitud, longitud, altitud, id_nom_ambito_localidad) " +
				" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)" ;
		try {
			PreparedStatement prepStmt = con.prepareStatement(sqlTxt);
			prepStmt.setString(1, bd.getNom_localidad() );
			prepStmt.setString(2, bd.getClave_localidad() );
			prepStmt.setString(3, bd.getAmbito() );
			prepStmt.setInt(4, bd.getId_nom_municipio() );
			prepStmt.setInt(5, bd.getClave_entidad() );
			prepStmt.setDouble(6, bd.getLatitud() );
			prepStmt.setDouble(7, bd.getLongitud() );
			prepStmt.setInt(8, bd.getAltitud() );
			prepStmt.setInt(9, bd.getId_nom_ambito_localidad() );
			val = prepStmt.executeUpdate();
			prepStmt.close();
			
	
			
	 		}
		catch(Exception e) {
			msg("Error en insercion: " + e.getMessage());
		  }  
		return val;
	}
	
	
	private ArrayList<Dm_catalogoDP> qryInegiLocalidad(int id_nom_municipio){
		ArrayList<Dm_catalogoDP>relCuentas = new ArrayList<Dm_catalogoDP>();
		String  sqlTxt = "SELECT id_nom_localidad, nom_localidad, clave_localidad, ambito, id_nom_municipio, clave_entidad, latitud, longitud, altitud, id_nom_ambito_localidad " +
				" FROM cg_localidad WHERE id_nom_municipio= " + id_nom_municipio; 
		
		try {
		    Statement stmt = con.createStatement();
		   	ResultSet rs = stmt.executeQuery(sqlTxt);
		 	while (rs.next()) {
		 		Dm_catalogoDP bd = new Dm_catalogoDP();
		 		bd.setId_nom_localidad(rs.getInt("id_nom_localidad"));
		 		bd.setNom_localidad(rs.getString("nom_localidad"));
		 		bd.setClave_localidad(rs.getString("clave_localidad"));
		 		bd.setAmbito(rs.getString("ambito"));
		     	bd.setId_nom_municipio(rs.getInt("id_nom_municipio"));
		 		bd.setClave_entidad(rs.getInt("clave_entidad"));
		 	   	bd.setLatitud(rs.getDouble("latitud"));
	        	bd.setLongitud(rs.getDouble("longitud"));
	        	bd.setAltitud(rs.getInt("altitud"));
	        	bd.setId_nom_ambito_localidad(rs.getInt("id_nom_ambito_localidad"));
	        	
	        	relCuentas.add(bd);
			    }
			stmt.close();
		 }
		  catch(Exception e) { msg("Dm_catalogosRN  qryInegiLocalidad: " + e.getMessage().toString());} 
		
		return relCuentas;
	}
	
	private ArrayList<Dm_catalogoDP> qryExpTecLocalidad(int id_nom_municipio){
		ArrayList<Dm_catalogoDP>relCuentas = new ArrayList<Dm_catalogoDP>();
		String  sqlTxt = "SELECT id_nom_localidad,nom_localidad, clave_localidad, ambito, id_nom_municipio, clave_entidad, id_nom_ambito_localidad " +
				" FROM portalin_exptec.cg_nom_localidad WHERE id_nom_municipio= " + id_nom_municipio; 
				
		try {
		    Statement stmt = con.createStatement();
		   	ResultSet rs = stmt.executeQuery(sqlTxt);
		 	while (rs.next()) {
		 		Dm_catalogoDP bd = new Dm_catalogoDP();
		 		bd.setId_nom_localidad(rs.getInt("id_nom_localidad"));
		 		bd.setNom_localidad(rs.getString("nom_localidad"));
		 		bd.setClave_localidad(rs.getString("clave_localidad"));
		 		bd.setAmbito(rs.getString("ambito"));
		     	bd.setId_nom_municipio(rs.getInt("id_nom_municipio"));
		 		bd.setClave_entidad(rs.getInt("clave_entidad"));
		 	   	//bd.setLatitud(rs.getDouble("latitud"));
	        	//bd.setLongitud(rs.getDouble("longitud"));
	        	//bd.setAltitud(rs.getInt("altitud"));
	        	bd.setId_nom_ambito_localidad(rs.getInt("id_nom_ambito_localidad"));
	        	
	        	relCuentas.add(bd);
			    }
			stmt.close();
		 }
		  catch(Exception e) { msg("Dm_catalogosRN  qryExpTecLocalidad: " + e.getMessage().toString());} 
		return relCuentas;
	}
	
	
	
	private void msg(String text){System.out.println(text);}

}
