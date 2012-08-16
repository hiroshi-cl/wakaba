package com.github.hiroshi_cl.wakaba.v2009.sol.wf2009;
import java.util.*;
import java.io.*;

/*
 -区間がn個与えられるので、各区間から1点ずつ持ってきたときの最小gapを最大化する問題。
 -2分探索でgapの範囲を狭めていく。
 -可能かどうかは、nが小さいのでとりあえず全探索。
 */
public class A {
	public static void main(String[] args) {
		new A().run();
	}

	final int M2S = 60;
	final int MAX = 1440 * M2S + 1;
	
	public void run() {
		Scanner sc = fromFile("approach.in");
		for(int t = 1;; t++) {
			int n = sc.nextInt();
			if(n == 0)
				break;
			
			Tuple[] intervals = new Tuple[n];
			for(int i = 0; i < n; i++)
				intervals[i] = new Tuple(sc.nextInt() * M2S, sc.nextInt() * M2S);
			int inf = 0, sup = MAX;
			while(inf + 1 < sup) {
				int mid = (inf  + sup) / 2;
				if(test(intervals, mid, 0, new boolean[n]))
					inf = mid;
				else
					sup = mid;
			}
			System.out.printf("Case %d: %d:%02d%n", t, inf / M2S, inf % M2S);
		}
	}
	
	private boolean test(Tuple[] intervals, int gap, int c, boolean[] used) {
		int n = used.length;
		boolean f = true;
		for(int i = 0; i < n; i++) {
			f &= used[i];
			if(!used[i] && intervals[i].b >= c) {
				int nc = Math.max(c, intervals[i].a) + gap;
				used[i] = true;
				boolean b = test(intervals, gap, nc, used);
				if(b)
					return true;
				used[i] = false;
			}
		}
		return f;
	}
	
	class Tuple implements Comparable<Tuple> {
		final int a, b;
		public Tuple(int a, int b) {
			this.a = a;
			this.b = b;
		}
		public int compareTo(Tuple o) {
			if(a == o.a)
				return b - o.b;
			else
				return a - o.a;
		}
	}
	
	private Scanner fromFile(String name) {
		try {
			return new Scanner(new File(name));
		} catch(Exception e) {
			System.out.println("using standard io"); // local test
			return new Scanner(System.in);
		}
	}
	private void debug(Object...a) {
		System.out.println(Arrays.deepToString(a));
	}
}
