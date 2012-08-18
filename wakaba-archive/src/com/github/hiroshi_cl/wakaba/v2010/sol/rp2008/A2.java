package com.github.hiroshi_cl.wakaba.v2010.sol.rp2008;

import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class A2 {
	void debug(Object... os) {
		System.err.println(deepToString(os));
	}

	static final int[] primes;
	static final int MAX = 1000000;
	static {
		boolean[] isComposite = new boolean[MAX + 1];
		for (int i = 2; i * i <= MAX; i++)
			if (!isComposite[i])
				for (int j = i; i * j <= MAX; j++)
					isComposite[i * j] = true;
		isComposite[0] = isComposite[1] = true;
		int n = 0;
		for (boolean b : isComposite)
			if (!b)
				n++;
		primes = new int[n];
		int c = 0;
		for (int i = 0; i < MAX; i++)
			if (!isComposite[i])
				primes[c++] = i;
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		int a = sc.nextInt();
		int b = sc.nextInt();
		System.out.println(score(a) > score(b) ? 'a' : 'b');
	}

	int score(int n) {
		int sum = 0;
		int p = 0;
		for (int i : primes)
			if (i > n)
				break;
			else if (n % i == 0) {
				sum += p;
				p = i;
			}
		return p - sum;
	}

	public static void main(String[] args) {
		new A2().run();
	}
}
