package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Buchung extends ModelHelp{

	private Date erfassungsdatum;
	
	private Date von;
	private Date bis;
	private int bid;
	private int dlbid;
	private Gast gast;
	private Zimmer zimmer;
	
	//Konstruktoren
	public Buchung (Gast gast, Zimmer zimmer, Date heute) {
		this.gast = gast;
		this.zimmer = zimmer;
		erfassungsdatum = heute;
	}
	
	public Buchung(int bid){
		this.bid = bid;
	}
	
	
	public void addBuchung(Connection con){
		String query = "INSERT INTO buchung (GID, Erfassungsdatum) VALUES("+gast.getGid()+", '" + getSQLDate(erfassungsdatum) + "')";
		this.bid = writeDbAi(query, con);
		System.out.println(query);
	}
	
	/**Zimmerbuchung
	 * 
	 * @param von
	 * @param bis
	 * @throws SQLException 
	 */
	public void bookZimmer(Date von, Date bis, Connection con) throws SQLException{
	
		
		Calendar Von = new GregorianCalendar();
		Calendar Bis = new GregorianCalendar();
		
		Von.setTime(von);
		Bis.setTime(bis);
		
		
		long time = Bis.getTime().getTime() - Von.getTime().getTime();  // Differenz in ms
		long days = Math.round( (double)time / (24. * 60.*60.*1000.) );     // Differenz in Tagen
		//Aufenthaltsdauer wurde berechnet
		//Preis wird berechnet
		String preis = selectDB("select Preis from zimmer where ZID = '"+ zimmer.getZid()+"'");
		//hotel-zimmerbuchung wird geschrieben
		String queryZimmerBooking = "INSERT INTO `zimmer-buchung` (BID, ZID, Von, Bis) VALUES("+getBid()+", "+zimmer.getZid()+
				", '" + getSQLDate(von) + "', '" + getSQLDate(bis)+"'  )";
		System.out.println(queryZimmerBooking);
		writeDbAi(queryZimmerBooking, con);
		//Gesamtpreis wird berechnet und gesetzt
		String queryUpdatePreis = "update buchung set Gesamtpreis = Gesamtpreis+"+preis+"*"+days+ " where BID = "+this.bid;
		writeDb(queryUpdatePreis, con);
		System.out.println(queryUpdatePreis);		
	}
	
	public void bookDl(Buchung buchung, Dienstleistung dl, Connection con) {
		
		//Dl-Buchung
		String query = "insert into `dl-buchung` (DID, BID, Datum) values ("+dl.getDid()+", "+buchung.getBid()+", '"+getSQLDate(dl.getDate())+"')";
		int DLBID = writeDbAi(query, con);
		dlbid = DLBID;
		//Preis wird verändert
		String query2 = "update buchung set Gesamtpreis = Gesamtpreis + (select Preis from dienstleistung where DID = '" + dl.getDid() +"')";
		writeDb(query2, con);
	}
	
	public void cancelZimmer(Buchung buchung, Connection con) {
		//stornierung Zimmerbuchung
		this.bid=buchung.getBid();
		writeDb("delete from `zimmer-buchung` where BID = " + bid, con);
		writeDb("delete from buchung where BID = " +bid, con);
		
	}
	
	public void cancelDl (Buchung buchung, Dienstleistung dl, Connection con){
		//stornieren Dienstleistung
		this.bid = buchung.getBid();
		dlbid = buchung.getDlbid();
		//Gesamtpreis wird berechnet
		writeDb("update buchung set Gesamtpreis = Gesamtpreis - (select Preis from dienstleistung where DID = '" + dl.getDid() +"')", con);
		writeDb("delete from `dl-buchung` where DLBID = "+dlbid, con);
	}
	
	//getter- und setter-Methoden
	public int getBid() {
		return bid;
	}
	
	public int getDlbid(){
		return dlbid;
	}
	
	public void setDlbid(int dlbid){
		this.dlbid = dlbid;
	}
	
	public void setVon(Date von){
		this.von = von;
	}
	
	public void setBis(Date bis){
		this.bis = bis;
	}
	
	public Date getVon() {
		return von;
	}
	
	public Date getBis(){
		return bis;
	}

	public Zimmer getZimmer() {
		return zimmer;
	}

	public void setZimmer(Zimmer zimmer) {
		this.zimmer = zimmer;
	}
}

