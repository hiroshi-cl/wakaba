package com.github.hiroshi_cl.wakaba.v2010.lib.math.modulo;

/*
 拡張Euclidの互除法で1次不定方程式の特解を求める。
 ついでに普通のgcdとlcmも。

 |d| < gcd(x,y) となる場合には整数解が存在しない

- 入力 : x, y
- 出力 :
-- d = ax + by = gcd(x,y) を満たす d,a,b を、この順番で返す。
-- |a|< y , |b| < x. (x=y=1 -> d=1,a=0,b=1 のときを除く)

- O( log( min(x,y) ) ).
 */

public class ExtendedEuclideanAlgorithm {
	static long gcd(long x, long y) {
		return y == 0 ? x : gcd(y, x % y);
	}

	static long lcm(long x, long y) {
		return x / gcd(x, y) * y;
	}

	static long[] extgcd(long x, long y) {
		if (y == 0)
			return new long[] { x, 1, 0 };
		long[] ls = extgcd(y, x % y);
		return new long[] { ls[0], ls[2], ls[1] - (x / y) * ls[2] };
	}
}
