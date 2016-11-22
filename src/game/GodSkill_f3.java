package game;

public class GodSkill_f3 extends GodSkill{

	
	public GodSkill_f3(Origin or) {
		super(or, "use a Apocalypse");
	}

	static public GodSkill_f3 parase(String str){
		String[] ss=str.split("\t");
		GodSkill_f3 res=null;
		try{
			res=new GodSkill_f3(Origin.valueOf(ss[0]));
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
	//使用大灾难
	@Override
	public boolean use(Game game,Desk.Player player,String... args){
		Desk desk=game.desk;
		Apocalypse apo = new Apocalypse(Origin.randGod(),"");
		apo.use(game, player, null);
		super.use(game, player, args);
		return true;
	}
}