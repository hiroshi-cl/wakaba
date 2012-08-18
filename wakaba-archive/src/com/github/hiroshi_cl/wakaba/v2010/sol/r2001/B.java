package com.github.hiroshi_cl.wakaba.v2010.sol.r2001;
import java.util.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
public class B {
	public static void main(String[] args) {
		new B().run();
	}
	void run() {
		Scanner sc = new Scanner(System.in);
		for (;;) {
			int n = sc.nextInt();
			if (n == 0) return;
			Market market = new Market(n);
			for (int i = 0; i < n; i++) {
				market.input(sc);
			}
			market.output();
		}
	}
	class Market {
		void output() {
			sort(coms);
			sort(pers);
			for (Comodity c : coms) {
				c.show();
			}
			System.out.println("--");
			for (Person p : pers)
				p.show();
			System.out.println("----------");
		}
		Market(int n) {
			orders = new Order[n];
		}
		int n;
		Order[] orders;
		ArrayList<Comodity> coms = new ArrayList<Comodity>();
		ArrayList<Person> pers = new ArrayList<Person>();
		void input(Scanner sc) {
			Order o = new Order(sc.next(), sc.next(), sc.next(), sc.nextInt());
			orders[n] = o;
			match(n++);
		}
		void match(int id) {
			Order o = orders[id];
			Order p = null;
			for (int i = 0; i < n; i++) {
				if (o.match(orders[i])) {
					if (p == null) {
						p = orders[i];
						continue;
					}
					if (o.buy) {
						if (orders[i].price < p.price) {
							p = orders[i];
						}
					} else {
						if (orders[i].price > p.price) {
							p = orders[i];
						}
					}
				}
			}
			if (p != null) {
				if (!p.buy) {
					update(p, o);
				} else {
					update(o, p);
				}
			}
		}
		void update(Order sell, Order buy) {
			sell.dealed = true;
			buy.dealed = true;
			Comodity c = getCom(sell.comodity);
			Person p1 = getPer(sell.name);
			Person p2 = getPer(buy.name);
			if (c == null) {
				c = new Comodity(sell.comodity);
				coms.add(c);
			}
			int price = (sell.price + buy.price) / 2;
			c.prices.add(price);
			p1.rec += price;
			p2.pay += price;
		}
		Comodity getCom(String name) {
			for (Comodity c : coms) {
				if (c.name.equals(name)) return c;
			}
			return null;
		}
		Person getPer(String name) {
			for (Person p : pers) {
				if (p.name.equals(name)) return p;
			}
			return null;
		}
		class Order {
			boolean match(Order o) {
				if (o.dealed) return false;
				if (name.equals(o.name)) return false;
				if (buy == o.buy) return false;
				if (!comodity.equals(o.comodity)) return false;
				if (buy) {
					if (price < o.price) return false;
				} else {
					if (price > o.price) return false;
				}
				return true;
			}
			boolean dealed = false;
			String name;
			boolean buy;
			String comodity;
			int price;
			Order(String name, String type, String com, int pri) {
				this.name = name;
				if (getPer(name) == null) pers.add(new Person(name));
				buy = type.equals("BUY");
				comodity = com;
				price = pri;
			}
		}
	}
	class Comodity implements Comparable<Comodity> {
		String name;
		ArrayList<Integer> prices = new ArrayList<Integer>();
		Comodity(String name) {
			this.name = name;
		}
		public int compareTo(Comodity o) {
			return name.compareTo(o.name);
		}
		void show() {
			sort(prices);
			int mean = 0;
			for (int p : prices)
				mean += p;
			mean /= prices.size();
			System.out.println(name + " " + prices.get(0) + " " + mean + " "
					+ prices.get(prices.size() - 1));
		}
	}
	class Person implements Comparable<Person> {
		void show() {
			System.out.println(name + " " + pay + " " + rec);
		}
		String name;
		int pay = 0;
		int rec = 0;
		Person(String name) {
			this.name = name;
		}
		public int compareTo(Person o) {
			return name.compareTo(o.name);
		}
	}
}