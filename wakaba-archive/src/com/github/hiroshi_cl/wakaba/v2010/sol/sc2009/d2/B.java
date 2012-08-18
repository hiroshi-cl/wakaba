package com.github.hiroshi_cl.wakaba.v2010.sol.sc2009.d2;
import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
class B {
	void debug(Object... os) {
		System.err.println(deepToString(os));
	}
	void run() {
		Scanner sc = new Scanner(System.in);
		for (int o = 1;; o++) {
			try {
				sc = new Scanner(new File(o + ".in"));
				System.setOut(new PrintStream(o + ".out"));
			} catch (Exception e) {
				return;
			}
			int n = sc.nextInt();
			int[] hps = new int[n];
			for (int i = 0; i < n; i++) {
				hps[i] = sc.nextInt();
			}
			sort(hps);
			int m = sc.nextInt();
			ArrayList<MagicA> listA = new ArrayList<MagicA>();
			ArrayList<MagicS> listS = new ArrayList<MagicS>();
			for (int i = 0; i < m; i++) {
				sc.next();
				int mp = sc.nextInt();
				if (sc.next().charAt(0) == 'A') {
					listA.add(new MagicA(mp, sc.nextInt()));
				} else {
					listS.add(new MagicS(mp, sc.nextInt()));
				}
			}
			MagicA[] as = listA.toArray(new MagicA[0]);
			MagicS[] ss = listS.toArray(new MagicS[0]);
			int max = 100000;
			long INF = 1l << 50;
			long[] dpA = new long[max + 1];
			fill(dpA, INF);
			dpA[0] = 0;
			for (int i = 0; i <= max; i++) {
				for (int j = 0; j < as.length; j++) {
					int bi = i - as[j].dm;
					if (bi >= 0) {
						dpA[i] = min(dpA[i], dpA[bi] + as[j].mp);
					} else {
						dpA[i] = min(dpA[i], as[j].mp);
					}
				}
			}
			long[] dpS = new long[max + 1];
			fill(dpS, INF);
			dpS[0] = 0;
			for (int i = 0; i <= max; i++) {
				for (int j = 0; j < ss.length; j++) {
					int bi = i - ss[j].dm;
					if (bi >= 0) {
						dpS[i] = min(dpS[i], dpS[bi] + ss[j].mp);
					} else {
						dpS[i] = min(dpS[i], ss[j].mp);
					}
				}
			}
			long res = INF;
			for (int i = 0; i <= max; i++) {
				long nres = dpA[i];
				for (int j = 0; j < n; j++) {
					int rest = hps[j] - i;
					if (rest > 0) {
						nres += dpS[rest];
					}
				}
				res = min(res, nres);
			}

			System.out.println(res);
			Scanner sc1, sc2;
			try {
				sc1 = new Scanner(new File(o + ".out"));
				sc2 = new Scanner(new File(o + ".diff"));
				boolean ok = true;
				while (sc1.hasNext()) {
					if (!sc1.next().equals(sc2.next())) {
						ok = false;
						break;
					}
				}
				if (sc2.hasNext()) ok = false;
				System.err.println(o+" "+ok);
			} catch (Exception e) {}
		}
	}
	class MagicA {
		int mp;
		int dm;
		public MagicA(int mp, int dm) {
			super();
			this.mp = mp;
			this.dm = dm;
		}
	}
	class MagicS {
		int mp;
		int dm;
		public MagicS(int mp, int dm) {
			super();
			this.mp = mp;
			this.dm = dm;
		}
	}
	public static void main(String[] args) {
		new B().run();
	}
}
