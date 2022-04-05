package vo;

public class StaffListView {
	private int StaffId; // staff_id AS ID
	private String name; // CONCAT(s.first_name, _utf8mb4' ', s.last_name)
	private String address; 
	private String postalCode; // postal_code AS `zip code`
	private String phone; // 
	private String city;
	private String country; 
	private int storeId; // store_id AS SID
	
	@Override
	public String toString() {
		return "StaffListView [StaffId=" + StaffId + ", name=" + name + ", address=" + address + ", postalCode="
				+ postalCode + ", phone=" + phone + ", city=" + city + ", country=" + country + ", storeId=" + storeId
				+ "]";
	}

	// getter + setter
	public int getStaffId() {
		return StaffId;
	}

	public void setStaffId(int staffId) {
		StaffId = staffId;
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

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	
	
}
