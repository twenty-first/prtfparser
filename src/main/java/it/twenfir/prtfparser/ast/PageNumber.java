package it.twenfir.prtfparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class PageNumber extends AstNode {

	public PageNumber(Location location) {
		super(location);
	}

	public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof PrtfVisitor ) {
			return ((PrtfVisitor<? extends ValueT>) visitor).visitPageNumber(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
