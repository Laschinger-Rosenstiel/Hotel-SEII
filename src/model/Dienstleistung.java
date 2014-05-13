package model;

import java.util.Date;

public class Dienstleistung extends ModelHelp {

	int did;
	Date date;
	String typ;
	int dnr;
	double preis;
	
	public Dienstleistung(int did, Date date) {
		this.did = did;
		this.date = date;
	}
	
	public Dienstleistung(int did, String typ, double preis)
	{
		this.did = did;
		this.typ = typ;
		this.preis = preis;
	}
	
	public Dienstleistung (String typ, double preis) {
		this.typ = typ;
		this.preis = preis;
	}
	
	public Dienstleistung(int did,String typ,double preis,int dnr)
	{
		this.did = did;
		this.typ = typ;
		this.preis = preis;
		this.dnr = dnr;
	}


	public Dienstleistung(int did) {
		this.did = did;
	}
	//Neue Dienstleistung auf die DB schreiben
	public void createDienst()
	{
		writeDb("INSERT INTO dienstleistung (Bezeichnung, Preis)" + "VALUES('" + typ + "', '" + preis +"')");
	}
	//Ändern der Dienstleistungsdaten
	public void updateDienst()
	{
		writeDb("update dienstleistung set DID = '" + did +"', Bezeichnung = '" + typ + "',  Preis = '" + preis + "' where DID = "+ dnr);
	}
	//Löscht Dienstleistung aus der DB
	public void deleteDienst()
	{
		String query = "DELETE from " + "dienstleistung" + " WHERE " + 
				"DID" + " = '" + did + "'"; 
		writeDb(query); 
	}
	
	public int getDid(){
		return did;
	}
	
	public Date getDate(){
		return date;
	}
}
