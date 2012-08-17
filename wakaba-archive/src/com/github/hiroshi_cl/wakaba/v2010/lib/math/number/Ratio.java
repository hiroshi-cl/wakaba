package com.github.hiroshi_cl.wakaba.v2010.lib.math.number;

import static java.lang.Math.*;

/*
 * num/denを、分数のままで管理する。
 * den>0が保たれる。0=0/1.
 * オーバーフローに注意。
 *
 * verified: pku1641.
 */
class Ratio implements Comparable<Ratio> {// 0 = 0/1. den>0.
	long num, den;

	Ratio(long num, long den) {
		this.num = num;
		this.den = den;
		normalize();
	}

	Ratio add(Ratio r) {
		return new Ratio(num * r.den + den * r.num, den * r.den);
	}

	Ratio sub(Ratio r) {
		return new Ratio(num * r.den - den * r.num, den * r.den);
	}

	Ratio mul(Ratio r) {
		return new Ratio(num * r.num, den * r.den);
	}

	Ratio div(Ratio r) {
		return new Ratio(num * r.den, den * r.num);
	}

	Ratio abs() {
		return new Ratio(Math.abs(num), den);
	}

	public int compareTo(Ratio o) {
		return (int) signum(num * o.den - den * o.num);
	}

	void normalize() {
		if (num == 0)
			den = 1;
		else {
			if (den < 0) {
				num *= -1;
				den *= -1;
			}
			long d = gcd(Math.abs(num), den);
			num /= d;
			den /= d;
		}
	}

	long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	void show() {
		if (den == 1)
			System.out.print(num);
		else
			System.out.print(num + "/" + den);
	}
}