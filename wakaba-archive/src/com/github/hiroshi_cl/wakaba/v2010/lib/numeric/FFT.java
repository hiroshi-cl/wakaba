package com.github.hiroshi_cl.wakaba.v2010.lib.numeric;

import static java.lang.Math.max;
import static java.lang.Math.round;
import static java.util.Arrays.deepToString;

import java.util.*;

import com.github.hiroshi_cl.wakaba.v2009.lib.data.*;

//using Complex Class
class FFT {
	// 長さを2の冪乗に合わせる
	// cf:循環的に長さを増やすか、0で埋めるか
	// df:2倍より長くするか、1倍より長くするか
	static Complex[] adjust(Complex[] cs, boolean cf, boolean df) {
		int n = cs.length;
		int l = 1;
		while (l < n)
			l <<= 1;
		if (df)
			l <<= 1;
		Complex[] ret = new Complex[l];
		for (int i = 0; i < n; i++)
			ret[i] = cs[i];
		for (int i = n; i < l; i++)
			ret[i] = (cf ? cs[i % n] : new Complex(0., 0.));
		return ret;
	}

	static Complex[] reverse(Complex[] cs) {
		Complex[] ret = new Complex[cs.length];
		for (int i = 0; i < cs.length; i++)
			ret[i] = cs[cs.length - i - 1];
		return ret;
	}

	// adjust しておくべし
	// 時間間引き
	// http://www.kurims.kyoto-u.ac.jp/~ooura/fftman/ftmn1_22.html#sec1_2_2 参考
	static Complex[] fft(Complex[] cs, boolean inv) {
		int n = cs.length;
		Complex[] ret = new Complex[n];
		// 並び替え
		for (int i = 0, j = 0; i < n; i++, j = nxtrev(j, n))
			ret[i] = cs[j];
		// 本体
		double theta = 2 * Math.PI * (inv ? 1. : -1.);
		for (int k = 1, m = 2; m <= n; k = m, m <<= 1) {
			theta *= 0.5;
			for (int i = 0; i < k; i++) {
				Complex w = Complex.polar(theta * i, 1.);
				for (int j = i; j < n; j += m) {
					Complex z = w.mul(ret[j + k]);
					ret[j + k] = ret[j].sub(z);
					ret[j] = ret[j].add(z);
				}
			}
		}
		// // 補正
		if (inv)
			for (int i = 0; i < n; i++)
				ret[i] = new Complex(ret[i].re / n, ret[i].im / n);
		return ret;
	}

	// adjust しておくべし
	// 畳み込み
	static Complex[] convol(Complex[] A, Complex[] B) {
		int n = A.length;
		Complex[] cs = new Complex[n];
		A = fft(A, false);
		B = fft(B, false);
		for (int i = 0; i < n; i++)
			cs[i] = A[i].mul(B[i]);
		return fft(cs, true);
	}

	static Complex[] convol2(Complex[] A, Complex[] B) {
		int n = A.length;
		Complex[] cs = new Complex[n];
		for (int i = 0; i < n; i++) {
			cs[i] = new Complex(0., 0.);
			for (int j = 0; j < n; j++)
				cs[i] = cs[i].add(A[j].mul(B[(i - j + n) % n]));
		}
		return cs;
	}

	static int nxtrev(int i, int n) {
		// ++ bit reverse version
		int k = n >> 1;
		for (; (i & k) > 0; k >>= 1)
			i ^= k;
		i |= k;
		// for(int k = n >> 1; k > (i ^= k); k >>= 1);
		return i;
	}

	// 今は使っていない
	static int bitrev(int i, int n) {
		int j = 0;
		// bit reverse
		for (int p = n >> 1, q = i; p > 0; p >>= 1, q >>= 1)
			j = (j << 1) | (q & 1);
		return j;
	}

	static void swap(Complex[] cs, int i, int j) {
		Complex t = cs[i];
		cs[i] = cs[j];
		cs[j] = t;
	}

	public static void main(String[] args) {
		int n = 16;
		Complex[] cs = new Complex[n];
		for (int i = 0; i < n; i++)
			cs[i] = new Complex(Math.random(), 0.);
		Complex[] fs = fft(fft(cs, false), true);
		for (int i = 0; i < n; i++)
			cs[i] = cs[i].sub(fs[i]);
		debug(cs);
		Complex[] as = new Complex[n];
		for (int i = 0; i < n; i++)
			as[i] = new Complex(Math.random(), 0.);
		Complex[] bs = new Complex[n];
		for (int i = 0; i < n; i++)
			bs[i] = new Complex(Math.random(), 0.);
		debug(convol(as, bs));
		debug(convol2(as, bs));
	}

	static void debug(Object... os) {
		System.out.println(Arrays.deepToString(os));
	}

	class CircularShifts {
		public int maxScore(int N, int Z0, int A, int B, int M) {
			long[] Z = new long[2 * N];
			Z[0] = Z0 % M;
			for (int i = 1; i < 2 * N; i++)
				Z[i] = (Z[i - 1] * A + B) % M;
			Complex[] X = new Complex[N];
			Complex[] Y = new Complex[N];
			for (int i = 0; i < N; i++) {
				X[i] = new Complex(Z[i] % 100, 0.);
				Y[i] = new Complex(Z[i + N] % 100, 0.);
			}
			X = FFT.adjust(X, true, true);
			Y = FFT.adjust(Y, false, true);
			Y = FFT.reverse(Y);
			Complex[] cs = FFT.convol(X, Y);
			debug(cs);
			debug(FFT.convol2(X, Y));
			int ret = 0;
			for (int i = 0; i < N; i++)
				ret = max(ret, (int) round(cs[i].re));
			return ret;
		}
	}
}
