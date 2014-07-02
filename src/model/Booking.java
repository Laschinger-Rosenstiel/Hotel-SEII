package model;

public interface Booking {
	
	public abstract void book(HotelObject d);
	public abstract void cancel(Booking b, HotelObject d);
	public abstract int getBid();
	public abstract void setBid(int bid);
}
