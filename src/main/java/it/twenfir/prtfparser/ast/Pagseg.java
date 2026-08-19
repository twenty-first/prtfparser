package it.twenfir.prtfparser.ast;

import java.math.BigDecimal;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class Pagseg extends AstNode {

	private final String library;
	private final BigDecimal down;
	private final BigDecimal across;
	
	public Pagseg(Location location, String library, BigDecimal down, BigDecimal across) {
		super(location);
		this.library = library;
		this.down = down;
		this.across = across;
	}

	public String getLibrary() {
		return library;
	}
	
	public BigDecimal getDown() {
		return down;
	}
	
	public BigDecimal getAcross() {
		return across;
	}
	
	public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof PrtfVisitor ) {
			return ((PrtfVisitor<? extends ValueT>) visitor).visitPagseg(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
