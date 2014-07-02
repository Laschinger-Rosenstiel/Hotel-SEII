package model;

public interface HotelObject {

	public final static String ACTION_CREATE = "CREATE"; 
	public final static String ACTION_NEXT = "DELETE"; 
	public final static String ACTION_BACK = "BACK";
	
	
	public abstract void create();
	public abstract void update();
	public abstract void delete();
	
	
}
