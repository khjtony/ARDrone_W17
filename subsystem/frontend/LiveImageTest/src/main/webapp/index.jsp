<%@ page import = "java.util.*" %>

<html>
<body>
<h2>Hello World!</h2>
<img alt="asdf" sytle="width:50%;" src="/LiveImageTest/LiveImage">

<%
	List styles = (List)request.getAttribute("styles");
	Iterator it = styles.iterator();
	while(it.hasNext()){
		out.print("<br>try:" + it.next());
		}
%>

</body>
</html>
