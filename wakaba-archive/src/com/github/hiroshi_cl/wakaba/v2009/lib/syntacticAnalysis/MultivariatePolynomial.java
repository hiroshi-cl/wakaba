package com.github.hiroshi_cl.wakaba.v2009.lib.syntacticAnalysis;

/*
 * 複数文字の多項式の構文解析
 *
 * EBNF:
 * form = expr{('+'|'-'),expr}
 * expr = term{term}
 * term = fact['^',number]
 * fact = '(',form,')' | number | alphabet
 */
import java.util.*;

public class MultivariatePolynomial {
	char[] cs;
	int p;

	void go() {
		while (cs[p] == ' ')
			p++;
	}

	Poly form() {
		go();
		Poly res = expr();
		while (cs[p] == '+' || cs[p] == '-') {
			if (cs[p] == '+') {
				p++;
				res = res.add(expr());
			} else {
				p++;
				res = res.sub(expr());
			}
			go();
		}
		return res;
	}

	Poly expr() {
		go();
		Poly res = term();
		while (Character.isDigit(cs[p]) || Character.isLetter(cs[p])
				|| cs[p] == '(') {
			res = res.mul(term());
			go();
		}
		return res;
	}

	Poly term() {
		go();
		Poly res = fact();
		if (cs[p] == '^') {
			p++;
			go();
			res = res.pow(cs[p] - '0');
			p++;
			go();
		}
		return res;
	}

	Poly fact() {
		go();
		Poly res;
		if (cs[p] == '(') {
			p++;
			go();
			res = form();
			p++;
		} else if (Character.isDigit(cs[p])) {
			res = new Poly();
			res.fs.add(new Factor());
			res.mul(number());
		} else {
			res = new Poly(cs[p++] - 'a');
		}
		go();
		return res;
	}

	int number() {
		go();
		int res = 0;
		while (Character.isDigit(cs[p])) {
			res *= 10;
			res += cs[p++] - '0';
		}
		go();
		return res;
	}

	class Poly {
		void normalize() {
			for (Factor f : fs.toArray(new Factor[0])) {
				if (f.a == 0) {
					fs.remove(f);
				}
			}
		}

		public String toString() {
			return fs.toString();
		}

		public boolean equals(Object obj) {
			Poly o = (Poly) obj;
			if (fs.size() != o.fs.size())
				return false;
			for (Factor f : fs) {
				if (!o.fs.contains(f))
					return false;
			}
			return true;
		}

		TreeSet<Factor> fs = new TreeSet<Factor>();

		Poly() {
		}

		Poly(Factor f) {
			fs.add(f);
			normalize();
		}

		Poly(int k) {
			Factor f = new Factor();
			f.ps[k] = 1;
			fs.add(f);
			normalize();
		}

		Poly pow(int p) {
			Poly res = new Poly(new Factor());
			for (int i = 0; i < p; i++) {
				res = res.mul(this);
			}
			return res;
		}

		void mul(int p) {
			for (Factor f : fs) {
				f.a *= p;
			}
			normalize();
		}

		Poly add(Poly o) {
			Poly res = new Poly();
			for (Factor f : fs)
				res.fs.add(f);
			for (Factor of : o.fs) {
				boolean contain = false;
				for (Factor f : res.fs) {
					if (f.equals(of)) {
						contain = true;
						f.a += of.a;
					}
				}
				if (!contain) {
					res.fs.add(of);
				}
			}
			res.normalize();
			return res;
		}

		Poly sub(Poly o) {
			Poly res = new Poly();
			for (Factor f : fs)
				res.fs.add(f);
			for (Factor of : o.fs) {
				of.a *= -1;
				boolean contain = false;
				for (Factor f : res.fs) {
					if (f.equals(of)) {
						contain = true;
						f.a += of.a;
					}
				}
				if (!contain) {
					res.fs.add(of);
				}
			}
			res.normalize();
			return res;
		}

		Poly mul(Poly o) {
			Poly res = new Poly();
			for (Factor of : o.fs) {
				for (Factor f : fs) {
					Factor nf = of.mul(f);
					boolean contain = false;
					if (res.fs.contains(nf)) {
						for (Factor nexf : res.fs) {
							if (nf.equals(nexf)) {
								contain = true;
								nexf.a += nf.a;
							}
						}
					}
					if (!contain) {
						res.fs.add(nf);
					}
				}
			}
			res.normalize();
			return res;
		}

		boolean identical(Poly o) {
			if (!equals(o))
				return false;
			Factor[] f = fs.toArray(new Factor[0]);
			Factor[] of = o.fs.toArray(new Factor[0]);
			for (int i = 0; i < f.length; i++) {
				if (f[i].a != of[i].a)
					return false;
			}
			return true;
		}

	}

	class Factor implements Comparable<Factor> {
		int[] ps = new int[26];
		int a = 1;

		public int compareTo(Factor o) {
			for (int i = 0; i < 26; i++) {
				if (ps[i] < o.ps[i])
					return -1;
				else if (ps[i] > o.ps[i])
					return 1;
			}
			return 0;
		}

		Factor mul(Factor f) {
			Factor res = new Factor();
			res.a = a * f.a;
			for (int i = 0; i < 26; i++) {
				res.ps[i] = ps[i] + f.ps[i];
			}
			return res;
		}

		public boolean equals(Object obj) {
			Factor o = (Factor) obj;
			for (int i = 0; i < 26; i++)
				if (ps[i] != o.ps[i])
					return false;
			return true;
		}

		public int hashCode() {
			return ps.hashCode();
		}
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		for (;;) {
			String s = sc.nextLine();
			if (s.equals("."))
				return;
			cs = (s + "=").toCharArray();
			p = 0;
			Poly ans = form();
			for (;;) {
				s = sc.nextLine();
				if (s.equals("."))
					break;
				cs = (s + "=").toCharArray();
				p = 0;
				Poly guess = form();
				if (ans.identical(guess)) {
					System.out.println("yes");
				} else {
					System.out.println("no");
				}
			}
			System.out.println(".");
		}
	}

	public static void main(String[] args) {
		new MultivariatePolynomial().run();
	}
}