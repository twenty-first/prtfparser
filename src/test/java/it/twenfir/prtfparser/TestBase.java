package it.twenfir.prtfparser;

import org.slf4j.Logger;

public class TestBase {

	protected Helper helper;
	
	protected TestBase(Logger log) {
		this.helper = new Helper(log);
	}

	protected PrtfParser parser(String src) {
		return helper.driver(src).getParser();
	}

}
