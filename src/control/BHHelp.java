package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class BHHelp {

	//wandelt date in SQL-Format um
	public String getSQLDate(Date date) {
		SimpleDateFormat Sql =new SimpleDateFormat("yyyy-MM-dd");
		return Sql.format(date);
	}
	//überprüft ob Postleitzahl
	public void checkNumber(String number) throws GUIException{

		try {
			Integer.parseInt(number);		
		}
		catch (NumberFormatException nex) {
			throw new GUIException("Postleitzahl überprüfen");
		}
	}

	//Überprüft Geburtsdatum
	public void checkBirthday(Date geb) throws GUIException{
		Calendar cPast = new GregorianCalendar();
		Calendar cGeb = new GregorianCalendar();
		Calendar cNow = new GregorianCalendar();
		Date now = new Date();

		cPast.setTime(now);
		cPast.add(Calendar.YEAR, -120);
		cGeb.setTime(geb);

		if (cGeb.before(cPast) ) {
			throw new GUIException("Geburtsdatum überprüfen!");
		}	
		if (cGeb.after(cNow)) {
			throw new GUIException("Geburtsdatum überprüfen!");
		}
	}

	//wandelt SQL-Date in deutsches Datumsformat
	public String getDateSqlToGer(String date) {

		String[] splitResult = date.split("-", 3);
		String tag = splitResult[2];
		String monat = splitResult[1];
		String jahr = splitResult[0];
		return tag + "." + monat + "." + jahr;
	}
	//String leer?
	public void checkStringEmpty(String check) throws GUIException {

		if (check.equals(""))
			throw new GUIException("Bitte alle Felder ausführen!");
	}
	//Dl Buchungsdatum überprüfen
	public void checkBookingDateDl(Date bookDate) throws GUIException{
		Calendar cNow = new GregorianCalendar();
		Calendar cBookDate = new GregorianCalendar();
		cNow.setTime(new Date());
		cBookDate.setTime(bookDate);
		if (cBookDate.before(cNow)) {
			throw new GUIException("Buchungsdatum überprüfen!");
		}
	}
	//Zimmerbuchungsdatum überprüfen
	public void checkBookingDate(Date vonDate, Date bisDate) throws GUIException{

		Calendar cNow = new GregorianCalendar();
		Calendar cVonDate = new GregorianCalendar();
		Calendar cBisDate = new GregorianCalendar();
		cNow.setTime(new Date());
		cVonDate.setTime(vonDate);
		cBisDate.setTime(bisDate);

		if (cVonDate.before(cNow) || cBisDate.before(cNow)) 
			throw new GUIException("Buchungsdatum überprüfen!");

		if(cVonDate.after(cBisDate))
			throw new GUIException("Buchungsdatum überprüfen!");		
	}
	//Telefonnummer überprüfen
	public void checkTel(String a, String b, String c) throws GUIException{
		try {
			Integer.parseInt(b);
			Integer.parseInt(c);

			if (!a.matches("\\+\\d{2,5}")) {
				throw new GUIException("falsche Ländervorwahl");
			}

		}
		catch (NumberFormatException ex) {
			throw new GUIException("Telefonnummer überprüfen!");
		}
	}
	//Login prüfen
	public void checkLogin(String x, String y) throws GUIException
	{

		String eingabe = x;
		String pw = y;

		if(eingabe.equals(pw))
		{

		}
		else
		{
			throw new GUIException("Falsches Passwort");
		}	
	}
	
	/**
	 * öffnet DB Connection für folgenden Schreibbefehl
	 * @return
	 */
	public Connection openDbConnection(){
		try {
			String sDbDriver=null, sDbUrl=null, sUsr="", sPwd=""; 
			sDbDriver = "com.mysql.jdbc.Driver"; 
			sDbUrl = "jdbc:mysql://localhost:3306/hotel-seII"; 
			sUsr = "root"; 
			sPwd = "init"; 
			Class.forName( sDbDriver ); 
			Connection cn = DriverManager.getConnection( sDbUrl, sUsr, sPwd ); 
			cn.setAutoCommit(false);
			return cn;
		}
		catch (SQLException ex) 
		{ 
			JOptionPane.showMessageDialog(new JFrame(),ex.getMessage()); 
			return null;
		} 
		catch( ClassNotFoundException ex ) 
		{ 
			JOptionPane.showMessageDialog(new JFrame(),ex.getMessage()); 
			return null;
		} 
		
	}
	
	public void commitDbConnection(Connection cn){
		try {
			cn.commit();
			cn.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(new JFrame(),ex.getMessage()); 
		} 
	}
	
	public void closeDbConnection(Connection cn){
		try {
			if(cn != null){
				cn.close();
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(new JFrame(),ex.getMessage()); 
		} 
	}
	
	public void rollbackDbConnection(Connection cn){
		try {
			if (cn != null){
				cn.rollback();
				cn.close();
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(new JFrame(),ex.getMessage()); 
		} 
	}
	
	/**schreibt auf DB und gibt Autoinkrement zurück
	 * 
	 * @param SQLquery
	 * @param cn
	 * @return
	 */
	public int writeDbAi(String SQLquery, Connection cn) {

		if (cn != null){
			try { 
				PreparedStatement stmt = cn.prepareStatement(SQLquery, 
						Statement.RETURN_GENERATED_KEYS);
				stmt.execute();
				
				ResultSet res = stmt.getGeneratedKeys();
				res.next();
				
				return res.getInt(1);
			}
			catch (SQLException ex) 
			{ 
				JOptionPane.showMessageDialog(new JFrame(),ex.getMessage()); 
				return -1;
			}
		}
		else {
			JOptionPane.showMessageDialog(new JFrame(),"Connection leer"); 
			return -1;
		}
	}
	/**schreibt auf DB
	 * 
	 * @param SQLquery
	 * @param cn
	 */
	public void writeDb(String SQLquery, Connection cn) 
	{ 
		if (cn != null) {
			try 
			{  	
				Statement st = cn.createStatement(); 
				st.execute(SQLquery); 
				st.close();
				
			} 
			catch (SQLException ex) 
			{ 
				JOptionPane.showMessageDialog(new JFrame(),ex.getMessage()); 
			} 
		}
		else  {
			JOptionPane.showMessageDialog(new JFrame(),"Connection leer"); 
		}

	}

	//ID überprüfen
	public void checkID(String number) throws GUIException{
		try {
			Integer.parseInt(number);		
		}
		catch (NumberFormatException nex) {
			throw new GUIException("Eingabe überprüfen");
		}

	}
	//Preisformat überprüfen
	public void checkPrize(String number) throws GUIException{
		try {
			Double.parseDouble(number);		
		}
		catch (NumberFormatException nex) {
			throw new GUIException("Eingabe überprüfen");
		}

	}
	
	/**Select Befehl für DB
	 * 
	 * @param SQLquery
	 * @return
	 */
	public String selectDB(String SQLquery) 
	{ 
		try 
		{ 
			String sDbDriver=null, sDbUrl=null, sUsr="", sPwd=""; 
			sDbDriver = "com.mysql.jdbc.Driver"; 
			sDbUrl = "jdbc:mysql://localhost:3306/hotel-seII"; 
			sUsr = "root"; 
			sPwd = "init"; 

			Class.forName( sDbDriver ); 
			Connection cn = DriverManager.getConnection( sDbUrl, sUsr, sPwd ); 
			
			String result = selectDbWithCon(SQLquery, cn);
			
			cn.close();
			return result;
		} 
		catch (SQLException ex) 
		{ 
			JOptionPane.showMessageDialog(new JFrame(),ex.getMessage()); 
			return "";
		} 
		catch( ClassNotFoundException ex ) 
		{ 
			JOptionPane.showMessageDialog(new JFrame(),ex.getMessage());
			return "";
		} 
	}
	
	/**
	 * Select-Befehl mit übergebener Connection
	 */
	public String selectDbWithCon(String SQLquery, Connection cn){
		
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(SQLquery);
			rs.next();
			String result = rs.getString(1);

			st.close(); 
			
			return result;
		} catch (SQLException ex) 
		{ 
			JOptionPane.showMessageDialog(new JFrame(),ex.getMessage()); 
			return "";
		} 
	}
	
	//Updaten der SQL-Tabellen
	public void updateTable(JPanel contentpane, JScrollPane scrollPane, JTableview jtv, String query, int x, int y, int width, int height, Connection con){

		scrollPane.setVisible(false);
		contentpane.remove(scrollPane);
		scrollPane = null;
		jtv = null;
		if (con == null){
			jtv = new JTableview(query);
		}
		else {
			jtv = new JTableview(query, con);
		}
		
		JTable table = jtv.getSQLTable();
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(x, y, width, height);
		contentpane.add(scrollPane);

	}
	
	public boolean checkDlRange(Date von, Date bis, Date dldate){
		Calendar vonC = new GregorianCalendar();
		vonC.setTime(von);
		
		Calendar bisC = new GregorianCalendar();
		bisC.setTime(bis);
		
		Calendar dlDateC = new GregorianCalendar();
		dlDateC.setTime(dldate);
		
		if((dlDateC.before(bisC) && dlDateC.after(vonC)) || von.equals(dlDateC) || bis.equals(dlDateC))
			return true;
		else
			return false;
	}


}
