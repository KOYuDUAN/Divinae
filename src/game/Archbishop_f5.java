package game;

public class Archbishop_f5 extends Archbishop{

	public Archbishop_f5(Origin or, int cr, String des, int number) {
		super(or, cr, des, number);
		// TODO Auto-generated constructor stub
	}

	static public Archbishop_f5 parase(String str){
		String[] ss=str.split("\t");
		Archbishop_f5 res=null;
		try{
			res=new Archbishop_f5(Origin.valueOf(ss[0]),Creed.parseInt(ss[1]),ss[2],Integer.parseInt(ss[3]));
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean sacrifice(Game game,Desk.Player player){
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
			System.out.println(player.toString()+" want to get an Archbishop from "+p.toString()+":");
			System.out.println(arc.toString());
		}

		player.archbishops.remove(this);
		for(Believer b:this.bels)
			desk.discard(b);
		this.bels.clear();
		desk.discard(this);
		return true;
	}

}
