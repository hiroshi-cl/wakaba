package com.github.hiroshi_cl.wakaba.v2010.sol.dp2005;

import java.io.*;
import java.util.*;
import java.math.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.math.BigInteger.*;

class E {
	static final int INF = 1 << 28;

	public void run() {
		Scanner sc = new Scanner(System.in);
		boolean flg = false;
		while (sc.hasNext()) {
			int n = sc.nextInt();
			int m = sc.nextInt();
			if (n == 0 && m == 0)
				return;
			if (flg)
				System.out.println();
			flg = true;
			int[][] map = new int[n][n];
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					map[i][j] = INF;
			int[][] nxt = new int[n][n];
			for (int i = 0; i < m; i++) {
				int s = sc.nextInt() - 1;
				int t = sc.nextInt() - 1;
				int d = sc.nextInt();
				map[s][t] = d;
				map[t][s] = d;
				nxt[s][t] = t;
				nxt[t][s] = s;
			}
			for (int k = 0; k < n; k++)
				for (int i = 0; i < n; i++)
					for (int j = 0; j < n; j++)
						if (map[i][j] > map[i][k] + map[k][j]
								|| (nxt[i][k] < nxt[i][j] && map[i][j] == map[i][k] + map[k][j])) {
							map[i][j] = map[i][k] + map[k][j];
							nxt[i][j] = nxt[i][k];
						}

			int l = sc.nextInt();
			Queue<Event> que = new PriorityQueue<Event>();
			for (int i = 0; i < l; i++) {
				int s = sc.nextInt() - 1;
				int t = sc.nextInt() - 1;
				int time = sc.nextInt();
				String name = sc.next();
				que.offer(new LetterArrived(time, new Letter(s, t, time, name)));
			}
			boolean[] unwaited = new boolean[n];

			List<Pair> lp = new ArrayList<Pair>();

			LL[] of = new LL[n];
			for (int i = 0; i < n; i++)
				of[i] = new LL();
			int rest = l;
			while (rest > 0) {
				Event e = que.poll();
//				Main.debug(e, of);
				switch (e.type) {
				case 0: {
					LetterArrived la = (LetterArrived) e;
					Letter let = la.letter;
//					Main.debug(let, let.nxt);
					if (la.letter.dst == la.letter.nxt) {
						lp.add(new Pair(let.time, let.name));
						rest--;
					} else {
						of[let.nxt].add(new Letter(nxt[let.nxt][let.dst], let.dst, let.time, let.name));
						if (!unwaited[let.nxt])
							que.offer(new PostmanReturned(let.time, let.nxt));
						unwaited[let.nxt] = true;
					}
					break;
				}
				case 1: {
					PostmanReturned pr = (PostmanReturned) e;
					int c = pr.office;
//					Main.debug(c);
					if (!of[c].isEmpty()) {
						sort(of[c]);
						Letter p = of[c].get(0);
						for (int i = 0; i < of[c].size(); i++) {
							Letter let = of[c].get(i);
							if (let.nxt == p.nxt) {
								of[c].remove(i);
								Letter nl = new Letter(let.nxt, let.dst, pr.time + map[c][let.nxt], let.name);
								que.add(new LetterArrived(nl.time, nl));
								i--;
							}
						}
						que.offer(new PostmanReturned(pr.time + 2 * map[c][p.nxt], c));
					} else
						unwaited[c] = false;
				}
				}
			}

			sort(lp);
			for (Pair p : lp)
				System.out.println(p.name + " " + p.time);
		}
	}

	class Pair implements Comparable<Pair> {
		final int time;
		final String name;

		public Pair(int time, String name) {
			this.time = time;
			this.name = name;
		}

		@Override
		public int compareTo(Pair o) {
			return time != o.time ? time - o.time : name.compareTo(o.name);
		}
	}

	class LL extends ArrayList<Letter> {
	};

	class Event implements Comparable<Event> {
		final int time, type;

		@Override
		public int compareTo(Event o) {
			return time != o.time ? time - o.time : type - o.type;
		}

		public Event(int time, int type) {
			this.time = time;
			this.type = type;
		}

		@Override
		public String toString() {
			return String.format("%d@%s", time, getClass().getName());
		}
	}

	class PostmanReturned extends Event {
		final int office;

		public PostmanReturned(int time, int office) {
			super(time, 1);
			this.office = office;
		}

	}

	class LetterArrived extends Event {
		final Letter letter;

		public LetterArrived(int time, Letter letter) {
			super(time, 0);
			this.letter = letter;
		}

	}

	class Letter implements Comparable<Letter> {
		final int nxt, dst, time;
		final String name;

		public Letter(int nxt, int dst, int time, String name) {
			this.nxt = nxt;
			this.dst = dst;
			this.time = time;
			this.name = name;
		}

		@Override
		public int compareTo(Letter o) {
			return time != o.time ? time - o.time : nxt - o.nxt;
		}

		@Override
		public String toString() {
			return name + " " + time;
		}
	}
}
