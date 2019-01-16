<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache">
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<title id="fileName">EXF DB MIGRATION TOOL</title>



	<link rel="stylesheet"
		href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
		integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
		crossorigin="anonymous">
		<script
			src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script
			src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<script
			src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>



		<style>
.head {
	padding: 2px;
}
.progress {
   
    text-align: center;
    background-color:#2874A6;
    color: white;
    font-size: 120%;
   
}	 

#b1 {
	display: none;
}

</style>
 <script type="text/javascript">
/* Check Session Every Time Mouse Click */
 
  $(document).ready(function() {
	 sendRequest();
		 var value = $("#fileName").val();
		 //console.log(value);
		  function sendRequest(){
		 $.ajax({
	            url: 'Sessioncheck',
	            type: "POST",
	            data: {
	            	value:value
	            },
	            
	            success: function(responseText){
	            //console.log(responseText);
		        	if(responseText == "failure")
		             {
		        		 window.location.href = 'logout.jsp';
		             }
		        }
		        
		 });
		  };
		  $(document).ready(function(){
	        	 setInterval(sendRequest,1000);
	        	});
	 });
</script>
</head>

<body background="img/bckcolr1.jpg">
	<!-- baneer -->
<div id="session_check">
	<%
		response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1 
		response.setHeader("Pragma", "no-cache"); //HTTP 1.0 
		response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	%>
	<div class="container-fluid head">
		<div class="container">
			<div class="row">
				<div class="col-md-2">
					<img src="img/exalogo.png" alt="" / style="margin-left: -60px;">
				</div>
				<div class="col-md-8">
					<h2
						style="font-weight: bold; font-family: 'Arial Black', Gadget, sans-serif; margin-left: 20%; margin-top: 18px;">EXF
						DB MIGRATION TOOL</h2>


				</div>

				<div class="col-md-2">
					<img src="img/exaname.png" alt="" />

				</div>


			</div>

		</div>

	</div>

	<!-- baneer -->

	<!-- form -->
	 <div class="btn-group"><a href="MongoDBUI.jsp" style="text-decoration: none; color:white; margin-left: 1006px;" class="btn btn-primary" >MongoDB UI</a>
<a href="logout.jsp" style="text-decoration: none; color:white;" class="btn btn-primary" >Logout</a> </div>
	<div class="container" style="width: 50%; color: rgb(204, 51, 51);">

		<div class="progress">
			<div class="progress-bar progress-bar-striped progress-bar-animated"
				style="width: 0%;"></div>
		</div>
	</div>

	<div class="alert alert-success" id="success-alert"
		style="width: 30%; height: 20%; margin-top: -70px; margin-left: 502px">
		<button type="button" class="close" data-dismiss="alert">x</button>
		<!-- <strong><center>Migration Has Completed!</center></strong> -->
	</div>
	<div class="alert alert-danger" id="alter-danger"
		style="width: 20%; height: 20%; margin-top: -70px; margin-left: 550px">
		<!--     <strong><center>Migration Has Failed!</center></strong> -->
	</div>
	<div class="alert alert-info" id="alter-info"
		style="width: 27%; height: 20%; margin-top: -70px; margin-left: 502px">
	</div>

	<!--  <div class="alert alert-info alert-dismissible fade show" id="alert-info" style="width: 65%; height: 20%; margin-top:-70px; margin-left:205px; word-break:break-all">
    <button type="button" class="close" data-dismiss="alert">&times;</button>
    
  </div> -->


	<!--   Bootstrap modal -->


	<!-- Modal -->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<!--  <button type="button" class="close" data-dismiss="modal">&times;</button>  -->
					<button type="button" onClick="window.location.reload();">Close</button>
					<!-- <button type="button" onclick="document.getElementById('jdbcpassword').value = ''">Close </button> -->

				</div>

				<div class="modal-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-success" data-dismiss="modal"
						style="margin-right: 46%;" id="Continue">Continue</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal"
						id="Drop" disabled="disabled">Drop and Continue</button>


				</div>
			</div>

		</div>
	</div>
	
	
	<div class="modal fade" id="logs" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					  <button type="button" data-dismiss="modal">Close</button> 
					<!-- <button type="button" onClick="window.location.reload();">Close</button> -->
					<!-- <button type="button" onclick="document.getElementById('jdbcpassword').value = ''">Close </button> -->

				</div>

				<div class="modal-body"></div>
				
			</div>

		</div>
	</div>
	
	
	


	<!-- end of bootstrap model -->


	<div class="container-fluid" style="margin-top: -14px;">
		<div class="container">
			<div class="row">
				<div class="container mt-4">



					<!-- Grid row -->
					<div class="row">

						<!-- Grid column -->
						<div class="col-md-6 mb-4">
							<div class="card">
								<div class="card-body"
									style="background-image: url(img/sqlback.png); height: 452px;">
									<br> <!--Body-->


										<div class="md-form">
											<label for="defaultForm-pass" style="font-weight: 500;">DB
												Type</label> <i class="fa fa-envelope prefix grey-text"></i> <select
												class="form-control" id="sqldb">

												<option>Mysql</option>
												<option>SQL Server</option>
												<option>Oracle</option>

											</select>


										</div>


										<div class="md-form">
											<label for="defaultForm-pass" style="font-weight: 500;">JDBC
												Connection string</label> <i class="fa fa-envelope prefix grey-text"></i>
											<input type="text" class="form-control"
												placeholder="jdbc:dbtype://host name:port/DBname"
												id="jdbcurl">
										</div>
										<div class="md-form">
											<label for="defaultForm-pass" style="font-weight: 500;">
												AppID</label> <i class="fa fa-lock prefix grey-text"></i> <input
												type="text" class="form-control" id="appid">
										</div>

										<div class="md-form">
											<label for="defaultForm-pass" style="font-weight: 500;">JDBC
												Userame</label> <i class="fa fa-lock prefix grey-text"></i> <input
												type="text" class="form-control" id="usernamejdbc">
										</div>
										<div class="md-form">
											<label for="defaultForm-pass" style="font-weight: 500;">JDBC
												Password</label> <i class="fa fa-lock prefix grey-text"></i> <input
												type="password" class="form-control" id="jdbcpassword">
										</div>
										<div class="text-center">
											<button type="button" class="btn btn-primary" id="Testsource"
												style="margin-top: 8px;">Test Source</button>
										</div>
								</div>
							</div>
						</div>
						<!-- Grid column -->
						<!-- Grid column -->
						<div class="col-md-6 mb-4">
							<div class="card indigo form-white">
								<div class="card-body"
									style="background-image: url(img/mogobcak.png); height: 452px; background-repeat: no-repeat;">
									<br> <!--Body-->

										<div class="md-form">
											<label for="defaultForm-pass" style="font-weight: 500;">DB
												Type</label> <i class="fa fa-envelope prefix grey-text"></i> <select
												class="form-control" id="mongo">

												<option>MongoDB</option>


											</select>


										</div>
										<div class="md-form">
											<label for="defaultForm-pass" style="font-weight: 500;">MongoDB
												connection</label> <i class="fa fa-envelope prefix white-text"></i>
											<input type="text" class="form-control"
												placeholder="host name:port/Database" id="mongodburl">
										</div>
										<div class="md-form">
											<label for="defaultForm-pass" style="font-weight: 500;">MongoDB
												Username</label> <i class="fa fa-lock prefix white-text"></i> <input
												type="text" class="form-control" id="usernamemongo">
										</div>

										<div class="md-form">
											<label for="defaultForm-pass" style="font-weight: 500;">MongoDB
												Password</label> <i class="fa fa-lock prefix white-text"></i> <input
												type="password" class="form-control" id="mongopassword">
												<br>
										</div>
										<div class="text-center">
											<button type="button" class="btn btn-primary" id="TestTarget"
												style="margin-top: 54px;">Test Target</button>

										</div>
								</div>
							</div>
						</div>
						<!-- Grid column -->
					</div>



				</div>

				<br>
<button type="button" class="btn btn-success" id="viewlogs" style="margin-left:2%;">Viewlogs</button>
					<button type="button" class="btn btn-success" id="submit"
						style="margin-left: 37%;">Submit</button>
					<button type="button" class="btn btn-success" id="status"
						style="margin-left: 32%;">Migration Status</button>
			</div>




		</div>

		<form action="Status.jsp" method="post">

			<input type="hidden" name="res" id="tableres" value="" />
			<button id="b1">demo</button>

		</form>

		<script
			src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
			integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
			crossorigin="anonymous"></script>

		<script>
	$.ajaxSetup({
	// Disable caching of AJAX responses
	cache : false
	});
	function move() {
		var elem = document.getElementById("myBar");
		var width = 1;
		var id = setInterval(frame, 10);
		function frame() {
			if (width >= 100) {
				clearInterval(id);
			} else {
				width++;
				elem.style.width = width + '%';
			}
		}
	}

	$("#success-alert").hide();
	$("#alter-info").hide();
	$("#alter-danger").hide();
	$("#alter-dangerM").hide();
	$("#alert-infoMon").hide();
	$("#alter-dangerMon").hide();
	$(".progress").hide();
	
	var mysqldbsize;
	$(document).on("click","#Testsource",function() {
		//tstsrc();
		var sqldb = $("#sqldb").val();
		var jdbcurl = $("#jdbcurl").val();
		var usernamejdbc = $("#usernamejdbc").val();
		var jdbcpassword = $("#jdbcpassword").val();
		var appID = $("#appid").val();

		if (jdbcurl == null || jdbcurl == "" && usernamejdbc == null || usernamejdbc == "" && jdbcpassword == null || jdbcpassword == ""  && appID == null || appID =="") {
									// && jdbctab == null || jdbctab =="" 
									// $("#sqldb").notify("All fields are required","error"); 
					$("#alter-danger").html("<strong><b>Please enter all required fields</b></strong>");
					$("#alter-danger").fadeTo(5000, 500).slideUp(500,function() {
								$("#alter-danger").slideUp(500);
								});

		} else {
				//$(".progress").show();

				$.ajax({	
					url : "Migration",
					type : "POST",
					data : {						
						sqldb : sqldb,
						jdbcurl : jdbcurl,
						usernamejdbc : usernamejdbc,
						jdbcpassword : jdbcpassword,
						appID : appID
					},
					cache : false,
					success : function(responseText) {
						//$(".progress").hide();
						var sqdb=responseText.split("@");
			var sqldb=sqdb[0];
			mysqldbsize = sqdb[1];
			console.log(sqdb);
			console.log(sqldb);
			console.log(mysqldbsize);
													
						if (sqldb === "success") {
								$("#success-alert").html("<strong><b>Test Source has Successful</b></strong>");
								$("#success-alert").fadeTo(5000,500).slideUp(500,function() {
											$("#success-alert").slideUp(500);
								});
						}else {
								$("#alter-danger").html("<strong><b>Test source failed</b></strong>");
								$("#alter-danger").fadeTo(5000,500).slideUp(500,function() {
										$("#alter-danger").slideUp(500);
								});
							}
					}

				});
			}

	});
	$(document).on("click","#TestTarget",function(){
			$("#success-alert").hide();
			var mongodb = $("#mongo").val();
			var mongourl = $("#mongodburl").val();
			var usernamemongo = $("#usernamemongo").val();
			var mongopassword = $("#mongopassword").val();

			//if (mongourl == null || mongourl == "" && usernamemongo == null || usernamemongo == "" && mongopassword == null || mongopassword == "") {
									/* $("#sqldb").notify("All fields are required","error"); */
				if (mongourl == null || mongourl == "" ) {
									$("#alter-danger").html("<strong><b>Please enter all required fields</b></strong>");
									$("#alter-danger").fadeTo(5000, 500).slideUp(500,function() {
											$("#alter-danger").slideUp(500);
									});

			} else {
				//$(".progress").show();

				$.ajax({
					url : "Migration",
					type : "POST",
					data : {
						mongodb : mongodb,
						mongourl : mongourl,
						usernamemongo : usernamemongo,
						mongopassword : mongopassword
					},
					cache : false,
					success : function(responseText) {
						$(".progress").hide();
						var mondb = responseText;
						console.log(mondb);
						if (mondb == "success") {
							$("#success-alert").html("<strong><b>Test Target has Successful</b></strong>");
							$("#success-alert").fadeTo(5000,500).slideUp(500,function() {
									$("#success-alert").slideUp(500);
							});
				
												
						}else {
							$("#alter-danger").html("<strong><b>Test Target failed</b></strong>");
							$("#alter-danger").fadeTo(5000,500).slideUp(500,function() {
										$("#alter-danger").slideUp(500);
							});
						}
					}

				});
			}
		});

		$(document).on("click","#submit",function() {

				console.log("sadfs");
				var sqldb = $("#sqldb").val();
				var jdbcurl = $("#jdbcurl").val();
				var usernamejdbc = $("#usernamejdbc").val();
				var jdbcpassword = $("#jdbcpassword").val();
				var appID = $("#appid").val();

				var mongodb = $("#mongo").val();
				var mongourl = $("#mongodburl").val();
				var usernamemongo = $("#usernamemongo").val();
				var mongopassword = $("#mongopassword").val();
				

			/* 	console.log("sqldb:::" + sqldb + "\n"
						+ "jdbc::" + jdbcurl + "\n"
						+ "usernamejdbc:::" + usernamejdbc
						+ "\n" + "jdbcpassword:::"
						+ jdbcpassword + "\n" + "mongodb::::"
						+ mongodb + "\n" + "mongourl:::"
						+ mongourl + "\n" + "usernamemongo:::"
						+ usernamemongo + "\n"
						+ "mongopassword:::" + mongopassword
						+ "\n" + "jdbctab:" + jdbctab); */

				if (jdbcurl == null || jdbcurl == "" && usernamejdbc == null || usernamejdbc == ""
						&& jdbcpassword == null || jdbcpassword == "" && mongourl == null || mongourl == ""
						&& usernamemongo == null || usernamemongo == "" && mongopassword == null || mongopassword == ""
							&& appID == null || appID == "") {
					/* $("#sqldb").notify("All fields are required","error"); */
					$("#alter-danger").html("<strong><b>Please enter all required fields</b></strong>");
					$("#alter-danger").fadeTo(5000, 500).slideUp(500,function() {
							$("#alter-danger").slideUp(500);
					});
				} else {
					//$(".progress").show();
					$.ajax({

						url : "whole",
						type : "POST",
						data : {
							sqldb : sqldb,
							jdbcurl : jdbcurl,
							usernamejdbc : usernamejdbc,
							jdbcpassword : jdbcpassword,
							mongodb : mongodb,
							mongourl : mongourl,
							usernamemongo : usernamemongo,
							mongopassword : mongopassword,
							appID : appID
						},
						cache : false,
						
						/// before calling the ajax response the progress bar will start 
					 	beforeSend: function(){
						     var counter = mysqldbsize;
						      // var sqllen = counter.length;
						       console.log("ddddddddddddddddddddddddd"+counter);
						       //console.log("sssssssss"+sqllen);
						       var c = 0;
						     //  var status = c*(100/sqllen);
						       var ss = counter/5;
						       console.log("ssssssddddd"+ss)
						       setInterval(function() {
						    	
										if(counter <= (counter%25)||counter <= (counter%50)||counter <= (counter%75)||counter <= (counter%100)){
										$(".progress").css('width', counter + '%').text(parseInt(counter) + " %"); // you want to change the width of the inner bar
								
								    $(".progress").show();
								 
								   counter = counter % 100+5;
								 
										}   }, 100);  
						  
								
						 	   },  
										/////////
						success : function(responseText) {
							$(".progress").hide();
							console.log("logggg    "+responseText);
							var fin = responseText.split("@");
							var succ = fin[0];
							var collen = fin[1];
							var tablen = fin[2];
							console.log("fin:::" + fin);
							console.log("succ:::" + fin[0]);
							console.log("collen:::" + fin[1]);
							console.log("tablen:::" + fin[2]);
							if (succ == "success") {
								
								//If response becomes successful the progress bar reaches to 100%
								 var counter = 100;
								 $(".progress").css('width', counter + '%').text(counter + " %");
								 $(".progress").show();
								
								$("#success-alert").html("<strong><p>Migration Successful</p></strong> "+ sqldb+ "tables count:- "
														+ tablen + "</p>Tables migrated into MongoDB count:- " + collen);
								$("#success-alert").fadeTo(10000,500).slideUp(500,function() {
									$("#success-alert").slideUp(500);
									$(".progress").hide();
									location.reload(true);
								});			            			
							}else if (succ == "fail") {
								$(".progress").hide(); 	
								$("#alter-danger").html("<strong><b>Migration failed</b></strong>");
								 $("#alter-danger").fadeTo(5000,500).slideUp(500,function() {
									 
									 $("#alter-danger").slideUp(500);
										
										//location.reload(true);
								});
							 } else {
								 $('#progress').hide();
								
									myFunction(succ);
									//$(".progress").hide(); 
								
								}

						}

					});

				}

			});

	function myFunction(tnames) {
		$(".progress").hide();
		$("#myModal").modal({backdrop : "static"});
		$("#myModal").find(".modal-header").append("<strong><p>Following Table names are existed in MongoDB</p><strong>");

				/* <br>(Continue: Migrate the tables which are not in MongoDB)<br>(Drop and continue: It will drop and migrate the tables which are checked and also Migrate the tables which are not in MongoDB)" */

				/* Converting String to Array and split it with "," and then create checkbox in the bootstrap model for continue or drop the tables */
				var string = tnames;
				var arrs = new Array();
				arrs = string.split(",");
				var i;
				var arr = "";
				for (i = 0; i < arrs.length; i++) {
					arr += "<label><input type='checkbox' class='check' value='"+arrs[i]+"' name='chectabname' />"
							+ arrs[i] + "</label><br>";
					$(".progress").hide();
				}

				$("#myModal").find(".modal-body").append(arr);
				$(".progress").hide();

			}

			/*
			 * Disabled the Drop & Continue button until checkbox is checked
			 */
			$(document).on("click", ".check", function() {
				$(".progress").hide();
				var checked_status = this.checked;
				var j = myfun();

				if (j != 0 && j != null) {
					$("#Drop").removeAttr("disabled");

				} else {

					$("#Drop").attr("disabled", "disabled");
				}

			});

			/*
			 * Disabled the continue button when checkbox is checked
			 */
			$(document).on("click", ".check", function() {
				$(".progress").hide();
				var checked_status = this.checked;
				var j = myfun();

				if (j != 0 && j != null) {
					$("#Continue").attr("disabled", "disabled");

				} else {

					$("#Continue").removeAttr("disabled");
				}

			});

			function myfun() {
				$(".progress").hide();
				var chktab = "";
				var i = 0;
				$.each($("input[name='chectabname']:checked"), function() {
					//chktab.push($(this).val());
					chktab += $(this).val() + ",";
					i++;

				});
				return i;
			}

			$(document).on("click","#Continue",function() {
				
				var sqldb = $("#sqldb").val();
				var jdbcurl = $("#jdbcurl").val();
				var usernamejdbc = $("#usernamejdbc").val();
				var jdbcpassword = $("#jdbcpassword").val();
				var jdbctab = $("#jdbctable").val();

				var mongodb = $("#mongo").val();
				var mongourl = $("#mongodburl").val();
				var usernamemongo = $("#usernamemongo").val();
				var mongopassword = $("#mongopassword").val();

				var unchktab = "";
				$.each(
					$("input[name='chectabname']:not(:checked)"),
					function() {
									//chktab.push($(this).val());
									unchktab += $(this).val()
											+ ",";

								});

				console.log("unchktab:::" + unchktab);

				$.ajax({

							url : "Continue",
							type : "POST",
							data : {
								unchktabname : unchktab,
								sqldb : sqldb,
								jdbcurl : jdbcurl,
								usernamejdbc : usernamejdbc,
								jdbcpassword : jdbcpassword,
								mongodb : mongodb,
								mongourl : mongourl,
								usernamemongo : usernamemongo,
								mongopassword : mongopassword,
								jdbctab : jdbctab
							},

							success : function(responseText) {
								
								var fin = responseText
										.split("@");
								var succ = fin[0];
								var collen = fin[1];
								var tablen = fin[2];
								console.log("fin:::" + fin);
								console.log("succ:::" + fin[0]);
								console.log("collen:::" + fin[1]);
								console.log("tablen:::" + fin[2]);
								if (succ == "success") {
									 var counter = 100;
									 $(".progress").css('width', counter + '%').text(counter + " %");
									 $(".progress").show();
									$("#success-alert").html("<strong><p>Migration Successful</p></strong>" + sqldb
															+ "tables count:- " + tablen
															+ "</p>Tables migrated into MongoDB count:- "
															+ collen);
									$("#success-alert").fadeTo(10000, 500).slideUp(500,function() {
											$("#success-alert").slideUp(500);
											location.reload(true);
									});
								}

								else if (succ == "fail") {
									 $(".progress").hide();
									$("#alter-danger").html("<strong><b>Migration failed</b></strong>");
									$("#alter-danger").fadeTo(5000, 500).slideUp(500,function() {
											$("#alter-danger").slideUp(500);
											location.reload(true);
									});
								} else {
									 $(".progress").hide();
									$("#alter-info").html("<strong><b>All tables are already existed in MongoDB</b></strong>");
									$("#alter-info").fadeTo(5000, 500).slideUp(500,function() {
											$("#alter-info").slideUp(500);
											location.reload(true);
									});

								}
							}
						});
			});
							

			$(document).on("click","#Drop",function() {

				
				var sqldb = $("#sqldb").val();
				var jdbcurl = $("#jdbcurl").val();
				var usernamejdbc = $("#usernamejdbc").val();
				var jdbcpassword = $("#jdbcpassword").val();
				var jdbctab = $("#jdbctable").val();

				var mongodb = $("#mongo").val();
				var mongourl = $("#mongodburl").val();
				var usernamemongo = $("#usernamemongo").val();
				var mongopassword = $("#mongopassword").val();

				var chktab = ""; //= [];

				$.each($("input[name='chectabname']:checked"),
						function() {
							//chktab.push($(this).val());
							chktab += $(this).val() + ",";

						});

				console.log("chktab:::" + chktab);

				var unchktab = "";
				$.each($("input[name='chectabname']:not(:checked)"),
								function() {
									//chktab.push($(this).val());
									unchktab += $(this).val()
											+ ",";

								});

				console.log("unchktab:::" + unchktab);

				$.ajax({

							url : "DropAndContinue",
							type : "POST",
							data : {
								chktabname : chktab,
								unchktabname : unchktab,
								sqldb : sqldb,
								jdbcurl : jdbcurl,
								usernamejdbc : usernamejdbc,
								jdbcpassword : jdbcpassword,
								mongodb : mongodb,
								mongourl : mongourl,
								usernamemongo : usernamemongo,
								mongopassword : mongopassword,
								jdbctab : jdbctab
							},

							beforeSend: function(){
							     var counter = mysqldbsize;
							      // var sqllen = counter.length;
							       console.log("ddddddddddddddddddddddddd"+counter);
							       //console.log("sssssssss"+sqllen);
							       var c = 0;
							     //  var status = c*(100/sqllen);
							       var ss = counter/5;
							       console.log("ssssssddddd"+ss)
							       setInterval(function() {
							    	
											if(counter <= (counter%25)||counter <= (counter%50)||counter <= (counter%75)||counter <= (counter%100)){
											$(".progress").css('width', counter + '%').text(parseInt(counter) + " %"); // you want to change the width of the inner bar
									
									    $(".progress").show();
									 
									   counter = counter % 100+5;
									 
											}   }, 100);  
							  
									
							 	   },  
							success : function(responseText) {
								
								var fin = responseText
										.split("@");
								var succ = fin[0];
								var collen = fin[1];
								var tablen = fin[2];
								var existablen = fin[3];
								console.log("fin:::" + fin);
								console.log("succ:::" + fin[0]);
								console.log("collen:::"
										+ fin[1]);
								console.log("tablen:::"
										+ fin[2]);
								console.log("existablen:::"
										+ fin[3]);
								if(succ=="success")
								{							
									 var counter = 100;
									 $(".progress").css('width', counter + '%').text(counter + " %");
									 $(".progress").show();
									$("#success-alert").html("<strong><p>Migration Successful</p></strong>" +sqldb+ " tables count:- " +tablen+ "</p>Existed tables in MongoDB count:- "+existablen + "</p>Tables migrated into MongoDB count:- " +collen);
									$("#success-alert").fadeTo(13000, 500).slideUp(500, function(){
									    $("#success-alert").slideUp(500);
									    location.reload(true);
									     });	
								}

								else {
									 $(".progress").hide();
									$("#alter-danger").html("<strong><b>Migration failed</b></strong>");
									$("#alter-danger").fadeTo(5000, 500).slideUp(500,function() {
											$("#alter-danger").slideUp(500);
											location.reload(true);
									});
								}
							}
						});
			});				
			
$(document).on("click","#status",function() {	
	
	var sqldb = $("#sqldb").val();
	var jdbcurl = $("#jdbcurl").val();
	var usernamejdbc = $("#usernamejdbc").val();
	var jdbcpassword = $("#jdbcpassword").val();
	var jdbctab = $("#jdbctable").val();

	var mongodb = $("#mongo").val();
	var mongourl = $("#mongodburl").val();
	var usernamemongo = $("#usernamemongo").val();
	var mongopassword = $("#mongopassword").val();
	
	console.log("sqldb:::" + sqldb + "\n"
			+ "jdbc::" + jdbcurl + "\n"
			+ "usernamejdbc:::" + usernamejdbc
			+ "\n" + "jdbcpassword:::"
			+ jdbcpassword + "\n" + "mongodb::::"
			+ mongodb + "\n" + "mongourl:::"
			+ mongourl + "\n" + "usernamemongo:::"
			+ usernamemongo + "\n"
			+ "mongopassword:::" + mongopassword
			+ "\n" + "jdbctab:" + jdbctab);
	
	if (jdbcurl == null || jdbcurl == "" && usernamejdbc == null || usernamejdbc == ""
		&& jdbcpassword == null || jdbcpassword == "" && mongourl == null || mongourl == ""
		&& usernamemongo == null || usernamemongo == "" && mongopassword == null || mongopassword == "") {
	/* $("#sqldb").notify("All fields are required","error"); */
	$("#alter-danger")
			.html(
					"<strong><b>Please enter all required fields</b></strong>");
	$("#alter-danger").fadeTo(5000, 500)
			.slideUp(
					500,
					function() {
						$("#alter-danger")
								.slideUp(500);
					});

} else {
	
	$.ajax({

		url : "MigrationStatus",
		type : "POST",
		data : {
			sqldb : sqldb,
			jdbcurl : jdbcurl,
			usernamejdbc : usernamejdbc,
			jdbcpassword : jdbcpassword,
			mongodb : mongodb,
			mongourl : mongourl,
			usernamemongo : usernamemongo,
			mongopassword : mongopassword,
			jdbctab : jdbctab
		},
		
		 success : function(responseText) {
			
			var fin = responseText.split("@");
			var succ = fin[0];
			var htm = fin[1];
			
			console.log("fin:::" + fin);
			console.log("succ:::"+ fin[0]);
			console.log("collen:::"+ fin[1]);
			
			
			$("#tableres").val(htm);
			$("#b1").click();
	/* 		
			$.ajax({

				url : "Status.jsp",
				type : "POST",
				data : {html:fin[1]
					
				}
			
			}); */
			// window.open("Status.jsp");
			//window.location.href="Status.jsp?html="+htm;
	} 
		
	});
	
}
	
	
});


$(document).on("click","#viewlogs",function(){
	   
	
	var ss= "<br></br>";
        $("#logs").find(".modal-body").load("hello.txt",function(){
        	//$("#logs").append(ss);
            $('#logs').modal('show');
    });

    
});

		</script>
		</div>
</body>
</html>
