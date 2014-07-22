package model;

import java.sql.Connection;
import java.util.Date;


public class Gast extends ModelHelp{

	private String vorname;
	private String name;
	private String strasse;
	private String hn;
	private String plz;
	private String ort;
	private String land;
	private String tel;
	private Date geb;
	private boolean existing;
	private int gid;
	
	
	public Gast (String id,String vor, String name, String str, String hn, String plz, String ort, String land, String tel, Date geb) {
		vorname = vor;
		this.name = name;
		this.strasse = str;
		this.hn = hn;
		this.plz = plz;
		this.ort = ort;
		this.land = land;
		this.tel = tel;
		this.geb = geb;
		gid = Integer.parseInt(id);
	}
	
	public Gast (String vor, String name, String str, String hn, String plz, String ort, String land, String tel, Date geb) {
		vorname = vor;
		this.name = name;
		strasse = str;
		this.hn = hn;
		this.plz = plz;
		this.ort = ort;
		this.land = land;
		this.tel = tel;
		this.geb = geb;
	}
	
	public Gast (int gid, String vor, String name, Date geb){
		this.gid = gid;
		vorname = vor;
		this.name = name;
		this.geb = geb;
	}
	public Gast(int gid) {
		this.gid = gid;
	}
	
	/**Gast hinzufügen
	 * 
	 */
	public void addGast(Connection con){
		int GID = writeDbAi("INSERT INTO gast (Vorname, Name, Strasse, Hausnummer, Postleitzahl, Ort, Land, Telefonnummer, Geburtstag) " + "VALUES('"+ getVorname() + 
				"', '"+ getName() +"', '"+ getStrasse() +"', '"+ getHn()+"', "+ getPlz()+", '"
				+getOrt()+"', '"+ getLand() +"', '"+ getTel()+"', '"+ getSQLDate(getGeb())+"')", con); 
		
		setGid(GID);
	}
	
	/**Löscht Gast aus der DB
	 * 
	 */
	public void deleteGast(Connection con){
		writeDb("delete from gast where GID = " + getGid(), con);
	}
	
	/**Ändert Gastdaten
	 * 
	 */
	public void updateGast(Connection con)
	{
		
		writeDb("update gast set Vorname = '" + getVorname() +"',  Name = '"+ getName()+"', Strasse = '"+ getStrasse() +"', Hausnummer = '"+ getHn() +"', Postleitzahl = '"+ getPlz() +"',"
				+ "Ort = '" + getOrt() +"', Land = '" + getLand() +"', Geburtstag = '" + getSQLDate(getGeb()) +"', Telefonnummer = '" + getTel()+"' where GID = "+getGid(), con);
	}
		
	public String getVorname(){
		return this.vorname;
	}
	
	public void setVorname(String vorname){
		this.vorname = vorname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getHn() {
		return hn;
	}

	public void setHn(String hn) {
		this.hn = hn;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String getLand() {
		return land;
	}

	public void setLand(String land) {
		this.land = land;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Date getGeb() {
		return geb;
	}

	public void setGeb(Date geb) {
		this.geb = geb;
	}

	public boolean isExisting() {
		return existing;
	}

	public void setExisting(boolean existing) {
		this.existing = existing;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}


	
	
	
	
}
