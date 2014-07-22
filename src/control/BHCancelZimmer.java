package control;


import gui.InterfaceCancelZimmer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.ParseException;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import model.Buchung;

public class BHCancelZimmer extends BHHelp implements ActionListener{

	InterfaceCancelZimmer guiZimmer;
	static Buchung buchung;

	public BHCancelZimmer (InterfaceCancelZimmer guiZimmer) {
		this.guiZimmer = guiZimmer;
	}

	public void actionPerformed(ActionEvent e) throws NullPointerException {

		System.out.println("Das Ereignis hat den Wert: " + e.getActionCommand());
		if (e.getActionCommand().equals("CancelZimmer?")) {
			//wirklich löschen?
			int answer = JOptionPane.showConfirmDialog(null, "Zimmerbuchung sowie Dienstleistungsbuchungen, so fern keine weitere Buchungen vorhanden sind, löschen?", "Error",JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.YES_OPTION) {
				try {
					//Zeile markiert?
					if (guiZimmer.getSuche().getSQLTable().getSelectedRow() == -1) {
						throw new GUIException("Fehler: Zeile nicht markiert!");
					}		
					//Bid auslesen
					String Bid = (String) guiZimmer.getSuche().getSQLTable().getValueAt(guiZimmer.getSuche().getSQLTable().getSelectedRow(), 4).toString();
					int bid = Integer.parseInt(Bid);
					//zbid auslesen
					String Zbid = (String) guiZimmer.getSuche().getSQLTable().getValueAt(guiZimmer.getSuche().getSQLTable().getSelectedRow(), 5).toString();
					int zbid = Integer.parseInt(Zbid);
					//Gid auslesen
					
					Connection con = openDbConnection();
				
					Buchung buchung = new Buchung(bid, zbid);
					buchung.cancelZimmer(buchung, con);

					commitDbConnection(con);
				
					if (guiZimmer.getScrollPane() != null){
						guiZimmer.getScrollPane().setVisible(false);
					}
					
					guiZimmer.setSucheNull();
					guiZimmer.setSuche(guiZimmer.getQuery());
					guiZimmer.setScrollPaneNull();
					guiZimmer.setScrollPane(guiZimmer.getSuche().getSQLTable());
					guiZimmer.setBoundsScrollPane(10, 280, 1000, 200);
					guiZimmer.addToContentPane(guiZimmer.getScrollPane());
				}
				catch (GUIException gex) {
					JOptionPane.showMessageDialog(null, gex, "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
				catch(ParseException pex) {
					JOptionPane.showMessageDialog(null, pex, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
		else if (e.getActionCommand().equals("SearchBu")){
			//Standardparameter werden gesetzt, damit bei leeren Feld auf alles geprüft wird
			String gebSuche = "%";
			String vorSuche = "%";
			String nameSuche = "%";
			String gidSuche = "%";
			guiZimmer.setSucheNull();
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
			
			guiZimmer.setSuche(query);
			//SQL-Tabelle wird erzeugt und zu contentpane hinzugefügt
			JTable suche = guiZimmer.getSuche().getSQLTable();

			guiZimmer.setScrollPaneVisible(false);
			
			guiZimmer.setScrollPaneNull();
			guiZimmer.setScrollPane(suche);
			guiZimmer.setBoundsScrollPane(10, 280, 1000, 200);
			guiZimmer.addToContentPane(guiZimmer.getScrollPane());

		}

		
	}		
}
