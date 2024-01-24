package it.twenfir.prtfparser.ast;

public class LocValue {

	private final boolean increment;
	private final Integer value;

	public LocValue(boolean increment, Integer value) {
		this.increment = increment;
		this.value = value;
	}

	public boolean isIncrement() {
		return increment;
	}

	public Integer getValue() {
		return value;
	}

}
