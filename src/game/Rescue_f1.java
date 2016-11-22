package game;

public class Rescue_f1 extends Rescue{

	public Rescue_f1(Origin or, String des) {
		super(or, des);
		if(or==null) throw new IllegalArgumentException("the rescue_f1's origin should NOT be null!");
	}
	static public Rescue_f1 parase(String str){
		String[] ss=str.split("\t");
		Rescue_f1 res=null;
		try{
			res=new Rescue_f1(Origin.valueOf(ss[0]),ss[1]);
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean use(Game game,Desk.Player player,String... args){
		Desk desk=game.desk;
		//获得某一玩家的至多3手牌
		Desk.Player p=null;
		while(p==null || p==player){
			p=desk.players.get((int)(Math.random()*desk.players.size()));
		}
		int num=p.getCards().length;
		if(num<=0){
			System.out.println(player.toString()+" want to get cards from "+p.toString()+" which has NO card!");
		}else{
			for(int i=0;i<3 && i<num;i++){
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
