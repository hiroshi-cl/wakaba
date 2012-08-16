package com.github.hiroshi_cl.wakaba.v2009.sol.dp2009;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
// すごく遅い
public class Be2 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		for(int N = sc.nextInt(); N >= 0; N = sc.nextInt()) {
			int P = sc.nextInt();
			int[] ps = new int[P];
			for(int i = 0, c = nextPrime(N); i < P; i++, c = nextPrime(c))
				ps[i] = c;
			List<Integer> li = new ArrayList<Integer>(P*P);
			for(int i = 0; i < P; i++)
				for(int j = i; j < P; j++)
					li.add(ps[i] + ps[j]);
			Collections.sort(li);
			System.out.println(li.get(P-1));
		}
	}

	static int nextPrime(int n) {
		return valueOf(n).nextProbablePrime().intValue();
	}

}
