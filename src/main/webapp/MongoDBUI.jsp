<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>UI Design</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="js/notify.js"></script>
<script src="js/notify.min.js"></script>	
<script type="text/javascript">

$(document).ready(function() {
	//$("#connection").empty();
	$("#message").hide();
	$("#messagesuccess").hide();
	$("#aftercon").hide();
   
    //Connect button script
    $("#connect").click(function(){
    	var host = $("#hostname").val();
    	var port=$("#port").val();
    	var appid=$("#appid").val();
    	var db=$("#databasename").val();
    	var user=$('#username').val();
    	var pwd=$('#password').val();
    	var encColl ="1";
    	if(host == "" && host == null || port == "" && port == null || appid == "" && appid == null || db == "" && db == null){ //|| user == "" && user == null || pwd == "" && pwd == null ){
    		

    		//$("#message").show();
    		
    		
    	}else{
    		//$("#message").hide();
    		//$("#messagesuccess").show();
    		
    		$.ajax({	
				url : "Testing",
				type : "POST",
				data : {host:host,port:port,db:db,appid:appid,user:user,pwd:pwd,encColl:encColl	},
				cache : false,
				success : function(responseText) {
					
					var res = responseText.trim().split("@")[0];
					
					if(res == "success"){
						var resp = responseText.trim().split("@")[1]; 
						$("#database").html(resp.trim().split(":")[0]);
						$("#connection").hide();
						$("#aftercon").show();
						$("#tblbody").html(resp.trim().split(":")[1]);
					}else{
						
					}
					
				}
    		});
    		
    	}
    	
    });
  });
 //toggle
 $(document).on("click","#icon",function(){
	
	$("#lst").toggle();
	     
});

  
//collection button click 
 $(document).on("click", "#collnames", function(){
	var encColl = $(this).attr('at');//$(this).val();
	collCalling(encColl,"0","20");

}); 

 //left side collection select
 $(document).on("click", "#linames", function(){
	var encColl = $(this).attr('at');//$(this).val();
	collCalling(encColl,"0","20");

 }); 
 
 
//Collection back button 
 $(document).on("click", "#colldb", function(){
	 $("#colloutput").hide(); 
	 $("#aftercon").show();
 });
 
 
 //forward pagination 
 $(document).on("click","#fwd",function(){
	  var encColl = $("#hidcoll").text();
	  var skipvalue = $("#skipvalue").text();
	  var limitvalue = $("#limitvalue").text();
	  skipvalue = (parseInt(skipvalue)+20);
	  limitvalue = parseInt(limitvalue)+20;
	  collCalling(encColl,skipvalue,"20");
	  
}); 

//Backward pagination 
  $(document).on("click","#bwd",function(){
	   var encColl = $("#hidcoll").text();
	   var skipvalue = $("#skipvalue").text();
	   var limitvalue = $("#limitvalue").text();
	   
	   if(skipvalue != "0"){
		  //skipvalue = (parseInt(limitvalue)-20);
		  var newlimit = "20";
		  var newskipvalue = skipvalue - newlimit;
		  console.log(newskipvalue);
		 // limitvalue = parseInt(limitvalue)+20;
		  collCalling(encColl,newskipvalue,"20"); 
		  
	   }
}); 

 //Ajax function for collection showing records
 function collCalling(EncCollection,skipdoc,limitdoc){
		$.ajax({	
			url : "Testing",
			type : "POST",
			data : {encColl:EncCollection,skip:skipdoc,limit:limitdoc},
			cache : false,
			success : function(responseText) {
				//console.log(responseText);
				
				if(responseText.length > 0){
					$("#aftercon").hide();
					$("#colloutput").html("");
					$("#colloutput").html(responseText);
					$("#colloutput").show();
					
				}
				
			}
		});
 }
 
 
//New Connection 
$(document).on("click","#nc",function(){
	
	$("#colloutput").empty();
	$("#aftercon").empty();
	$("#connection").find('input:text').val('');
	$("#connection").find('input:password').val('');
	$("#icon").html("");
	$("#lst").html("");
	$("#connection").show();
	

	
	});
	
//Execute Button script

$(document).on("click","#execute", function(){

	var query = $("#query").val();
	var tableFull = query.split("db.getCollection(")[1];
	var table = tableFull.split(").find({")[0].toString().replace(/"/g, '');
	var findObj = tableFull.split(").find({")[1].toString().replace("})","");
	if(findObj !="" && findObj != null){
	$.ajax({	
		url : "Testing",
		type : "POST",
		data : {encColl:"1000",table:table,findObj:findObj},
		cache : false,
		success : function(responseText) {
			//console.log(responseText);
			
			if(responseText.length > 0){
				$("#aftercon").hide();
				$("#colloutput").html("");
				$("#colloutput").html(responseText);
				$("#colloutput").show();
				
			}
			
		}
	});
	}else{
		
	}

});

</script>	
<style>
/* Set height of the grid so .sidenav can be 100% (adjust if needed) */

html{
overflow: hidden;
}
#query {
    background-color: #ffffff;
    font-size: 18px;
    color: #333;
    border-radius: 37px;
    border: 4px solid #333;
    }
.row.content {
	height: 650px;
}

/* Set gray background color and 100% height */
.sidenav {
	background-color: #454444;
	height: 100%;
	width: 20%;
}

/* Set black background color, white text and some padding */
footer {
	background-color: #555;
	color: white;
	padding: 15px;
}

/* On small screens, set height to 'auto' for sidenav and grid */
@media screen and (max-width: 767px) {
	.sidenav {
		height: auto;
		padding: 15px;
	}
	.row.content {
		height: auto;
	}
}

#nc {
	background-color: #8B8989;
	width: 242px;
}

#db {
	background-color: #8B8989;
	width: 242px;
	color:white;
}

#hostname{
			width:50%;
}

#port{
			width:50%;
}

#appid{
			width:50%;
}

#databasename
{
	width:50%;
}

#username{
	width:50%;
}
#password{
	width:50%;
}

#dbname{
		color:white;
		 text-decoration: none;
}

#icon{
		color:white;
		font-style: italic;
		text-decoration: none;
}
#lst{

color:white;
list-style-type: none;
font-style: italic;
}
#unlst{
color:white;
list-style-type: none;
font-style: italic;
margin-left: -20px;
}

hr { 
  display: block;
  margin-top: 0.5em;
  margin-bottom: 0.5em;
  margin-left: auto;
  margin-right: auto;
  border-style: inset;
  border-width: 8px;
}
em {
	 color: #337ab7; 
}
q {
	 color: #337ab7; 
}
label{
	color: #000000;
}
#colloutput{
 /* background: #FFCC; */ 
/* //#b18b7854 */
}

</style>
</head>
<body>

	<div class="container-fluid">
		<div class="row content">
			<div class="col-sm-3 sidenav">
				<br />
				<ul class="nav nav-pills nav-stacked">
					<li class="active"><a id="nc" href="#"><span
							class="glyphicon glyphicon-flash"></span> New Connection</a></li><br/>
							
					<li><a id="db" href="#">Databases</a></li>
				</ul>
				<br>
				<div id="database"></div>
				
<!-- 				<a id="icon" href="#"><span id="icon" class="glyphicon glyphicon-chevron-right"></span>  DatabaseName</a>
			
				<div id="lst" align="left">
					<ul id="unlst">
					<li >First item</li> 
					<li>Second item</li> 
					<li>Third item</li>
					</ul>
				</div> -->




			</div>

<!--After Connections is Established  -->
<div class="col-sm-9" id="aftercon">
<h3>Collections</h3>
<hr>
				<table class="table">
					<thead class="thead-dark">
						<tr>
							<th scope="col">Collection Name</th>
							<th scope="col">Documents</th>
						</tr>
					</thead>
					<tbody id="tblbody">
	<!-- <tr> <td>Otto</td> <td>@mdo</td> </tr> <tr> <td>Thornton</td> <td>@fat</td> </tr> <tr> <td>the Bird</td> <td>@twitter</td> </tr> -->
					</tbody>
				</table>
			</div>
<!--After Connections is Established div ends here -->

<div class="col-sm-9" id="colloutput" >
<!--   <h3>Sotero.Collection Name</h3>
<hr><br>
<input class="form-control" type="text"/>  


<div><br>
&emsp;<label>id</label>: <em>"4"</em><br>
&emsp;<label>k</label>: "2490583"<br>
&emsp;<label>c</label>: "40247053792-02896461481-48241738872-92923046881-11970114312-44310196157-23183913773-06033734524-67808223526-15580247572"<br>
&emsp;<label>pad</label>: "48726261158-89413882211-30023977539-65146951488-05456170482"<br>
</div>
<div><br>
&emsp;<label>id</label>: "4"<br>
&emsp;<label>k</label>: "2490583"<br>
&emsp;<label>c</label>: "40247053792-02896461481-48241738872-92923046881-11970114312-44310196157-23183913773-06033734524-67808223526-15580247572"<br>
&emsp;<label>pad</label>: "48726261158-89413882211-30023977539-65146951488-05456170482"<br>
</div>  -->
</div>

<!-- Connection Box div  -->
			<div class="col-sm-9" id="connection">
				<div class="page-header">
					<h3>Connect to Host</h3>
				</div>

				<form class="form-horizontal">
					<div class="form-group form-group-sm">
						<label class="col-sm-2 control-label" for="sm">HostName</label>
						<div class="col-sm-10">
							<input placeholder="localhost" id="hostname" class="form-control" type="text">
						</div>
					</div>
					
					<div class="form-group form-group-sm">
						<label class="col-sm-2 control-label">Port</label>
						<div class="col-sm-10">
							<input placeholder="27017" id="port" class="form-control" type="text">
						</div>
					</div>
					
					
					<div class="form-group form-group-sm">
						<label class="col-sm-2 control-label">AppId</label>
						<div class="col-sm-10">
							<input placeholder="Appid" id="appid" class="form-control" type="text">
						</div>
					</div>
					
					<div class="form-group form-group-sm">
						<label class="col-sm-2 control-label">DatabaseName</label>
						<div class="col-sm-10">
							<input placeholder="databasename" id="databasename" class="form-control" type="text">
						</div>
					</div>
					
					<div class="form-group form-group-sm">
						<label class="col-sm-2 control-label">UserName</label>
						<div class="col-sm-10">
							<input placeholder="username" id="username" class="form-control" type="text">
						</div>
					</div>
					
					<div class="form-group form-group-sm">
						<label class="col-sm-2 control-label">Password</label>
						<div class="col-sm-10">
							<input placeholder="password" id="password" class="form-control" type="password">
						</div>
					</div>
					<div align="center">
					<button id="connect" type="button" class="btn btn-success">Connect</button>
					</div>
				</form><br/>
				<div id="message" class="alert alert-danger">
 						<strong><center>please provide all details</center></strong> 
				</div>
				
				<div id="messagesuccess" class="alert alert-success">
 						<strong><center>Connection suceessfull</center></strong> 
				</div>
			
			<!-- Connection Box div  end  here-->
		</div>
	</div>

</body>
</html>
