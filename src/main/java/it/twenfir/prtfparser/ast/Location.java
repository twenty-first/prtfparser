package it.twenfir.prtfparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;

public class Location extends AstNode {

	private final LocValue line;
	private final LocValue pos;
	
	public Location(it.twenfir.antlr.ast.Location location, LocValue line, LocValue pos) {
		super(location);
		this.line = line;
		this.pos = pos;
	}

	public LocValue getLocLine() {
		return line;
	}

	public LocValue getLocPos() {
		return pos;
	}

	public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof PrtfVisitor ) {
			return ((PrtfVisitor<? extends ValueT>) visitor).visitLocation(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
