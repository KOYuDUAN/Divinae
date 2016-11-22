package game;

public class Believer_f1 extends Believer{
	
	public Believer_f1(Origin or, int cr, String des, int number) {
		super(or, cr, des, number);
	}
	static public Believer_f1 parase(String str){
		String[] ss=str.split("\t");
		Believer_f1 res=null;
		try{
			res=new Believer_f1(Origin.valueOf(ss[0]),Creed.parseInt(ss[1]),ss[2],Integer.parseInt(ss[3]));
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean sacrifice(Game game,Desk.Player player){
		Desk desk=game.desk;
		//获得此卡起源的行动点
		System.out.println(player.toString()+" get ONE act point("+this.origin.toString()+")");
		player.actPoint.add(this.origin,1);
		
		player.discard(this);
		desk.discard(this);
		return true;
	}

}
