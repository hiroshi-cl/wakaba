package com.github.hiroshi_cl.wakaba.v2009.sol.utpc2009.a;

import java.util.*;
import static java.lang.Math.*;

class A {
	void run() {
		Scanner sc = new Scanner(System.in);
		int X = sc.nextInt();
		int Y = sc.nextInt();
		int W = sc.nextInt();
		int H = sc.nextInt();
		int N = sc.nextInt();
		int ans = 0;
		for (int i = 0; i < N; i++) {
			int x = sc.nextInt();
			int y = sc.nextInt();
			if (x >= X && y >= Y && x <= X + W && y <= Y + H)
				ans++;
		}
		System.out.println(ans);
	}
}

public class Main {
	public static void main(String[] args) {
		new A().run();
	}
}