package it.twenfir.prtfparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class Default extends AstNode {

	public Default(Location location) {
		super(location);
	}

	public Description getDescription() {
		return getChild(Description.class);

	}

    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof PrtfVisitor ) {
			return ((PrtfVisitor<? extends ValueT>) visitor).visitDefault(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
