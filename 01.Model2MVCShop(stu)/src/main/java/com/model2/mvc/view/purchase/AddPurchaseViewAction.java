// 구매를 위한 화면 요청
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
		
		// jsp파일에서 prodNo 기준으로 가져오게 끔 되어있다
		// getProduct.jsp에서 구매버튼 누를 시 제일 먼저 실행 됨
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		// session 열어주기
		HttpSession session = request.getSession();
		
		// ProductServiceImpl의 getProduct를 가져온다 -- productDAO.findProduct
		ProductService service = new ProductServiceImpl();
		ProductVO productVO = service.getProduct(prodNo);
		request.setAttribute("productVO", productVO);
		
		// session을 열고 userId를 가져온다
		UserVO userVO = new UserVO();
		userVO.setUserId(request.getParameter("userId"));	
		session.setAttribute("userVO", userVO);

		return "forward:/purchase/addPurchaseView.jsp";
	}
}