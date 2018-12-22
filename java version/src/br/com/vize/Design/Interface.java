package br.com.vize.Design;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.com.vize.Main;
import br.com.vize.Objects.TempSensor;
import br.com.vize.Util.Data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.JLabel;

public class Interface extends JFrame {

	private static final long serialVersionUID = 5385734301258121570L;
	private JPanel contentPane;
	
	JButton connectButton;
	JProgressBar connectProgress;
	Timer connectTimer;
	
	JButton turnOn;
	JButton turnOf;
	
	JLabel tempLabel;
	
	public Interface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		connectButton = new JButton("Conectar");
		connectButton.setBounds(141, 106, 166, 53);
		contentPane.add(connectButton);
		
		connectProgress = new JProgressBar();
		connectProgress.setBounds(153, 123, 146, 20);
		connectProgress.setVisible(false);
		connectProgress.setMaximum(25);
		contentPane.add(connectProgress);
		
		turnOf = new JButton("Desligar");
		turnOf.setBounds(81, 118, 117, 29);
		turnOf.setVisible(false);
		contentPane.add(turnOf);
		
		turnOn = new JButton("Ligar");
		turnOn.setBounds(219, 118, 117, 29);
		turnOn.setVisible(false);
		contentPane.add(turnOn);
		
		tempLabel = new JLabel("Temperatura: 0°C");
		tempLabel.setBounds(141, 78, 118, 16);
		tempLabel.setVisible(false);
		contentPane.add(tempLabel);
		
		events();
	}
	
	public void events() {
		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				connectButton.setVisible(false);
				turnOn.setVisible(false);
				turnOf.setVisible(false);
				connectProgress.setVisible(true);
				connectTimer = new Timer(80, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						connectProgress.setValue(connectProgress.getValue() + 1);
					}
				});
				connectTimer.start();
			}
		});
		turnOn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.getConnection().sendData(new Data(1));
			}
		});
		
		turnOf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.getConnection().sendData(new Data(0));
			}
		});
		
		TempSensor temp = new TempSensor();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				temp.action();
				tempLabel.setText("Temperatura: " + temp.getData() + "°C");
			}
		}).start();
		
		connectProgress.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (connectProgress.getValue() == connectProgress.getMaximum())
				{
					connectTimer.stop();
					connectProgress.setVisible(false);
					turnOn.setVisible(true);
					turnOf.setVisible(true);
					tempLabel.setVisible(true);
				}
			}
		});
	}
}
