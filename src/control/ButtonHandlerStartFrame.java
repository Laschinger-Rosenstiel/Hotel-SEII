package control;

import gui.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;



public class ButtonHandlerStartFrame implements ActionListener 
{
	StartFrame sf;
	JPanel panel;
	


	public ButtonHandlerStartFrame(StartFrame sf)
	{
		this.sf = sf;

	}

	public ButtonHandlerStartFrame(JPanel panel)
	{
		this.panel = panel;
	}

	public void actionPerformed(ActionEvent e) 
	{ 
		System.out.println(sf.getS());
		
		if(sf.getS().equals("Rezeption"))
		{

			System.out.println("Das Ereignis hat den Wert: " +e.getActionCommand());
			if (e.getActionCommand().equals("Zimmer buchen"))
			{
				sf.launchStartFrame(new BookZimmer().launchStartPanel(),sf.getJPanel4());
			}
			else if (e.getActionCommand().equals("Dl buchen"))
			{
				sf.launchStartFrame(new BookDl().launchStartPanel(), sf.getJPanel4());
			}
			else if (e.getActionCommand().equals("Zimmer stornieren")){
				sf.launchStartFrame(new CancelZimmer().launchStartPanel(), sf.getJPanel4());
			}
			else if (e.getActionCommand().equals("Dl stornieren")){
				sf.launchStartFrame(new CancelDl().launchStartPanel(), sf.getJPanel4());
			}
			else if (e.getActionCommand().equals("Verfügbarkeit prüfen")) {
				sf.launchStartFrame(new CheckZimmer().launchStartPanel(), sf.getJPanel4());
			}
			else if (e.getActionCommand().equals("Preis berechnen")) {
				sf.launchStartFrame(new CalcPreis().launchStartPanel(), sf.getJPanel4());
			}
		}
		else if(sf.getS().equals("Manager"))
		{
			System.out.println("Das Ereignis hat den Wert: " +e.getActionCommand());
			if (e.getActionCommand().equals("Zimmer buchen"))
			{
				sf.launchStartFrame(new BookZimmer().launchStartPanel(),sf.getJPanel3());
			}
			else if (e.getActionCommand().equals("Dl buchen"))
			{
				sf.launchStartFrame(new BookDl().launchStartPanel(), sf.getJPanel3());
			}
			else if (e.getActionCommand().equals("Zimmer stornieren"))
			{
				sf.launchStartFrame(new CancelZimmer().launchStartPanel(), sf.getJPanel3());
			}
			else if (e.getActionCommand().equals("Dl stornieren"))
			{
				sf.launchStartFrame(new CancelDl().launchStartPanel(), sf.getJPanel3());
			}
			else if (e.getActionCommand().equals("Verfügbarkeit prüfen"))
			{
				sf.launchStartFrame(new CheckZimmer().launchStartPanel(), sf.getJPanel3());
			}
			else if (e.getActionCommand().equals("Preis berechnen")) 
			{
				sf.launchStartFrame(new CalcPreis().launchStartPanel(), sf.getJPanel3());
			}
			else if (e.getActionCommand().equals("Gast anlegen")) 
			{
					sf.launchStartFrame(new DataGast().launchJPanel(), sf.getJPanel3());
			}
			else if (e.getActionCommand().equals("Zimmer anlegen")) 
			{
				sf.launchStartFrame(new DataZimmer().launchJPanel(), sf.getJPanel3());
				
			}
			else if (e.getActionCommand().equals("Dienstleistung anlegen")) 
			{
				sf.launchStartFrame(new DataDienst().launchJPanel(), sf.getJPanel3());
			}
			
		
		}
	}


/*public void actionPerformedM(ActionEvent e) 
{
	System.out.println("Das Ereignis hat den Wert: " +e.getActionCommand());
	if (e.getActionCommand().equals("Zimmer buchen"))
	{
		BookZimmer bz = new BookZimmer();
		System.out.println("vor ausführen bookzimmer()");
		sf.launchStartFrame(bz.launchStartPanel(),sf.getJPanel3());
		System.out.println("nach ausführen bookzimmer()");
	}
	else if (e.getActionCommand().equals("Dl buchen"))
	{
		sf.launchStartFrame(new BookDl().launchStartPanel(), sf.getJPanel3());
	}
	else if (e.getActionCommand().equals("Zimmer stornieren")){
		sf.launchStartFrame(new CancelZimmer().launchStartPanel(), sf.getJPanel3());
	}

}
*/


}
