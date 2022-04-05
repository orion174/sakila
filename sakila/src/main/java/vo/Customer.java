package vo;

public class Customer {
	private int customerId; // customer_id AS ID
	private String name; // first_name + last_name AS name
	private String address; 
	private String postalCode; // postal_code AS `zip code`
	private String phone; // 
	private String city; 
	private String country; 
	private String active; // cu.active, _utf8mb4'active',_utf8mb4'' AS notes
	private String storeId; // store_id AS SID
	
	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", name=" + name + ", address=" + address + ", postalCode="
				+ postalCode + ", phone=" + phone + ", city=" + city + ", country=" + country + ", active=" + active
				+ ", storeId=" + storeId + "]";
	}
	// getter + setter
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	
}
