package com.github.hiroshi_cl.wakaba.v2009.sol.d1998;
import java.util.*;
//import .*;
import java.math.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.math.BigInteger.*;

public class B {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int o=sc.nextInt();
		sc.nextLine();
		while(o-->0){
			sb=new StringBuffer(sc.nextLine());
//			debug(sb);
			int oo=sc.nextInt();
			int cur=0;
			int l=sb.length();

			while(oo-->0){
//				debug(cur);
//				debug(sb.insert(cur, '^'));
//				sb.delete(cur, cur+1);

				String t=sc.next();
				int n=sb.length();
				if(t.equals("forward")){
					if(sc.next().equals("word")){
						int bef=cur;
						cur = next(cur);
						if(cur==bef){
							cur=n;
						}
					}else{
						cur=min(n,cur+1);
					}
					sc.nextLine();
				}
				else if(t.equals("backward")){
					if(sc.next().equals("word")){
						int bef=cur;
						cur=before(cur);
						if(cur==bef)cur=0;
					}else{
						cur=max(0,cur-1);
					}
					sc.nextLine();
				}
				else if(t.equals("insert")){
					String ins=sc.nextLine();
					sb.insert(cur, ins.subSequence(2, ins.length()-1));
					cur+=ins.length()-3;
				}
				else {
					if(sc.next().equals("word")){
						int from=cur,to=next(cur);
						sb.delete(from, to);
					}else{
						if(cur<n)
							sb.delete(cur, cur+1);
					}
					sc.nextLine();
				}
			}
			System.out.println(sb.insert(cur, '^'));
		}

	}
	static StringBuffer sb;
	static int next(int s){
		int n=sb.length();
		int i=s;
		while(i<n&&sb.charAt(i)==' ')i++;
		if(i==n)return s;
		while(i<n&&sb.charAt(i)!=' ')i++;
		return i;
	}
	static int before(int s){
		int i=s;
		while(i>0 && sb.charAt(i-1)==' ')i--;
		if(i==0)return s;
		while(i>0 && sb.charAt(i-1)!=' ')i--;
		return i;
	}
	private static void debug(Object...os) {
		System.err.println(deepToString(os));
	}
}
