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
	//�berpr�ft ob Postleitzahl
	public void checkNumber(String number) throws GUIException{

		try {
			Integer.parseInt(number);		
		}
		catch (NumberFormatException nex) {
			throw new GUIException("Postleitzahl �berpr�fen");
		}
	}

	//�berpr�ft Geburtsdatum
	public void checkBirthday(Date geb) throws GUIException{
		Calendar cPast = new GregorianCalendar();
		Calendar cGeb = new GregorianCalendar();
		Calendar cNow = new GregorianCalendar();
		Date now = new Date();

		cPast.setTime(now);
		cPast.add(Calendar.YEAR, -120);
		cGeb.setTime(geb);

		if (cGeb.before(cPast) ) {
			throw new GUIException("Geburtsdatum �berpr�fen!");
		}	
		if (cGeb.after(cNow)) {
			throw new GUIException("Geburtsdatum �berpr�fen!");
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
			throw new GUIException("Bitte alle Felder ausf�hren!");
	}
	//Dl Buchungsdatum �berpr�fen
	public void checkBookingDateDl(Date bookDate) throws GUIException{
		Calendar cNow = new GregorianCalendar();
		Calendar cBookDate = new GregorianCalendar();
		cNow.setTime(new Date());
		cBookDate.setTime(bookDate);
		if (cBookDate.before(cNow)) {
			throw new GUIException("Buchungsdatum �berpr�fen!");
		}
	}
	//Zimmerbuchungsdatum �berpr�fen
	public void checkBookingDate(Date vonDate, Date bisDate) throws GUIException{

		Calendar cNow = new GregorianCalendar();
		Calendar cVonDate = new GregorianCalendar();
		Calendar cBisDate = new GregorianCalendar();
		cNow.setTime(new Date());
		cVonDate.setTime(vonDate);
		cBisDate.setTime(bisDate);

		if (cVonDate.before(cNow) || cBisDate.before(cNow)) 
			throw new GUIException("Buchungsdatum �berpr�fen!");

		if(cVonDate.after(cBisDate))
			throw new GUIException("Buchungsdatum �berpr�fen!");		
	}
	//Telefonnummer �berpr�fen
	public void checkTel(String a, String b, String c) throws GUIException{
		try {
			Integer.parseInt(b);
			Integer.parseInt(c);

			if (!a.matches("\\+\\d{2,5}")) {
				throw new GUIException("falsche L�ndervorwahl");
			}

		}
		catch (NumberFormatException ex) {
			throw new GUIException("Telefonnummer �berpr�fen!");
		}
	}
	//Login pr�fen
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
	//Methode um auf DB zu schreiben - gibt AutoIncrement-Feld zur�ck
	public int writeDbAi(String SQLquery) {

		try {
			String sDbDriver=null, sDbUrl=null, sUsr="", sPwd=""; 
			sDbDriver = "com.mysql.jdbc.Driver"; 
			sDbUrl = "jdbc:mysql://localhost:3306/Hotel";
			sUsr = "root";  
			sPwd = "init"; 
			Class.forName( sDbDriver ); 
			Connection cn = DriverManager.getConnection( sDbUrl, sUsr, sPwd ); 

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
		catch( ClassNotFoundException ex ) 
		{ 
			JOptionPane.showMessageDialog(new JFrame(),ex.getMessage()); 
			return -1;
		} 

	}

	//Schreibbefehl auf die DB
	public void writeDb(String SQLquery) 
	{ 
		try 
		{ 
			String sDbDriver=null, sDbUrl=null, sUsr="", sPwd=""; 
			sDbDriver = "com.mysql.jdbc.Driver"; 
			sDbUrl = "jdbc:mysql://localhost:3306/Hotel"; 
			sUsr = "root"; 
			sPwd = "init"; 
			Class.forName( sDbDriver ); 
			Connection cn = DriverManager.getConnection( sDbUrl, sUsr, sPwd ); 
			Statement st = cn.createStatement(); 
			st.execute(SQLquery); 
			st.close(); 
			cn.close(); 
		} 
		catch (SQLException ex) 
		{ 
			JOptionPane.showMessageDialog(new JFrame(),ex.getMessage()); 
		} 
		catch( ClassNotFoundException ex ) 
		{ 
			JOptionPane.showMessageDialog(new JFrame(),ex.getMessage()); 
		} 

	}

	//ID �berpr�fen
	public void checkID(String number) throws GUIException{
		try {
			Integer.parseInt(number);		
		}
		catch (NumberFormatException nex) {
			throw new GUIException("Eingabe �berpr�fen");
		}

	}
	//Preisformat �berpr�fen
	public void checkPrize(String number) throws GUIException{
		try {
			Double.parseDouble(number);		
		}
		catch (NumberFormatException nex) {
			throw new GUIException("Eingabe �berpr�fen");
		}

	}
	//Select-Methode f�r DB
	public String selectDB(String SQLquery) 
	{ 
		try 
		{ 
			String sDbDriver=null, sDbUrl=null, sUsr="", sPwd=""; 
			sDbDriver = "com.mysql.jdbc.Driver"; 
			sDbUrl = "jdbc:mysql://localhost:3306/Hotel"; 
			sUsr = "root"; 
			sPwd = "init"; 

			Class.forName( sDbDriver ); 
			Connection cn = DriverManager.getConnection( sDbUrl, sUsr, sPwd ); 
			Statement st = cn.createStatement(); 
			ResultSet rs = st.executeQuery(SQLquery);
			rs.next();
			String result = rs.getString(1);

			st.close(); 
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
	//Updaten der SQL-Tabellen
	public void updateTable(JPanel contentpane, JScrollPane scrollPane, JTableview jtv, String query, int x, int y, int width, int height){

		scrollPane.setVisible(false);
		scrollPane = null;
		jtv = new JTableview(query);
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
