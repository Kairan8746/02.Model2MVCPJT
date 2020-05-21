package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

/*
 *	FileName :  ProductServiceTest.java
 * �� JUnit4 (Test Framework) �� Spring Framework ���� Test( Unit Test)
 * �� Spring �� JUnit 4�� ���� ���� Ŭ������ ���� ������ ��� ���� �׽�Ʈ �ڵ带 �ۼ� �� �� �ִ�.
 * �� @RunWith : Meta-data �� ���� wiring(����,DI) �� ��ü ����ü ����
 * �� @ContextConfiguration : Meta-data location ����
 * �� @Test : �׽�Ʈ ���� �ҽ� ����
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class ProductServiceTest {

	// ==>@RunWith,@ContextConfiguration �̿� Wiring, Test �� instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	// @Test
	public void testAddProduct() throws Exception {

		Product product = new Product();
		product.setProdNo(Integer.parseInt("1001"));
		product.setProdName("������");
		product.setProdDetail("111112222222");
		product.setManuDate("20180205");
		product.setPrice(Integer.parseInt("11111"));
		product.setFileName("testProduct");

		productService.addProduct(product);

		product = productService.getProduct(1001);

		// ==> console Ȯ��
		System.out.println(product);

		// ==> API Ȯ��
		Assert.assertEquals(1001, product.getProdNo());
		Assert.assertEquals("������", product.getProdName());
		Assert.assertEquals("111112222222", product.getProdDetail());
		Assert.assertEquals("20180205", product.getManuDate());
		Assert.assertEquals(11111, product.getPrice());
		Assert.assertEquals("testProduct", product.getFileName());

	}
	@Test
		public void testGetProduct() throws Exception {
			
			Product product = new Product();
			//==> �ʿ��ϴٸ�...
//			product.setProductId("testProductId");
//			product.setProdName("testProdName");
//			product.setPassword("testPasswd");
//			product.setSsn("1111112222222");
//			product.setPhone("111-2222-3333");
//			product.setAddr("��⵵");
//			product.setEmail("test@test.com");
			
			product = productService.getProduct(1001);

			//==> console Ȯ��
			//System.out.println(product);
			
			//==> API Ȯ��
			Assert.assertEquals(1001, product.getProdNo());
			Assert.assertEquals("������", product.getProdName());
			Assert.assertEquals("111112222222", product.getProdDetail());
			Assert.assertEquals("20180205", product.getManuDate());
			Assert.assertEquals(11111, product.getPrice());
			Assert.assertEquals("testProduct", product.getFileName());

		
		}
	/*	
		//@Test
		 public void testUpdateProduct() throws Exception{
			 
			Product product = productService.getProduct("testProductId");
			Assert.assertNotNull(product);
			
			Assert.assertEquals("testProdName", product.getProdName());
			Assert.assertEquals("111-2222-3333", product.getPhone());
			Assert.assertEquals("��⵵", product.getAddr());
			Assert.assertEquals("test@test.com", product.getEmail());

			product.setProdName("change");
			product.setPhone("777-7777-7777");
			product.setAddr("change");
			product.setEmail("change@change.com");
			
			productService.updateProduct(product);
			
			product = productService.getProduct("testProductId");
			Assert.assertNotNull(product);
			
			//==> console Ȯ��
			//System.out.println(product);
				
			//==> API Ȯ��
			Assert.assertEquals("change", product.getProdName());
			Assert.assertEquals("777-7777-7777", product.getPhone());
			Assert.assertEquals("change", product.getAddr());
			Assert.assertEquals("change@change.com", product.getEmail());
		 }
		 
		//@Test
		public void testCheckDuplication() throws Exception{

			//==> �ʿ��ϴٸ�...
//			Product product = new Product();
//			product.setProductId("testProductId");
//			product.setProdName("testProdName");
//			product.setPassword("testPasswd");
//			product.setSsn("1111112222222");
//			product.setPhone("111-2222-3333");
//			product.setAddr("��⵵");
//			product.setEmail("test@test.com");
//			
//			productService.addProduct(product);
			
			//==> console Ȯ��
			//System.out.println(productService.checkDuplication("testProductId"));
			//System.out.println(productService.checkDuplication("testProductId"+System.currentTimeMillis()) );
		 	
			//==> API Ȯ��
			Assert.assertFalse( productService.checkDuplication("testProductId") );
		 	Assert.assertTrue( productService.checkDuplication("testProductId"+System.currentTimeMillis()) );
			 	
		}
		*/
		 //==>  �ּ��� Ǯ�� �����ϸ�....
		 //@Test
		 public void testGetProductListAll() throws Exception{
			 
		 	Search search = new Search();
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	Map<String,Object> map = productService.getProductList(search);
		 	
		 	List<Object> list = (List<Object>)map.get("list");
		 	Assert.assertEquals(3, list.size());
		 	
			//==> console Ȯ��
		 	//System.out.println(list);
		 	
		 	Integer totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 	
		 	System.out.println("=======================================");
		 	
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	search.setSearchCondition("0");
		 	search.setSearchKeyword("");
		 	map = productService.getProductList(search);
		 	
		 	list = (List<Object>)map.get("list");
		 	Assert.assertEquals(3, list.size());
		 	
		 	//==> console Ȯ��
		 	//System.out.println(list);
		 	
		 	totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 }
		 
		 //@Test
		 public void testGetProductListByProductId() throws Exception{
			 
		 	Search search = new Search();
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	search.setSearchCondition("0");
		 	search.setSearchKeyword("admin");
		 	Map<String,Object> map = productService.getProductList(search);
		 	
		 	List<Object> list = (List<Object>)map.get("list");
		 	Assert.assertEquals(1, list.size());
		 	
			//==> console Ȯ��
		 	//System.out.println(list);
		 	
		 	Integer totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 	
		 	System.out.println("=======================================");
		 	
		 	search.setSearchCondition("0");
		 	search.setSearchKeyword(""+System.currentTimeMillis());
		 	map = productService.getProductList(search);
		 	
		 	list = (List<Object>)map.get("list");
		 	Assert.assertEquals(0, list.size());
		 	
			//==> console Ȯ��
		 	//System.out.println(list);
		 	
		 	totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 }
		 
		//@Test
		 public void testGetProductListByProdName() throws Exception{
			 
		 	Search search = new Search();
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	search.setSearchCondition("1");
		 	search.setSearchKeyword("SCOTT");
		 	Map<String,Object> map = productService.getProductList(search);
		 	
		 	@SuppressWarnings("unchecked")
			List<Object> list = (List<Object>)map.get("list");
		 	Assert.assertEquals(3, list.size());
		 	
			//==> console Ȯ��
		 	System.out.println(list);
		 	
		 	Integer totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 	
		 	System.out.println("=======================================");
		 	
		 	search.setSearchCondition("1");
		 	search.setSearchKeyword(""+System.currentTimeMillis());
		 	map = productService.getProductList(search);
		 	
		 	list = (List<Object>)map.get("list");
		 	Assert.assertEquals(0, list.size());
		 	
			//==> console Ȯ��
		 	System.out.println(list);
		 	
		 	totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 }	 
	}