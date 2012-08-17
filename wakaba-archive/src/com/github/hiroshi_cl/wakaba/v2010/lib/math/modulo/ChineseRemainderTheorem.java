package com.github.hiroshi_cl.wakaba.v2010.lib.math.modulo;

import static java.math.BigInteger.*;
import java.math.*;

/*
 中国剰余定理により、
 gcd(p,q) == 1 であるとき、
 x = a (mod p) , x = b (mod q) をみたす x が、 0<= x < pq で、ただひとつ存在する。

 さらに、
 p_0 , p_1 , ... , p_n-1 のどの2数をとっても、最大公約数が 1 で あるとき、
 x = a_i (mod p_i) をみたす x が、 0<= x < p_0 p_1 .. p_n-1 で、ただひとつ存在する。
*/

public class ChineseRemainderTheorem {
/*
 - 入力 : a (mod p), b (mod q)
 - 出力 : x = a (mod p) , x = b (mod q) をみたす x 

 - O( log( min(p,q) ) ).
 - extgcdが必要
 
 2^31 > a,b >= 0 ,2^31> p,q > 0 であること。
 */
	static long chineseRemainderTheorem(long a, long b, long p, long q) {
		BigInteger mod = valueOf(p * q);
		long[] ds = ExtendedEuclideanAlgorithm.extgcd(p, q);
		return valueOf(ds[1]).multiply(valueOf(b - a)).multiply(valueOf(p))
				.add(valueOf(a)).mod(mod).add(mod).mod(mod).longValue();
	}

/*
- 入力 : {a_i (mod p_i)}_i
- 出力 : x = a_i (mod p_i) をみたす x

-  O( n * log( p_0 p_1 .. p_n-2 ) ) ?
- extgcdが必要

 2^31 > a_i >=0 , p_i > 0 , p_0 p_1 .. p_n-1 < 2^31 であること。
 */
	static long chineseRemainderTheorem(long[] a, long[] p) {
		long x = a[0];
		long np = p[0];
		for (int i = 1; i < a.length; i++) {
			x = chineseRemainderTheorem(x, a[i], np, p[i]);
			np *= p[i];
		}
		return x;
	}
}