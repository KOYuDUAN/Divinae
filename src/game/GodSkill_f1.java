package game;

public class GodSkill_f1 extends GodSkill{

	
	public GodSkill_f1(Origin or) {
		super(or, "draw 3 cards from pioch");
	}

	static public GodSkill_f1 parase(String str){
		
		String[] ss=str.split("\t");
		GodSkill_f1 res=null;
		try{
			res=new GodSkill_f1(Origin.valueOf(ss[0]));
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
	//≥È»˝’≈ø®
	@Override
	public boolean use(Game game,Desk.Player player,String... args){
		Desk desk=game.desk;
		
		for(int i=0;i<3;i++){
			Card card=desk.draw();
			player.draw(card);
			System.out.println("draw: "+card.toString());
		}
		
		
		super.use(game, player, args);
		return true;
	}
	
}
