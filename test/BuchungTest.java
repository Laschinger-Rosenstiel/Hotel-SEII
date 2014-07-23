import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Calendar;
import java.util.Date;

import model.Buchung;
import model.Dienstleistung;
import model.Gast;
import model.Zimmer;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;


public class BuchungTest extends TestHelp{
	
	static int gid;
	static int bid;
	static Buchung buchung;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		String sDbDriver=null, sDbUrl=null, sUsr="", sPwd=""; 
		sDbDriver = "com.mysql.jdbc.Driver"; 
		sDbUrl = "jdbc:mysql://localhost:3306/hotel-seII"; 
		sUsr = "root"; 
		sPwd = "init"; 
		Class.forName( sDbDriver ); 
		Connection cn = DriverManager.getConnection( sDbUrl, sUsr, sPwd ); 
		cn.setAutoCommit(false);
		
		Calendar geb = Calendar.getInstance();
		geb.set(1991, Calendar.JUNE, 13, 0, 0, 0);
		Date gebDate = geb.getTime();
		
		Gast gast = new Gast("Cranberry", "Nuckles", "Ichostraße", "4", "81541", "München", "Deutschland", "+49 89 23456", gebDate);
		gast.addGast(cn);
		
		cn.commit();
		gid = gast.getGid();
	}

	/*@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}*/

	

	@Test
	public void testBuchungGastZimmerDateDateDate() {
		Calendar von = Calendar.getInstance();
		von.set(2014, Calendar.AUGUST, 13, 0, 0, 0);
		Date vonDate = von.getTime();
		
		Calendar bis = Calendar.getInstance();
		bis.set(2014, Calendar.AUGUST, 20, 0, 0, 0);
		Date bisDate = bis.getTime();
		
		Calendar geb = Calendar.getInstance();
		geb.set(1991, Calendar.JUNE, 13, 0, 0, 0);
		Date gebDate = geb.getTime();
		
		Gast gast = new Gast("Wilhelm", "Laschinger", "Ichostraße", "4", "81541", "München", "Deutschland", "+49 89 23456", gebDate);
		Zimmer zimmer = new Zimmer("3.001");
		
		Buchung buchung = new Buchung(gast, zimmer, new Date(), vonDate, bisDate);
		
		assertTrue("Buchung angelegt ", buchung != null);
	}

	@Test
	public void testBuchungInt() {
		Buchung buchungInt = new Buchung(231);
		assertTrue("BuchungInt angelegt", buchungInt != null);
	}

	@Test
	public void testBuchungIntInt() {
		
		Buchung buchungIntInt = new Buchung(231, 22);
		assertTrue("BuchungIntInt angelegt", buchungIntInt != null);
	}

	@Test
	public void testAddBuchung() {
		//Buchung1
		Calendar von = Calendar.getInstance();
		von.set(2014, Calendar.AUGUST, 13, 0, 0, 0);
		Date vonDate = von.getTime();
		
		Calendar bis = Calendar.getInstance();
		bis.set(2014, Calendar.AUGUST, 20, 0, 0, 0);
		Date bisDate = bis.getTime();
		
		Gast gast = new Gast(gid);
		Zimmer zimmer = new Zimmer("1.002");
		
		Buchung buchung = new Buchung(gast, zimmer, new Date(), vonDate, bisDate);
		Connection con = openDbConnection();
		buchung.addBuchung(con);
		commitDbConnection(con);
		closeDbConnection(con);
		
		bid = buchung.getBid();
		setBuchung(buchung);
		int gastId = Integer.parseInt(selectDB("select GID from buchung where bid = "+ buchung.getBid()));
		String erfDatum = selectDB("select Erfassungsdatum from buchung where bid = "+ buchung.getBid());
		String vonDatum = selectDB("select Von from buchung where bid = " + buchung.getBid());
		String bisDatum = selectDB("select Bis from buchung where bid = " + buchung.getBid());
		getSQLDate(vonDate);
		getSQLDate(bisDate);
		assertTrue("GID der Buchung: " + gastId + " = " +gid, gid == gastId);
		assertTrue("Erfassungsdatum der Buchung: "+erfDatum + " = " + getSQLDate(new Date()), erfDatum.equals(getSQLDate(new Date())));
		assertTrue("Von-Datum der Buchung: "+vonDatum+" = "+ getSQLDate(vonDate), vonDatum.equals(getSQLDate(vonDate)));
		assertTrue("Bis-Datum der Buchung: "+bisDatum+" = "+ getSQLDate(bisDate), bisDatum.equals(getSQLDate(bisDate)));
		
		}

	@Test
	public void testBookZimmer() {
		Buchung buchung = getBuchung();
		Connection con = openDbConnection();
		
		buchung.bookZimmer(con);
		
		
		int bid = buchung.getBid();
		int zbid = buchung.getZbid();
		String zid = buchung.getZimmer().getZid();
		
		
	
		
		Zimmer zimmer2 = new Zimmer("1.001");
		buchung.setZimmer(zimmer2);
		
		buchung.bookZimmer(con);
		
		commitDbConnection(con);
		closeDbConnection(con);
		
		int zbid2 = buchung.getZbid();
		String zid2 = buchung.getZimmer().getZid();
		
		int buchungId = Integer.parseInt(selectDB("select BID from `zimmer-buchung` where ZBID = "+ zbid));
		int buchungId2 = Integer.parseInt(selectDB("select BID from `zimmer-buchung` where ZBID = "+ zbid));
		String zimmerId = selectDB("select ZID from `zimmer-buchung` where ZBID = "+ zbid);
		String zimmerId2 = selectDB("select ZID from `zimmer-buchung` where ZBID = "+ zbid2);
		
		int preisZimmer1 = Integer.parseInt(selectDB("select Preis from zimmer where ZID = '"+zid+"'")); 
		int preisZimmer2 = Integer.parseInt(selectDB("select Preis from zimmer where ZID = '"+zid2+"'")); 
		int gesamtpreis = preisZimmer1 * 7 + preisZimmer2*7;
		int gesamtpreisDb = Integer.parseInt(selectDB("select Gesamtpreis from buchung where BID = " +bid)) ;
				
		assertTrue("BID der Zimmerbuchung1: " + bid + " = " +buchungId, bid == buchungId);
		assertTrue("BID der Zimmerbuchung2: " + bid + " = " +buchungId2, bid == buchungId2);
		assertTrue("Beide BIDs werden verglichen: " + buchungId + " = " + buchungId2, buchungId==buchungId2);
		assertTrue("ZimmerNr der Zimmerbuchung1: "+zid  + " = " + zimmerId, zimmerId.equals(zid));
		assertTrue("ZimmerNr der Zimmerbuchung1: "+zid2  + " = " + zimmerId2, zimmerId2.equals(zid2));
		assertTrue("Gesamtpreis der Buchung: "+gesamtpreis  + " = " + gesamtpreisDb, gesamtpreis == gesamtpreisDb);
	}

	@Test
	public void testGetBookedDays() {
		Calendar von = Calendar.getInstance();
		von.set(2014, Calendar.AUGUST, 13, 0, 0, 0);
		Date vonDate = von.getTime();
		
		Calendar bis = Calendar.getInstance();
		bis.set(2014, Calendar.AUGUST, 20, 0, 0, 0);
		Date bisDate = bis.getTime();
		
		Gast gast = new Gast(26);
		Zimmer zimmer = new Zimmer("3.1002");
		
		Buchung buchung = new Buchung(gast, zimmer, new Date(), vonDate, bisDate);
		long days = buchung.getBookedDays(buchung.getVonDate(), buchung.getBisDate());
		
		assertTrue("Dauer der Buchung: "+days  + " = " + 7, days == 7);
		
	}

	@Test
	public void testGetPreis() {
		Calendar von = Calendar.getInstance();
		von.set(2014, Calendar.AUGUST, 13, 0, 0, 0);
		Date vonDate = von.getTime();
		
		Calendar bis = Calendar.getInstance();
		bis.set(2014, Calendar.AUGUST, 20, 0, 0, 0);
		Date bisDate = bis.getTime();
		
		Calendar geb = Calendar.getInstance();
		geb.set(1991, Calendar.JUNE, 13, 0, 0, 0);
		Date gebDate = geb.getTime();
		
		Connection con = openDbConnection();
		Gast gast = new Gast("Test", "GetPreis", "Ichostraße", "4", "81541", "München", "Deutschland", "+49 89 23456", gebDate);
		gast.addGast(con);
		
		Zimmer zimmer = new Zimmer("1.001");
		Zimmer zimmer2 = new Zimmer("1.002");
		Buchung buchung = new Buchung(gast, zimmer, new Date(), vonDate, bisDate);
		buchung.addBuchung(con);
		
		buchung.bookZimmer(con);
		buchung.setZimmer(zimmer2);
		buchung.bookZimmer(con);
		
		commitDbConnection(con);
		closeDbConnection(con);
		int preisZimmer1 = Integer.parseInt(selectDB("select Preis from zimmer where ZID = '"+zimmer.getZid()+"'")); 
		int preisZimmer2 = Integer.parseInt(selectDB("select Preis from zimmer where ZID = '"+zimmer2.getZid()+"'")); 
		long days = buchung.getBookedDays(vonDate, bisDate);
		int gesamtpreis = (int) (preisZimmer1 * days + preisZimmer2* days);
		int gesamtpreisDb = Integer.parseInt(selectDB("select Gesamtpreis from buchung where BID = " +buchung.getBid())) ;
		assertTrue("Gesamtpreis der Buchung: "+gesamtpreis  + " = " + gesamtpreisDb, gesamtpreis == gesamtpreisDb);
		
		
	}

	@Test
	public void testBookDl() {
		Calendar von = Calendar.getInstance();
		von.set(2014, Calendar.AUGUST, 13, 0, 0, 0);
		Date vonDate = von.getTime();
		
		Calendar bis = Calendar.getInstance();
		bis.set(2014, Calendar.AUGUST, 20, 0, 0, 0);
		Date bisDate = bis.getTime();
		
		Calendar geb = Calendar.getInstance();
		geb.set(1991, Calendar.JUNE, 13, 0, 0, 0);
		Date gebDate = geb.getTime();
		
		Connection con = openDbConnection();
		Gast gast = new Gast("Test", "GetPreis", "Ichostraße", "4", "81541", "München", "Deutschland", "+49 89 23456", gebDate);
		gast.addGast(con);
		
		Zimmer zimmer = new Zimmer("1.001");
		Zimmer zimmer2 = new Zimmer("1.002");
		Dienstleistung dl = new Dienstleistung(2, new Date());
		Dienstleistung dl2 = new Dienstleistung(3, new Date());
		Buchung buchung = new Buchung(gast, zimmer, new Date(), vonDate, bisDate);
		buchung.addBuchung(con);
		
		buchung.bookZimmer(con);
		buchung.setZimmer(zimmer2);
		buchung.bookZimmer(con);
		
		buchung.bookDl(buchung, dl, con);
		int dlbid = buchung.getDlbid();
		int did = 2;
		buchung.bookDl(buchung, dl2, con);
		commitDbConnection(con);
		closeDbConnection(con);
		
		int dlbid2 = buchung.getDlbid();
		int did2 = 3;
		String erfDatum = getSQLDate(new Date());
		String erfDatum2 = getSQLDate(new Date());
		
		int dId = Integer.parseInt(selectDB("select DID from `dl-buchung` where DLBID = "+dlbid));
		int bId = Integer.parseInt(selectDB("select BID from `dl-buchung` where DLBID = "+dlbid));
		int dId2 = Integer.parseInt(selectDB("select DID from `dl-buchung` where DLBID = "+dlbid2));
		
		String erfDate = selectDB("select Datum from `dl-buchung` where DLBID = "+dlbid);
		String erfDate2 = selectDB("select Datum from `dl-buchung` where DLBID = "+dlbid2);
		
		assertTrue("DID der Dl-Buchung: "+did+ " = " + dId, did == dId);
		assertTrue("DID der Dl-Buchung2: "+did2+ " = " + dId2, did2 == dId2);
		assertTrue("BID der Dl-Buchungen: "+buchung.getBid()+ " = " + bId, buchung.getBid() == bId);
		assertTrue("Datum der Dl-Buchung: "+erfDatum+ " = " + erfDate, erfDatum.equals(erfDate));
		assertTrue("Datum der Dl-Buchung2: "+erfDatum2+ " = " + erfDate2, erfDatum2.equals(erfDate2));
		
	}


	@Test
	public void testCancelZimmer() {
		Calendar von = Calendar.getInstance();
		von.set(2014, Calendar.AUGUST, 13, 0, 0, 0);
		Date vonDate = von.getTime();
		
		Calendar bis = Calendar.getInstance();
		bis.set(2014, Calendar.AUGUST, 20, 0, 0, 0);
		Date bisDate = bis.getTime();
		
		Calendar geb = Calendar.getInstance();
		geb.set(1991, Calendar.JUNE, 13, 0, 0, 0);
		Date gebDate = geb.getTime();
		
		Connection con = openDbConnection();
		Gast gast = new Gast("Test", "GetPreis", "Ichostraße", "4", "81541", "München", "Deutschland", "+49 89 23456", gebDate);
		gast.addGast(con);
		
		Zimmer zimmer = new Zimmer("1.001");
		Zimmer zimmer2 = new Zimmer("1.002");
		Buchung buchung = new Buchung(gast, zimmer, new Date(), vonDate, bisDate);
		buchung.addBuchung(con);
		
		buchung.bookZimmer(con);
		buchung.setZimmer(zimmer2);
		buchung.bookZimmer(con);
		
		commitDbConnection(con);
		closeDbConnection(con);
		
		int anzahlBuchungen = Integer.parseInt(selectDB("select count(*) from `zimmer-buchung` where BID = " + buchung.getBid()));
		
		Connection con2 = openDbConnection();
		buchung.cancelZimmer(buchung, con2);
		commitDbConnection(con2);
		closeDbConnection(con2);
		
		int anzahlBuchungen2 = Integer.parseInt(selectDB("select count(*) from `zimmer-buchung` where BID = " + buchung.getBid()));
		
		
		
		String zimmerId = selectDB("select ZID from `zimmer-buchung` where BID = "+ buchung.getBid());
		
		int preisZimmer1 = Integer.parseInt(selectDB("select Preis from zimmer where ZID = '"+zimmer.getZid()+"'")); 
		long days = buchung.getBookedDays(vonDate, bisDate);
		int gesamtpreis = (int) (preisZimmer1 * days);
		int gesamtpreisDb = Integer.parseInt(selectDB("select Gesamtpreis from buchung where BID = " +buchung.getBid())) ;
	
		assertTrue("ZimmerNr der übrigen Zimmerbuchung: "+zimmerId  + " = 1.001", zimmerId.equals("1.001"));
		assertTrue("Anzahl Buchungen kleiner geworden: ",  anzahlBuchungen2 + 1 == anzahlBuchungen);
		assertTrue("Gesamtpreis der Buchung: "+gesamtpreis  + " = " + gesamtpreisDb, gesamtpreis == gesamtpreisDb);
	}

	@Test
	@Ignore
	public void testCancelDl() {
		Calendar von = Calendar.getInstance();
		von.set(2014, Calendar.AUGUST, 13, 0, 0, 0);
		Date vonDate = von.getTime();
		
		Calendar bis = Calendar.getInstance();
		bis.set(2014, Calendar.AUGUST, 20, 0, 0, 0);
		Date bisDate = bis.getTime();
		
		Calendar geb = Calendar.getInstance();
		geb.set(1991, Calendar.JUNE, 13, 0, 0, 0);
		Date gebDate = geb.getTime();
		
		Connection con = openDbConnection();
		Gast gast = new Gast("Test", "GetPreis", "Ichostraße", "4", "81541", "München", "Deutschland", "+49 89 23456", gebDate);
		gast.addGast(con);
		
		Zimmer zimmer = new Zimmer("1.001");
		Zimmer zimmer2 = new Zimmer("1.002");
		Dienstleistung dl = new Dienstleistung(2, new Date());
		Dienstleistung dl2 = new Dienstleistung(3, new Date());
		Buchung buchung = new Buchung(gast, zimmer, new Date(), vonDate, bisDate);
		buchung.addBuchung(con);
		
		buchung.bookZimmer(con);
		buchung.setZimmer(zimmer2);
		buchung.bookZimmer(con);
		
		buchung.bookDl(buchung, dl, con);
		buchung.bookDl(buchung, dl2, con);
		commitDbConnection(con);
		closeDbConnection(con);
	}

	@Test
	@Ignore
	public void testGetBid() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testGetDlbid() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testSetDlbid() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testSetVon() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testSetBis() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testGetVon() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testGetBis() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testGetZimmer() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testSetZimmer() {
		fail("Not yet implemented");
	}

		@Test
	@Ignore
	public void testGetZbid() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testSetZbid() {
		fail("Not yet implemented");
	}

	public static Buchung getBuchung() {
		return buchung;
	}

	public static void setBuchung(Buchung buchung) {
		BuchungTest.buchung = buchung;
	}
	
}
