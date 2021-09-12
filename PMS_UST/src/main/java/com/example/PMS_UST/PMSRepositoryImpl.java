package com.example.PMS_UST;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class PMSRepositoryImpl implements PMSRepositoryCustom{

	@Autowired
	PMSRepository repository;
	
	@Override
	public List<PMS> findAllByProductName(String productName) {
		
		List<PMS> pnames = new ArrayList<PMS>();
		List<PMS> records = repository.findAll();
		
		for(PMS items : records)
			if(items.getProductname().equalsIgnoreCase(productName))
			{
				pnames.add(items);
			}
		return pnames;
	}

}
