package it.twenfir.prtfparser.ast;

import java.util.Iterator;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class Prtf extends AstNode {

	private final boolean indara;
	
    public Prtf(Location location, boolean indara) {
        super(location);
        this.indara = indara;
    }
	
	public boolean isIndara() {
		return indara;
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
