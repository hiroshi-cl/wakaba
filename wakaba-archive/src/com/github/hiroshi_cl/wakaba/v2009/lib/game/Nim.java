package com.github.hiroshi_cl.wakaba.v2009.lib.game;

public class Nim {
	long[] nim;

	boolean win() {
		return xor() != 0l;
	}

	boolean go() {
		long xor = xor();
		if (xor == 0)
			return false;
		for (int i = 0;; i++) {
			if (nim[i] > (nim[i] ^ xor)) {
				System.out.printf("get %d from %d.%n", nim[i] - (nim[i] ^ xor),
						i);
				nim[i] ^= xor;
				return true;
			}
		}
	}

	long xor() {
		long res = 0l;
		for (long l : nim)
			res ^= l;
		return res;
	}
}
