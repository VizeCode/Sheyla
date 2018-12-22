package br.com.vize;

import java.awt.EventQueue;

import br.com.vize.Control.Connection;
import br.com.vize.Design.Interface;

public class Main {
	
	private static Connection connection;
	public static int port = 1;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface frame = new Interface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		connection = new Connection();
		getConnection().initialize();
	}
	
	public static Connection getConnection()
	{
		return connection;
	}
}
