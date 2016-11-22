package game;

public final class ActPoint implements Comparable<ActPoint>,Cloneable{
	
	public final int length=Origin.values().length-3;
	private final int ps[];
	public ActPoint(){
		ps=new int[length];
	}
	public ActPoint(int[] aps){
		this();
		if(aps==null) return;
		for(int i=0;i<length && i<aps.length;i++)
			this.ps[i]=aps[i];
	}
	
	public int get(Origin org){
		if(org==null) return 0;
		return ps[org.ordinal()];
	}
	public boolean add(Origin org,int point){
		if(org==null) return false;
		ps[org.ordinal()]+=point;
		return true;
	}
	public boolean add(Origin org,Origin god){
		if(org==null||god==null) return false;
		if(org==Origin.daylight){
			if(god==Origin.daylight)
				this.add(org,2);
			else if(god==Origin.dawn)
				this.add(org,1);
		}else if(org==Origin.darkness){
			if(god==Origin.darkness)
				this.add(org,2);
			else if(god==Origin.dusk)
				this.add(org,1);
		}else if(org==Origin.nihility){
			if(god==Origin.dusk || god==Origin.dawn)
				this.add(org,1);
		}
		return true;
	}
	public boolean substract(Origin org,int point){
		if(org==null) return false;
		if(ps[org.ordinal()]<point) return false;
		ps[org.ordinal()]-=point;
		return true;
	}
	@Override
	public int compareTo(ActPoint o) {
		if(o==null) return 1;
		for(int i=0;i<length;i++){
			if(this.ps[i]<o.ps[i]) return -1;
			if(this.ps[i]>o.ps[i]) return 1;
		}
		return 0;
	}
	@Override
	public boolean equals(Object o){
		if(o==null) return false;
		if(o instanceof ActPoint)
			return this.compareTo((ActPoint)o)==0;
		return false;
	}
	@Override
	public ActPoint clone(){
		return new ActPoint(this.ps);
	}
	
	@Override
	public String toString(){
		StringBuilder sb=new StringBuilder();
		Origin os[]=Origin.values();
		for(int i=0;i<length;i++){
			sb.append(os[i].toString());
			sb.append(": ");
			sb.append(this.ps[i]);
			sb.append('\t');
		}sb.append('\n');
		return sb.toString();
	}
	
	
}
