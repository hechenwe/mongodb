package sooncode.mongodb.test.model;

public class Address {
	private Integer addressId;

	private String addressName;

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	@Override
	public String toString() {
		return "Address [addressId=" + addressId + ", addressName=" + addressName + "]";
	}

}
