package com.github.hiroshi_cl.wakaba.v2009.sol.d2009.a;
import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
class Main {
	void debug(Object... o) {
		System.err.println(deepToString(o));
	}
	void run() {
		Scanner sc = new Scanner(System.in);
//		try {
//			sc = new Scanner(new File("A"));
//			System.setOut(new PrintStream("A.out"));
//		} catch (Exception e) {}
		for(;;){
			int n=sc.nextInt();
			int p=sc.nextInt();
			if(n+p==0)return;
			int num=p;
			int[] is=new int[n];
			for(int i=0;;i++){
				i=i%n;
				if(num==0){
					num+=is[i];
					is[i]=0;
				}
				else{
					num--;
					is[i]++;
				}
				if(is[i]==p){
					System.out.println(i);;
					break;
				}
			}
		}
	}
	public static void main(String[] args) {
		new Main().run();
	}
}
