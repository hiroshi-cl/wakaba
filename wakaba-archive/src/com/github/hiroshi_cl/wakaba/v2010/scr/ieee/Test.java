package com.github.hiroshi_cl.wakaba.v2010.scr.ieee;

public class Test {
	public static void main(String[] args) {
		ieee(5, 3);
		ieee(4.5, 3);
		ieee(4, 3);
		ieee(-5, 3);
		ieee(-4.5, 3);
		ieee(-4, 3);
		ieee(-5, -3);
		ieee(10, 2 * Math.PI);
	}

	static void ieee(double n, double d) {
		System.out.printf("%.3f %% %.3f = %.3f%n", n, d,
				Math.IEEEremainder(n, d));
	}
}

/*
 5.000 % 3.000 = -1.000
 4.500 % 3.000 = -1.500
 4.000 % 3.000 = 1.000
 -5.000 % 3.000 = 1.000
 -4.500 % 3.000 = 1.500
 -4.000 % 3.000 = -1.000
 -5.000 % -3.000 = 1.000
 10.000 % 6.283 = -2.566
*/