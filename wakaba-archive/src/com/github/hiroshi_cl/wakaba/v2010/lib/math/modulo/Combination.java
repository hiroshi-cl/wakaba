package com.github.hiroshi_cl.wakaba.v2010.lib.math.modulo;

/*
 nCk をmod p で計算するには、ルーカスの定理 というものを使う。
 n = n1 n2 ... ,  k = k1 k2 ... と p進法であらわすと、nCk = n1Ck1 * n2Ck2 * ... になる、というもの。
 i < p における、i! mod p , 1/i! mod p の値を覚えておけば、後は簡単にやれる。

 - 入力 : 
 -- long n, long k, long mod, long[] fact, long[] factinv.
 -- fact, factinv（階乗とその逆数）はあらかじめ p を法として (p-1)! まで計算しておくこと。
 -- 逆元計算は、invModを使うか、1/a = a^(p-2) mod p を使う。
 - 出力 : nCk をmod p で。

 - O( log(n) * p ). logの底はp.

 p が素数でないといけない。
 一般の法 m で計算するときは、mを素因数分解して、平方因子が存在しなければ、
 それぞれの素因数に関して計算し、それから中国剰余定理を使えばよい。
 平方因子があるときはどうするんだろう……。
 */

public class Combination {
	static int chooseMod(long n, long k, long p, long[] fact, long[] factinv) {
		if (k > n)
			return 0;
		if (n < p)
			return (int) (((fact[(int) n] * factinv[(int) k]) % p)
					* factinv[(int) (n - k)] % p);
		long res = 1;
		while (k > 0) {
			res = (res * chooseMod(n % p, k % p, p, fact, factinv)) % p;
			n /= p;
			k /= p;
		}
		return (int) res;
	}
}
