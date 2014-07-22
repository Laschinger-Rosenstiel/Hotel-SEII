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
import control.ButtonHandlerDataDienst;
import control.JTableview;

public class DataDienst extends GUIHelp implements InterfaceDataDienst 
{
	public JFrame createFrameD, changeFrameD;
	private JButton bChange,bDel, bCreate, bConfirme, bConfirme2;
	public JTextField jtfTyp, jtfPreis, jtfID,jtfTyp2, jtfPreis2, jtfID2;
	public JLabel lTyp,lPreis,lID,lTitel;
	public JTableview jtvDienst;
	public JPanel panelD1;
	public JPanel panelD2;
	public JPanel panelD3;
	public JScrollPane scrollPaneD;
	private int dnr;
	public ButtonHandlerDataDienst control;

	public DataDienst()
	{
		//ButtonHandler
		control = new ButtonHandlerDataDienst(this);
		
		//First JPanel
		bChange = new JButton("Ändern");
		bChange.setActionCommand(ACTION_CHANGE);
		bChange.addActionListener(control);
		bDel = new JButton("Löschen");
		bDel.setActionCommand(ACTION_DELETE);
		bDel.addActionListener(control);
		bCreate = new JButton("Anlegen");
		bCreate.setActionCommand(ACTION_CREATE);
		bCreate.addActionListener(control);
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
		bConfirme.setActionCommand(ACTION_CONFIRM_CREATE);
		bConfirme.addActionListener(control);

		//Change Frame
		changeFrameD = new JFrame();
		bConfirme2 = new JButton("Bestätigen");
		bConfirme2.setActionCommand(ACTION_CONFIRM_CHANGE);
		bConfirme2.addActionListener(control);
		jtfTyp2 = new JTextField(40);
		jtfPreis2 = new JTextField(40);
		jtfID2 = new JTextField(40);

	}

	
	public JPanel launchPanel() {
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

	
	public JFrame launchCreateFrame() {
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

	
	public JFrame launchChangeFrame() {
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
	
	
	//Allgemeiner Setter für die Textfelder
	/* (non-Javadoc)
	 * @see gui.InterfaceDataDienst#setTextTextField(javax.swing.JTextField, java.lang.String)
	 */
	@Override
	public void setTextTextField(JTextField x, String text){
		x.setText(text);
	}

	//Getter für die Textfelder
	/* (non-Javadoc)
	 * @see gui.InterfaceDataDienst#getJtfTyp()
	 */
	@Override
	public JTextField getJtfTyp() {
		return jtfTyp;
	}
	/* (non-Javadoc)
	 * @see gui.InterfaceDataDienst#getJtfPreis()
	 */
	@Override
	public JTextField getJtfPreis() {
		return jtfPreis;
	}
	/* (non-Javadoc)
	 * @see gui.InterfaceDataDienst#getJtfID()
	 */
	@Override
	public JTextField getJtfID() {
		return jtfID;
	}
	/* (non-Javadoc)
	 * @see gui.InterfaceDataDienst#getJtfTyp2()
	 */
	@Override
	public JTextField getJtfTyp2() {
		return jtfTyp2;
	}
	/* (non-Javadoc)
	 * @see gui.InterfaceDataDienst#getJtfPreis2()
	 */
	@Override
	public JTextField getJtfPreis2() {
		return jtfPreis2;
	}
	/* (non-Javadoc)
	 * @see gui.InterfaceDataDienst#getJtfID2()
	 */
	@Override
	public JTextField getJtfID2() {
		return jtfID2;
	}
	
	//Übrige Getter&Setter
	/* (non-Javadoc)
	 * @see gui.InterfaceDataDienst#getCreateFrameD()
	 */
	@Override
	public JFrame getCreateFrameD() {
		return createFrameD;
	}

	/* (non-Javadoc)
	 * @see gui.InterfaceDataDienst#setCreateFrameD(javax.swing.JFrame)
	 */
	@Override
	public void setCreateFrameD(JFrame createFrameD) {
		this.createFrameD = createFrameD;
	}

	/* (non-Javadoc)
	 * @see gui.InterfaceDataDienst#getChangeFrameD()
	 */
	@Override
	public JFrame getChangeFrameD() {
		return changeFrameD;
	}

	/* (non-Javadoc)
	 * @see gui.InterfaceDataDienst#setChangeFrameD(javax.swing.JFrame)
	 */
	@Override
	public void setChangeFrameD(JFrame changeFrameD) {
		this.changeFrameD = changeFrameD;
	}

	/* (non-Javadoc)
	 * @see gui.InterfaceDataDienst#getJtvDienst()
	 */
	@Override
	public JTableview getJtvDienst() {
		return jtvDienst;
	}

	/* (non-Javadoc)
	 * @see gui.InterfaceDataDienst#setJtvDienst(control.JTableview)
	 */
	@Override
	public void setJtvDienst(JTableview jtvDienst) {
		this.jtvDienst = jtvDienst;
	}

	/* (non-Javadoc)
	 * @see gui.InterfaceDataDienst#getPanelD1()
	 */
	@Override
	public JPanel getPanelD1() {
		return panelD1;
	}

	/* (non-Javadoc)
	 * @see gui.InterfaceDataDienst#setPanelD1(javax.swing.JPanel)
	 */
	@Override
	public void setPanelD1(JPanel panelD1) {
		this.panelD1 = panelD1;
	}

	/* (non-Javadoc)
	 * @see gui.InterfaceDataDienst#getPanelD2()
	 */
	@Override
	public JPanel getPanelD2() {
		return panelD2;
	}

	/* (non-Javadoc)
	 * @see gui.InterfaceDataDienst#setPanelD2(javax.swing.JPanel)
	 */
	@Override
	public void setPanelD2(JPanel panelD2) {
		this.panelD2 = panelD2;
	}

	/* (non-Javadoc)
	 * @see gui.InterfaceDataDienst#getPanelD3()
	 */
	@Override
	public JPanel getPanelD3() {
		return panelD3;
	}

	/* (non-Javadoc)
	 * @see gui.InterfaceDataDienst#setPanelD3(javax.swing.JPanel)
	 */
	@Override
	public void setPanelD3(JPanel panelD3) {
		this.panelD3 = panelD3;
	}

	/* (non-Javadoc)
	 * @see gui.InterfaceDataDienst#getScrollPaneD()
	 */
	@Override
	public JScrollPane getScrollPaneD() {
		return scrollPaneD;
	}

	/* (non-Javadoc)
	 * @see gui.InterfaceDataDienst#setScrollPaneD(javax.swing.JScrollPane)
	 */
	@Override
	public void setScrollPaneD(JScrollPane scrollPaneD) {
		this.scrollPaneD = scrollPaneD;
	}

	/* (non-Javadoc)
	 * @see gui.InterfaceDataDienst#getDnr()
	 */
	@Override
	public int getDnr() {
		return dnr;
	}

	/* (non-Javadoc)
	 * @see gui.InterfaceDataDienst#setDnr(int)
	 */
	@Override
	public void setDnr(int dnr) {
		this.dnr = dnr;
	}

	/* (non-Javadoc)
	 * @see gui.InterfaceDataDienst#setJTableview(control.JTableview)
	 */
	@Override
	public void setJTableview(JTableview jtvDienst) {
		this.jtvDienst = jtvDienst;
		
	}

}