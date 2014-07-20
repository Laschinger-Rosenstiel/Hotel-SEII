package gui;
import java.awt.CardLayout;
import java.awt.Color;
import java.util.Date;

import control.BHBook;
import control.JTableview;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

public class BookZimmer extends GUIHelp{
	
	BHBook ButtonHandler = new BHBook(this);
	
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
	private JButton weiter;
	public JDateChooser geb2;
	
	//Objekte für 2. Card des JFrame werden erstellt
	public JPanel contentpane3 = null;
	public JLabel labeltable4, labeltable5, labeltable6, labeljtfVon, labeljtfBis;
	private JTextField labelVor3, labelName3, labelStr3, labelPlz3, labelOrt3, labelLand3;
	private String tel;
	public JTextField labelTel3;
	private JTextField labelGeb3;
	public JTextField labelVor3_2, labelName3_2, labelStr3_2, labelPlz3_2, labelOrt3_2, labelLand3_2, labelTel3_2, labelGeb3_2;
	private JButton checkAvailability, back, bookZimmer, next, cancel, next2;
	public JDateChooser pickerVon, pickerBis;
	public JTableview availableZimmer, bookedZimmer;
	public JScrollPane scrollPaneZimmer;

	//Objekte für 3. Card des JFrame werden erstellt
	public JPanel contentpane4 = null;
	public JLabel labelDl, labelDatum;
	public JTableview showDl;
	public JScrollPane scrollPaneShow;
	public JDateChooser bookDateDl;
	private JButton bookDl, cancelDl, cancelAll2;
	
	//
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
		jb2.addActionListener(new BHBook(this));
		jb3.setActionCommand("SEARCH");
		jb3.addActionListener(new BHBook(this));
		
		//Query für SQL-Tabelle auf Startpanel wird gesetzt
		query = "Select * From gast";
		
		//Tabelle für bebuchbare Gäste wird erstellt (query, s.o.), Koordinaten gesetzt und zugeordnet
		sucheGast = new JTableview(query);
		JTable suche = sucheGast.getSQLTable();
		scrollPaneSuche = new JScrollPane(suche);
		
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
		scrollPaneSuche.setBounds(x_column1, y_line9, 1000, 200);
		contentpane1.add(scrollPaneSuche);
		
		//Color
		contentpane1.setOpaque(true);
		contentpane1.setBackground(new Color(209,218,248));
		//Contentpane wird zurückgegeben
		return contentpane1;
	}

	public void launchJFrame() {
		//JFrame für Zimmer-Buchungsfenster wird erzeugt, contentpane2 wird erzeugt, Layout auf null	
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
		
		weiter = new JButton("Weiter...");
		
		//ActionListener und ActionCommand wird gesetzt
		weiter.setActionCommand("NEXT");
		weiter.addActionListener(new BHBook(this));
		
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
		
		weiter.setBounds(x_column1, y_line10, x_width, y_height);
		contentpane2.add(weiter);
		
		//Hintergrundfarbe wird gesetzt
		contentpane2.setOpaque(true);
		contentpane2.setBackground(new Color(209,218,248));
		
		//contentpane wird zu CardLayout hinzugefügt
		card.add("Card1", contentpane2);
		contentPane.add(card);
		
		//Frame wird visible gesetzt, größe angepasst und Close-Option gesetzt
		jf.setVisible(true);
		jf.setResizable(true);
		jf.setSize(700,500);
		jf.setLocation(300,50);
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		labeljtfVon = new JLabel("Von:");
		labeljtfBis = new JLabel("Bis:");
		
		pickerVon = new JDateChooser();
		pickerBis = new JDateChooser();
		
		checkAvailability = new JButton("Verügbare Zimmer anzeigen...");
		bookZimmer = new JButton("Zimmer buchen");
		next = new JButton("Weiter...");
		back = new JButton("Zurück");
		cancel = new JButton("Abbrechen");
		
		
		//ActionListener und ActionCommand für Buttons werden gesetzt
		checkAvailability.setActionCommand("Available?");
		checkAvailability.addActionListener(new BHBook(this));
		bookZimmer.setActionCommand("BOOK ZIMMER");
		bookZimmer.addActionListener(new BHBook(this));
		back.setActionCommand("BACK");
		back.addActionListener(new BHBook(this));
		next.setActionCommand("NEXT2");
		next.addActionListener(new BHBook(this));
		cancel.setActionCommand("CANCEL");
		cancel.addActionListener(new BHBook(this));
		
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
		labeljtfVon.setBounds(x_column1, y_line7, 40, y_height);
		contentpane3.add(labeljtfVon);	
		labeljtfBis.setBounds(x_column1, y_line8, 40, y_height);
		contentpane3.add(labeljtfBis);		
		pickerVon.setBounds(x_column2, y_line7, 100, y_height);
		contentpane3.add(pickerVon);
		pickerBis.setBounds(x_column2, y_line8, 100, y_height);
		contentpane3.add(pickerBis);
		checkAvailability.setBounds(x_column1, y_line9, x_width, y_height);
		contentpane3.add(checkAvailability);
		bookZimmer.setBounds(x_column4, y_line10, x_width, y_height);
		contentpane3.add(bookZimmer);
		back.setBounds(x_column1, y_line10, x_width, y_height);
		contentpane3.add(back);
		cancel.setBounds(x_column1, y_line10, x_width, y_height);
		contentpane3.add(cancel);
		cancel.setEnabled(false);
		cancel.setVisible(false);
		next.setBounds(x_column3, y_line10, x_width, y_height);
		contentpane3.add(next);
		
		//Hintergrundfarbe wird gesetzt
		contentpane3.setOpaque(true);
		contentpane3.setBackground(new Color(209,218,248));
		
		//contentpane wird zurückgegeben
		return contentpane3;
	}
	
	public JPanel launchThird() {
		//contenpane3 wird invisible gesetzt
		contentpane3.setVisible(false);
		//JPanel contentpane wird erzeugt, Layout wird auf null gesetzt
		contentpane4 = new JPanel();
		contentpane4.setLayout(null);
		
		//GUI-Objekte werden erzeugt
		labelDl = new JLabel("Dienstleistung buchen:");
		labelDatum = new JLabel("Buchungsdatum: ");
		showDl = new JTableview("select * from dienstleistung");
		JTable show = showDl.getSQLTable();
		bookDateDl = new JDateChooser();
		scrollPaneShow = new JScrollPane(show);
		bookDl = new JButton("Buchen");
		cancelDl=new JButton("Dienstleistungsbuchung beenden");
		cancelAll2=new JButton("Abbrechen");
		bookDateDl = new JDateChooser();
		
		//Datumsrauf für JDateChooser wird gesetzt
		bookDateDl.setSelectableDateRange(pickerVon.getDate(), pickerBis.getDate());
		
		//ActionListener und ActionCommands werden für die Buttons gesetzt
		bookDl.addActionListener(new BHBook(this));
		bookDl.setActionCommand("Dl hinzufügen");
		cancelDl.addActionListener(new BHBook(this));
		cancelDl.setActionCommand("Dl cancel");
		cancelAll2.addActionListener(new BHBook(this));
		cancelAll2.setActionCommand("cancel All");
		
		//Koordinaten, Größe wird gesetzt und Objekte zu contentpane hinzugefügt
		labelDl.setBounds(x_column1, y_line1, x_width, y_height);
		contentpane4.add(labelDl);
		labelDatum.setBounds(x_column4, y_line1, x_width, y_height);
		contentpane4.add(labelDatum);
		bookDateDl.setBounds(x_column4, y_line2, x_width, y_height);
		contentpane4.add(bookDateDl);
		scrollPaneShow.setBounds(x_column1, y_line2, 200, 200);
		contentpane4.add(scrollPaneShow);
		bookDl.setBounds(x_column4, y_line8, x_width, y_height);
		contentpane4.add(bookDl);
		cancelDl.setBounds(x_column1, y_line8, 250, y_height);
		contentpane4.add(cancelDl);
		cancelAll2.setBounds(x_column6, y_line8, x_width, y_height);
		contentpane4.add(cancelAll2);
		
		//Hintergrundfarbe
		contentpane4.setOpaque(true);
		contentpane4.setBackground(new Color(209,218,248));
		
		//Contentpane wird zurückgegeben
		return contentpane4;
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
	
}
