package it.twenfir.prtfparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class RefField extends AstNode {

	private String format;
	private String name;
	private String library;
	private String file;
	
	public RefField(Location location, String format, String name, String library, String file) {
		super(location);
		this.format = format;
		this.name = name;
		this.library = library;
		this.file = file;
	}

	public String getFormat() {
		return format;
	}

	public String getName() {
		return name;
	}

	public String getLibrary() {
		return library;
	}

	public String getFile() {
		return file;
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
