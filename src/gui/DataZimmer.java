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

import control.BHDataZimmer;
import control.JTableview;

public class DataZimmer extends GUIHelp implements InterfaceDataZimmer 
{
	public JFrame createFrame, changeFrameZ;
	private JButton bChange,bDel, bCreate, bConfirme,bConfirme2;
	public JTextField jtfZnr, jtfPreis,jtfZnr2,jtfPreis2; 
	@SuppressWarnings("rawtypes")
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
	BHDataZimmer control;

	@SuppressWarnings("rawtypes")
	public DataZimmer()
	{
		//ButtonHandler
		control = new BHDataZimmer(this);

		//JPanel
		//jf = new JFrame();
		bChange = new JButton("Ändern");
		bChange.setActionCommand(ACTION_CHANGE);
		bChange.addActionListener(control);
		bDel = new JButton("Löschen");
		bDel.setActionCommand(ACTION_DELETE);
		bDel.addActionListener(control);
		bCreate = new JButton("Anlegen");
		bCreate.setActionCommand(ACTION_CREATE);
		bCreate.addActionListener(control);
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
		bConfirme.setActionCommand(ACTION_CONFIRM_CREATE);
		bConfirme.addActionListener(control);

		//Change Frame
		changeFrameZ = new JFrame();
		bConfirme2 = new JButton("Bestätigen");
		bConfirme2.setActionCommand(ACTION_CONFIRM_CHANGE);
		bConfirme2.addActionListener(control);
		lTyp2 = new JLabel("Zimmer:", JLabel.LEFT);
		lPreis2 = new JLabel("Preis:", JLabel.LEFT);
		lZnr2 = new JLabel("Zimmernummer:", JLabel.LEFT);
		jtfZnr2 = new JTextField(40);
		jtfPreis2 = new JTextField(40);
		cb2 = new JComboBox(typ);

	}

	public JPanel launchPanel() {
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

	public JFrame launchChangeFrame() {
		changeFrameZ = null;
		panelZ3 = null;
		//x = typ;
		//zid = id;
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
		panelZ3.add(jtfPreis2);
		lZnr2.setBounds(x_column1, y_line3, x_width, y_height);
		panelZ3.add(lZnr2);
		jtfZnr2.setBounds(x_column3, y_line3, x_width, y_height);
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

	public void setTextTextField(JTextField x, String text){
		x.setText(text);
	}

	//Getter Textfield
	public JTextField getJtfZnr() {
		return jtfZnr;
	}
	
	public JTextField getJtfPreis() {
		return jtfPreis;
	}
	
	public JTextField getJtfZnr2() {
		return jtfZnr2;
	}
	
	public JTextField getJtfPreis2() {
		return jtfPreis2;
	}

	//Übrigen Getter&Setter	
	public JFrame getCreateFrame() {
		return createFrame;
	}

	public void setCreateFrame(JFrame createFrame) {
		this.createFrame = createFrame;
	}

	public JFrame getChangeFrameZ() {
		return changeFrameZ;
	}

	public void setChangeFrameZ(JFrame changeFrameZ) {
		this.changeFrameZ = changeFrameZ;
	}

	public JComboBox getCb() {
		return cb;
	}

	public void setCb(JComboBox cb) {
		this.cb = cb;
	}

	public JComboBox getCb2() {
		return cb2;
	}

	public void setCb2(JComboBox cb2) {
		this.cb2 = cb2;
	}

	public String[] getTyp() {
		return typ;
	}

	public void setTyp(String[] typ) {
		this.typ = typ;
	}

	public JTableview getJtv() {
		return jtv;
	}

	public void setJtv(JTableview jtv) {
		this.jtv = jtv;
	}

	public JPanel getPanelZ1() {
		return panelZ1;
	}

	public void setPanelZ1(JPanel panelZ1) {
		this.panelZ1 = panelZ1;
	}

	public JPanel getPanelZ2() {
		return panelZ2;
	}

	public void setPanelZ2(JPanel panelZ2) {
		this.panelZ2 = panelZ2;
	}

	public JPanel getPanelZ3() {
		return panelZ3;
	}

	public void setPanelZ3(JPanel panelZ3) {
		this.panelZ3 = panelZ3;
	}

	public JScrollPane getScrollPaneZ() {
		return scrollPaneZ;
	}

	public void setScrollPaneZ(JScrollPane scrollPaneZ) {
		this.scrollPaneZ = scrollPaneZ;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getZid() {
		return zid;
	}

	public void setZid(String zid) {
		this.zid = zid;
	}
	public JTable getAbc() {
		return abc;
	}

	public void setAbc(JTable abc) {
		this.abc = abc;
	}

}