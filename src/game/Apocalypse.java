package game;

import game.Desk.Player;

public class Apocalypse extends Card{
	
	public Apocalypse(Origin or,String des) {
		super(or,0,des);
	}
	
	@Override
	public String toString(){
		return this.getClass().getSimpleName().split("_")[0]+"("+origin.toString()+")"
				+"{"+description+"}";
	};
	static public Apocalypse parase(String str){
		String[] ss=str.split("\t");
		Apocalypse res=null;
		try{
			res=new Apocalypse(Origin.valueOf(ss[0]),ss[1]);
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
	
	@Override
	public boolean use(Game game,Desk.Player player,String... args){
		Desk desk=game.desk;
		System.out.println("Apocalypse used!");
		//System.out.println(this.toString());
		System.out.println("Now the NUMBER of players is "+desk.players.size()+".");
		if(desk.players.size()>=4){
			boolean flag=false;
			int min=Integer.MAX_VALUE;
			int minid=-1;
			for(int i=0;i<desk.players.size();i++){
				int sum=desk.players.get(i).getNumberOfBelievers();
				if(minid<0||sum<min){
					flag=false;
					min=sum;
					minid=i;
				}else if(sum==min)
					flag=true;
			}
			if(flag){
				System.out.println("There are more than ONE players have "+min+" belivers.");
				System.out.println("The Apocalypse failed!");
			}else{
				Player p=desk.players.get(minid);
				System.out.println(p.name+" died!");
				desk.players.remove(p);
			}
		}else{
			boolean flag=false;
			int max=Integer.MIN_VALUE;
			int maxid=-1;
			for(int i=0;i<desk.players.size();i++){
				int sum=desk.players.get(i).getNumberOfBelievers();
				if(maxid<0||sum>max){
					flag=false;
					max=sum;
					maxid=i;
				}else if(sum==max)
					flag=true;
			}
			if(flag){
				System.out.println("There are more than ONE players have "+max+" belivers.");
				System.out.println("The Apocalypse failed!");
			}else{
				for(int i=0;i<desk.players.size();i++){
					if(i!=maxid){
						Player p=desk.players.get(i);
						System.out.println(p.name+" died!");
						desk.players.remove(p);
					}
				}
			}
		}
		player.discard(this);
		desk.discard(this);
		return true;
	}
	@Override
	public final boolean sacrifice(Game game,Desk.Player player){
		return false;
	}
}
