package com.github.hiroshi_cl.wakaba.v2010.sol.r2001;
import java.util.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
public class D {
	public static void main(String[] args) {
		new D().run();
	}
	final int[] ds = new int[] { 0, 0, 3, 3, 3, 3, 3, 4, 3, 4 };
	final int[] toi = new int[255];
	final char[][] toc = new char[10][];
	void run() {
		Scanner sc = new Scanner(System.in);
		for (int i = 0, k = 0; i < 10; i++) {
			toc[i] = new char[ds[i]];
			for (int j = 0; j < ds[i]; j++, k++) {
				toc[i][j] = (char) (k + 'a');
				toi[k + 'a'] = i;
			}
		}
		for (;;) {
			int n = sc.nextInt();
			if (n == 0) return;
			final int[][] js = new int[n][];
			String[] ss = new String[n];
			for (int i = 0; i < n; i++)
				ss[i] = sc.next();
			sort(ss);
			for (int i = 0; i < n; i++) {
				js[i] = tois(ss[i]);
			}
			char[] cs = sc.next().toCharArray();
			int m = cs.length;
			int[] is = new int[m];
			for (int i = 0; i < m; i++)
				is[i] = cs[i] - '0';
			ArrayList<Integer>[] dp = new ArrayList[m];
			for (int i = 0; i < m; i++)
				dp[i] = new ArrayList<Integer>();
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					if (fit(is, js[j], i)) {
						dp[i].add(j);
					}
				}
			}
			set = new TreeSet<String>();
			dfs(dp, ss, m - 1, "");
			for (String s : set) {
				System.out.println(s.substring(0, s.length() - 1) + ".");
			}
			System.out.println("--");
		}
	}
	TreeSet<String> set;
	void dfs(ArrayList<Integer>[] dp, String[] ss, int id, String now) {
		if (id == -1) {
			set.add(now);
			return;
		}
		if (dp[id].isEmpty()) return;
		for (int i : dp[id]) {
			dfs(dp, ss, id - ss[i].length(), ss[i] + " " + now);
		}
	}
	boolean fit(int[] is, int[] js, int end) {
		if (end - js.length + 1 < 0) return false;
		for (int i = 0; i < js.length; i++) {
			if (is[end - js.length + 1 + i] != js[i]) return false;
		}
		return true;
	}
	int[] tois(String s) {
		char[] cs = s.toCharArray();
		int[] js = new int[cs.length];
		for (int i = 0; i < cs.length; i++) {
			js[i] = toi[cs[i]];
		}
		return js;
	}
}