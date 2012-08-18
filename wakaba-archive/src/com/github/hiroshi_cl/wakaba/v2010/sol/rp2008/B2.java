package com.github.hiroshi_cl.wakaba.v2010.sol.rp2008;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;

public class B2 {
	void debug(Object... os) {
		System.err.println(deepToString(os));
	}
	void run() {
		Scanner sc = new Scanner(System.in);
		int n=sc.nextInt(),m=sc.nextInt();
		n%=m;
		HashMap<Integer, Integer> map=new HashMap<Integer, Integer>();
		for(int i=0;;i++){
//			debug(n);
			map.put(n,i);
			n*=10;
			n%=m;
			if(map.containsKey(n)){
				debug(n);
				if(n==0){
					System.out.println(map.get(n)+" "+(0));
					break;
				}
				System.out.println(map.get(n)+" "+(i-map.get(n)+1));
				break;
			}
		}
	}
	public static void main(String[] args) {
		new B2().run();
	}
}
