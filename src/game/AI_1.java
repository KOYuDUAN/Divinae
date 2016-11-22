package game;

import game.Desk.*;

public class AI_1 implements AI{
	public enum Oper{
		draw,use(new Object[2]),discard;
		
		Object arg[];
		Oper(){
			this(null);
		}
		Oper(Object arg[]){
			this.arg=arg;
		}
	}
	
	@Override
	public void ai(Game game, Player me) {
		game.showNow(me);
		
		Oper oper=null;
		Card cards[]=me.getCards();
		if(cards.length<=0) oper=Oper.draw;
		else{
			for(int i=0;oper==null && i<cards.length;i++){
				if(cards[i] instanceof Rescue_f3) continue;
				for(Origin type:Origin.values())
					if(cards[i].canUse(me.actPoint,type)){
						oper=Oper.use;
						oper.arg[0]=i;
						oper.arg[1]=type;
						break;
					}
			}
			if(oper==null) oper=Oper.discard;
		}
		System.out.print(">>>"+me.toString()+" "+(oper==null?"do nothing":oper.toString())+" ");
		if(oper==Oper.draw){
			System.out.println(""+(7-cards.length)+" cards!");
			for(int i=cards.length;i<7;i++)
				me.draw();
		}else if(oper==Oper.use){
			String s[]=null;
			Card card=cards[(int)(oper.arg[0])];
			System.out.print(card.toString());
			boolean flag=false;
			if(card instanceof Rescue){
				if(card.use(me.actPoint,(Origin)oper.arg[1])
						&&game.useFunction(me,card,false)
						&&card.use(game,me,s)){
					flag=true;
				}
			}else{
				if(game.useFunction(me,card,false)
						&&card.use(game,me,s)){
					flag=true;
				}
			}
				
			if(flag){
				System.out.println(" success");
			}else System.out.println(" fail");
		}else if(oper==Oper.discard){
			System.out.println("");
			for(int i=0;i<cards.length;i++){
				me.discard(cards[i]);
				game.desk.discard(cards[i]);
			}
		}else{
			System.out.println();
		}
	}

	@Override
	public boolean getWuxie(Game game, Player me, Player p) {
		return me!=p;
	}


}
