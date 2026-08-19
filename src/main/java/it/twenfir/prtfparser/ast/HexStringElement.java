package it.twenfir.prtfparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class HexStringElement extends AstNode {

	private String value;
	
	public HexStringElement(Location location, String value) {
		super(location);
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof PrtfVisitor ) {
			return ((PrtfVisitor<? extends ValueT>) visitor).visitHexStringElement(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
