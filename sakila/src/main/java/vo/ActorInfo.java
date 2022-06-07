package vo;

public class ActorInfo {

	private int actorId;
	private String firstName;
	private String lastName;
	private String filmInfo;

	/*
	 * @Override public String toString() { // Source로 자동생성가능 // vo 확인하기 위한 코드 // 이
	 * 필드의 객체값을 출력해줌 <- 약속? return this.actorId + this.firstName + this.lastName +
	 * this.filmInfo; }
	 */

	@Override
	public String toString() {
		return "Actorinfo [actorId=" + actorId + ", firstName=" + firstName + ", lastName=" + lastName + ", filmInfo="
				+ filmInfo + "]";
	}

	public int getActorId() {
		return actorId;
	}

	public void setActorId(int actorId) {
		this.actorId = actorId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFilmInfo() {
		return filmInfo;
	}

	public void setFilmInfo(String filmInfo) {
		this.filmInfo = filmInfo;
	}

}