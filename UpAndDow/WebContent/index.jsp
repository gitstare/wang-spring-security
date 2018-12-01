<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="UploadServet" method="post" enctype="multipart/form-data">
        学号:<input name="sno" ><br>
        姓名:<input name="sname" ><br>
        上传照片<input type="file" name="spicture"><br>
        <input type="submit" value="注册">
</form>
<a href="DownloadServer?filename=mm.jpg">111</a>
</body>
</html>