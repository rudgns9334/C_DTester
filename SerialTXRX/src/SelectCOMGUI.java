import javax.swing.*;

import gnu.io.CommPortIdentifier;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class SelectCOMGUI extends JFrame implements ActionListener{

	JButton connect;
	JButton close;
	JLabel label;
	JComboBox comboBox;
	SelectCOMGUI(){
		
		connect = new JButton("연결");
		close = new JButton("닫기");
		label = new JLabel("포트를 골라주세요.");
		List<String> coms = new ArrayList<String>();
		Enumeration portList = CommPortIdentifier.getPortIdentifiers();
		CommPortIdentifier port ; 
        while ( portList.hasMoreElements()) { 
            port = (CommPortIdentifier)portList.nextElement (); 
            coms.add(port.getName()); 
        }
        String[] com = coms.toArray(new String[coms.size()]);
		
        comboBox = new JComboBox(com);
        
        label.setBounds(100, 50, 120, 40);
		comboBox.setBounds(100, 100, 80, 30);
		connect.setBounds(200, 200, 80, 30);
		close.setBounds(300, 200, 80, 30);
		
		connect.addActionListener(this);
		close.addActionListener(this);
		
		this.add(label);
		this.add(comboBox);
		this.add(connect);
		this.add(close);
		this.setTitle("select Port");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(420,320);
		this.setLayout(null);
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==connect) {
			this.dispose();
			Serial serial = new Serial();
			MainGUI gui = new MainGUI(serial);
			try {
				serial.connect(String.valueOf(comboBox.getSelectedItem()), gui);
				System.out.println(String.valueOf(comboBox.getSelectedItem()));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		else if(e.getSource()==close) {
			System.exit(0);
		}
	}

}
