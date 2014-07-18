package control;

import gui.LoginFrame;
import gui.StartFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;

import javax.swing.JOptionPane;




public class ButtonHandlerLogin extends BHHelp implements ActionListener , KeyListener
{
	LoginFrame lf;
	StartFrame sf;
		
	String s;

	public ButtonHandlerLogin(LoginFrame x)
	{
		lf = x;
	}
	public ButtonHandlerLogin(StartFrame x)
	{
		sf = x;
	}




	public void actionPerformed(ActionEvent e) throws NullPointerException
	{
		//System.out.println("Das Ereignis hat den Wert: " +e.getActionCommand());
		if (e.getActionCommand().equals("Change"))
		{
			//Fenster zum ändern des Passworts
			lf.launchChangeFrame();
		}
		else if(e.getActionCommand().equals("BackToLogin"))
		{
			//Zurück zum Login
			lf.cf.dispose();
		}
		else if(e.getActionCommand().equals("SavePw"))
		{
			try{
				//Auslesen und Kontrollieren
				String user = (String) lf.cb.getSelectedItem();
				String pwAlt = selectDB("Select Passwort from Benutzer where Benutzername = '"+user+"'");
				//System.out.println(pwAlt);
					
				checkStringEmpty(lf.jpf2.getText());
				checkStringEmpty(lf.jpf3.getText());
				checkStringEmpty(lf.jpf4.getText());
				checkLogin(lf.jpf4.getText(), pwAlt);
				checkLogin(lf.jpf2.getText(), lf.jpf3.getText());
				
				//System.out.println("Vergleich1: "+lf.jpf2.getText() + " mit "+lf.jpf3.getText());
				//System.out.println("Vergleich2: "+lf.jpf4.getText() + "mit " + pwAlt);
				if(lf.jpf2.getText().equals(lf.jpf3.getText()) & lf.jpf4.getText().equals(pwAlt))
				{
					//lf.setPw(lf.jpf2.getText());
					//Passwort auf der DB ändern
					Connection con = openDbConnection();
					writeDb("update hotel-seII.benutzer set passwort ='"+lf.jpf2.getText()+"' where Benutzername = '"+user+"'", con);
					commitDbConnection(con);
					//ChangeFrame schließen und zurück zu Login
					lf.launchLoginFrame();
					lf.cf.dispose();
				}

			}
			catch(GUIException gex)
			{
				JOptionPane.showMessageDialog(null, gex, "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		}
		else if(e.getActionCommand().equals("Next"))
		{
			//Wahl ob Anmeldung als Manager oder MA der Rezeption
			s = (String) lf.cb.getSelectedItem();
			//System.out.println(s);
			if(s.equals("Rezeption"))
			{
				try{
					//Vergleich Passwort mit DB-Eintrag
					String passwort = selectDB("Select Passwort from hotel-seII.benutzer where Benutzername = 'Rezeption'");
					System.out.println(passwort);
					checkStringEmpty(lf.jpf.getText());
					if(lf.jpf.getText().equals(passwort))
					{
						//Starten als MA der Rezeption
						sf = new StartFrame();
						sf.setS(s);
						sf.launchStartFrame(sf.getJPanel2(), sf.getJPanel4());
						lf.jf.dispose();
						
					}
					else
					{
						try{
							checkLogin(lf.pw, lf.jpf.getText());
						}
						catch(GUIException gex)
						{
							JOptionPane.showMessageDialog(null, gex, "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				catch(GUIException gex)
				{
					JOptionPane.showMessageDialog(null, gex, "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(s.equals("Manager"))
			{

				try{
					//Passwort mit DB-Eintrag vergleichen
					String passwort = selectDB("Select Passwort from hotel-seII.benutzer where Benutzername = 'Manager'");
					checkStringEmpty(lf.jpf.getText());
					if(lf.jpf.getText().equals(passwort))
					{
						//Starten als Manager
						sf = new StartFrame();
						sf.setS(s);
						sf.launchStartFrame(sf.getJPanel2(), sf.getJPanel3());
						lf.jf.dispose();
					}
					else
					{
						try{
							checkLogin(lf.pw, lf.jpf.getText());
						}
						catch(GUIException gex)
						{
							JOptionPane.showMessageDialog(null, gex, "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}

				}
				
				catch(GUIException gex)
				{
					JOptionPane.showMessageDialog(null, gex, "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
	}




//Möglichkeit mit Enter zu bestätigen
	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
		{
			
			String s = (String) lf.cb.getSelectedItem();
			if(s.equals("Rezeption"))
			{
				try{
					//Passwort mit DB-Eintrag vergleichen
					String passwort = selectDB("Select Passwort from benutzer where Benutzername = 'Rezeption'");
					checkStringEmpty(lf.jpf.getText());
					if(lf.jpf.getText().equals(passwort))
					{
						//Als MA der Rezeption starten
						sf = new StartFrame();
						sf.setS(s);
						sf.launchStartFrame(sf.getJPanel2(), sf.getJPanel4());
						lf.jf.dispose();
					}
					else
					{
						try{
							checkLogin(passwort, lf.jpf.getText());
						}
						catch(GUIException gex)
						{
							JOptionPane.showMessageDialog(null, gex, "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}


				}
				catch(GUIException gex)
				{
					JOptionPane.showMessageDialog(null, gex, "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
			else if(s.equals("Manager"))
			{

				try{
					//Passwort mit DB-Eintrag vergleichen
					String passwort = selectDB("Select Passwort from benutzer where Benutzername = 'Manager'");
					checkStringEmpty(lf.jpf.getText());
					if(lf.jpf.getText().equals(passwort))
					{
						//Starten als Manager
						sf = new StartFrame();
						sf.setS(s);
						sf.launchStartFrame(sf.getJPanel2(), sf.getJPanel3());
						lf.jf.dispose();
					}
					else
					{
						try{
							checkLogin(passwort, lf.jpf.getText());
						}
						catch(GUIException gex)
						{
							JOptionPane.showMessageDialog(null, gex, "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}

				}
				catch(GUIException gex)
				{
					JOptionPane.showMessageDialog(null, gex, "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	




		@Override
		public void keyReleased(KeyEvent arg0) 
		{
			// TODO Auto-generated method stub

		}




		@Override
		public void keyTyped(KeyEvent arg0) 
		{
			// TODO Auto-generated method stub

		}
	}

