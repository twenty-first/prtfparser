package it.twenfir.prtfparser.ast;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class EntryKeywords extends Keywords {

	public EntryKeywords(Location location, Integer skipAfter, Integer skipBefore, Integer spaceAfter, Integer spaceBefore) {
		super(location, skipAfter, skipBefore, spaceAfter, spaceBefore);
	}

	public boolean isDate() {
		return getChild(Date.class) != null;
	}
	
	public String getDefault() {
		Default t = getChild(Default.class);
		return t != null ? t.getDescription().getDescription() : null;
	}

	public String getEditCode() {
		EditCode ec = getChild(EditCode.class);
		return ec != null ? ec.getEditCode() : null;
	}
	
	public String getEditWord() {
		EditWord ew = getChild(EditWord.class);
		return ew != null ? ew.getDescription().getDescription() : null;
	}
	
	public boolean isHighlight() {
		return getChild(Highlight.class) != null;
	}
	
	public boolean isPageNumber() {
		return getChild(PageNumber.class) != null;
	}
	
	public RefField getRefField() {
		return getChild(RefField.class);
	}
	
	public boolean isTime() {
		return getChild(Time.class) != null;
	}
	
	public boolean isUnderline() {
		return getChild(Underline.class) != null;
	}

	public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof PrtfVisitor ) {
			return ((PrtfVisitor<? extends ValueT>) visitor).visitEntryKeywords(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
