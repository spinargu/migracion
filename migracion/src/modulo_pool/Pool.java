package modulo_pool;

import java.io.IOException;
import java.io.InputStream;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public  class Pool {
	private static final String CONFIG_FILENAME = "config.xml";
	public DataSource dataSource;

    public String db = "portalin_inegi";
    public String url = "jdbc:mysql://localhost/"+db;
    public String user = "admin";//root /portalin_adminb
    public String pass = "admin";//admin
    
    private int maxactive = 50;
    private int maxidle = 25;
    private int timeout = 10000;
    private long maxwait = 5000;
    
	public Pool() {
		configurationFromXML();
		inicializaDataSource();
	}
	
	private void inicializaDataSource(){
		
		BasicDataSource basicDataSource = new BasicDataSource();

        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(pass);
        basicDataSource.setUrl(url);
        basicDataSource.setMaxActive(maxactive);
      //avanzadas 
        /*
	     basicDataSource.setMaxIdle(maxidle);//50 en espera
	     basicDataSource.setRemoveAbandoned(true); //conexiones no cerradas
	     basicDataSource.setRemoveAbandonedTimeout(timeout);//10000    10 seg tiempo espera para conexiones no cerradas
	     basicDataSource.setMaxWait(maxwait);//5000 tiempo de espera para lanzar una excepcion
	     */
        dataSource = basicDataSource;
	}
	
	private void configurationFromXML() {
       	SAXBuilder builder = new SAXBuilder();

    	try {
    		InputStream is = this.getClass().getClassLoader().getResourceAsStream( CONFIG_FILENAME );
    	
            Document doc = builder.build ( is );
            Element root = doc.getRootElement();

            user = root.getChild("user").getTextTrim();
            pass = root.getChild("pass").getTextTrim();
            url = root.getChild("url").getTextTrim();//"jdbc:mysql://127.0.0.1/";
            db = root.getChild("db").getTextTrim();
            maxactive = Integer.parseInt( root.getChild("maxactive").getTextTrim() );
            maxidle = Integer.parseInt( root.getChild("maxidle").getTextTrim() );
            timeout = Integer.parseInt( root.getChild("timeout").getTextTrim() );
            maxwait = Long.parseLong( root.getChild("maxwait").getTextTrim() );
           
            url = url+db;
            /*
            System.out.println(root.getChild("user").getTextTrim()+"\n "+
            		root.getChild("pass").getTextTrim()+"\n  "+
            		root.getChild("url").getTextTrim() + "\n  " +
            		root.getChild("db").getTextTrim() + " \n "+
            		Integer.parseInt( root.getChild("maxactive").getTextTrim() )+ "\n "+
            		Integer.parseInt( root.getChild("maxidle").getTextTrim() )+ "\n "+
            		Integer.parseInt( root.getChild("timeout").getTextTrim() )+ "\n "+
            		Long.parseLong( root.getChild("maxwait").getTextTrim()) );
            */
       	}catch ( IOException ex ) {System.out.println( "Could not read configuration file: "+ ex );}
    
    	catch (JDOMException e) {System.out.println( "Could not build Document: "+e);}
    }

}
