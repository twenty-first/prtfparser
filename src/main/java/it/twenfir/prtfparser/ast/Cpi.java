package it.twenfir.prtfparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class Cpi extends AstNode {

	private final int value;
	
	public Cpi(Location location, int value) {
		super(location);
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof PrtfVisitor ) {
			return ((PrtfVisitor<? extends ValueT>) visitor).visitCpi(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
