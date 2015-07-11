package jam3;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GPanel extends JFrame implements ActionListener, ChangeListener{
	
	private int tmpCount = 0;
	
	private JButton AdvanceButton;
	private JButton trafficLight;
	private JLabel SignalLabel;
	private JLabel SliderLabel;
	private JLabel CountJamLabel;
	private JLabel MAXJamLengthLabel;
	private JLabel MAXMAXJamLengthLabel;
	private JLabel jamState;
	private JLabel jamState2;
	private JCheckBox AutoCheckBox;
	private JCheckBox AutoSignalCheckBox;
	private JSlider slider;
	private JSpinner ToRedSpinner;
	private JSpinner ToBlueSpinner;
	private SpinnerNumberModel ToRedmodel;
	private SpinnerNumberModel ToBluemodel;
	private Timer timer;
	
	private Signal signal = new Signal();
	private Road road = new Road();
	
	GPanel(){
		
		setTitle("jam");
		setSize(850, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel OverPanel= new JPanel();
		JPanel UnderPanel = new JPanel();
		JPanel CenterPanel = new JPanel();
		JPanel BluePanel = new JPanel();
		JPanel RedPanel = new JPanel();
		JPanel SliderPanel = new JPanel();
		JPanel RightPanel = new JPanel();
		CountJamLabel = new JLabel("渋滞の長さ" );
		MAXJamLengthLabel = new JLabel("最大の渋滞の長さ");
		MAXMAXJamLengthLabel = new JLabel("今までの最大の渋滞の長さ" );
		jamState = new JLabel();
		jamState2 = new JLabel("aaaa");
		jamState.setVerticalAlignment(JLabel.BOTTOM);
		jamState2.setVerticalAlignment(JLabel.TOP);
		CenterPanel.setLayout(new GridLayout(2, 1));
		BluePanel.setLayout(new BoxLayout(BluePanel, BoxLayout.Y_AXIS));
		RedPanel.setLayout(new BoxLayout(RedPanel, BoxLayout.Y_AXIS));
		SliderPanel.setLayout(new BoxLayout(SliderPanel, BoxLayout.Y_AXIS));
		RightPanel.setLayout(new BoxLayout(RightPanel, BoxLayout.Y_AXIS));
		JLabel BlueLabel = new JLabel("緑秒");
		JLabel RedLabel = new JLabel("赤秒");
		SliderLabel = new JLabel("車が出る確率：" + (String.valueOf(road.getProbability())) + "%");
		AdvanceButton = new JButton("advance");
		trafficLight = new JButton("traffic light");
		AutoCheckBox =  new JCheckBox("Auto Advance");
		AutoSignalCheckBox =  new JCheckBox("Auto Signal");
		SignalLabel = new JLabel();
		SignalLabel.setText("●");
		AdvanceButton.addActionListener(this);
		AdvanceButton.setActionCommand("advance");
		trafficLight.addActionListener(this);
		trafficLight.setActionCommand("signal");
		SignalLabel.setForeground(Color.GREEN);
		AutoCheckBox.addActionListener(this);
		AutoSignalCheckBox.addActionListener(this);
		slider = new JSlider();
		slider.addChangeListener(this);
		ToRedmodel = new SpinnerNumberModel(1, 1, 1000, 1);
		ToBluemodel = new SpinnerNumberModel(1, 1, 1000, 1);
		ToRedSpinner = new JSpinner(ToRedmodel);
		ToRedSpinner.setPreferredSize(new Dimension(50, 30));
		ToBlueSpinner = new JSpinner(ToBluemodel);
		ToBlueSpinner.setPreferredSize(new Dimension(50, 30));
		BluePanel.add(BlueLabel);
		BluePanel.add(ToRedSpinner);
		UnderPanel.add(BluePanel);
		RedPanel.add(RedLabel);
		RedPanel.add(ToBlueSpinner);
		SliderPanel.add(SliderLabel);
		SliderPanel.add(slider);
		UnderPanel.add(RedPanel);
		UnderPanel.add(SliderPanel);
		OverPanel.add(AutoCheckBox);
		OverPanel.add(AutoSignalCheckBox);
		OverPanel.add(AdvanceButton);
		OverPanel.add(trafficLight);
		UnderPanel.add(SignalLabel);
		RightPanel.add(CountJamLabel);
		RightPanel.add(MAXJamLengthLabel);
		RightPanel.add(MAXMAXJamLengthLabel);
		CenterPanel.add(jamState);
		CenterPanel.add(jamState2);
		getContentPane().add(CenterPanel, BorderLayout.CENTER);
		getContentPane().add(OverPanel, BorderLayout.NORTH);
		getContentPane().add(UnderPanel, BorderLayout.SOUTH);
		getContentPane().add(RightPanel, BorderLayout.EAST);
		timer = new Timer(100 , this);
		timer.setActionCommand("timer");
		timer.start();
		
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd = e.getActionCommand();
		
		/********************信号***************************/
		
		/*スピンボックスから信号の色を保持する時間を所得*/
		signal.setBlue(((Integer)(ToRedmodel.getValue())).intValue());
		signal.setRed(((Integer)ToBluemodel.getValue()).intValue());
		
		/*signalボタンを押すと信号の色が変化*/
		if(cmd.equals("signal")){
			signal.signalChange();
		}
		
		/*信号が自動的に切り替わる*/
		if(AutoSignalCheckBox.isSelected()){
			ToBlueSpinner.setEnabled(false);
			ToRedSpinner.setEnabled(false);
			trafficLight.setEnabled(false);
			signal.AutoSignalStart();
		}else{
			signal.AutoSignalStop();
			trafficLight.setEnabled(true);
			ToBlueSpinner.setEnabled(true);
			ToRedSpinner.setEnabled(true);
		}
		
		/*信号の色を変更*/
		if(signal.isSignalColor()){
			SignalLabel.setForeground(Color.RED);
		}
		else{
			SignalLabel.setForeground(Color.GREEN);
		}
		
		/*********************前進****************************/
		
		/*現在の信号の色をroadクラスへ渡す*/
		road.setSiganl(signal.isSignalColor());

		/*advanceボタンを押すと車が前進*/
		if(cmd.equals("advance")){
			String s = GetString(road.advance());
			jamState.setText(s);;
//			System.out.println(s);
		}
		
		/*AutoCheckBoxにチェックを入れると自動前進*/
		if(AutoCheckBox.isSelected()){
			AdvanceButton.setEnabled(false);    
			road.autoAdvanceStart();
			String s = GetString(road.getJam());
			jamState.setText(s);
//			System.out.println(s);
		}
		else{
			road.autoAdvanceStop();
			AdvanceButton.setEnabled(true);
		}
		
		/*ラベルの設定*/
		int l = road.MAXJamLength();
		if(tmpCount < l) tmpCount = l;
		CountJamLabel.setText("渋滞の数:" + road.CountJam());
		MAXJamLengthLabel.setText("最大の渋滞の長さ:" + l);
		MAXMAXJamLengthLabel.setText("今までの最大の渋滞の長さ:" + tmpCount);
	
	}//end of ActionPerformed

	
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		SliderLabel.setText("車が出る確率：" + (slider.getValue()) + "%");
	    road.setProbability(slider.getValue());
	}
	
	private String GetString(boolean[] jam){
		String s = "";
		for(int i=0; i<jam.length; i++){
			if(i == road.getSIGNALPOINT()+1){
				if(signal.isSignalColor()){
					s = s + "//";
				}else{
					s = s + "  ";
				}
			}
			if(jam[i] == true){
				s = s + "●";
			}else{
				s = s + "○";
			}
		}
		return s;
	}
	
}//end of class
