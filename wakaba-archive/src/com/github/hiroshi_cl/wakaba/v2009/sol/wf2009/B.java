package com.github.hiroshi_cl.wakaba.v2009.sol.wf2009;
import java.util.*;
import java.io.*;

/*
 -論理回路（組み合わせ回路）が与えられるが、論理素子に挙動が怪しいものがあるので、それを特定する問題。
 -入力から期待される出力と実際の出力が合わない時にどの素子（どれか１個）がおかしいのかを答える。
 -異常は３種類
 --常に1
 --常に0
 --正しい出力と反転
 -シミュレータを作り、正しくない出力がある時に、各素子に異常出力をそれぞれセットしてみて出力がどうなるか計算していく（全探索）。
 -複数の可能性がある場合はUnable to totally classifyを返す。
 */
public class B {
	public static void main(String[] args) {
		new B().run();
	}

	public void run() {
		Scanner sc = fromFile("bad.in");
		for(int t = 1;; t++) {
			int N = sc.nextInt();
			int G = sc.nextInt();
			int U = sc.nextInt();
			if((N | G | U) == 0)
				break;

			Gate[] ii = new Gate[N];
			Gate[] gi = new Gate[G];
			Gate[] oi = new Gate[U];

			for(int i = 0; i < N; i++)
				ii[i] = new Gate('i');
			String[][] inputlines = new String[G][2];
			for(int i = 0; i < G; i++) {
				gi[i] = new Gate(sc.next().charAt(0));
				inputlines[i][0] = sc.next();
				if(gi[i].gate != GType.NOT)
					inputlines[i][1] = sc.next();
			}
			for(int i = 0; i < G; i++) {
				Gate g1, g2 = null;

				int k1 = Integer.parseInt(inputlines[i][0].substring(1)) - 1;
				if(inputlines[i][0].charAt(0) == 'i')
					g1 = ii[k1];
				else
					g1 = gi[k1];

				if(gi[i].gate != GType.NOT) {
					int k2 = Integer.parseInt(inputlines[i][1].substring(1)) - 1;
					if(inputlines[i][1].charAt(0) == 'i')
						g2 = ii[k2];
					else
						g2 = gi[k2];
				}
				gi[i].setInput(g1, g2);
			}
			for(int i = 0; i < U; i++)
				oi[i] = gi[sc.nextInt()-1];

			int B = sc.nextInt();
			int[][] inputs = new int[B][N];
			int[][] outputs = new int[B][U];
			for(int i = 0; i < B; i++) {
				for(int j = 0; j < N; j++)
					inputs[i][j] = sc.nextInt();
				for(int j = 0; j < U; j++)
					outputs[i][j] = sc.nextInt();
			}

			FType failure = (check(ii, oi, inputs, outputs) ? FType.NONE : null);
			int fp = 0;
			if(failure != FType.NONE) {
				loop :
					for(int i = 0; i < G; i++) {
						for(FType type : FType.failures) {
							gi[i].setType(type);
							if(check(ii, oi, inputs, outputs))
								if(failure == null) {
									fp = i + 1;
									failure = type;
								}
								else {
									failure = FType.UNKNOWN;
									break loop;
								}
						}
						gi[i].setType(FType.NONE);
					}
			}
			if(failure == null)
				failure = FType.UNKNOWN;
			System.out.println(failure.message(t, fp));
		}
	}

	private boolean check(Gate[] ii, Gate[] oi, int[][] inputs, int[][] outputs) {
		int B = inputs.length;
		int N = inputs[0].length;
		int U = outputs[0].length;
		for(int i = 0; i < B; i++) {
			for(int j = 0; j < N; j++)
				ii[j].setType(FType.val(inputs[i][j]));
			for(int j = 0; j < U; j++)
				if(oi[j].failedVal() != outputs[i][j]) {
					//					debug(inputs[i], "output:", oi[j].failedVal(), "expected:", outputs[i][j]);
					return false;
				}
		}
		return true;
	}

	private class Gate {

		FType type = FType.NONE;
		GType gate;
		Gate input1, input2;
		void setInput(Gate i1, Gate i2) {
			input1 = i1;
			input2 = i2;
		}
		void setType(FType t) {
			type = t;
		}
		int failedVal() {
			int v = gate.val(input1, input2);
			switch(type) {
			case NONE : return v;
			case INVERT : return 1 - v;
			case ZERO : return 0;
			case ONE : return 1;
			}
			return -1;
		}

		Gate(char c) {
			switch(c) {
			case 'a' : gate = GType.AND; break;
			case 'o' : gate = GType.OR;  break;
			case 'n' : gate = GType.NOT; break;
			case 'x' : gate = GType.XOR; break;
			case 'i' : gate = GType.INPUT; break;
			}
		}
	}
	enum GType {
		AND {
			@Override
			int val(Gate input1, Gate input2) {
				return input1.failedVal() & input2.failedVal();
			}
		},

		OR {
			@Override
			int val(Gate input1, Gate input2) {
				return input1.failedVal() | input2.failedVal();
			}
		},

		XOR {
			@Override
			int val(Gate input1, Gate input2) {
				return input1.failedVal() ^ input2.failedVal();
			}
		},

		NOT {
			@Override
			int val(Gate input1, Gate input2) {
				return 1 - input1.failedVal();
			}
		},

		INPUT {
			@Override
			int val(Gate input1, Gate input2) {
				return -1;
			}
		};

		abstract int val(Gate input1, Gate input2);
	}

	private enum FType {

		NONE {
			@Override
			String message(int c, int gate) {
				return String.format("Case %d: No faults detected", c);
			}
		},

		INVERT {
			@Override
			String message(int c, int gate) {
				return String.format("Case %d: Gate %d is failing; output inverted", c, gate);
			}
		},

		ZERO {
			@Override
			String message(int c, int gate) {
				return String.format("Case %d: Gate %d is failing; output stuck at 0", c, gate);
			}
		},

		ONE {
			@Override
			String message(int c, int gate) {
				return String.format("Case %d: Gate %d is failing; output stuck at 1", c, gate);
			}
		},

		UNKNOWN {
			@Override
			String message(int c, int gate) {
				return String.format("Case %d: Unable to totally classify the failure", c);
			}
		};

		static FType[] failures = { INVERT, ZERO, ONE };

		static FType val(int i) {
			if(i == 1)
				return ONE;
			else if(i == 0)
				return ZERO;
			else
				return null;
		}

		abstract String message(int c,int gate);
	}

	private Scanner fromFile(String name) {
		try {
			return new Scanner(new File(name));
		} catch(Exception e) {
			System.out.println("using standard io"); // local test
			return new Scanner(System.in);
		}
	}
	private static void debug(Object...a) {
		System.out.println(Arrays.deepToString(a));
	}
}
