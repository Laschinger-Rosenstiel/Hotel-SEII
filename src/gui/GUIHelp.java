package gui;

import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

public class GUIHelp {
	//Y-Koordinaten
	int y_line1 = 40;
	int y_line2 =80;
	int y_line3 =120;
	int y_line4 = 160;
	int y_line5 = 200;
	int y_line6 = 240;
	int y_line7 = 280;
	int y_line8 = 320;
	int y_line9 = 360;
	int y_line10 = 400;
	int y_line11 = 440;
	int y_line12 = 480;
	int y_line13 = 520;
	int y_line14 = 560;
	//X-Koordinaten
	int x_column1 = 10;
	int x_column2 = 40;
	int x_column3 = 150;
	int x_column4 = 300;
	int x_column5= 450;
	int x_column6= 470;
	//Standardgrößen
	int x_width = 150;
	int y_height = 20;
	
	Date now = new Date();
	Calendar calendar = new GregorianCalendar();
	
	

public JTextField setTfForm (JTextField tf){
	Font fontTf=new Font(tf.getFont().getName(),Font.BOLD,tf.getFont().getSize());
	tf.setEditable(false);
	tf.setBorder(null);
	tf.setFont(fontTf);
	tf.setOpaque(true);
	tf.setBackground(new Color(209,218,248));
	
	return tf;
}

public void setGebRoom(JDateChooser geb) {
	calendar.setTime(now);
	calendar.add(Calendar.YEAR, -120);
	Date past = calendar.getTime();
	geb.setSelectableDateRange(past, now);
}

public String getSQLDate(Date date) {
	SimpleDateFormat Sql =new SimpleDateFormat("yyyy-MM-dd");
	return Sql.format(date);
}

}
