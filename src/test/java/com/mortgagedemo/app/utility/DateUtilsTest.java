package com.mortgagedemo.app.utility;

import org.junit.jupiter.api.Test;

class DateUtilsTest {

	DateUtils dateUtils = new DateUtils();

	@Test
	void testGetTodaysDate() {
		DateUtils.getTodaysDate();
	}

	@Test
	void testIsOfferDateIsBeforeSixMonths() {
		DateUtils.isOfferDateIsBeforeSixMonths("05/02/2020");
	}

	@Test
	void testConvertDateForComparison() {
		DateUtils.convertDateForComparison("05/02/2020");
	}

}
