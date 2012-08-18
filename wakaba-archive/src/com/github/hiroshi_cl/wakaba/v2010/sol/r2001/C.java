package com.github.hiroshi_cl.wakaba.v2010.sol.r2001;
import java.util.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
public class C {
	public static void main(String[] args) {
		new C().run();
	}
	void run() {
		Scanner sc = new Scanner(System.in);
		for (;;) {
			int N = sc.nextInt();
			if (N == 0) break;
			double[][] a = new double[4][N + 2];
			double[][] X = new double[N + 2][N + 2], Y = new double[N + 2][N + 2];
			double maxS = 0.0;
			for (int i = 0; i < 4; i++) {
				a[i][0] = 0.0;
				a[i][N + 1] = 1.0;
				for (int j = 1; j <= N; j++) {
					a[i][j] = sc.nextDouble();
				}
			}
			for (int i = 0; i <= N + 1; i++) {
				for (int j = 0; j <= N + 1; j++) {
					X[i][j] = CalX(a[0][i], a[1][i], a[2][j], a[3][j]);
					Y[i][j] = CalY(a[0][i], a[1][i], a[2][j], a[3][j]);
				}
			}
			for (int i = 0; i <= N; i++) {
				for (int j = 0; j <= N; j++) {
					maxS = Math.max(maxS, CalS(i, j, X, Y));
				}
			}
			System.out.printf("%.6f%n", maxS);
		}
	}
	double CalX(double a, double b, double c, double d) {
		return ((-a - (b - a) * c) / ((b - a) * (d - c) - 1.0));
	}
	double CalY(double a, double b, double c, double d) {
		return ((-c - (d - c) * a) / ((d - c) * (b - a) - 1.0));
	}
	double CalS(int x, int y, double[][] X, double[][] Y) {
		return (((X[x][y] - X[x + 1][y + 1]) * (Y[x][y + 1] - Y[x + 1][y]) + (X[x][y + 1] - X[x + 1][y])
				* (Y[x + 1][y + 1] - Y[x][y])) / -2);
	}
}