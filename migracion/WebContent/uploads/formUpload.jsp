<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>
<%
	String message = request.getParameter("message");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Carga archivo de registros</title>
<link rel="shortcut icon" href="../imagenes/ico/favicon.ico" />
<style type="text/css">
 BODY {
	    font-family:Trebuchet MS,Tahoma,Verdana,arial; 
	  	font-size:11px;
	   	background-color:#eeeeee;
	   	scrollbar-arrow-color:#000000;
		scrollbar-face-color:transparent;
		scrollbar-shadow-color:#000000;
		scrollbar-track-color:transparent;
	    margin:0px; 
	    padding:0px;
	    }
	    
.btn_upload{background: transparent url(../imagenes/upload.png) no-repeat 0 0; background-position: 50% 50%;}
.btn_img_text23 {
		height:30px;
		background-position: left center;
		font-size: 14px;
	    font-family: Trebuchet MS,Tahoma,Verdana,Arial;
	    padding-left:12px;
	    border-top: 2px solid buttonhighlight;
	    border-left: 2px solid buttonhighlight;
	    border-bottom: 2px solid buttonshadow;
	    border-right: 2px solid buttonshadow;
	    cursor:pointer;	
	}	    
	    
</style>	
<script  type="text/javascript">
window.onload=function(){
	
	if(document.getElementById('message').value != 'null'){
		alert(document.getElementById('message').value);
	
		}
	
}

</script>    
</head>
<body>

<div class='base-layer' style='position:absolute; width:485px; height:600px; left:255px;  top:150px; z-index:1; '>

	<form id="regform" name="regform" action="../FileUploadHandler" 	method="post" enctype="multipart/form-data">
		<fieldset style='border-right: #6487AE 1px solid; border-bottom: #6487AE 2px double;'>
		<legend align='center'>  <b> CARGA DE DOCUMENTACION.  </b> </legend>
		
		<table width='465' cellpadding='0' cellspacing='1' border='0' align='center'>
			<tr height='25'>
				<td width='5' nowrap>&nbsp;</td>
				<td width='150' align='right' valign='middle' nowrap>
			
					<input type='submit'  id='btnUpload' name='btnUpload' value='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Upload'
						class='btn_img_text23 btn_upload' style='width:165px;'  >
					
				</td>
				<td width='150' align='center' valign='middle' nowrap>
					
				</td>
				<td width='150' align='left' valign='middle' nowrap>	
					
				</td>
				<td width='5'nowrap>&nbsp;</td>
			</tr>
			<tr height='5'>
				<td>&nbsp;</td>
				<td colspan='3' align='left' valign='bottom' nowrap>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr height='5'>
				<td>&nbsp;</td>
				<td colspan='3' align='left' valign='bottom' nowrap>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			
			
			<tr height='20'>
				<td>&nbsp;</td>
				<td colspan='3' align='left' valign='bottom' nowrap>
					<span style='color:black; font-weight:bold; font-size: 14px;' >Seleccionar archivo para cargar:</span>
				</td>
				<td>&nbsp;</td>
			</tr>	
			<tr >
				<td nowrap>&nbsp;</td>
				<td colspan='3' align='left' valign='top' nowrap>
					<input type='file' name='file' id='file' size='35' class='userfile' value='' > 
					
				</td>
				<td nowrap>&nbsp;</td>
			</tr>
		</table>
	
	</form>

</div>
<input type='hidden' id='message'  value='<%=message%>'>
</body>
</html>