package com.github.hiroshi_cl.wakaba.v2010.sol.utpc2009;

import java.util.*;
import static java.lang.Math.*;

public class D {
	void run() {
		Scanner sc = new Scanner(System.in);
		String[] num = sc.next().split("\\.");
		String pre = sc.next();
		String main;
		int p = 0;
		try {
			p += Prefix.valueOf(pre).p;
			main = sc.next();
		} catch (Exception e) {
			main = pre;
		}
		String out = "";
		if (num[0].equals("0")) {
			int c = 0;
			for (; c < num[1].length() && num[1].charAt(c) == '0'; c++)
				;
			p += -c - 1;
			out = insertdot(num[1].substring(c));
		} else {
			p += num[0].length() - 1;
			if (num.length > 1)
				out = insertdot(num[0] + num[1]);
			else
				out = insertdot(num[0]);
		}
		System.out.println(out + " * 10^" + p + " " + main);
	}

	private String insertdot(String s) {
		if (s.length() > 1)
			return s.charAt(0) + "." + s.substring(1);
		else
			return s;
	}

	enum Prefix {
		yotta(24), zetta(21), exa(18), peta(15), tera(12), giga(9), mega(6), kilo(
				3), hecto(2), deca(1), deci(-1), centi(-2), milli(-3), micro(-6), nano(
				-9), pico(-12), femto(-15), ato(-18), zepto(-21), yocto(-24);
		final int p;

		private Prefix(int p) {
			this.p = p;
		}
	}

	public static void main(String[] args) {
		new D().run();
	}
}