<%@ page language="java" contentType="text/html; charset=ISO-8859-1"     pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tabulador ajax</title>
<link rel="shortcut icon" href="imagenes/ico/favicon.ico" />

<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="jquery-ui-1.12.1/jquery-ui.min.js"></script>

<link href="dist/css/tabulator_simple.min.css" rel="stylesheet">
<script type="text/javascript" src="dist/js/tabulator.min.js"></script>

<script type="text/javascript">  

/*
 * http://blog.aulaformativa.com/plugins-jquery-para-modificar-tablas-html/
	 http://tabulator.info/docs/3.4
 */
$(document).ready(function(){ 
	//Build Tabulator
	$("#example-table").tabulator({
	    height:"700px",
	    layout:"fitColumns",
	    placeholder:"No Data Set",
	    columns:[
	        {title:"ID", field:"id_nom_catalogo",  align:"center", headerSort:false, width:80},
	        {title:"CLAVE", field:"clave_catalogo",  align:"center", width:100},
	        {title:"NOMBRE", field:"nom_catalogo", sorter:"string", headerFilter:true,  headerFilterPlaceholder:"Filtrar por nombre de la localidad."},
	       
	        {title:"MUNICIPIO", field:"rel_catalogo", align:"LEFT", sorter:"string", width:310, headerFilter:true,  headerFilterPlaceholder:"Filtrar por nombre del municipio." },
	        {title:"AMBITO", field:"rel_sub_catalogo", align:"center", headerSort:false, width:130},
	        {title:"LATITUD", field:"latitud", align:"right", headerSort:false, width:130},
	        {title:"LONGITUD", field:"longitud", align:"right", headerSort:false, width:130},
	        {title:"ALTITUD", field:"altitud", align:"right", headerSort:false, width:130},
	    ],
	});
	
		
	//trigger AJAX load on "Load Data via AJAX" button click
	$("#ajax-trigger").click(function(){
	    $("#example-table").tabulator("setData", "TabuladorServelet");//,{noreg_find:0,cata_nom_catalogo:"san isiadro"}
	});

	$("#example-table").tabulator("setData", "TabuladorServelet");
}); 
</script>  

<style>
*{
	margin:0;
	padding:0;
}

body {
    font-family: Arial; 
  	font-size:12px;
  
   	background-color:#eeeeee;
   	margin:0px; 
    padding:0px;
}

#masthead {
	padding: 10px 0;
	width:85%;
	position:fixed;
	
}
.contenedor{
	width:85%;

	margin:0 auto;
	background-color:#ccc;/*#ff8000;*/
	overflow:hidden;
}

</style>
</head>
<body>

	<div class='contenedor'>
		<header id='masthead'  role='banner' style=' height:30px;  z-index:2; background-color:#eeeeee;'>
		</header>
    	<article id='intro' style='padding-top:84px; background-color:#eeeeee; ' >
		<div id="example-table"></div>
		</article>
	</div>	
</body>
</html>