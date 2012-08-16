package com.github.hiroshi_cl.wakaba.v2009.sol.d2008.wakaba081221;
//icpc 2004 domestic D
import java.util.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
public class Main {
	public static void main(String[] args) {
		new C().run();
	}
	static void debug(Object... o){
		System.err.println(deepToString(o));
	}
	static class C{
		void run(){
			Scanner sc=new Scanner(System.in);
			for(String input=sc.next(); !input.equals("."); input = sc.next()) {
				int ans = 0;
				//debug(input);
				for(int i=0;i<3;i++) {
					for(int j=0;j<3;j++) {
						for(int k=0;k<3;k++) {
							in = input.replace('P', (char)('0'+i)).replace('Q', (char)('0'+j)).replace('R', (char)('0'+k));
							//debug(in);
							n=0;
							if(eval()==2) ans++;
						}
					}
				}
				System.out.println(ans);
			}
		}
		String in;
		int n;
		char getc() {
			if(n>=in.length()) return '#';
			char r = in.charAt(n);
			n++;
			//debug(r);
			return r;
		}
		int eval() {
			char c = getc();
			if (c=='(') {
				int l = eval();
				c =getc();
				if (c=='+') { 
					int r=eval();
					getc();
					if (r==0&&l==0) return 0;
					if (r==2||l==2) return 2;
					return 1;
				}
				if (c=='*') {
					int r=eval();
					getc();
					if (r==0||l==0) return 0;
					if (r==2&&l==2) return 2;
					return 1;
				}
			}
			else if (c=='-') {
				int r=eval();
				return 2-r;
			}
			return (int)(c-'0');
		}
	}
}
