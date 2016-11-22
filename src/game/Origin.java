package game;

public enum Origin {
	daylight,darkness,nihility,
	dawn,dusk,
	NULL;

	static public Origin rand(){
		Origin os[]=Origin.values();
		return os[(int)(Math.random()*(os.length-3))];
	}
	static public Origin randGod(){
		Origin os[]=Origin.values();
		int r=(int)(Math.random()*(os.length-2));
		return os[r<2?r:(r+1)];
	}
}
