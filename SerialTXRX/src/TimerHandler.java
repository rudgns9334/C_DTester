import java.util.Timer;
import java.util.TimerTask;

public class TimerHandler {

	Timer timer;
	TimerTask task;	
	Serial serial;
	private int restingTime;
	private int mode;
	private boolean onlyC = false;
	private boolean onlyD = false;
	
	public TimerHandler(Serial serial) {
		this.serial = serial;
		mode = 0;
	}

	public void setrestingTime(int restTime) {
		this.restingTime = restTime;
	}
	
	public void SetOnlyCharging() {
		onlyC = true;
	}
	
	public void SetOnlyDisCharging() {
		onlyD = true;
	}
	
	public void TimerOn() {
		timer = new Timer();
		task = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					switch(mode) {
					case 0:
						System.out.println("charging-resting-end");
						serial.SetGUIState("discharging");
						serial.sendString("방전,,,DC_IR,Q");
						serial.addData(3, 0);
						mode = 1;
						break;
					case 1:
						System.out.println("discharging-resting-end");
						serial.SetGUIState("pulse-charging");
						serial.sendString("펄스 충전,,,SOC");
						serial.addData(5, 0);
						mode = 2;
						break;
					case 2:
						System.out.println("pulse-charging-end");
						serial.SetGUIState("pulse-discharging");
						serial.sendString("펄스 방전,,,SOC");
						serial.addData(7, 0);
						mode = 3;
						break;
					case 3:
						System.out.println("pulse-discharging-end");
						serial.SetGUIState("End");
						serial.GUIendSetting();
						serial.addData(0, 0);
						mode = 0;
						break;
					}
					serial.sendStart();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
		};
		if(onlyD) {
			mode = 1;
		}
		switch (mode) {
		case 0:
			if(onlyC) {
				System.out.println("End");
				serial.SetGUIState("End");
				serial.GUIendSetting();
				onlyC = false;
				try {
					serial.addData(0, 0);
					serial.sendStart();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				System.out.println("charging-resting");
				serial.SetGUIState("charging-resting");
				timer.schedule(task, restingTime*1000*60);
			}
			break;
		case 1:
			if(onlyD) {
				System.out.println("End");
				serial.SetGUIState("End");
				serial.GUIendSetting();
				onlyD = false;
				try {
					serial.addData(0, 0);
					serial.sendStart();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				System.out.println("discharging-resting");
				serial.SetGUIState("discharging-resting");
				timer.schedule(task, restingTime*1000*60);
			}
			break;
		case 2:
			System.out.println("pulse-charging-resting");
			serial.SetGUIState("pulse-charging-resting");
			timer.schedule(task, restingTime*1000*60);
			break;
		case 3:
			System.out.println("pulse-discharging-resting");
			serial.SetGUIState("pulse-discharging-resting");
			timer.schedule(task, restingTime*1000*60);
			break;
		}
	}
	
//	public void ChargingRest() {
//		try {
//			System.out.println("charging-resting-end");
//			serial.SetGUIState("discharging");
//			serial.sendString("방전");
//			serial.addData(3, 0);
//			serial.sendStart();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		mode = 1;
//	}
}
