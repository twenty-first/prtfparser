package it.twenfir.prtfparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class Record extends AstNode {

	private final String name;
	private final Integer skipa;
	private final Integer skipb;
	private final Integer spacea;
	private final Integer spaceb;
	
	public Record(Location location, String name, Integer skipa, Integer skipb, Integer spacea, Integer spaceb) {
		super(location);
		this.name = name;
		this.skipa = skipa;
		this.skipb = skipb;
		this.spacea = spacea;
		this.spaceb = spaceb;
	}
	
    public String getName() {
		return name;
	}

	public Integer getSkipa() {
		return skipa;
	}

	public Integer getSkipb() {
		return skipb;
	}

	public Integer getSpacea() {
		return spacea;
	}

	public Integer getSpaceb() {
		return spaceb;
	}

	public String getText() {
		Text t = getChild(Text.class);
		return t != null ? t.getDescription().getDescription() : null;
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
