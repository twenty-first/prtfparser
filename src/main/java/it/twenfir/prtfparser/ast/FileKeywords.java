package it.twenfir.prtfparser.ast;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class FileKeywords extends Keywords {

	private final boolean indArea;
	private final boolean relPos;

	public FileKeywords(Location location, boolean indArea, boolean relPos) {
		super(location, null, null, null, null);
        this.indArea = indArea;
        this.relPos = relPos;
	}
	
	public boolean isIndArea() {
		return indArea;
	}

	public Ref getRef() {
		return getChild(Ref.class);
	}

	public boolean isRelPos() {
		return relPos;
	}

	public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof PrtfVisitor ) {
			return ((PrtfVisitor<? extends ValueT>) visitor).visitFileKeywords(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
