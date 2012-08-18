package com.github.hiroshi_cl.wakaba.v2010.sol.utpc2009;

import java.util.*;
import java.math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;

public class K {
	void debug(Object... os) {
		System.err.println(deepToString(os));
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		int a = sc.nextInt(), b = sc.nextInt();
		long ten = 1;
		for (int i = 0; i < b; i++)
			ten *= 10;
		BigInteger num = valueOf(ten).multiply(valueOf(ten - 1));
		BigInteger[] ds = divisors(num);
		sort(ds);
		boolean found = false;
		for (BigInteger x : ds) {
			BigInteger y = num.divide(x);
			if (x.add(y).mod(TWO).equals(ONE)) {
				BigInteger A = x.add(y).add(ONE).divide(TWO)
						.subtract(valueOf(ten));
				BigInteger B = x.subtract(y).add(ONE).divide(TWO);
				if (len(A) == a && len(B) == b) {
					found = true;
					System.out.println(A + " " + B);
				}
			}
		}
		if (!found)
			System.out.println("No cats.");
	}

	BigInteger TWO = valueOf(2);

	int len(BigInteger b) {
		if (b.compareTo(ZERO) <= 0)
			return -1;
		return b.toString().length();
	}

	ArrayList<BigInteger> list;

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
		for (BigInteger p = valueOf(2); p.multiply(p).compareTo(num) <= 0; p = p
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

	class Factor {
		BigInteger p;
		int l;

		Factor(BigInteger p, int l) {
			this.p = p;
			this.l = l;
		}
	}

	public static void main(String[] args) {
		new K().run();
	}
}