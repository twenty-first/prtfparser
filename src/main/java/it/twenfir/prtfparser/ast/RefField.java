package it.twenfir.prtfparser.ast;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;
import it.twenfir.parser.ast.CommonRefField;

public class RefField extends CommonRefField {

	private String format;
	
	public RefField(Location location, String format, String name, String library, String file) {
		super(location, name, library, file);
		this.format = format;
	}

	public String getFormat() {
		return format;
	}

    public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof PrtfVisitor ) {
			return ((PrtfVisitor<? extends ValueT>) visitor).visitRefField(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
