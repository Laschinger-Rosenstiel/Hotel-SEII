package control;
import gui.BookDl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import model.Buchung;
import model.Dienstleistung;
import model.Gast;

public class BHBookDl extends BHHelp implements ActionListener{

	BookDl guiDl;

	private Gast gast = null;
	private static Buchung buchung = null;
	private Connection con = null;

	public BHBookDl(BookDl bookDl) {
		this.guiDl = bookDl;
	}

	public void actionPerformed(ActionEvent e) throws NullPointerException {

		System.out.println("Das Ereignis hat den Wert: " + e.getActionCommand());
		
		if (e.getActionCommand().equals("NewBookingDl")) {
			try {//Dienstleistung buchen
				//Zeile markiert?
				if (guiDl.sucheGast.getSQLTable().getSelectedRow() == -1) {

					throw new GUIException("Fehler: Zeile nicht markiert!");
				}
				
				//ausgewählte Zeile auslesen
				String gid = (String) guiDl.sucheGast.getSQLTable().getValueAt(guiDl.sucheGast.getSQLTable().getSelectedRow(), 0).toString();
				String vorname = (String) guiDl.sucheGast.getSQLTable().getValueAt(guiDl.sucheGast.getSQLTable().getSelectedRow(), 1).toString();
				String name = (String) guiDl.sucheGast.getSQLTable().getValueAt(guiDl.sucheGast.getSQLTable().getSelectedRow(), 2).toString();
				String geb = getDateSqlToGer((String) guiDl.sucheGast.getSQLTable().getValueAt(guiDl.sucheGast.getSQLTable().getSelectedRow(), 3).toString());
				int bid = Integer.parseInt((String) guiDl.sucheGast.getSQLTable().getValueAt(guiDl.sucheGast.getSQLTable().getSelectedRow(), 4).toString());
				String zid = (String) guiDl.sucheGast.getSQLTable().getValueAt(guiDl.sucheGast.getSQLTable().getSelectedRow(), 5).toString();
				String von = getDateSqlToGer((String) guiDl.sucheGast.getSQLTable().getValueAt(guiDl.sucheGast.getSQLTable().getSelectedRow(), 6).toString());
				String bis = getDateSqlToGer((String) guiDl.sucheGast.getSQLTable().getValueAt(guiDl.sucheGast.getSQLTable().getSelectedRow(), 7).toString());
				
				//SQL-Datum in deutsches Datum
				SimpleDateFormat toDate = new SimpleDateFormat("dd.MM.yyyy");
				Date Geb = toDate.parse(geb);
				Date vonD = toDate.parse(von);
				Date bisD = toDate.parse(bis); 
				
				//neues Gast-Objekt wird erzeugt
				Gast gast = new Gast(Integer.parseInt(gid), vorname, name, Geb);
				//neue Buchung wird erzeugt
				Buchung buchung = new Buchung(bid);
				buchung.setVon(vonD);
				buchung.setBis(bisD);
				//buchung und gast wird zwischengespeichert
				setBuchung(buchung);
				this.gast = gast;
				//Frame für DL-Buchung wird geöffnet
				guiDl.launchJFrame();
				
				guiDl.bookDate2.setSelectableDateRange(vonD, bisD);
				//Labels werden nach Gastdaten gesetzt
				guiDl.labelId2_2.setText(gid);
				guiDl.labelVor2_2.setText(vorname);
				guiDl.labelName2_2.setText(name);
				guiDl.labelZimmer2_2.setText(zid);
				guiDl.labelVon2_2.setText(von);
				guiDl.labelBis2_2.setText(bis);
				
				//update SQL-Table in Startpanel
				updateTable(guiDl.contentpane1, guiDl.scrollPaneSuche, guiDl.sucheGast, guiDl.getQuery(), guiDl.scrollPaneSuche.getX(), guiDl.scrollPaneSuche.getY(), guiDl.scrollPaneSuche.getWidth(), guiDl.scrollPaneSuche.getHeight(), null);
				
			}
			catch (GUIException gex ){
				JOptionPane.showMessageDialog(null, gex, "Error",
						JOptionPane.ERROR_MESSAGE);
			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(null, e1, "Error",
						JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		}

		else if (e.getActionCommand().equals("BOOK?Dl")) {
			try {
				//durchführen der Zimmerbuchung
				//Zeile markiert?
				if (guiDl.tableDl.getSQLTable().getSelectedRow() == -1) {
					throw new GUIException("Fehler: Zeile nicht markiert!");
				}
				//Buchungsdatum überprüfen
				checkBookingDateDl(guiDl.bookDate2.getDate());
				String a = getSQLDate(guiDl.bookDate2.getDate());
				String von = getSQLDate(getBuchung().getVon());
				String bis = getSQLDate(getBuchung().getBis());
				
				System.out.println(a + " " + von + " " + bis + " ");
				//wirklich buchen?
				int answer = JOptionPane.showConfirmDialog(guiDl.jf, "Dienstleistung wirklich buchen?", "Error",JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
					
					//Datum in Range?
					if(!checkDlRange(getBuchung().getVon(), getBuchung().getBis(), guiDl.bookDate2.getDate()))
						throw new GUIException("Buchungsdatum überprüfen");
						//Dienstleistungsnummer wird ausgelesen
					int did = Integer.parseInt((String) guiDl.tableDl.getSQLTable().getValueAt(guiDl.tableDl.getSQLTable().getSelectedRow(), 0).toString());
					//DL-Objekt wird erzeugt
					Dienstleistung dl = new Dienstleistung(did, guiDl.bookDate2.getDate());
					//buchung wird durchgeführt
					Connection con = openDbConnection();
					getBuchung().bookDl(getBuchung(), dl, con);
					commitDbConnection(con);
					//Fenster wird geschlossen
					guiDl.jf.dispose();
				}
			}
			catch (GUIException gex){
				JOptionPane.showMessageDialog(null, gex, "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			catch (NullPointerException ex) {
				JOptionPane.showMessageDialog(guiDl.jf, "Bitte alle Felder ausfüllen", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
		else if (e.getActionCommand().equals("SEARCHDl")) {

			//Standardparameter werden gesetzt, damit bei leeren Feld auf alles geprüft wird
			String gebSuche = "%";
			String vorSuche = "%";
			String nameSuche = "%";
			String gidSuche = "%";
			guiDl.sucheGast = null;

			//Exception für falsches Datum wird abgefangen
			try {
				gebSuche = getSQLDate(guiDl.getGebSuche());
			}
			catch (NullPointerException ex) {

			}

			//Eingabefelder werden überprüft und bei Eingabe geändert 
			if (!guiDl.getGidSuche().equals(""))
				gidSuche = guiDl.getGidSuche();

			if (!guiDl.getVorSuche().equals(""))
				vorSuche = guiDl.getVorSuche()+"%";			

			if (!guiDl.getNameSuche().equals(""))
				nameSuche = guiDl.getNameSuche() +"%";

			//Query für Suche
			String query = guiDl.getQuery() + " AND gast.GID like '"+gidSuche+"' AND gast.Vorname like '"+vorSuche+ "' AND gast.Name like'"+ nameSuche+"' AND gast.Geburtstag like '"+gebSuche+"'";

			//SQL-Tabelle wird erzeugt und zu contentpane hinzugefügt
			Connection sucheGast = openDbConnection();
			guiDl.sucheGast = new JTableview(query, sucheGast);
			JTable suche = guiDl.sucheGast.getSQLTable();
			closeDbConnection(sucheGast);

			guiDl.scrollPaneSuche.setVisible(false);
			guiDl.scrollPaneSuche = null;
			guiDl.scrollPaneSuche = new JScrollPane(suche);
			guiDl.scrollPaneSuche.setBounds(10, 320, 1000, 200);
			guiDl.contentpane1.add(guiDl.scrollPaneSuche);
		}
	}

	public Connection getCon(){
		
		return con;
	}
	
	public Gast getGast(){
		return gast;
	}

	public static Buchung getBuchung() {
		return buchung;
	}

	public static void setBuchung(Buchung buchung) {
		BHBookDl.buchung = buchung;
	}
}
