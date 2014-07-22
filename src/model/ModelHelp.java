package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ModelHelp {
	
	/**rechnet date in SQL-Datumsformat
	 * 
	 * @param date
	 * @return
	 */
	public String getSQLDate(Date date) {
		SimpleDateFormat Sql =new SimpleDateFormat("yyyy-MM-dd");
		return Sql.format(date);
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
	
	public void rollbackDbConnection(Connection cn){
		try {
			cn.rollback();
			cn.close();
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
				System.out.println(SQLquery);
				
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
}