package game;

public class Rescue_f3 extends Rescue{

	public Rescue_f3(String des) {
		super(Origin.NULL, des);
	}
	static public Rescue_f3 parase(String str){
		String[] ss=str.split("\t");
		Rescue_f3 res=null;
		try{
			res=new Rescue_f3(ss[0]);
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

	public boolean using=false;
	@Override
	public boolean use(Game game,Desk.Player player,String... args){
		@SuppressWarnings("unused")
		Desk desk=game.desk;
		//使卡牌无效化
		return false;
	}


}
