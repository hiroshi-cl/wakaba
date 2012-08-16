package com.github.hiroshi_cl.wakaba.v2009.sol.d2009.c;
import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;
import static java.math.BigInteger.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
class Main {
	void debug(Object... o) {
		System.err.println(deepToString(o));
	}
	void run() {
		Scanner sc = new Scanner(System.in);
		try {
			sc = new Scanner(new File("C"));
			System.setOut(new PrintStream("C.out"));
		} catch (Exception e) {}
		for(;;){
			n=sc.nextInt();
			if(n==0)return;
			cs=new char[n][];
			map=new HashMap<Character, Integer>();
//			HashSet<E>
			for(int i=0,j=0;i<n;i++){
				cs[i]=sc.next().toCharArray();
				for(char c:cs[i]){
					if(map.containsKey(c));
					else map.put(c,j++);
				}
			}
			int[] is=new int[10];
			for(int i=0;i<10;i++)is[i]=i;
			int res=0;
			int m=map.size();
			do{
//				int[] js=new int[map.size()];
				if(ok(is,cs,m))res++;
			}while(next_permutaion(is));
			for(int i=0;i<10-m;i++){
				res/=i+1;
			}
			System.out.println(res);
		}
	}
	HashMap<Character, Integer> map;
	int n;
	boolean ok(int[] is,char[][] cs,int m){
		for(int i=0;i<n;i++){
			if(cs[i].length==1)continue;
			if(is[map.get(cs[i][0])] == 0)return false;
		}
		int sum=0;
		for(int i=0;i<n-1;i++){
			sum+=c2i(cs[i],is);
		}
		if(sum==c2i(cs[n-1], is))return true;
		return false;
	}
	int c2i(char[] cs,int[] is){
		int res=0;
		for(int i=0;i<cs.length;i++){
			res*=10;
			res += is[map.get(cs[i])];
		}
		return res;
	}
	boolean next_permutaion(int[] is) {
		int n = is.length;
		for (int i = n - 1; i > 0; i--) {
			if (is[i - 1] < is[i]) {
				int j = n;
				while (is[i - 1] >= is[--j]);
				swap(is, i - 1, j);
				rev(is, i, n);
				return true;
			}
		}
		rev(is, 0, n);
		return false;
	}

	void swap(int[] is, int i, int j) {
		int t = is[i];
		is[i] = is[j];
		is[j] = t;
	}
	void rev(int[] is, int s, int t) {
		while(s<--t)swap(is, s++, t);
	}
	char[][] cs;
	public static void main(String[] args) {
		new Main().run();
	}
}
