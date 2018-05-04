package modulo.moduloCNT;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modulo_control.Functions;
import modulo_pool.ObjectRegistry;
import modulo_pool.Pool;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.input.ReversedLinesFileReader;



public class FileUploadHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Functions fun = new Functions();
	private Connection con = null;
	private final String UPLOAD_DIRECTORY = "C:/uploads";
	private String path  = null;
	private String subPath = "downloads";
	private String message = "Problemas no se actualizaron registros";
	private String mesgError = null;
	private int noreg= 0;   
   
    public FileUploadHandler() {
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
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		path  = getServletContext().getRealPath("/");
		String fileUpload = null;
		File newFile = null;
		int noreg = 0;
		int iReg = 0;
		 
		mesgError = null;
		//process only if its multipart content
	    if(ServletFileUpload.isMultipartContent(request)){
			try {
		         List<FileItem> multiparts = new ServletFileUpload(
			     new DiskFileItemFactory()).parseRequest(request);
				 for(FileItem item : multiparts){
					 if(!item.isFormField()){
						 String name = new File(item.getName()).getName();
						 fileUpload = path+subPath + File.separator + name;
						newFile =  new File(path+subPath + File.separator + name);
						 item.write( newFile);//UPLOAD_DIRECTORY + File.separator + name
					 	}
			         }
		          //File uploaded successfully
				 iReg = readFileLog(fileUpload);
				 message = "Se subieron y se actualizaron en tabla registros:"+iReg+" registros";
				 if(iReg == 0 && mesgError!= null)
					 message = mesgError;
			 		
			
				 /*
				 ArrayList <RegistrosDP>relCuentas = readLog(fileUpload);//null
				// noreg = relCuentas.size();
				 if(noreg> 0){
					 iReg = qryUpdateRegistros(relCuentas);
					 message = "Se subieron: "+noreg+" registros, y se actualizaron en tabla registros:"+iReg+" registros";
					 if(iReg == 0 && mesgError!= null)
						 message = mesgError;
				 		}
				 */
				 newFile.delete();
				 
				 request.setAttribute("message", "File Uploaded Successfully");
			     } catch (Exception ex) {
			    	 request.setAttribute("message", "File Upload Failed due to " + ex);
				    }         
		
		  }else{
			  request.setAttribute("message", "Sorry this Servlet only handles file upload request");
		  		}
	    
	    
	    //e=60128=  f=2017-03-01 09:53:47=  h=1= r=0= fh=2017-03-01 09:53:47 1=
	    //message = "Total de registros cargados: "+relCuentas.size();
	    msg( message);
	    response.sendRedirect("./uploads/formUpload.jsp?message="+message);
		//  request.getRequestDispatcher("../uploads/formUpload.jsp").forward(request, response);
	}
	
	private int readFileLog(String fileUpload){
		int iReg = 0, iCount= 0;
		 File archivo = null;
	     FileReader fr = null;
	     BufferedReader br = null;
		msg("readFileLog inicia");
		int val = 0;//registros
		String sqlTxt = " INSERT INTO cg_localidad (clave_entidad, id_nom_municipio, clave_localidad, nom_localidad,  ambito,  latitud, longitud, altitud)" +
				" VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		
		 try {
			 connectBD();
	         archivo = new File (fileUpload);
	         fr = new FileReader (archivo);
	         br = new BufferedReader(fr);

	         // Lectura del fichero
	         String sCurrentLine;
	         //String[] arFields = null;
	         PreparedStatement prepStmt = con.prepareStatement(sqlTxt);
	         
	         while((sCurrentLine=br.readLine())!=null){
	        	 String[] arFields = sCurrentLine.split(",");
	    		 if(arFields!=null && arFields.length>1){
	    			 
	    			prepStmt.setInt(1, fun.gInt( extraer( arFields[0] ) ) );//clave_entidad
	 				prepStmt.setInt(2, fun.gInt(extraer( arFields[1]) ) );//id_nom_municipio
	 				prepStmt.setString(3, extraer( arFields[2] ) );//clave_localidad
	 				prepStmt.setString(4, extraer( arFields[3] ) );//nom_localidad
	 				prepStmt.setString(5, extraer( arFields[4] ) );// ambito
	 				prepStmt.setDouble(6, fun.gDouble(extraer( arFields[5]) ) );// latitud
	 				prepStmt.setDouble(7, fun.gDouble(extraer( arFields[6]) ) );// longitud
	 				prepStmt.setInt(8, fun.gInt(extraer( arFields[7]) ) );// altitud
	 		
	 				val = prepStmt.executeUpdate();
	 				
	 				if(val==0)
	 					msg("Error en line= "+arFields[0]+" "+arFields[1]+" "+arFields[2]+" "+arFields[3]+" "+arFields[4]+" "+arFields[5]+" "+arFields[6]+" "+arFields[7]);
	    			 
	 				iReg = iReg + val;
	    		 	}
	    		 
	        	 iCount++;
	         	}
	         prepStmt.close();
	      	}
	      catch(Exception e){ e.printStackTrace();}
	      finally{
	         try{                    
	            if( null != fr )  
	            	fr.close();     
	         }catch (Exception e2){ e2.printStackTrace(); }
	         disconnectBD();
	         
	      }
		 msg("Total iReg="+iReg+" de iCount="+iCount);
		return iReg;
	}
	
	private int qryUpdate(String sCurrentLine){
		int val = 0;//registros
		String sqlTxt = " INSERT INTO cg_localidad (clave_entidad, id_nom_municipio, clave_localidad, nom_localidad,  ambito,  latitud, longitud, altitud)" +
				" VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		
		String[] arFields = sCurrentLine.split(",");
		 if(arFields!=null && arFields.length>1){
			 //msg("line= "+arFields[0]+" "+arFields[1]+" "+arFields[2]+" "+arFields[3]+" "+arFields[4]+" "+arFields[5]+" "+arFields[6]+" "+arFields[7]);
			try {
				//con.setAutoCommit(false);
				noreg++;
				PreparedStatement prepStmt = con.prepareStatement(sqlTxt);
				prepStmt.setInt(1, fun.gInt( extraer( arFields[0] ) ) );//clave_entidad
				prepStmt.setInt(2, fun.gInt(extraer( arFields[1]) ) );//id_nom_municipio
				prepStmt.setString(3, extraer( arFields[2] ) );//clave_localidad
				prepStmt.setString(4, extraer( arFields[3] ) );//nom_localidad
				prepStmt.setString(5, extraer( arFields[4] ) );// ambito
				prepStmt.setDouble(6, fun.gDouble(extraer( arFields[5]) ) );// latitud
				prepStmt.setDouble(7, fun.gDouble(extraer( arFields[6]) ) );// longitud
				prepStmt.setInt(8, fun.gInt(extraer( arFields[7]) ) );// altitud
		
				val = prepStmt.executeUpdate();
				if(val==0)
					msg("line= "+arFields[0]+" "+arFields[1]+" "+arFields[2]+" "+arFields[3]+" "+arFields[4]+" "+arFields[5]+" "+arFields[6]+" "+arFields[7]);
				prepStmt.close();
		
			} catch (SQLException e) {e.toString();	}
		 }
		//msg("result:"+result);
		return val;
	}
	
	private int readFileLogXXX(String fileUpload){
		int iReg = 0, iCount= 0;
		 File archivo = null;
	     FileReader fr = null;
	     BufferedReader br = null;
		msg("readFileLog inicia");
		
		 try {
			 connectBD();
	         archivo = new File (fileUpload);
	         fr = new FileReader (archivo);
	         br = new BufferedReader(fr);

	         // Lectura del fichero
	         String sCurrentLine;
	         //String[] arFields = null;
	         while((sCurrentLine=br.readLine())!=null){
	        	 int val = qryUpdate(sCurrentLine);
	        	 iReg = iReg + val;
	        	 iCount++;
	         	}
	      	}
	      catch(Exception e){ e.printStackTrace();}
	      finally{
	         try{                    
	            if( null != fr )  
	            	fr.close();     
	         }catch (Exception e2){ e2.printStackTrace(); }
	         disconnectBD();
	         
	      }
		 msg("iCount="+iCount);
		return iReg;
	}
	
	private String  extraer(String srt){
		return srt.substring(1,srt.length()-1);
		 
	}
	
	private void msg(String text){System.out.println(text);}

}
