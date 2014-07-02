package model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BookingRoom extends ModelHelp implements Booking{

	private int bid;
	private Date erfassungsdatum;
	private Date von;
	private Date bis;
	private Gast gast;
	private Zimmer zimmer;
	
	public BookingRoom (Gast gast, Zimmer zimmer, Date heute) {
		this.gast = gast;
		this.setZimmer(zimmer);
		erfassungsdatum = heute;
	}
	
	public BookingRoom (Gast gast, Date heute, Date von, Date bis){
		this.gast = gast;
		erfassungsdatum = heute;
		this.von = von;
		
	}
	
	public BookingRoom(int bid){
		this.bid = bid;
	}
	
	
	public void book(HotelObject zimmer){
		//für existierende Gäste wird AI Feld mit ausgelesen
		Zimmer room = (Zimmer) zimmer;
		
		if (!getGast().isExisting()){
			int GID = writeDbAi("INSERT INTO gast (Vorname, Name, Strasse, Hausnummer, Postleitzahl, Ort, Land, Telefonnummer, Geburtstag) " + "VALUES('"+ getGast().getVorname() + 
					"', '"+ getGast().getName() +"', '"+ getGast().getStrasse() +"', '"+ getGast().getHn()+"', "+ getGast().getPlz()+", '"
					+getGast().getOrt()+"', '"+ getGast().getLand() +"', '"+ getGast().getTel()+"', '"+ getSQLDate(getGast().getGeb())+"')"); 
			
			getGast().setGid(GID);
		}
		
		
		Calendar Von = new GregorianCalendar();
		Calendar Bis = new GregorianCalendar();
		
		Von.setTime(getVon());
		Bis.setTime(getBis());
		
		
		long time = Bis.getTime().getTime() - Von.getTime().getTime();  // Differenz in ms
		long days = Math.round( (double)time / (24. * 60.*60.*1000.) );     // Differenz in Tagen
		//Aufenthaltsdauer wurde berechnet
		//Preis wird berechnet
		String preis = selectDB("select Preis from zimmer where ZID = '"+ room.getZid()+"'");
		//Gesamtpreis wird berechnet und gesetzt
		this.bid = writeDbAi("INSERT INTO buchung (GID, Erfassungsdatum, Gesamtpreis) VALUES("+getGast().getGid()+", '" + getSQLDate(getErfassungsdatum()) + "', "+preis+"*"+days+")");
		//hotel-zimmerbuchung wird geschrieben
		writeDbAi("INSERT INTO `zimmer-buchung` (BID, ZID, Von, Bis) VALUES("+getBid()+", "+room.getZid()+
		", '" + getSQLDate(getVon()) + "', '" + getSQLDate(getBis())+"'  )");
	}
	
	public Date getBis() {
		return bis;
	}

	public Date getVon() {
		return von;
	}

	public void setBis(Date bis) {
		this.bis = bis;
	}
	
	public void setVon(Date von){
		this.von = von;
	}
	
	public Date getErfassungsdatum(){
		return erfassungsdatum;
	}
	
	public void cancel(Booking raumbuchung, HotelObject raum) {
		//stornierung Zimmerbuchung
		BookingRoom bookingRoom = (BookingRoom) raumbuchung;
		this.bid=bookingRoom.getBid();
		writeDb("delete from `zimmer-buchung` where BID = " + bid);
		writeDb("delete from buchung where BID = " +bid);
	}
	
	public int getBid(){
		return bid;
	}
	
	public void setBid(int bid){
		this.bid = bid;
	}
	
	public Gast getGast(){
		return gast;
	}
	
	public void setGast(Gast gast){
		this.gast = gast;
	}

	public Zimmer getZimmer() {
		return zimmer;
	}

	public void setZimmer(Zimmer zimmer) {
		this.zimmer = zimmer;
	}
}
