package com.github.hiroshi_cl.wakaba.v2010.lib.math.numberTheory;

import static java.lang.Math.*;
import java.util.*;

/*
 * 1/n (n:2以上の自然数)の循環節と、非循環節を、それぞれ、rep,nonrepに格納する。
 * 
 * verified : pku3720
 */
public class RepeatingDecimal {
	int n;
	int[] nonrep;
	ArrayList<Integer> rep;

	RepeatingDecimal(int n) {
		this.n = n;
		rep = new ArrayList<Integer>();
	}

	void run() {
		int twos = twos(n), fives = fives(n);
		nonrep = new int[max(twos, fives)];
		int r = 10;
		for (int j = 0; j < nonrep.length; j++) {
			nonrep[j] = r / n;
			r %= n;
			r *= 10;
		}
		if (r == 0)
			return;
		int fr = r;
		do {
			rep.add(r / n);
			r %= n;
			r *= 10;
		} while (fr != r);
	}

	int twos(int n) {
		int res = 0;
		while (n % 2 == 0) {
			res++;
			n /= 2;
		}
		return res;
	}

	int fives(int n) {
		int res = 0;
		while (n % 5 == 0) {
			res++;
			n /= 5;
		}
		return res;
	}
}
