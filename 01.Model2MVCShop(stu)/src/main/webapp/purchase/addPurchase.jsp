<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="com.model2.mvc.service.user.vo.*" %>
<%@ page import="com.model2.mvc.service.product.vo.*" %>
<%@ page import="com.model2.mvc.service.purchase.vo.*" %>
<% UserVO userVO = (UserVO)session.getAttribute("userVO"); %>
<% System.out.println("addPurchase.jsp____userVO : "+userVO); %>
<% ProductVO productVO = (ProductVO)request.getAttribute("productVO"); %>
<% System.out.println("addPurchase.jsp____productVO : "+productVO); %>
<% PurchaseVO purchaseVO = (PurchaseVO)request.getAttribute("purchaseVO"); %>
<% System.out.println("addPurchase.jsp____purchaseVO : "+purchaseVO); %>

<html>
<head>
<title>구매요청</title>
</head>

<body>

<form name="updatePurchase" action="/updatePurchaseView.do?tranNo=<%=purchaseVO.getTranNo()%>" method="post">


다음과 같이 구매가 되었습니다.

<table border=1>
	<tr>
		<td>물품번호</td>
		<td><%=productVO.getProdNo()%></td>
		<td></td>
	</tr>
	<tr>
		<td>구매자아이디</td>
		<td><%=userVO.getUserId()%></td>
		<td></td>
	</tr>
	<tr>
		<td>구매방법</td>
		<td><%=purchaseVO.getPaymentOption()%></td>
		<td></td>
	</tr>
	<tr>
		<td>구매자이름</td>
		<td><%=purchaseVO.getReceiverName()%></td>
		<td></td>
	</tr>
	<tr>
		<td>구매자연락처</td>
		<td><%=purchaseVO.getReceiverPhone()%></td>
		<td></td>
	</tr>
	<tr>
		<td>구매자주소</td>
		<td><%=purchaseVO.getDlvyAddr()%></td>
		<td></td>
	</tr>
		<tr>
		<td>구매요청사항</td>
		<td><%=purchaseVO.getDlvyRequest()%></td>
		<td></td>
	</tr>
	<tr>
		<td>배송희망일자</td>
		<td><%=purchaseVO.getDlvyDate()%></td>
		<td></td>
	</tr>
</table>
</form>

</body>
</html>