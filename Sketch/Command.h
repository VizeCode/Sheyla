struct Command
{
	enum Devices { TV, AR };
	enum Modes { POWER, INP, VOLUME_UP, VOLUME_DOWN, RIGHT, LEFT, OK };

	Devices device;
	Modes mode;
	int response;
};
