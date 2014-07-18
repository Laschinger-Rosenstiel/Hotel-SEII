package control;

import gui.CancelDl;
import gui.CancelZimmer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Buchung;
import model.Dienstleistung;
import model.Gast;

public class BHCancel extends BHHelp implements ActionListener{

	CancelZimmer guiZimmer;
	CancelDl guiDl;
	static Buchung buchung;

	public BHCancel (CancelZimmer guiZimmer) {
		this.guiZimmer = guiZimmer;
	}

	public BHCancel (CancelDl guiDl) {
		this.guiDl = guiDl;
	}

	public void actionPerformed(ActionEvent e) throws NullPointerException {

		System.out.println("Das Ereignis hat den Wert: " + e.getActionCommand());
		if (e.getActionCommand().equals("CancelZimmer?")) {
			//wirklich löschen?
			int answer = JOptionPane.showConfirmDialog(null, "Zimmerbuchung, sowie Dienstleistungsbuchungen und Gaststammdaten, so fern keine weitere Buchung vorhanden sind, löschen?", "Error",JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.YES_OPTION) {
				try {
					//Zeile markiert?
					if (guiZimmer.sucheBu.getSQLTable().getSelectedRow() == -1) {
						throw new GUIException("Fehler: Zeile nicht markiert!");
					}		
					//Bid auslesen
					String Bid = (String) guiZimmer.sucheBu.getSQLTable().getValueAt(guiZimmer.sucheBu.getSQLTable().getSelectedRow(), 4).toString();
					int bid = Integer.parseInt(Bid);
					//Gid auslesen
					String Gid = (String) guiZimmer.sucheBu.getSQLTable().getValueAt(guiZimmer.sucheBu.getSQLTable().getSelectedRow(), 0).toString();
					int gid = Integer.parseInt(Gid);
					//wieviele Zimmerbuchungen hat der Gast?
					int anzahlBuchungen =Integer.parseInt(selectDB("SELECT count(GID) from buchung where GID = " + gid));
					
					Connection con = openDbConnection();
					//wenn nur eine Zimmerbuchung, wird Gast gelöscht und alle seine Buchungen mit
					if (anzahlBuchungen == 1){
						Gast gast = new Gast(gid);
						gast.deleteGast(con);
					}
					//sonst nur die Zimmerbuchung
					else {
						Buchung buchung = new Buchung(bid);
						buchung.cancelZimmer(buchung, con);
					}
					commitDbConnection(con);
					updateTable(guiZimmer.contentpane1, guiZimmer.scrollPaneSuche, guiZimmer.sucheBu, guiZimmer.getQuery(), guiZimmer.scrollPaneSuche.getX(), guiZimmer.scrollPaneSuche.getY(), guiZimmer.scrollPaneSuche.getWidth(), guiZimmer.scrollPaneSuche.getHeight(), null);
					
				}
				catch (GUIException gex) {
					JOptionPane.showMessageDialog(null, gex, "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if (e.getActionCommand().equals("CancelDl?")) {
			//Dienstleistung stornieren
			//wirklich löschen?
			int answer = JOptionPane.showConfirmDialog(null, "Dienstleistungen wirklich löschen?", "Error",JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.YES_OPTION) {
				try {
					//Zeile markiert?
					if (guiDl.sucheBu.getSQLTable().getSelectedRow() == -1) {
						throw new GUIException("Fehler: Zeile nicht markiert!");
					}		
					//bid und dlbid werden ausgelesen
					String Bid = (String) guiDl.sucheBu.getSQLTable().getValueAt(guiDl.sucheBu.getSQLTable().getSelectedRow(), 4).toString();
					int bid = Integer.parseInt(Bid);
					String Dlbid = (String) guiDl.sucheBu.getSQLTable().getValueAt(guiDl.sucheBu.getSQLTable().getSelectedRow(), 5).toString();
					int dlbid = Integer.parseInt(Dlbid);
					//did auslesen
					String did = (String) guiDl.sucheBu.getSQLTable().getValueAt(guiDl.sucheBu.getSQLTable().getSelectedRow(), 7).toString();
					//Dl und Buchungsobjekt erzeugen
					Dienstleistung dl = new Dienstleistung(Integer.parseInt(did));
					Buchung buchung = new Buchung(bid);
					buchung.setDlbid(dlbid);
					Connection con = openDbConnection();
					//stornieren
					buchung.cancelDl(buchung, dl, con);
					commitDbConnection(con);
					updateTable(guiDl.contentpane1, guiDl.scrollPaneSuche, guiDl.sucheBu, guiDl.getQuery(), guiDl.scrollPaneSuche.getX(), guiDl.scrollPaneSuche.getY(), guiDl.scrollPaneSuche.getWidth(), guiDl.scrollPaneSuche.getHeight(), null);
					
				}
				catch (GUIException gex) {
					JOptionPane.showMessageDialog(null, gex, "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if (e.getActionCommand().equals("SearchBu")){
			//Standardparameter werden gesetzt, damit bei leeren Feld auf alles geprüft wird
			String gebSuche = "%";
			String vorSuche = "%";
			String nameSuche = "%";
			String gidSuche = "%";
			guiZimmer.sucheBu = null;
			//Exception für falsches Datum wird abgefangen
			try {
				gebSuche = getSQLDate(guiZimmer.getGebSuche());
			}
			catch (NullPointerException ex) {
			}
			//Eingabefelder werden überprüft und bei Eingabe geändert 
			if (!guiZimmer.getGidSuche().equals(""))
				gidSuche = guiZimmer.getGidSuche();

			if (!guiZimmer.getVorSuche().equals(""))
				vorSuche = guiZimmer.getVorSuche()+"%";

			if (!guiZimmer.getNameSuche().equals(""))
				nameSuche = guiZimmer.getNameSuche() +"%";
			
			//Query für Suche
			String query = guiZimmer.getQuery() + " AND gast.GID like '" + gidSuche + "' AND gast.Name like '" + nameSuche + "' AND gast.Vorname like '" + vorSuche + "' AND gast.Geburtstag like '"+gebSuche+"'";
			System.out.println(query);
			guiZimmer.sucheBu = new JTableview(query);
			//SQL-Tabelle wird erzeugt und zu contentpane hinzugefügt
			JTable suche = guiZimmer.sucheBu.getSQLTable();

			guiZimmer.scrollPaneSuche.setVisible(false);
			guiZimmer.scrollPaneSuche = null;
			guiZimmer.scrollPaneSuche = new JScrollPane(suche);
			guiZimmer.scrollPaneSuche.setBounds(10, 280, 1000, 200);
			guiZimmer.contentpane1.add(guiZimmer.scrollPaneSuche);

		}

		else if (e.getActionCommand().equals("SearchDl")){
			//Standardparameter werden gesetzt, damit bei leeren Feld auf alles geprüft wird
			String gebSuche = "%";
			String vorSuche = "%";
			String nameSuche = "%";
			String gidSuche = "%";
			guiDl.sucheBu = null;

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
			String query = guiDl.getQuery() + " AND gast.GID like '" + gidSuche + "' AND gast.Name like '" + nameSuche + "' AND gast.Vorname like '" + vorSuche + "' AND gast.Geburtstag like '"+gebSuche+"'";

			//SQL-Tabelle wird erzeugt und zu contentpane hinzugefügt
			guiDl.sucheBu = new JTableview(query);

			JTable suche = guiDl.sucheBu.getSQLTable();

			guiDl.scrollPaneSuche.setVisible(false);
			guiDl.scrollPaneSuche = null;
			guiDl.scrollPaneSuche = new JScrollPane(suche);
			guiDl.scrollPaneSuche.setBounds(10, 280, 1000, 200);
			guiDl.contentpane1.add(guiDl.scrollPaneSuche);
		}
	}		
}
