package gui;

import java.util.Date;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import control.JTableview;

public interface InterfaceCancel {

	public abstract JPanel launchStartPanel();

	//getter-Methoden
	public abstract String getGidSuche();

	public abstract String getVorSuche();

	public abstract String getNameSuche();

	public abstract Date getGebSuche();

	public abstract String getQuery();
	
	public abstract JTableview getSuche();
	
	public abstract JScrollPane getScrollPane();
	
	public abstract void setScrollPane(JTable table);
	
	public abstract void setScrollPaneNull();
	
	public abstract void setScrollPaneVisible(boolean b);
	
	public abstract void setBoundsScrollPane(int x, int y, int width, int height);
	
	public abstract void addToContentPane(JScrollPane pane);
	
	public abstract void setSuche(String query);
	
	public abstract void setSucheNull();

}