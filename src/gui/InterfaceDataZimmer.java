package gui;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import control.JTableview;

public interface InterfaceDataZimmer {
	
	public final static String ACTION_CREATE = "Create"; 
	public final static String ACTION_CHANGE = "Change"; 
	public final static String ACTION_DELETE = "Delete";
	public final static String ACTION_CONFIRM_CHANGE = "Confirme_Change";
	public final static String ACTION_CONFIRM_CREATE = "Confirme_Create";


	public final static String kennung = "Zimmer";

	public abstract JPanel launchPanel();

	public abstract JFrame launchCreateFrame();

	public abstract JFrame launchChangeFrame();

	//Allgemeiner Setter Textfelder
	public abstract void setTextTextField(JTextField x, String text);

	//Getter Textfield
	public abstract JTextField getJtfZnr();

	public abstract JTextField getJtfPreis();

	public abstract JTextField getJtfZnr2();

	public abstract JTextField getJtfPreis2();

	//Übrigen Getter&Setter	
	public abstract JFrame getCreateFrame();

	public abstract void setCreateFrame(JFrame createFrame);

	public abstract JFrame getChangeFrameZ();

	public abstract void setChangeFrameZ(JFrame changeFrameZ);

	public abstract JComboBox getCb();

	public abstract void setCb(JComboBox cb);

	public abstract JComboBox getCb2();

	public abstract void setCb2(JComboBox cb2);

	public abstract String[] getTyp();

	public abstract void setTyp(String[] typ);

	public abstract JTableview getJtv();

	public abstract void setJtv(JTableview jtv);

	public abstract JPanel getPanelZ1();

	public abstract void setPanelZ1(JPanel panelZ1);

	public abstract JPanel getPanelZ2();

	public abstract void setPanelZ2(JPanel panelZ2);

	public abstract JPanel getPanelZ3();

	public abstract void setPanelZ3(JPanel panelZ3);

	public abstract JScrollPane getScrollPaneZ();

	public abstract void setScrollPaneZ(JScrollPane scrollPaneZ);

	public abstract String getX();

	public abstract void setX(String x);

	public abstract String getZid();

	public abstract void setZid(String zid);
	
	public abstract JTable getAbc();

	public void setAbc(JTable abc);

}