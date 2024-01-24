package it.twenfir.prtfparser.ast;

import java.util.Iterator;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class Record extends AstNode {

	private final String name;
	
	public Record(Location location, String name) {
		super(location);
		this.name = name;
	}
	
    public String getName() {
		return name;
	}

    private RecordKeywords getKeywords() {
    	return getChild(RecordKeywords.class);
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
		Text t = getChild(Text.class);
		return t != null ? t.getDescription().getDescription() : null;
	}

	public Iterator<Entry> getEntries() {
		return getChildren(Entry.class);
	}
	
	public <ValueT> ValueT accept(AstVisitor<? extends ValueT> visitor) {
		if ( visitor instanceof PrtfVisitor ) {
			return ((PrtfVisitor<? extends ValueT>) visitor).visitRecord(this);
    	}
    	else {
    		return visitor.visit(this);
    	}
    }

}
