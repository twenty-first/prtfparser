package it.twenfir.prtfparser.ast;

import java.util.Iterator;

import it.twenfir.antlr.ast.AstVisitor;
import it.twenfir.antlr.ast.Location;

public class Record extends KeywordNode {

	private final String name;
	
	public Record(Location location, String name) {
		super(location);
		this.name = name;
	}
	
    public String getName() {
		return name;
	}

    @Override
    public RecordKeywords getKeywords() {
    	return getChild(RecordKeywords.class);
    }

    public Pagseg getPagseg() {
    	return getChild(Pagseg.class);
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
