package it.twenfir.prtfparser.ast;

import java.math.BigDecimal;

public class LocValue {

	private final boolean increment;
	private final BigDecimal value;

	public LocValue(boolean increment, BigDecimal value) {
		this.increment = increment;
		this.value = value;
	}

	public boolean isIncrement() {
		return increment;
	}

	public BigDecimal getValue() {
		return value;
	}

}
