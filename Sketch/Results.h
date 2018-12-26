#include <Command.h>

struct Results
{
	String commands[30];

	bool update()
	{
		String command;
		int splits[10];

		if (Serial.available() > 0)
		{
			command = Serial.readString();
			int countSplits = 0;

			for (int i = 0; i < command.length(); i++) 
			{
				if (command[i] == ';') 
				{
					splits[countSplits] = i;
					countSplits++;
				}
			}

			int countCommands = 0;
			for (int i = 0; i < countSplits; i++)
			{
				int index = i == 0 ? splits[i] : splits[i] - splits[i - 1] - 1;
				String cmd = command.substring(0, index);
				commands[countCommands] = cmd;
				command = command.substring(index + 1);
				countCommands++;
			}

			return true;
		}

		return false;
	}

	Command getCommand(int i)
	{
		String command;
		Command cmd;

		if (commands[0].length() > 0)
		{
			command = commands[i];
			int div = locationChar(command, '/');
			cmd.device = command.substring(0, div).toInt();
			command = command.substring(div + 1);
			int equal = locationChar(command, '=');
			cmd.mode = command.substring(0, equal).toInt();
			command = command.substring(equal + 1);
			cmd.response = command.toInt();
		}

		return cmd;
	}

	int locationChar(String string, char c)
	{
		for (int i = 0; i < string.length(); i++) if (string[i] == c) return i;
	}
};
