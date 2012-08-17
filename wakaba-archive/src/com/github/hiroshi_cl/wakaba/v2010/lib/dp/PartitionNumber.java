package com.github.hiroshi_cl.wakaba.v2010.lib.dp;
/* 
 * 分割数をDPにより求める。
 * nを、k個に分割する方法の数を、p[n][k]とすると、
 * p[n][k]=p[n-1][k-1] + p[n-k][k] が成立する。
 * 
 * 分割に０を含めてもいい場合は、方法の数は p[n+k][k] となる。 
 * 
 * verified : pku1283 pku1664 
 */
public class PartitionNumber {
	static long[][] p;

	static long partition(int N, int K) {
		p = new long[N + 1][K + 1];
		p[1][1] = 1;
		for (int n = 1; n <= N; n++) {
			for (int k = 1; k <= K; k++) {
				if (k > 0 && n > 0)
					p[n][k] += p[n - 1][k - 1];
				if (n - k >= 0)
					p[n][k] += p[n - k][k];
			}
		}
		return p[N][K];
	}

	public static void main(String[] args) {// pku1664
		java.util.Scanner sc = new java.util.Scanner(System.in);
		partition(20, 10);
		int o = sc.nextInt();
		while (o-- > 0) {
			int n = sc.nextInt(), k = sc.nextInt();
			System.out.println(p[n + k][k]);
		}
		sc.close();
	}
}
