package control;

import gui.BookZimmer;
import gui.StartFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import model.Buchung;
import model.Dienstleistung;
import model.Gast;
import model.Zimmer;

public class BHBookZimmer extends BHHelp implements ActionListener{

	BookZimmer guiZimmer;

	private Gast gast = null;
	private static Buchung buchung = null;
	private Connection con = null;
	private StartFrame startFrame = null;

	public BHBookZimmer(BookZimmer bookZimmer, StartFrame startFrame) {
		this.guiZimmer = bookZimmer;
		gast = null;
		setBuchung(null);
		con = null;
		this.startFrame = startFrame;
	}

	public void actionPerformed(ActionEvent e) throws NullPointerException {

		System.out.println("Das Ereignis hat den Wert: " + e.getActionCommand());
		if (e.getActionCommand().equals("NewBooking")) {
			//JFrame wird gelauncht, Card1 im CardLayout wird aufgerufen
			guiZimmer.launchJFrame();
			guiZimmer.cardLayout.show(this.guiZimmer.card, "Card1");
		}
		
		else if (e.getActionCommand().equals("NEXT")) {
			try {
				//Überprüfung der Eingaben
				checkStringEmpty(guiZimmer.getVorname());
				checkStringEmpty(guiZimmer.getName());
				checkStringEmpty(guiZimmer.getStrasse());
				checkStringEmpty(guiZimmer.getHn());
				checkStringEmpty(guiZimmer.getPlz());
				checkStringEmpty(guiZimmer.getOrt());
				checkStringEmpty(guiZimmer.getLand());
				checkStringEmpty(guiZimmer.getTel2_1());
				checkStringEmpty(guiZimmer.getTel2_2());
				checkStringEmpty(guiZimmer.getTel2_3());
				checkNumber(guiZimmer.getPlz());
				checkBirthday(guiZimmer.getGeb());
				checkTel(guiZimmer.getTel2_1(), guiZimmer.getTel2_2(), guiZimmer.getTel2_3());			

				//aus Eingaben wird neues Objekt erzeugt
				Gast gast = new Gast(guiZimmer.getVorname(), guiZimmer.getName(), guiZimmer.getStrasse(), guiZimmer.getHn(),
						guiZimmer.getPlz(), guiZimmer.getOrt(), guiZimmer.getLand(), "0", guiZimmer.getGeb());
				//existing als falsch gesetzt, da neuer Gast
				gast.setExisting(false);
				
				String Vorwahl = guiZimmer.getTel2_2();
				//Splitten der Vorwahl um 0 ggf. zu ersetzen
				if (Vorwahl.charAt(0) == '0'){
					String[] splitResult = Vorwahl.split("0", 2);
					Vorwahl = splitResult[1];
				}

				//nächste Contentpane wird gelauncht und Eingaben Labels mit Eingaben versehen
				guiZimmer.contentpane3 = guiZimmer.launchSecond();	
				guiZimmer.labelVor3_2.setText(gast.getVorname());
				guiZimmer.labelName3_2.setText(gast.getName());
				guiZimmer.labelStr3_2.setText(gast.getStrasse() + " " + gast.getHn());
				guiZimmer.labelPlz3_2.setText(gast.getPlz());
				guiZimmer.labelOrt3_2.setText(gast.getOrt());
				guiZimmer.labelLand3_2.setText(gast.getLand());
				guiZimmer.setTel(guiZimmer.getTel2_1() + " (0) "+ Vorwahl + " " + guiZimmer.getTel2_3());

				//Telefonnummer wird nachträglich gesetzt, da erst noch zusammengesetzt werden musst
				gast.setTel(guiZimmer.getTel());

				guiZimmer.labelTel3_2.setText(gast.getTel());

				//SDM um Date in String umzuwandeln
				SimpleDateFormat gebForm =new SimpleDateFormat("dd.MM.yyyy");
				String geb = gebForm.format(guiZimmer.getGeb());

				//Label für Geb wird nun auch gesetzt
				guiZimmer.labelGeb3_2.setText(geb);

				//speichern der Gastdaten in static Variable und schreiben in Datenbank
				this.gast = gast;
				
				Connection con = openDbConnection();
				this.con = con;
				
				gast.addGast(con);

				//neue contentpane wird zum CardLayout hinzugefügt, CardLayout wird auf nächste Card gesetzt
				guiZimmer.card.add("Card2", guiZimmer.contentpane3);
				guiZimmer.cardLayout.show(this.guiZimmer.card, "Card2");
			}

			catch (GUIException gex){
				JOptionPane.showMessageDialog(null, gex, "Error",
						JOptionPane.ERROR_MESSAGE);
			}
/*
			catch (NullPointerException ex) {
				JOptionPane.showMessageDialog(guiZimmer.jf, "Bitte alle Felder ausfüllen", "Error",
						JOptionPane.ERROR_MESSAGE);
			}*/
		}
		
		else if (e.getActionCommand().equals("NEXT2")) {
			guiZimmer.launchThird();
			guiZimmer.card.add("Card3", guiZimmer.contentpane4);
			guiZimmer.cardLayout.show(this.guiZimmer.card, "Card3");
			String preis =selectDbWithCon("select Gesamtpreis from buchung where bid = "+getBuchung().getBid(), con);
			System.out.println(preis);
		}

		else if (e.getActionCommand().equals("Available?")) {
			try{
			if (guiZimmer.scrollPaneZimmer != null){
				guiZimmer.scrollPaneZimmer.setVisible(false);
			}
			guiZimmer.availableZimmer = null;
			guiZimmer.availableZimmer = getAvailableRoomTable();
			guiZimmer.scrollPaneZimmer = null;
			guiZimmer.scrollPaneZimmer = new JScrollPane(guiZimmer.availableZimmer.getSQLTable());
			guiZimmer.scrollPaneZimmer.setBounds(200, 280, 300, 100);
			guiZimmer.contentpane3.add(guiZimmer.scrollPaneZimmer);
			
			guiZimmer.enableButton(guiZimmer.getBookZimmer(), true);
			}
			catch (GUIException gex) {
				JOptionPane.showMessageDialog(null, gex, "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			
			catch (NullPointerException ex){
				JOptionPane.showMessageDialog(null, "Datum überprüfen", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		else if (e.getActionCommand().equals("ExistBooking")) {

			try {
				con = null;
				setBuchung(null);
				
				//Überprüfung ob Zeile markiert
				if (guiZimmer.sucheGast.getSQLTable().getSelectedRow() == -1) {
					throw new GUIException("Fehler: Zeile nicht markiert!");
				}
				//Frame für die Buchung wird gelauncht

				guiZimmer.launchJFrame();

				guiZimmer.contentpane3 = guiZimmer.launchSecond();
				guiZimmer.disableBackButton();
				guiZimmer.addCancelButton();
				
				
				//gid wird aus der Tabelle ausgelesen
				int gid = Integer.parseInt((String) guiZimmer.sucheGast.getSQLTable().getValueAt(guiZimmer.sucheGast.getSQLTable().getSelectedRow(), 0).toString());
				
				//restliche Tabelle wird ausgelesen
				String vorname = (String) guiZimmer.sucheGast.getSQLTable().getValueAt(guiZimmer.sucheGast.getSQLTable().getSelectedRow(), 1).toString();
				String name = (String) guiZimmer.sucheGast.getSQLTable().getValueAt(guiZimmer.sucheGast.getSQLTable().getSelectedRow(), 2).toString();
				String strasse = (String) guiZimmer.sucheGast.getSQLTable().getValueAt(guiZimmer.sucheGast.getSQLTable().getSelectedRow(), 3).toString();
				String hn = (String) guiZimmer.sucheGast.getSQLTable().getValueAt(guiZimmer.sucheGast.getSQLTable().getSelectedRow(), 4).toString();
				String plz = (String) guiZimmer.sucheGast.getSQLTable().getValueAt(guiZimmer.sucheGast.getSQLTable().getSelectedRow(), 5).toString();
				String ort = (String) guiZimmer.sucheGast.getSQLTable().getValueAt(guiZimmer.sucheGast.getSQLTable().getSelectedRow(), 6).toString();
				String land = (String) guiZimmer.sucheGast.getSQLTable().getValueAt(guiZimmer.sucheGast.getSQLTable().getSelectedRow(), 7).toString();
				String geb = getDateSqlToGer((String) guiZimmer.sucheGast.getSQLTable().getValueAt(guiZimmer.sucheGast.getSQLTable().getSelectedRow(), 8).toString());
				String tel = (String) guiZimmer.sucheGast.getSQLTable().getValueAt(guiZimmer.sucheGast.getSQLTable().getSelectedRow(), 9).toString();

				//Datum wird aus SQL-Format in deutsches Format geändert und in Date umgewandelt
				SimpleDateFormat toDate = new SimpleDateFormat("dd.MM.yyyy");
				Date Geb = toDate.parse(geb);
				
				//Gast wird erzeugt
				Gast exiGast = new Gast(vorname, name, strasse, hn, plz, ort, land, tel, Geb);
				//existing True, da bereits vorhanden
				exiGast.setExisting(true);
				exiGast.setGid(gid);

				//labels werden gesetzt
				guiZimmer.labelVor3_2.setText(exiGast.getVorname());
				guiZimmer.labelName3_2.setText(exiGast.getName());
				guiZimmer.labelStr3_2.setText(exiGast.getStrasse() + " "+ exiGast.getHn());
				guiZimmer.labelPlz3_2.setText(exiGast.getPlz());
				guiZimmer.labelOrt3_2.setText(exiGast.getOrt());
				guiZimmer.labelLand3_2.setText(exiGast.getLand());
				guiZimmer.labelTel3_2.setText(exiGast.getTel());
				guiZimmer.labelGeb3_2.setText(toDate.format(exiGast.getGeb()));
				
				//SimpleDateFormat gebForm =new SimpleDateFormat("dd.MM.yyyy");
				//Gast wird gespeichert
				gast = exiGast;
				//contentpane wird zu CardLayout hinzugefügt, und CardLayout umgeschaltet
				guiZimmer.card.add("Card2", guiZimmer.contentpane3);
				guiZimmer.cardLayout.show(this.guiZimmer.card, "Card2");
			}
			catch (GUIException gex) {
				JOptionPane.showMessageDialog(null, gex, "Error",
						JOptionPane.ERROR_MESSAGE);
			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(null, e1, "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		else if (e.getActionCommand().equals("BookZimmer")) {

			try {
				//Überprüfung der Buchungsdaten
				checkBookingDate(guiZimmer.getPickerVon(), guiZimmer.getPickerBis());
				
				if (guiZimmer.availableZimmer.getSQLTable().getSelectedRow() == -1) {
					System.out.println(guiZimmer.availableZimmer.getSQLTable().getSelectedRow());
					throw new GUIException("Fehler: Zeile nicht markiert!");
				}
				//zid aus Tabelle lesen
				String ZID = (String) guiZimmer.availableZimmer.getSQLTable().getValueAt(guiZimmer.availableZimmer.getSQLTable().getSelectedRow(), 0).toString();
				
				//Neues Zimmer-Objekt wird erstellt
				Zimmer zimmer = new Zimmer(ZID);
				//Neues Buchungs-Objekt wird erstellt
				if (con == null){
					Connection con = openDbConnection();
					this.con = con;
					System.out.println("con ist null!!!");
				}
				if (getBuchung() == null){
					Buchung buchung = new Buchung(gast, zimmer, new Date(), guiZimmer.getPickerVon(), guiZimmer.getPickerBis());
					buchung.addBuchung(con);
					setBuchung(buchung);
				}
				else {
					getBuchung().setZimmer(zimmer);
				}
				//Buchung wird gebucht
				System.out.println(con.toString());
				getBuchung().bookZimmer(con);
				
				
				guiZimmer.scrollPaneZimmer.setVisible(false);
				guiZimmer.contentpane3.remove(guiZimmer.scrollPaneZimmer);
				
				if(guiZimmer.scrollPaneBookedZimmer!=null){
					guiZimmer.scrollPaneBookedZimmer.setVisible(false);
					guiZimmer.contentpane3.remove(guiZimmer.scrollPaneBookedZimmer);
				}	
				guiZimmer.bookedZimmer = getBookedZimmerTable(con);
				guiZimmer.scrollPaneBookedZimmer = new JScrollPane(guiZimmer.bookedZimmer.getSQLTable());
				guiZimmer.scrollPaneBookedZimmer.setBounds(500, 280, 300, 100);
				guiZimmer.contentpane3.add(guiZimmer.scrollPaneBookedZimmer);
				guiZimmer.enableButton(guiZimmer.getBookZimmer(), false);
				guiZimmer.enableButton(guiZimmer.getNext2(), true);
				guiZimmer.pickerVon.setEnabled(false);
				guiZimmer.pickerBis.setEnabled(false);
				guiZimmer.enableButton(guiZimmer.getBackButton(), false);
				}
			catch (GUIException gex) {
				JOptionPane.showMessageDialog(null, gex, "Error",
						JOptionPane.ERROR_MESSAGE);
			}			
			catch (NullPointerException nex) {
				JOptionPane.showMessageDialog(null, "Bitte alle Felder ausfüllen!", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
		else if (e.getActionCommand().equals("BACK")){ 
			//Zurück zur vorherigen Karte
			guiZimmer.cardLayout.show(guiZimmer.card, "Card1");
			rollbackDbConnection(con);
			closeDbConnection(con);
			
		}
		
		else if (e.getActionCommand().equals("AddDl")){
			//Dl Buchung, folgend auf Zimmer-Buchung
			try{
				if (guiZimmer.showDl.getSQLTable().getSelectedRow() == -1) 
					throw new GUIException("Fehler: Zeile nicht markiert!");
				
				Date von = guiZimmer.getPickerVon();
				Date bis = guiZimmer.getPickerBis();
				Date dlDate = guiZimmer.bookDateDl.getDate();
				checkBookingDateDl(dlDate);	
				//Prüfen ob im Buchungszeitraum
				if (!checkDlRange(von, bis, dlDate))
					throw new GUIException("Dienstleistungsdatum nicht im Buchungszeitraum");
				
				//Zeile ausgewählt?
				if (guiZimmer.showDl.getSQLTable().getSelectedRow() == -1) {
					throw new GUIException("Fehler: Zeile nicht markiert!");
				} 
				//Datum wird ausgelesen
				guiZimmer.bookDateDl.getDate();
				getBuchung().getBid();
				//Dienstleistungsobjekt wird erstellt
				int did = Integer.parseInt((String) guiZimmer.showDl.getSQLTable().getValueAt(guiZimmer.showDl.getSQLTable().getSelectedRow(), 0).toString());
				Dienstleistung dl = new Dienstleistung(did, guiZimmer.bookDateDl.getDate());
				//buchung wird durchgeführt
				
				getBuchung().bookDl(getBuchung(), dl, con);
				
				if(guiZimmer.scrollPaneBookedDl!=null){
					guiZimmer.scrollPaneBookedDl.setVisible(false);
					guiZimmer.contentpane4.remove(guiZimmer.scrollPaneBookedDl);
				}	
				//guiZimmer.bookedDl = getBuchung().getBookedDlTable(con);
				guiZimmer.bookedDl = getBookedDlTable(con);
				guiZimmer.scrollPaneBookedDl = new JScrollPane(guiZimmer.bookedDl.getSQLTable());
				guiZimmer.scrollPaneBookedDl.setBounds(470, 80, 300, 100);
				guiZimmer.contentpane4.add(guiZimmer.scrollPaneBookedDl);
			}
		
			catch (GUIException gex) {
				JOptionPane.showMessageDialog(null, gex, "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			/*catch (NullPointerException nex) {
				JOptionPane.showMessageDialog(null, "Bitte alle Felder ausfüllen", "Error",
						JOptionPane.ERROR_MESSAGE);
			}*/
		}		

		else if (e.getActionCommand().equals("NEXT3")){
			//Abbrechen der DL-Buchung nach Zimmer-Buchung
			guiZimmer.launchFourth();
			guiZimmer.card.add("Card4", guiZimmer.contentpane5);
			guiZimmer.cardLayout.show(this.guiZimmer.card, "Card4");
		}
		
		else if(e.getActionCommand().equals("COMMIT")){
			int answer = JOptionPane.showConfirmDialog(guiZimmer.jf, "Möchten Sie die Buchung wirklich speichern?", null,JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.YES_OPTION) {
				commitDbConnection(con);
				closeDbConnection(con);
				guiZimmer.jf.dispose();
				reloadMainframe();
			}
		}
		
		else if (e.getActionCommand().equals("CancelAll")){
			int answer = JOptionPane.showConfirmDialog(guiZimmer.jf, "Möchten Sie die Buchung wirklich abbrechen?", null,JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.YES_OPTION) {
				rollbackDbConnection(con);
				closeDbConnection(con);
				guiZimmer.jf.dispose();
				reloadMainframe();
			}
		}

		else if (e.getActionCommand().equals("SEARCH")) {
			//Standardparameter werden gesetzt, damit bei leeren Feld auf alles geprüft wird
			String gebSuche = "%";
			String vorSuche = "%";
			String nameSuche = "%";
			String gidSuche = "%";
			guiZimmer.sucheGast = null;

			//Exception für falsches Datum wird abgefangen
			try {
				gebSuche = getSQLDate(guiZimmer.getGebSuche());
			}
			catch (NullPointerException ex) {

			}

			//Eingabefelder werden überprüft und bei Eingabe geändert 
			if (!guiZimmer.getGidSuche().equals(""))
				gidSuche = guiZimmer.getGidSuche();

			if (!guiZimmer.getVornameSuche().equals(""))
				vorSuche = guiZimmer.getVornameSuche()+"%";

			if (!guiZimmer.getNameSuche().equals(""))
				nameSuche = guiZimmer.getNameSuche() +"%";

			//Query für Suche
			String query = "Select * from gast where GID like '"+ gidSuche +"' AND Vorname like '"+vorSuche +
					"' AND Name like '" + nameSuche +"' AND Geburtstag like '" + gebSuche+"'";

			//SQL-Tabelle wird erzeugt und zu contentpane hinzugefügt
			setSearchtable(query);

		}
	}
		
	
	private JTableview getAvailableRoomTable() throws GUIException{
		Date von = guiZimmer.getPickerVon();
		Date bis = guiZimmer.getPickerBis();
		//Check Buchungsdatum	
		checkBookingDate(von, bis);
		
		Calendar vonC = new GregorianCalendar();
		vonC.setTime(von);
		vonC.add(Calendar.DATE, 1);	
		
		//Buchungsdatum wird in SQL-Datus-Format umgewandelt
		String vonSql = getSQLDate(vonC.getTime());
		String bisSql = getSQLDate(bis);

		//SQL Tabelle mit verfügbaren Zimmern wird erzeugt und zum Panel hinzugefügt
		String query="SELECT * from zimmer where zimmer.ZID not in (SELECT zb.ZID from `zimmer-buchung` zb, buchung b  where ((b.Von between '"+vonSql+"' AND '"+bisSql+"') OR (b.Bis between '"+vonSql+"' AND '"+bisSql+"') OR b.Von = '"+vonSql+"') and zb.BID = b.BID)";
		if (con == null) {
			con = openDbConnection();
		}
		
		JTableview availableTable = new JTableview(query, con);
		return availableTable;
	}
	
	public Connection getCon(){
		return con;
	}
	
	public Gast getGast(){
		return gast;
	}
	
	public void reloadMainframe(){

		if (startFrame.getS() == "Manager"){
			startFrame.launchStartFrame(new BookZimmer(startFrame).launchStartPanel(),startFrame.getJPanel3());
		}
		else {
			startFrame.launchStartFrame(new BookZimmer(startFrame).launchStartPanel(),startFrame.getJPanel4());
		}
	}
	
	private void setSearchtable(String query){
		guiZimmer.sucheGast = new JTableview(query);
		
		JTable suche = guiZimmer.sucheGast.getSQLTable();

		guiZimmer.scrollPaneSuche.setVisible(false);
		guiZimmer.scrollPaneSuche = new JScrollPane(suche);
		guiZimmer.scrollPaneSuche.setBounds(10, 360, 800, 200);
		guiZimmer.contentpane1.add(guiZimmer.scrollPaneSuche);
	}

	public static Buchung getBuchung() {
		return buchung;
	}

	public static void setBuchung(Buchung buchung) {
		BHBookZimmer.buchung = buchung;
	}
	
	public JTableview getBookedDlTable (Connection con){
		String query = "SELECT dl.Bezeichnung, dlb.Datum from `dl-buchung` dlb, dienstleistung dl where dlb.BID =" + buchung.getBid() +" and dl.did = dlb.did";
		JTableview bookedDlTable;
		if (con == null){
			bookedDlTable = new JTableview(query);
		}
		else {
			bookedDlTable = new JTableview(query, con);
		}
		return bookedDlTable;
	}
	
	public JTableview getBookedZimmerTable (Connection con){
		String query = "select zb.ZID, b.Von, b.Bis from `zimmer-buchung` zb, buchung b where zb.bid = b.BID and zb.bid =" + buchung.getBid();
		JTableview bookedZimmerTable;
		if (con == null){
			bookedZimmerTable = new JTableview(query);
			
		}
		else {
			bookedZimmerTable = new JTableview(query, con);
		}
		return bookedZimmerTable;
	}
}
