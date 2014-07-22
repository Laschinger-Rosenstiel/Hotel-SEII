package model;

import java.sql.Connection;

public class Zimmer extends ModelHelp
{

	String zid;
	String typ;
	double preis;
	String znr;
	Connection writeCon;
	
	public Zimmer (String zid) {
		this.zid = zid;
	}
	public Zimmer(String zn, String typZ, double p)
	{
		zid = zn;
		typ = typZ;
		preis = p;
	}
	
	public Zimmer(String zn, String typZ, double p, String id)
	{
		zid = id;
		typ = typZ;
		preis = p;
		znr = zn;
	}
	//Ändert Zimmerdaten
	public void updateZimmer()
	{
		writeDb("update zimmer set ZID = '" + znr +"',  Typ = '"+ typ +"', Preis = '"+ preis+"' where ZID = "+zid, getWriteCon());
		commitDbConnection(getWriteCon());
	}
	//Schreibt neues Zimmer auf die DB
	public void createZimmer()
	{
		writeDb("INSERT INTO zimmer (ZID, Typ, Preis)" + "VALUES('"+ zid + 
				"', '"+ typ+"', '"+ preis+"')", getWriteCon());
		commitDbConnection(getWriteCon());
	}
	//Löscht Zimmer von der DB
	public void deleteZimmer()
	{
		String query = "DELETE from " + "zimmer" + " WHERE " + 
				"ZID" + " = '" + zid + "'"; 
		writeDb(query, getWriteCon());
		commitDbConnection(getWriteCon());
	}
	
	public void setZnr(String x)
	{
		znr = x;
	}
	
	public String getZid(){
		return zid;
	}
	
	public String getTyp(){
		return typ;
	}
	
	public void setTyp(String typ){
		this.typ = typ;
	}
	
	public Double getPreis(){
		return preis;
	}
	
	public void setPreis(Double preis){
		this.preis = preis;
	}
	
	private Connection getWriteCon(){
		if (writeCon ==null){
			writeCon = openDbConnection();
		}
		return writeCon;
	}
	
}
