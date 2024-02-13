<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
<div id="controls">
   <button onclick="left()" >Left</button>
   <button onclick="rotate()" >Rotate</button>
   <button onclick="drop()" >Drop</button>
   <button onclick="newgame()" >NewGame</button>
   <button onclick="save()" >Save</button>
   <button onclick="restart()" >Restart</button>
   <button onclick="right()" >Right</button>
</div>

 <script>


 function newgame() {
  $.ajax({
              success: function () {
                  window.location='/start';
              }
          });
  }



 function falldown() {
         $.ajax({
             success: function () {
                 window.location='/logic?click=0';
             }
         });
     }

   var myTimer =setInterval(falldown, 500);
   if(!${isGameOn}) clearInterval(myTimer);



 </script>
</body>
</html>