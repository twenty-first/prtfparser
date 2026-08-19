package it.twenfir.prtfparser.ast;

import java.util.Iterator;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class HexString extends AstNode {

	private String value;
	
	public HexString(Location location) {
		super(location);
	}
	
	public String getValue() {
		if ( value == null ) {
			StringBuilder sb = new StringBuilder();
			getHexStringElements().forEachRemaining((de) -> { sb.append(de.getValue()); });
			value = sb.toString();
		}
		return value;
	}
	
	public Iterator<HexStringElement> getHexStringElements() {
		return getChildren(HexStringElement.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof PrtfVisitor ) {
			return ((PrtfVisitor<? extends ValueT>) visitor).visitHexString(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
