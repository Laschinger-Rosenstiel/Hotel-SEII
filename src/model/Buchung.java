package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import control.JTableview;

public class Buchung extends ModelHelp{

	private Date erfassungsdatum;
	
	private Date von;
	private Date bis;
	private int bid;
	private int dlbid;
	private int zbid;
	private Gast gast;
	private Zimmer zimmer;
	
	//Konstruktoren
	public Buchung (Gast gast, Zimmer zimmer, Date heute, Date von, Date bis) {
		this.gast = gast;
		this.zimmer = zimmer;
		erfassungsdatum = heute;
		this.von = von;
		this.bis = bis;
	}
	
	public Buchung(int bid){
		this.bid = bid;
	}
	
	public Buchung(int bid, int zbid){
		this.bid = bid;
		this.zbid=zbid;
	}	
	
	public void addBuchung(Connection con){
		String query = "INSERT INTO buchung (GID, Erfassungsdatum, Von, Bis) VALUES("+gast.getGid()+", '" + getSQLDate(erfassungsdatum) + "', '"+getSQLDate(getVon())+"', '"+getSQLDate(getBis())+"')";
		this.bid = writeDbAi(query, con);
		System.out.println(query);
	}
	
	/**Zimmerbuchung
	 * 
	 * @param von
	 * @param bis
	 * @throws SQLException 
	 */
	public void bookZimmer(Connection con) throws SQLException{
	
		
		long days = getBookedDays(getVon(), getBis());
		//Aufenthaltsdauer wurde berechnet
		//Preis wird berechnet
		String preis = selectDB("select Preis from zimmer where ZID = '"+ zimmer.getZid()+"'");
		//hotel-zimmerbuchung wird geschrieben
		String queryZimmerBooking = "INSERT INTO `zimmer-buchung` (BID, ZID) VALUES("+getBid()+", "+zimmer.getZid()+
				")";
	
		System.out.println(queryZimmerBooking);
		writeDbAi(queryZimmerBooking, con);
		//Gesamtpreis wird berechnet und gesetzt
		String queryUpdatePreis = "update buchung set Gesamtpreis = Gesamtpreis+"+preis+"*"+days+ " where BID = "+this.bid;
		System.out.println(queryUpdatePreis);
		
		writeDb(queryUpdatePreis, con);	
	}
	
	public long getBookedDays(Date von, Date bis){
		
		Calendar Von = new GregorianCalendar();
		Calendar Bis = new GregorianCalendar();
		
		Von.setTime(von);
		Bis.setTime(bis);
		
		
		long time = Bis.getTime().getTime() - Von.getTime().getTime();  // Differenz in ms
		long days = Math.round( (double)time / (24. * 60.*60.*1000.) );     // Differenz in Tagen
		
		return days;
	}
	
	public String getPreis(int bid, Connection con){
		String query = "select buchung.Gesamtpreis from buchung where BID = "+bid;
		return selectDbWithCon(query, con);
	}
	
	public void bookDl(Buchung buchung, Dienstleistung dl, Connection con) {
		
		//Dl-Buchung
		String query = "insert into `dl-buchung` (DID, BID, Datum) values ("+dl.getDid()+", "+buchung.getBid()+", '"+getSQLDate(dl.getDate())+"')";
		int DLBID = writeDbAi(query, con);
		dlbid = DLBID;
		//Preis wird verändert
		String query2 = "update buchung set Gesamtpreis = Gesamtpreis + (select Preis from dienstleistung where DID = '" + dl.getDid() +"') where BID = '"+getBid()+"'";
		writeDb(query2, con);
	}
	
	public void cancelZimmer(Buchung buchung, Connection con) throws ParseException {
		//stornierung Zimmerbuchung
		this.bid=buchung.getBid();
		this.zbid =buchung.getZbid();
		
		
		
		String von = selectDbWithCon("select Von from buchung where bid = "+buchung.getBid(),con); 
		String bis = selectDbWithCon("select Bis from buchung where bid = "+buchung.getBid(),con); 
		
		
		Date Von = new SimpleDateFormat("yyyy-MM-dd").parse(von);
		Date Bis = new SimpleDateFormat("yyyy-MM-dd").parse(bis);
		//Neuberechnung Preis
		long days = getBookedDays(Von, Bis);
		String zid = selectDbWithCon("select zb.ZID from `zimmer-buchung` zb where zb.ZBID = "+zbid, con);
		int preisZimmer = Integer.parseInt(selectDbWithCon("select Preis from zimmer where ZID = "+zid, con));
		
		writeDb("delete from `zimmer-buchung` where ZBID = " + zbid, con);
		writeDb("update buchung set Gesamtpreis = Gesamtpreis - ("+preisZimmer+" * "+days+ ") where bid = "+buchung.getBid(), con);
		
		//löscht Buchung wenn keine Hotelzimmerbuchung mehr vorliegt
		int count = Integer.parseInt(selectDbWithCon("select count(*) from `zimmer-buchung` where BID = " +bid, con));
		if (count == 0){
			writeDb("delete from buchung where BID = " +bid, con);	
		}
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
	
	public JTableview getBookedZimmerTable (Connection con){
		String query = "select zb.ZID, b.Von, b.Bis from `zimmer-buchung` zb, buchung b where zb.bid = b.BID and zb.bid =" + getBid();
		JTableview bookedZimmerTable;
		if (con == null){
			bookedZimmerTable = new JTableview(query);
			
		}
		else {
			bookedZimmerTable = new JTableview(query, con);
		}
		return bookedZimmerTable;
	}
	
	public JTableview getBookedDlTable (Connection con){
		String query = "SELECT dl.Bezeichnung, dlb.Datum from `dl-buchung` dlb, dienstleistung dl where dlb.BID =" + getBid() +" and dl.did = dlb.did";
		JTableview bookedDlTable;
		if (con == null){
			bookedDlTable = new JTableview(query);
		}
		else {
			bookedDlTable = new JTableview(query, con);
		}
		return bookedDlTable;
	}

	public int getZbid() {
		return zbid;
	}

	public void setZbid(int zbid) {
		this.zbid = zbid;
	}
}

