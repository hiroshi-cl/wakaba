package com.github.hiroshi_cl.wakaba.v2010.sol.srm;

import java.util.*;
import java.util.regex.*;
import java.text.*;
import java.math.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.math.BigInteger.*;

public class SyllableSorting {

	public String[] sortWords(String[] words) {
		int n = words.length;
		Word[] ws = new Word[n];
		for (int i = 0; i < n; i++)
			ws[i] = new Word(words[i]);

		sort(ws);

		String[] ret = new String[n];
		for (int i = 0; i < n; i++)
			ret[i] = ws[i].word;

		return ret;
	}

	class Word implements Comparable<Word> {
		String word;
		String[] syllables;

		public Word(String w) {
			word = w;
			syllables = decompose(word);
			sort(syllables);
		}

		String[] decompose(String s) {
			String[] con = s.split("[aeiou]+");
			String[] vow = s.split("[^aeiou]+");
			String[] ret = new String[con.length];
			for (int i = 0; i < con.length; i++)
				ret[i] = con[i] + vow[i + 1];
			return ret;
		}

		public int compareTo(Word o) {
			for (int i = 0; i < min(syllables.length, o.syllables.length); i++)
				if (syllables[i].compareTo(o.syllables[i]) != 0)
					return syllables[i].compareTo(o.syllables[i]);
			if (syllables.length != o.syllables.length)
				return syllables.length - o.syllables.length;
			return word.compareTo(o.word);
		}
	}
}