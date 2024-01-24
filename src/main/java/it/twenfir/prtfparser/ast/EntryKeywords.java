package it.twenfir.prtfparser.ast;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class EntryKeywords extends Keywords {

	public EntryKeywords(Location location, Integer skipAfter, Integer skipBefore, Integer spaceAfter, Integer spaceBefore) {
		super(location, skipAfter, skipBefore, spaceAfter, spaceBefore);
	}

	public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof PrtfVisitor ) {
			return ((PrtfVisitor<? extends ValueT>) visitor).visitEntryKeywords(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
