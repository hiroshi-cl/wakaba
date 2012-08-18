package com.github.hiroshi_cl.wakaba.v2010.sol.d2006;

import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class A {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		new A().run(sc);
	}

	void run(Scanner sc) {
		for (;;) {
			int a = sc.nextInt(), d = sc.nextInt(), n = sc.nextInt();
			if ((a | d | n) == 0)
				return;
			int count = 0;
			int i = 0;
			for (; count < n; i++) {
				if (isPrime(a + i * d))
					count++;
			}
			System.out.println(a + (i - 1) * d);
		}
	}

	boolean isPrime(int p) {
		if (p == 1)
			return false;
		for (int i = 2; i * i <= p; i++)
			if (p % i == 0)
				return false;
		return true;
	}

}