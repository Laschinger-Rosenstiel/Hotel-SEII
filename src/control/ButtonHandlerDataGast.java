package control;

import gui.DataGast;
import gui.InterfaceDataGast;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Gast;

public class ButtonHandlerDataGast extends BHHelp implements ActionListener {
	
	/*DataInterface dg;
	DataGast datagast;
	
	/*public ButtonHandlerDataGast(DataGast datagast, DataInterface dg){
		this.datagast = datagast;
		this.dg = dg;
	}
	
	public ButtonHandlerDataGast(DataInterface dg){
		this.dg = dg;
	}
	public ButtonHandlerDataGast(DataGast datagast){
		this.datagast = datagast;
		
	}*/
	
	InterfaceDataGast idg;
	
	public ButtonHandlerDataGast(InterfaceDataGast idg){
		this.idg = idg;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Das Ereignis hat den Wert: " + e.getActionCommand());

		//Behandlung der DataGast Buttons
		if(idg!=null)
		{
			if(e.getActionCommand().equals(InterfaceDataGast.ACTION_CHANGE)){
				try {

					if(idg.getJTableview().getSQLTable().getSelectedRow()== -1)
						throw new GUIException("Fehler: Zeile nicht markiert!");
					//liest Werte aus der Datenbank
				//	String id = (String) idg.getJTableview().getSQLTable().getValueAt(idg.getJTableview().getSQLTable().getSelectedRow(), 0).toString(); 
					String vn = (String) idg.getJTableview().getSQLTable().getValueAt(idg.getJTableview().getSQLTable().getSelectedRow(), 1).toString(); 
					String name = (String) idg.getJTableview().getSQLTable().getValueAt(idg.getJTableview().getSQLTable().getSelectedRow(), 2).toString(); 
					String str = (String) idg.getJTableview().getSQLTable().getValueAt(idg.getJTableview().getSQLTable().getSelectedRow(), 3).toString(); 
					String hnr = (String) idg.getJTableview().getSQLTable().getValueAt(idg.getJTableview().getSQLTable().getSelectedRow(), 4).toString(); 
					String plz = (String) idg.getJTableview().getSQLTable().getValueAt(idg.getJTableview().getSQLTable().getSelectedRow(), 5).toString(); 
					String ort = (String) idg.getJTableview().getSQLTable().getValueAt(idg.getJTableview().getSQLTable().getSelectedRow(), 6).toString(); 
					String land = (String) idg.getJTableview().getSQLTable().getValueAt(idg.getJTableview().getSQLTable().getSelectedRow(), 7).toString(); 
					String geb = (String) idg.getJTableview().getSQLTable().getValueAt(idg.getJTableview().getSQLTable().getSelectedRow(), 8).toString(); 
					String tel = (String) idg.getJTableview().getSQLTable().getValueAt(idg.getJTableview().getSQLTable().getSelectedRow(), 9).toString(); 

					//Launch ChangeFrame Textfelder werden mit den übergebenen Werten befüllt
					idg.launchChangeFrame(); 
					//id = x;
					idg.setTextTextField(idg.getJtfVn2(), vn);;
					idg.setTextTextField(idg.getJtfName2(), name);
					idg.setTextTextField(idg.getJtfStr(), str);
					idg.setTextTextField(idg.getJtfHnr(), hnr);
					idg.setTextTextField(idg.getJtfPlz(), plz);
					idg.setTextTextField(idg.getJtfOrt(), ort);
					idg.setTextTextField(idg.getJtfLand(), land);
					idg.setTextTextField(idg.getJtfGeb2(), geb);//!!!Datum??
					idg.setTextTextField(idg.getJtfTel(), tel);

				} 
				catch (GUIException e1) 
				{
					JOptionPane.showMessageDialog(null, e1, "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
			else if(e.getActionCommand().equals(InterfaceDataGast.ACTION_SEARCH)){
				String gebSuche = "";				
				try{
					gebSuche = getSQLDate(idg.getGeb().getDate());
				}
				catch (NullPointerException nex){

				}

				String vn = idg.getJtfVn().getText(); 
				String name = idg.getJtfName().getText();

				String query = "Select * from gast where Vorname like '" + vn + "%' and Name like '" + name + "%' and Geburtstag like '" + gebSuche +"%'";
				System.out.println(query);
				idg.getScrollPaneG().setVisible(false);
				idg.setScrollPaneG(null);
				idg.setJTableview(new JTableview(query));
				JTable gast = idg.getJTableview().getSQLTable();
				idg.setScrollPaneG( new JScrollPane(gast));
				idg.getScrollPaneG().setBounds(10, 200, 600, 300);
				idg.getPanelG1().add(idg.getScrollPaneG());

			}
			else if (e.getActionCommand().equals(InterfaceDataGast.ACTION_CONFIRM))
			{

				try
				{
					SimpleDateFormat toDate = new SimpleDateFormat("dd.MM.yyyy");
					Date Geb;
					Geb = toDate.parse(idg.getJtfGeb2().getText());
					checkStringEmpty(idg.getJtfVn2().getText());
					checkStringEmpty(idg.getJtfName2().getText());
					checkStringEmpty(idg.getJtfStr().getText());
					checkStringEmpty(idg.getJtfHnr().getText());
					checkStringEmpty(idg.getJtfPlz().getText());
					checkStringEmpty(idg.getJtfOrt().getText());
					checkStringEmpty(idg.getJtfLand().getText());
					checkStringEmpty(idg.getJtfTel().getText());
					checkBirthday(Geb);
					//Gast-Objekt wird erzeugt hierzu werden die aus den Textfeldern gelesenen Werte an den Gastkonstruktor übergeben
					//Im Anschluss wird vom Gast-Objekt die updateGast Methode ausgeführt.
					Gast gast = new Gast(idg.getId(),idg.getJtfVn2().getText(), idg.getJtfName2().getText(), idg.getJtfStr().getText(), idg.getJtfHnr().getText(),idg.getJtfPlz().getText(),
							idg.getJtfOrt().getText(), idg.getJtfLand().getText(), idg.getJtfTel().getText(), Geb);
					Connection con = openDbConnection();
					gast.updateGast(con);
					commitDbConnection(con);
					// Tabelle wird aktuallisiert
					idg.getChangeFrameG().dispose();
					idg.setJTableview(new JTableview("Select * From gast")); 
					JTable gtable = idg.getJTableview().getSQLTable();
					idg.getScrollPaneG().setVisible(false);
					idg.setScrollPaneG(null);
					idg.setScrollPaneG(new JScrollPane(gtable));
					idg.getScrollPaneG().setBounds(10, 200, 600, 300);
					idg.getPanelG1().add(idg.getScrollPaneG()); 


				}
				catch (GUIException e1) 
				{
					JOptionPane.showMessageDialog(null, e1, "Error",
							JOptionPane.ERROR_MESSAGE);
				} 
				catch (ParseException e1) 
				{
					JOptionPane.showMessageDialog(null, e1, "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
			else if(e.getActionCommand().equals(InterfaceDataGast.ACTION_DELETE))
			{
				try {
					//Zeile ausgewählt?
					if(idg.getJTableview().getSQLTable().getSelectedRow()== -1)
						throw new GUIException("Fehler: Zeile nicht markiert!");
					//Abfrage wirklich löschen?
					int answer = JOptionPane.showConfirmDialog(null, "Gast, und alle seine zugehörigen Buchungungen wirklich löschen?", "Error",JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {


						String id = (String) idg.getJTableview().getSQLTable().getValueAt(idg.getJTableview().getSQLTable().getSelectedRow(), 0).toString(); 
						int i = Integer.parseInt(id);
						//Gast-Objekt wird erzeugt und die ausgewählte Gast-ID mit übergeben... im Anschluss wird deleteGast ausgeführt.
						Gast g = new Gast(i);
						Connection con = openDbConnection();
						g.deleteGast(con);
						commitDbConnection(con);
						//Erneute Tabellenabfrage und aktuallisieren des Panels
						idg.setJTableview(new JTableview("Select * From gast"));
						JTable gast = idg.getJTableview().getSQLTable();
						idg.getScrollPaneG().setVisible(false);
						idg.setScrollPaneG(null);
						idg.setScrollPaneG(new JScrollPane(gast));
						idg.getScrollPaneG().setBounds(10, 200, 600, 300);
						idg.getPanelG1().add(idg.getScrollPaneG());	 
					}
				}
				catch (GUIException e1) 
				{
					JOptionPane.showMessageDialog(null, e1, "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		}
			}
	

}
