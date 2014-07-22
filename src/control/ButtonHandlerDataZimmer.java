package control;

import gui.DataZimmer;
import gui.InterfaceDataZimmer;
import gui.StartFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Zimmer;


public class ButtonHandlerDataZimmer extends BHHelp implements ActionListener {
	
	/*DataInterface dz;
	DataZimmer datazimmer;
	
	public ButtonHandlerDataZimmer(DataInterface dz, InterfaceDataZimmer datazimmer){
		this.dz = dz;
		this.datazimmer = datazimmer;
	}
	
	/*public ButtonHandlerDataZimmer(DataZimmer datazimmer){
		this.datazimmer = datazimmer;
	}*/
	
	InterfaceDataZimmer idz;
	
	public ButtonHandlerDataZimmer(InterfaceDataZimmer idz){
		this.idz = idz;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Das Ereignis hat den Wert: " + e.getActionCommand());

		if(idz!=null){
			if(e.getActionCommand().equals(idz.ACTION_CHANGE))
			{
				try 
				{
					//Zeile markiert?
					if(idz.getJtv().getSQLTable().getSelectedRow()== -1)
						throw new GUIException("Fehler: Zeile nicht markiert!");

					//Werte aus der DB auslesen und im Anschluss dem ChangeFrame übergeben um die Textfelder zu befüllen
					String id = (String) idz.getJtv().getSQLTable().getValueAt(idz.getJtv().getSQLTable().getSelectedRow(), 0).toString(); 
					String typ = (String) idz.getJtv().getSQLTable().getValueAt(idz.getJtv().getSQLTable().getSelectedRow(), 1).toString(); 
					String preis = (String) idz.getJtv().getSQLTable().getValueAt(idz.getJtv().getSQLTable().getSelectedRow(), 2).toString(); 

					idz.launchChangeFrame();
					idz.setTextTextField(idz.getJtfPreis2(), preis);
					idz.setTextTextField(idz.getJtfZnr2(), id);
				} 
				catch (GUIException gex) 
				{
					JOptionPane.showMessageDialog(null, gex, "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(e.getActionCommand().equals(idz.ACTION_CREATE))
			{

				idz.launchCreateFrame();
				idz.setTextTextField(idz.getJtfZnr(), "");
				idz.setTextTextField(idz.getJtfPreis(), "");

			}
			else if(e.getActionCommand().equals(idz.ACTION_CONFIRM_CREATE))
			{
				try 
				{
					//Kontrolle und Auslesen der Textfelder
					checkStringEmpty(idz.getJtfZnr().getText());	
					checkStringEmpty((String) idz.getCb().getSelectedItem());
					checkStringEmpty(idz.getJtfPreis().getText());
					String zid = idz.getJtfZnr().getText();
					String typ = (String) idz.getCb().getSelectedItem();
					String preis =  idz.getJtfPreis().getText();

					Double p = Double.parseDouble(preis);
					//Zimmer-Objet wird mit übergebenen Werten erzeugt und im Anschluss mit der createZimmer Methode
					//auf der Datenbank ein neues Zimmer angelegt
					Zimmer zimmer = new Zimmer(zid,typ,p);
					zimmer.createZimmer();
					//Fester wird geschlossen und Tabelle aktuallisiert
					idz.getCreateFrame().dispose();
					idz.setJtv(new JTableview("Select * From zimmer"));
					JTable zTable = idz.getJtv().getSQLTable();///Achtung
					idz.getScrollPaneZ().setVisible(false);
					idz.setScrollPaneZ(null);
					idz.setScrollPaneZ(new JScrollPane(zTable));
					idz.getScrollPaneZ().setBounds(10, 120, 600, 300);
					idz.getPanelZ1().add(idz.getScrollPaneZ());	 
				} 
				catch (GUIException e1) 
				{
					JOptionPane.showMessageDialog(null, e1, "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(e.getActionCommand().equals(idz.ACTION_CONFIRM_CHANGE))
			{
				try {

					if(idz.getJtv().getSQLTable().getSelectedRow()== -1)
						throw new GUIException("Fehler: Zeile nicht markiert!");

					String id = idz.getJtfZnr2().getText(); 
					String typ = (String) idz.getCb2().getSelectedItem(); 
					String preis = idz.getJtfPreis2().getText();

					Double p = Double.parseDouble(preis);
					System.out.println(id+"__"+ typ+ "__"+ p);
					//Zimmwe-Objekt wird mit ausgelesenen Werten erzeugt und mit der updateZimmer Methode werden Änderungen auf die Db geschrieben
					Zimmer zimmer = new Zimmer(id,typ,p,idz.getZid());
					zimmer.updateZimmer();
					//Fenster wird geschlossen und panel aktuallisiert neu gelauncht
					idz.getChangeFrameZ().dispose();
					idz.setJtv(new JTableview("Select * From zimmer"));
					JTable zTable = idz.getJtv().getSQLTable();
					idz.getScrollPaneZ().setVisible(false);
					idz.setScrollPaneZ(null);;
					idz.setScrollPaneZ(new JScrollPane(zTable));
					idz.getScrollPaneZ().setBounds(10, 120, 600, 300);
					idz.getPanelZ1().add(idz.getScrollPaneZ());	 


				} catch (GUIException e1) 
				{
					JOptionPane.showMessageDialog(null, e1, "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(e.getActionCommand().equals(idz.ACTION_DELETE))
			{
				try{
					if(idz.getJtv().getSQLTable().getSelectedRow()== -1)
						throw new GUIException("Fehler: Zeile nicht markiert!");


					//Zimmer-Id wird ausgelesen und dem Zimmer-Konstruktor übergeben-> das erzeugte Zimmer-Objekt löscht das Zimmer aus der DB
					String id = (String) idz.getJtv().getSQLTable().getValueAt(idz.getJtv().getSQLTable().getSelectedRow(), 0).toString(); 
					Zimmer zim = new Zimmer(id);
					zim.deleteZimmer();
					// Tabelle wird aktualliesiert 
					idz.setJtv( new JTableview("Select * From zimmer"));
					JTable zimmer = idz.getJtv().getSQLTable();//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
					idz.getScrollPaneZ().setVisible(false);
					idz.setScrollPaneZ(null);
					idz.setScrollPaneZ(new JScrollPane(zimmer));
					idz.getScrollPaneZ().setBounds(10, 120, 600, 300);
					idz.getPanelZ1().add(idz.getScrollPaneZ());	
				}
				catch (GUIException e1) {
					JOptionPane.showMessageDialog(null, e1, "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		}

			
		
		
	}

}
