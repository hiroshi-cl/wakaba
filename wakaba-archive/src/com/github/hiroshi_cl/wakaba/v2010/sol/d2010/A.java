package com.github.hiroshi_cl.wakaba.v2010.sol.d2010;
import java.util.*;
import java.math.*;
import java.io.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;
public class A {
	public static void main(String[] args) {
		new A().run();
	}
	void debug(Object...os){
		System.err.println(deepToString(os));
	}
	static final int[] dx = { -1, 0, 1, 0 };
	static final int[] dy = { 0, -1, 0, 1 };
	void run(){
		Scanner sc = new Scanner(System.in);
		try{
			String s = getClass().getSimpleName();
			sc = new Scanner(new File(s));
			System.setOut(new PrintStream(new File(s+".out")));
		}catch (Exception e) {
		}
		while(true) {
			int N = sc.nextInt();
			if(N == 0)
				return;
			int[] n = new int[N];
			int[] d = new int[N];
			for(int i = 1; i < N; i++) {
				n[i] = sc.nextInt();
				d[i] = sc.nextInt();
			}
			int[] x = new int[N];
			int[] y = new int[N];
			for(int i = 1; i < N; i++) {
				x[i] = x[n[i]] + dx[d[i]];
				y[i] = y[n[i]] + dy[d[i]];
			}
			int minx = Integer.MAX_VALUE, maxx = Integer.MIN_VALUE;
			int miny = Integer.MAX_VALUE, maxy = Integer.MIN_VALUE;
			for(int i = 0; i < N; i++) {
				minx = min(minx, x[i]);
				maxx = max(maxx, x[i]);
				miny = min(miny, y[i]);
				maxy = max(maxy, y[i]);
			}
			System.out.println((maxx - minx + 1) + " " + (maxy - miny + 1));
		}
	}
}
