package com.github.hiroshi_cl.wakaba.v2010.sol.sgu;
import java.util.*;
import java.math.*;

import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.math.BigInteger.*;

public class D20100613D {
	public static void main(String[] args) {
		new D20100613D().run();
	}
	void debug(Object...os){
		System.err.println(os);
	}
	void run() {
		Scanner sc = new Scanner(System.in);
		int n=sc.nextInt();
		BigInteger[] is=new BigInteger[n];
		for (int i = 0; i < n; i++) {
			is[i]=sc.nextBigInteger();
		}
		sort(is);
		boolean f = false;
		loop:for (int i = 0; i < n-2; i++) {
			if(is[i].add(is[i+1]).compareTo(is[i+2]) > 0){
				f=true;
				System.out.printf("%s %s %s\n",is[i].toString(),is[i+1].toString(),is[i+2].toString());
				break;
			}
		}
		if(!f){
			System.out.printf("%d %d %d\n",0,0,0);
		}
	}
}
