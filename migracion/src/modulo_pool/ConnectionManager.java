package modulo_pool;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;


/*
 para que se despliege el odbc para paradox en 
Panel de control\Todos los elementos de Panel de control\Herramientas administrativas 
origenes de datos ODBC 
se requirio instalar el archivo de borland database engine
 */

public class ConnectionManager {
	
	
	private static final String CONFIG_FILENAME = "config.xml";
    private static final String DBDRIVERNAME  = "com.mysql.jdbc.Driver";
    private static final String DBUSER = "root";
    private static final String DBPASSWORD = "admin";
    private String dbURI = null;
   
	
	public ConnectionManager() {
	
		initClassName();
		//configurationFromXML();
	}
	
	private void initClassName(){
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		}catch (ClassNotFoundException e) {
			msg(	" ClassNotFoundException : " + e.toString() );
		}
	}
	
	public Connection getCon() {
		Connection con = null ;
		try {
			con = java.sql.DriverManager.getConnection("jdbc:odbc:asistencia");
			if(con==null)
				msg("ConnectionManager getCon : Error schema  is not open.......");
			
			/*String sqlTxt = " SELECT count(registro)" +
		   		     " FROM empleados emp  ";
			 Statement stmt = con.createStatement();
		    	ResultSet rs = stmt.executeQuery(sqlTxt);
			    if(rs.next()){
			    	System.out.println("empleados cont:"+rs.getInt(1));
			
			    	}
			    stmt.close();*/
	
		}catch (SQLException e) {
			msg(	" SQLException : " + e.toString() );
		}
		return con;
	}
	
	public void closeConn(Connection con) {
		try {
			if(con != null){
				con.close();
				//log.info("ConnectionManager closeConn : schema  is closed.");
				}
		} catch (SQLException e) {msg(e.toString());}
	}
	
	public Connection connectToMySqlDb() {
		
		Connection conn = null;	
		/*
   	 try{
            java.lang.Class.forName( DBDRIVERNAME ).newInstance();//Class.forName(driver);
            
           conn = DriverManager.getConnection(dbURI, DBUSER, DBPASSWORD);
            
        	}
        catch(Exception e){msg("Error when attempting to obtain DB Driver: "+ DBDRIVERNAME + " on "+ e);}
   	 */
   	 return conn;
	}
	
	
	
	private void configurationFromXML() {
       	SAXBuilder builder = new SAXBuilder();

    	try {
    		InputStream is = this.getClass().getClassLoader().getResourceAsStream( CONFIG_FILENAME );
    	
            Document doc = builder.build ( is );
            Element root = doc.getRootElement();

            //dbDriverName = root.getChild("dbDriverName").getTextTrim();
            //dbUser = root.getChild("dbUser").getTextTrim();
            //dbPassword = root.getChild("dbPassword").getTextTrim();
            dbURI = root.getChild("dbURI").getTextTrim();//"jdbc:mysql://127.0.0.1/";
            String params = root.getChild("params").getTextTrim();
            String schema = root.getChild("schema").getTextTrim();
            String ip = root.getChild("ip").getTextTrim();
            int server = Integer.parseInt( root.getChild("server").getTextTrim() );
         //   dbPoolMinSize = Integer.parseInt( root.getChild("dbPoolMinSize").getTextTrim() );
         //   dbPoolMaxSize = Integer.parseInt( root.getChild("dbPoolMaxSize").getTextTrim() );
         //   validationQuery = root.getChild("validationQuery").getTextTrim();
            
            if(server==0)
            	dbURI += ip+"/";
            else
            	dbURI += "127.0.0.1"+"/";
            dbURI += schema+params;
            
        //  msg("ConnectionManager configurationFromXML server="+server+" dbURI="+dbURI);//LOG.info
          //msg("ConnectionManager configurationFromXML dbDriverName="+dbDriverName+" dbUser="+dbUser+" dbPassword="+dbPassword+" dbURI="+dbURI);//LOG.info
    	}catch ( IOException ex ) {msg( "Could not read configuration file: "+ ex );}
    
    	catch (JDOMException e) {msg( "Could not build Document: "+e);}
    }
	
	private void msg(String text){
		System.out.println(text);
	}

}
