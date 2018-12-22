package br.com.vize.Objects;

import br.com.vize.Control.Object;
import br.com.vize.Control.Type;

public class Led implements Object {

	boolean state;
	String data;
	
	@Override
	public void action() {
		state = state ? false : true;
		data = Boolean.toString(state);
	}

	@Override
	public Type getType() {
		return Type.BOOLEAN;
	}

	@Override
	public String getData() {
		return data;
	}

	
}
