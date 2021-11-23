import java.io.InputStream;
import java.io.OutputStream;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class Serial {
	private CommPort commPort;
	private InputStream in;
	private OutputStream out;
	private byte[] bytes = new byte[10];
	private WriteCSV csv;
	private SerialReader sr;
	private SerialWriter sw;
	private int restTime;
	private TimerHandler timer;
	private MainGUI gui;
	
	public Serial() {
		super();
	}
	
	static String bytesToBinaryString(Byte b) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            builder.append(((0x80 >>> i) & b) == 0 ? '0' : '1');
        }

        return builder.toString();
    }

	void connect(String portName, MainGUI gui) throws Exception {
		this.gui = gui;
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		if (portIdentifier.isCurrentlyOwned()) {
			System.out.println("Error: Port is currently in use.");
		}
		else {
			this.commPort = portIdentifier.open(this.getClass().getName(), 2000);
			
			if (commPort instanceof SerialPort) {
				SerialPort serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
				
				this.in = serialPort.getInputStream();
				this.out = serialPort.getOutputStream();
				
				sr = new SerialReader(in);
				sw = new SerialWriter(out);
				timer = new TimerHandler(this);
				
				Thread Input = new Thread(sr);
				Thread Output = new Thread(sw);
				
				Input.start();
				Output.start();
			}
			else {
				commPort.close();
				System.out.println("Error: serial ports No");
			}
		}
	}
	
	void disconnect(String portName) throws Exception{
		
	}
	
	void addData(Integer data, Integer index) throws Exception {
		byte b;
		b = data.byteValue();
		bytes[index] = b;
	}
	
	void addData(byte data, Integer index) throws Exception {
		bytes[index] = data;
	}
	
	void sendFusing() throws Exception{
		for(int i=0;i<bytes.length;i++) {
			System.out.println(bytesToBinaryString(bytes[i]));
		}
		this.out.write(bytes);
	}
	
	void sendStart() throws Exception{
		System.out.println(bytesToBinaryString(bytes[0]));
		this.out.write(bytes[0]);
	}
	
	
//	void sendData(Integer in) throws Exception{
//		byte bytes;
//		bytes = in.byteValue();
//		System.out.println(bytesToBinaryString(bytes));
//		this.out.write(bytes);
//	}
	
	void bridge(WriteCSV csv) {
		this.csv = csv;
		this.csv.SetSerial(this);
		sr.bridgeCSV(this.csv);
	}
	
	void setrestTime(int restTime) {
		this.restTime = restTime;
		timer.setrestingTime(this.restTime);
		sr.setTimer(timer);
	}
	
	void SetGUIState(String str) {
		gui.SetState(str);
	}
	
	void sendString(String str) {
		csv.StringCSV(str);
	}
	
	void GUIendSetting() {
		gui.endSetting();
	}
	
	void OnlyCharging() {
		timer.SetOnlyCharging();
	}

	void OnlyDisCharging() {
		timer.SetOnlyDisCharging();
	}
	
	int GetCycle() {
		return gui.GetCycle();
	}
}
