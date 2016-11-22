package game;

import java.io.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.stream.events.Namespace;

public final class Game implements Runnable{
	
	public final Desk desk=new Desk();
	
	static public final String pileFile="src/piledata.txt";
	
	
	public void initPile(){
		File file=new File(pileFile);
		try{
			try(BufferedReader br = new BufferedReader(new FileReader(file));){
				String str=null;
				while((str=br.readLine())!=null){
					if(str.length()<=0) continue;
					Card res=Card.parase(str);
					if(res!=null){
						desk.add(res);
						System.out.println("get:"+res);
					}
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		desk.shuffle();
	}
	AI ais[];
	//用户神 数据
	public void initPlayer(String name[],AI ais[]){
		String pows[]=new String[name.length];
		for(int i=0;i<pows.length;i++){
			double ran=Math.random();
			if(ran<1.0/3)
				pows[i]="GodSkill_f1";
			else if (ran<2.0/3)
				pows[i]="GodSkill_f2";
			else
				pows[i]="GodSkill_f3";
		}
		this.initPlayer(name,pows,ais);
	}
	public void initPlayer(String name[],String power[],AI ais[]){
		if(name==null || name.length<=0) throw new IllegalArgumentException("Number of Players<0!");
		for(int i=0;i<name.length;i++){
			GodSkill r=new GodSkill(Origin.randGod(),"");
			try{
				Constructor<?> m=Class.forName("game."+power[i]).getConstructor(Origin.class);
				r=(GodSkill)m.newInstance(Origin.randGod());
			}catch(Exception e){
				e.printStackTrace();
			}
			desk.players.add(desk.new Player(name[i],r));
		}
		this.ais=ais;
	}
	
	
	private int step=0;
	private Origin now=Origin.darkness;
	public void showNow(Desk.Player player){
		System.out.println("=========================================");
		System.out.println("=========================================");
		System.out.println("Origin: "+now.toString().toUpperCase());
		System.out.println("=========================================");
		System.out.println(desk.toString());
		System.out.println("- - - - - - - - - - - - - - - - - - - - -");
		System.out.printf("%s's turn:\n",player.toString());
		System.out.println("- - - - - - - - - - - - - - - - - - - - -");
	}
	
	public void runRandStart(){
		now=Origin.rand();
		System.out.println("丢骰子:"+now.toString());
		for(Desk.Player p:desk.players){
			p.actPoint.add(now,p.power.origin);
		}
		desk.refreshCenter();
	}

	@Override
	public void run(){
		if(step==0){
			runRandStart();
			try{Thread.sleep(1500);}catch(Exception e){}
		}
		Desk.Player player=desk.players.get(step);
		if(ais[step]==null){
			this.showNow(player);
			runPlayer(player);
		}else{
			ais[step].ai(this,player);
		}
		step=(step+1)%desk.players.size();
		try{Thread.sleep(1500);}catch(Exception e){}
	}
	public void runPlayer(Desk.Player player){
		{
			boolean sacrificeable=true;
			boolean used=false;
			if(checkEnd())
				return;
			System.out.println(player.toStringAll());
			while(true){
				try{Thread.sleep(100);}catch(Exception e){}
				if(checkEnd())
					break;
				Card cards[]=player.getCards();
				String input=getInputLine("input command and the param");
				if(input==null) break;
				String ss[]=input.split(" ");
				if(ss[0].equals("help")){
					System.out.println("see");
					System.out.print("\t");
					System.out.println("to show all the thing you can see.");
					System.out.println("draw");
					System.out.print("\t");
					System.out.println("to draw the cards upto 7 cards in hand.");
					System.out.println("discard a b c ...");
					System.out.print("\t");
					System.out.println("to discard No.a, No.b, No.c and so on.");
					System.out.println("use a type b c ...");
					System.out.print("\t");
					System.out.println("to use Card(in hand) No.a by PayingType type, and effects on Card(on desk) No.b, No.c and so on.");
					System.out.println("type: darkness(using 2 darkness's act points to use a nihility Card)");
					System.out.println("      daylight(using 2 daylight's act points to use a nihility Card)");
					System.out.println("      others  (using 1 act points which is equal to the Card)");
					System.out.println("skill arg...");
					System.out.print("\t");
					System.out.println("to use the God Skill which belongs to your God.");
					System.out.println("sacrifice a");
					System.out.print("\t");
					System.out.println("to sacrifice a Archbishop No.a which belongs to you.");
					System.out.println("sacrifice a b");
					System.out.print("\t");
					System.out.println("to sacrifice a Believer No.b under the Archbishop No.a which belongs to you.");
				}
				else if(ss[0].equals("see")){
					this.showNow(player);
					System.out.println(player.toStringAll());
				}
				else if(ss[0].equals("draw")){
					if(sacrificeable==false || used){
						System.out.println("you cannot draw!");
						continue;
					}
					if(cards.length>=7){
						System.out.println("your cards in hand is more than 7, and cannot draw!");
						continue;
					}
					for(int i=cards.length;i<7;i++)
						player.draw();
					this.showNow(player);
					System.out.println(player.toStringAll());
					break;
				}else if(ss[0].equals("discard")){
					if(sacrificeable==false || used){
						System.out.println("you cannot draw!");
						continue;
					}
					ArrayList<Integer> js=new ArrayList<Integer>();
					try{
						for(int i=1;i<ss.length;i++){
							int j=Integer.parseInt(ss[i])-1;
							if(j<0||j>=cards.length) throw new Exception();
							js.add(j);
						}
					}catch(Exception e){
						System.out.println("input error!");
						continue;
					}
					for(int j:js){
						player.discard(cards[j]);
						desk.discard(cards[j]);
					}
					this.showNow(player);
					System.out.println(player.toStringAll());
					break;
				}else if(ss[0].equals("use")){
					try{
						int j=Integer.parseInt(ss[1])-1;
						if(j<0||j>=cards.length) throw new Exception();
						while(true){
							Origin type=null;
							if(ss.length>2)
								if(ss[2].equals(Origin.daylight.toString())) type=Origin.daylight;
								else if(ss[2].equals(Origin.darkness.toString())) type=Origin.darkness;
							boolean res=cards[j].canUse(player.actPoint,type);
							if(res){
								String sss[]=null;
								if(ss.length>3){
									sss=new String[ss.length-3];
									for(int i=0;i<sss.length;i++) sss[i]=ss[i+3];
								}
								if(cards[j] instanceof Rescue)
									cards[j].use(player.actPoint,type);
								if(useFunction(player,cards[j],false)
										&& cards[j].use(this,player,sss)){
									if(cards[j] instanceof Rescue==false)
										cards[j].use(player.actPoint,type);
									System.out.println("use successfully!");
									used=true;
								}
								else
									System.out.println("use failed!");
								break;
							}
							if(ss.length<=2){
								String sss[]=new String[3];
								for(int i=0;i<2;i++) sss[i]=ss[i];
								ss=sss;
							}
							ss[2]=getInputLine("you have NOT enough act points, please change the using type(origin/daylight/darkness)");
							if(ss[2]==null)
								break;
						}
					}catch(Exception e){
						e.printStackTrace();
						System.out.println("input error!");
						continue;
					}
				}else if(ss[0].equals("skill")){
					String sss[]=null;
					if(ss.length>1){
						sss=new String[ss.length-1];
						for(int i=0;i<sss.length;i++) sss[i]=ss[i+1];
					}
					if(useFunction(player,player.power,false)
							&& player.power.use(this,player,sss)){
						System.out.println("use successfully!");
					}
					else
						System.out.println("you have No skill!");
				}else if(ss[0].equals("sacrifice")||ss[0].equals("sac")){
					if(sacrificeable==false){
						System.out.println("you cannot sacrifice!");
						continue;
					}
					try{
						int j=Integer.parseInt(ss[1])-1;
						if(j<0||j>=cards.length) throw new Exception();
						sacrificeable=false;
						if(useFunction(player,cards[j],true)){
							if(cards[j].sacrifice(this,player))
								System.out.println("sacrifice successfully!");
							else
								System.out.println("sacrifice failed!");
						}
						else
							System.out.println("sacrifice failed!");
					}catch(Exception e){
						e.printStackTrace();
						System.out.println("input error!");
						continue;
					}
				}
			}
		}
	}
	
	public boolean useFunction(Desk.Player player,Card card,boolean sudo){
		if(player==null||card==null) return true;
		if(!sudo && card instanceof Rescue==false) return true;
		System.out.println(player.toString()+" use a card:"+card.toString());
		for(int i=0;i<desk.players.size();i++){
			Desk.Player p=desk.players.get(i);
			Card car=null;
			for(Card c:p.getCards()){
				if(c instanceof Rescue_f3
						&& !((Rescue_f3)c).using){
					car=c;
					break;
				}
			}
			if(car==null) continue;
			System.out.println("query "+p.toString()+" :");
			String ans;
			if(ais[i]==null)
				ans=getInputLine("\tinput yes to use "+car.toString()+", others to pass");
			else
				ans=(ais[i].getWuxie(this,p,player)?"yes":"no");
			if(ans.equals("yes")){
				((Rescue_f3)car).using=true;
				if(!useFunction(p,car,false)){
					//被无效了
					((Rescue_f3)car).using=false;
					continue;
				}
				System.out.println(p.toString()+" use "+car.toString()+" to make the card("+card.toString()+") 无效了!");
				player.discard(card);
				p.discard(car);
				desk.discard(car);
				desk.discard(card);
				return false;
			}
		}
		return true;
	}
	
	public boolean checkEnd(){
		return this.desk.players.size()<=1;
	}
	
	
	static public Scanner in=new Scanner(System.in);
	
	static public String getInputLine(String msg){
		System.out.print(msg+": ");
		String res=in.nextLine();
		res=res.toLowerCase();
		if(res.equals("back")||res.equals("end")) res=null;
		return res;
	}
	static public String getInputLine2(String msg){
		System.out.print(msg+": ");
		String res=in.nextLine();
		if(res.equals("back")||res.equals("end")) res=null;
		return res;
	}
}
