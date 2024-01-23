package it.twenfir.prtfparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class OpTerm extends AstNode {

	private CondOp operator;
	
	public OpTerm(Location location, CondOp operator) {
		super(location);
		this.operator = operator;
	}

	CondOp getOperator() {
		return operator;
	}
	
	Term getTerm() {
		return getChild(Term.class);
	}
	
	public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof PrtfVisitor ) {
			return ((PrtfVisitor<? extends ValueT>) visitor).visitOpTerm(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
