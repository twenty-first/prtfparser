package it.twenfir.prtfparser.ast;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class Field extends Entry {

	private final String name;
	private final boolean reference;
	private final Usage usage;
	
	public Field(Location location, String name, boolean reference, Usage usage) {
		super(location);
		this.name = name;
		this.reference = reference;
		this.usage = usage;
	}

	public String getName() {
		return name;
	}

	public boolean isReference() {
		return reference;
	}

	public Usage getUsage() {
		return usage;
	}

	public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof PrtfVisitor ) {
			return ((PrtfVisitor<? extends ValueT>) visitor).visitField(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
