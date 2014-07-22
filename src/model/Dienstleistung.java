package model;

import java.sql.Connection;
import java.util.Date;

public class Dienstleistung extends ModelHelp {

	int did;
	Date date;
	String typ;
	int dnr;
	double preis;
	Connection writeCon;
	
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
		writeDb("INSERT INTO dienstleistung (Bezeichnung, Preis)" + "VALUES('" + typ + "', '" + preis +"')", getWriteCon());
		commitDbConnection(getWriteCon());
	}
	//Ändern der Dienstleistungsdaten
	public void updateDienst()
	{
		writeDb("update dienstleistung set Bezeichnung = '" + typ + "',  Preis = '" + preis + "' where DID = "+ getDid(), getWriteCon());
		commitDbConnection(getWriteCon());
	}
	//Löscht Dienstleistung aus der DB
	public void deleteDienst()
	{
		String query = "DELETE from " + "dienstleistung" + " WHERE " + 
				"DID" + " = '" + did + "'"; 
		writeDb(query, getWriteCon()); 
		commitDbConnection(getWriteCon());
	}
	
	public int getDid(){
		return did;
	}
	
	public Date getDate(){
		return date;
	}
	
	public void setTyp(String typ){
		this.typ = typ;
	}
	
	public void setPreis(Double preis){
		this.preis = preis;
	}
	
	public Connection getWriteCon(){
		if (writeCon==null){
			writeCon = openDbConnection();
		}
		return writeCon;
	}
	
}
