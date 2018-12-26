#include <Results.h>
#include <IRremote.h>

Results results;
IRsend sender;

unsigned int tv[][67] = {};
unsigned int ar[][67] = {};

bool upd;

void executeCommand(Command command)
{
	switch (command.device)
	{
		case command.TV:
			standardSetup(command, command.VOLUME_UP, command.VOLUME_DOWN, tv);
			break;
		case command.AR:
			standardSetup(command, command.TEMP_UP, command.TEMP_DOWN, ar);
			break;
	}
}

void standardSetup(Command command, int up, int down, unsigned int device[][67])
{
	if (command.mode == up || command.mode == down)
	{
		for (int i = 0; i < command.response; i++)
		{
			sendIR(device, command.mode);
		}
	} else
	{
		sendIR(device, command.mode);
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
	int count = 0;

	if (results.commands[0].length() > 0 && upd)
	{
		for (int i = 0; i < len; i++)
		{
			if (results.commands[i].length() > 0)
			{
				executeCommand(results.getCommand(i));
			}
		}
		upd = false;
	}
}
