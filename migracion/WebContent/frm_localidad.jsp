<%@ page language="java" contentType="text/html; charset=ISO-8859-1"     pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Load scroll</title>
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


/*tabla express*/
 
 #hor-minimalist-b
{
   /* font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
    font-size: 12px;
    background: #fff;*/
   /* margin: 45px;*/
    width: 95%;
    border-collapse: collapse;
    text-align: left;
}
#hor-minimalist-b th{
    font-size: 12px;
    font-weight: normal;
    background: #eee;
    /*color: #38AB3A;
    padding: 10px 8px;*/
    border-bottom: 2px solid #c1c1c1;/*#38AB3A*/
    border-top: 2px solid #c1c1c1;
}
#hor-minimalist-b td{
	background: #fff;
    border-bottom: 1px solid #ccc;
   /* color: #669;*/
    padding: 6px 8px;
}
#hor-minimalist-b tbody tr:hover td{
    color: #009;
    font-weight:bold;
}

</style>
<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>  
<script type="text/javascript">  
var inireg = 0;
var noreg = 100;
var load = false;

$(document).ready(function(){ 
	loadContent(inireg);
	
	$(window).scroll(function () {
		var curScroll = $(window).scrollTop();//$(this)[0].scrollTop;
		var maxScroll = $(document).height() - $(window).height();//$(this)[0].scrollHeight - $(this).height();
		
        //if ($(window).scrollTop() == $(document).height() - $(window).height() && load == false) {
        if ( curScroll >= maxScroll -800 && !load) {	
        	inireg = inireg + noreg;
        	load = true;
        	loadContent(inireg);
        	//new_element.hide().appendTo('.your_div').fadeIn(); 
        	//$(window).scrollTop($(window).scrollTop()-1);
        	}
    });

}); 

function loadContent(inireg){
	//$('#hor-minimalist-b').find("tr:gt(0)").remove();
	var params = 'params={"inireg":'+inireg+',"noreg":'+noreg+'}';//"inireg="+inireg;
	$.getJSON('ContentServlet',params, function(data){
		if (data!=null){
	    $.each(data, function (index, item) {
	    	
	    	 //console.log(item['no_contrato']);
	    	// console.log(item['descripcion_tipo_contrato']);
	    	 var $tr = $('<tr>').append(
	    			 $('<td>').text(item.id_nom_localidad),
	    	            $('<td>').text(item.clave_localidad),
	    	            $('<td>').text(item.nom_localidad),
	    	            $('<td style="text-align:center;">').text(item.nom_ambito_localidad),
	    	            $('<td style="text-align:right;">').text(item.nom_municipio),
	    	            $('<td style="text-align:center;">').text(item.latitud),
	    	            $('<td style="text-align:center;">').text(item.longitud)
	    	            );
	    	 //console.log($tr.wrap('<p>').html());
	    	 $('#hor-minimalist-b').append($tr);
	    	 load = false;
	    });
		}
	});
}

</script>  
</head>
<body class='containerxxx'>

<input type="button" id="showTable" value="showTable"/> 
	<div id='tablediv'>
		<table id='hor-minimalist-b'  >
			<tr  height='32px'>
			<th class='table_panel_3' width='8%' nowrap>ID</th>
					<th class='table_panel_3' width='8%' nowrap>CLAVE</th>
					<th class='table_panel_3' width='24%' nowrap>NOMBRE</th>
					<th class='table_panel_3' width='8%' nowrap>AMBITO</th>
					<th class='table_panel_3' width='15%' nowrap>MUNICIPIO</th>
					<th class='table_panel_3' width='10%' nowrap>LATITUD</th>
					<th class='table_panel_3' width='10%' nowrap>LONGITUD</th>
				
				</tr>
			
		</table>
	</div>
</body>
</html>