package gui;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import control.ButtonHandlerData;
import control.JTableview;

public class DataDienst extends GUIHelp
{
	public JFrame createFrameD, changeFrameD;
	private JButton bChange,bDel, bCreate, bConfirme, bConfirme2;
	public JTextField jtfTyp, jtfPreis, jtfID,jtfTyp2, jtfPreis2, jtfID2;
	public JLabel lTyp,lPreis,lID,lTitel;
	public JTableview jtvDienst;
	private String x = "Dienst";
	public JPanel panelD1;
	public JPanel panelD2;
	public JPanel panelD3;
	public JScrollPane scrollPaneD;
	private int dnr;
	
	public DataDienst()
	{
		//First JPanel
		bChange = new JButton("Ändern");
		bChange.setActionCommand("ChangeDienst");
		bChange.addActionListener(new ButtonHandlerData(this));
		bDel = new JButton("Löschen");
		bDel.setActionCommand("DeleteDienst");
		bDel.addActionListener(new ButtonHandlerData(this));
		bCreate = new JButton("Anlegen");
		bCreate.setActionCommand("CreateDienst");
		bCreate.addActionListener(new ButtonHandlerData(this));
		lTitel = new JLabel("Dienstleistungen:", JLabel.LEFT);
		jtvDienst = new JTableview("Select * from dienstleistung");
		
		//Create JFrame
		createFrameD = new JFrame();
		jtfTyp = new JTextField(40);
		jtfPreis = new JTextField(40);
		jtfID = new JTextField(40);
		lTyp = new JLabel("Dienstleistung:", JLabel.LEFT);
		lPreis = new JLabel("Preis:", JLabel.LEFT);
		lID = new JLabel("ID:", JLabel.LEFT);
		bConfirme = new JButton("Bestätigen");
		bConfirme.setActionCommand("ConfirmeCreateDienst");
		bConfirme.addActionListener(new ButtonHandlerData(this));
		
		//Change Frame
		changeFrameD = new JFrame();
		bConfirme2 = new JButton("Bestätigen");
		bConfirme2.setActionCommand("ConfirmeChangeDienst");
		bConfirme2.addActionListener(new ButtonHandlerData(this));
		jtfTyp2 = new JTextField(40);
		jtfPreis2 = new JTextField(40);
		jtfID2 = new JTextField(40);
		
	}
	
	public JPanel launchJPanel()
	{
		//Panel wird erzeugt und befüllt
		panelD1 = new JPanel();
		panelD1.setLayout(null);
		jtvDienst = new JTableview("Select * from dienstleistung");
		//Tabelle erzeugen und mit den von der DB gelesenen Dienstleistungen befüllen
		JTable dienstTab = jtvDienst.getSQLTable();
		scrollPaneD = new JScrollPane(dienstTab);
		scrollPaneD.setBounds(x_column1, y_line3, 600, 300); 
		panelD1.add(scrollPaneD);
		lTitel.setBounds(x_column1, y_line1, x_width, y_height);
		lTitel.setBounds(x_column1, y_line1, x_width, y_height);
		panelD1.add(lTitel);
		bChange.setBounds(x_column1, y_line13, x_width, y_height);
		panelD1.add(bChange);
		bDel.setBounds(x_column3, y_line13, x_width, y_height);
		panelD1.add(bDel);
		bCreate.setBounds(x_column4, y_line13, x_width, y_height);
		panelD1.add(bCreate);
		
		panelD1.setOpaque(true);
		panelD1.setBackground(new Color(209,218,248));
		
		return panelD1;
	}
	
	public JFrame launchCreateFrameD()
	{
		createFrameD = null;
		panelD2 = null;
		//Panel wird erzeugt und befüllt
		JPanel panelD2 = new JPanel();
		panelD2.setLayout(null);
		panelD2.setVisible(false);
		lTyp.setBounds(x_column1, y_line1, x_width, y_height);
		panelD2.add(lTyp);
		jtfTyp.setBounds(x_column3, y_line1, x_width, y_height);
		panelD2.add(jtfTyp);
		lPreis.setBounds(x_column1, y_line2, x_width, y_height);
		panelD2.add(lPreis);
		jtfPreis.setBounds(x_column3, y_line2, x_width, y_height);
		panelD2.add(jtfPreis);
		//lID.setBounds(x_column1, y_line3, x_width, y_height);
		//panelD2.add(lID);
	//	jtfID.setBounds(x_column3, y_line3, x_width, y_height);
		//panelD2.add(jtfID);
		bConfirme.setBounds(x_column3, y_line4, x_width, y_height);
		panelD2.add(bConfirme);
		panelD2.setOpaque(true);
		panelD2.setBackground(new Color(209,218,248));
		panelD2.setVisible(true);
		//Panel wird ans Frame übergeben
		createFrameD = new JFrame();
		createFrameD.add(panelD2);
		createFrameD.setSize(600,500);
		createFrameD.setLocation(300, 50);
		createFrameD.setDefaultCloseOperation(createFrameD.DISPOSE_ON_CLOSE);
		createFrameD.setVisible(true);
		
		return createFrameD;
	}
	
	public JFrame launchChangeFrameD(String id, String preis, String typ)
	{
		changeFrameD = null;
		panelD3 = null;
		//Panel wird erzeugt und befüllt
		JPanel panelD3 = new JPanel();
		panelD3.setLayout(null);
		panelD3.setVisible(false);
		lTyp.setBounds(x_column1, y_line1, x_width, y_height);
		panelD3.add(lTyp);
		jtfTyp2.setBounds(x_column3, y_line1, x_width, y_height);
		panelD3.add(jtfTyp2);
		lPreis.setBounds(x_column1, y_line2, x_width, y_height);
		panelD3.add(lPreis);
		jtfPreis2.setBounds(x_column3, y_line2, x_width, y_height);
		panelD3.add(jtfPreis2);
		lID.setBounds(x_column1, y_line3, x_width, y_height);
		panelD3.add(lID);
		jtfID2.setBounds(x_column3, y_line3, x_width, y_height);
		setTfForm(jtfID2);
		panelD3.add(jtfID2);
		bConfirme2.setBounds(x_column3, y_line4, x_width, y_height);
		panelD3.add(bConfirme2);
		jtfID2.setText(id);
		jtfTyp2.setText(typ);
		jtfPreis2.setText(preis);
		dnr = Integer.parseInt(id);
		panelD3.setOpaque(true);
		panelD3.setBackground(new Color(209,218,248));
		panelD3.setVisible(true);
		//Panel wird ans Frame übergeben
		changeFrameD = new JFrame();
		changeFrameD.add(panelD3);
		changeFrameD.setSize(600,500);
		changeFrameD.setLocation(300, 50);
		changeFrameD.setDefaultCloseOperation(changeFrameD.DISPOSE_ON_CLOSE);
		changeFrameD.setVisible(true);
		
		return changeFrameD;
	}
	public int getX()
	{
		return dnr;
	}
	public void setX(int y)
	{
		dnr=y;
	}
}