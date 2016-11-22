package game;

public class Believer_f4 extends Believer{
	
	public Believer_f4(Origin or, int cr, String des, int number) {
		super(or, cr, des, number);
	}
	static public Believer_f4 parase(String str){
		String[] ss=str.split("\t");
		Believer_f4 res=null;
		try{
			res=new Believer_f4(Origin.valueOf(ss[0]),Creed.parseInt(ss[1]),ss[2],Integer.parseInt(ss[3]));
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean sacrifice(Game game,Desk.Player player){
		Desk desk=game.desk;
		//重新掷骰子结算行动点
		game.runRandStart();

		player.discard(this);
		desk.discard(this);
		return true;
	}

}
