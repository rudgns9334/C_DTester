import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class WriteCSV {

	private String filepath;
	private String filename;
	private String makepath;
	private float total_Q = 0;
	private float char_Q = 0;
	private float dischar_Q = 0;
	private int V=0;
	private int Pre_V = 0;
	private int I=0;
	private boolean dataIn = false;
	private Serial serial;
	
	public WriteCSV(String filepath, String title){
		this.filepath = filepath;
		this.filename = title;
		
		this.makepath = this.filepath + "/" + this.filename + ".csv";
		
		File file = null;
		BufferedWriter bw = null;
		String NewLine = System.lineSeparator();
		
		try {
			file = new File(this.filepath);
			if(!file.exists()) {
				file.mkdirs();
			}
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(makepath),"EUC-KR"));
			
			bw.write("시간, 전압, 전류");
			bw.write(NewLine);
			
			bw.flush();
			bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void SetSerial(Serial serial) {
		this.serial = serial;
	}
	
	public void StringCSV(String str) {
		BufferedWriter bw = null;
		String NewLine = System.lineSeparator();
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(makepath,true),"EUC-KR"));
			bw.write(str);
			bw.write(NewLine);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void SendCSV(String time, byte[] buffer, boolean[] mode) {
		BufferedWriter bw = null;
		String NewLine = System.lineSeparator();
		int cycle;
		float Q;
		String data = null;
		
		V = (int)(((buffer[1] & 0xFF)<< 8) | (buffer[0] & 0xFF));
		I = (int)(((buffer[3] & 0xFF)<< 8) | (buffer[2] & 0xFF));
		V = V*5;
		I = I/10;
		cycle = serial.GetCycle();
		Q = I / cycle;
		if(mode[0]) {
			float DC_IR = (float)V/I;
			this.total_Q += Q;
			data = time + "," + Integer.toString(V) + "," + Integer.toString(I) + "," + String.valueOf(DC_IR) + "," + String.valueOf(this.total_Q);
			serial.SetV(Integer.toString(V));
			serial.SetI(Integer.toString(I));
			serial.SetDCIR(String.valueOf(DC_IR));
			serial.SetQ(String.valueOf(this.total_Q));
			dataIn = true;
			
		}
		else if(mode[1]) {
			if(I==0) {
				Pre_V = V;
			}
			else {
				char_Q += Q;
				float SOC = char_Q/total_Q * 100;
				if(Pre_V != 0) {
					data = time + "," + Integer.toString(Pre_V) + "," + Integer.toString(I) + "," + String.valueOf(SOC);
					serial.SetV(Integer.toString(Pre_V));
					serial.SetI(Integer.toString(I));
					serial.SetSOC(String.valueOf(SOC));
				}
				else {
					data = time + "," + Integer.toString(V) + "," + Integer.toString(I) + "," + String.valueOf(SOC);
					serial.SetV(Integer.toString(V));
					serial.SetI(Integer.toString(I));
					serial.SetSOC(String.valueOf(SOC));
				}
				
				Pre_V = 0;
				dataIn = true;
			}
		}
		else if(mode[2]) {
			if(I==0) {
				Pre_V = V;
			}
			else {
				dischar_Q += Q;
				float SOC = (total_Q-dischar_Q)/total_Q * 100;
				if(Pre_V != 0) {
					data = time + "," + Integer.toString(Pre_V) + "," + Integer.toString(I) + "," + String.valueOf(SOC);
					serial.SetV(Integer.toString(Pre_V));
					serial.SetI(Integer.toString(I));
					serial.SetSOC(String.valueOf(SOC));
				}
				else {
					data = time + "," + Integer.toString(V) + "," + Integer.toString(I) + "," + String.valueOf(SOC);
					serial.SetV(Integer.toString(V));
					serial.SetI(Integer.toString(I));
					serial.SetSOC(String.valueOf(SOC));
				}
				
				Pre_V = 0;
				dataIn = true;
			}
			
		}
		else {
			data = time + "," + Integer.toString(V) + "," + Integer.toString(I);
			serial.SetV(Integer.toString(V));
			serial.SetI(Integer.toString(I));
			dataIn = true;
		}
		try {
			bw = new BufferedWriter(new FileWriter(this.makepath,true));
			if(dataIn) {
				bw.write(data);
				bw.write(NewLine);
				dataIn = false;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bw.flush();
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public int Byte2Short(byte[] data) {
		ByteBuffer buffer = ByteBuffer.allocate(2);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.put(data[0]);
		buffer.put(data[1]);
		int shortdata = buffer.getShort();
		return shortdata;
	}

}
