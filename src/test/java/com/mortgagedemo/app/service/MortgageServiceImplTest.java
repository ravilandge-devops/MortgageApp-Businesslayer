package com.mortgagedemo.app.service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mortgagedemo.app.model.Mortgage;

@SpringBootTest
class MortgageServiceImplTest {

	@Autowired
	MortgageServiceImpl mortgageServiceImpl;

	@Mock
	MortgageServiceImpl mortgageServiceMockImpl;
	
	public static Mortgage getMortgageObj() {
		Mortgage obj = new Mortgage();
		obj.setOfferID("123");
		obj.setMortgageId("123");
		obj.setProductId("123");
		obj.setVersion(1);
		obj.setCreatedDate("05/02/2020");
		obj.setOfferDate("05/02/2020");
		obj.setOfferExpired('N');;
		return obj;
	}
	
	public static Mortgage mortgage0 = new Mortgage("M1", 1, "OI-1", "B1", "20/05/2020", "20/07/2020", 'N');
	public static Mortgage mortgage1 = new Mortgage("M2", 2, "OI-2", "B1", "20/05/2021", "20/07/2020", 'N');
	public static Mortgage mortgage2 = new Mortgage("M3", 1, "OI-1", "B1", "20/05/2021", "20/07/2020", 'N');
	public static Mortgage mortgage3 = new Mortgage("M4", 3, "OI-3", "B2", "20/05/2014", "20/07/2020", 'Y');
	
	public static Mortgage[] getMortgageArray() {
		Mortgage[] ortgageArray= new Mortgage[4];
		ortgageArray[0]=mortgage0;
		ortgageArray[1]=mortgage1;
		ortgageArray[2]=mortgage2;
		ortgageArray[3]=mortgage3;
		return ortgageArray;
	}

	@Test
	void testCreateMortgageApplication() {
		mortgageServiceImpl.createMortgageApplication(getMortgageObj());
	}

	@Test
	void testCreateMortgageApplicationEmpty() {
		when(mortgageServiceMockImpl.createMortgageApplication(new Mortgage())).thenReturn(null);
	}

	
	 @Test 
	 void testIsAlreadyPresentInMortgageDynamicArray() {
	 mortgageServiceImpl.isAlreadyPresentInMortgageDynamicArray(getMortgageObj(),getMortgageArray());
	 }
	 
	 @Test 
	 void testIsAlreadyPresentInMortgageDynamicArrayEmpty() {
	 when(mortgageServiceMockImpl.isAlreadyPresentInMortgageDynamicArray(new Mortgage(),getMortgageArray())).thenReturn(false); 
	 }
	 

	@Test
	void testSortByOfferDate() throws CloneNotSupportedException {
		when(mortgageServiceMockImpl.sortByOfferDate(getMortgageArray())).thenReturn(new Mortgage[4]);
	}

	@Test
	void testsortByCreatedDate() throws CloneNotSupportedException {
		when(mortgageServiceMockImpl.sortByCreatedDate(getMortgageArray())).thenReturn(new Mortgage[4]);
	}
	
	@Test
	void testautomaticallyUpdateExpireFlag() throws CloneNotSupportedException {
		mortgageServiceImpl.automaticallyUpdateExpireFlag();
	}

}

