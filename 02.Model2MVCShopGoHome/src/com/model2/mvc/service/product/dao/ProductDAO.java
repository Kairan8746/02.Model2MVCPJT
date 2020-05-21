package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;


public class ProductDAO {

	public Product findProduct (int prodNo) throws Exception {
		
		Connection con = DBUtil.getConnection();
System.out.println("����� findProductDAO");
String sql = 	"SELECT "+
		"prod_no ,  prod_name ,  prod_detail , manufacture_day , price , image_file , reg_date  " + 
		"FROM product WHERE prod_no = ?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, prodNo);
		
		ResultSet rs = pStmt.executeQuery();
		
		Product product = null;
		while (rs.next()) {
			product = new Product();
			
			product.setProdNo(rs.getInt("PROD_NO"));
			product.setProdName(rs.getString("PROD_NAME"));
			product.setProdDetail(rs.getString("PROD_DETAIL"));
			product.setManuDate(rs.getString("MANUFACTURE_DAY"));
			product.setPrice(rs.getInt("PRICE"));
			product.setFileName(rs.getString("IMAGE_FILE"));
			product.setRegDate(rs.getDate("REG_DATE"));
	
		}
		
		con.close();
		pStmt.close();
		return product;
	}
	public Map<String,Object> getProductList(Search search) throws Exception {
		
		Map<String,Object>  map = new HashMap<String, Object>();
	
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM product ";
		
		if (search.getSearchCondition() != null) {
		if ( search.getSearchCondition().equals("0") &&  !search.getSearchKeyword().equals("") ) {
			sql += " WHERE prod_no LIKE '%" + search.getSearchKeyword()+"%'";
		} else if ( search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("")) {
			sql += " WHERE prod_name LIKE '%" + search.getSearchKeyword()+"%'";
		}
		}
		sql += " ORDER BY prod_no";
		System.out.println("ProductDAO::Original SQL :: " + sql);

		int totalCount = this.getTotalCount(sql);
		
		System.out.println("ProductDAO :: totalCount  :: " + totalCount);
		
		//==> CurrentPage �Խù��� �޵��� Query �ٽñ���
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
	
		System.out.println(search);
		
		List<Product> list = new ArrayList<Product>();
		
		while(rs.next()){
			Product product = new Product();
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setPrice(rs.getInt("price"));
			product.setManuDate(rs.getString("MANUFACTURE_DAY"));
			list.add(product);
		}
				//==> totalCount ���� ����
				map.put("totalCount", new Integer(totalCount));
				//==> currentPage �� �Խù� ���� ���� List ����
				map.put("list", list);

				rs.close();
				pStmt.close();
				con.close();

				return map;
			}
	
public void insertProduct(Product product) throws Exception {
	
	Connection con = DBUtil.getConnection();

	String sql = "INSERT INTO product VALUES (SEQ_PRODUCT_PROD_NO.NEXTVAL,?,?,?,?,?,sysdate)";
	
	PreparedStatement stmt = con.prepareStatement(sql);
	//stmt.setInt(1, product.getProdNo());
	stmt.setString(1, product.getProdName());
	stmt.setString(2, product.getProdDetail());
	stmt.setString(3, product.getManuDate());
	stmt.setInt(4, product.getPrice());
	stmt.setString(5, product.getFileName());

	stmt.executeUpdate();
	
	con.close();
}
public void updateProduct(Product product) throws Exception {
	
	Connection con = DBUtil.getConnection();
	System.out.println( product+"ProductDAO");
	String sql = "UPDATE product SET prod_name = ?,PROD_DETAIL=?,MANUFACTURE_DAY=?,PRICE=?,IMAGE_FILE=? where PROD_NO=?";
	
	PreparedStatement stmt = con.prepareStatement(sql);
	stmt.setString(1, product.getProdName());
	stmt.setString(2, product.getProdDetail());
	stmt.setString(3, product.getManuDate().replace("-",""));
	stmt.setInt(4, product.getPrice());
	stmt.setString(5, product.getFileName());
	stmt.setInt(6, product.getProdNo());
	System.out.println(product+" DAO SQL");
	stmt.executeUpdate();
	
	con.close();
}
	
//�Խ��� Page ó���� ���� ��ü Row(totalCount)  return
	private int getTotalCount(String sql) throws Exception {
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	
	// �Խ��� currentPage Row ��  return 
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("ProductDAO :: make SQL :: "+ sql);	
		
		return sql;
	}
}