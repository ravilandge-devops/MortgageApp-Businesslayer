package com.mortgagedemo.app.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mortgagedemo.app.model.Mortgage;
import com.mortgagedemo.app.service.MortgageService;

@RestController
@RequestMapping("/mortgage")
public class MortgageController {

	@Autowired
	private MortgageService mortgageService;

	@PostMapping("/mortgageApplication")
	public ResponseEntity<Mortgage> insertMortgageApplication(@RequestBody Mortgage mortgage) {
		
		mortgage = mortgageService.createMortgageApplication(mortgage);
		if (mortgage != null)
			return new ResponseEntity<Mortgage>(mortgage, HttpStatus.CREATED);
		else
			return new ResponseEntity<Mortgage>(mortgage, HttpStatus.PARTIAL_CONTENT);
	}

	@GetMapping("/getAllMortgageApplication/{sortBy}")
	public ResponseEntity<Mortgage[]> getAllMortgageApplication(@PathVariable String sortBy) {
		Mortgage[] mortgages = mortgageService.getAllMortgageApplication(sortBy);
		if (mortgages != null && mortgages[0] != null)
			return new ResponseEntity<Mortgage[]>(mortgages, HttpStatus.OK);
		else
			return new ResponseEntity<Mortgage[]>(new Mortgage[0], HttpStatus.NO_CONTENT);
	}

}
