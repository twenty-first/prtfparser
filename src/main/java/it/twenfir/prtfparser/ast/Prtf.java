package it.twenfir.prtfparser.ast;

import java.util.Iterator;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class Prtf extends AstNode {

	private final boolean indArea;
	
    public Prtf(Location location, boolean indArea) {
        super(location);
        this.indArea = indArea;
    }
	
	public boolean isIndArea() {
		return indArea;
	}

	public Ref getRef() {
		return getChild(Ref.class);
	}
	
	public Iterator<Record> getRecords() {
		return getChildren(Record.class);
	}
	
    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof PrtfVisitor ) {
			return ((PrtfVisitor<? extends ValueT>) visitor).visitPrtf(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
