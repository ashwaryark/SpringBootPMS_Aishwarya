package com.example.PMS_UST;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class PMSAddService {
	
	@Autowired
	PMSRepository repository;
	
	public boolean checkProductAlreadyAdded(String id)
	{
		Optional<PMS> pms = repository.findById(id);
		if(pms.isPresent())
		{
			return true;
		}
		else
		{	
		return false;
		}
		
	}

}
