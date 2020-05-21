package com.model2.mvc.service.product;


import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;


public interface ProductDao {

	public void addProduct (Product product) throws Exception;

	public List<Product> getProductList(Search search) throws Exception ;
	
	public void insertProduct(Product product) throws Exception;

	public Product getProduct(int prodNo) throws Exception;
	
	public void updateProduct(Product product) throws Exception;

	//�Խ��� Page ó���� ���� ��ü Row(totalCount)  return
	public int getTotalCount(Search search) throws Exception;

	// �Խ��� currentPage Row �� return
	public String makeCurrentPageSql(String sql , Search search);
		
}