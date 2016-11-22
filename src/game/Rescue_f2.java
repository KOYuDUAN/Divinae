package game;

public class Rescue_f2 extends Rescue{

	public Rescue_f2(Origin or, String des) {
		super(or, des);
		if(or==null) throw new IllegalArgumentException("the rescue_f2's origin should NOT be null!");
	}
	static public Rescue_f2 parase(String str){
		String[] ss=str.split("\t");
		Rescue_f2 res=null;
		try{
			res=new Rescue_f2(Origin.valueOf(ss[0]),ss[1]);
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean use(Game game,Desk.Player player,String... args){
		Desk desk=game.desk;
		//获取其他某一个玩家的大主教卡及其信徒
		Desk.Player p=null;
		while(p==null || p==player){
			p=desk.players.get((int)(Math.random()*desk.players.size()));
		}
		if(p.archbishops.size()<=0){
			System.out.println(player.toString()+" want to get Archbishop from "+p.toString()+" which has NO Archbishop!");
		}else{
			Archbishop arc=p.archbishops.get((int)(Math.random()*p.archbishops.size()));
			p.archbishops.remove(arc);
			player.archbishops.add(arc);
			System.out.println(player.toString()+" get an Archbishop from "+p.toString()+":");
			System.out.println(arc.toString());
		}

		player.discard(this);
		desk.discard(this);
		return true;
	}


}
