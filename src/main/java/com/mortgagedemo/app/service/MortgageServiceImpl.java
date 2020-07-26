package com.mortgagedemo.app.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.mortgagedemo.app.exceptionhandler.GlobleAPIException;
import com.mortgagedemo.app.model.Mortgage;
import com.mortgagedemo.app.utility.DateUtils;

@Service
public class MortgageServiceImpl implements MortgageService {
	
	@Autowired
	@Lazy
	private RestTemplate restTemplate;
	
	@Value("${addMortgageUrl}")
	private String addMortgageUrl;
	
	@Value("${setMortgageUrl}")
	private String setMortgageUrl;
	
	@Value("${getAllMortgageUrl}")
	private String getAllMortgageUrl;
	
	@Value("${sortByOfferDate}")
	private String sortByOfferDate;

	@Override
	public Mortgage createMortgageApplication(Mortgage mortgage) {
		 mortgage.setCreatedDate(DateUtils.getTodaysDate());
		 if(!validateObject(mortgage))
		 return mortgage;
		 ResponseEntity<Mortgage[]> response = null;
		//ResponseEntity<Mortgage[]> response = restTemplate.getForEntity(getAllMortgageUrl, Mortgage[].class);
		  try {
			  response = restTemplate.getForEntity(getAllMortgageUrl, Mortgage[].class);
		    } catch (ResourceAccessException e) {
		      System.out.println(e.getMessage());
		      throw new GlobleAPIException(e.getMessage(),HttpStatus.SERVICE_UNAVAILABLE);
		    }
		if(response!=null && response.getStatusCode().equals(HttpStatus.OK)) {
			Mortgage[] mortgagesResponse = response.getBody();
				if(mortgagesResponse!=null && mortgagesResponse.length>0) {
			for (Mortgage mortgageObj : mortgagesResponse) {
				System.out.println("getAll" +mortgageObj);
			}
		if (!isAlreadyPresentInMortgageDynamicArray(mortgage,mortgagesResponse)) {
			if (mortgage.getOfferDate() != null && !DateUtils.isOfferDateIsBeforeSixMonths(mortgage.getOfferDate())) {
				int key = checkMortgageApplicationsVirsion(mortgage,mortgagesResponse);

				switch (key) {
				case -2:
					throw new GlobleAPIException(
							"Mortgage Applications received by the bank is rejected because of lower version",
							HttpStatus.CONFLICT);
				case -1:
					return restTemplate.postForObject(addMortgageUrl, mortgage, Mortgage.class);
				default:
					return restTemplate.postForObject(setMortgageUrl + key, mortgage, Mortgage.class);
				}
			} else {
				return null;
			}
		} else {
			return mortgage;
		}
				}else {
					return restTemplate.postForObject(addMortgageUrl, mortgage, Mortgage.class);
				}
		}else {
			//return restTemplate.postForObject(addMortgageUrl, mortgage, Mortgage.class);
			try {
				return restTemplate.postForObject(addMortgageUrl, mortgage, Mortgage.class);
			    } catch (ResourceAccessException e) {
			      System.out.println(e.getMessage());
			      throw new GlobleAPIException(e.getMessage(),HttpStatus.SERVICE_UNAVAILABLE);
			      //return null;
			    }
		}
	}
	
	public boolean isAlreadyPresentInMortgageDynamicArray(Mortgage mortgage, Mortgage[] mortgagesResponse) {
		boolean flag = false;
			 if (mortgagesResponse==null || mortgagesResponse.length<1) {
				 System.out.println("List is empty");
				 return flag; 
			 }else {
				 for (Mortgage mortgageObj : mortgagesResponse) {
				System.out.println(mortgageObj + " ");
				if (mortgageObj != null && mortgageObj.equals(mortgage)) {
					flag = true;
					break;
				}
			}
		}
			if (flag)
				System.out.println("Entity Already Exists");
			else
				System.out.println("Entity Not Exists");
			return flag;
	}
	
	@Override
	public Mortgage[] getAllMortgageApplication(String sortBy) {
		Mortgage[] mortgagesResponse =null;
		ResponseEntity<Mortgage[]> response = null;
		try {
			  response = restTemplate.getForEntity(getAllMortgageUrl, Mortgage[].class);
		    } catch (ResourceAccessException e) {
		      System.out.println(e.getMessage());
		      throw new GlobleAPIException(e.getMessage(),HttpStatus.SERVICE_UNAVAILABLE);
		    }
		if(response!=null && response.getStatusCode().equals(HttpStatus.OK)) {
			mortgagesResponse = response.getBody();
		if(mortgagesResponse!=null && mortgagesResponse.length>0) {
			for (Mortgage mortgageObj : mortgagesResponse) {
				System.out.println("getAll" +mortgageObj);
			}
		try {
			mortgagesResponse = (sortBy != null && sortBy.equalsIgnoreCase(sortByOfferDate)) ? sortByOfferDate(mortgagesResponse)
					: sortByCreatedDate(mortgagesResponse);
		} catch (CloneNotSupportedException e) {
			throw new GlobleAPIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		}
		}
		return mortgagesResponse;
	}

	public boolean validateObject(Mortgage mortgage) {
		boolean isValid = true;
		if (mortgage == null) {
			isValid = false;
		} else if (mortgage.getMortgageId() == null || mortgage.getMortgageId().equals("")) {
			isValid = false;
		} else if (mortgage.getVersion() < 1) {
			isValid = false;
		} else if (mortgage.getOfferID() == null || mortgage.getOfferID().equals("")) {
			isValid = false;
		} else if (mortgage.getProductId() == null || mortgage.getProductId().equals("")) {
			isValid = false;
		} else if (mortgage.getOfferDate() == null || mortgage.getOfferDate().equals("")) {
			isValid = false;
		} else if (mortgage.getOfferExpired() == null || mortgage.getOfferExpired().equals(' ')) {
			isValid = false;
		}
		return isValid;
	}

	public int checkMortgageApplicationsVirsion(Mortgage mortgage,	Mortgage[] mortgagesResponse) {
		int key = -1;
		if (mortgage.getMortgageId() != null && mortgage.getOfferID() != null) {
			if(mortgagesResponse!=null && mortgagesResponse.length>0) {
				for (Mortgage mortgageObj : mortgagesResponse) {
					System.out.println("checkMortgageApplicationsVirsion" +mortgageObj);
				}
			for (int i = mortgagesResponse.length - 1; i >= 0; i--) {
				if (mortgagesResponse[i] != null && mortgagesResponse[i].getProductId() != null
						&& mortgagesResponse[i].getOfferID() != null
						&& mortgagesResponse[i].getProductId().equals(mortgage.getProductId())
						&& mortgagesResponse[i].getOfferID().equals(mortgage.getOfferID())) {
					if (mortgage.getVersion() < mortgagesResponse[i].getVersion())
						return -2;
					else if (mortgage.getVersion() == mortgagesResponse[i].getVersion())
						return i;
					else
						return -1;
				}
			}
			}
		}
		return key;
	}

	public Mortgage[] sortByOfferDate(Mortgage[] mortgagesResponse) throws CloneNotSupportedException {
		Mortgage tempMortgage;
		int sortArrayLength = 0;
		if(mortgagesResponse!=null && mortgagesResponse.length>0) {
			for (Mortgage mortgageObj : mortgagesResponse) {
				System.out.println("getAll" +mortgageObj);
			}
		for (int i = 0; i < mortgagesResponse.length; i++) {
			for (int j = i + 1; j < mortgagesResponse.length; j++) {
				if (mortgagesResponse[i] != null && mortgagesResponse[j] != null
						&& (DateUtils.convertDateForComparison(mortgagesResponse[i].getOfferDate())
								.before(DateUtils
										.convertDateForComparison(mortgagesResponse[j].getOfferDate())))) {
					tempMortgage = mortgagesResponse[i];
					mortgagesResponse[i] = mortgagesResponse[j];
					mortgagesResponse[j] = tempMortgage;
				}
			}
		}
		}
		for (int i = 0; i < mortgagesResponse.length; i++) {
			if(mortgagesResponse[i]!=null) {
				sortArrayLength++;
			}
			}
		Mortgage[] mortgageArray = new Mortgage[sortArrayLength];
		int j = 0;
		System.out.println("Array elements in descending order by Offer Date:");
		for (int i = 0; i < mortgagesResponse.length; i++) {
			if(mortgagesResponse[i]!=null) {
			mortgageArray[j]=mortgagesResponse[i];
			j++;
			}
		}
		return mortgageArray;
	}

	public Mortgage[] sortByCreatedDate(Mortgage[] mortgagesResponse) throws CloneNotSupportedException {
		Mortgage tempMortgage;
		int sortArrayLength = 0;
		if(mortgagesResponse!=null && mortgagesResponse.length>0) {
			for (Mortgage mortgageObj : mortgagesResponse) {
				System.out.println("getAll" +mortgageObj);
			}
		for (int i = 0; i < mortgagesResponse.length; i++) {
			for (int j = i + 1; j < mortgagesResponse.length; j++) {
				if (mortgagesResponse[i] != null && mortgagesResponse[j] != null
						&& (DateUtils.convertDateForComparison(mortgagesResponse[i].getCreatedDate())
								.before(DateUtils
										.convertDateForComparison(mortgagesResponse[j].getCreatedDate())))) {
					tempMortgage = mortgagesResponse[i];
					mortgagesResponse[i] = mortgagesResponse[j];
					mortgagesResponse[j] = tempMortgage;
				}
			}
		}
		}
		for (int i = 0; i < mortgagesResponse.length; i++) {
			if(mortgagesResponse[i]!=null) {
				sortArrayLength++;
			}
			}
		Mortgage[] mortgageArray = new Mortgage[sortArrayLength];
		int j = 0;
		System.out.println("Array elements in descending order by Created Date:");
		for (int i = 0; i < mortgagesResponse.length; i++) {
			if(mortgagesResponse[i]!=null) {
			mortgageArray[j]=mortgagesResponse[i];
			j++;
			}
		}
		return mortgageArray;
	}

	@Scheduled(cron = "0 0 * * * *")
	//@Scheduled(cron = "0/15 * * * * *")
	public void automaticallyUpdateExpireFlag() throws CloneNotSupportedException {
		ResponseEntity<Mortgage[]> response = restTemplate.getForEntity(getAllMortgageUrl, Mortgage[].class);
		if(response.getStatusCode().equals(HttpStatus.OK)) {
			Mortgage[] mortgagesResponse = response.getBody();
		if(mortgagesResponse!=null && mortgagesResponse.length>0) 
		for (int i = 0; i < mortgagesResponse.length; i++) {
			if (mortgagesResponse[i] != null) {
				Date todysDate = DateUtils.convertDateForComparison(DateUtils.getTodaysDate());
				Date offerDate = DateUtils.convertDateForComparison(mortgagesResponse[i].getOfferDate());
				if (todysDate.after(offerDate)) {
					Mortgage mortgage = mortgagesResponse[i].clone();
					mortgage.setOfferExpired('Y');
					Mortgage mortgageResponse = createMortgageApplication(mortgage);
					System.out.println("mortgagesResponse : " + mortgageResponse);
				}
			}
		}
		}
	}

}
