package com.github.hiroshi_cl.wakaba.v2010.sol.r2009;
import java.util.*;
import java.math.*;
import java.io.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
public class A {
	void debug(Object... os){
//		System.err.println(deepToString(os));
	}
	void run(){
		Scanner sc=new Scanner(System.in);
		try{
			sc = new Scanner(new File("A.in"));
		}catch (Exception e) {
			// TODO: handle exception
		}
		for(;;){
			int n=sc.nextInt(),m=sc.nextInt();
			if(n==0 && m==0)return;
			int[] is=new int[21];
			int[] js=new int[21];
			for (int i = 0; i < n; i++) {
				is[sc.nextInt()]++;
			}
			for (int i = 0; i < m; i++) {
				js[sc.nextInt()]++;
			}
			debug(is,js);
			int res=0;
			for (int i = 0; i < 21; i++) {
				res += max(is[i],js[i]) * i;
			}
			System.out.println(res);
			
		}
		
	}
	public static void main(String[] args) {
		new A().run();
	}
}
