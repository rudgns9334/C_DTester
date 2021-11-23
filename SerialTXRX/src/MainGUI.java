import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.TimerTask;
import java.util.Timer;
import javax.swing.*;
import javax.swing.border.Border;

public class MainGUI extends JFrame implements ActionListener{
	JButton Set;
	JButton Path;
	JButton Start;
	JButton Fusing;
	JButton Terminal;
	Serial serial;
	Border border;
	ImageIcon image;
	int run=0;
	int fus = 0;
	WriteCSV csv;
	String[] func = {"충전", "방전", "일괄실험"};	
	JComboBox comboBox;
	
	JLabel OptionBoxName;
	JLabel SpecBoxName;
	JLabel StateBoxName;
	JLabel StateRun;
	JLabel Statemode;
	JLabel ChoiceFunction;
	JLabel constCrate;
	JLabel RestingTime;
	JLabel Min;
	JLabel VMax;
	JLabel VMin;
	JLabel IMax;
	JLabel IMin;
	JLabel FN;
	JLabel FP;
	JLabel Cycle;
	JLabel transportSpeed;
	
	JTextField InputC;
	JTextField InputRT;
	JTextField InputTime;
	JTextField InputVMax;
	JTextField InputVMin;
	JTextField InputIMax;
	JTextField InputIMin;
	JTextField filepath;
	JTextField filename;
	JTextField cyclecount;
	
	JRadioButton Crate1_5;
	JRadioButton Crate1_10;
	JRadioButton Crate_input;
	JRadioButton TimeButton;
	JRadioButton VoltButton;
	JRadioButton CurrentButton;
	
	ButtonGroup Cgroup;
	
	JPanel OptionSetting;
	JPanel BatterySpec;
	JPanel PathandSet;
	JPanel State;
	JPanel StartandStop;
	JPanel SettingBox;
	JPanel SpecBox;
	JPanel StateBox;
	JPanel TimeSetting;
	JPanel VoltSetting;
	JPanel CurrentSetting;
	
	public MainGUI(Serial serial) {
		this.serial = serial;
		border = BorderFactory.createLineBorder(Color.black,1);
		image = new ImageIcon("battery.JPG");
		OptionBoxName = new JLabel("Option Setting");
		SpecBoxName = new JLabel("Battery Specification");
		StateBoxName = new JLabel("State");
		StateRun = new JLabel("Ready");
		Statemode = new JLabel("");
		ChoiceFunction = new JLabel("Choice Function");
		constCrate = new JLabel("const C rate");
		RestingTime = new JLabel("Resting Time");
		Min = new JLabel("Min");
		VMax = new JLabel("Max");
		VMin = new JLabel("Min");
		IMax = new JLabel("Max");
		IMin = new JLabel("Min");
		FN = new JLabel("파일이름");
		FP = new JLabel("파일경로");
		Cycle = new JLabel("회/초");
		transportSpeed = new JLabel("통신속도");
		
		comboBox = new JComboBox(func);
		
		InputC = new JTextField();
		InputRT = new JTextField();
		InputTime = new JTextField();
		InputVMax = new JTextField();
		InputVMin = new JTextField();
		InputIMax = new JTextField();
		InputIMin = new JTextField();
		filename = new JTextField();
		filepath = new JTextField();
		cyclecount = new JTextField();
		
		Crate1_5 = new JRadioButton("1/5C");
		Crate1_10 = new JRadioButton("1/10C");
		Crate_input = new JRadioButton("");
		TimeButton = new JRadioButton("Time");
		VoltButton = new JRadioButton("Volt");
		CurrentButton = new JRadioButton("Current");
		
		Cgroup = new ButtonGroup();
		
		OptionSetting = new JPanel();
		BatterySpec = new JPanel();
		PathandSet = new JPanel();
		State = new JPanel();
		StartandStop = new JPanel();
		SettingBox = new JPanel();
		SpecBox = new JPanel();
		StateBox = new JPanel();
		TimeSetting = new JPanel();
		VoltSetting = new JPanel();
		CurrentSetting = new JPanel();
		
		Set = new JButton("set");
		Path = new JButton("find");
		Start = new JButton("Start");
		Terminal = new JButton("종료");
		Fusing = new JButton("Fusing");
		Fusing.setEnabled(false);
		
		/* 옵션세팅 */
		//OptionSetting.setBackground(Color.red);
		OptionSetting.setBounds(0, 0, 280, 420);
		OptionSetting.setLayout(null);
		
		OptionBoxName.setBounds(10,0,280,20);
		
		SettingBox.setBackground(Color.white);
		SettingBox.setBounds(10, 20, 270, 400);
		SettingBox.setBorder(border);
		SettingBox.setLayout(null);
		
		ChoiceFunction.setBounds(10, 0, 200, 20);
		comboBox.setBounds(10, 30, 80, 30);
		comboBox.addActionListener(this);
		comboBox.setSelectedIndex(0);
		
		constCrate.setBounds(10, 70, 200, 20);
		Crate1_5.setBounds(10, 90, 70, 20);
		Crate1_5.setBackground(Color.white);
		Crate1_10.setBounds(80, 90, 70, 20);
		Crate1_10.setBackground(Color.white);
		InputC.setBounds(170, 90, 70, 20);
		Crate_input.setBounds(150, 90, 20, 20);
		Crate_input.setBackground(Color.white);

		Cgroup.add(Crate1_5);
		Cgroup.add(Crate1_10);
		Cgroup.add(Crate_input);
		
		RestingTime.setBounds(10, 140, 200, 20);
		InputRT.setBounds(10, 160, 120, 20);
		InputRT.setText("0");
		Min.setBounds(130, 160, 50, 20);
		
		TimeSetting.setBackground(Color.white);
		TimeSetting.setBounds(0, 210, 90, 90);
		TimeSetting.setBorder(border);
		TimeSetting.setLayout(null);
		
		TimeButton.setBounds(10, 0, 70, 20);
		TimeButton.setBackground(Color.white);
		TimeButton.addActionListener(this);
		
		InputTime.setBounds(10, 30, 60, 20);
		InputTime.setText("0");
		InputTime.setEnabled(false);
		
		VoltSetting.setBackground(Color.white);
		VoltSetting.setBounds(90, 210, 90, 90);
		VoltSetting.setBorder(border);
		VoltSetting.setLayout(null);
		
		VoltButton.setBounds(10, 0, 70, 20);
		VoltButton.setBackground(Color.white);
		VoltButton.addActionListener(this);
		
		VMax.setBounds(10, 30, 50, 20);
		InputVMax.setBounds(35, 30, 40, 20);
		InputVMax.setText("0");
		InputVMax.setEnabled(false);
		VMin.setBounds(10, 60, 50, 20);
		InputVMin.setBounds(35, 60, 40, 20);
		InputVMin.setText("0");
		InputVMin.setEnabled(false);
		
		CurrentSetting.setBackground(Color.white);
		CurrentSetting.setBounds(180, 210, 90, 90);
		CurrentSetting.setBorder(border);
		CurrentSetting.setLayout(null);
		
		CurrentButton.setBounds(10, 0, 70, 20);
		CurrentButton.setBackground(Color.white);
		CurrentButton.addActionListener(this);
		
		IMax.setBounds(10, 30, 50, 20);
		InputIMax.setBounds(35, 30, 40, 20);
		InputIMax.setText("0");
		InputIMax.setEnabled(false);
		IMin.setBounds(10, 60, 50, 20);
		InputIMin.setBounds(35, 60, 40, 20);
		InputIMin.setText("0");
		InputIMin.setEnabled(false);
		
		transportSpeed.setBounds(10, 310, 80, 20);
		cyclecount.setBounds(10, 330, 80, 20);
		cyclecount.setText("1");
		Cycle.setBounds(90, 330, 50, 20);
		
		TimeSetting.add(TimeButton);
		TimeSetting.add(InputTime);
		
		VoltSetting.add(VoltButton);
		VoltSetting.add(VMax);
		VoltSetting.add(InputVMax);
		VoltSetting.add(VMin);
		VoltSetting.add(InputVMin);
		
		CurrentSetting.add(CurrentButton);
		CurrentSetting.add(IMax);
		CurrentSetting.add(InputIMax);
		CurrentSetting.add(IMin);
		CurrentSetting.add(InputIMin);
		
		SettingBox.add(ChoiceFunction);
		SettingBox.add(comboBox);
		SettingBox.add(constCrate);
		SettingBox.add(Crate1_5);
		SettingBox.add(Crate1_10);
		SettingBox.add(Crate_input);
		SettingBox.add(InputC);
		SettingBox.add(RestingTime);
		SettingBox.add(InputRT);
		SettingBox.add(Min);
		SettingBox.add(TimeSetting);
		SettingBox.add(VoltSetting);
		SettingBox.add(CurrentSetting);
		SettingBox.add(transportSpeed);
		SettingBox.add(cyclecount);
		SettingBox.add(Cycle);
		
		OptionSetting.add(OptionBoxName);
		OptionSetting.add(SettingBox);
		
		/* 베터리 스펙 입력 */
		//BatterySpec.setBackground(Color.blue);
		BatterySpec.setBounds(280, 0, 190, 180);
		BatterySpec.setLayout(null);
		
		SpecBoxName.setBounds(10, 0, 180, 20);
		
		SpecBox.setBackground(Color.white);
		SpecBox.setBounds(10, 20, 180, 160);
		SpecBox.setBorder(border);
		
		BatterySpec.add(SpecBoxName);
		BatterySpec.add(SpecBox);
		
		Fusing.setBounds(340, 220, 80, 30);
		Fusing.addActionListener(this);
		
		/* 파일 위치 정하기*/
		//PathandSet.setBackground(Color.cyan);
		PathandSet.setBounds(0, 420, 280, 180);
		PathandSet.setLayout(null);
		
		Path.setBounds(200, 30, 60, 20);
		Set.setBounds(200, 60, 60, 20);
		
		FP.setBounds(10, 30, 60, 20);
		FN.setBounds(10, 60, 60, 20);
		
		filepath.setBounds(70, 30, 130, 20);
		filename.setBounds(70, 60, 130, 20);
		filepath.setEnabled(false);
		
		Path.addActionListener(this);
		Set.addActionListener(this);
		
		PathandSet.add(Set);
		PathandSet.add(Path);
		PathandSet.add(FP);
		PathandSet.add(FN);
		PathandSet.add(filepath);
		PathandSet.add(filename);
		
		/* 상태 정보 */
		//State.setBackground(Color.gray);
		State.setBounds(280, 280, 190, 120);
		State.setLayout(null);
		
		StateBoxName.setBounds(10, 0, 180, 20);
		
		StateBox.setBackground(Color.white);
		StateBox.setBounds(10, 20, 180, 100);
		StateBox.setBorder(border);
		StateBox.setLayout(new GridLayout(2,1,10,10));
		
		StateRun.setVerticalAlignment(JLabel.TOP);
		StateRun.setHorizontalAlignment(JLabel.CENTER);
		
		Statemode.setVerticalAlignment(JLabel.TOP);
		Statemode.setHorizontalAlignment(JLabel.CENTER);
		
		StateBox.add(StateRun);
		StateBox.add(Statemode);
		
		State.add(StateBoxName);
		State.add(StateBox);
		
		/*시작 중지*/
		//StartandStop.setBackground(Color.black);
		StartandStop.setBounds(260, 440, 240, 160);
		StartandStop.setLayout(null);
		
		Start.setBounds(20, 50, 80, 30);
		Terminal.setBounds(120, 50, 80, 30);
		
		Start.addActionListener(this);
		Terminal.addActionListener(this);
		
		StartandStop.add(Start);
		StartandStop.add(Terminal);
		
		add(Fusing);
		add(OptionSetting);
		add(BatterySpec);
		add(PathandSet);
		add(State);
		add(StartandStop);
		
		setTitle("Test C/D");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setSize(500,600);
		setIconImage(image.getImage());
		setVisible(true);
	}
	
	public void SetState(String str) {
		Statemode.setText(str);
	}
	
	public void endSetting() {
		Start.setText("Start");
		StateRun.setText("Ready");
		run = 0;
	}
	
	public int GetCycle() {
		return Integer.parseInt(cyclecount.getText());
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==Path) {
			System.out.println("Path");
			JFileChooser jfc = new JFileChooser();
			jfc.setCurrentDirectory(new File("C:/Users/82109/Desktop/리벌스팩토리/GUI/battery"));
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			jfc.showDialog(this, null);
			File dir = jfc.getSelectedFile();
			filepath.setText(dir==null?"":dir.getPath());
		}
		else if(e.getSource()==Set) {
			System.out.println("Set");
			csv = new WriteCSV(filepath.getText(),filename.getText());
			serial.bridge(csv);
			if(!InputRT.getText().equals("")) {
				serial.setrestTime(Integer.parseInt(InputRT.getText()));
			}
		}
		else if(e.getSource()==Start) {
			if(run == 0) {
				System.out.println("Start");
				Start.setText("Stop");
				StateRun.setText("Running");
				Statemode.setText("charging");
				run = 1;
				if(comboBox.getSelectedItem().equals("충전")) {
					try {
						serial.sendString("충전");
						serial.OnlyCharging();
						serial.addData(1, 0);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if(comboBox.getSelectedItem().equals("방전")) {
					try {
						serial.sendString("방전");
						serial.OnlyDisCharging();
						serial.addData(3, 0);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else if(comboBox.getSelectedItem().equals("일괄실험")) {
					try {
						serial.sendString("충전");
						serial.addData(1, 0);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				try {
					this.serial.sendStart();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(run == 1) {
				System.out.println("Stop");
				endSetting();
				Statemode.setText("");
				try {
					this.serial.addData(0, 0);
					this.serial.sendStart();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		else if(e.getSource()==Fusing) {
			try {
				serial.addData(8, 0);
				float Vmax = Float.parseFloat(InputVMax.getText());
				float Vmin = Float.parseFloat(InputVMin.getText());
				float Imax = Float.parseFloat(InputIMax.getText());
				float Imin = Float.parseFloat(InputIMin.getText());
				
				Vmax = Vmax * 200;
				Vmin = Vmin * 200;
				Imax = Imax * 10000;
				Imin = Imin * 10000;
				
				byte data;
				data = (byte) (((int)Vmax)&0xFF);
				serial.addData(data, 1);
				data = (byte) ((((int)Vmax)>>8)&0xFF);
				serial.addData(data, 2);
				data = (byte) (((int)Vmin)&0xFF);
				serial.addData(data, 3);
				data = (byte) ((((int)Vmin)>>8)&0xFF);
				serial.addData(data, 4);
				data = (byte) (((int)Imax)&0xFF);
				serial.addData(data, 5);
				data = (byte) ((((int)Imax)>>8)&0xFF);
				serial.addData(data, 6);
				data = (byte) (((int)Imin)&0xFF);
				serial.addData(data, 7);
				data = (byte) ((((int)Imin)>>8)&0xFF);
				serial.addData(data, 8);
				serial.addData(Integer.parseInt(cyclecount.getText()), 9);
				serial.sendFusing();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getSource()==TimeButton) {
			System.out.println("Time");
			if(InputTime.isEnabled()) {
				InputTime.setEnabled(false);
			}
			else {
				InputTime.setEnabled(true);
			}
		}
		else if(e.getSource()==VoltButton) {
			System.out.println("Volt");
			if(VoltButton.isSelected()) {
				Fusing.setEnabled(true);
			}
			if(!VoltButton.isSelected() && !CurrentButton.isSelected()) {
				Fusing.setEnabled(false);
			}
			if(InputVMax.isEnabled() && InputVMin.isEnabled()) {
				InputVMax.setEnabled(false);
				InputVMin.setEnabled(false);
			}
			else {
				InputVMax.setEnabled(true);
				InputVMin.setEnabled(true);
			}
		}
		else if(e.getSource()==CurrentButton) {
			System.out.println("Current");
			if(CurrentButton.isSelected()) {
				Fusing.setEnabled(true);
			}
			if(!VoltButton.isSelected() && !CurrentButton.isSelected()) {
				Fusing.setEnabled(false);
			}
			if(InputIMax.isEnabled() && InputIMin.isEnabled()) {
				InputIMax.setEnabled(false);
				InputIMin.setEnabled(false);
			}
			else {
				InputIMax.setEnabled(true);
				InputIMin.setEnabled(true);
			}
		}						
		else if(e.getSource()==comboBox) {
			
		}
		else if(e.getSource()==Terminal) {
			System.exit(0);
		}
	}
//	public static void main(String[] args) {
////		 TODO Auto-generated method stub
//		new MainGUI();
//	}

}
