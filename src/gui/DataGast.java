package gui;



import java.awt.CardLayout;
import java.awt.Color;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import control.ButtonHandlerData;
import control.ButtonHandlerDataGast;
import control.JTableview;

public class DataGast extends GUIHelp implements InterfaceDataGast  {

	public ButtonHandlerDataGast control;


	public JFrame changeFrameG;
	private JButton bChange,bSearch, bDel, bConfirme;
	public JTextField jtfVn, jtfName,jtfVn2,jtfName2,jtfGeb2,jtfStr,jtfHnr,jtfPlz,jtfOrt,jtfLand,jtfTel;
	private JLabel lVn,lName,lGeb,lTitel,lVn2,lName2,lGeb2,lStr,lHnr,lPlz,lOrt,lLand,lTel;
	public JTableview jtvGast;
	public JPanel panelG1, panelG2;
	public JScrollPane scrollPaneG;
	public String id;
	public JDateChooser Geb;


	public DataGast()
	{
		//ButtonHandler
		control = new ButtonHandlerDataGast(this);
		// JPanel
		bChange = new JButton("Ändern");
		bChange.setActionCommand(ACTION_CHANGE);
		bChange.addActionListener(control);
		bSearch = new JButton("Suchen");
		bSearch.setActionCommand(ACTION_SEARCH);
		bSearch.addActionListener(control);
		bDel = new JButton("Löschen");
		bDel.setActionCommand(ACTION_DELETE);
		bDel.addActionListener(control);
		jtfVn = new JTextField(40);
		jtfName = new JTextField(40);
		Geb = new JDateChooser();
		lVn = new JLabel("Vorname:", JLabel.LEFT);
		lName = new JLabel("Name:", JLabel.LEFT);
		lGeb = new JLabel("Geburtsdatum:", JLabel.LEFT);
		lTitel = new JLabel("Gastsuche:", JLabel.LEFT);
		jtvGast = new JTableview("Select * From gast");

		//ChangeFrame
		changeFrameG = new JFrame();
		bConfirme = new JButton("Bestätigen");
		bConfirme.setActionCommand(ACTION_CONFIRM);
		bConfirme.addActionListener(control);
		jtfVn2 = new JTextField(40);
		jtfName2 = new JTextField(40);
		jtfGeb2 = new JTextField(40);
		jtfStr = new JTextField(40);
		jtfHnr = new JTextField(40);
		jtfPlz = new JTextField(40);
		jtfOrt = new JTextField(40);
		jtfLand = new JTextField(40);
		jtfTel = new JTextField(40);
		lVn2 = new JLabel("Vorname:", JLabel.LEFT);
		lName2 = new JLabel("Name:", JLabel.LEFT);
		lGeb2 = new JLabel("Geburtsdatum:", JLabel.LEFT);
		lStr = new JLabel("Strasse:", JLabel.LEFT);
		lHnr = new JLabel("Hausnummer:", JLabel.LEFT);
		lPlz = new JLabel("PLZ:", JLabel.LEFT);
		lOrt = new JLabel("Ort:", JLabel.LEFT);
		lLand = new JLabel("Land:", JLabel.LEFT);
		lTel = new JLabel("Telefon:", JLabel.LEFT);
	}

	public JPanel launchPanel() {
		//Panel wird erzeugt und befüllt
		panelG1 = new JPanel();
		panelG1.setLayout(null);
		lVn.setBounds(x_column1, y_line2, x_width, y_height);
		panelG1.add(lVn);
		lName.setBounds(x_column1, y_line3, x_width, y_height);
		panelG1.add(lName);
		lGeb.setBounds(x_column1, y_line4, x_width, y_height);
		panelG1.add(lGeb);
		lTitel.setBounds(x_column1, y_line1, x_width, y_height);
		panelG1.add(lTitel);
		jtfVn.setBounds(x_column3, y_line2, x_width, y_height);
		panelG1.add(jtfVn);
		jtfName.setBounds(x_column3, y_line3, x_width, y_height);
		panelG1.add(jtfName);
		Geb.setBounds(x_column3, y_line4, x_width, y_height);
		panelG1.add(Geb);
		//Tabelle erzeugt und mit Gastdaten befüllt
		JTable abc = jtvGast.getSQLTable();
		scrollPaneG = new JScrollPane(abc); 
		scrollPaneG.setBounds(x_column1, y_line5, 600, 300); 
		panelG1.add(scrollPaneG);
		bChange.setBounds(x_column1, y_line13, x_width, y_height);
		panelG1.add(bChange);
		bSearch.setBounds(x_column3, y_line13, x_width, y_height);
		panelG1.add(bSearch);
		bDel.setBounds(x_column4, y_line13, x_width, y_height);
		panelG1.add(bDel);
		panelG1.setOpaque(true);
		panelG1.setBackground(new Color(209,218,248));

		return panelG1;
	}

	public JFrame launchChangeFrame() {
		changeFrameG = null;
		panelG2 = null;
		//Panel wird erzeugt und befüllt
		JPanel panelG2 = new JPanel();
		panelG2.setLayout(null);
		panelG2.setVisible(false);
		lVn2.setBounds(x_column1, y_line1, x_width, y_height);
		panelG2.add(lVn2);
		jtfVn2.setBounds(x_column3, y_line1, x_width, y_height);
		panelG2.add(jtfVn2);
		lName2.setBounds(x_column1, y_line2, x_width, y_height);
		panelG2.add(lName2);
		jtfName2.setBounds(x_column3, y_line2, x_width, y_height);
		panelG2.add(jtfName2);
		lStr.setBounds(x_column1, y_line3, x_width, y_height);
		panelG2.add(lStr);
		jtfStr.setBounds(x_column3, y_line3, x_width, y_height);
		panelG2.add(jtfStr);
		lHnr.setBounds(x_column1, y_line4, x_width, y_height);
		panelG2.add(lHnr);
		jtfHnr.setBounds(x_column3, y_line4, x_width, y_height);
		panelG2.add(jtfHnr);
		lPlz.setBounds(x_column1, y_line5, x_width, y_height);
		panelG2.add(lPlz);
		jtfPlz.setBounds(x_column3, y_line5, x_width, y_height);
		panelG2.add(jtfPlz);
		lOrt.setBounds(x_column1, y_line6, x_width, y_height);
		panelG2.add(lOrt);
		jtfOrt.setBounds(x_column3, y_line6, x_width, y_height);
		panelG2.add(jtfOrt);
		lLand.setBounds(x_column1, y_line7, x_width, y_height);
		panelG2.add(lLand);
		jtfLand.setBounds(x_column3, y_line7, x_width, y_height);
		panelG2.add(jtfLand);
		lGeb2.setBounds(x_column1, y_line8, x_width, y_height);
		panelG2.add(lGeb2);
		jtfGeb2.setBounds(x_column3, y_line8, x_width, y_height);
		panelG2.add(jtfGeb2);
		lTel.setBounds(x_column1, y_line9, x_width, y_height);
		panelG2.add(lTel);
		jtfTel.setBounds(x_column3, y_line9, x_width, y_height);
		panelG2.add(jtfTel);
		bConfirme.setBounds(x_column3, y_line10, x_width, y_height);
		panelG2.add(bConfirme);
		panelG2.setOpaque(true);
		panelG2.setBackground(new Color(209,218,248));
		panelG2.setVisible(true);
		//Panel wird dem Frame übergeben
		changeFrameG = new JFrame();
		changeFrameG.add(panelG2);
		changeFrameG.setSize(700,600);
		changeFrameG.setLocation(300, 50);
		changeFrameG.setDefaultCloseOperation(changeFrameG.DISPOSE_ON_CLOSE);
		changeFrameG.setVisible(true);

		return changeFrameG;
	}




	//Algemeiner Setter für Textfelder
	
	public void setTextTextField(JTextField x,String text) {
		x.setText(text);
	}


	//Getter Textfelder
	
	public JTextField getJtfVn() {
		return jtfVn;
	}

	public JTextField getJtfName() {
		return jtfName;
	}

	public JTextField getJtfVn2() {
		return jtfVn2;
	}

	public JTextField getJtfName2() {
		return jtfName2;
	}

	public JTextField getJtfGeb2() {
		return jtfGeb2;
	}

	public JTextField getJtfStr() {
		return jtfStr;
	}

	public JTextField getJtfHnr() {
		return jtfHnr;
	}

	public JTextField getJtfPlz() {
		return jtfPlz;
	}

	public JTextField getJtfOrt() {
		return jtfOrt;
	}

	public JTextField getJtfLand() {
		return jtfLand;
	}
	
	public JTextField getJtfTel() {
		return jtfTel;
	}


	//Getter&Setter Tableview
	
	public JTableview getJTableview() {
		return jtvGast;
	}
	public void setJTableview(JTableview jtvGast){
		this.jtvGast = jtvGast;
	}


	






	//Übrige Setter&Getter


	public JPanel getPanelG1() {
		return panelG1;
	}
	public void setPanelG1(JPanel panelG1) {
		this.panelG1 = panelG1;
	}

	public JPanel getPanelG2() {
		return panelG2;
	}
	public void setPanelG2(JPanel panelG2) {
		this.panelG2 = panelG2;
	}

	public JScrollPane getScrollPaneG() {
		return scrollPaneG;
	}
	public void setScrollPaneG(JScrollPane scrollPaneG) {
		this.scrollPaneG = scrollPaneG;
	}
	
	public void setChangeFrameG(JFrame changeFrameG) {
		this.changeFrameG = changeFrameG;
	}
	public JFrame getChangeFrameG() {
		return changeFrameG;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public JDateChooser getGeb() {
		return Geb;
	}
	public void setGeb(JDateChooser geb) {
		Geb = geb;
	}




}