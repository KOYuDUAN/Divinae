package game;

public class GodSkill_f2 extends GodSkill{

	
	public GodSkill_f2(Origin or) {
		super(or, "draw 3 cards from other player");
	}

	static public GodSkill_f2 parase(String str){
		String[] ss=str.split("\t");
		GodSkill_f2 res=null;
		try{
			res=new GodSkill_f2(Origin.valueOf(ss[0]));
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
	//获得其他玩家3张牌
	@Override
	public boolean use(Game game,Desk.Player player,String... args){
		Desk desk=game.desk;
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
		super.use(game, player, args);
		return true;
	}
}