package com.github.hiroshi_cl.wakaba.v2009.sol.pku1982;
import java.util.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int D = sc.nextInt();
		while(D-- > 0) {
			int N = sc.nextInt();
			int[] B = new int[N+2];
			int[] H = new int[N+2];
			for(int i = 1; i <= N; i++) {
				B[i] = sc.nextInt();
				H[i] = sc.nextInt();
			}
			int M = sc.nextInt();
			int[] F = new int[M];
			int[] A = new int[M];
			for(int i = 0; i < M; i++) {
				F[i] = sc.nextInt();
				A[i] = sc.nextInt();
			}
			int L = sc.nextInt();
			int[] P = new int[L];
			int[] T = new int[L];
			for(int i = 0; i < L; i++) {
				P[i] = sc.nextInt();
				T[i] = sc.nextInt();
			}

			B[0] = 0;
			B[N+1] = 100;
			H[0] = H[N+1] = 50;
			
			WaterTank root = new WaterTank(B, H);
			for(int i = 0; i < L; i++) {
				root.reset();
				for(int j = 0; j < M; j++)
					root.pour(F[j], A[j] * T[i]);
				System.out.printf("%.3f%n", root.observe(P[i]));
			}
		}
	}
	static class WaterTank {
		static int cid = 0;
		final int id;
		final int size, minh, pivot;
		final boolean isLeaf;
		final WaterTank[] t = new WaterTank[2];
		int water = 0;
		boolean isFlat = false;
		WaterTank(int[] b, int[] h) {
			id = cid++;
			int n = b.length;
			size = (b[n-1] - b[0]) * 30;
			minh = min(h[0], h[n-1]);
			if(n == 2) {
				isLeaf = true;
				pivot = -1;
				t[0] = t[1] = null;
			} else { 
				isLeaf = false;

				int mi = 0;
				int mh = 0;
				for(int i = 1; i < n - 1; i++)
					if(mh < h[i])
						mh = h[mi = i];

				pivot = b[mi];
				t[0] = new WaterTank(copyOfRange(b, 0, mi+1), copyOfRange(h, 0, mi+1));
				t[1] = new WaterTank(copyOfRange(b, mi, n), copyOfRange(h, mi, n));
			}
		}
		int pour(int f, int w) {
//			debug("pour", f, w, this);
			if(!isLeaf && !isFlat) {
				int m = f < pivot ? 0 : 1;
				int wf = t[m].pour(f, w);
				if(wf > 0)
					wf = t[m^1].pour(pivot+1-2*m, wf);
				if(wf > 0)
					isFlat = true;
			}
			w += water;
			water = min(w, size * minh);
			return w - water;
		}
		double observe(int p) {
//			debug("observe", p, this);
			if(isLeaf || isFlat)
				return ((double) water) / size;
			else
				return t[p < pivot ? 0 : 1].observe(p);
		}
		void reset() {
			if(!isLeaf)
				for(int i = 0; i < 2; i++)
					t[i].reset();
			water = 0;
			isFlat = false;
		}
		@Override
		public String toString() {
			return String.format("[%d : %d, %d, %d; %.3f]", id, size, minh, pivot, (double) water / size);
		}
	}
	static int[] copyOfRange(int[] a, int from, int to) { // for JDK 1.5 [Arrays.copyOfRange(int[],int,int)]
		int[] res = new int[min(to, a.length) - from];
		for(int i = 0; i < res.length; i++)
			res[i] = a[from + i];
		return res;
	}
	static void debug(Object...os) {
		System.err.println(deepToString(os));
	}
}