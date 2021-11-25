import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.text.SimpleDateFormat;

public class SerialReader implements Runnable{
	private InputStream in;
	private WriteCSV csv;
	private int type=0;
	private boolean mode[];
	private TimerHandler timer;
	
	public SerialReader(InputStream in) {
		this.in = in;
		mode = new boolean[3];
		Arrays.fill(mode,false);
	}
	
	static String bytesToBinaryString(Byte b) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            builder.append(((0x80 >>> i) & b) == 0 ? '0' : '1');
        }

        return builder.toString();
    }

	void bridgeCSV(WriteCSV csv) {
		this.csv = csv;
	}
	
	void setTimer(TimerHandler timer) {
		this.timer = timer;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		byte[] buffer = new byte[1024];
		int len = -1;
		try {
//			InputStreamReader isr = new InputStreamReader(this.in, "UTF-8");
//			BufferedReader br = new BufferedReader(new InputStreamReader(this.in,"UTF-8"));
//			System.out.println(br.readLine());
			while ((len = in.read(buffer)) > -1) {
				byte[] data = new byte[len];
				for(int i=0;i<len;i++) {
					System.out.println(bytesToBinaryString(buffer[i]) + "   " + i);
					data[i] = buffer[i];
				}
				//System.out.print(new String(buffer,0,len,"UTF-8"));
				
				if(len > 7) {
					new ErrorGUI(0);
				}
				
				if(type==1 && len>1) {
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date time = new Date();
					String time1 = format1.format(time);
					System.out.println(" " + time1);
					csv.SendCSV(time1, data, mode);
				}
				if(len == 1) {
					int signal = data[0];
					if(signal>=8 || signal<0) {
//						System.out.println("haha");
					}
					else if(signal == 0) {
//						System.out.println("STTTTOP");
					}
					else {
						switch(signal) {
						case 3:
							mode[0] = true;
							mode[1] = false;
							mode[2] = false;
							break;
						case 5:
							mode[0] = false;
							mode[1] = true;
							mode[2] = false;
							break;
						case 7:
							mode[0] = false;
							mode[1] = false;
							mode[2] = true;
							break;
						default:
							mode[0] = false;
							mode[1] = false;
							mode[2] = false;
							break;
						}
						
						int dt = data[0]&0x01;
						if(dt==0) {
							type=0;
							timer.TimerOn();
						}
						else if(dt == 1) {
							type=1;
						}
					}
				}
					
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
