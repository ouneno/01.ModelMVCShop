// ���Ÿ� ���� ȭ�� ��û
package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class AddPurchaseViewAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// jsp���Ͽ��� prodNo �������� �������� �� �Ǿ��ִ�
		// getProduct.jsp���� ���Ź�ư ���� �� ���� ���� ���� ��
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		// session �����ֱ�
		HttpSession session = request.getSession();
		
		// ProductServiceImpl�� getProduct�� �����´� -- productDAO.findProduct
		ProductService service = new ProductServiceImpl();
		ProductVO productVO = service.getProduct(prodNo);
		request.setAttribute("productVO", productVO);
		
		// session�� ���� userId�� �����´�
		UserVO userVO = new UserVO();
		userVO.setUserId(request.getParameter("userId"));	
		session.setAttribute("userVO", userVO);

		return "forward:/purchase/addPurchaseView.jsp";
	}
}