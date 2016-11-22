package main;
import java.lang.reflect.Constructor;
import java.util.Scanner;

import game.*;

public class Main {
	static public void main(String... args){
		Game game=new Game();
		System.out.println("hey!");
		game.initPile();
		Scanner in=new Scanner(System.in);
		System.out.println("Input the number of players:");
		int num=in.nextInt();
		System.out.println("Input the number of human players:");
		int human_num=in.nextInt();
		AI ai[]=new AI[num];
		for(int i=0;i<num;i++){
			if(i<human_num)
				ai[i]=null;
			else
				ai[i]=new AI_1();
		}
		String names[]=new String[num];
		for(int i=0;i<num;i++)
			names[i]=(i<human_num?"Human_Player":"AI_Player")+"_"+(i+1);
		game.initPlayer(names,ai);
		System.out.println("game start!");
		while(!game.checkEnd()){
			game.run();
		}
		System.out.println(game.desk.players.get(0).name+" win!");
	}
}
