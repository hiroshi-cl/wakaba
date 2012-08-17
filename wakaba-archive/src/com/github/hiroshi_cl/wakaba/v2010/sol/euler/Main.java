package com.github.hiroshi_cl.wakaba.v2010.sol.euler;

import java.math.*;
import java.util.*;

import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;

class Main {
	void debug(Object... os) {
		System.err.println(deepToString(os));
	}
	public static void main(String[] args) {
		long st = System.currentTimeMillis();
		new Main().run083();
		new Main().debug(System.currentTimeMillis() - st);
	}

	void run078(){
		int n=100000;
		int mod=1000000;
		int[] memo=new int[n];
		fill(memo,-1);
		for (int i = 0; i < n; i++) {
			if(partitionNumber(i, memo, mod)==0){
				System.out.println(i);
				break;
			}
		}
	}
	/*
	 * p(n)をメモ化再帰で計算する。memoにははじめ、すべて-1を割り当てておくこと。
	 */
	int partitionNumber(int n,int[] memo,int mod){
		if(n==0)return memo[n]=1;
		if(memo[n]>=0)return memo[n];
		memo[n]=0;
		for(int k=1;n-k*(3*k-1)/2>=0;k++)
			memo[n]=(memo[n]+(k%2==0?-1:1)*partitionNumber(n-k*(3*k-1)/2, memo, mod))%mod;
		for(int k=1;n-k*(3*k+1)/2>=0;k++)
			memo[n]=(memo[n]+(k%2==0?-1:1)*partitionNumber(n-k*(3*k+1)/2, memo, mod))%mod;
		return memo[n]=(memo[n]+mod)%mod;
	}
	void run083() {
		Scanner sc = new Scanner(System.in);
		int n = 80;
		int[] is = new int[n * n];
		for (int i = 0; i < n; i++) {
			String[] ss = sc.next().split(",");
			for (int j = 0; j < n; j++) {
				is[i * n + j] = Integer.valueOf(j);
			}
		}
		int[][] f = new int[n * n][n * n];
		for (int i = 0; i < n * n; i++) {
			fill(f[i], 1 << 28);
		}
		for (int i = 0; i < n * n; i++) {
			int x = i / n, y = i % n;
			for (int d = 0; d < 4; d++) {
				int nx = x + dx[d], ny = y + dy[d];
				if (0 <= nx && nx < n && 0 <= ny && ny < n) {
					f[i][nx * n + ny] = is[nx * n + ny];
				}
			}
		}
		for (int k = 0; k < n * n; k++) {
			for (int i = 0; i < n * n; i++) {
				for (int j = 0; j < n * n; j++) {
					f[i][j] = min(f[i][k] + f[k][j], f[i][j]);
				}
			}
		}
		System.out.println(f[0][79 * n + 79]);
	}
	void run077() {
		int n = 1000;
		int[] dp=new int[n];
		dp[0]=1;
		for(int i=1;i<n;i++)if(isPrime(i)){
			for(int j=0;j<n;j++){
				if(j>=i){
					dp[j] += dp[j-i];
				}
			}
		}
		for(int i=0;;i++){
			if(dp[i]>5000){
				System.out.println(i);
				break;
			}
		}

	}
	void run104() {
		BigInteger f0 = ONE, f1 = ONE;
		BigInteger MOD = valueOf(1000000000l);
		for (int i = 2;; i++) {
			BigInteger tmp = f1;
			f1 = f1.add(f0);
			f0 = tmp;
			int count = 0;
			if (isPermutation(f1.mod(MOD).intValue(), 123456789)) {
				System.out.println(i + 1);
				// break;
				count++;
			}
			if (f1.compareTo(MOD) > 0
			&& isPermutation(Integer.valueOf(f1.toString().substring(0, 9)), 123456789)) {
				System.out.println(i + 1);
				// break;
				count++;
			}
			if (count == 2) break;
		}
	}
	void run066() {
		int n = 1000;
		int res = 0;
		int val = 0;
		for (int D = 1; D <= n; D++)
			if (!isSquareNumber(D)) {
				for (int y = 1;; y++) {
					if (isSquareNumber((long) D * y * y + 1)) {
						int x = (int) round(sqrt((long) D * y * y + 1));
						if (x > val) {
							val = x;
							res = D;
						}
						debug(D, x, y);
						break;
					}
				}
			}
		System.out.println(res);
	}
	void run096() {
		Scanner sc = new Scanner(System.in);
		int res = 0;
		while (sc.hasNext()) {
			Sudoku sudoku = new Sudoku(3);
			int n = 9;
			sc.next();
			sc.next();
			for (int i = 0; i < n; i++) {
				char[] cs = sc.next().toCharArray();
				for (int j = 0; j < n; j++) {
					sudoku.set(i, j, cs[j] - '0');
				}
			}
			sudoku.solve();
			res += sudoku.answer[0][0] * 100 + sudoku.answer[0][1] * 10 + sudoku.answer[0][2];
		}
		System.out.println(res);
	}

	class Sudoku {
		int n;
		int[][] answer;
		Sudoku(int n) {// n*n * n*n の盤面
			this.n = n;
			answer = new int[n * n][n * n];
		}
		void set(int x, int y, int num) {
			answer[x][y] = num;
		}
		boolean solve() {// 解なしの時のみfalseを返す。
			return dfs(0, 0);
		}
		private boolean dfs(int x, int y) {
			if (x == n * n) {
				x = 0;
				y++;
			}
			if (y == n * n) return true;
			if (answer[x][y] != 0) return dfs(x + 1, y);
			for (int i = 0; i < n * n; i++) {
				answer[x][y] = i + 1;
				if (isValid(x, y) && dfs(x + 1, y)) return true;
			}
			answer[x][y] = 0;
			return false;
		}
		private boolean isValid(int x, int y) {
			for (int i = 0; i < n * n; i++) {
				if (i != x && answer[i][y] == answer[x][y]) return false;
				if (i != y && answer[x][i] == answer[x][y]) return false;
			}
			int r = x / n * n, c = y / n * n;
			for (int i = r; i < r + n; i++)
				for (int j = c; j < c + n; j++)
					if (i != x && j != y && answer[i][j] == answer[x][y]) return false;
			return true;
		}
	}
	void run068() {
		int[] is = new int[10];
		for (int i = 0; i < 10; i++)
			is[i] = i + 1;
		long res = 0;
		int[] js = null;
		loop: do {
			for (int i = 0; i < 5; i++)
				if (is[0] > is[i]) continue loop;
			for (int i = 0; i < 5; i++) {
				if (is[0] + is[5] + is[6] != is[i] + is[5 + i] + is[5 + ( i + 1 ) % 5])
					continue loop;
			}
			long l = 0;
			for (int i = 0; i < 5; i++) {
				l *= pow(10, dig(is[i]));
				l += is[i];
				for (int j = 0; j < 2; j++) {
					l *= pow(10, dig(is[5 + ( i + j ) % 5]));
					l += is[5 + ( i + j ) % 5];
				}
			}
			if (dig(l) == 16) {
				// debug(is);
				if (res < l) {
					res = l;
					js = is.clone();
				}
				// res=max(res,l);
			}
		} while (nextPermutation(is));
		// debug(is);
		debug(js);
		System.out.println(res);
	}
	void run124() {
		int n = 100000;
		Entry124[] es = new Entry124[n];
		for (int i = 1; i <= n; i++)
			es[i - 1] = new Entry124(i, rad(i));
		sort(es);
		// for(Entry124 e:es)debug(e.n);
		System.out.println(es[9999].n);
	}
	int rad(int n) {
		int res = 1;
		for (int i = 2; i * i <= n; i++) {
			if (n % i == 0) {
				res *= i;
				while (n % i == 0)
					n /= i;
			}
		}
		if (n > 1) res *= n;
		return res;
	}
	class Entry124 implements Comparable<Entry124> {
		int n, rad;
		public Entry124(int n, int rad) {
			// TODO 自動生成されたコンストラクター・スタブ
			this.n = n;
			this.rad = rad;
		}
		public int compareTo(Entry124 o) {
			// TODO 自動生成されたメソッド・スタブ
			return rad != o.rad ? rad - o.rad : n - o.n;
		}
	}
	void run205() {
		int[] ps = new int[5], cs = new int[7];
		for (int i = 1; i < 5; i++)
			ps[i] = 1;
		for (int i = 1; i < 7; i++)
			cs[i] = 1;
		ps = pow(ps, 9);
		cs = pow(cs, 6);
		long num = 0;
		for (int i = 0; i < ps.length; i++)
			for (int j = 0; j < i && j < cs.length; j++)
				num += ps[i] * cs[j];
		System.out.println((double) num / ( pow(4, 9) * pow(6, 6) ));
	}
	int[] pow(int[] is, int p) {
		int[] res = { 1 };
		while (p > 0) {
			if (p % 2 == 1) res = mul(res, is);
			is = mul(is, is);
			p /= 2;
		}
		return res;
	}
	int[] mul(int[] is, int[] js) {
		int n = is.length, m = js.length;
		int[] res = new int[n + m - 1];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++) {
				res[i + j] += is[i] * js[j];
			}
		return res;
	}
	void run064() {
		int res = 0;
		for (int i = 0; i <= 10000; i++)
			if (!isSquareNumber(i))
				if (new ContinuousFraction064().CFsqrt(i).rep.size() % 2 == 1) res++;
		System.out.println(res);
	}
	class ContinuousFraction064 {
		long A;
		long[] nonrep;
		LinkedList<Long> rep = new LinkedList<Long>();
		ContinuousFraction064 CFsqrt(long m) {
			double sm = sqrt(m);
			A = (long) floor(sm);
			long a = 1, b = -A, c = 1;
			HashMap<Pair064, Integer> map = new HashMap<Pair064, Integer>();
			Pair064 p = null;
			for (int i = 0;; i++) {
				p = new Pair064(a, b, c);
				if (map.containsKey(p)) break;
				map.put(p, i);
				long nA = (long) floor(c / ( a * sm + b ));
				rep.add(nA);
				long na = c * a, nb = -b * c - nA * ( a * a * m - b * b ), nc = a * a * m - b * b;
				long d = gcd(gcd(na, nb), nc);
				a = na / d;
				b = nb / d;
				c = nc / d;
			}
			return this;
		}
	}
	class Pair064 {
		long a, b, c;
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + (int) ( a ^ ( a >>> 32 ) );
			result = prime * result + (int) ( b ^ ( b >>> 32 ) );
			result = prime * result + (int) ( c ^ ( c >>> 32 ) );
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			Pair064 other = (Pair064) obj;
			if (!getOuterType().equals(other.getOuterType())) return false;
			if (a != other.a) return false;
			if (b != other.b) return false;
			if (c != other.c) return false;
			return true;
		}

		Pair064(long a, long b, long c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}
		private Main getOuterType() {
			return Main.this;
		}
	}
	void run145() {
		int n = 1000000000;
		int res = 0;
		for (int i = 1; i < n; i++)
			if (i % 10 != 0) {
				if (i % 1000000 == 1) debug(i);
				if (isOddNumber(i + rev(i))) res++;
			}
		System.out.println(res);
	}
	boolean isOddNumber(long l) {
		while (l > 0) {
			if (l % 2 == 0) return false;
			l /= 10;
		}
		return true;
	}
	void run080() {
		int res = 0;
		for (int i = 1; i <= 100; i++)
			if (!isSquareNumber(i)) {
				BigDecimal sq = bigDecimalSqrt(new BigDecimal(i), 1000);
				String s = sq.toString().replace(".", "");
				// debug(s.length());
				for (int j = 0; j < 100; j++)
					res += s.charAt(j) - '0';
			}
		System.out.println(res);
	}
	BigDecimal bigDecimalSqrt(BigDecimal a, int scale) {
		// とりあえずdoubleのsqrtを求める
		BigDecimal x = new BigDecimal(Math.sqrt(a.doubleValue()), MathContext.DECIMAL64);
		if (scale < 17) return x;

		BigDecimal b2 = new BigDecimal(2);
		for (int tempScale = 16; tempScale < scale; tempScale *= 2) {
			// x = x - (x * x - a) / (2 * x);
			x = x.subtract(x.multiply(x).subtract(a).divide(x.multiply(b2), scale,
			BigDecimal.ROUND_FLOOR));
		}
		return x;
	}
	void run060() {
		int n = 1000;
		int[] ps = new int[n];
		for (int i = 3, j = 0; j < n; i += 2) {
			if (isPrime(i) && i != 5) ps[j++] = i;
		}
		System.out.println(calc060(ps, new long[5], 0, -1, n));
	}
	long INF = 1 << 60;
	long calc060(int[] ps, long[] is, int id, int ni, final int n) {
		// if(id==3)debug(is);
		if (id == 4) {
			debug(is);
			boolean ok = false;
			loop: for (long i = is[3] + 2; i < 200149; i += 2) {
				if (isPrime(i)) {
					for (int j = 0; j < 4; j++)
						if (!ok060(is[j], i)) continue loop;
					is[4] = i;
					ok = true;
					break;
				}
			}
			if (ok) debug(is, sum(is));
			return ok ? sum(is) : INF;
			// debug(" ",is);
		}
		// if(id==5){
		// debug("!",is);
		// return sum(is);
		// }
		long res = INF;
		loop: for (int i = ni + 1; i < n; i++) {
			for (int j = id - 1; j >= 0; j--) {
				if (!ok060(is[j], ps[i])) continue loop;
			}
			is[id] = ps[i];
			res = min(res, calc060(ps, is, id + 1, i, n));
		}
		return res;
	}
	boolean ok060(long a, long b) {
		return isPrime(a * pow(10, dig(b)) + b) && isPrime(b * pow(10, dig(a)) + a);
	}
	long sum(long[] ls) {
		long res = 0;
		for (long l : ls)
			res += l;
		return res;
	}
	void run082() {
		Scanner sc = new Scanner(System.in).useDelimiter("\\s|,");
		int n = 80;
		long[][] is = new long[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				is[j][i] = sc.nextLong();
		long[][] dp = new long[n][n];
		dp[0] = is[0];
		for (int i = 1; i < n; i++) {
			fill(dp[i], 1l << 60);
			for (int j = 0; j < n; j++) {
				dp[i][j] = min(dp[i][j], dp[i - 1][j] + is[i][j]);
				if (j > 0) dp[i][j] = min(dp[i][j], dp[i][j - 1] + is[i][j]);
			}
			for (int j = n - 1; j >= 0; j--) {
				dp[i][j] = min(dp[i][j], dp[i - 1][j] + is[i][j]);
				if (j < n - 1) dp[i][j] = min(dp[i][j], dp[i][j + 1] + is[i][j]);
			}
		}
		long res = 1l << 60;
		for (int j = 0; j < n; j++)
			res = min(res, dp[n - 1][j]);
		System.out.println(res);
	}

	void run061() {
		int res = 0;
		for (int i = 1;; i++) {
			long l = i_thN_gonal(8, i);
			if (l >= 1000) {

			}
		}
	}
	void run070() {
		int res = 0;
		double val = 10000;
		for (int i = 2; i < 10000000l; i++) {
			if (i % 10000 == 0) debug(i, res, totient(res));
			long tot = totient(i);
			double d = (double) i / totient(i);
			if (isPermutation(i, tot) && d < val) {
				val = d;
				res = i;
			}
		}
		System.out.println(res);
	}
	void run072() {
		long res = 0;
		int n = MILLION;
		for (int i = 2; i <= n; i++) {
			res += totient(i);
		}
		System.out.println(res);
	}
	void run102() {
		Scanner sc = new Scanner(System.in);
		sc.useDelimiter(",|\\s");
		int res = 0;
		// while(sc.hasNext())debug(sc.next());
		while (sc.hasNext()) {
			P[] ps = new P[3];
			for (int i = 0; i < 3; i++)
				ps[i] = new P(sc.nextDouble(), sc.nextDouble());
			debug(ps);
			if (contains(ps, new P(0, 0)) >= 0) res++;
		}
		System.out.println(res);
	}
	int contains(P[] ps, P p) {// 点、多角形包含判定 OUT,ON,IN = -1, 0, 1.
		int n = ps.length;
		int res = -1;
		for (int i = 0; i < n; i++) {
			P a = ps[i].sub(p), b = ps[( i + 1 ) % n].sub(p);
			if (a.y > b.y) {
				P t = a;
				a = b;
				b = t;
			}
			if (a.y <= 0 && 0 < b.y && a.det(b) < 0) res *= -1;
			if (a.det(b) == 0 && a.dot(b) <= 0) return 0;
		}
		return res;
	}
	void run206() {
		debug(1389019170l * 1389019170);
		int[] is = { 0, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
		loop: for (int i = 10;; i += 10) {
			long a = (long) i * i;
			for (int j = 0; j < 10; j++) {
				if (a % 10 != is[j]) continue loop;
				a /= 100;
			}
			System.out.println(i);
		}
	}
	void run065() {
		int n = 100 - 1;
		long[] as = new long[n];
		for (int i = 0; i < n; i++)
			as[i] = i % 3 == 1 ? ( i / 3 + 1 ) * 2 : 1;
		System.out.println(sum(calcContinueFraction(2, as).num));
	}

	void run085() {
		int M = 2 * MILLION;
		long res = 0;
		int H = 0, W = 0;
		int val = 0;
		for (int i = 1; i * ( i + 1 ) <= 2 * M; i++) {
			double d = solve(i * ( i + 1 ), i, -4 * M);
			int w = (int) d;
			int c = i * ( i + 1 ) * w * ( w + 1 ) / 4;
			if (abs(M - val) > abs(M - c)) {
				val = c;
				res = (long) i * w;
				H = i;
				W = w;
			}
			w++;
			c = i * ( i + 1 ) * w * ( w + 1 ) / 4;
			if (abs(M - val) > abs(M - c)) {
				val = c;
				res = (long) i * w;
				H = i;
				W = w;
			}
		}
		debug(H, W, val);
		System.out.println(res);
	}
	double solve(double a, double b, double c) {
		return ( -b + sqrt(b * b - 4 * a * c) ) / ( 2 * a );
	}

	void run074() {
		int res = 0;
		int[] f = new int[10];
		f[0] = 1;
		for (int i = 1; i < 10; i++)
			f[i] = f[i - 1] * i;
		loop: for (int i = 1; i <= MILLION; i++) {
			HashSet<Integer> set = new HashSet<Integer>();
			int num = i;
			for (int j = 0; j < 60; j++) {
				if (set.contains(num)) continue loop;
				set.add(num);
				num = next(num, f);
			}
			res++;
		}
		System.out.println(res);
	}

	int next(int num, int[] f) {
		int res = 0;
		while (num > 0) {
			res += f[num % 10];
			num /= 10;
		}
		return res;
	}

	void run112() {
		int b = 0;
		for (int i = 1;; i++) {
			if (!isDecreasingNumber(i) && !isIncreasingNumber(i)) b++;
			if (b * 100 / i >= 99) {
				System.out.println(i);
				return;
			}
		}
	}

	boolean isDecreasingNumber(long l) {
		long k = 0;
		while (l > 0) {
			if (k > l % 10) return false;
			k = l % 10;
			l /= 10;
		}
		return true;
	}
	boolean isIncreasingNumber(long l) {
		long k = 9;
		while (l > 0) {
			if (k < l % 10) return false;
			k = l % 10;
			l /= 10;
		}
		return true;
	}

	void run062() {
		HashMap<Entry, Pair> map = new HashMap<Entry, Pair>();
		Entry f = new Entry(127035954683l);
		for (int i = 1;; i++) {
			Entry e = new Entry((long) i * i * i);
			if (e.equals(f)) {
				debug(i, (long) i * i * i);
			}

			if (map.containsKey(e)) {
				map.put(e, map.get(e).inc());
				if (map.get(e).count == 5) {
					System.out.println(map.get(e).val);
					return;
				}
			} else {
				map.put(e, new Pair((long) i * i * i, 1));
			}
		}
	}
	class Pair {
		long val;
		int count;
		Pair(long val, int c) {
			this.val = val;
			count = c;
		}
		Pair inc() {
			count++;
			return this;
		}
		long a, b;
		Pair(long a, long b) {
			this.a = a;
			this.b = b;
		}
	}
	class Entry {
		int[] is = new int[10];
		Entry(long l) {
			while (l > 0) {
				is[(int) ( l % 10 )]++;
				l /= 10;
			}
		}
		public int hashCode() {
			return Arrays.hashCode(is);
		}
		public boolean equals(Object obj) {
			return Arrays.equals(is, ( (Entry) obj ).is);
		}
	}

	void run073() {
		int res = 0;
		for (int i = 1; i <= 10000; i++)
			for (int j = 1; j <= i; j++)
				if (gcd(i, j) == 1) {
					if (.5 > (double) j / i && (double) j / i > 1. / 3) res++;
				}
		System.out.println(res);
	}

	void run076() {
		System.out.println(partition(100 + 100, 100) - 1);
	}
	long partition(int N, int K) {
		long[][] p;
		p = new long[N + 1][K + 1];
		p[1][1] = 1;
		for (int n = 1; n <= N; n++) {
			for (int k = 1; k <= K; k++) {
				if (k > 0 && n > 0) p[n][k] += p[n - 1][k - 1];
				if (n - k >= 0) p[n][k] += p[n - k][k];
			}
		}
		return p[N][K];
	}

	void run071() {
		int res = 0;
		double val = 0;
		for (int i = 1; i <= MILLION; i++)
			if (i != 7) {
				int m = 3 * i / 7;
				while (gcd(m, i) > 1)
					m--;
				double d = (double) m / i;
				if (d > val) {
					val = d;
					res = m;
					debug(m, i, 3. / 7 - val);
				}
			}
		debug(val);
		System.out.println(res);
	}

	void run081() {
		Scanner sc = new Scanner(System.in);
		int n = 80;
		int[][] is = new int[n][n];
		for (int i = 0; i < n; i++) {
			String[] ss = sc.next().split(",");
			for (int j = 0; j < n; j++)
				is[i][j] = Integer.valueOf(ss[j]);
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int val = 1 << 29;
				if (i > 0) val = is[i - 1][j];
				if (j > 0) val = min(val, is[i][j - 1]);
				is[i][j] += val == ( 1 << 29 ) ? 0 : val;
			}
		}
		System.out.println(is[n - 1][n - 1]);
	}

	void run099() {
		Scanner sc = new Scanner(System.in);
		int i = 1;
		int res = 1;
		double val = 0;
		while (sc.hasNext()) {
			// debug(i);
			String[] ss = sc.next().split(",");
			// debug(ss);
			double d = Integer.valueOf(ss[1]) * log(Integer.valueOf(ss[0]));
			if (d > val) {
				val = d;
				res = i;
			}
			i++;
		}
		System.out.println(res);
	}

	void run069() {
		int res = 0;
		double val = 0;
		for (int i = 1; i <= MILLION; i++) {
			long tot = totient(i);
			if (val < (double) i / tot) {
				val = (double) i / tot;
				res = i;
			}
		}
		System.out.println(res);
	}

	void run092() {
		int res = 0;
		int[] memo = new int[MILLION * 10];
		for (int i = 1; i < 10 * MILLION; i++) {
			if (calc092(i, memo) == 89) res++;
		}
		System.out.println(res);
	}
	int calc092(int n, int[] memo) {
		if (n == 89) return memo[n] = n;
		if (n == 1) return memo[n] = n;
		if (memo[n] > 0) { return memo[n]; }
		int next = 0;
		int m = n;
		while (m > 0) {
			next += pow(m % 10, 2);
			m /= 10;
		}
		return memo[n] = calc092(next, memo);
	}

	void run097() {
		System.out.println(pow(2, 7830457, 10000000000l) * 28433 % 10000000000l + 1);
	}

	void run067() {
		Scanner sc = new Scanner(System.in);
		int n = 100;
		int[][] is = new int[n][];
		for (int i = 0; i < n; i++) {
			is[i] = new int[i + 1];
			String[] ss = sc.nextLine().split(" ");
			for (int j = 0; j <= i; j++)
				is[i][j] = Integer.valueOf(ss[j]);
		}
		for (int i = 1; i < n; i++) {
			for (int j = 0; j <= i; j++) {
				int val = 0;
				if (j - 1 >= 0) val = is[i - 1][j - 1];
				if (j < i) val = max(is[i - 1][j], val);
				is[i][j] += val;
			}
		}
		int res = 0;
		for (int j = 0; j < n; j++)
			res = max(res, is[n - 1][j]);
		System.out.println(res);
	}
	void run063() {
		int res = 0;
		for (int i = 1; i <= 9; i++) {
			int p = 0;
			int dig = 0;
			while (dig >= p) {
				p++;
				dig = dig(valueOf(i).pow(p));
				if (dig == p) {
					debug(i, p, valueOf(i).pow(p));
					res++;
				}
			}
		}
		System.out.println(res);

	}

	int head(long l) {
		while (l >= 10)
			l /= 10;
		return (int) l;
	}

	int[] PrimeList(int len) {
		int[] res = new int[len];
		for (int i = 2, j = 0; j < len; i++)
			if (isPrime(i)) res[j++] = i;
		return res;
	}

	int dig(BigInteger b) {
		int res = 0;
		while (b.compareTo(ZERO) > 0) {
			res++;
			b = b.divide(valueOf(10));
		}
		return res;
	}
	long totient(long n) {
		if (n == 0) return 0;
		long ans = n;
		for (int i = 2; i * i <= n; i++) {
			if (n % i == 0) {
				ans -= ans / i;
				while (n % i == 0)
					n /= i;
			}
		}
		if (n > 1) ans -= ans / n;
		return ans;
	}
	int dig(long l) {
		int res = 0;
		while (l > 0) {
			res++;
			l /= 10;
		}
		return res;
	}

	long sum(BigInteger b) {
		long res = 0;
		while (b.compareTo(ZERO) > 0) {
			res += b.mod(valueOf(10)).intValue();
			b = b.divide(valueOf(10));
		}
		return res;
	}
	long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}
	long rev(long a) {
		long res = 0;
		while (a > 0) {
			res *= 10;
			res += a % 10;
			a /= 10;
		}
		return res;
	}
	boolean isPalindrome(long l) {
		return l == rev(l);
	}

	static class Poker {
		String NUM = "23456789TJQKA";
		String SUIT = "CDHS";
		class Card implements Comparable<Card> {
			boolean usedForHand;
			int num, suit;
			public int compareTo(Card o) {
				return num != o.num ? num - o.num : suit - o.suit;
			}
		}
		enum _Hand {
			Highest_Card, One_Pair, Two_Pairs, Three_of_a_Kind, Straight, Flush, Full_House, Four_of_a_Kind, Straight_Flush
		};
		class Hand {
			Card[] cards;
			_Hand hand;
			Hand(Card[] cards) {
				this.cards = cards;
			}

		}
	}

	BigInteger comB(int a, int b) {
		BigInteger res = ONE;
		for (int i = 0; i < b; i++) {
			res = res.multiply(valueOf(a - i));
			res = res.divide(valueOf(i + 1));
		}
		return res;
	}
	final int MILLION = 1000000;
	int make(int dig, int hole, int num, int val) {
		int res = 0;
		for (; dig > 0; dig--) {
			res *= 10;
			if (( hole & 1 ) == 1) {
				res += val;
			} else {
				res += num % 10;
				num /= 10;
			}
			hole >>= 1;
		}
		return res;
	}
	Ratio calcContinueFraction(long a, long[] as) {
		int n = as.length;
		Ratio num = new Ratio(0, 0);
		Ratio one = new Ratio(1, 1);
		for (int i = n - 1; i >= 0; i--) {
			num = one.div(num.add(new Ratio(as[i], 1)));
		};
		num = num.add(new Ratio(a, 1));
		return num;
	}

	boolean isPermutation(long a, long b) {
		int[] is = new int[10], js = new int[10];
		while (a > 0) {
			is[(int) ( a % 10 )]++;
			a /= 10;
		}
		while (b > 0) {
			js[(int) ( b % 10 )]++;
			b /= 10;
		}
		for (int i = 0; i < 10; i++)
			if (is[i] != js[i]) return false;
		return true;
	}
	long pow(long a, long b) {
		long res = 1;
		while (b > 0) {
			if (b % 2 == 1) res *= a;
			b /= 2;
			a *= a;
		}
		return res;
	}

	long pow(long a, long b, long n) {// a ^ b mod n.
		a %= n;
		long res = 1;
		while (b > 0) {
			if (b % 2 == 1) res = product(res, a, n);
			b /= 2;
			a = product(a, a, n);
		}
		return res;
	}
	long product(long a, long b, long n) {// a * b mod n.
		if (a <= Integer.MAX_VALUE && b <= Integer.MAX_VALUE) return a * b % n;
		long res = 0;
		long na = a % n;
		while (b > 0) {
			if (b % 2 == 1) res = ( res + na ) % n;
			b /= 2;
			na = ( na * 2 ) % n;
		}
		return res;
	}

	boolean isTriangle(long l) {
		if (!isSquareNumber(1 + 8 * l)) return false;
		return (long) round(sqrt(1 + 8 * l)) % 2 == 1;
	}
	boolean isSquareNumber(long l) {
		return eq(pow(round(sqrt(l)), 2), l);
	}
	boolean isPentagonal(long l) {
		if (!isSquareNumber(1 + 24 * l)) return false;
		return ( (long) round(sqrt(1 + 24 * l)) + 1 ) % 6 == 0;
	}
	boolean isN_gonal(long n, long l) {// lがn角数である
		long d = ( n - 4 ) * ( n - 4 ) + 8 * l * ( n - 2 );
		return isSquareNumber(d) && ( n - 4 + (long) round(sqrt(d)) ) % ( 2 * ( n - 2 ) ) == 0;
	}
	long i_thN_gonal(long n, long i) {
		return i * ( ( n - 2 ) * i - ( n - 4 ) ) / 2;
	}

	double pow(double a, double b) {
		return Math.pow(a, b);
	}
	boolean eq(double a, double b) {
		return signum(a - b) == 0;
	}

	long toi(int[] is) {
		long res = 0;
		for (int i : is) {
			res *= 10;
			res += i;
		}
		return res;
	}

	boolean isPalindrome(String s) {
		return s.equals(new StringBuilder(s).reverse().toString());
	}
	class Ratio implements Comparable<Ratio> {// 0 = 0/1. den>0.
		BigInteger num, den;
		Ratio(long num, long den) {
			this.num = valueOf(num);
			this.den = valueOf(den);
			normalize();
		}
		Ratio(BigInteger num, BigInteger den) {
			this.num = num;
			this.den = den;
		}
		Ratio add(Ratio r) {
			return new Ratio(num.multiply(r.den).add(den.multiply(r.num)), den.multiply(r.den));
		}
		Ratio sub(Ratio r) {
			return new Ratio(num.multiply(r.den).subtract(den.multiply(r.num)), den.multiply(r.den));
		}
		Ratio mul(Ratio r) {
			return new Ratio(num.multiply(r.num), den.multiply(r.den));
		}
		Ratio div(Ratio r) {
			return new Ratio(num.multiply(r.den), den.multiply(r.num));
		}
		Ratio abs() {
			return new Ratio(num.abs(), den);
		}
		public int compareTo(Ratio o) {
			return ( num.multiply(o.den).subtract(den.multiply(o.num)) ).signum();
		}
		public boolean equals(Object obj) {
			return compareTo((Ratio) obj) == 0;
		}
		public String toString() {
			return num + "/" + den;
		}
		void normalize() {
			if (num.equals(ZERO)) den = ONE;
			else {
				if (den.compareTo(ZERO) < 0) {
					num = num.negate();
					den = den.negate();
				}
				BigInteger d = gcd(num.abs(), den);
				num = num.divide(d);
				den = den.divide(d);
			}
		}
		BigInteger gcd(BigInteger a, BigInteger b) {
			return b.equals(ZERO) ? a : gcd(b, a.mod(b));
		}

		long gcd(long a, long b) {
			return b == 0 ? a : gcd(b, a % b);
		}
		void show() {
			if (den.equals(ONE)) System.out.print(num);
			else System.out.print(num + "/" + den);
		}
	}
	boolean isPrime(long p) {
		if (p < 2) return false;
		for (long i = 2; i * i <= p; i++) {
			if (p % i == 0) return false;
		}
		return true;
	}
	public class RepeatingDecimal {
		int n;
		int[] nonrep;
		ArrayList<Integer> rep;
		RepeatingDecimal(int n) {
			this.n = n;
			rep = new ArrayList<Integer>();
		}
		void run() {
			int twos = twos(n), fives = fives(n);
			nonrep = new int[max(twos, fives)];
			int r = 10;
			for (int j = 0; j < nonrep.length; j++) {
				nonrep[j] = r / n;
				r %= n;
				r *= 10;
			}
			if (r == 0) return;
			int fr = r;
			do {
				rep.add(r / n);
				r %= n;
				r *= 10;
			} while (fr != r);
		}
		int twos(int n) {
			int res = 0;
			while (n % 2 == 0) {
				res++;
				n /= 2;
			}
			return res;
		}
		int fives(int n) {
			int res = 0;
			while (n % 5 == 0) {
				res++;
				n /= 5;
			}
			return res;
		}
	}
	boolean nextPermutation(int[] is) {
		int n = is.length;
		for (int i = n - 1; i > 0; i--) {
			if (is[i - 1] < is[i]) {
				int j = n;
				while (is[i - 1] >= is[--j]);
				swap(is, i - 1, j);
				rev(is, i, n);
				return true;
			}
		}
		rev(is, 0, n);
		return false;
	}
	void swap(int[] is, int i, int j) {
		int t = is[i];
		is[i] = is[j];
		is[j] = t;
	}
	void rev(int[] is, int s, int t) {
		while (s < --t)
			swap(is, s++, t);
	}
	int numberOfDistinctPrimeFactors(long l) {
		int res = 0;
		for (int i = 2; i * i <= l; i++) {
			if (l % i == 0) {
				res++;
				while (l % i == 0) {
					l /= i;
				}
			}
		}
		if (l > 1) res++;
		return res;
	}
	int sumOfProperDivisor(int n) {
		int res = 0;
		for (int i = 1; i < n; i++) {
			if (n % i == 0) res += i;
		}
		return res;
	}
	long comb(int n, int m) {
		long res = 1;
		for (int i = 0; i < m; i++) {
			res *= n - i;
			res /= i + 1;
		}
		return res;
	}
	public class P implements Comparable<P> {
		double x, y;
		static private final double EPS = 1e-9;
		P(double x, double y) {
			this.x = x;
			this.y = y;
		}
		P add(P p) {
			return new P(x + p.x, y + p.y);
		}
		P sub(P p) {
			return new P(x - p.x, y - p.y);
		}
		P mul(double d) {
			return new P(x * d, y * d);
		}
		P div(double d) {
			return new P(x / d, y / d);
		}
		double dot(P p) {
			return x * p.x + y * p.y;
		}
		double det(P p) {
			return x * p.y - y * p.x;
		}
		double norm() {
			return sqrt(x * x + y * y);
		}
		double norm2() {
			return x * x + y * y;
		}
		double dist(P p) {
			return sub(p).norm();
		}
		P rot90() {
			return new P(-y, x);
		}
		P rot(double t) {// verified
			return new P(x * cos(t) - y * sin(t), x * sin(t) + y * cos(t));
		}
		P unit() {
			double d = norm();
			return new P(x / d, y / d);
		}
		P unit(double d) {
			return mul(d / norm());
		}
		double angle() {// verified.
			return atan2(y, x);// [-PI,PI].
		}
		double angle(P p) {// not verified.
			return ( p.angle() - angle() + 3 * PI ) % ( 2 * PI ) - PI;// [-PI,PI].
		}
		private int signum(double d) {
			return d < -EPS ? -1 : d > EPS ? 1 : 0;
		}
		public int compareTo(P o) {
			return signum(x - o.x) != 0 ? signum(x - o.x) : signum(y - o.y);
		}
		public boolean equals(Object o) {
			return compareTo((P) o) == 0;
		}
		public int hashCode() {
			return new Double(x).hashCode() * 0x0000f000 + new Double(y).hashCode();
		}
		public String toString() {
			return x + " " + y;
		}
	}

	int divisorCount(long l) {
		int res = 1;
		for (int i = 2; i * i <= l; i++) {
			int count = 0;
			while (l % i == 0) {
				l /= i;
				count++;
			}
			res *= count + 1;
		}
		if (l > 1) res *= 2;
		return res;
	}
	int[] dx = { 1, 1, 1, 0 }, dy = { -1, 0, 1, 1 };
	double EPS = 1e-7;
	int signum(double d) {
		return d < -EPS ? -1 : d > EPS ? 1 : 0;
	}
}
