package com.example.PMS_UST;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@SpringBootTest
class PmsUstApplicationTests {
	
	AtomicInteger counter = new AtomicInteger();
	
	@Autowired
	 PMS_Controller con;
	 
	 @MockBean
	 PMSAddService PMService;
	 
	 
	 @MockBean
	 PMSRepository repository;

	@Test
	void contextLoads() {
	}

	
	@Test
	public void addProductWithMockito()
	{
		when(PMService.checkProductAlreadyAdded(PMSDataPayload().getProductname()+counter.getAndIncrement())).thenReturn(true);
		//when(PMService.checkProductAlreadyAdded(PMSDataPayload().getProductname()+counter.getAndIncrement())).thenReturn(false);
		  
		  ResponseEntity response=con.addPizza(PMSDataPayload());
		  System.out.println(response.getStatusCode());
		  assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		  
		  AddResponse ad=(AddResponse) response.getBody();
		  //assertEquals("Success : Product is Added", ad.getMsg());
		  assertEquals("FAILED : Product Already Exists", ad.getMsg());
		  
		  
		 } 
		 
		 
		 public PMS PMSDataPayload()
		 {
		  PMS pms=new PMS();
		  pms.setProductname("VegParadise");
		  pms.setPrice("650");
		  return pms;
		 }
		 
}
