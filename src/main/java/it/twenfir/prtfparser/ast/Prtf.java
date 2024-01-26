package it.twenfir.prtfparser.ast;

import java.util.Iterator;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class Prtf extends AstNode {
	
    public Prtf(Location location) {
        super(location);
    }
	
    private FileKeywords getKeywords() {
    	return getChild(FileKeywords.class);
    }
    
	public boolean isIndArea() {
		FileKeywords fk = getKeywords();
		return fk != null && fk.isIndArea();
	}

	public Ref getRef() {
		FileKeywords fk = getKeywords();
		return fk != null ? fk.getRef() : null;
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
