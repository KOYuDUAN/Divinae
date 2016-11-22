package game;

public class Believer_f2 extends Believer{
	
	public Believer_f2(Origin or, int cr, String des, int number) {
		super(or, cr, des, number);
	}
	static public Believer_f2 parase(String str){
		String[] ss=str.split("\t");
		Believer_f2 res=null;
		try{
			res=new Believer_f2(Origin.valueOf(ss[0]),Creed.parseInt(ss[1]),ss[2],Integer.parseInt(ss[3]));
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean sacrifice(Game game,Desk.Player player){
		Desk desk=game.desk;
		//获得某一玩家的至多两张手牌
		Desk.Player p=null;
		while(p==null || p==player){
			p=desk.players.get((int)(Math.random()*desk.players.size()));
		}
		int num=p.getCards().length;
		if(num<=0){
			System.out.println(player.toString()+" want to get cards from "+p.toString()+" which has NO card!");
		}else{
			for(int i=0;i<2 && i<num;i++){
				Card cs[]=p.getCards();
				Card card=cs[(int)(Math.random()*cs.length)];
				p.discard(card);
				player.draw(card);
				System.out.println(player.toString()+" get a card from "+p.toString());
				System.out.println("\t"+card.toString());
			}
		}

		player.discard(this);
		desk.discard(this);
		return true;
	}

}
