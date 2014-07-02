package control;
import gui.BookDl;
import gui.BookZimmer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class CopyOfBHBook extends BHHelp implements ActionListener{

	BookDl guiDl;
	BookZimmer guiZimmer;

	static Gast gast;
	static Buchung buchung;
	static Date von1, bis1;
	//String tel;	

	public CopyOfBHBook(BookZimmer bookZimmer) {
		this.guiZimmer = bookZimmer;
	}

	public CopyOfBHBook(BookDl bookDl) {
		this.guiDl = bookDl;
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

				//speichern der Gastdaten in static Variable
				this.gast = gast;

				//neue contentpane wird zum CardLayout hinzugefügt, CardLayout wird auf nächste Card gesetzt
				guiZimmer.card.add("Card2", guiZimmer.contentpane3);
				guiZimmer.cardLayout.show(this.guiZimmer.card, "Card2");
			}

			catch (GUIException gex){

				JOptionPane.showMessageDialog(null, gex, "Error",
						JOptionPane.ERROR_MESSAGE);
			}

			catch (NullPointerException ex) {
				JOptionPane.showMessageDialog(guiZimmer.jf, "Bitte alle Felder ausfüllen", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}


		else if (e.getActionCommand().equals("Available?")) {
			//Verfügbarkeitsprüfung wird durchgeführt
			try {
			Date von = guiZimmer.getPickerVon();
			Date bis = guiZimmer.getPickerBis();
			//Check Buchungsdatum	
			checkBookingDate(von, bis);
			
			Calendar vonC = new GregorianCalendar();
			vonC.setTime(von);
			vonC.add(vonC.DATE, 1);	
			
			//Buchungsdatum wird in SQL-Datus-Format umgewandelt
			String vonSql = getSQLDate(vonC.getTime());
			String bisSql = getSQLDate(bis);

			//SQL Tabelle mit verfügbaren Zimmern wird erzeugt und zum Panel hinzugefügt
			guiZimmer.availableZimmer = new JTableview("SELECT * from zimmer where zimmer.ZID not in (SELECT `zimmer-buchung`.ZID from `zimmer-buchung` where (Von between '"+vonSql+"' AND '"+bisSql+"') OR (Bis between '"+vonSql+"' AND '"+bisSql+"'))");
			JTable available = guiZimmer.availableZimmer.getSQLTable();

			guiZimmer.scrollPaneZimmer = new JScrollPane(available);
			guiZimmer.scrollPaneZimmer.setBounds(200, 280, 300, 100);
			guiZimmer.contentpane3.add(guiZimmer.scrollPaneZimmer);
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
				//Überprüfung ob Zeile markiert
				if (guiZimmer.sucheGast.getSQLTable().getSelectedRow() == -1) {
					throw new GUIException("Fehler: Zeile nicht markiert!");
				}
				//Frame für die Buchung wird gelauncht
				guiZimmer.launchJFrame();
				guiZimmer.contentpane3 = guiZimmer.launchSecond();
				
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

		else if (e.getActionCommand().equals("BOOK?")) {

			try {
				//Überprüfung der Buchungsdaten
				checkBookingDate(guiZimmer.getPickerVon(), guiZimmer.getPickerBis());
				//Wirklich buchen?
				int answer = JOptionPane.showConfirmDialog(guiZimmer.jf, "Zimmer wirklich buchen?", "Error",JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
					//Zeile ausgewählt?
					if (guiZimmer.availableZimmer.getSQLTable().getSelectedRow() == -1) {
						throw new GUIException("Fehler: Zeile nicht markiert!");
					}
					//zid aus Tabelle lesen
					String ZID = (String) guiZimmer.availableZimmer.getSQLTable().getValueAt(guiZimmer.availableZimmer.getSQLTable().getSelectedRow(), 0).toString();
					//Neues Zimmer-Objekt wird erstellt
					Zimmer zimmer = new Zimmer(ZID);
					//Neues Buchungs-Objekt wird erstellt
					Buchung buchung = new Buchung(gast, zimmer, new Date());
					buchung.bookZimmer(guiZimmer.getPickerVon(), guiZimmer.getPickerBis());
					//Buchung wird gespeichert
					this.buchung = buchung;
					guiZimmer.launchThird();
					//Tabelle im Startpanel wird aktualisiert
					updateTable(guiZimmer.contentpane1, guiZimmer.scrollPaneSuche, guiZimmer.sucheGast, guiZimmer.getQuery(), guiZimmer.scrollPaneSuche.getX(), guiZimmer.scrollPaneSuche.getY(), guiZimmer.scrollPaneSuche.getWidth(), guiZimmer.scrollPaneSuche.getHeight());
					//nächste Card wird zu CardLayout hinzugefügt und anschließen umgeschaltet
					guiZimmer.card.add("Card3", guiZimmer.contentpane4);
					guiZimmer.cardLayout.show(this.guiZimmer.card, "Card3");
				}

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
		}
		

		else if (e.getActionCommand().equals("Dl hinzufügen")){
			//Dl Buchung, folgend auf Zimmer-Buchung
			try{
				if (guiZimmer.showDl.getSQLTable().getSelectedRow() == -1) 
					throw new GUIException("Fehler: Zeile nicht markiert!");
				
				//wirklich buchen?
				int answer = JOptionPane.showConfirmDialog(guiZimmer.jf, "Dienstleistung wirklich buchen?", "Error",JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
					//Buchungsdatum wird geprüft
					
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
					buchung.getBid();
					//Dienstleistungsobjekt wird erstellt
					int did = Integer.parseInt((String) guiZimmer.showDl.getSQLTable().getValueAt(guiZimmer.showDl.getSQLTable().getSelectedRow(), 0).toString());
					Dienstleistung dl = new Dienstleistung(did, guiZimmer.bookDateDl.getDate());
					//buchung wird durchgeführt
					buchung.bookDl(buchung, dl);
				}
			}
			catch (GUIException gex) {
				JOptionPane.showMessageDialog(null, gex, "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			catch (NullPointerException nex) {
				JOptionPane.showMessageDialog(null, "Bitte alle Felder ausfüllen", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}		

		else if (e.getActionCommand().equals("Dl cancel")){
			//Abbrechen der DL-Buchung nach Zimmer-Buchung
			guiZimmer.jf.dispose();
		}


		
		else if (e.getActionCommand().equals("NewBookingDl")) {
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
				this.buchung = buchung;
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
				updateTable(guiDl.contentpane1, guiDl.scrollPaneSuche, guiDl.sucheGast, guiDl.getQuery(), guiDl.scrollPaneSuche.getX(), guiDl.scrollPaneSuche.getY(), guiDl.scrollPaneSuche.getWidth(), guiDl.scrollPaneSuche.getHeight());
				
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
				String von = getSQLDate(buchung.getVon());
				String bis = getSQLDate(buchung.getBis());
				
				System.out.println(a + " " + von + " " + bis + " ");
				//wirklich buchen?
				int answer = JOptionPane.showConfirmDialog(guiDl.jf, "Dienstleistung wirklich buchen?", "Error",JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
					
					//Datum in Range?
					if(!checkDlRange(buchung.getVon(), buchung.getBis(), guiDl.bookDate2.getDate()))
						throw new GUIException("Buchungsdatum überprüfen");
						//Dienstleistungsnummer wird ausgelesen
					int did = Integer.parseInt((String) guiDl.tableDl.getSQLTable().getValueAt(guiDl.tableDl.getSQLTable().getSelectedRow(), 0).toString());
					//DL-Objekt wird erzeugt
					Dienstleistung dl = new Dienstleistung(did, guiDl.bookDate2.getDate());
					//buchung wird durchgeführt
					buchung.bookDl(buchung, dl);
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
			guiZimmer.sucheGast = new JTableview(query);

			JTable suche = guiZimmer.sucheGast.getSQLTable();

			guiZimmer.scrollPaneSuche.setVisible(false);
			guiZimmer.scrollPaneSuche = null;
			guiZimmer.scrollPaneSuche = new JScrollPane(suche);
			guiZimmer.scrollPaneSuche.setBounds(10, 360, 1000, 200);
			guiZimmer.contentpane1.add(guiZimmer.scrollPaneSuche);

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
			guiDl.sucheGast = new JTableview(query);
			JTable suche = guiDl.sucheGast.getSQLTable();

			guiDl.scrollPaneSuche.setVisible(false);
			guiDl.scrollPaneSuche = null;
			guiDl.scrollPaneSuche = new JScrollPane(suche);
			guiDl.scrollPaneSuche.setBounds(10, 320, 1000, 200);
			guiDl.contentpane1.add(guiDl.scrollPaneSuche);
		}
	}	
}