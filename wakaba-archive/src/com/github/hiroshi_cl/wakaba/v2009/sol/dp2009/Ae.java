package com.github.hiroshi_cl.wakaba.v2009.sol.dp2009;
import java.util.*;
import static java.lang.Math.*;

public class Ae {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		for(int N = sc.nextInt(); N > 0; N = sc.nextInt()) {
			int A = sc.nextInt();
			int B = sc.nextInt();
			int C = sc.nextInt();
			int X = sc.nextInt();
			int[] Y = new int[N];
			for(int i = 0; i < N; i++)
				Y[i] = sc.nextInt();

			int res = 0;
			for(int i = 0; i < N; i++, res++, X = (A * X + B) % C)
				for(; X != Y[i] && res <= 10000; res++, X = (A * X + B) % C);
			if(res > 10001)
				System.out.println(-1);
			else
				System.out.println(res - 1);
		}
	}


}
