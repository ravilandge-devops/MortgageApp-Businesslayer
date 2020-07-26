package com.mortgagedemo.app.model;

import org.junit.jupiter.api.Test;

class MortgageTest {

	@Test
	void test() {
		new Mortgage("123", 1, "123", "12", "01/01/2020", "01/01/2020", 'N');
	}

}
