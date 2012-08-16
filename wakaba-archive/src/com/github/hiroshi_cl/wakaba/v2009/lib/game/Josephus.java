package com.github.hiroshi_cl.wakaba.v2009.lib.game;
/*
 * josephus(n,k,s)は、
 * n人の人が円形に並んでいて、k人ごとに殺していったとき、s番目に殺される人を求める。
 * n,k は 0-originで、０から数え始めている。（つまり、最初に殺されるのは k-1.）
 * s は 1-origin.
 * josephus(n,m,k,s)は、同様のルールで、最初に殺されるのがm(0-origin)の場合。
 *
 * verified : pku2359,pku2244,pku3517 (全て n==s の場合)
 *
 * オーダー不明  。文献を見つけるか、自分で考えるべし。
 */
public class Josephus {
	int josephus(int n, int k, int s) {
		long x = k * s;
		while (x > n)
			x = ((x - n) * k - 1) / (k - 1);
		return (int) x - 1;
	}

	int josephus(int n, int m, int k, int s) {
		return ((josephus(n, k, s) - k + 1 + m) % n + n) % n;
	}

	public static void main(String[] args) {
		new Josephus().run();
	}

	void run() {// Japan2007A(pku:3517)
		java.util.Scanner sc = new java.util.Scanner(System.in);
		for (;;) {
			int n = sc.nextInt(), k = sc.nextInt(), m = sc.nextInt() - 1;
			if (n == 0)
				break;
			System.out.println(josephus(n, m, k, n) + 1);
		}
	}
}
