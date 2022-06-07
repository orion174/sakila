package vo;

// Film List View 테이블이랑 actors 칼럼 데이터값의 영대소문자 구분만 다른 View 테이블
public class NBSF {
	private int filmId; // film_id AS FID
	private String title;
	private String description;
	private String name; // name AS category
	private int rentalRate; // rental_rate AS price
	private int length; //
	private String rating;
	private String actor;
	/*
	 * GROUP_CONCAT(CONCAT(CONCAT(UCASE(SUBSTR(actor.first_name,1,1)),
	 * LCASE(SUBSTR(actor.first_name,2,LENGTH(actor.first_name))), _utf8mb4'
	 * ',CONCAT(UCASE(SUBSTR(actor.last_name,1,1)),
	 * LCASE(SUBSTR(actor.last_name,2,LENGTH(actor.last_name)))))) SEPARATOR ', ')
	 * AS actors
	 */

	@Override
	public String toString() {
		return "Film [filmId=" + filmId + ", title=" + title + ", description=" + description + ", name=" + name
				+ ", rentalRate=" + rentalRate + ", length=" + length + ", rating=" + rating + ", actor=" + actor + "]";
	}

	public int getFilmId() {
		return filmId;
	}

	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRentalRate() {
		return rentalRate;
	}

	public void setRentalRate(int rentalRate) {
		this.rentalRate = rentalRate;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}
}
