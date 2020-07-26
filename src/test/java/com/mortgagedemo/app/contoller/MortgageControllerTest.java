package com.mortgagedemo.app.contoller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;

import com.mortgagedemo.app.model.Mortgage;

@SpringBootTest
class MortgageControllerTest {

	private static final ResponseEntity<Mortgage> IOException = null;

	private static final ResponseEntity<Mortgage> ConnectException = null;

	private static final ResponseEntity<Mortgage> GlobleAPIException = null;

	@Autowired
	private MortgageController mortgageController;

	@Mock
	private MortgageController mortgageControllerMock;

	public static Mortgage getMortgageObj() {
		Mortgage obj = new Mortgage();
		obj.setOfferID("123");
		obj.setMortgageId("123");
		obj.setProductId("123");
		obj.setVersion(1);
		obj.setCreatedDate("05/02/2020");
		obj.setOfferDate("05/02/2020");
		obj.setOfferExpired('N');
		return obj;
	}

	@Test()
	void testInsertMortgageApplication() {
		mortgageController.insertMortgageApplication(getMortgageObj());
		//when(mortgageControllerMock.insertMortgageApplication(getMortgageObj())).thenReturn(GlobleAPIException);
		//assertThrows(ResourceAccessException.class, mortgageController.insertMortgageApplication(getMortgageObj()));
	}

	@Test
	void testInsertMortgageApplicationEmptyObj() {
		mortgageController.insertMortgageApplication(new Mortgage());
	}

	@Test
	void testInsertMortgageApplicationNullObj() {
		when(mortgageControllerMock.insertMortgageApplication(getMortgageObj())).thenReturn(null);
	}

	@Test
	void testGetAllMortgageApplicationEmpty() {
		when(mortgageControllerMock.getAllMortgageApplication("")).thenReturn(null);
	}

	@Test
	void testGetAllMortgageApplicationNull() {
		when(mortgageControllerMock.getAllMortgageApplication(null)).thenReturn(null);
	}

}
