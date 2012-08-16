package com.github.hiroshi_cl.wakaba.v2009.sol.dp2009;
import java.util.*;
public class De {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		for(int N = sc.nextInt(); N > 0; N = sc.nextInt()) {
			Tree root = new Tree(0, 1000 * 1000 * 1000 + 1);
			while(N-- > 0)
				switch(sc.next().charAt(0)) {
				case 'R' :
					System.out.println(root.R(sc.nextInt()));
					break;
				case 'W' :
					root.W(sc.nextInt(), sc.nextInt());
					break;
				case 'D' :
					root.D(sc.nextInt());
					break;
				}
			System.out.println();
		}
	}

	static class Tree {
		final int lp, rp;
		int i = -1;
		Tree left = null, right = null;
		public Tree(int l, int r) {
			lp = l;
			rp = r;
		}
		public Tree(int l, int r, int I) {
			lp = l;
			rp = r;
			i = I;
		}
		int R(int P) {
			if(left != null)
				return (left.rp > P ? left.R(P) : right.R(P));
			else
				return i;
		}
		int W(int I, int S) {
			if(left != null) {
				S = left.W(I, S);
				if(S > 0)
					S = right.W(I, S);
				return S;
			}
			else if(i == -1)
				if(S >= rp - lp) {
					i = I;
					return S - rp + lp;
				}
				else {
					left = new Tree(lp, lp + S, I);
					right = new Tree(lp + S, rp);
					return 0;
				}
			else
				return S;
		}
		void D(int I) {
			if(left != null) {
				left.D(I);
				right.D(I);
			}
			else if(i == I)
				i = -1;
		}
	}
}
