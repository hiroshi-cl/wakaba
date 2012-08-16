package com.github.hiroshi_cl.wakaba.v2009.sol.dp2009;
import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

class A {
	static void debug(Object... o){
//		System.err.println(deepToString(o));
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
			sc = new Scanner(new File("A"));
			System.setOut(new PrintStream("A.out"));
		} catch (Exception e) {
		}
		for(;;){
			int n=sc.nextInt(),a=sc.nextInt(),b=sc.nextInt(),c=sc.nextInt(),x=sc.nextInt();
			if(n==0)return;
			int res=0;
			int[] is=new int[n];
			for(int i=0;i<n;i++)is[i]=sc.nextInt();
			for(int i=0;i<n;i++){
				debug(x);
				int nx=is[i];
				int j=0;
				for(j=0;j<=c;j++){
					if(nx==x){
						break;
					}
					x=(a*x+b)%c;
					res++;
				}
				if(j==c+1 || res>10000){
					res=-1;
					break;
				}
				if(i==n-1)break;
				x=(a*x+b)%c;
				res++;
			}
			System.out.println(res);
		}
	}
}
