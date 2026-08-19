package it.twenfir.prtfparser.ast;

import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.Location;

public abstract class KeywordNode extends AstNode {

	public KeywordNode(Location location) {
		super(location);
	}

	public abstract Keywords getKeywords();
	
	public boolean isHighlight() {
		Keywords k = getKeywords();
		return k != null && k.isHighlight();
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
