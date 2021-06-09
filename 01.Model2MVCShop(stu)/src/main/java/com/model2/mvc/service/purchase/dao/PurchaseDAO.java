package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.dao.UserDAO;
import com.model2.mvc.service.user.vo.UserVO;

public class PurchaseDAO {
	
	public PurchaseDAO() {
	}

	// 1. 구매창 / addPurchase == insertPurchase
	public static void insertPurchase(PurchaseVO purchaseVO) throws Exception {
		
		Connection con = DBUtil.getConnection();			
		String sql = "INSERT INTO transaction VALUES (SEQ_transaction_prod_no.NEXTVAL,?,?,?,?,?,?,sysdate)";
		PreparedStatement stmt = con.prepareStatement(sql);
		System.out.println("\nPurchaseDAO__________ 1. InsertPurchase Connection END");
		System.out.println("ProdNO : "+purchaseVO);
		
		stmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo()); // 상품번호
		stmt.setString(2, purchaseVO.getBuyer().getUserId());	// 구매자아이디
		stmt.setString(3, purchaseVO.getPaymentOption());		// 구매방법
		stmt.setString(4, purchaseVO.getReceiverName());		// 수령인이름
		stmt.setString(5, purchaseVO.getReceiverPhone());		// 수령인연락처
		stmt.setString(6, purchaseVO.getDlvyAddr());			// 배송지	
		stmt.setString(7, purchaseVO.getDlvyRequest());			// 요청사항
		stmt.setString(8, purchaseVO.getDlvyDate());			// 배송희망일자
		
		System.out.println("3. END");
		
		stmt.executeUpdate();
		
		con.close();
	}
	
	// 2. 구매정보상세조회 / getPurchase == findPurchase
	public PurchaseVO findPurchase(int tranNo) throws Exception {
		
		System.out.println("1. findPurchase__________START");
		
		Connection con = DBUtil.getConnection();
		System.out.println("2. Connection START");

		String sql = "SELECT * FROM transaction WHERE tran_no=?";
			
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);

		ResultSet rs = stmt.executeQuery();
		System.out.println("3. Resultset END");

		PurchaseVO purchaseVO = new PurchaseVO();
		while (rs.next()) {
			purchaseVO = new PurchaseVO();
			purchaseVO.setTranNo(rs.getInt("tran_no"));
			purchaseVO.setPurchaseProd(new ProductDAO().findProduct(rs.getInt("prod_no")));
			purchaseVO.setBuyer(new UserDAO().findUser(rs.getString("buyer_id")));
			purchaseVO.setPaymentOption(rs.getString("payment_option"));
			purchaseVO.setReceiverName(rs.getString("receiver_name"));
			purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
			purchaseVO.setDlvyAddr(rs.getString("dlvy_addr"));
			purchaseVO.setDlvyRequest(rs.getString("dlvy_request"));
			purchaseVO.setTranCode(rs.getString("tran_status_code"));
			purchaseVO.setOrderDate(rs.getDate("order_data"));
			purchaseVO.setDlvyDate(rs.getString("dlvy_date"));
		}
		System.out.println("4. END");		
		con.close();

		return purchaseVO;
	}
	
	// 3. 구매목록보기 / HashMap
	public HashMap<String,Object> getPurchaseList(SearchVO searchVO) throws Exception {

		Connection con = DBUtil.getConnection();		
		String sql = "SELECT * FROM transaction ";

		PreparedStatement stmt = 
			con.prepareStatement( sql,
										ResultSet.TYPE_SCROLL_INSENSITIVE,
										ResultSet.CONCUR_UPDATABLE );
		ResultSet rs = stmt.executeQuery();

		rs.last();
		int total = rs.getRow();
		System.out.println("로우의 수:" + total);

		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(total));

		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());

		ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				PurchaseVO purchaseVO = new PurchaseVO();
				purchaseVO.setPurchaseProd(rs.getInt("prod_no"));
				purchaseVO.setBuyer(rs.getString("buyer_id"));
				purchaseVO.setReceiverName(rs.getString("receiver_name"));
				purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
				purchaseVO.setTranCode(rs.getString("tran_no"));

				list.add(purchaseVO);
				if (!rs.next())
					break;
			}
		}
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());		
	}
	
	// 4. 판매목록보기 / HashMap
	public HashMap<String,Object> getSaleList(SearchVO searchVO) throws Exception {
			
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM transaction";
		
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0")) {
				sql += " WHERE prod_no LIKE '%" + searchVO.getSearchKeyword() +"%'";
			} else if (searchVO.getSearchCondition().equals("1")) {
				sql += "WHERE prod_name LIKE '%" + searchVO.getSearchKeyword() + "%'";
			}
		}
		sql += "order by PROD_NO";

		PreparedStatement stmt = 
			con.prepareStatement( sql,
										ResultSet.TYPE_SCROLL_INSENSITIVE,
										ResultSet.CONCUR_UPDATABLE );
		ResultSet rs = stmt.executeQuery();

		rs.last();
		int total = rs.getRow();
		System.out.println("로우의 수:" + total);

		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(total));

		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());

		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				ProductVO productVO = new ProductVO();
				PurchaseVO purchaseVO = new PurchaseVO();
				productVO.setProdNo(rs.getInt("prod_no"));
				productVO.setProdName(rs.getString("prod_name"));
				productVO.setPrice(rs.getInt("price"));
				productVO.setRegDate(rs.getDate("reg_date"));
				purchaseVO.setTranCode(rs.getString("tran_no"));

				list.add(productVO);
				if (!rs.next())
					break;
			}
		}
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());

		con.close();
			
		return map;
	}
/*	
	// 5. 구매정보수정
	public void updatePurchase(PurchaseVO purchaseVO) throws Exception {
		
	}
	
	// 6. 배송상태수정
	public void updateTranCode(PurchaseVO purchaseVO) throws Exception {
		
		Connection con = DBUtil.getConnection();	
		String sql = "UPDATE transaction TRAN_STATUS_CODE=? WHERE TRAN_NO=?";
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, purchaseVO.getTranCode());
		stmt.setInt(2, purchaseVO.getTranNo());

		stmt.executeUpdate();
		
		con.close();
	}
*/
		
		
} // end of class
	
