import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.sun.corba.se.impl.ior.ByteBuffer;

public class SerialWriter implements Runnable {
	OutputStream out;
	
	public SerialWriter(OutputStream out) {
		this.out = out;
	}
	
	static String bytesToBinaryString(Byte b) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            builder.append(((0x80 >>> i) & b) == 0 ? '0' : '1');
        }

        return builder.toString();
    }
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			int c = 0;
			while((c = System.in.read())>-1) {
				this.out.write(c);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
