package com.github.hiroshi_cl.wakaba.v2010.sol.d2006;

import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class B {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		new B().run(sc);
	}

	void run(Scanner sc) {
		for (int i = sc.nextInt(); i > 0; i--) {
			String s = sc.next();
			TreeSet<String> t = new TreeSet<String>();
			for (int j = 1; j < s.length(); j++) {
				String a = s.substring(0, j);
				String b = s.substring(j);
				for (int k = 0; k < 2; k++) {
					t.add(a + b);
					t.add(a + rev(b));
					t.add(rev(a) + b);
					t.add(rev(a) + rev(b));
					String l = a;
					a = b;
					b = l;
				}
			}
			System.out.println(t.size());
		}
	}

	static String rev(String s) {
		return new StringBuilder(s).reverse().toString();
	}

}