package gui;

import java.awt.*;

import javax.swing.*;

//import Control.ButtonHandlerChange;
import control.ButtonHandlerLogin;


public class LoginFrame extends GUIHelp 
{
	//Login Frame
	public JFrame jf;
	private JButton b1,b2;
	public JLabel l1,l2;
	public JComboBox cb;
	public JPasswordField jpf;
	public String[] konto = {"Rezeption", "Manager"};
	public String pw;
	//public String pw ="lala";
	//ChangeFrame
	public JFrame cf;
	public JLabel l3,l4,l5;
	public JPasswordField jpf2,jpf3, jpf4;
	private JButton b3,b4;
	
	
	public LoginFrame()
	{
		//LoginFrame
		jf = new JFrame("Anmeldung");
		b1 = new JButton("Anmelden");
		b1.setActionCommand("Next");
		b1.addActionListener(new ButtonHandlerLogin(this));
		b2 = new JButton("Passwort ändern");
		b2.setActionCommand("Change");
		b2.addActionListener(new ButtonHandlerLogin(this));		
		l1 = new JLabel("Konto", JLabel.CENTER);
		l2 = new JLabel("Passwort", JLabel.CENTER);
		jpf = new JPasswordField(40);
		jpf.addKeyListener(new ButtonHandlerLogin(this));
		cb = new JComboBox(konto);
		//cb.setSelectedIndex(2);
		cb.addActionListener(new ButtonHandlerLogin(this));

		//ChangeFrame
		cf = new JFrame("Passwort ändern");
		b3 = new JButton("Passwort speichern");
		b3.setActionCommand("SavePw");
		b3.addActionListener(new ButtonHandlerLogin(this));
		b4 = new JButton("Zurück");
		b4.setActionCommand("BackToLogin");
		b4.addActionListener(new ButtonHandlerLogin(this));
		jpf2 = new JPasswordField(40);
		jpf3 = new JPasswordField(40);
		jpf4 = new JPasswordField(40);
		jpf4.addKeyListener(new ButtonHandlerLogin(this));
		l5 = new JLabel("Altes Passwort", JLabel.CENTER);
		l3 = new JLabel("Neues Passwort", JLabel.CENTER);
		l4 = new JLabel("Bestätigen", JLabel.CENTER);
		
		
	}
	
	
	public void launchLoginFrame()
	{
		//Panel erzeugt und befüllt
		JPanel panel = new JPanel();
		panel.setLayout(null);
		l1.setBounds(10, 40,120, 30);
		panel.add(l1);
		cb.setBounds(350, 40, 120, 30);
		panel.add(cb);
		l2.setBounds(10, 80, 120, 30);
		panel.add(l2);
		jpf.setBounds(350, 80, 120, 30);
		panel.add(jpf);
		b2.setBounds(10, 120, 300, 30);
		panel.add(b2);
		b1.setBounds(350, 120, 300, 30);
		panel.add(b1);
		panel.setOpaque(true);
		panel.setBackground(new Color(209,218,248));
		jf.add(panel);
		jf.setSize(700, 300);
		jf.setLocation(200, 200);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		
		
		
	}
	
	public void launchChangeFrame()
	{
		//Panel erzeugt und befüllt
		JPanel panel = new JPanel();
		panel.setLayout(null);
		l5.setBounds(10, 10,120, 30);
		panel.add(l5);
		jpf4.setBounds(350, 10, 120, 30);
		panel.add(jpf4);
		l3.setBounds(10, 50,120, 30);
		panel.add(l3);
		jpf2.setBounds(350, 50, 120, 30);
		panel.add(jpf2);
		l4.setBounds(10, 90, 120, 30);
		panel.add(l4);
		jpf3.setBounds(350, 90, 120, 30);
		panel.add(jpf3);
		b4.setBounds(10, 130, 300, 30);
		panel.add(b4);
		b3.setBounds(350, 130, 300, 30);
		panel.add(b3);
		panel.setOpaque(true);
		panel.setBackground(new Color(209,218,248));
		//Panel an Frame übergeben
		cf.add(panel);
		cf.setSize(700, 300);
		cf.setLocation(400, 200);
		cf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		cf.setVisible(true);
	}
	
	public void setPw(String x)
	{
		pw = x;
	}
	
	public static void main(String[] args)
	{
		LoginFrame bla = new LoginFrame();
		bla.launchLoginFrame();
	}
}

