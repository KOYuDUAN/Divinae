package game;

public class Rescue_f4 extends Rescue{

	public Rescue_f4(Origin or, String des) {
		super(or, des);
		if(or==null) throw new IllegalArgumentException("the rescue_f1's origin should NOT be null!");
	}

	static public Rescue_f4 parase(String str){
		String[] ss=str.split("\t");
		Rescue_f4 res=null;
		try{
			res=new Rescue_f4(Origin.valueOf(ss[0]),ss[1]);
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
	@Override
	public boolean use(Game game,Desk.Player player,String... args){
		Desk desk=game.desk;
		//摧毁一张大主教，其信徒回到牌桌中央
		Desk.Player p=null;
		while(p==null || p==player){
			p=desk.players.get((int)(Math.random()*desk.players.size()));
		}
		int num=p.archbishops.size();
		if(num<=0){
			System.out.println(player.toString()+" want to kill Archbishop from "+p.toString()+" which has NO Archbishop!");
		}else{
			Archbishop arc=p.archbishops.get((int)(Math.random()*p.archbishops.size()));
			p.archbishops.remove(arc);
			System.out.println(player.toString()+" kill an Archbishop from "+p.toString()+":");
			System.out.println(arc.toString());
			for(Believer b:arc.bels){
				desk.layCard(b);
			}
			arc.bels.clear();
			p.archbishops.remove(arc);
			desk.discard(arc);
		}

		player.discard(this);
		desk.discard(this);
		return true;
	}


}
