package com.github.hiroshi_cl.wakaba.v2009.sol.dp2009;
import java.util.*;

import static java.lang.Math.*;

public class Be {
	static final int MAXP = 150000;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean[] isComp = new boolean[MAXP];
		isComp[0] = isComp[1] = true;
		for(int i = 2; i < sqrt(MAXP); i++)
			if(!isComp[i])
				for(int j = i * i; j < MAXP; j += i)
					isComp[j] = true;

		for(int N = sc.nextInt(); N >= 0; N = sc.nextInt()) {
			int P = sc.nextInt();
			int[] ps = new int[P];
			for(int i = 0, c = N + 1; i < P; i++, c++) {
				while(isComp[c]) c++;
				ps[i] = c;
			}
			List<Integer> li = new ArrayList<Integer>(P*P);
			for(int i = 0; i < P; i++)
				for(int j = i; j < P; j++)
					li.add(ps[i] + ps[j]);
			Collections.sort(li);
			System.out.println(li.get(P-1));
		}
	}
}
