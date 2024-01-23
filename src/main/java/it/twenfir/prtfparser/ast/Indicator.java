package it.twenfir.prtfparser.ast;

public class Indicator {

	private final boolean negate;
	private final int index;
	
	public Indicator(boolean negate, int index) {
		this.negate = negate;
		this.index = index;
	}

	public boolean isNegate() {
		return negate;
	}

	public int getIndex() {
		return index;
	}

}
