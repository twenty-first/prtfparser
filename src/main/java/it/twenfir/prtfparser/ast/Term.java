package it.twenfir.prtfparser.ast;

import java.util.Iterator;
import java.util.List;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class Term extends AstNode {

	private final List<Indicator> indicators;
	
	public Term(Location location, List<Indicator> indicators) {
		super(location);
		this.indicators = indicators;
	}
	
	public Iterator<Indicator> getIndicators() {
		return indicators.iterator();
	}
	
	public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof PrtfVisitor ) {
			return ((PrtfVisitor<? extends ValueT>) visitor).visitTerm(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
