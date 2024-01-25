package it.twenfir.prtfparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class Dft extends AstNode {

	public Dft(Location location) {
		super(location);
	}

	public Description getDescription() {
		return getChild(Description.class);

	}

    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof PrtfVisitor ) {
			return ((PrtfVisitor<? extends ValueT>) visitor).visitDft(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
