#include <Results.h>
#include <IRremote.h>

#define SLAVE_ADDRESS 0x40

Results results;
IRsend sender;

unsigned int tv[][67] = {};

void executeCommand(Command command)
{
	if (command.device == command.TV)
	{
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

	//Wire.begin(SLAVE_ADDRESS);
}

void loop()
{
	results.update();

	executeCommand(results.getCommand(0));

	delay(1000);
}

void I2CReceived(int howMany)
{
	results.update();
}
