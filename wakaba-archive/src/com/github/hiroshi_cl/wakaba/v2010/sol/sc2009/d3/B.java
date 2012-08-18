package com.github.hiroshi_cl.wakaba.v2010.sol.sc2009.d3;
import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
class B {
	void debug(Object... os) {
		System.err.println(deepToString(os));
	}
	void run() {
		Scanner sc = new Scanner(System.in);
		for (int o = 1;; o++) {
			try {
				sc = new Scanner(new File(o + ".in"));
				System.setOut(new PrintStream(o + ".out"));
			} catch (Exception e) {
				return;
			}

			int t=sc.nextInt();
			int[]ts=new int[t];
			for (int i = 0; i < t; i++) {
				ts[i]=sc.nextInt();
			}
			int n=sc.nextInt();
			int[] ms=new int[100];
			fill(ms,24);
			for (int i = 0; i < n; i++) {
				int d=sc.nextInt();
				ms[d-1] = min(ms[d-1],sc.nextInt());
			}
			int[][] dp=new int[100][t];
			for (int i = 0; i < 100; i++) {
				fill(dp[i],101);
			}
			dp[0][0]=0;
			for (int i = 0; i < 99; i++) {
				for (int j = 0; j < t; j++) {
					if(ts[(j+1)%t] <= ms[i+1]){
						dp[i+1][(j+1)%t] = min(dp[i+1][(j+1)%t] , dp[i][j]);
					}
					dp[i+1][0] = min(dp[i+1][0],dp[i][j]+1);
				}
			}
			int res=101;
			for (int i = 0; i < t; i++) {
				res = min(dp[99][i],res);
			}
			System.out.println(res);

			Scanner sc1, sc2;
			try {
				sc1 = new Scanner(new File(o + ".out"));
				sc2 = new Scanner(new File(o + ".diff"));
				boolean ok = true;
				while (sc1.hasNext()) {
					if (!sc1.next().equals(sc2.next())) {
						ok = false;
						break;
					}
				}
				if (sc2.hasNext()) ok = false;
				System.err.println(ok);
			} catch (Exception e) {}
		}
	}
	public static void main(String[] args) {
		new B().run();
	}
}
