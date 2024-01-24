package it.twenfir.prtfparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.Location;

public abstract class Keywords extends AstNode {

	private final Integer skipAfter;
	private final Integer skipBefore;
	private final Integer spaceAfter;
	private final Integer spaceBefore;

	public Keywords(Location location, Integer skipAfter, Integer skipBefore, Integer spaceAfter, Integer spaceBefore) {
		super(location);
		this.skipAfter = skipAfter;
		this.skipBefore = skipBefore;
		this.spaceAfter = spaceAfter;
		this.spaceBefore = spaceBefore;
	}

	public Integer getSkipAfter() {
		return skipAfter;
	}

	public Integer getSkipBefore() {
		return skipBefore;
	}

	public Integer getSpaceAfter() {
		return spaceAfter;
	}

	public Integer getSpaceBefore() {
		return spaceBefore;
	}

}
