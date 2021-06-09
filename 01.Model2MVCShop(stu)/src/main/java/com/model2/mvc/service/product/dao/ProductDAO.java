package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;

public class ProductDAO {
	
	// Constructor
	public ProductDAO() {
	}
	
	public void insertProduct(ProductVO productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "insert into PRODUCT values (seq_product_prod_no.NEXTVAL,?,?,?,?,?,sysdate)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, productVO.getProdName());									// 상품명
		stmt.setString(2, productVO.getProdDetail());								// 상세정보
		stmt.setString(3, productVO.getManuDate().replaceAll("[\\s\\-()]", ""));	// 제조일자
		stmt.setInt(4, productVO.getPrice());										// 가격
		stmt.setString(5, productVO.getFileName());									// 이미지파일
		stmt.executeUpdate();
		
		con.close();
	}
	
	public ProductVO findProduct(int prodNo) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "select * from PRODUCT where PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		ProductVO productVO = null;
		while (rs.next()) {
			productVO = new ProductVO();
			productVO.setProdNo(rs.getInt("PROD_NO"));
			productVO.setProdName(rs.getString("PROD_NAME"));
			productVO.setProdDetail(rs.getString("PROD_DETAIL"));
			productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
			productVO.setPrice(rs.getInt("PRICE"));
			productVO.setFileName(rs.getString("IMAGE_FILE"));
			productVO.setRegDate(rs.getDate("REG_DATE"));
		}
		
		con.close();

		return productVO;
	}

	public HashMap<String,Object> getProductList(SearchVO searchVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from PRODUCT ";
		
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0")) {
				sql += " where PROD_NO like '%" + searchVO.getSearchKeyword() +"%'";
			} else if (searchVO.getSearchCondition().equals("1")) {
				sql += "where PROD_NAME like '%" + searchVO.getSearchKeyword() + "%'";
			}
		}
		sql += " order by PROD_NO";

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
				ProductVO productvo = new ProductVO();
				productvo.setProdNo(rs.getInt("PROD_NO"));
				productvo.setProdName(rs.getString("PROD_NAME"));
				productvo.setProdDetail(rs.getString("PROD_DETAIL"));
				productvo.setManuDate(rs.getString("MANUFACTURE_DAY"));
				productvo.setPrice(rs.getInt("PRICE"));
				productvo.setFileName(rs.getString("IMAGE_FILE"));
				productvo.setRegDate(rs.getDate("REG_DATE"));

				list.add(productvo);
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

	public void updateProduct(ProductVO productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		System.out.println("ProductDAO.....Connection");
	
		String sql = "update PRODUCT set PROD_NAME=?,PROD_DETAIL=?,MANUFACTURE_DAY=?,PRICE=?,REG_DATE=? where PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setDate(5, productVO.getRegDate());
		stmt.setInt(6, productVO.getProdNo());
		stmt.executeUpdate();
		System.out.println("ProductDAO......PreparedStatement");
		
		con.close();
	}
}
