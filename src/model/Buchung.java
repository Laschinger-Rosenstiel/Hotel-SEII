package model;

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
	
	//Zimmerbuchung
	public void bookZimmer(Date von, Date bis){
		//für existierende Gäste wird AI Feld mit ausgelesen
		if (!gast.isExisting()){
			int GID = writeDbAi("INSERT INTO gast (Vorname, Name, Strasse, Hausnummer, Postleitzahl, Ort, Land, Telefonnummer, Geburtstag) " + "VALUES('"+ gast.getVorname() + 
					"', '"+ gast.getName() +"', '"+ gast.getStrasse() +"', '"+ gast.getHn()+"', "+ gast.getPlz()+", '"
					+gast.getOrt()+"', '"+ gast.getLand() +"', '"+ gast.getTel()+"', '"+ getSQLDate(gast.getGeb())+"')"); 
			
			gast.setGid(GID);
		}
		
		
		Calendar Von = new GregorianCalendar();
		Calendar Bis = new GregorianCalendar();
		
		Von.setTime(von);
		Bis.setTime(bis);
		
		
		long time = Bis.getTime().getTime() - Von.getTime().getTime();  // Differenz in ms
		long days = Math.round( (double)time / (24. * 60.*60.*1000.) );     // Differenz in Tagen
		//Aufenthaltsdauer wurde berechnet
		//Preis wird berechnet
		String preis = selectDB("select Preis from hotel.zimmer where ZID = '"+ zimmer.getZid()+"'");
		//Gesamtpreis wird berechnet und gesetzt
		this.bid = writeDbAi("INSERT INTO buchung (GID, Erfassungsdatum, Gesamtpreis) VALUES("+gast.getGid()+", '" + getSQLDate(erfassungsdatum) + "', "+preis+"*"+days+")");
		//hotel-zimmerbuchung wird geschrieben
		writeDbAi("INSERT INTO hotel.`zimmer-buchung` (BID, ZID, Von, Bis) VALUES("+getBid()+", "+zimmer.getZid()+
		", '" + getSQLDate(von) + "', '" + getSQLDate(bis)+"'  )");
	}
	
	public void bookDl(Buchung buchung, Dienstleistung dl) {
		
		//Dl-Buchung
		String query = "insert into hotel.`dl-buchung` (DID, BID, Datum) values ("+dl.getDid()+", "+buchung.getBid()+", '"+getSQLDate(dl.getDate())+"')";
		int DLBID = writeDbAi(query);
		dlbid = DLBID;
		//Preis wird verändert
		String query2 = "update hotel.buchung set Gesamtpreis = Gesamtpreis + (select Preis from hotel.dienstleistung where DID = '" + dl.getDid() +"')";
		writeDb(query2);
	}
	
	public void cancelZimmer(Buchung buchung) {
		//stornierung Zimmerbuchung
		this.bid=buchung.getBid();
		writeDb("delete from hotel.`zimmer-buchung` where BID = " + bid);
		writeDb("delete from hotel.buchung where BID = " +bid);
		
	}
	
	public void cancelDl (Buchung buchung, Dienstleistung dl){
		//stornieren Dienstleistung
		this.bid = buchung.getBid();
		dlbid = buchung.getDlbid();
		//Gesamtpreis wird berechnet
		writeDb("update hotel.buchung set Gesamtpreis = Gesamtpreis - (select Preis from hotel.dienstleistung where DID = '" + dl.getDid() +"')");
		writeDb("delete from hotel.`dl-buchung` where DLBID = "+dlbid);
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
}

