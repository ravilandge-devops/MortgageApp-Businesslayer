package com.mortgagedemo.app.model;

import java.io.Serializable;

public class Mortgage implements Serializable,Cloneable{

	private static final long serialVersionUID = 1L;
	private String mortgageId;
	private Integer version;
	private String offerID;
	private String productId;
	private String offerDate;
	private String createdDate;
	private Character offerExpired;

	public Mortgage() {
	}

	public Mortgage(String mortgageId, Integer version, String offerID, String productId, String offerDate,
			String createdDate, Character offerExpired) {
		super();
		this.mortgageId = mortgageId;
		this.version = version;
		this.offerID = offerID;
		this.productId = productId;
		this.offerDate = offerDate;
		this.createdDate = createdDate;
		this.offerExpired = offerExpired;
	}
	
	@Override
	public Mortgage clone() throws CloneNotSupportedException {
		return (Mortgage) super.clone();
	}

	public String getMortgageId() {
		return mortgageId;
	}

	public void setMortgageId(String mortgageId) {
		this.mortgageId = mortgageId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getOfferID() {
		return offerID;
	}

	public void setOfferID(String offerID) {
		this.offerID = offerID;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getOfferDate() {
		return offerDate;
	}

	public void setOfferDate(String offerDate) {
		this.offerDate = offerDate;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public Character getOfferExpired() {
		return offerExpired;
	}

	public void setOfferExpired(Character offerExpired) {
		this.offerExpired = offerExpired;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mortgageId == null) ? 0 : mortgageId.hashCode());
		result = prime * result + ((offerDate == null) ? 0 : offerDate.hashCode());
		result = prime * result + ((offerExpired == null) ? 0 : offerExpired.hashCode());
		result = prime * result + ((offerID == null) ? 0 : offerID.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mortgage other = (Mortgage) obj;
		if (mortgageId == null) {
			if (other.mortgageId != null)
				return false;
		} else if (!mortgageId.equals(other.mortgageId))
			return false;
		if (offerDate == null) {
			if (other.offerDate != null)
				return false;
		} else if (!offerDate.equals(other.offerDate))
			return false;
		if (offerExpired == null) {
			if (other.offerExpired != null)
				return false;
		} else if (!offerExpired.equals(other.offerExpired))
			return false;
		if (offerID == null) {
			if (other.offerID != null)
				return false;
		} else if (!offerID.equals(other.offerID))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Mortgage [mortgageId=" + mortgageId + ", version=" + version + ", offerID=" + offerID + ", productId="
				+ productId + ", offerDate=" + offerDate + ", createdDate=" + createdDate + ", offerExpired="
				+ offerExpired + "]";
	}


}
