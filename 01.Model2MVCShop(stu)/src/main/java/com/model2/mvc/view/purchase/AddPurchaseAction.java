// 구매를 위한 화면 요청
package com.model2.mvc.view.purchase;

import java.security.Provider.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.dao.UserDAO;
import com.model2.mvc.service.user.vo.UserVO;

public class AddPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("AddPurchaseAction Start__________");
		
		HttpSession session = request.getSession();
		
		// 1. PurchaseDAO의 insertPurchase불러오기
		PurchaseVO purchaseVO = new PurchaseVO();
		purchaseVO.setPurchaseProd(new ProductVO().setProdNo(Integer.parseInt("prodNo")));
		purchaseVO.setBuyer(new UserVO().setUserId(request.getParameter("userId")));
		purchaseVO.setPaymentOption(request.getParameter("paymentOption")); 		// 구매방법
		purchaseVO.setReceiverName(request.getParameter("receiverName")); 			// 구매자이름
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone")); 		// 구매자연락처
		purchaseVO.setDlvyAddr(request.getParameter("dlvyAddr"));					// 배송지
		purchaseVO.setDlvyRequest(request.getParameter("dlvyRequest")); 			// 요청사항
		purchaseVO.setDlvyDate(request.getParameter("dlvyDate")); 					// 희망일자
		
		// 2. PurchaseServiceImpl과 PurchaseService 연결 도와주기
		PurchaseService service = new PurchaseServiceImpl();
		service.addPurchase(purchaseVO);
		System.out.println("ENDDDDDDDDDDDDDDDDDDDDDDD");	
		// 3. UserVO.userId
		UserVO userVO = new UserVO();
		userVO.setUserId(request.getParameter("userId"));
		
		// 4. ProductVO.prodNo
		ProductVO productVO = new ProductVO();
		productVO.setProdNo(Integer.parseInt(request.getParameter("prodNo")));

		request.setAttribute("purchaseVO", purchaseVO);
		session.setAttribute("productVO", productVO);
		session.setAttribute("userVO", userVO);

		return "forward:/product/addPurchase.jsp";

	}

}