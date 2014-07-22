package gui;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import control.BHBookZimmer;
import control.JTableview;

import javax.swing.*;

import model.Buchung;
import model.Gast;

import com.toedter.calendar.JDateChooser;

public class BookZimmer extends GUIHelp{
	
	StartFrame startFrame;
	BHBookZimmer ButtonHandler;
	
	//Objekte für Startpanel werden erstellt
	public JPanel contentpane1;
	private JButton jb1, jb2, jb3;
	public JTextField jtfGID, jtfVorname, jtfName, jtfGeb; 
	private JLabel labeltable1, labeltable2, labeljtfGID, labeljtfVorname, labeljtfName, labeljtfGeb; 
	public JDateChooser geb;
	public JScrollPane scrollPaneSuche;
	public JTableview sucheGast;
	String query;
	
	//card Layout wird erstellt und erzeugt
	public CardLayout cardLayout;
	public JPanel card = new JPanel();
	
	//Objekte für JFrame werden erstellt
	public JFrame jf;
	private JPanel contentpane2;
	public JTextField jtfVorname2, jtfName2, jtfGeb2, jtfStr2, jtfHn2, jtfPlz2, jtfOrt2, jtfLand2, jtfTel2_1, jtfTel2_2, jtfTel2_3;
	private JLabel labeltable3, labeljtfVorname2, labeljtfName2, labeljtfGeb2, labeljtfStr2, labeljtfPlz2, labeljtfOrt2, labeljtfLand2, labeljtfTel2;
	private JButton next1;
	public JDateChooser geb2;
	
	//Objekte für 2. Card des JFrame werden erstellt
	public JPanel contentpane3 = null;
	public JLabel labeltable4, labeltable5, labeltable6, labeltable7, labeljtfVon, labeljtfBis;
	private JTextField labelVor3, labelName3, labelStr3, labelPlz3, labelOrt3, labelLand3;
	private String tel;
	public JTextField labelTel3;
	private JTextField labelGeb3;
	public JTextField labelVor3_2, labelName3_2, labelStr3_2, labelPlz3_2, labelOrt3_2, labelLand3_2, labelTel3_2, labelGeb3_2;
	private JButton checkAvailability, back, bookZimmer, next2, cancel;
	public JDateChooser pickerVon, pickerBis;
	public JTableview availableZimmer, bookedZimmer, bookedZimmer2;
	public JScrollPane scrollPaneZimmer, scrollPaneBookedZimmer, scrollPaneBookedZimmer2;

	//Objekte für 3. Card des JFrame werden erstellt
	public JPanel contentpane4 = null;
	public JLabel labelDl, labelDatum, labelBookedDl;
	public JTableview showDl, bookedDl, bookedDl2;
	public JScrollPane scrollPaneShow, scrollPaneBookedDl, scrollPaneBookedDl2;
	public JDateChooser bookDateDl;
	private JButton bookDl, next3, cancelAll2;
	
	//Objekte für 4. Card des JFrame werden erstellt
	public JPanel contentpane5 = null;
	private JButton buttonCommit, cancelAll3;
	private JTextField labeltablePreis, labelPreis4;
	//Objekte Gaststammdaten
	public JLabel labelOverviewGast;
	public JTextField labeltableVorn, labeltableName, labeltableStr, labeltableHn, labeltablePlz, labeltableOrt, labeltableLand, labeltableGeb, labeltableTel;
	public JTextField labelVor4, labelName4, labelStr4, labelHn4, labelPlz4, labelOrt4, labelLand4, labelGeb4, labelTel4;
	//Objekte Zimmer
	public JLabel labelOverviewZimmer;
	public JTableview jtvShowBookedZimmer;
	public JScrollPane spShowBookedZimmer;
	//Objekte Dienstleistungen
	public JLabel labelOverviewDl;
	public JTableview jtvShowBookedDl;
	public JScrollPane spShowBookedDl;
	
	public BookZimmer(StartFrame startFrame){
		this.startFrame = startFrame;
		 ButtonHandler = new BHBookZimmer(this, startFrame);
	}
	
	public JPanel launchStartPanel() {
		//JPanel contentpane wird erzeugt, Layout wird auf null gesetzt
		
		contentpane1 = new JPanel();
		contentpane1.setLayout(null);
		
		//GUI Objekte für den Startpanel werden erzeugt
		jtfVorname = new JTextField(20);
		jtfName = new JTextField(30);
		jtfGID = new JTextField(30);
		geb = new JDateChooser();
		labeltable1 = new JLabel("Gastsuche: ");
		labeltable2 = new JLabel("Suchergebnisse: ");
		labeljtfGID = new JLabel("GID: ", JLabel.LEFT);
		labeljtfVorname = new JLabel("Vorname: ", JLabel.LEFT);
		labeljtfName = new JLabel("Name: ", JLabel.LEFT);
		labeljtfGeb = new JLabel("Geburtsdatum: ", JLabel.LEFT);
		jb1 = new JButton("Neuer Gast...");
		jb2 = new JButton("Auf ausgewählten Gast buchen");
		jb3 = new JButton("Suche");
		
		//ButtonHandler, ActionListener und ActionCommands für Buttons werden erzeugt
		jb1.setActionCommand("NewBooking");
		jb1.addActionListener(ButtonHandler);
		jb2.setActionCommand("ExistBooking");
		jb2.addActionListener(ButtonHandler);
		jb3.setActionCommand("SEARCH");
		jb3.addActionListener(ButtonHandler);
		
		//Query für SQL-Tabelle auf Startpanel wird gesetzt
		query = "Select * From gast";
		
		//Tabelle für bebuchbare Gäste wird erstellt (query, s.o.), Koordinaten gesetzt und zugeordnet
			
		sucheGast = new JTableview(query);
		JTable suche = sucheGast.getSQLTable();
		scrollPaneSuche = new JScrollPane(suche);
		contentpane1.remove(scrollPaneSuche);
		
		
		//Bounds setzen und zur Contentpane hinzufügen
		labeltable1.setBounds(x_column1, y_line2, x_width, y_height);
		contentpane1.add(labeltable1);
		jb1.setBounds(x_column1, y_line1, x_width, y_height);
		contentpane1.add(jb1);
		labeljtfGID.setBounds(x_column1, y_line3, x_width, y_height);
		contentpane1.add(labeljtfGID);
		labeljtfVorname.setBounds(x_column1, y_line4, x_width, y_height);
		contentpane1.add(labeljtfVorname);
		labeljtfName.setBounds(x_column1, y_line5, x_width, y_height);
		contentpane1.add(labeljtfName);
		labeljtfGeb.setBounds(x_column1, y_line6, x_width, y_height);
		contentpane1.add(labeljtfGeb);
		labeltable2.setBounds(x_column1, y_line8, x_width, y_height);
		contentpane1.add(labeltable2);
		
		jtfGID.setBounds(x_column3, y_line3, x_width, y_height);
		contentpane1.add(jtfGID);
		jtfVorname.setBounds(x_column3, y_line4, x_width, y_height);
		contentpane1.add(jtfVorname);
		jtfName.setBounds(x_column3, y_line5, x_width, y_height);
		contentpane1.add(jtfName);
		//Datumsraum für Geburtsdatum wird gesetzt
		setGebRoom(geb);
		geb.setBounds(x_column3, y_line6, x_width, y_height);
		contentpane1.add(geb);
		jb2.setBounds(x_column1, y_line14, 250, y_height);
		contentpane1.add(jb2);
		jb3.setBounds(x_column1, y_line7, x_width, y_height);
		contentpane1.add(jb3);
		scrollPaneSuche.setBounds(x_column1, y_line9, 800, 200);
		contentpane1.add(scrollPaneSuche);
		
		//Color
		contentpane1.setOpaque(true);
		contentpane1.setBackground(new Color(209,218,248));
		//Contentpane wird zurückgegeben
		return contentpane1;
	}

	public void launchJFrame() {
		//JFrame für Zimmer-Buchungsfenster wird erzeugt, contentpane2 wird erzeugt, Layout auf null	
		jf = null;
		//contentpane1= null;
		contentpane2 = null;
		contentpane3 = null;
		contentpane4 = null;
		contentpane5 = null;
		
		jf = new JFrame("Zimmerbuchung");		
		contentpane2 = new JPanel();
		contentpane2.setLayout(null);
		
		JPanel contentPane = (JPanel)jf.getContentPane();
		//Layout des CardLayout wird gesetzt
		card.setLayout(cardLayout = new CardLayout());
		
		//contentpane3 wird unvisible wenn "zurück" gegangen wird
		if (contentpane3 != null) {
		contentpane3.setVisible(false);
		}
		
		//GUI-Objekte für Popup-JFrame wwerden erzeugt
		labeltable3 = new JLabel("Neuen Gast anlegen: ");
		labeljtfVorname2 = new JLabel("Vorname: ", JLabel.LEFT);
		labeljtfName2 = new JLabel("Name: ", JLabel.LEFT);
		labeljtfGeb2 = new JLabel("Geburtsdatum: ", JLabel.LEFT);
		labeljtfStr2 = new JLabel("Straße & Hausnummer: ", JLabel.LEFT);
		labeljtfPlz2 = new JLabel("Postleitzahl: ", JLabel.LEFT);
		labeljtfOrt2 = new JLabel("Ort: ", JLabel.LEFT);
		labeljtfLand2 = new JLabel("Land: ", JLabel.LEFT);
		labeljtfTel2 = new JLabel("Telefonnummer: ", JLabel.LEFT);
		
		jtfVorname2 = new JTextField(20);
		jtfName2 = new JTextField(30);
		geb2 = new JDateChooser();
		jtfStr2 = new JTextField(30);
		jtfHn2 = new JTextField(8);
		jtfPlz2 = new JTextField(10);
		jtfOrt2 = new JTextField(10);
		jtfLand2 = new JTextField(10);
		jtfTel2_1 = new JTextField(6);
		jtfTel2_1.setText("+49");
		jtfTel2_2 = new JTextField(8);
		jtfTel2_3 = new JTextField(12);
		
		next1 = new JButton("Weiter...");
		
		
		//ActionListener und ActionCommand wird gesetzt
		next1.setActionCommand("NEXT");
		next1.addActionListener(ButtonHandler);
		
		//Bounds setzen und zur Contentpane hinzufügen		
		labeltable3.setBounds(x_column1, y_line1, x_width, y_height);
		contentpane2.add(labeltable3);
		labeljtfVorname2.setBounds(x_column1, y_line2, x_width, y_height);
		contentpane2.add(labeljtfVorname2);
		labeljtfName2.setBounds(x_column1, y_line3, x_width, y_height);
		contentpane2.add(labeljtfName2);
		labeljtfGeb2.setBounds(x_column1, y_line4, x_width, y_height);
		contentpane2.add(labeljtfGeb2);
		labeljtfStr2.setBounds(x_column1, y_line5, x_width, y_height);
		contentpane2.add(labeljtfStr2);
		labeljtfPlz2.setBounds(x_column1, y_line6, x_width, y_height);
		contentpane2.add(labeljtfPlz2);
		labeljtfOrt2.setBounds(x_column1, y_line7, x_width, y_height);
		contentpane2.add(labeljtfOrt2);
		labeljtfLand2.setBounds(x_column1, y_line8, x_width, y_height);
		contentpane2.add(labeljtfLand2);
		labeljtfTel2.setBounds(x_column1, y_line9, x_width, y_height);
		contentpane2.add(labeljtfTel2);
		
		jtfVorname2.setBounds(x_column3, y_line2, x_width, y_height);
		contentpane2.add(jtfVorname2);
		jtfName2.setBounds(x_column3, y_line3, x_width, y_height);
		contentpane2.add(jtfName2);
		
		setGebRoom(geb2);
		geb2.setBounds(x_column3, y_line4, x_width, y_height);
		contentpane2.add(geb2);
		
		jtfStr2.setBounds(x_column3, y_line5, x_width, y_height);
		contentpane2.add(jtfStr2);
		jtfHn2.setBounds(x_column4, y_line5, 40, y_height);
		contentpane2.add(jtfHn2);
		jtfPlz2.setBounds(x_column3, y_line6, x_width, y_height);
		contentpane2.add(jtfPlz2);
		jtfOrt2.setBounds(x_column3, y_line7, x_width, y_height);
		contentpane2.add(jtfOrt2);
		jtfLand2.setBounds(x_column3, y_line8, x_width, y_height);
		jtfLand2.setText("Deutschland");
		contentpane2.add(jtfLand2);
		jtfTel2_1.setBounds(x_column3, y_line9, 30, y_height);
		contentpane2.add(jtfTel2_1);
		jtfTel2_2.setBounds(180, y_line9, 50, y_height);
		contentpane2.add(jtfTel2_2);
		jtfTel2_3.setBounds(230, y_line9, 90, y_height);
		contentpane2.add(jtfTel2_3);
		
		next1.setBounds(x_column1, y_line10, x_width, y_height);
		contentpane2.add(next1);
		
		//Hintergrundfarbe wird gesetzt
		contentpane2.setOpaque(true);
		contentpane2.setBackground(new Color(209,218,248));
		
		//contentpane wird zu CardLayout hinzugefügt
		card.add("Card1", contentpane2);
		contentPane.add(card);
		
		//Frame wird visible gesetzt, größe angepasst und Close-Option gesetzt
		jf.setVisible(true);
		jf.setResizable(true);
		jf.setSize(900,500);
		jf.setLocation(300,50);
		jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
            	int answer = JOptionPane.showConfirmDialog(jf, "Möchten Sie die Buchung wirklich beenden?", null,JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
                	if (ButtonHandler.getCon() != null){
	                	ButtonHandler.closeDbConnection(ButtonHandler.getCon());
                	}
	                jf.dispose();
	                ButtonHandler.reloadMainframe();
	            }
                
            }
        };
        jf.addWindowListener(exitListener);
	}

	public JPanel launchSecond() {
		//contentpane2 wird invisible gesetzt
		contentpane2.setVisible(false);
		//contentpane3 wird erstellt und Layout auf null gesetzt
		contentpane3 = new JPanel();
		contentpane3.setLayout(null);
		
		//GUI-Objekte werden erstellt
		labelVor3 = new JTextField("Vorname: ");
		labelName3 = new JTextField("Nachname: ");
		labelStr3 = new JTextField("Straße: ");
		labelPlz3 = new JTextField("Postleitzahl: ");
		labelOrt3 = new JTextField("Ort: ");
		labelTel3 = new JTextField("Telefon: ");
		labelLand3 = new JTextField("Land: ");
		labelGeb3 = new JTextField("Geburtsdatum: ");
		labelVor3_2 = new JTextField();
		labelName3_2 = new JTextField();
		labelStr3_2 = new JTextField();
		labelPlz3_2 = new JTextField();
		labelOrt3_2 = new JTextField();
		labelTel3_2 = new JTextField();
		labelLand3_2 = new JTextField();
		labelGeb3_2 = new JTextField();
		
		labeltable4 = new JLabel("Gaststammdaten:");
		labeltable5 = new JLabel("Datum auswählen:");
		labeltable6 = new JLabel("Zimmer auswählen:");
		labeltable7 = new JLabel("Zimmerbuchungen:");
		labeljtfVon = new JLabel("Von:");
		labeljtfBis = new JLabel("Bis:");
		
		pickerVon = new JDateChooser();
		pickerBis = new JDateChooser();
		
		checkAvailability = new JButton("Verügbare Zimmer anzeigen...");
		bookZimmer = new JButton("Zimmer buchen");
		next2 = new JButton("Weiter...");
		back = new JButton("Zurück");
		cancel = new JButton("Abbrechen");
		
		
		//ActionListener und ActionCommand für Buttons werden gesetzt
		checkAvailability.setActionCommand("Available?");
		checkAvailability.addActionListener(ButtonHandler);
		bookZimmer.setActionCommand("BookZimmer");
		bookZimmer.addActionListener(ButtonHandler);
		back.setActionCommand("BACK");
		back.addActionListener(ButtonHandler);
		next2.setActionCommand("NEXT2");
		next2.addActionListener(ButtonHandler);
		cancel.setActionCommand("CancelAll");
		cancel.addActionListener(ButtonHandler);
		
		//Datumsraum für JDateChooser werden gesetzt
		pickerVon.setSelectableDateRange(new Date(), null);
		pickerBis.setSelectableDateRange(new Date(), null);
		
		//Textfelder werden zu "Labels" formatiert
		setTfForm(labelVor3);
		setTfForm(labelName3);
		setTfForm(labelStr3);
		setTfForm(labelPlz3);
		setTfForm(labelOrt3);
		setTfForm(labelTel3);
		setTfForm(labelLand3);
		setTfForm(labelGeb3);
		setTfForm(labelVor3_2);
		setTfForm(labelName3_2);
		setTfForm(labelStr3_2);
		setTfForm(labelPlz3_2);
		setTfForm(labelOrt3_2);
		setTfForm(labelTel3_2);
		setTfForm(labelLand3_2);
		setTfForm(labelGeb3_2);
		
		//Bounds der GUI-Objekte setzen und zur Contentpane hinzufügen
		labeltable4.setBounds(x_column1, y_line1, x_width, y_height);
		contentpane3.add(labeltable4);
		labelVor3.setBounds(x_column1, y_line2, 100, y_height);
		contentpane3.add(labelVor3);
		labelName3.setBounds(x_column1, y_line3, 100, y_height);
		contentpane3.add(labelName3);
		labelStr3.setBounds(x_column1, y_line4, 100, y_height);
		contentpane3.add(labelStr3);
		labelPlz3.setBounds(x_column1, y_line5, 100, y_height);
		contentpane3.add(labelPlz3);
		labelOrt3.setBounds(x_column4, y_line2, 100, y_height);
		contentpane3.add(labelOrt3);
		labelLand3.setBounds(x_column4, y_line3, 100, y_height);
		contentpane3.add(labelLand3);
		labelGeb3.setBounds(x_column4, y_line4, 100, y_height);
		contentpane3.add(labelGeb3);
		labelTel3.setBounds(x_column4, y_line5, 100, y_height);
		contentpane3.add(labelTel3);
		labelVor3_2.setBounds(x_column3, y_line2, x_width, y_height);
		contentpane3.add(labelVor3_2);
		labelName3_2.setBounds(x_column3, y_line3, x_width, y_height);
		contentpane3.add(labelName3_2);
		labelStr3_2.setBounds(x_column3, y_line4, x_width, y_height);
		contentpane3.add(labelStr3_2);
		labelPlz3_2.setBounds(x_column3, y_line5, x_width, y_height);
		contentpane3.add(labelPlz3_2);
		labelOrt3_2.setBounds(x_column5, y_line2, x_width, y_height);
		contentpane3.add(labelOrt3_2);
		labelLand3_2.setBounds(x_column5, y_line3, x_width, y_height);
		contentpane3.add(labelLand3_2);
		labelTel3_2.setBounds(x_column5, y_line4, x_width, y_height);
		contentpane3.add(labelTel3_2);
		labelGeb3_2.setBounds(x_column5, y_line5, x_width, y_height);
		contentpane3.add(labelGeb3_2);
		labeltable5.setBounds(x_column1, y_line6, x_width, y_height);
		contentpane3.add(labeltable5);
		labeltable6.setBounds(200, y_line6, x_width, y_height);
		contentpane3.add(labeltable6);
		labeltable7.setBounds(500, y_line6, x_width, y_height);
		contentpane3.add(labeltable7);
		labeljtfVon.setBounds(x_column1, y_line7, 40, y_height);
		contentpane3.add(labeljtfVon);	
		labeljtfBis.setBounds(x_column1, y_line8, 40, y_height);
		contentpane3.add(labeljtfBis);		
		pickerVon.setBounds(x_column2, y_line7, 100, y_height);
		contentpane3.add(pickerVon);
		pickerBis.setBounds(x_column2, y_line8, 100, y_height);
		contentpane3.add(pickerBis);
		checkAvailability.setBounds(x_column1, y_line10, x_width, y_height);
		contentpane3.add(checkAvailability);
		bookZimmer.setBounds(200, y_line10, x_width, y_height);
		bookZimmer.setEnabled(false);
		contentpane3.add(bookZimmer);
		back.setBounds(x_column1, y_line11, x_width, y_height);
		contentpane3.add(back);
		cancel.setBounds(x_column1, y_line11, x_width, y_height);
		contentpane3.add(cancel);
		cancel.setEnabled(false);
		cancel.setVisible(false);
		next2.setBounds(x_column3, y_line11, x_width, y_height);
		contentpane3.add(next2);
		next2.setEnabled(false);
		
		//Hintergrundfarbe wird gesetzt
		contentpane3.setOpaque(true);
		contentpane3.setBackground(new Color(209,218,248));
		
		//contentpane wird zurückgegeben
		return contentpane3;
	}
	
	public JPanel launchThird() {
		//contenpane3 wird invisible gesetzt
		
		contentpane3.setVisible(false);
		contentpane3 = null;
		//JPanel contentpane wird erzeugt, Layout wird auf null gesetzt
		contentpane4 = null;
		contentpane4 = new JPanel();
		contentpane4.setLayout(null);
		
		//GUI-Objekte werden erzeugt
		labelDl = new JLabel("Dienstleistung buchen:");
		labelDatum = new JLabel("Buchungsdatum: ");
		labelBookedDl = new JLabel("ausgewählte Dienstleistungen:");
		showDl = new JTableview("select * from dienstleistung");
		JTable show = showDl.getSQLTable();
		bookDateDl = new JDateChooser();
		scrollPaneShow = new JScrollPane(show);
		bookDl = new JButton("Buchen");
		next3=new JButton("Weiter");
		cancelAll2=new JButton("Abbrechen");
		bookDateDl = new JDateChooser();
		
		//Datumsrauf für JDateChooser wird gesetzt
		bookDateDl.setSelectableDateRange(pickerVon.getDate(), pickerBis.getDate());
		
		//ActionListener und ActionCommands werden für die Buttons gesetzt
		bookDl.addActionListener(ButtonHandler);
		bookDl.setActionCommand("AddDl");
		next3.addActionListener(ButtonHandler);
		next3.setActionCommand("NEXT3");
		cancelAll2.addActionListener(ButtonHandler);
		cancelAll2.setActionCommand("CancelAll");
		
		//Koordinaten, Größe wird gesetzt und Objekte zu contentpane hinzugefügt
		labelDl.setBounds(x_column1, y_line1, x_width, y_height);
		contentpane4.add(labelDl);
		labelDatum.setBounds(x_column4, y_line1, x_width, y_height);
		contentpane4.add(labelDatum);
		labelBookedDl.setBounds(x_column6, y_line1, x_width +30, y_height);
		contentpane4.add(labelBookedDl);
		bookDateDl.setBounds(x_column4, y_line2, x_width, y_height);
		contentpane4.add(bookDateDl);
		scrollPaneShow.setBounds(x_column1, y_line2, 200, 200);
		contentpane4.add(scrollPaneShow);
		bookDl.setBounds(x_column1, y_line7, x_width, y_height);
		contentpane4.add(bookDl);
		next3.setBounds(x_column3, y_line11, x_width, y_height);
		contentpane4.add(next3);
		cancelAll2.setBounds(x_column1, y_line11, x_width, y_height);
		contentpane4.add(cancelAll2);
		
		//Hintergrundfarbe
		contentpane4.setOpaque(true);
		contentpane4.setBackground(new Color(209,218,248));
		
		//Contentpane wird zurückgegeben
		return contentpane4;
	}
	
	public JPanel launchFourth() {
		//contentpane3 wird invisible gesetzt
				contentpane4.setVisible(false);
				//contentpane4 wird erstellt und Layout auf null gesetzt
				contentpane5 = new JPanel();
				contentpane5.setLayout(null);
				
				
				//Bereicht Gaststammdaten
				Gast gast = ButtonHandler.getGast();
				Buchung buchung = BHBookZimmer.getBuchung();
				
				//GUI-Objekte werden erstellt
				labelOverviewGast = new JLabel("Gaststammdaten: ");
				labeltableVorn = new JTextField("Vorname: ");
				labeltableName = new JTextField("Nachname: ");
				labeltableStr = new JTextField("Straße: ");
				labeltablePlz = new JTextField("Postleitzahl: ");
				labeltableOrt = new JTextField("Ort: ");
				labeltableTel = new JTextField("Telefon: ");
				labeltableLand = new JTextField("Land: ");
				labeltableGeb = new JTextField("Geburtsdatum: ");
				labeltablePreis = new JTextField("Gesamtpreis: ");
				labelPreis4 = new JTextField(buchung.getPreis(buchung.getBid(), ButtonHandler.getCon())+ " €");
				labelVor4 = new JTextField(gast.getVorname());
				labelName4 = new JTextField(gast.getName());
				labelStr4 = new JTextField(gast.getStrasse());
				labelPlz4 = new JTextField(gast.getPlz());
				labelOrt4 = new JTextField(gast.getOrt());
				labelTel4 = new JTextField(gast.getTel());
				labelLand4 = new JTextField(gast.getLand());
				SimpleDateFormat gebForm =new SimpleDateFormat("dd.MM.yyyy");
				String geb = gebForm.format(gast.getGeb());
				labelGeb4 = new JTextField(geb);
				
				//Textfelder zu Labels formatiert
				setTfForm(labelVor4);
				setTfForm(labelName4);
				setTfForm(labelStr4);
				setTfForm(labelPlz4);
				setTfForm(labelOrt4);
				setTfForm(labelTel4);
				setTfForm(labelLand4);
				setTfForm(labelGeb4);
				setTfForm(labeltableVorn);
				setTfForm(labeltableName);
				setTfForm(labeltableStr);
				setTfForm(labeltablePlz);
				setTfForm(labeltableOrt);
				setTfForm(labeltableTel);
				setTfForm(labeltableLand);
				setTfForm(labeltableGeb);
				setTfForm(labeltablePreis);
				setTfForm(labelPreis4);
				
				//Bounds für Objekte werden gesettz und zur Contentpane hinzugefügt
				labelOverviewGast.setBounds(x_column1, y_line1, x_width, y_height);
				contentpane5.add(labelOverviewGast);
				labeltableVorn.setBounds(x_column1, y_line2, 100, y_height);
				contentpane5.add(labeltableVorn);
				labeltableName.setBounds(x_column1, y_line3, 100, y_height);
				contentpane5.add(labeltableName);
				labeltableStr.setBounds(x_column1, y_line4, 100, y_height);
				contentpane5.add(labeltableStr);
				labeltablePlz.setBounds(x_column1, y_line5, 100, y_height);
				contentpane5.add(labeltablePlz);
				labeltableOrt.setBounds(x_column4, y_line2, 100, y_height);
				contentpane5.add(labeltableOrt);
				labeltableLand.setBounds(x_column4, y_line3, 100, y_height);
				contentpane5.add(labeltableLand);
				labeltableGeb.setBounds(x_column4, y_line4, 100, y_height);
				contentpane5.add(labeltableGeb);
				labeltableTel.setBounds(x_column4, y_line5, 100, y_height);
				contentpane5.add(labeltableTel);
				labelVor4.setBounds(x_column3, y_line2, x_width, y_height);
				contentpane5.add(labelVor4);
				labelName4.setBounds(x_column3, y_line3, x_width, y_height);
				contentpane5.add(labelName4);
				labelStr4.setBounds(x_column3, y_line4, x_width, y_height);
				contentpane5.add(labelStr4);
				labelPlz4.setBounds(x_column3, y_line5, x_width, y_height);
				contentpane5.add(labelPlz4);
				labelOrt4.setBounds(x_column5, y_line2, x_width, y_height);
				contentpane5.add(labelOrt4);
				labelLand4.setBounds(x_column5, y_line3, x_width, y_height);
				contentpane5.add(labelLand4);
				labelTel4.setBounds(x_column5, y_line4, x_width, y_height);
				contentpane5.add(labelTel4);
				labelGeb4.setBounds(x_column5, y_line5, x_width, y_height);
				contentpane5.add(labelGeb4);
				labeltablePreis.setBounds(x_column1, y_line10, 100, y_height);
				contentpane5.add(labeltablePreis);
				labelPreis4.setBounds(x_column3, y_line10, x_width, y_height);
				contentpane5.add(labelPreis4);
				
				
				//Bereich gebuchte Zimmerdaten
				//GUI-Objekte werden erstellt
				labelOverviewZimmer = new JLabel("Zimmerbuchungen: ");
				
				labelOverviewZimmer.setBounds(x_column1, y_line6, x_width, y_height);
				contentpane5.add(labelOverviewZimmer);
				
				bookedZimmer2 = BHBookZimmer.getBuchung().getBookedZimmerTable(ButtonHandler.getCon());
				spShowBookedZimmer = new JScrollPane(bookedZimmer2.getSQLTable());
				spShowBookedZimmer.setBounds(x_column1, y_line7, 300, 100);
				contentpane5.add(spShowBookedZimmer);
				
				//Bereich gebuchte Dinestleistungen
				
				labelOverviewDl = new JLabel("Dienstleistungsbuchungen: ");
				
				labelOverviewDl.setBounds(x_column5, y_line6, 180, y_height);
				contentpane5.add(labelOverviewDl);
				
				bookedDl2 = BHBookZimmer.getBuchung().getBookedDlTable(ButtonHandler.getCon());
				spShowBookedDl = new JScrollPane(bookedDl2.getSQLTable());
				spShowBookedDl.setBounds(x_column5, y_line7, 300, 100);
				contentpane5.add(spShowBookedDl);
				
				//Button
				buttonCommit = new JButton("Buchen");
				cancelAll3 = new JButton("Abbrechen");
				
				buttonCommit.setActionCommand("COMMIT");
				buttonCommit.addActionListener(ButtonHandler);
				buttonCommit.setBounds(x_column3, y_line11, x_width, y_height);
				contentpane5.add(buttonCommit);
				
				cancelAll3.setActionCommand("cancel All");
				cancelAll3.addActionListener(ButtonHandler);
				cancelAll3.setBounds(x_column1, y_line11, x_width, y_height);
				contentpane5.add(cancelAll3);

				
				//Hintergrundfarbe
				contentpane5.setOpaque(true);
				contentpane5.setBackground(new Color(209,218,248));
				
				return contentpane5;
	}
	//getter- und setter-Methoden
	public String getQuery() {
		return query;
	}
	
	public String getGidSuche() {
		return jtfGID.getText();
	}
	
	public String getVornameSuche() {
		return jtfVorname.getText();
	}
	
	public String getNameSuche() {
		return jtfName.getText();
	}
	
	public Date getGebSuche() {
		return geb.getDate();
	}
	
	public void setTel(String tel){
		this.tel = tel;
	}
	
	public String getTel() {
		return tel;
	}
	
	public String getVorname() {
		return jtfVorname2.getText();
	}
	
	public String getName() {
		return jtfName2.getText();
	}
	
	public String getStrasse() {
		return jtfStr2.getText();
	}
	
	public String getHn(){
		return jtfHn2.getText();
	}
	
	public String getPlz(){
		return jtfPlz2.getText();
	}
	
	public String getOrt(){
		return jtfOrt2.getText();
	}
	
	public String getLand() {
		return jtfLand2.getText();
	}
	
	public String getTel2_1() {
		return jtfTel2_1.getText();
	}

	public String getTel2_2(){
		return jtfTel2_2.getText();
	}
	
	public String getTel2_3(){
		return jtfTel2_3.getText();
	}
	
	public Date getGeb(){
		return geb2.getDate();
	}
	
	public Date getPickerVon(){
		return pickerVon.getDate();
	}
	
	public Date getPickerBis(){
		return pickerBis.getDate();
	}
	
	public void disableBackButton() {
		back.setVisible(false);
	}
	
	public void addCancelButton() {
		cancel.setEnabled(true);
		cancel.setVisible(true);
	}
	
	public void enableButton(JButton button, boolean b) {
		button.setEnabled(b);
	}
	
	public JButton getNext2(){
		return next2;
	}
	
	public JButton getBookZimmer(){
		return bookZimmer;
	}
	
}
