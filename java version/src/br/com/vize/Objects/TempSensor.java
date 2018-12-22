package br.com.vize.Objects;

import br.com.vize.Main;
import br.com.vize.Control.Object;
import br.com.vize.Control.Type;

public class TempSensor implements Object {

	String data;
	
	@Override
	public void action() {
		data = "-1";
		String sensorName = this.getClass().getName();
		
		if (Main.getConnection().exist())
		{
			String receive = Main.getConnection().read();
			
			if (receive.contains(sensorName)) data = receive.replace(sensorName + ": ", "");
		}
	}
	
	@Override
	public Type getType() {
		return Type.INTEGER;
	}

	@Override
	public String getData() {
		return data;
	}

}
