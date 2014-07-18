package control;

import gui.DataDienst;
import gui.DataGast;
import gui.DataZimmer;
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

import model.Dienstleistung;
import model.Gast;
import model.Zimmer;

public class ButtonHandlerData extends BHHelp implements ActionListener
{	
	DataGast dg;
	DataZimmer dz;
	DataDienst dd;
	//ButtonHandlerStartFrame bh;
	StartFrame sf;


	public ButtonHandlerData(DataGast dg)
	{
		this.dg = dg;
	}
	public ButtonHandlerData(DataZimmer dz)
	{	
		this.dz = dz;
	}
	public ButtonHandlerData(DataDienst dd)
	{
		this.dd = dd;
	}

	public void actionPerformed(ActionEvent e)throws NullPointerException
	{
		System.out.println("Das Ereignis hat den Wert: " + e.getActionCommand());

		//Behandlung der DataGast Buttons
		if(dg!=null)
		{
			if(e.getActionCommand().equals("ChangeGast"))
			{
				try {

					if(dg.jtvGast.getSQLTable().getSelectedRow()== -1)
						throw new GUIException("Fehler: Zeile nicht markiert!");
					//liest Werte aus der Datenbank
					String id = (String) dg.jtvGast.getSQLTable().getValueAt(dg.jtvGast.getSQLTable().getSelectedRow(), 0).toString(); 
					String vn = (String) dg.jtvGast.getSQLTable().getValueAt(dg.jtvGast.getSQLTable().getSelectedRow(), 1).toString(); 
					String name = (String) dg.jtvGast.getSQLTable().getValueAt(dg.jtvGast.getSQLTable().getSelectedRow(), 2).toString(); 
					String str = (String) dg.jtvGast.getSQLTable().getValueAt(dg.jtvGast.getSQLTable().getSelectedRow(), 3).toString(); 
					String hnr = (String) dg.jtvGast.getSQLTable().getValueAt(dg.jtvGast.getSQLTable().getSelectedRow(), 4).toString(); 
					String plz = (String) dg.jtvGast.getSQLTable().getValueAt(dg.jtvGast.getSQLTable().getSelectedRow(), 5).toString(); 
					String ort = (String) dg.jtvGast.getSQLTable().getValueAt(dg.jtvGast.getSQLTable().getSelectedRow(), 6).toString(); 
					String land = (String) dg.jtvGast.getSQLTable().getValueAt(dg.jtvGast.getSQLTable().getSelectedRow(), 7).toString(); 
					String geb = (String) dg.jtvGast.getSQLTable().getValueAt(dg.jtvGast.getSQLTable().getSelectedRow(), 8).toString(); 
					String tel = (String) dg.jtvGast.getSQLTable().getValueAt(dg.jtvGast.getSQLTable().getSelectedRow(), 9).toString(); 

					//launcht ChangeFrame Textfelder werden mit den übergebenen Werten befüllt
					dg.launchChangeFrameG(id,vn, name, str, hnr, plz, ort, land, tel, getDateSqlToGer(geb));


				} 
				catch (GUIException e1) 
				{
					JOptionPane.showMessageDialog(null, e1, "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
			else if(e.getActionCommand().equals("SearchGast"))
			{
				String gebSuche = "";				
				try{
					gebSuche = getSQLDate(dg.Geb.getDate());
				}
				catch (NullPointerException nex){

				}

				String vn = dg.jtfVn.getText(); 
				String name = dg.jtfName.getText();

				String query = "Select * from gast where Vorname like '" + vn + "%' and Name like '" + name + "%' and Geburtstag like '" + gebSuche +"%'";
				System.out.println(query);
				dg.scrollPaneG.setVisible(false);
				dg.scrollPaneG = null;
				dg.jtvGast = new JTableview(query);
				JTable gast = dg.jtvGast.getSQLTable();
				dg.scrollPaneG = new JScrollPane(gast);
				dg.scrollPaneG.setBounds(10, 200, 600, 300);
				dg.panelG1.add(dg.scrollPaneG);

			}
			else if (e.getActionCommand().equals("ConfirmeChangeGast"))
			{

				try
				{
					SimpleDateFormat toDate = new SimpleDateFormat("dd.MM.yyyy");
					Date Geb;
					Geb = toDate.parse(dg.jtfGeb2.getText());
					checkStringEmpty(dg.jtfVn2.getText());
					checkStringEmpty(dg.jtfName2.getText());
					checkStringEmpty(dg.jtfStr.getText());
					checkStringEmpty(dg.jtfHnr.getText());
					checkStringEmpty(dg.jtfPlz.getText());
					checkStringEmpty(dg.jtfOrt.getText());
					checkStringEmpty(dg.jtfLand.getText());
					checkStringEmpty(dg.jtfTel.getText());
					checkBirthday(Geb);
					//Gast-Objekt wird erzeugt hierzu werden die aus den Textfeldern gelesenen Werte an den Gastkonstruktor übergeben
					//Im Anschluss wird vom Gast-Objekt die updateGast Methode ausgeführt.
					Gast gast = new Gast(dg.id,dg.jtfVn2.getText(), dg.jtfName2.getText(), dg.jtfStr.getText(), dg.jtfHnr.getText(),dg.jtfPlz.getText(),
							dg.jtfOrt.getText(), dg.jtfLand.getText(), dg.jtfTel.getText(), Geb);
					Connection con = openDbConnection();
					gast.updateGast(con);
					commitDbConnection(con);
					// Tabelle wird aktuallisiert
					dg.changeFrameG.dispose();
					dg.jtvGast = new JTableview("Select * From gast");
					JTable gtable = dg.jtvGast.getSQLTable();
					dg.scrollPaneG.setVisible(false);
					dg.scrollPaneG = null;
					dg.scrollPaneG = new JScrollPane(gtable);
					dg.scrollPaneG.setBounds(10, 200, 600, 300);
					dg.panelG1.add(dg.scrollPaneG); 


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
			else if(e.getActionCommand().equals("DeleteGast"))
			{
				try {
					//Zeile ausgewählt?
					if(dg.jtvGast.getSQLTable().getSelectedRow()== -1)
						throw new GUIException("Fehler: Zeile nicht markiert!");
					//Abfrage wirklich löschen?
					int answer = JOptionPane.showConfirmDialog(null, "Gast, und alle seine zugehörigen Buchungungen wirklich löschen?", "Error",JOptionPane.YES_NO_OPTION);
					if (answer == JOptionPane.YES_OPTION) {


						String id = (String) dg.jtvGast.getSQLTable().getValueAt(dg.jtvGast.getSQLTable().getSelectedRow(), 0).toString(); 
						int i = Integer.parseInt(id);
						//Gast-Objekt wird erzeugt und die ausgewählte Gast-ID mit übergeben... im Anschluss wird deleteGast ausgeführt.
						Gast g = new Gast(i);
						Connection con = openDbConnection();
						g.deleteGast(con);
						commitDbConnection(con);
						//Erneute Tabellenabfrage und aktuallisieren des Panels
						dg.jtvGast = new JTableview("Select * From gast");
						JTable gast = dg.jtvGast.getSQLTable();
						dg.scrollPaneG.setVisible(false);
						dg.scrollPaneG = null;
						dg.scrollPaneG = new JScrollPane(gast);
						dg.scrollPaneG.setBounds(10, 200, 600, 300);
						dg.panelG1.add(dg.scrollPaneG);	 
					}
				}
				catch (GUIException e1) 
				{
					JOptionPane.showMessageDialog(null, e1, "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		}
		//Behandlung der DataZimmer Buttons
		else if(dz!=null)
		{
			if(e.getActionCommand().equals("ChangeZimmer"))
			{
				try 
				{
					//Zeile markiert?
					if(dz.jtv.getSQLTable().getSelectedRow()== -1)
						throw new GUIException("Fehler: Zeile nicht markiert!");

					//Werte aus der DB auslesen und im Anschluss dem ChangeFrame übergeben um die Textfelder zu befüllen
					String id = (String) dz.jtv.getSQLTable().getValueAt(dz.jtv.getSQLTable().getSelectedRow(), 0).toString(); 
					String typ = (String) dz.jtv.getSQLTable().getValueAt(dz.jtv.getSQLTable().getSelectedRow(), 1).toString(); 
					String preis = (String) dz.jtv.getSQLTable().getValueAt(dz.jtv.getSQLTable().getSelectedRow(), 2).toString(); 

					dz.launchChangeFrameZ(id,typ,preis);
				} 
				catch (GUIException gex) 
				{
					JOptionPane.showMessageDialog(null, gex, "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
			else if(e.getActionCommand().equals("CreateZimmer"))
			{

				dz.launchCreateFrame();
				dz.jtfZnr.setText("");
				dz.jtfPreis.setText("");

			}
			else if(e.getActionCommand().equals("ConfirmeCreateZimmer"))
			{
				try 
				{
					//Kontrolle und Auslesen der Textfelder
					checkStringEmpty(dz.jtfZnr.getText());	
					checkStringEmpty((String) dz.cb.getSelectedItem());
					checkStringEmpty(dz.jtfPreis.getText());
					String zid = dz.jtfZnr.getText();
					String typ = (String) dz.cb.getSelectedItem();
					String preis =  dz.jtfPreis.getText();

					Double p = Double.parseDouble(preis);
					//Zimmer-Objet wird mit übergebenen Werten erzeugt und im Anschluss mit der createZimmer Methode
					//auf der Datenbank ein neues Zimmer angelegt
					Zimmer zimmer = new Zimmer(zid,typ,p);
					zimmer.createZimmer();
					//Fester wird geschlossen und Tabelle aktuallisiert
					dz.createFrame.dispose();
					dz.jtv = new JTableview("Select * From zimmer");
					JTable zTable = dz.jtv.getSQLTable();
					dz.scrollPaneZ.setVisible(false);
					dz.scrollPaneZ = null;
					dz.scrollPaneZ = new JScrollPane(zTable);
					dz.scrollPaneZ.setBounds(10, 120, 600, 300);
					dz.panelZ1.add(dz.scrollPaneZ);	 
				} 
				catch (GUIException e1) 
				{
					JOptionPane.showMessageDialog(null, e1, "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(e.getActionCommand().equals("ConfirmeChangeZimmer"))
			{
				try {

					if(dz.jtv.getSQLTable().getSelectedRow()== -1)
						throw new GUIException("Fehler: Zeile nicht markiert!");

					String id = dz.jtfZnr2.getText(); 
					String typ = (String) dz.cb2.getSelectedItem(); 
					String preis = dz.jtfPreis2.getText();

					Double p = Double.parseDouble(preis);
					System.out.println(id+"__"+ typ+ "__"+ p);
					//Zimmwe-Objekt wird mit ausgelesenen Werten erzeugt und mit der updateZimmer Methode werden Änderungen auf die Db geschrieben
					Zimmer zimmer = new Zimmer(id,typ,p,dz.getZID());
					zimmer.updateZimmer();
					//Fenster wird geschlossen und panel aktuallisiert neu gelauncht
					dz.changeFrameZ.dispose();
					dz.jtv = new JTableview("Select * From zimmer");
					JTable zTable = dz.jtv.getSQLTable();
					dz.scrollPaneZ.setVisible(false);
					dz.scrollPaneZ = null;
					dz.scrollPaneZ = new JScrollPane(zTable);
					dz.scrollPaneZ.setBounds(10, 120, 600, 300);
					dz.panelZ1.add(dz.scrollPaneZ);	 


				} catch (GUIException e1) 
				{
					JOptionPane.showMessageDialog(null, e1, "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(e.getActionCommand().equals("DeleteZimmer"))
			{
				try{
					if(dz.jtv.getSQLTable().getSelectedRow()== -1)
						throw new GUIException("Fehler: Zeile nicht markiert!");


					//Zimmer-Id wird ausgelesen und dem Zimmer-Konstruktor übergeben-> das erzeugte Zimmer-Objekt löscht das Zimmer aus der DB
					String id = (String) dz.jtv.getSQLTable().getValueAt(dz.jtv.getSQLTable().getSelectedRow(), 0).toString(); 
					Zimmer zim = new Zimmer(id);
					zim.deleteZimmer();
					// Tabelle wird aktualliesiert 
					dz.jtv = new JTableview("Select * From zimmer");
					JTable zimmer = dz.jtv.getSQLTable();
					dz.scrollPaneZ.setVisible(false);
					dz.scrollPaneZ = null;
					dz.scrollPaneZ = new JScrollPane(zimmer);
					dz.scrollPaneZ.setBounds(10, 120, 600, 300);
					dz.panelZ1.add(dz.scrollPaneZ);	 
				}
				catch (GUIException e1) {
					JOptionPane.showMessageDialog(null, e1, "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		}
		//Behandlung der DataDienst Buttons
		else if(dd!=null)
		{
			if(e.getActionCommand().equals("ChangeDienst"))
			{
				try {
					//Zeile markiert?
					if(dd.jtvDienst.getSQLTable().getSelectedRow()== -1)
						throw new GUIException("Fehler: Zeile nicht markiert!");

					//Lese Werte aus der DB und öffne damit ChangeFrame
					String id = (String) dd.jtvDienst.getSQLTable().getValueAt(dd.jtvDienst.getSQLTable().getSelectedRow(), 0).toString(); 
					String typ = (String) dd.jtvDienst.getSQLTable().getValueAt(dd.jtvDienst.getSQLTable().getSelectedRow(), 1).toString(); 
					String preis = (String) dd.jtvDienst.getSQLTable().getValueAt(dd.jtvDienst.getSQLTable().getSelectedRow(), 2).toString(); 

					dd.launchChangeFrameD(id, preis,typ );

				} catch (GUIException e1) 
				{
					JOptionPane.showMessageDialog(null, e1, "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(e.getActionCommand().equals("CreateDienst"))
			{
				dd.launchCreateFrameD();
				dd.jtfTyp.setText("");
				dd.jtfPreis.setText("");
			}

			else if(e.getActionCommand().equals("DeleteDienst"))
			{
				try{
					//Zeile markiert?
					if(dd.jtvDienst.getSQLTable().getSelectedRow()== -1)
						throw new GUIException("Fehler: Zeile nicht markiert!");
					//Lese Zimmer-ID aus und übergebe diese dem Zimmer-Konstruktor...> Zimmer-Objekt löscht das Zimmer aus der Db
					String id = (String) dd.jtvDienst.getSQLTable().getValueAt(dd.jtvDienst.getSQLTable().getSelectedRow(), 0).toString();
					int i = Integer.parseInt(id);
					Dienstleistung dienst = new Dienstleistung(i);
					dienst.deleteDienst();
					// Tabelle wird aktuallisiert 
					dd.jtvDienst = new JTableview("Select * From dienstleistung");
					JTable dTable = dd.jtvDienst.getSQLTable();
					dd.scrollPaneD.setVisible(false);
					dd.scrollPaneD = null;
					dd.scrollPaneD = new JScrollPane(dTable);
					dd.scrollPaneD.setBounds(10, 120, 600, 300);
					dd.panelD1.add(dd.scrollPaneD);
				}
				catch (GUIException e1) {
					JOptionPane.showMessageDialog(null, e1, "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(e.getActionCommand().equals("ConfirmeCreateDienst"))
			{
				try 
				{
					checkStringEmpty(dd.jtfTyp.getText());
					checkPrize(dd.jtfPreis.getText());
					//Werte aus Textfeldern lesen
					//String did = dd.jtfID.getText();
					String typ = dd.jtfTyp.getText();
					String preis = dd.jtfPreis.getText();
					double p = Double.parseDouble(preis);
					//Neues Dienstleistungs-Objekt führt createDienst Methode aus
					Dienstleistung dienst = new Dienstleistung(typ,p);
					dienst.createDienst();
					//Tabelle wird aktualisiert 
					dd.createFrameD.dispose();
					dd.jtvDienst = new JTableview("Select * From dienstleistung");
					JTable dTable = dd.jtvDienst.getSQLTable();
					dd.scrollPaneD.setVisible(false);
					dd.scrollPaneD = null;
					dd.scrollPaneD = new JScrollPane(dTable);
					dd.scrollPaneD.setBounds(10, 120, 600, 300);
					dd.panelD1.add(dd.scrollPaneD); 
				} 
				catch (GUIException e1) 
				{
					JOptionPane.showMessageDialog(null, e1, "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
			else if(e.getActionCommand().equals("ConfirmeChangeDienst"))
			{
				System.out.println("1");
				try 
				{
					checkStringEmpty(dd.jtfTyp2.getText());
					checkPrize(dd.jtfPreis2.getText());
					checkID(dd.jtfID2.getText());
					System.out.println("1");
					//Zeile markiert?
					if(dd.jtvDienst.getSQLTable().getSelectedRow()== -1)
						throw new GUIException("Fehler: Zeile nicht markiert!");
					//Textfelder auslesen
					String id = dd.jtfID2.getText(); 
					String typ = dd.jtfTyp2.getText(); 
					String preis = dd.jtfPreis2.getText();
					double p = Double.parseDouble(preis);
					int i = Integer.parseInt(id);
					//Dienstleisung-Objekt erzeugenen und Änderung mit updateDienst auf die DB schreiben
					Dienstleistung dienst = new Dienstleistung(i, typ, p, dd.getX());
					dienst.updateDienst();
					//Fenster schließen und Tabelle aktuallisieren
					dd.changeFrameD.dispose();
					dd.jtvDienst = new JTableview("Select * From dienstleistung");
					JTable dTable = dd.jtvDienst.getSQLTable();
					dd.scrollPaneD.setVisible(false);
					dd.scrollPaneD = null;
					dd.scrollPaneD = new JScrollPane(dTable);
					dd.scrollPaneD.setBounds(10, 120, 600, 300);
					dd.panelD1.add(dd.scrollPaneD); 

				}
				catch (GUIException e1) {
					JOptionPane.showMessageDialog(null, e1, "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}	

		}


	}


}
