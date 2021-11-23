import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ErrorGUI extends JFrame implements ActionListener{
	
	JLabel label;
	JButton button;
	
	ErrorGUI(){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(300,200);
		
		label = new JLabel("Error 발생");
		label.setBounds(100, 50, 100, 80);
		
		button = new JButton();
		button.setText("닫기");
		button.setBounds(130, 130, 40, 30);
		button.addActionListener(this);
		
		
		this.add(label);
		this.add(button);
		this.setLayout(null);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==button) {
			System.exit(0);
		}
	}
}
