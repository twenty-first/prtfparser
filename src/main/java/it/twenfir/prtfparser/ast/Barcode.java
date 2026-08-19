package it.twenfir.prtfparser.ast;

import java.math.BigDecimal;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class Barcode extends AstNode {

	private final String name;
	private final BigDecimal height;
	private final BigDecimal width;
	private final BigDecimal ratio;
	
	public Barcode(Location location, String name, BigDecimal height, BigDecimal width, BigDecimal ratio) {
		super(location);
		this.name = name;
		this.height = height;
		this.width = width;
		this.ratio = ratio;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public BigDecimal getWidth() {
		return width;
	}

	public BigDecimal getRatio() {
		return ratio;
	}

	public String getModifier() {
		HexString mod = getChild(HexString.class);
		return mod != null ? mod.getValue() : null;
	}

	public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof PrtfVisitor ) {
			return ((PrtfVisitor<? extends ValueT>) visitor).visitBarcode(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
