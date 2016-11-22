package game;

public enum Creed {
	natural,
	people,
	symbol,
	mysterious,
	chaos;
	
	public boolean get(int st){
		return (st&(1<<this.ordinal()))!=0;
	}
	static public int get(int a,int b){
		int c=a&b,res=0;
		while(c!=0){
			c&=c-1;
			res++;
		}
		return res;
	}
	static public String toString(int st){
		StringBuilder sb=new StringBuilder();
		Creed[] values=Creed.values();
		boolean first=true;
		for(int i=0;i<values.length;i++){
			if(values[i].get(st)){
				if(first) first=false;
				else sb.append(",");
				sb.append(values[i].toString());
			}
		}
		return sb.toString();
	}
	public static int parseInt(String str) {
		int res=0;
		for(int i=0;i<str.length();i++){
			res<<=1;
			if(str.charAt(i)=='1') res|=1;
		}
		return res;
	}
}
