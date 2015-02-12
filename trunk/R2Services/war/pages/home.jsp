<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.r2.appservices.dao.TrackerDAO"%>
<%@ page import="com.r2.appservices.dao.UserLocation"%>
<%@ page import="com.r2.appservices.dao.User"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<!-- The HTML 4.01 Transitional DOCTYPE declaration-->
<!-- above set at the top of the file will set     -->
<!-- the browser's rendering engine into           -->
<!-- "Quirks Mode". Replacing this declaration     -->
<!-- with a "Standards Mode" doctype is supported, -->
<!-- but may lead to some differences in layout.   -->

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../css/normalize.css" />
<link rel="stylesheet" type="text/css" href="../css/login.css" />
<script type="text/javascript" src="../js/jquery-1.11.0.min.js"></script>

<title>Happiest Minds</title>
<script type="text/javascript"
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDL3Ug0kmiNtrFXaFpmQLfziX21t68PRVI&sensor=false">
    </script>

<script type="text/javascript">
    var map;
    var zoomlevel = 11;
    var center = new google.maps.LatLng(12.9016711, 77.6057053);
    var timer = null;
      function initialize() {
    	  console.log ( 'initialize called' + new Date() );

    	  if(map != null){
    		  zoomlevel = map.getZoom(); 
    		  center = map.getCenter();
    	  }
        var mapOptions = {
          center: center,
          zoom: zoomlevel
        };
        map = new google.maps.Map(document.getElementById("map-canvas"),
            mapOptions);
        
        $('#users').empty();
        $('#users').append("<ul id='userList' class='userList'></ul>");


        <%
        
       
        List<UserLocation> locationList = TrackerDAO.INSTANCE.listCurrentLocations();
        UserLocation location = null;
        for (int i = 0; i < locationList.size(); i++) {
        	//out.print("var imei='" + location.getUserId() +"';\n");
        	 String contents = "<input type=\"button\" text=\"Ring\" name=\"image\" value=\"Ring\" onclick=\"ringthephone()\">" +
           		  "$1" + " Updated on " + "$2" ;
        	location = locationList.get(i);
	        out.print("var myLatlng" + i +" = new google.maps.LatLng(" + location.getLatitude() + "," + location.getLongitude() + ");\n");
	        out.print("var marker" + i +" = new google.maps.Marker({position:myLatlng" + i +"\n, title:'" + location.getUserId()
	                     + "'\n,map: map\n});\n");
	        out.print("\n");
	        
	        contents = contents.replace("$1", location.getFirstame());
	        contents = contents.replace("$2", location.getUpdatedAt().toString());
	        out.print("var infowindow" + i +" = new google.maps.InfoWindow({\n");
	        out.print(" content:'" + contents +"',\n");
	        out.print("  maxWidth: 300\n");
	        out.print("});\n");
	        out.print("\n");
	        
	        out.print("google.maps.event.addListener(marker" + i +", 'click', function() {\n");
	        out.print("infowindow" + i +".open(map,marker" + i +");\n");
	        out.print(" });\n");
	        out.print("\n");
	        out.print("$(\"#userList\").append(\"<li>"+ location.getFirstame() +"</li>\")");
	        
 		}
        %>
        
        
        timer = setTimeout('initialize()',60000);
       
      }
      
      function ringthephone(imei){
    	  
      }
     
      google.maps.event.addDomListener(window, 'load', initialize);
      
    </script>
<script type="text/javascript">
     </script>
</head>

<body onload="">
	<h1 id="header">Happiest Minds Asset Tracking System</h1>
	<div id="menu" class="menu">
		<b>Devices on Map</b>
		<hr>
		<div id="users" class="users"></div>
	</div>
	
	<div id="map-canvas"
		style="width: 80%; height: 500px; border: 2px solid; margin: 0 auto; border-radius: 20px; box-shadow: 10px 10px 5px #888888;">
	</div>
</body>
</html>
