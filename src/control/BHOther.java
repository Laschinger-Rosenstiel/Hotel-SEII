package control;

import gui.CalcPreis;
import gui.CheckZimmer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class BHOther extends BHHelp implements ActionListener{
	CheckZimmer guiZimmer;
	CalcPreis guiPreis;
	
	public BHOther (CheckZimmer guiZimmer) {
		this.guiZimmer = guiZimmer;
	}
	
	public BHOther (CalcPreis guiPreis) {
		this.guiPreis = guiPreis;
	}

	public void actionPerformed(ActionEvent e) throws NullPointerException {
		
		System.out.println("Das Ereignis hat den Wert: " + e.getActionCommand());
		if (e.getActionCommand().equals("Available?")) {
			//Verfügbarkeitsprüfung
			try {
			checkBookingDate(guiZimmer.von.getDate(), guiZimmer.bis.getDate());
			Date von = guiZimmer.getPickerVon();
			Date bis = guiZimmer.getPickerBis();
			Calendar vonC = new GregorianCalendar();
			vonC.setTime(von);
			vonC.add(Calendar.DATE, 1);
			System.out.println(getSQLDate(vonC.getTime()));
	
			
			//umwandeln in SQL-Format
			String vonSql = getSQLDate(vonC.getTime());
			String bisSql = getSQLDate(bis);
			
			//SQL-Tabelle wird erzeugt und zu Startpanel hinzugefügt
			guiZimmer.checkedZimmer = new JTableview("SELECT * from zimmer where zimmer.ZID not in (SELECT `zimmer-buchung`.ZID from `zimmer-buchung` where (Von between '"+vonSql+"' AND '"+bisSql+"') OR (Bis between '"+vonSql+"' AND '"+bisSql+"'))");
			JTable available = guiZimmer.checkedZimmer.getSQLTable();
			
			guiZimmer.scrollPaneChecked = new JScrollPane(available);
			guiZimmer.scrollPaneChecked.setBounds(10, 240, 400, 300);
			guiZimmer.contentpane1.add(guiZimmer.scrollPaneChecked);

			}
			catch (GUIException gex) {
				JOptionPane.showMessageDialog(null, gex, "Error",
                        JOptionPane.ERROR_MESSAGE);
			}
			catch (NullPointerException nex) {
				JOptionPane.showMessageDialog(null, "Datum überprüfen", "Error",
                        JOptionPane.ERROR_MESSAGE);
			}			
		}
		
		else if (e.getActionCommand().equals("Search")){
			//Suche, siehe andere Suchen bspw. BHBook
			String gebSuche = "%";
			String vorSuche = "%";
			String nameSuche = "%";
			String gidSuche = "%";
			guiPreis.searchBu = null;
			
			try {
				gebSuche = getSQLDate(guiPreis.getGebSuche());
			}
			catch (NullPointerException ex) {
				
			}
			
			
			if (!guiPreis.getGidSuche().equals(""))
				gidSuche = guiPreis.getGidSuche();
		
			
			if (!guiPreis.getVorSuche().equals(""))
				vorSuche = guiPreis.getVorSuche()+"%";
			
			
		
			if (!guiPreis.getNameSuche().equals(""))
				nameSuche = guiPreis.getNameSuche() +"%";
		
			String query = guiPreis.getQuery() + " AND gast.GID like '" + gidSuche + "' AND gast.Name like '" + nameSuche + "' AND gast.Vorname like '" + vorSuche + "' AND gast.Geburtstag like '"+gebSuche+"'";
			System.out.println(query);
			guiPreis.searchBu = new JTableview(query);
			
			JTable suche = guiPreis.searchBu.getSQLTable();
			
			guiPreis.scrollPaneSuche.setVisible(false);
			guiPreis.scrollPaneSuche = null;
			guiPreis.scrollPaneSuche = new JScrollPane(suche);
			guiPreis.scrollPaneSuche.setBounds(10, 320, 1000, 200);
			guiPreis.contentpane1.add(guiPreis.scrollPaneSuche);
		
		}
	}
}