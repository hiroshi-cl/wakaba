package com.github.hiroshi_cl.wakaba.v2010.sol.utpc2009;

import java.util.HashMap;
import java.util.Scanner;

public class F {
	public static void main(String[] args) {
		new F().run();
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < 12; i++)
			map.put(tones[i], i);
		n = sc.nextInt();
		m = sc.nextInt();
		ts = new int[n];
		ss = new int[m];
		for (int i = 0; i < n; i++)
			ts[i] = map.get(sc.next());
		for (int i = 0; i < m; i++)
			ss[i] = map.get(sc.next());
		System.out.println((ok(n - 1, m - 1) || ok(n - 2, m - 1)) ? "Yes"
				: "No");
	}

	int n, m;
	int[] ts;
	int[] ss;

	boolean ok(int i, int j) {
		if (i == -1 && j == -1)
			return true;
		if (i < 0 || i >= n || j < 0)
			return false;
		if (ts[i] == ss[j])
			return ok(i - 1, j - 1);
		if (ts[i] == (ss[j] + 1) % 12)
			return ok(i + 1, j - 1);
		if ((ts[i] + 1) % 12 == ss[j])
			return ok(i - 2, j - 1);
		return false;
	}

	HashMap<String, Integer> map = new HashMap<String, Integer>();
	String[] tones = new String[] { "C", "C#", "D", "D#", "E", "F", "F#", "G",
			"G#", "A", "A#", "B" };
}