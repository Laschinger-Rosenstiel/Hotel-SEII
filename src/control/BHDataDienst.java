package control;


import gui.InterfaceDataDienst;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Dienstleistung;

public class BHDataDienst extends BHHelp implements ActionListener {

	/*DataInterface dd;
	DataDienst datadienst;

	public ButtonHandlerDataDienst(DataInterface dd, InterfaceDataDienst datadienst){
		this.dd = dd;
		this.datadienst = datadienst;
	}
	/*public ButtonHandlerDataDienst(DataDienst datadienst){
		this.datadienst = datadienst;
	}*/
	
	InterfaceDataDienst idd;
	private Dienstleistung dl;
	
	public BHDataDienst(InterfaceDataDienst idd){
		this.idd = idd;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Das Ereignis hat den Wert: " + e.getActionCommand());

		if(idd!=null){
			if(e.getActionCommand().equals(InterfaceDataDienst.ACTION_CHANGE))
			{
				try {
					//Zeile markiert?
					if(idd.getJtvDienst().getSQLTable().getSelectedRow()== -1)
						throw new GUIException("Fehler: Zeile nicht markiert!");

					//Lese Werte aus der DB und öffne damit ChangeFrame
					String id = (String) idd.getJtvDienst().getSQLTable().getValueAt(idd.getJtvDienst().getSQLTable().getSelectedRow(), 0).toString(); 
					String typ = (String) idd.getJtvDienst().getSQLTable().getValueAt(idd.getJtvDienst().getSQLTable().getSelectedRow(), 1).toString(); 
					String preis = (String) idd.getJtvDienst().getSQLTable().getValueAt(idd.getJtvDienst().getSQLTable().getSelectedRow(), 2).toString(); 

					dl = new Dienstleistung(Integer.parseInt(id), typ, Double.parseDouble(preis));
					idd.launchChangeFrame();
					idd.setTextTextField(idd.getJtfID2(), id);
					idd.setTextTextField(idd.getJtfTyp2(), typ);
					idd.setTextTextField(idd.getJtfPreis2(), preis);
					//dd.dnr = Integer.parseInt(id);

				} catch (GUIException e1) 
				{
					JOptionPane.showMessageDialog(null, e1, "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(e.getActionCommand().equals(InterfaceDataDienst.ACTION_CREATE)){
				idd.launchCreateFrame();
				idd.setTextTextField(idd.getJtfTyp(), "");
				idd.setTextTextField(idd.getJtfPreis(), "");
				}
			else if(e.getActionCommand().equals(InterfaceDataDienst.ACTION_DELETE))
			{
				try{
					//Zeile markiert?
					if(idd.getJtvDienst().getSQLTable().getSelectedRow()== -1)
						throw new GUIException("Fehler: Zeile nicht markiert!");
					//Lese Zimmer-ID aus und übergebe diese dem Zimmer-Konstruktor...> Zimmer-Objekt löscht das Zimmer aus der Db
					String id = (String) idd.getJtvDienst().getSQLTable().getValueAt(idd.getJtvDienst().getSQLTable().getSelectedRow(), 0).toString();
					int i = Integer.parseInt(id);
					Dienstleistung dienst = new Dienstleistung(i);
					dienst.deleteDienst();
					// Tabelle wird aktuallisiert 
					idd.setJtvDienst(new JTableview("Select * From dienstleistung"));
					JTable dTable = idd.getJtvDienst().getSQLTable();
					idd.getScrollPaneD().setVisible(false);
					idd.setScrollPaneD(null);
					idd.setScrollPaneD(new JScrollPane(dTable));
					idd.getScrollPaneD().setBounds(10, 120, 600, 300);
					idd.getPanelD1().add(idd.getScrollPaneD());
				}
				catch (GUIException e1) {
					JOptionPane.showMessageDialog(null, e1, "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(e.getActionCommand().equals(InterfaceDataDienst.ACTION_CONFIRM_CREATE))
			{
				try 
				{
					checkStringEmpty(idd.getJtfTyp().getText());
					checkPrize(idd.getJtfPreis().getText());
					//Werte aus Textfeldern lesen
					//String did = dd.jtfID.getText();
					String typ = idd.getJtfTyp().getText();
					String preis = idd.getJtfPreis().getText();
					double p = Double.parseDouble(preis);
					//Neues Dienstleistungs-Objekt führt createDienst Methode aus
					Dienstleistung dienst = new Dienstleistung(typ,p);
					dienst.createDienst();
					//Tabelle wird aktualisiert 
					idd.getCreateFrameD().dispose();
					idd.setJtvDienst(new JTableview("Select * From dienstleistung"));
					JTable dTable = idd.getJtvDienst().getSQLTable();
					idd.getScrollPaneD().setVisible(false);
					idd.setScrollPaneD(null);
					idd.setScrollPaneD(new JScrollPane(dTable));
					idd.getScrollPaneD().setBounds(10, 120, 600, 300);
					idd.getPanelD1().add(idd.getScrollPaneD()); 
				} 
				catch (GUIException e1) 
				{
					JOptionPane.showMessageDialog(null, e1, "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
			else if(e.getActionCommand().equals(InterfaceDataDienst.ACTION_CONFIRM_CHANGE))
			{
				System.out.println("1");
				try 
				{
					checkStringEmpty(idd.getJtfTyp2().getText());
					checkPrize(idd.getJtfPreis2().getText());
					checkID(idd.getJtfID2().getText());
					System.out.println("1");
					//Zeile markiert?
					if(idd.getJtvDienst().getSQLTable().getSelectedRow()== -1)
						throw new GUIException("Fehler: Zeile nicht markiert!");
					//Textfelder auslesen
					String typ = idd.getJtfTyp2().getText(); 
					String preis = idd.getJtfPreis2().getText();
					double p = Double.parseDouble(preis);
					//Dienstleisung-Objekt erzeugenen und Änderung mit updateDienst auf die DB schreiben
					dl.setTyp(typ);
					dl.setPreis(p);
					dl.updateDienst();
					//Fenster schließen und Tabelle aktuallisieren
					idd.getChangeFrameD().dispose();
					idd.setJtvDienst(new JTableview("Select * From dienstleistung"));
					JTable dTable = idd.getJtvDienst().getSQLTable();
					idd.getScrollPaneD().setVisible(false);
					idd.setScrollPaneD(null);
					idd.setScrollPaneD(new JScrollPane(dTable)); 
					idd.getScrollPaneD().setBounds(10, 120, 600, 300);
					idd.getPanelD1().add(idd.getScrollPaneD()); 

				}
				catch (GUIException e1) {
					JOptionPane.showMessageDialog(null, e1, "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}	

		
	}

}

}
