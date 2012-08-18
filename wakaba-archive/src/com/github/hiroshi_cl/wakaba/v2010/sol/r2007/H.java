package com.github.hiroshi_cl.wakaba.v2010.sol.r2007;
import java.util.*;

import static java.util.Arrays.*;
import static java.lang.Math.*;
import static java.util.Collections.*;
public class H {
	public static void main(String[] args) {
		new H().run();
	}
	static void debug(Object... o){
		System.err.println(deepToString(o));
	}
	void run(){
		Scanner sc=new Scanner(System.in);
		for(;;){
			String[] ss=new String[1001];
			int n=0;
			for(n=0;;n++){
				ss[n]=sc.next();
				if(ss[n].equals("."))break;
			}
			if(n==0)return;
			Program pr=new Program();
			int i=0;
			for(i=0;i<n;i++){
				if(!pr.run(ss[i])){
					System.out.println(i+1);
					break;
				}
			}
			if(i==n){
				System.out.println(0);
			}
		}
	}
	class Program{
		int[] size=new int[26*2];
		int[][] ids=new int[26*2][1000];
		int[] maxids=new int[26*2];     //定義済みidの個数
		int[][] vals=new int[26*2][1000];
		Program(){
			fill(size,-1);
		}
		boolean run(final String s){
			//                System.err.println();
			//                System.err.println("run("+s+")");
			String[] ss=s.split("=");
			if(ss.length==1){
				size[name(ss[0])] = Integer.valueOf(ss[0].substring(2,s.length()-1));
				//                    System.err.println("setsize "+name(ss[0])+" -> " + size[name(ss[0])]);
				return true;
			}
			else{
				int right=-1;

				int[] names=new int[2];
				names[0]=name(ss[0]);
				int[] t_ids=new int[2];
				t_ids[0]=calcId(ss[0]);
				if(isNum(ss[1].charAt(0))){
					right=Integer.valueOf(ss[1]);
				}
				else{
					names[1]=name(ss[1]);
					t_ids[1]=calcId(ss[1]);
					if(t_ids[1]<0)return false;
					right=calcVal(names[1], t_ids[1]);
					if(right<0)return false;
					if(size[names[1]]<0){
						//                            debug(names[1]+"is not valid");
						return false;
					}
				}
				return setVal(names[0],t_ids[0],right);
			}
		}
		boolean setVal(int name,int id,int val){ //idが範囲を超えていたら、false を返す。
			if(size[name]<0){
				//                    System.err.println(name+" is not defined");
				return false;
			}
			if(id>=size[name]){
				//                    System.err.println(name+"["+id+"]'s index is too big");
				return false;
			}
			for(int i=0;i<maxids[name];i++){
				if(ids[name][i]==id){
					vals[name][i]=val;
					//                        debug(name+"[" +id+"] = "+val);
					//                        debug(vals[name],ids[name],maxids[name]);
					return true;
				}
			}
			//                debug(name+"[" +id+"] = "+val);
			ids[name][maxids[name]] = id;
			vals[name][maxids[name]] = val;
			maxids[name]++;
			//                debug(vals[name]);debug(ids[name]);debug(maxids[name]);
			return true;
		}
		int calcVal(int name,int id){ //バグってたら、-1を返す。
			if(id>=size[name]){
				//                    System.err.println(name+"["+id+"] is index of arrays error");
				return -1;
			}
			for(int i=0;i<maxids[name];i++){
				if(ids[name][i]==id){
					return vals[name][i];
				}
			}
			//                System.err.println(name+"["+id+"] is not defined");
			return -1;
		}
		int calcId(String s){ //バグってたら、-1を返す。返す値のチェックのみ、行わない。
			//                System.err.println("calculating "+s);
			String t=s.substring(2,s.length()-1);
			//                debug(t);
			if(isNum(t.charAt(0))){
				//                    debug(s+"'s id="+Integer.valueOf(t));
				return Integer.valueOf(t);
			}
			int name=name(t);
			int id=calcId(t);
			//                System.err.println("calcVal("+name+","+id+")="+calcVal(name,id));
			if(id<0)return -1;
			return calcVal(name,id);
		}
		int name(String s){
			return name2int(s.charAt(0));
		}
		int name2int(char c){
			if(Character.isLowerCase(c)){
				return c-'a';
			}
			else{
				return c-'A'+26;
			}
		}
	}
	boolean isNum(char c){
		return '0'<=c && c<='9';
	}
}

