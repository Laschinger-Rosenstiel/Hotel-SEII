package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import control.JTableview;

public interface InterfaceDataGast {

	 
	public final static String ACTION_CHANGE = "Change"; 
	public final static String ACTION_DELETE = "Delete";
	public final static String ACTION_CONFIRM = "Confirme";
	public final static String ACTION_SEARCH = "Search";
	/* (non-Javadoc)
	 * @see gui.InterfaceDataGast#getChangeFrameG()
	 */
	public abstract JFrame getChangeFrameG();

	public abstract JPanel launchPanel();

	public abstract JFrame launchChangeFrame();

	//Algemeiner Setter für Textfelder
	public abstract void setTextTextField(JTextField x, String text);

	//Getter Textfelder
	public abstract JTextField getJtfVn();

	public abstract JTextField getJtfName();

	public abstract JTextField getJtfVn2();

	public abstract JTextField getJtfName2();

	public abstract JTextField getJtfGeb2();

	public abstract JTextField getJtfStr();

	public abstract JTextField getJtfHnr();

	public abstract JTextField getJtfPlz();

	public abstract JTextField getJtfOrt();

	public abstract JTextField getJtfLand();

	public abstract JTextField getJtfTel();

	//Getter&Setter Tableview
	public abstract JTableview getJTableview();

	public abstract void setJTableview(JTableview jtvGast);

	public abstract void setChangeFrameG(JFrame changeFrameG);

	public abstract JPanel getPanelG1();

	public abstract void setPanelG1(JPanel panelG1);

	public abstract JPanel getPanelG2();

	public abstract void setPanelG2(JPanel panelG2);

	public abstract JScrollPane getScrollPaneG();

	public abstract void setScrollPaneG(JScrollPane scrollPaneG);

	public abstract String getId();

	public abstract void setId(String id);

	public abstract JDateChooser getGeb();

	public abstract void setGeb(JDateChooser geb);

}