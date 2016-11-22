package game;

import java.lang.reflect.Method;

public abstract class Card{
	public final Origin origin;
	public final int creed;
	public final String description;
	
	public Card(Origin or,int cr,String des){
		this.origin=or;
		this.creed=cr;
		this.description=des;
	}

	public final boolean canUse(ActPoint aps,Origin type){
		return use(aps.clone(),type);
	}
	public final boolean use(ActPoint aps,Origin type){
		if(aps==null) return false;
		if(this.origin==null) return true;
		if(type==null && aps.substract(this.origin,1)) return true;
		if(type==Origin.daylight && aps.substract(Origin.daylight,2)) return true;
		if(type==Origin.darkness && aps.substract(Origin.darkness,2)) return true;
		return false;
	}
	
	@Override
	public abstract String toString();
	
	static public Card parase(String str){
		String ss[]=str.split("\t",2);
		Card res=null;
		try{
			Method m=Class.forName("game."+ss[0]).getMethod("parase",String.class);
			res=(Card)m.invoke(null,ss[1]);
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
	
	
	
	public abstract boolean use(Game game,Desk.Player player,String... args);
	public abstract boolean sacrifice(Game game,Desk.Player player);
}
