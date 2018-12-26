#include <Results.h>
#include <IRremote.h>

Results results;
IRsend sender;

unsigned int tv[][67] = {};
bool upd;

void executeCommand(Command command)
{
	if (command.device == command.TV)
	{
		Serial.println(command.device);
		if (command.mode == command.VOLUME_UP || command.mode == command.VOLUME_DOWN)
		{
			for (int i = 0; i < command.response; i++)
			{
				sendIR(tv, command.mode);
			}
		} else
		{
			sendIR(tv, command.mode);
		}
		Serial.println(command.mode);
		Serial.println(command.response);
	}
}

void sendIR(unsigned int device[][67], int mode)
{
	sender.sendRaw(device[mode], sizeof(device[mode])/ sizeof(device[mode][0]), 38);
	delay(350);
}

void setup()
{
	Serial.begin(9600);
}

void loop()
{
	int len = sizeof(results.commands) / sizeof(results.commands[0]);
	upd = results.update();

	if (results.commands[0].length() > 0 && upd)
	{
		for (int i = 0; i < len; i++)
		{
			if (results.commands[i].length() > 0)
			{
				Serial.print("Comando ");
				Serial.println(i);
				executeCommand(results.getCommand(i));
			}
		}

		upd = false;
	}

	//delay(100);
}
