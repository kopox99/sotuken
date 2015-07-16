package jam3;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Road implements ActionListener{
	
	private int PROBABILITY = 50;
	private final int MAX = 50;
	private int SIGNALPOINT = MAX/2;
	
	private boolean signalcolor = false;
	private boolean AutoAdvance = false;
	
	private boolean slowLane[] = new boolean[MAX];
	private boolean fastLane[] = new boolean[MAX];
	
	private Timer timer;
	
	Road(){
		timer = new Timer(1000 , this);
		timer.setActionCommand("timer");
	}
	
	public boolean[] advance(){
		boolean bb[] = new boolean[MAX];
		bb = getSlowLane();
		advanceCar(bb);
		setSlowLane(bb);
		return bb;
	}
	
	public void autoAdvanceStart(){
		AutoAdvance = true;
		timer.start();
	}
	
	public void autoAdvanceStop(){
		timer.stop();
		AutoAdvance = false;
	}
	
	public int MAXJamLength(){
		int count = 0;
		int tmp = 0;
		int j;
		for(int i=1; i<slowLane.length; i++){
			if(slowLane[i-1] == true){
				for(j=i-1; j<slowLane.length-1; j++){
					if(slowLane[j] == true){
						count++;
					}else{
						break;
					}
				}
				if(tmp < count){
					tmp = count;
				}
				count = 0;
				i = j + 1;
			}
		}
		return tmp;
	}
	
	public int countJam(){
		int count = 0;
		int j;
		for(int i=1; i<slowLane.length; i++){
			if(slowLane[i-1]==true && slowLane[i]==true){
				count++;
				for(j=i; j<slowLane.length-1; j++){
					if(slowLane[j] == false){
						break;
					}
				}
				i = j + 1;
			}
		}
		return count;
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if(AutoAdvance){
			slowLane = getSlowLane();
			advanceCar(slowLane);
			setFastLane(slowLane);
		}
		
	}
	
	private boolean[] advanceCar(boolean[] jam){
		boolean check[] = new boolean[MAX];
		check = checkRow(jam);
		jam[MAX-1] = false;
		for(int i=0; i < MAX-1; i++){
			if(check[i] == true){
				jam[i+1] = jam[i];
				jam[i] = false;
			}
		}
		if(jam[0] != true && jam[1] != true){
			appear(jam);
		}
		return jam;
	}
	
	private boolean[] checkRow(boolean[] jam){
		boolean check[] = new boolean[MAX];
		for(int i=0; i < MAX; i++){
			check[i] = false;
		}
		for(int i=0; i < MAX-1; i++){
			if(jam[i]==true && jam[i+1]==false){
				check[i] = true;
			}
		}
		if(signalcolor)
			check[SIGNALPOINT] = false;
		return check;
	}
	
	private void appear(boolean[] jam){
		if(Math.random() * 100 > 100 - PROBABILITY){
			jam[0] = true;
		}else{
			jam[0] = false;
		}
	}
	
	private void changeLane(){
		for(int i=1; i<MAX-1; i++){
			if(fastLane[i-1]==false && fastLane[i]==false && fastLane[i+1]==false){
				fastLane[i] = slowLane[i];
				slowLane[i] = false;
			}
		}
	}
	
	public void setProbability(int i){
		this.PROBABILITY = i;
	}
	
	public int getProbability() {
		return PROBABILITY;
	}

	public int getMAX() {
		return MAX;
	}

	public int getSIGNALPOINT() {
		return SIGNALPOINT;
	}
	
	public boolean[] getSlowLane() {
		return this.slowLane;
	}

	
	public void setSiganl(boolean i){
		this.signalcolor = i;
	}

	public boolean[] getFastLane() {
		return fastLane;
	}

	public void setFastLane(boolean fastLane[]) {
		this.fastLane = fastLane;
	}

	public void setSlowLane(boolean slowLane[]) {
		this.slowLane = slowLane;
	}

	


}
