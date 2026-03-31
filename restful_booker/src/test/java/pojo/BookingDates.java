package pojo;

public class BookingDates {
    private String checkin;
    private String checkout;

    public BookingDates() {}

    public BookingDates(String checkin, String checkout) {
        this.checkin = checkin;
        this.checkout = checkout;
    }

	public String getCheckin() {
		return checkin;
	}

	public String getCheckout() {
		return checkout;
	}
}