package game;

public class Archbishop_f2 extends Archbishop{


	public Archbishop_f2(Origin or, int cr, String des, int number) {
		super(or, cr, des, number);
		// TODO Auto-generated constructor stub
	}

	static public Archbishop_f2 parase(String str){
		String[] ss=str.split("\t");
		Archbishop_f2 res=null;
		try{
			res=new Archbishop_f2(Origin.valueOf(ss[0]),Creed.parseInt(ss[1]),ss[2],Integer.parseInt(ss[3]));
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean sacrifice(Game game,Desk.Player player){
		Desk desk=game.desk;
		//获取当前信徒个数的行动点
		System.out.println("It has "+this.bels.size()+" Believers.");
		if(this.bels.size()>0){
			Origin o=null;
			while(true){
				String t=Game.getInputLine("input the type of act point");
				try{
					o=Origin.valueOf(t);
					break;
				}catch(Exception e){
					System.out.println("input error!");
					continue;
				}
			}
			player.actPoint.add(o,this.bels.size());
			System.out.println(player.toString()+" get "+this.bels.size()+" act point("+o.toString()+")");
		}
		
		player.archbishops.remove(this);
		for(Believer b:this.bels)
			desk.discard(b);
		this.bels.clear();
		desk.discard(this);
		return true;
	}

}
