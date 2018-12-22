package br.com.vize.Util;

import br.com.vize.Main;

public class Variable {
	
	private static String ports[] = { "/dev/cu.usbmodem411", "/dev/cu.usbmodem641" };
	
	public static int bound = 9600;
	
	public static String getPort()
	{
		return ports[Main.port - 1];
	}
	
}
