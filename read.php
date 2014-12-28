<table frame="box">
  <tr>
    <th>Messege Board</th>
  </tr>
<?php
$count = 1;
$myfile = fopen("read.txt", "r") or die("Unable to open file!");
while(!feof($myfile)) {
//  if(fgets($myfile) === NULL){
//   echo "is null";
//   }else{
   echo "<tr><td>".$count.". ".fgets($myfile) . "</td></tr>";
   $count = $count + 1;
//  }
}
$test = fread($myfile,filesize("read.txt"));
fclose($myfile);
?>
</table>
<br><br>

