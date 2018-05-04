package modulo_pool;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/*
para que se despliege el odbc para paradox en 
Panel de control\Todos los elementos de Panel de control\Herramientas administrativas 
origenes de datos ODBC 
se requirio instalar el archivo de borland database engine
*/

public class SimpleDAO {
	private Connection conMysql = null ;
		
	public SimpleDAO(){

	}
	
	public Connection getConXXX() {
		Connection con = null ;
		try {
			Class.forName("com.mysql.jdbc.Driver"); //Class.forName("com.mysql.jdbc.Driver");//portalin_asistencia_mill
			//Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:17770/pro_db_asistencia_mill", "root", "");jdbc:mysql://127.0.0.1:3306/pro_db_asistencia", "root", "admin"
			//System.out.println("SimpleDAO con:"+(con!=null));
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/portalin_asistencia_mill", "portalin_adminb", "admin");//jdbc:mysql://192.168.36.36:17770/pro_db_asistencia", "root", ""
		} catch (SQLException e) {e.getMessage();}
		catch (ClassNotFoundException ex) {
            throw new ClassCastException(ex.getMessage() );
         }
	
		return con;
	}
	
	public Connection getCon_remote() {
		Connection con = null ;
		try {
			Class.forName("com.mysql.jdbc.Driver"); //Class.forName("com.mysql.jdbc.Driver");//
			//Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:17770/pro_db_asistencia_mill", "root", "");jdbc:mysql://127.0.0.1:3306/pro_db_asistencia", "root", "admin"
			//System.out.println("SimpleDAO con:"+(con!=null));
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:17770/pro_db_asistencia_mill", "root", "");//jdbc:mysql://192.168.36.36:17770/pro_db_asistencia", "root", ""
		} catch (SQLException e) {e.getMessage();}
		catch (ClassNotFoundException ex) {
            throw new ClassCastException(ex.getMessage() );
         }
	
		return con;
	}
	
	public Connection getCon_local() {
		Connection con = null ;
		try {
			Class.forName("com.mysql.jdbc.Driver"); //Class.forName("com.mysql.jdbc.Driver");//
			//Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:17770/pro_db_asistencia_mill", "root", "");jdbc:mysql://127.0.0.1:3306/pro_db_asistencia", "root", "admin"
			//System.out.println("SimpleDAO con:"+(con!=null));
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/portalin_asistencia_mill", "portalin_adminb", "admin");//jdbc:mysql://192.168.36.36:17770/pro_db_asistencia", "root", ""
		} catch (SQLException e) {e.getMessage();}
		catch (ClassNotFoundException ex) {
            throw new ClassCastException(ex.getMessage() );
         }
	
		return con;
	}
	
	public void closeConnXXX(Connection con) {
		try {
			if(con != null){
				con.close();
				//log.info("ConnectionManager closeConn : schema  is closed.");
				}
		} catch (SQLException e) {System.out.println(e.toString());}
	}

	
	public Connection getCon_back_up() {
		Connection con = null ;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = java.sql.DriverManager.getConnection("jdbc:odbc:asistencia");
			if(con==null)
				System.out.println("ConnectionManager getCon : Error schema  is not open.......");
			
			/*String sqlTxt = " SELECT count(registro)" +
		   		     " FROM empleados emp  ";
			 Statement stmt = con.createStatement();
		    	ResultSet rs = stmt.executeQuery(sqlTxt);
			    if(rs.next()){
			    	System.out.println("empleados cont:"+rs.getInt(1));
			
			    	}
			    stmt.close();*/
	
		}catch (ClassNotFoundException e) {
			System.out.println(	" ClassNotFoundException : " + e.toString() );
		}
		catch (SQLException e) {
			System.out.println(	" SQLException : " + e.toString() );
		}
		return con;
	}

}
