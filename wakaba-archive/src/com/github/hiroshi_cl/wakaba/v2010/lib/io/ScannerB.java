package com.github.hiroshi_cl.wakaba.v2010.lib.io;

// ソース/委譲メソッドの生成を使うと大半を自動生成できる
import java.io.*;
public class ScannerB {
	final java.util.Scanner sc;

	public double nextDouble() {
		return Double.parseDouble(sc.next());
	}

	public ScannerB(InputStream is) {
		this.sc = new java.util.Scanner(is);
	}

	public boolean hasNext() {
		return sc.hasNext();
	}

	public String next() {
		return sc.next();
	}

	public int nextInt() {
		return Integer.parseInt(sc.next());
	}

	public String nextLine() {
		return sc.nextLine();
	}

	public long nextLong() {
		return Long.parseLong(sc.next());
	}
}