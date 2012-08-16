package com.github.hiroshi_cl.wakaba.v2009.sol.utpc2009.g;

import java.util.*;

import static java.lang.Math.*;

class G {
	void run() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int X = sc.nextInt();
		int Y = sc.nextInt();
		int nx = sc.nextInt();
		int ny = sc.nextInt();
		int[] sx = new int[N];
		int[] sy = new int[N];
		for (int i = 0; i < N; i++) {
			sx[i] = sc.nextInt();
			sy[i] = sc.nextInt();
		}
		boolean[][] map = new boolean[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				map[i][j] = hypot(sx[i] - sx[j], sy[i] - sy[j]) <= 50;

		boolean welcome = false;
		int[] stopped = new int[N];
		Set<Integer> gree = new HashSet<Integer>();
		for (int i = 0; i < N; i++)
			if (hypot(sx[i] - nx, sy[i] - ny) <= 10) {
				gree.add(i);
				stopped[i] = X;
			}
		int ans = 0;
		while (!welcome && !gree.isEmpty()) {
			ans++;
			Set<Integer> ng = new HashSet<Integer>();
			for (int i : gree)
				for (int j = 0; j < N; j++)
					if (map[i][j])
						if (stopped[j] == 0) {
							ng.add(j);
							stopped[j] = X * (ans + 1);
						} else if (X * ans - stopped[j] > Y)
							welcome = true;
			// System.err.println(gree);
			gree = ng;
		}
		if (!welcome)
			System.out.println(ans * X);
		else
			System.out.println("You're always welcome!");
	}
}

public class Main {
	public static void main(String[] args) {
		new G().run();
	}
}