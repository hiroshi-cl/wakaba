package com.github.hiroshi_cl.wakaba.v2010.sol.r2009;
import java.util.*;
import java.math.*;
import java.io.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;

public class B {
	void debug(Object... os) {
		System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			sc = new Scanner(new File("B.in"));
		} catch (Exception e) {
		}
		for (;;) {
			n = sc.nextInt();
			if (n == 0)
				return;
			as = new String[n];
			bs = new String[n];
			for (int i = 0; i < n; i++) {
				as[i]=sc.next();
				bs[i]=sc.next();
			}
		
			String c = sc.next();
			d = sc.next();
			int INF = 1 << 28;
			res = INF;
			dfs(c, 0);
			System.out.println(res == INF ? -1 : res);
		}
	}

	String[] as, bs;
	int n;
	int res;
	String d;

	void dfs(String now, int depth) {
		if(now.equals(d)){
			res = min(res,depth);
		}
		if(now.length() > d.length())return;
		for (int i = 0; i < n; i++) {
			if(now.contains(as[i])){
				String next = now.replaceAll(as[i], bs[i]);
				dfs(next,depth+1);
			}
		}
	}

	public static void main(String[] args) {
		new B().run();
	}
}
