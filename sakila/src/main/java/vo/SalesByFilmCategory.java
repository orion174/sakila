package vo;

public class SalesByFilmCategory {
	private String name; // name AS category
	private long totalSales; // SUM(p.amount) AS total_sales -> 값이 큼 : long

	@Override
	public String toString() {
		return "SalesByFilmCategory [name=" + name + ", totalSales=" + totalSales + "]";
	}

	// getter + setter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(long totalSales) {
		this.totalSales = totalSales;
	}
}