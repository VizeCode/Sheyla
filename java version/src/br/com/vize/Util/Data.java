package br.com.vize.Util;

import br.com.vize.Control.Object;
import br.com.vize.Control.Type;
import br.com.vize.Exception.DataException;

public class Data {
	
	int integerData;
	boolean booleanData;
	String stringData;
	Object objectData;
	Type type;
	
	public Data(int integerData)
	{
		this.integerData = integerData;
		type = Type.INTEGER;
	}
	
	public Data(boolean booleanData)
	{
		this.booleanData = booleanData;
		type = Type.BOOLEAN;
	}
	
	public Data(String stringData)
	{
		this.stringData = stringData;
		type = Type.STRING;
	}
	
	public Data(Object objectData)
	{
		this.objectData = objectData;
		type = objectData.getType();
	}
	
	public String get()
	{
		if (objectData != null) return objectData.getData();
		switch (type)
		{
			case INTEGER:
				return Integer.toString(integerData);
			case BOOLEAN:
				return Boolean.toString(booleanData);
			case STRING:
				return stringData;
			default:
			try {
				throw new DataException(3);
			} catch (DataException e) {
				e.printStackTrace();
			}
			return new String();
		}
	}
	
	public Type getType()
	{
		return type;
	}
	
}
