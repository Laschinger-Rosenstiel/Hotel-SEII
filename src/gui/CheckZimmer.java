package gui;
import java.awt.Color;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.toedter.calendar.JDateChooser;

import control.BHOther;
import control.JTableview;

public class CheckZimmer extends GUIHelp{
			//ButtonHandler wird erzeugt
			BHOther ButtonHandler = new BHOther(this);
			//GUI-Objekte des Startpanels werden erstellt
			public JPanel contentpane1;
			private JButton buttonCheck;
			private JLabel labelZeitraum, labelVon, labelBis, labelZimmer; 
			public JDateChooser von, bis;
			public JTableview checkedZimmer;
			public JScrollPane scrollPaneChecked;
			
		public JPanel launchStartPanel(){
			//contentpane wird erzeugt, Layout auf null gesetzt
			contentpane1 = new JPanel();
			contentpane1.setLayout(null);
			
			//GUI-Objekte werden erzeugt
			labelZeitraum = new JLabel("Zeitraum:");
			labelVon = new JLabel("Von:");
			labelBis = new JLabel("Bis:");
			labelZimmer = new JLabel("Verfügbare Zimmer:");
			von = new JDateChooser();
			bis = new JDateChooser();
			buttonCheck = new JButton("Verfügbarkeit prüfen...");
			
			//ActionListener und ActionCommands werden gesetzt
			buttonCheck.addActionListener(ButtonHandler);
			buttonCheck.setActionCommand("Available?");
			
			//Datumsraum für Buchungsdatum wird gesetzt
			von.setSelectableDateRange(new Date(), null);
			bis.setSelectableDateRange(new Date(), null);
			
			//Koordinaten und Größe der GUI-Objekte wird gesetzt und zu contentpane hinzugefügt
			labelZeitraum.setBounds(x_column1, y_line1, x_width, y_height);
			contentpane1.add(labelZeitraum);
			labelVon.setBounds(x_column1, y_line2, x_width, y_height);
			contentpane1.add(labelVon);
			labelBis.setBounds(x_column1, y_line3, x_width, y_height);
			contentpane1.add(labelBis);
			von.setBounds(x_column2, y_line2, x_width, y_height);
			contentpane1.add(von);
			bis.setBounds(x_column2, y_line3, x_width, y_height);
			contentpane1.add(bis);
			buttonCheck.setBounds(x_column1, y_line4, 180, y_height);
			contentpane1.add(buttonCheck);
			labelZimmer.setBounds(x_column1, y_line5, x_width, y_height);
			contentpane1.add(labelZimmer);
			
			//Hintergrundfarbe wird gesetzt
			contentpane1.setOpaque(true);
			contentpane1.setBackground(new Color(209,218,248));
		
			//Contentpane wird zurückgegeben
			return contentpane1;
		}
		
		//getter-Methoden
		public Date getPickerVon(){
			return von.getDate();
		}
		
		public Date getPickerBis(){
			return bis.getDate();
		}
}
