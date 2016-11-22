package game;

import java.util.ArrayList;

public class Archbishop extends Card{
	
	final ArrayList<Believer> bels=new ArrayList<Believer>();
	int number;

	public Archbishop(Origin or,int cr,String des,int number) {
		super(or,cr,des);
		if(or==null) throw new IllegalArgumentException("Archbishop.orgin==null!");
		this.number=number;
	}
	
	public final int getNumberOfBelievers(){
		int res=0;
		for(Believer be:bels)
			res+=be.number;
		return res;
	}
	
	@Override
	public String toString(){
		return this.getClass().getSimpleName().split("_")[0]
				+"["+this.bels.size()+"/"+this.number+"]"
				+"("+origin.toString()+"||"+Creed.toString(creed)+")"+"{"+description+"}"
				+(bels.isEmpty()?"":("\n"+Desk.toString(bels,"\t\t")));
	}
	static public Archbishop parase(String str){
		String[] ss=str.split("\t");
		Archbishop res=null;
		try{
			res=new Archbishop(Origin.valueOf(ss[0]),Creed.parseInt(ss[1]),ss[2],Integer.parseInt(ss[3]));
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
	
	public final boolean contains(Believer bel){
		return this.bels.contains(bel);
	}

	@SuppressWarnings("serial")
	public static class Contained extends Exception{
		public Contained(){
			super("Contained!");
		}
	}
	@SuppressWarnings("serial")
	public static class OriginEqual extends Exception{
		public OriginEqual(){
			super("Origin Equal!");
		}
	}
	@SuppressWarnings("serial")
	public static class CreedIllegal extends Exception{
		public CreedIllegal(){
			super("Creed Illegal!");
		}
	}
	@SuppressWarnings("serial")
	public static class NotAvailable extends Exception{
		public NotAvailable(){
			super("Not Available!");
		}
	}
	public final void canConvert(Believer bel) throws Exception{
		if(bel==null) throw new NullPointerException("bel==null!");
		if(this.bels.contains(bel)) throw new Contained();
		if(!bel.available) throw new NotAvailable();
		if(this.origin==bel.origin) throw new OriginEqual();
		if(Creed.get(this.creed,bel.creed)<1) throw new CreedIllegal();
	}
	protected final void convertSudo(Believer bel){
		if(bel==null) throw new NullPointerException("bel==null!");
		this.bels.add(bel);
	}
	
	public final void remove(Believer bel){
		if(bel==null) throw new NullPointerException("bel==null!");
		if(!this.contains(bel)) return;
		this.bels.remove(bel);
	}

	@Override
	public boolean use(Game game,Desk.Player player,String... args){
		Desk desk=game.desk;
		if(args!=null && args.length>0){
			ArrayList<Believer> bs=new ArrayList<Believer>();
			int sum=0;
			for(String s:args)
				bs.add(desk.center.get(Integer.parseInt(s)-1));
			for(@SuppressWarnings("unused") Believer b:bs)
				sum++;
			if(sum>this.number)
				return false;
			for(Believer b:bs){
				try{
					this.canConvert(b);
				}catch(Exception e){
					System.out.println(e.getMessage());
					return false;
				}
			}
			for(Believer b:bs){
				this.convertSudo(b);
				desk.center.remove(b);
			}
		}
		player.discard(this);
		player.archbishops.add(this);
		return true;
	}
	@Override
	public boolean sacrifice(Game game,Desk.Player player){
		return false;
	}

}
