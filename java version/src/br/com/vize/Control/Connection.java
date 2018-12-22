package br.com.vize.Control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import br.com.vize.Exception.DataException;
import br.com.vize.Util.Data;
import br.com.vize.Util.Variable;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

public class Connection implements SerialPortEventListener {

	private SerialPort serialPort;
	private OutputStream serial;
	
	private BufferedReader input;
	private static final int TIME_OUT = 2000;
	private String inputLine;
	
	public void initialize()
	{
		CommPortIdentifier port = null;
		@SuppressWarnings("rawtypes")
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
		
		while (portEnum.hasMoreElements())
		{
			CommPortIdentifier currentPort = (CommPortIdentifier) portEnum.nextElement();
			
			if (Variable.getPort().equals(currentPort.getName()))
			{
				port = currentPort;
				break;
			}
		}
		
		try 
		{
			if (port == null) throw new DataException(1);
			
			serialPort = (SerialPort) port.open(this.getClass().getName(), TIME_OUT);	
			serialPort.setSerialPortParams(Variable.bound, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			serial = serialPort.getOutputStream();
			
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (PortInUseException | UnsupportedCommOperationException | IOException | TooManyListenersException | DataException e) {
			e.printStackTrace();
		}
	}
	
	public void close()
	{
		try 
		{
			serial.close();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void sendData(Data data)
	{
		try {
			switch (data.getType())
			{
				case BOOLEAN:
					if (!data.get().equalsIgnoreCase("true") && !data.get().equalsIgnoreCase("false")) throw new DataException(0);
				case INTEGER:
					try {
						Integer.parseInt(data.get());
					} catch (NumberFormatException e)
					{
						throw new DataException(0);
					}
				default:
					break;
			}
			
			if (serial == null)
			{
				throw new DataException(4);
			}
				
			serial.write(data.get().getBytes());
		} catch (DataException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public String read()
	{
		return inputLine;
	}
	
	public void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.err.println(e.toString());
        }
    }
	
	@Override
    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                inputLine = input.readLine();
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
    }
	
}
