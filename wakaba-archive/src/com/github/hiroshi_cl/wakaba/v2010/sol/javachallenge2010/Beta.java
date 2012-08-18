package com.github.hiroshi_cl.wakaba.v2010.sol.javachallenge2010;

import id075.ID075.SpellPos;
import jp.ac.waseda.cs.washi.game.api.*;
import java.util.*;

public class Beta implements Player {
	Direction[] directions;
	PlayerAction[] actions;
	int indexOfDirections = 0;
	int indexOfActions = 0;
	Direction[] fourDirections = Direction.values();
	GameEnvironmentInfo env;

	PlayerAction[] bonusMakeActions = { PlayerAction.LEFT, PlayerAction.LEFT, PlayerAction.CLEAR, PlayerAction.UP,
			PlayerAction.RIGHT, PlayerAction.CLEAR, PlayerAction.UP, PlayerAction.RIGHT, PlayerAction.CLEAR,
			PlayerAction.RIGHT, PlayerAction.DOWN, PlayerAction.CLEAR, PlayerAction.RIGHT, PlayerAction.DOWN,
			PlayerAction.CLEAR, PlayerAction.DOWN, PlayerAction.LEFT, PlayerAction.CLEAR, PlayerAction.DOWN,
			PlayerAction.LEFT, PlayerAction.CLEAR, PlayerAction.LEFT, PlayerAction.UP, PlayerAction.CLEAR, };
	long LIMIT = 100;

	@Override
	public PlayerAction execute(GameEnvironmentInfo env) {
		this.env = env;

		return AI_beta();
	}

	ArrayList<Point> getPPos() {
		MapInfo mi = env.getMapInfo();
		ArrayList<Point> r = new ArrayList<Point>();
		for (int i = 1; i < mi.getWidth() - 1; i++) {
			for (int j = 1; j < mi.getHeight() - 1; j++) {
				if (isWritable(new Point(i, j)) && isWritable(new Point(i, j - 1)) && isWritable(new Point(i, j + 1))
						&& isWritable(new Point(i - 1, j)) && isWritable(new Point(i + 1, j))) {
					r.add(new Point(i, j));
				}
			}
		}
		return r;
	}

	boolean isWritable(Point p) {
		MapInfo mi = env.getMapInfo();
		return mi.isMovable(p) && !mi.isLocked(p);
	}

	PlayerAction AI_beta() {
		MapInfo mi = env.getMapInfo();
		Point p = env.getPlayerInfo().getLocation();
		CharaInfo pi = env.getPlayerInfo();
		if (mi.getNeedingSpellCount(env.getPlayerInfo(), p) == 0) {
			SpellPos minSpellPos = null;
			for (int i = 0; i < mi.getWidth(); i++) {
				for (int j = 0; j < mi.getHeight(); j++) {
					Point pp = new Point(i, j);
					if (mi.getNeedingSpellCount(pi, pp) > 0) {
						//						list.add(new SpellPos(pp));
						minSpellPos = min(minSpellPos,new SpellPos(pp));
					}
				}
			}
			//			Collections.sort(list);
			if (minSpellPos == null)
				return PlayerAction.NONE;
			return minSpellPos.ds[0].toPlayerAction();
		} else {
			return PlayerAction.SPELL;
		}
	}

	SpellPos min(SpellPos a, SpellPos b) {
		if (a == null)
			return b;
		if (b == null)
			return a;
		int i = a.compareTo(b);
		if (i < 0)
			return a;
		if (i > 0)
			return b;
		return Math.random() < 0.5 ? a : b;
	}

	class SpellPos implements Comparable<SpellPos> {
		Point p;
		Direction[] ds;
		int spellCount;

		SpellPos(Point p) {
			this.p = p;
			ds = env.getMapInfo().getPath(env.getPlayerInfo().getLocation(), p);
			spellCount = env.getMapInfo().getNeedingSpellCount(env.getPlayerInfo(), p);
		}

		public int compareTo(SpellPos o) {
			CharaInfo pi = env.getPlayerInfo();
			int need = ds.length;
			int needo = o.ds.length;
			return (need + spellCount) - (needo + o.spellCount);
		}
	}

}