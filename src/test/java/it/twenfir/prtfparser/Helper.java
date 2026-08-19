package it.twenfir.prtfparser;

import org.slf4j.Logger;

import it.twenfir.prtfparser.PrtfParser.PrtfContext;
import it.twenfir.prtfparser.ast.Prtf;

public class Helper {

	Logger log;
	
	public Helper(Logger log) {
		this.log = log;
	}
	
	public PrtfParserDriver driver(String src) {
		return new PrtfParserDriver(src);
	}
	
	public PrtfContext parse(String src) {
		PrtfParserDriver d = driver(src);
		PrtfContext dc = d.parse();
		return dc;
	}

	public Prtf ast(String src) {
		PrtfParserDriver d = driver(src);
		Prtf prtf = d.makeAst();
		return prtf;
	}
}
