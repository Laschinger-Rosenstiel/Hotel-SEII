package model;

public class BookingService extends ModelHelp implements Booking{
	
	private int dlbid;
	private int bid;

	public BookingService(int bid){
		this.bid = bid;
	}
		
	public void book(HotelObject dl) {
		
		//Dl-Buchung
		Dienstleistung service = (Dienstleistung) dl;
		
		String query = "insert into `dl-buchung` (DID, BID, Datum) values ("+service.getDid()+", "+getBid()+", '"+getSQLDate(service.getDate())+"')";
		int DLBID = writeDbAi(query);
		dlbid = DLBID;
		//Preis wird verändert
		String query2 = "update buchung set Gesamtpreis = Gesamtpreis + (select Preis from dienstleistung where DID = '" + service.getDid() +"')";
		writeDb(query2);
}
	
	public void cancel (Booking buchung, HotelObject dl){
		BookingRoom bookingRoom = (BookingRoom) buchung;
		Dienstleistung service = (Dienstleistung) dl;
		//stornieren Dienstleistung
		setBid(bookingRoom.getBid());
		//dlbid = bookingRoom.getDlbid();
		//Gesamtpreis wird berechnet
		writeDb("update buchung set Gesamtpreis = Gesamtpreis - (select Preis from dienstleistung where DID = '" + service.getDid() +"')");
		writeDb("delete from `dl-buchung` where DLBID = "+dlbid);
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

}
