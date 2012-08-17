package com.github.hiroshi_cl.wakaba.v2010.lib.math.modulo;

/*
 逆元計算。

 mod が素数のときには体になり、乗法逆元が存在する。

 - 入力 : x, mod
 - 出力 :
 -- a * x = 1 なる （複数あれば）最小の 0 < a < mod を返す。
 -- 存在しないときは、 0 を返す。

 - O( log( min(x,mod) ) ).

 - extgcdが必要
 
 BigInteger.modInverse とかはあります。
 */

public class ModInverse {
	long invMod(long x, long mod) {
		long[] ls = ExtendedEuclideanAlgorithm.extgcd(x, mod);
		if (ls[0] != 1)
			return 0; // Unsolvable.
		return (ls[1] + mod) % mod;
	}
}
