package br.com.vize.Exception;

public class DataException extends Exception {

	private static final long serialVersionUID = 3600003507222240800L;
	
	enum DataEnum
	{
		IMCOMPATIBLE("IMCOMPATIBLE VARIABLE TO TYPE DATA"),
		SERIAL_PORT("COULD NOT FIND SERIAL PORT"),
		UNKOWN_OBJECT("OBJECT NOT SPECIFIED"),
		UNKWON_DATA("UNDEFINED TYPE OF DATA"),
		NOT_CONNECTED("APPLICATION NOT CONNECTED WITH FIRMWARE");
		
		String description;
		
		DataEnum(String description)
		{
			this.description = description;
		}
	}
	
	public static String getInfo(int id, int type)
	{
		String info = "UNKOWN";
		DataEnum dataEnum = DataEnum.values()[id];
		switch (type)
		{
			case 0:
				info = dataEnum.name();
				break;
			default:
				info = dataEnum.description;
				break;
		}
		return info;
	}
	
	public DataException(int id)
	{
		super("Error (" + id + ") " + getInfo(id, 0) + ": " + (getInfo(id, 1).equals("UNKOWN") ? getInfo(id, 1).concat(" ERROR ID") : getInfo(id, 1)));
	}
	
}
