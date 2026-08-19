package it.twenfir.prtfparser.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.twenfir.antlr.ast.Location;

public abstract class Entry extends KeywordNode {

	private List<Condition> conditions = new ArrayList<Condition>();
	
	public Entry(Location location) {
		super(location);
	}

	public Iterator<Condition> getConditions() {
		return conditions.iterator();
	}

	public void setConditions(Iterator<Condition> condIter) {
		while ( condIter.hasNext() ) {
			conditions.add(condIter.next());
		}
	}

	public it.twenfir.prtfparser.ast.Location getLocation() {
		return getChild(it.twenfir.prtfparser.ast.Location.class);
	}
	
	@Override
	public EntryKeywords getKeywords() {
		return getChild(EntryKeywords.class);
	}
    
	public Barcode getBarcode() {
		EntryKeywords k = getKeywords();
		return k != null ? k.getBarcode() : null;		
	}

	public Integer getCpi() {
		EntryKeywords k = getKeywords();
		return k != null ? k.getCpi() : null;
	}
	
	public Date getDate() {
		EntryKeywords k = getKeywords();
		return k != null ? k.getDate() : null;
	}
	
	public String getDateFormat() {
		EntryKeywords k = getKeywords();
		return k != null ? k.getDateFormat() : null;
	}
	
	public String getDefault() {
		EntryKeywords k = getKeywords();
		return k != null ? k.getDefault() : null;
	}
    
	public String getEditCode() {
		EntryKeywords k = getKeywords();
		return k != null ? k.getEditCode() : null;
	}
    
	public String getEditWord() {
		EntryKeywords k = getKeywords();
		return k != null ? k.getEditWord() : null;
	}
	
	public Font getFont() {
		EntryKeywords k = getKeywords();
		return k != null ? k.getFont() : null;
	}

	public boolean isPageNumber() {
		EntryKeywords k = getKeywords();
		return k != null && k.isPageNumber();
	}
	
	public RefField getRefField() {
		EntryKeywords k = getKeywords();
		return k != null ? k.getRefField() : null;
	}

	public boolean isChrid() {
		EntryKeywords k = getKeywords();
		return k != null && k.isChrid();
	}

	public boolean isTime() {
		EntryKeywords k = getKeywords();
		return k != null && k.isTime();
	}

	public boolean isUnderline() {
		EntryKeywords k = getKeywords();
		return k != null && k.isUnderline();
	}

}
