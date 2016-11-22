package game;

import java.util.ArrayList;

public class Archbishop_f3 extends Archbishop{

	public Archbishop_f3(Origin or, int cr, String des, int number) {
		super(or, cr, des, number);
		// TODO Auto-generated constructor stub
	}

	static public Archbishop_f3 parase(String str){
		String[] ss=str.split("\t");
		Archbishop_f3 res=null;
		try{
			res=new Archbishop_f3(Origin.valueOf(ss[0]),Creed.parseInt(ss[1]),ss[2],Integer.parseInt(ss[3]));
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean sacrifice(Game game,Desk.Player player){
		Desk desk=game.desk;
		//中央信徒中非本卡起源的会被弃置
		ArrayList<Believer> del=new ArrayList<Believer>();
		for(Believer b:desk.center){
			if(b.origin==this.origin) continue;
			del.add(b);
		}
		for(Believer b:del){
			desk.center.remove(b);
			desk.discard(b);
			System.out.println("Discard: "+b.toString());
		}

		player.archbishops.remove(this);
		for(Believer b:this.bels)
			desk.discard(b);
		this.bels.clear();
		desk.discard(this);
		return true;
	}

}
