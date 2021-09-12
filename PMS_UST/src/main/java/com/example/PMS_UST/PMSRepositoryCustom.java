package com.example.PMS_UST;

import java.util.List;

public interface PMSRepositoryCustom {

	List<PMS> findAllByProductName(String productName);
}
