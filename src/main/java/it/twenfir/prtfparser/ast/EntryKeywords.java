package it.twenfir.prtfparser.ast;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class EntryKeywords extends Keywords {

	private final String dateFormat;
	
	public EntryKeywords(Location location, String dateFormat, Integer skipAfter, 
			Integer skipBefore, Integer spaceAfter, Integer spaceBefore) {
		super(location, skipAfter, skipBefore, spaceAfter, spaceBefore);
		this.dateFormat = dateFormat;
	}

	public Barcode getBarcode() {
		return getChild(Barcode.class);
	}
	
	public boolean isChrid() {
		return getChild(Chrid.class) != null;
	}
	
	public Integer getCpi() {
		Cpi c = getChild(Cpi.class);
		return c != null ? c.getValue() : null;
	}
	
	public Date getDate() {
		return getChild(Date.class);
	}
	
	public String getDateFormat() {
		return dateFormat;
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
	
	public Font getFont() {
		return getChild(Font.class);
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
