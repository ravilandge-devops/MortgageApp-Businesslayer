package com.mortgagedemo.app.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import com.mortgagedemo.app.exceptionhandler.GlobleAPIException;

public class DateUtils {

	@Value("${dateFormate}")
	private static String dateFormate;

	private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	public static String getTodaysDate() {
		Date todaysDate = new Date();
		return formatter.format(todaysDate);
	}

	public static boolean isOfferDateIsBeforeSixMonths(String strDate) {
		boolean isOfferDateIsBeforeSixMonths = false;

		System.out.println("today : " + getTodaysDate());

		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, 6);

		Date dateAfterSixMonths = now.getTime();
		System.out.println("dateAfterSixMonths : " + formatter.format(dateAfterSixMonths));

		Date offerDate = convertDateForComparison(strDate);

		if (offerDate.before(dateAfterSixMonths)) {
			System.out.println(formatter.format(offerDate) + "isOfferDateIsBeforeSixMonths");
			isOfferDateIsBeforeSixMonths = true;
		}
		return isOfferDateIsBeforeSixMonths;
	}

	public static Date convertDateForComparison(String strDate) {
		Date offerDate = null;
		try {
			offerDate = formatter.parse(strDate);
		} catch (ParseException e) {
			throw new GlobleAPIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return offerDate;
	}

}
