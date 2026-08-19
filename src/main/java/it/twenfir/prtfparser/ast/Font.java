package it.twenfir.prtfparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class Font extends AstNode {

	private Integer identifier;
	private Integer pointsize;
	
	public Font(Location location, Integer identifier, Integer pointsize) {
		super(location);
		this.identifier = identifier;
		this.pointsize = pointsize;
	}
	
	public Integer getIdentifier() {
		return identifier;
	}

	public Integer getPointsize() {
		return pointsize;
	}

	public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof PrtfVisitor ) {
			return ((PrtfVisitor<? extends ValueT>) visitor).visitFont(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
