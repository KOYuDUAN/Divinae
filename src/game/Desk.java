package game;

import java.util.*;

public final class Desk {
	
	private final Queue<Card> pile=new LinkedList<Card>();
	public final Queue<Card> dispile=new LinkedList<Card>();
	public final List<Believer> center=new LinkedList<Believer>();
	public final ArrayList<Player> players=new ArrayList<Player>();
	
	
	public final class Player{
		private final ArrayList<Card> cards=new ArrayList<Card>();
		public final ArrayList<Archbishop> archbishops=new ArrayList<Archbishop>();
		public GodSkill power;
		public final int getNumberOfBelievers(){
			int res=0;
			for(Archbishop arc:archbishops)
				res+=arc.getNumberOfBelievers();
			return res;
		}
		protected ActPoint actPoint=new ActPoint();
		public final String name;
		public Player(String name,GodSkill power){
			this.name=name;
			this.power=power;
		}
		
		public Card[] getCards(){
			if(this.cards.size()<=0){
				return new Card[0];
			}
			Card res[]=new Card[this.cards.size()];
			this.cards.toArray(res);
			return res;
		}
		public void draw(){
			this.draw(Desk.this.draw());
		}
		public void draw(Card card){
			if(card==null || this.cards.contains(card)) return;
			this.cards.add(card);
		}
		public void discard(Card card){
			if(card==null || !cards.contains(card)) return;
			cards.remove(card);
		}
		
		@Override
		public String toString(){
			return this.name+"["+this.power.origin.toString()+"]"+"("+this.cards.size()+")";
		}
		public String toStringAll(){
			StringBuilder sb=new StringBuilder();
			sb.append("your skill is: ");
			sb.append( this.power.description);
			sb.append('\n');
			sb.append("your cards in hand:");
			sb.append('\n');
			sb.append(Desk.toString(cards,"\t"));
			sb.append('\n');
			sb.append("your archbishops on desk:");
			sb.append('\n');
			sb.append(Desk.toString(this.archbishops,"\t"));
			sb.append("your act points:");
			sb.append('\n');
			sb.append(this.actPoint.toString());
			return sb.toString();
		}
		public String toString2(){
			StringBuilder sb=new StringBuilder();
			sb.append(this.toString());
			sb.append('\n');
			sb.append("\t  archbishops on desk:");
			sb.append('\n');
			sb.append(Desk.toString(this.archbishops,"\t\t"));
			sb.append("\t  act points: ");
			sb.append(this.actPoint.toString());
			return sb.toString();
		}
	}
	
	public void add(Card card){
		if(card==null) return;
		this.pile.add(card);
	}
	public void shuffle(){
		Collections.shuffle((LinkedList<Card>)this.pile);
	}
	public Card draw(){
		if(pile.isEmpty()){
			for(Card c:this.dispile)
				this.add(c);
			this.dispile.clear();
		}
		if(pile.isEmpty()) return null;
		return pile.poll();
	}
	public void discard(Card card){
		if(card==null) return;
		if(this.dispile.contains(card)) return;
		this.dispile.add(card);
	}
	public void layCard(Believer bel){
		if(bel==null) return;
		bel.available=false;
		this.center.add(bel);
	}
	public void refreshCenter(){
		for(Believer b:this.center){
			b.available=true;
		}
	}

	static public String toString(Object os[],String tab){
		StringBuilder sb=new StringBuilder();
		for(int i=0;os!=null && i<os.length;i++){
			sb.append(tab);
			sb.append('[');
			sb.append(i+1);
			sb.append(']');
			sb.append(": ");
			sb.append(os[i].toString());
			sb.append('\n');
		}
		return sb.toString();
	}
	static public String toString(List<?> arr,String tab){
		return toString(arr.toArray(),tab);
	}
	
	@Override
	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append("Pile:("+pile.size()+")\n");
		sb.append("Dispile:("+dispile.size()+")\n");
		sb.append("Center: ");
		sb.append('\n');
		sb.append(Desk.toString(center,"\t"));
		sb.append('\n');
		sb.append("Players:\n");
		for(int i=0;i<this.players.size();i++){
			sb.append("\t");
			sb.append(players.get(i).toString2());
			sb.append("\n");
		}
		return sb.toString();
	}
	
}
