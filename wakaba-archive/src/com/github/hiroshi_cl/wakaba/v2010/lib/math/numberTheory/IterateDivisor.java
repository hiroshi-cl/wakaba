package com.github.hiroshi_cl.wakaba.v2010.lib.math.numberTheory;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static java.math.BigInteger.valueOf;
import java.math.BigInteger;
import java.util.ArrayList;

public class IterateDivisor {
	ArrayList<BigInteger> list;
	BigInteger TWO = valueOf(2);

	BigInteger[] divisors(BigInteger num) {
		list = new ArrayList<BigInteger>();
		Factor[] ps = toPrimes(num);
		dfs(ps, 0, ONE);
		return list.toArray(new BigInteger[0]);
	}

	void dfs(Factor[] ps, int id, BigInteger val) {
		int len = ps.length;
		if (id == len)
			list.add(val);
		else {
			for (int i = 0; i <= ps[id].l; i++) {
				dfs(ps, id + 1, val);
				val = val.multiply(ps[id].p);
			}
		}
	}

	Factor[] toPrimes(BigInteger num) {
		ArrayList<Factor> res = new ArrayList<Factor>();
		for (BigInteger p = TWO; p.multiply(p).compareTo(num) <= 0; p = p
				.add(ONE)) {
			int l = 0;
			while (num.mod(p).equals(ZERO)) {
				num = num.divide(p);
				l++;
			}
			if (l > 0)
				res.add(new Factor(p, l));
		}
		if (num.compareTo(ONE) > 0)
			res.add(new Factor(num, 1));
		return res.toArray(new Factor[0]);
	}

	class Factor {// pow(p,l).
		BigInteger p;
		int l;

		Factor(BigInteger p, int l) {
			this.p = p;
			this.l = l;
		}
	}
}