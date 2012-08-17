package com.github.hiroshi_cl.wakaba.v2010.lib.io;

public class ScannerA {
	int nextInt() {
		try {
			int c = System.in.read();
			if (c == -1)
				return c;
			while (c != '-' && (c < '0' || '9' < c)) {
				c = System.in.read();
				if (c == -1)
					return c;
			}
			if (c == '-')
				return -nextInt();
			int res = 0;
			do {
				res *= 10;
				res += c - '0';
				c = System.in.read();
			} while ('0' <= c && c <= '9');
			return res;
		} catch (Exception e) {
			return -1;
		}
	}

	long nextLong() {
		try {
			int c = System.in.read();
			if (c == -1)
				return -1;
			while (c != '-' && (c < '0' || '9' < c)) {
				c = System.in.read();
				if (c == -1)
					return -1;
			}
			if (c == '-')
				return -nextLong();
			long res = 0;
			do {
				res *= 10;
				res += c - '0';
				c = System.in.read();
			} while ('0' <= c && c <= '9');
			return res;
		} catch (Exception e) {
			return -1;
		}
	}

	double nextDouble() {
		return Double.parseDouble(next());
	}

	String next() {
		try {
			StringBuilder res = new StringBuilder("");
			int c = System.in.read();
			while (Character.isWhitespace(c))
				c = System.in.read();
			do {
				res.append((char) c);
			} while (!Character.isWhitespace(c = System.in.read()));
			return res.toString();
		} catch (Exception e) {
			return null;
		}
	}

	String nextLine() {
		try {
			StringBuilder res = new StringBuilder("");
			int c = System.in.read();
			while (c == '\r' || c == '\n')
				c = System.in.read();
			do {
				res.append((char) c);
				c = System.in.read();
			} while (c != '\r' && c != '\n');
			return res.toString();
		} catch (Exception e) {
			return null;
		}
	}
}