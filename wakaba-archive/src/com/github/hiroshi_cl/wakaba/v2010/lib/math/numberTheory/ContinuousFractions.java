package com.github.hiroshi_cl.wakaba.v2010.lib.math.numberTheory;

import java.util.ArrayList;

/*
 * p/q (p>q>0) を、連分数表示
 * 
 * ........1...
 * a0.+.-------
 * ..........1.
 * .....a1.+.--
 * ..........an
 * (an=1とする)
 * 
 * した時の、aの値をdensに格納する。
 * 
 * verified: pku3197
 */
class ContinuousFractions {
	long p, q;
	ArrayList<Long> dens;

	public ContinuousFractions(long p, long q) {
		this.p = p;
		this.q = q;
		dens = new ArrayList<Long>();
	}

	void run() {
		long p = this.p, q = this.q;
		while (p % q != 0) {
			dens.add(p / q);
			p %= q;
			long tmp = p;
			p = q;
			q = tmp;
		}
		if (p / q > 1) {
			dens.add(p / q - 1);
		}
		dens.add(1l);
	}
}