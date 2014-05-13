package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.ScrollPane;

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

public class DataZimmer extends GUIHelp
{
	public JFrame createFrame, changeFrameZ;
	private JButton bChange,bDel, bCreate, bConfirme,bConfirme2;
	public JTextField jtfZnr, jtfPreis,jtfZnr2,jtfPreis2; 
	public JComboBox cb, cb2;
	public JLabel l1,l2,l3,l4,lTyp2,lPreis2,lZnr2;
	public String[] typ ={"Einzelzimmer","Doppelzimmer"};
	public JTableview jtv;
	public JPanel panelZ1;
	public JPanel panelZ2;
	public JPanel panelZ3;
	public JScrollPane scrollPaneZ;
	public JTable abc;
	private String x;
	private String zid;

	public DataZimmer()
	{
		//JPanel
		//jf = new JFrame();
		bChange = new JButton("Ändern");
		bChange.setActionCommand("ChangeZimmer");
		bChange.addActionListener(new ButtonHandlerData(this));
		bDel = new JButton("Löschen");
		bDel.setActionCommand("DeleteZimmer");
		bDel.addActionListener(new ButtonHandlerData(this));
		bCreate = new JButton("Anlegen");
		bCreate.setActionCommand("CreateZimmer");
		bCreate.addActionListener(new ButtonHandlerData(this));
		l4 = new JLabel("Stammdaten Zimmer:", JLabel.LEFT);

		//CreateJFrame
		createFrame = new JFrame();
		cb = new JComboBox(typ);
		jtfZnr = new JTextField(40);
		jtfPreis = new JTextField(40);
		l1 = new JLabel("Zimmertyp:", JLabel.LEFT);
		l2 = new JLabel("Preis:", JLabel.LEFT);
		l3 = new JLabel("Zimmernr:", JLabel.LEFT);
		bConfirme = new JButton("Bestätigen");
		bConfirme.setActionCommand("ConfirmeCreateZimmer");
		bConfirme.addActionListener(new ButtonHandlerData(this));

		//Change Frame
		changeFrameZ = new JFrame();
		bConfirme2 = new JButton("Bestätigen");
		bConfirme2.setActionCommand("ConfirmeChangeZimmer");
		bConfirme2.addActionListener(new ButtonHandlerData(this));
		lTyp2 = new JLabel("Zimmer:", JLabel.LEFT);
		lPreis2 = new JLabel("Preis:", JLabel.LEFT);
		lZnr2 = new JLabel("Zimmernummer:", JLabel.LEFT);
		jtfZnr2 = new JTextField(40);
		jtfPreis2 = new JTextField(40);
		cb2 = new JComboBox(typ);

	}

	public JPanel launchJPanel()
	{
		//Panel wird erzeugt und befüllt
		panelZ1 = new JPanel();
		panelZ1.setLayout(null);
		jtv = new JTableview("Select * from zimmer");
		//Tabelle wird erzeugt und mit allen Zimmerdaten aus der DB befüllt
		JTable abc = jtv.getSQLTable();
		scrollPaneZ = new JScrollPane(abc); 
		scrollPaneZ.setBounds(x_column1, y_line3, 600, 300); 
		panelZ1.add(scrollPaneZ);
		l4.setBounds(x_column1, y_line1, x_width, y_height);
		panelZ1.add(l4);
		bChange.setBounds(x_column1, y_line13, x_width, y_height);
		panelZ1.add(bChange);
		bDel.setBounds(x_column3, y_line13, x_width, y_height);
		panelZ1.add(bDel);
		bCreate.setBounds(x_column4, y_line13, x_width, y_height);
		panelZ1.add(bCreate);
		panelZ1.setOpaque(true);
		panelZ1.setBackground(new Color(209,218,248));

		return panelZ1;
	}

	public JFrame launchCreateFrame()
	{
		createFrame = null;
		panelZ2 = null;
		//Panel wird erzeugt und befüllt
		panelZ2 = new JPanel();
		panelZ2.setLayout(null);
		panelZ2.setVisible(false);
		l1.setBounds(x_column1, y_line2, x_width, y_height);
		panelZ2.add(l1);
		cb.setBounds(x_column3, y_line2, x_width, y_height);
		panelZ2.add(cb);
		l2.setBounds(x_column1, y_line3, x_width, y_height);
		panelZ2.add(l2);
		jtfPreis.setBounds(x_column3, y_line3, x_width, y_height);
		panelZ2.add(jtfPreis);
		l3.setBounds(x_column1, y_line4, x_width, y_height);
		panelZ2.add(l3);
		jtfZnr.setBounds(x_column3, y_line4, x_width, y_height);
		panelZ2.add(jtfZnr);
		bConfirme.setBounds(x_column3, y_line5, x_width, y_height);
		panelZ2.add(bConfirme);
		panelZ2.setOpaque(true);
		panelZ2.setBackground(new Color(209,218,248));
		panelZ2.setVisible(true);
		//Panel wird dem Frame übergeben
		createFrame = new JFrame();
		createFrame.add(panelZ2);
		createFrame.setLocation(300, 50);
		createFrame.setSize(600,500);
		createFrame.setDefaultCloseOperation(createFrame.DISPOSE_ON_CLOSE);
		createFrame.setVisible(true);

		return createFrame;
	}

	public JFrame launchChangeFrameZ(String id,String typ,String preis)
	{
		changeFrameZ = null;
		panelZ3 = null;
		x = typ;
		zid = id;
		//Panel wird erzeugt und befüllt
		JPanel panelZ3 = new JPanel();
		panelZ3.setLayout(null);
		panelZ3.setVisible(false);
		lTyp2.setBounds(x_column1, y_line1, x_width, y_height);
		panelZ3.add(lTyp2);
		cb2.setBounds(x_column3, y_line1, x_width, y_height);
		panelZ3.add(cb2);
		lPreis2.setBounds(x_column1, y_line2, x_width, y_height);
		panelZ3.add(lPreis2);
		jtfPreis2.setBounds(x_column3, y_line2, x_width, y_height);
		jtfPreis2.setText(preis);
		panelZ3.add(jtfPreis2);
		lZnr2.setBounds(x_column1, y_line3, x_width, y_height);
		panelZ3.add(lZnr2);
		jtfZnr2.setBounds(x_column3, y_line3, x_width, y_height);
		jtfZnr2.setText(id);
		panelZ3.add(jtfZnr2);
		bConfirme2.setBounds(x_column3, y_line4, x_width, y_height);
		panelZ3.add(bConfirme2);
		panelZ3.setOpaque(true);
		panelZ3.setBackground(new Color(209,218,248));
		panelZ3.setVisible(true);
		//Panel wird dem Frame übergeben
		changeFrameZ = new JFrame();
		changeFrameZ.add(panelZ3);
		changeFrameZ.setSize(600,500);
		changeFrameZ.setLocation(300, 50);
		changeFrameZ.setDefaultCloseOperation(changeFrameZ.DISPOSE_ON_CLOSE);
		changeFrameZ.setVisible(true);

		return changeFrameZ;
	}
	
	public String getZID()
	{
		return zid;
	}
	public void setZID(String zid)
	{
		this.zid = zid;
	}

}