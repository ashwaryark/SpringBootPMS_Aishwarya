package com.example.PMS_UST;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.hibernate.query.QueryParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;



@RestController
public class PMS_Controller {
	
	AtomicInteger counter = new AtomicInteger();
	
	@Autowired
	PMSRepository repository;
	
	@Autowired
	PMSAddService service;
	
	@GetMapping("/getPizza/{id}")
	 public PMS getEmployeeById(@PathVariable(value="id")String id)
	 {
		 HttpHeaders headers = new HttpHeaders();
		try {
			PMS pms = repository.findById(id).get();
			return pms;
			
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} 
		
	 }
	
	@GetMapping("/getPizza")
	 public List<PMS> getProductByName(@RequestParam(value="ProductName")String productname)
	 {
		 return repository.findAllByProductName(productname);
		
	 }
	
	@PostMapping("/addPizza")
	public ResponseEntity<AddResponse> addPizza(@RequestBody PMS pms)
	{
		String id = pms.getProductname() + counter.incrementAndGet();
		HttpHeaders headers = new HttpHeaders();
		AddResponse add = new AddResponse();
		
		if(!service.checkProductAlreadyAdded(id))
		{
			pms.setPid(id);
			repository.save(pms);
			add.setId(id);
			add.setMsg("Success : Product is Added");
			headers.add("Unique", id);	
			return new ResponseEntity<AddResponse>(add,headers,HttpStatus.CREATED);
			}
			else
			{
				add.setId(id);
				add.setMsg("FAILED : Product Already Exists");
				headers.add("Unique", id);	
				return new ResponseEntity<AddResponse>(add,headers,HttpStatus.ACCEPTED);
			}
	}
	
	@PutMapping("/updatePizza/{id}")
	public ResponseEntity<PMS> updatePizza(@PathVariable(value="id")String id,@RequestBody PMS pms)
	{
		PMS pmsRecord = repository.findById(id).get();
		pmsRecord.setProductname(pms.getProductname());
		pmsRecord.setPrice(pms.getPrice());
		repository.save(pmsRecord);
		
		return new ResponseEntity<PMS>(pmsRecord,HttpStatus.OK);
	}
	
	@DeleteMapping("/deletePizza")
	public ResponseEntity<String> deleteById(@RequestBody PMS pms)
	{
		PMS deleteRecord = repository.findById(pms.getPid()).get();
		repository.delete(deleteRecord);
		
		return new ResponseEntity<>("Pizza Record Deleted", HttpStatus.CREATED);
	}
	
	@DeleteMapping("/deleteAll")
	public ResponseEntity<String> deleteAll()
	{
		repository.deleteAll();
		
		return new ResponseEntity<>("All Product Record Deleted", HttpStatus.CREATED);
	}
	
}
