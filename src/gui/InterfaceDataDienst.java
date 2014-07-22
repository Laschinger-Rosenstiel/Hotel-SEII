package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import control.JTableview;

public interface InterfaceDataDienst {
	
	
	public final static String ACTION_CREATE = "Create"; 
	public final static String ACTION_CHANGE = "Change"; 
	public final static String ACTION_DELETE = "Delete";
	public final static String ACTION_CONFIRM_CREATE = "ConfirmeCreate";
	public final static String ACTION_CONFIRM_CHANGE = "ConfirmeChange";
	

	public final static String kennung = "Dienst";

	public abstract JPanel launchPanel();

	public abstract JFrame launchCreateFrame();

	public abstract JFrame launchChangeFrame();

	//Allgemeiner Setter für die Textfelder
	public abstract void setTextTextField(JTextField x, String text);

	//Getter für die Textfelder
	public abstract JTextField getJtfTyp();

	public abstract JTextField getJtfPreis();

	public abstract JTextField getJtfID();

	public abstract JTextField getJtfTyp2();

	public abstract JTextField getJtfPreis2();

	public abstract JTextField getJtfID2();

	//Übrige Getter&Setter
	public abstract JFrame getCreateFrameD();

	public abstract void setCreateFrameD(JFrame createFrameD);

	public abstract JFrame getChangeFrameD();

	public abstract void setChangeFrameD(JFrame changeFrameD);

	public abstract JTableview getJtvDienst();

	public abstract void setJtvDienst(JTableview jtvDienst);

	public abstract JPanel getPanelD1();

	public abstract void setPanelD1(JPanel panelD1);

	public abstract JPanel getPanelD2();

	public abstract void setPanelD2(JPanel panelD2);

	public abstract JPanel getPanelD3();

	public abstract void setPanelD3(JPanel panelD3);

	public abstract JScrollPane getScrollPaneD();

	public abstract void setScrollPaneD(JScrollPane scrollPaneD);

	public abstract int getDnr();

	public abstract void setDnr(int dnr);

	public abstract void setJTableview(JTableview jtvDienst);

}