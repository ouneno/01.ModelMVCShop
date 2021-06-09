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
<title>���ſ�û</title>
</head>

<body>

<form name="updatePurchase" action="/updatePurchaseView.do?tranNo=<%=purchaseVO.getTranNo()%>" method="post">


������ ���� ���Ű� �Ǿ����ϴ�.

<table border=1>
	<tr>
		<td>��ǰ��ȣ</td>
		<td><%=productVO.getProdNo()%></td>
		<td></td>
	</tr>
	<tr>
		<td>�����ھ��̵�</td>
		<td><%=userVO.getUserId()%></td>
		<td></td>
	</tr>
	<tr>
		<td>���Ź��</td>
		<td><%=purchaseVO.getPaymentOption()%></td>
		<td></td>
	</tr>
	<tr>
		<td>�������̸�</td>
		<td><%=purchaseVO.getReceiverName()%></td>
		<td></td>
	</tr>
	<tr>
		<td>�����ڿ���ó</td>
		<td><%=purchaseVO.getReceiverPhone()%></td>
		<td></td>
	</tr>
	<tr>
		<td>�������ּ�</td>
		<td><%=purchaseVO.getDlvyAddr()%></td>
		<td></td>
	</tr>
		<tr>
		<td>���ſ�û����</td>
		<td><%=purchaseVO.getDlvyRequest()%></td>
		<td></td>
	</tr>
	<tr>
		<td>����������</td>
		<td><%=purchaseVO.getDlvyDate()%></td>
		<td></td>
	</tr>
</table>
</form>

</body>
</html>