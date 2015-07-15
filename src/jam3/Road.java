package jam3;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Road implements ActionListener{
	
	private int PROBABILITY = 50;
	private final int MAX = 50;
	private int SIGNALPOINT = MAX/2;
	
	private int numberOfJam = 0;
	private int mostLengthJam = 0;
	private int allTimeMostLengthJam = 0;
	
	private boolean signalcolor = false;
	private boolean AutoAdvance = false;
	
	private boolean slowLane[] = new boolean[MAX];
	private boolean fastLane[] = new boolean[MAX];
	
	private Timer timer;
	
	Road(){
		timer = new Timer(1000 , this);
		timer.setActionCommand("timer");
	}
	
	public void advance(){
		boolean bb[] = new boolean[MAX];
		bb = getSlowLane();
		advanceCar(bb);
		setFaseLane(bb);
		countJam(bb);
		MAXJamLength(bb);
		if(allTimeMostLengthJam < mostLengthJam){
			allTimeMostLengthJam = mostLengthJam;
		}
	}
	
	public void autoAdvanceStart(){
		AutoAdvance = true;
		timer.start();
	}
	
	public void autoAdvanceStop(){
		timer.stop();
		AutoAdvance = false;
	}
	
	public void MAXJamLength(boolean jam[]){
		int count = 0;
		int tmp = 0;
		int j;
		for(int i=1; i<jam.length; i++){
			if(jam[i-1] == true){
				for(j=i-1; j<jam.length-1; j++){
					if(jam[j] == true){
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
		setMostLengthJam(tmp);
	}
	
	public void countJam(boolean jam[]){
		int count = 0;
		int j;
		for(int i=1; i<jam.length; i++){
			if(jam[i-1]==true && jam[i]==true){
				count++;
				for(j=i; j<jam.length-1; j++){
					if(jam[j] == false){
						break;
					}
				}
				i = j + 1;
			}
		}
		setNumberOfJam(count);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if(AutoAdvance){
			slowLane = getSlowLane();
			advanceCar(slowLane);
			setFaseLane(slowLane);
		}
		countJam(slowLane);
		MAXJamLength(slowLane);
		if(allTimeMostLengthJam < mostLengthJam){
			allTimeMostLengthJam = mostLengthJam;
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

	public void setFaseLane(boolean[] jam) {
		this.slowLane = jam;
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

	public int getNumberOfJam() {
		return numberOfJam;
	}

	public void setNumberOfJam(int numberOfJam) {
		this.numberOfJam = numberOfJam;
	}

	public int getMostLengthJam() {
		return mostLengthJam;
	}

	public void setMostLengthJam(int mostLengthJam) {
		this.mostLengthJam = mostLengthJam;
	}

	public int getAllTimeMostLengthJam() {
		return allTimeMostLengthJam;
	}

	public void setAllTimeMostLengthJam(int allTimeMostLengthJam) {
		this.allTimeMostLengthJam = allTimeMostLengthJam;
	}


	


}
