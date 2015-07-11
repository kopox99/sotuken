package jam3;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Signal implements ActionListener{
	
	private int ChangeSignalToRed = 0;
	private int ChangeSignalToBlue = 0;
	
	private int StateRed = 1;
	private int StateBlue = 1;
	
	private boolean SignalColor = false;
	private boolean AutoSignal = false;
	
	private Timer timer;
	
	Signal(){
		timer = new Timer(1000 , this);
		timer.setActionCommand("timer");
	}
	
	public void signalChange(){
		if(!SignalColor){
			SignalColor = true;
		}
		else{
			SignalColor = false;
		}
	}
	
	public void AutoSignalStart(){
		timer.start();
		AutoSignal = true;
	}
	
	public void AutoSignalStop(){
		timer.stop();
		AutoSignal = false;
		ChangeSignalToBlue = 0;
		ChangeSignalToRed = 0;
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd = e.getActionCommand();
		
		/*信号自動変化*/
		if(AutoSignal){
			if(cmd.equals("timer")){
				if(!SignalColor){
					ChangeSignalToRed++;
//					System.out.println(ChangeSignalToRed);
					if(ChangeSignalToRed ==  getBlue()){
						setSignalColor(true);
						ChangeSignalToRed = 0;
					}
				}//青信号の時の処理
				else{
					ChangeSignalToBlue++;
//					System.out.println(ChangeSignalToBlue);
					if(ChangeSignalToBlue ==  getRed()){
						setSignalColor(false);
						ChangeSignalToBlue = 0;
					}
				}//赤信号の時の処理
			}
		}else{}
		
	}//end of ActionPerformed

	public void setRed(int i){
		this.StateRed = i;
	}
	
	public int getRed() {
		return StateRed;
	}
	
	public void setBlue(int i){
		this.StateBlue = i;
	}
	
	public int getBlue() {
		return StateBlue;
	}

	public boolean isSignalColor() {
		return SignalColor;
	}

	public void setSignalColor(boolean signalColor) {
		SignalColor = signalColor;
	}
	

	
}
