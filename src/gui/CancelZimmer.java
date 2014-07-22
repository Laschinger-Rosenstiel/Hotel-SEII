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

public class CancelZimmer extends GUIHelp{
	
		//ButtonHandler wird erzeugt
		BHCancel ButtonHandler = new BHCancel(this);
		//GUI-Objekte werden erstellt
		public JPanel contentpane1;
		private JButton buttonCancelZimmer, buttonSearch;
		private JLabel labelCancelZimmer, labelGid, labelVor, labelName, labelGeb; 
		public JTextField jtfGid, jtfVor, jtfName;
		public JDateChooser geb; 
		public JScrollPane scrollPaneSuche;
		public JTableview sucheBu;
		private String query;
		
	public JPanel launchStartPanel(){
		//contentpane wird erzeugt, Layout auf null gesetzt
		contentpane1 = new JPanel();
		contentpane1.setLayout(null);

		//GUI-Objekte werden erzeugt
		labelCancelZimmer = new JLabel("Zimmerbuchung stornieren:");	
		buttonCancelZimmer = new JButton("Stornieren");
		buttonSearch = new JButton("Suchen");
		labelGid = new JLabel("GID: ", JLabel.LEFT);
		labelVor = new JLabel("Vorname: ", JLabel.LEFT);
		labelName = new JLabel("Name: ", JLabel.LEFT);
		labelGeb = new JLabel("Geburtsdatum: ", JLabel.LEFT);
		jtfGid = new JTextField();
		jtfVor = new JTextField();
		jtfName = new JTextField();
		geb = new JDateChooser();
		
		//ActionListener und ActionCommand für Buttons werden gesetzt
		buttonSearch.addActionListener(new BHCancel(this));
		buttonSearch.setActionCommand("SearchBu");
		buttonCancelZimmer.addActionListener(ButtonHandler);
		buttonCancelZimmer.setActionCommand("CancelZimmer?");
		
		//Query für SQL-Tabelle in Startpanel wird gesetzt
		query = "select g.GID, g.Vorname, g.Name, g.Geburtstag, zb.BID, zb.ZBID, zb.ZID, b.Von, b.Bis from gast g, buchung b, `zimmer-buchung` zb	where g.GID = b.GID and b.BID = zb.BID and b.Von > '"+getSQLDate(new Date())+"'";
		
		//Koordinaten und Größe der GUI-Objekte wird gesetzt und zu contentpane hinzugefügt
		labelCancelZimmer.setBounds(x_column1, y_line1, 200, y_height);
		contentpane1.add(labelCancelZimmer);
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
		//Datumsraum für Geburtsdatum wird gesetzt
		setGebRoom(geb);
		geb.setBounds(x_column3, y_line5, x_width, y_height);
		contentpane1.add(geb);
		buttonSearch.setBounds(x_column1, y_line6, x_width, y_height);
		contentpane1.add(buttonSearch);
		buttonCancelZimmer.setBounds(x_column1, y_line14, x_width, y_height);
		contentpane1.add(buttonCancelZimmer);
		
		//Tabelle für stornierbare Zimmer wird erzeugt und zu contentpane hinzugefügt
		sucheBu = new JTableview(query);
		JTable suche = sucheBu.getSQLTable();
		scrollPaneSuche = new JScrollPane(suche);
		scrollPaneSuche.setBounds(x_column1, y_line7, 1000, 200);
		contentpane1.add(scrollPaneSuche);
		
		//Hintergrundfarbe
		contentpane1.setOpaque(true);
		contentpane1.setBackground(new Color(209,218,248));
	
		//contentpane wird zurückgegeben
		return contentpane1;
	}
	
	//getter-Methoden
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
