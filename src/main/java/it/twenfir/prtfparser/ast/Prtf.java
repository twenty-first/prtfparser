package it.twenfir.prtfparser.ast;

import java.util.Iterator;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;
import it.twenfir.parser.ast.CommonDds;

public class Prtf extends AstNode implements CommonDds<Field> {
	
    public Prtf(Location location) {
        super(location);
    }
	
    public FileKeywords getKeywords() {
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
    
	public boolean isRelPos() {
		FileKeywords fk = getKeywords();
		return fk != null && fk.isRelPos();
	}
	
	public Iterator<Record> getRecords() {
		return getChildren(Record.class);
	}

	@Override
	public Iterator<Field> getFields() {
		return getDescendants(Field.class);
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
