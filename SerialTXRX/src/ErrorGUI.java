import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ErrorGUI extends JFrame implements ActionListener{
	
	JLabel label;
	JLabel label2;
	JButton button;
	
	ErrorGUI(int mode){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(300,200);
		
		label = new JLabel("Error 발생");
		label.setBounds(100, 50, 100, 50);
		
		if(mode == 0) {
			label2 = new JLabel("너무 많은 데이터가 들어옵니다.");
			label.setBounds(100, 100, 100, 50);
			this.add(label2);
		}
		else if(mode == 1) {
			label2 = new JLabel("전류의 최소값은 0이 될 수 없습니다.");
			label.setBounds(100, 100, 100, 50);
			this.add(label2);
		}
		
		button = new JButton();
		button.setText("닫기");
		button.setBounds(130, 150, 40, 30);
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
