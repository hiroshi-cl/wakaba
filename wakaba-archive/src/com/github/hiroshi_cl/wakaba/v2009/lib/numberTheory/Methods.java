package com.github.hiroshi_cl.wakaba.v2009.lib.numberTheory;

public class Methods {
	// verified
	long pow(long a, long b, long n) {// a ^ b mod n.
		a %= n;
		long res = 1;
		while (b > 0) {
			if (b % 2 == 1)
				res = product(res, a, n);
			b /= 2;
			a = product(a, a, n);
		}
		return res;
	}

	long product(long a, long b, long n) {// a * b mod n.
		if (a <= Integer.MAX_VALUE && b <= Integer.MAX_VALUE)
			return a * b % n;
		long res = 0;
		long na = a % n;
		while (b > 0) {
			if (b % 2 == 1)
				res = (res + na) % n;
			b /= 2;
			na = (na * 2) % n;
		}
		return res;
	}

	/**
	 * n と互いに素な数の個数を φ(n) と表す．この関数のことをオイラーのφ関数という．オイラーのφ関数には次の性質がある． φ(n) = n (1
	 * - 1/p1) ... (1 - 1/pm)，ただし { pk } は n の素因数分解 a^φ(n) = 1 (mod n) (オイラーの定理)
	 * φ関数は第一の性質を用いて計算できる．何度も計算する場合はまとめて計算しておき，表引きする．
	 * 
	 * 原始根（げんしこん）とは、奇素数pを法として、p未満のある自然数aが p-1乗してはじめて1と合同になるときの aをいう。
	 * 
	 * 出典：ttp://www.prefield.com/algorithm/index.html　(spaghetti source)
	 * 
	 * verified:pku1284
	 */
	int totient(int n) {
		if (n == 0)
			return 0;
		int ans = n;
		for (int i = 2; i * i <= n; i++) {
			if (n % i == 0) {
				ans -= ans / i;
				while (n % i == 0)
					n /= i;
			}
		}
		if (n > 1)
			ans -= ans / n;
		return ans;
	}

	/**
	 * ヤコビ記号はルジャンドル記号を拡張したものなので，まずルジャンドル記号について述べる． 奇数 a, 素数 p > 2 に対し X^2 == a
	 * (mod p) が可解のとき (a/p) = 1, そうでないとき (a/p) = -1 となる記号 (a/p)
	 * を導入する．この記号をルジャンドル記号という． このときオイラー基準より (a/p) = a^{(p-1)/2} が成立する．
	 * ルジャンドル記号は一般に計算が難しい．そこで素数条件を緩和した記号 (a/n) = (n/p1) ... (a/pm) を導入する．
	 * ただし右辺はルジャンドル記号，{ pk } は n の素因数分解．こうして導入された左辺をヤコビ記号という．
	 * ヤコビ記号には平方剰余の相互法則と呼ばれる豊富な計算規則があるため， それを用いて O(log a) 程度の計算量で計算できる．
	 * ところでヤコビ記号の引数 n が素数のとき，ヤコビ記号はルジャンドル記号と一致する．
	 * そのため平方剰余であるかの判定にヤコビ記号を用いることができる．
	 * 素数でない場合もストレートに計算できるわけではないが，ヤコビ記号を利用することができる．
	 * 
	 * ヤコビ記号 (a/p) を計算する．ただし a, p は非負の整数，特に p は奇数でなければならない．
	 * 
	 * 出典：ttp://www.prefield.com/algorithm/index.html　(spaghetti source)
	 * 
	 * verified:pku1808
	 */
	int jacobi(int a, int m) {
		if (a == 0)
			return m == 1 ? 1 : 0;
		if (a < 0)
			return negpow((m - 1) / 2) * jacobi(-a, m);
		if (a % 2 != 0)
			return negpow((a - 1) * (m - 1) / 4) * jacobi(m % a, a);
		return negpow((m * m - 1) / 8) * jacobi(a / 2, m);
	}

	int negpow(int p) {
		return p % 2 == 0 ? 1 : -1;
	}
}