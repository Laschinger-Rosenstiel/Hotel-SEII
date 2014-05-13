package gui;

import java.awt.Color;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import control.BHCancel;
import control.JTableview;

public class CancelDl extends GUIHelp{
		
		//ButtonHandler wird erzeugt
		BHCancel ButtonHandler = new BHCancel(this);
		//GUI-Objekte des Startpanel werden erstellt
		public JPanel contentpane1;
		private JButton buttonCancelDl, buttonSearch;
		private JLabel labelCancelDl, labelGid, labelVor, labelName, labelGeb; 
		public JTextField jtfGid, jtfVor, jtfName;
		public JDateChooser geb; 
		public JScrollPane scrollPaneSuche;
		public JTableview sucheBu;
		private String query;
		
		
	public JPanel launchStartPanel(){
		//JPanel contentpane1 wird erzeugt und Layout auf null gesetzt
		contentpane1 = new JPanel();
		contentpane1.setLayout(null);
		
		//GUI-Objekte werden erzeugt
		labelCancelDl = new JLabel("Dienstleistung stornieren:");	
		buttonCancelDl = new JButton("Stornieren");
		buttonSearch = new JButton("Suchen");
		labelGid = new JLabel("GID: ", JLabel.LEFT);
		labelVor = new JLabel("Vorname: ", JLabel.LEFT);
		labelName = new JLabel("Name: ", JLabel.LEFT);
		labelGeb = new JLabel("Geburtsdatum: ", JLabel.LEFT);
		jtfGid = new JTextField();
		jtfVor = new JTextField();
		jtfName = new JTextField();
		geb = new JDateChooser();
		
		//Query für SQL-Tabelle im Startpanel wird gesetzt
		query = "select gast.GID, gast.Vorname, gast.Name, gast.Geburtstag, `dl-buchung`.BID, `dl-buchung`.DLBID, `dl-buchung`.Datum, dienstleistung.DID, dienstleistung.Bezeichnung, dienstleistung.Preis from gast, buchung, `dl-buchung`, dienstleistung where gast.GID = buchung.GID and buchung.BID = `dl-buchung`.BID and `dl-buchung`.DID = dienstleistung.DID and `dl-buchung`.Datum > '"+getSQLDate(new Date())+"'";
		
		//ActionListener und ActionCommand der Buttons wird gesetzt
		buttonSearch.addActionListener(ButtonHandler);
		buttonSearch.setActionCommand("SearchDl");
		buttonCancelDl.addActionListener(ButtonHandler);
		buttonCancelDl.setActionCommand("CancelDl?");
		
		//Koordinaten und Größe der GUI-Objekte werden gesetzt und zu contentpane hinzugefügt
		labelCancelDl.setBounds(x_column1, y_line1, 200, y_height);
		contentpane1.add(labelCancelDl);
		labelGid.setBounds(x_column1, y_line2, x_width, y_height);
		contentpane1.add(labelGid);
		labelVor.setBounds(x_column1, y_line3, x_width, y_height);
		contentpane1.add(labelVor);
		labelName.setBounds(x_column1, y_line4, x_width, y_height);
		contentpane1.add(labelName);
		labelGeb.setBounds(x_column1, y_line5, x_width, y_height);
		contentpane1.add(labelGeb);
		jtfGid.setBounds(x_column3, y_line2, x_width, y_height);
		contentpane1.add(jtfGid);
		jtfVor.setBounds(x_column3, y_line3, x_width, y_height);
		contentpane1.add(jtfVor);
		jtfName.setBounds(x_column3, y_line4, x_width, y_height);
		contentpane1.add(jtfName);
		//Datumsraum für Geburtstag wird gesetzt
		setGebRoom(geb);
		geb.setBounds(x_column3, y_line5, x_width, y_height);
		contentpane1.add(geb);
		buttonSearch.setBounds(x_column1, y_line6, x_width, y_height);
		contentpane1.add(buttonSearch);
		
		//Tabelle für Dienstleistungsbuchungen wird erzeugt und zu contentpane hinzugefügt
		sucheBu = new JTableview(query);
		JTable suche = sucheBu.getSQLTable();
		scrollPaneSuche = new JScrollPane(suche);
		scrollPaneSuche.setBounds(x_column1, y_line7, 1000, 200);
		contentpane1.add(scrollPaneSuche);
		
		buttonCancelDl.setBounds(x_column1, y_line14, x_width, y_height);
		contentpane1.add(buttonCancelDl);
		
		//Hintergrundfarbe
		contentpane1.setOpaque(true);
		contentpane1.setBackground(new Color(209,218,248));
	
		//Contentpane wird zurückgegeben
		return contentpane1;
	}
	
	//getter-Methoden werden gesetzt
	
	public String getGidSuche(){
		return jtfGid.getText();
	}
	
	public String getVorSuche(){
		return jtfVor.getText();
	}
	
	public String getNameSuche(){
		return jtfName.getText();
	}
	
	public Date getGebSuche(){
		return geb.getDate();
	}
	
	public String getQuery(){
		return query;
	}
	
}
