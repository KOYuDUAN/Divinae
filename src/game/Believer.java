package game;

public class Believer extends Card{
	
	public final int number;
	protected boolean available=true;

	public Believer(Origin or,int cr,String des,int number) {
		super(or,cr,des);
		this.number=number;
		if(or==null) throw new IllegalArgumentException("Believer.orgin==null!");
		if(this.number<0) throw new IndexOutOfBoundsException("Believer.number<0!");
		
	}
	
	@Override
	public String toString(){
		return (this.available?" ":"*")
				+this.getClass().getSimpleName().split("_")[0]+"["+number+"]"+"("+origin.toString()+"||"+Creed.toString(creed)+")"
				+"{"+description+"}";
	}
	
	static public Believer parase(String str){
		String[] ss=str.split("\t");
		Believer res=null;
		try{
			res=new Believer(Origin.valueOf(ss[0]),Creed.parseInt(ss[1]),ss[2],Integer.parseInt(ss[3]));
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean use(Game game,Desk.Player player,String... args){
		Desk desk=game.desk;
		player.discard(this);
		desk.layCard(this);
		return true;
	}
	@Override
	public boolean sacrifice(Game game,Desk.Player player){
		return false;
	}

}
