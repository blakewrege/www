<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><HTML><HEAD>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=iso 8859-1">
<TITLE>server webcam</TITLE>
<STYLE type="text/css">
<!--
BODY {
	FONT-SIZE: small; COLOR: #000000; FONT-FAMILY: "trebuchet ms", Verdana, Arial, Helvetica, sans-serif; BACKGROUND-COLOR: #FFFFFF
}

H1 {
	FONT-SIZE: large;
}

A:hover {
	COLOR: #ff9900; TEXT-DECORATION: underline;
}

.footer {
	TEXT-ALIGN: center; FONT-SIZE: smaller;
}

.footer IMG {
	BORDER: 1px solid #888;
}
-->
</STYLE>
</HEAD>
<BODY bgColor="#ffffff" text="#000000">
<H1 align="center"> HELLO WORLD </H1>
<P>
<B>UGLYDUCK Web Server is functioning </B> </P>
<P>
Please include in your web pages (at least the first) the <b><i>'Powered by giggles'</i></b> banner to promote the use of the software.
</P>


<head>
    <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
    <script>
        $(function() {
            $.get('read.txt', function(data) {
                $('#text-file-container').html(data);
            });
        });
    </script>
</head>
<body>
<div id="text-file-container"></div>
</body>






<?php
$myfile = fopen("read.txt", "r") or die("Unable to open file!");
$test = fread($myfile,filesize("read.txt"));
fclose($myfile);
?>
<table frame="box">
  <tr>
    <th>Messege Board</th>
  </tr>
  <tr>
    <td><?php echo "$test";?></td>
  </tr>
</table>
<br><br>


<button type="button"
onclick="document.getElementById('demo').innerHTML = Date()">
Click me to display Date and Time.</button>
<p id="demo"></p>



<script>
function readfile(f) {
    var reader = new FileReader();  // Create a FileReader object
    reader.readAsText(f);           // Read the file
    reader.onload = function() {    // Define an event handler
        var text = reader.result;   // This is the file contents
        var out = document.getElementById("output");    // Find output element
        out.innerHTML = "";                             // Clear it
        out.appendChild(document.createTextNode(text)); // Display file contents
    }
    reader.onerror = function(e) {  // If anything goes wrong
        console.log("Error", e);    // Just log it
    };
}
</script>
Select the file to display:
<input type="file" onchange="readfile(this.files[0])"></input>
<pre id="output"></pre>


<script type="text/javascript">
function LoadFile() {
    var oFrame = document.getElementById("frmFile");
    var strRawContents = oFrame.contentWindow.document.body.childNodes[0].innerHTML;
    while (strRawContents.indexOf("\r") >= 0)
        strRawContents = strRawContents.replace("\r", "");
    var arrLines = strRawContents.split("\n");
    alert("File " + oFrame.src + " has " + arrLines.length + " lines");
    for (var i = 0; i < arrLines.length; i++) {
        var curLine = arrLines[i];
        alert("Line #" + (i + 1) + " is: '" + curLine + "'");
    }
}
</script>

<iframe src="read.txt" width="500" height="25"></iframe>



<P>Links to stuff:</p>
<li><a href="http://ccowmu.org/~alex/door.php">Doorbot</a></li>
<li><a href="http://t4ls.duckdns.org:8080/">To Do List</a></li>
<li><a href="https://yakko.cs.wmich.edu/~gigglesbw4/stuff">Stuff</a></li>
<br><br><br>
<img src="http://t4ls.duckdns.org:8090/cam_1.cgi?resolution=4CIF&amp;dummy=1405525467514" height="480" width="704" onError="this.onerror=null;this.src='image.png';" />
<img src="webcam.jpg">
<hr>
</P>
<hr>
<CENTER>
<img src="giggles.jpg">
</P>
<P CLASS="footer">
<P CLASS="footer">
<A HREF="http://www.aprelium.com" ><ALT="Powered by Abyss Web Server" TITLE="Powered by Abyss Web Server" BORDER="0" WIDTH="88" HEIGHT="31"></A>
</P>
</CENTER>
</BODY></HTML>
