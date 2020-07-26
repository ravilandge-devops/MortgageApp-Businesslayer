package com.mortgagedemo.app.service;

import com.mortgagedemo.app.model.Mortgage;

public interface MortgageService {

	Mortgage createMortgageApplication(Mortgage mortgage);

	Mortgage[] getAllMortgageApplication(String sortBy);

}
