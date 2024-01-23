package it.twenfir.prtfparser.ast;

import java.util.Iterator;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class Record extends AstNode {

	private final String name;
	private final Integer skipAfter;
	private final Integer skipBefore;
	private final Integer spaceAfter;
	private final Integer spaceBefore;
	
	public Record(Location location, String name, Integer skipAfter, Integer skipBefore, Integer spaceAfter, Integer spaceBefore) {
		super(location);
		this.name = name;
		this.skipAfter = skipAfter;
		this.skipBefore = skipBefore;
		this.spaceAfter = spaceAfter;
		this.spaceBefore = spaceBefore;
	}
	
    public String getName() {
		return name;
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

	public String getText() {
		Text t = getChild(Text.class);
		return t != null ? t.getDescription().getDescription() : null;
	}

	public Iterator<Entry> getEntries() {
		return getChildren(Entry.class);
	}
	
	public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof PrtfVisitor ) {
			return ((PrtfVisitor<? extends ValueT>) visitor).visitRecord(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
