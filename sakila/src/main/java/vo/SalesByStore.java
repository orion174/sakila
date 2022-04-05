package vo;

public class SalesByStore {
	private String store; // CONCAT(c.city, _utf8mb4',', cy.country) AS store
	private String manager; // CONCAT(m.first_name, _utf8mb4' ', m.last_name) AS manager
	private long totalSales; // SUM(p.amount) AS total_sales 
	
	@Override
	public String toString() {
		return "SalesByStore [store=" + store + ", manager=" + manager + ", totalSales=" + totalSales + "]";
	}
	
	// getter + setter
	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public long getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(long totalSales) {
		this.totalSales = totalSales;
	}
}
