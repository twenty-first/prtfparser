package it.twenfir.prtfparser.ast;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class RecordKeywords extends Keywords {

	public RecordKeywords(Location location, Integer skipAfter, Integer skipBefore, Integer spaceAfter, Integer spaceBefore) {
		super(location, skipAfter, skipBefore, spaceAfter, spaceBefore);
	}

	public Pagseg getPagseg() {
		return getChild(Pagseg.class);
	}
	
	public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof PrtfVisitor ) {
			return ((PrtfVisitor<? extends ValueT>) visitor).visitRecordKeywords(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
