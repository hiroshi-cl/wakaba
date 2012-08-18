package com.github.hiroshi_cl.wakaba.v2010.sol.rp2009;
import java.util.*;
import java.math.*;
import java.io.*;
import static java.math.BigInteger.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class A2 {
	void debug(Object... os) {
//		System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			sc = new Scanner(new File("A.txt"));
		} catch (Exception e) {
		}
		char[] ss="23456789TJQKA".toCharArray();
		int[] f=new int[256];
		for (int i = 0; i < ss.length; i++) {
			f[ss[i]] = i;
		}
		for(;;){
			tru = sc.next().charAt(0);
			if(tru=='#')return;
			Card[][] cards=new Card[13][4];
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 13; j++) {
					char[] cs=sc.next().toCharArray();
					cards[j][i] = new Card(f[cs[0]],cs[1],i);
				}
			}
			int pl = 0;
			int[] point=new int[2];
			for (int i = 0; i < 13; i++) {
				led = cards[i][pl].suit;
				sort(cards[i]);
				point[cards[i][3].play%2]++;
				pl = cards[i][3].play;
				debug(pl);
			}
			if(point[0]>6){
				System.out.println("NS "+(point[0]-6));
			}
			else {
				System.out.println("EW "+(point[1]-6));
			}
		}
	}
	char tru;
	char led;
	class Card implements Comparable<Card>{
		public Card(int num, char suit, int play) {
			super();
			this.num = num;
			this.suit = suit;
			this.play = play;
		}
//		@Override
		public int compareTo(Card o) {
			if(suit == tru){
				if(o.suit == tru)return num-o.num;
				else return 1;
			}
			if(o.suit == tru)return -1;
			if(suit == led){
				if(o.suit ==led)return num-o.num;
				else return 1;
			}
			if(o.suit==led)return -1;
			return num-o.num;
		}
		int num;
		char suit;
		int play;

	}
	public static void main(String[] args) {
		new A2().run();
	}
}
