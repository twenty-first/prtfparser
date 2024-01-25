package it.twenfir.prtfparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class Date extends AstNode {

	private final boolean job;
	private final int digits;
	
	public Date(Location location, boolean job, int digits) {
		super(location);
		this.job = job;
		this.digits = digits;
	}
	
	public boolean isJob() {
		return job;
	}

	public int getDigits() {
		return digits;
	}

	public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof PrtfVisitor ) {
			return ((PrtfVisitor<? extends ValueT>) visitor).visitDate(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
