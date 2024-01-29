package it.twenfir.prtfparser.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.Location;

public abstract class Entry extends AstNode {

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
	
	private EntryKeywords getKeywords() {
		return getChild(EntryKeywords.class);
	}
    
	public Integer getCpi() {
		EntryKeywords k = getKeywords();
		return k != null ? k.getCpi() : null;
	}
	
	public boolean isDate() {
		EntryKeywords k = getKeywords();
		return k != null && k.isDate();
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
	
	public boolean isHighlight() {
		EntryKeywords k = getKeywords();
		return k != null && k.isHighlight();
	}

	public boolean isPageNumber() {
		EntryKeywords k = getKeywords();
		return k != null && k.isPageNumber();
	}
	
	public RefField getRefField() {
		EntryKeywords k = getKeywords();
		return k != null ? k.getRefField() : null;
	}

	public Integer getSkipAfter() {
		Keywords k = getKeywords();
		return k != null ? k.getSkipAfter() : null;
	}

	public Integer getSkipBefore() {
		Keywords k = getKeywords();
		return k != null ? k.getSkipBefore() : null;
	}

	public Integer getSpaceAfter() {
		Keywords k = getKeywords();
		return k != null ? k.getSpaceAfter() : null;
	}

	public Integer getSpaceBefore() {
		Keywords k = getKeywords();
		return k != null ? k.getSpaceBefore() : null;
	}

	public String getText() {
		Keywords k = getKeywords();
		return k != null ? k.getText() : null;
	}
	
}
