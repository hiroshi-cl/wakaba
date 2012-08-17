package com.github.hiroshi_cl.wakaba.v2010.lib.string.search;
import java.util.*;
/*
 * RabinKarpは、長さの等しい複数の文字列を、同時に検索するのに適したアルゴリズム。
 * Hashを用いて、計算量の見込みを減らしている。
 * 構築:O(t*m) , 応答:平均的に、O(z*m).
 * ただし、tは検索する文字列の数、mはその長さ、zはマッチする回数。
 *
 * Verified: 自前,pku:3690
 */
public class RabinKarp {
	final long C = 1000000009;// 素数
	long[] cs;
	final int MOD = 1234567891;// 素数
	long[][] ass;
	HashMap<Long, TreeSet<Integer>> has = new HashMap<Long, TreeSet<Integer>>();
	int t, m;
	RabinKarp(long[][] ass) {// 全て同じ長さであること。
		t = ass.length;
		this.m = ass[0].length;
		for (int k = 0; k < t; k++)
			for (int i = 0; i < m; i++)
				ass[k][i] %= MOD;
		this.ass = ass;
		build();
	}
	void build() {
		cs = new long[m + 1];
		cs[0] = 1;
		for (int i = 1; i <= m; i++)
			cs[i] = mod(cs[i - 1] * C);
		for (int k = 0; k < t; k++) {
			long ha = 0;
			for (int i = 0; i < m; i++) {
				ha = mod(ha + cs[i] * ass[k][m - 1 - i]);
			}
			TreeSet<Integer> set = has.containsKey(ha) ? has.get(ha) : new TreeSet<Integer>();
			set.add(k);
			has.put(ha, set);
		}
	}
	long[] ts;
	int searchFrom(long[] ts) {
		int n = ts.length;
		for (int i = 0; i < n; i++)
			ts[i] %= MOD;
		this.ts = ts;
		long[] hts = new long[n - m + 1];
		for (int i = 0; i < m; i++) {
			hts[0] = mod(hts[0] + cs[i] * ts[m - 1 - i]);
		}
		for (int i = 0; i < n - m; i++) {
			hts[i + 1] = mod(hts[i] * C + ts[i + m] - mod(cs[m] * ts[i]));
		}
		int res = 0;
		for (int i = 0; i <= n - m; i++) {
			if (has.containsKey(hts[i])) for (int k : has.get(hts[i]))
				if (same(i, ass[k])) res++;
		}
		return res;
	}
	boolean same(int s, long[] as) {
		for (int i = 0; i < m; i++) {
			if (ts[s + i] != as[i]) return false;
		}
		return true;
	}
	long mod(long l) {
		return (l + MOD) % MOD;
	}

	public static void main(String[] args) {
		int o = 10;
		int t = 1000, n = 100000, m = 15;
		int max = 2;
		Random r = new Random();
		while (o-- > 0) {
			long[] ts = new long[n];
			long[][] ass = new long[t][m];
			for (int i = 0; i < n; i++)
				ts[i] = Long.MAX_VALUE - r.nextInt(max);
			for (int i = 0; i < t; i++) {
				for (int j = 0; j < m; j++) {
					ass[i][j] = Long.MAX_VALUE - r.nextInt(max);
				}
			}
			System.out.println("start RK");
			long st = System.currentTimeMillis();
			RabinKarp rk = new RabinKarp(ass);
			System.out.println(System.currentTimeMillis() - st);
			int res = rk.searchFrom(ts);
			System.out.println(System.currentTimeMillis() - st);
			System.out.println("res :" + res);
			System.out.println("start Naive");
			st = System.currentTimeMillis();
			int ans = 0;
			for (int i = 0; i < t; i++) {
				for (int j = 0; j <= n - m; j++) {
					if (same(ts, ass[i], j, m)) ans++;
				}
			}
			System.out.println(System.currentTimeMillis() - st);
			System.out.println("ans :" + ans);
		}
	}
	static boolean same(long[] ts, long[] as, int s, int m) {
		for (int i = 0; i < m; i++) {
			if (ts[s + i] != as[i]) return false;
		}
		return true;
	}
}