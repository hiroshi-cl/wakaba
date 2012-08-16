package com.github.hiroshi_cl.wakaba.v2009.sol.pku3040.naive;
import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int C = sc.nextInt();
		Pair[] ps = new Pair[N];
		for(int i = 0; i < N; i++)
			ps[i] = new Pair(sc.nextInt(), sc.nextInt());
		Arrays.sort(ps);
		int ans = 0;
		for(;; ans++) {
			int sum = 0;
			for(int i = 0; i < N; i++)
				sum += ps[i].B;
			if(sum == 0)
				break;
			int p = C;
			for(int i = 0; i < N; i++) {
				int t = Math.min(p / ps[i].V, ps[i].B);
				p -= t * ps[i].V;
				ps[i].B -= t;
			}
			for(int i = N - 1; p > 0 && i >= 0; i--)
				if(ps[i].B > 0 && p < ps[i].V) {
					p -= ps[i].V;
					ps[i].B--;
				}
			if(p > 0)
				break;
		}
		System.out.println(ans);
	}
	static void debug(Object...os) {
		System.err.println(Arrays.deepToString(os));
	}
	static class Pair implements Comparable<Pair>{
		int V, B;
		Pair(int v, int b) {
			V = v;
			B = b;
		}
		public int compareTo(Pair p) {
			return p.V - V;
		}
		@Override
		public String toString() {
			return String.format("[V=%d,B=%d]", V, B);
		}
	}
}

