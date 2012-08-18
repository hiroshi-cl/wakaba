package com.github.hiroshi_cl.wakaba.v2010.sol.srm;

import java.util.*;
import java.util.regex.*;
import java.text.*;
import java.math.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.math.BigInteger.*;

public class DivisibleByDigits {

	public long getContinuation(int n) {
		boolean[] dig = new boolean[10];
		for (int i = n; i > 0; i /= 10)
			dig[i % 10] = true;

		int div = 1;
		for (int i = 1; i < 10; i++)
			if (dig[i])
				div = lcm(div, i);

		long ret = n;
		int d = 1;
		for (; (ret + d - 1) % div >= d; d *= 10, ret *= 10)
			;
		ret += (d - 1 - (ret + d - 1) % div) % div;

		return ret;
	}

	int gcd(int x, int y) {
		if (y == 0)
			return x;
		else
			return gcd(y, x % y);
	}

	int lcm(int x, int y) {
		return x * y / gcd(x, y);
	}
}