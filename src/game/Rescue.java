package game;

public class Rescue extends Card{
	
	public Rescue(Origin or,String des) {
		super(or,0,des);
	}
	
	@Override
	public String toString(){
		return this.getClass().getSimpleName().split("_")[0]+"("+origin.toString()+"||"+Creed.toString(creed)+")"
				+"{"+description+"}";
	}
	
	static public Rescue parase(String str){
		String[] ss=str.split("\t");
		Rescue res=null;
		try{
			res=new Rescue(Origin.valueOf(ss[0]),ss[1]);
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean use(Game game,Desk.Player player,String... args){
		@SuppressWarnings("unused")
		Desk desk=game.desk;
		return false;
	}
	@Override
	public final boolean sacrifice(Game game,Desk.Player player){
		return false;
	}

}
