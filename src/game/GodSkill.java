package game;

public class GodSkill extends Card{
	
	public GodSkill(Origin or,String des) {
		super(or,0,des);
	}
	
	@Override
	public String toString(){
		return this.getClass().getSimpleName().split("_")[0]+"("+origin.toString()+"||"+Creed.toString(creed)+")"
				+"{"+description+"}";
	}
	
	static public GodSkill parase(String str){
		String[] ss=str.split("\t");
		GodSkill res=null;
		try{
			res=new GodSkill(Origin.valueOf(ss[0]),ss[1]);
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean use(Game game,Desk.Player player,String... args){
		@SuppressWarnings("unused")
		Desk desk=game.desk;
		player.power=new GodSkill(this.origin,this.description);
		return false;
	}
	@Override
	public final boolean sacrifice(Game game,Desk.Player player){
		return false;
	}

}
