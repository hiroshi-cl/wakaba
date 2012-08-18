package com.github.hiroshi_cl.wakaba.v2010.sol.sc2009.d2;
import java.io.*;
import java.util.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
public class J2 {
	static void debug(Object... os) {
		System.err.println(deepToString(os));
	}

	static final int M = 1000003;
	void run() {
		Scanner sc = new Scanner(System.in);
		final int n = sc.nextInt();
		final int k = sc.nextInt();
		final int[] memo = new int[n+1];
		Arrays.fill(memo, -1);

		int ret = 0;
		for(int i = 1; i <= n; i++) {
			int m = gcd(i, n);
			if(memo[m] < 0)
				memo[m] = calc(n, m, k);
			ret = (ret + memo[m]) % M;
		}

		System.out.println(1L * ret * invMod(n) % M);
	}

	int[][] dp;

	int calc(int n, int m, int k) {
		if(k >= n) return (int) powMod(2, m);
		else if(k >= m) return (int) powMod(2, m) - 2;

		if(dp == null) 
		{
			dp = new int[2][n];
			dp[0][0] = 1;

			for(int i = 0; i < n; i++)
				for(int j = 1; j <= k && i + j < n; j++)
					for(int b = 0; b <= 1; b++)
						dp[b][i+j] = (dp[b][i+j] + dp[b^1][i]) % M;
		}


		int ret = 0;
		for(int i = 1; i <= k; i++)
			ret = (ret + dp[1][m-i] * i) % M;
		return ret * 2 % M;
	}

	long powMod(int x, int n) {
		if(n == 0)
			return 1;
		else if(n == 1)
			return x % M;

		long p = powMod(x, n / 2);
		p = p * p % M;
		if(n % 2 == 1)
			p = p * x % M;
		return p;
	}

	int gcd(int x, int y) {
		return y == 0 ? x : gcd(y, x % y);
	}

	int invMod(int a) {
		return invMod(1, a, 0, M);
	}

	int invMod(int u, int d, int v1, int v2) {
		return v2 == 0 ? u : invMod(v1, v2, ((u - d / v2 * v1) % M + M) % M, d % v2);
	}

	public static void main(String... args) {
		if(true)
			for(int i = 1;; i++) {
				try {
					System.setIn(new FileInputStream(i + ".in"));
					System.setOut(new PrintStream(i + ".out"));
					long time = System.currentTimeMillis();
					new J2().run();
					debug(System.currentTimeMillis() - time);
					Scanner sc1 = new Scanner(new File(i + ".out"));
					Scanner sc2 = new Scanner(new File(i + ".diff"));
					boolean ok = true;
					while (sc1.hasNextLine()) {
						if (!sc1.nextLine().equals(sc2.nextLine())) {
							ok = false;
							break;
						}
					}
					if (sc2.hasNext()) ok = false;
					debug(i, ok);
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
		else
			new J2().run();
	}
}