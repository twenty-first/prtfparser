package it.twenfir.prtfparser;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.twenfir.antlr.exception.ParseException;

public class ParserUnitTests extends TestBase {

    private static Logger log = LoggerFactory.getLogger(ParserUnitTests.class);

    protected ParserUnitTests() {
        super(log);
    }

	@Test
	public void minimalTest() throws ParseException {
		String src = 
				"     A          R TESTPRTF\n" + 
				"     A            STRING        10";

		helper.parse(src);
	}

	@Test
	public void simpleConditionTest() throws ParseException {
		String src = 
				"     A          R TESTPRTF                  SKIPB(3)\n" +
				"     A  30        FIELD         40        47SPACEA(2) UNDERLINE";
		helper.parse(src);
	}

	@Test
	public void namelessFieldTest() throws ParseException {
		String src = 
				"00010A          R TESTPRTF\n" +
				"00020A                                    66'string'";
		helper.parse(src);
	}

	@Test
	public void complexConditionTest() throws ParseException {
		String src = 
				"     A          R TESTPRTF\n" +
				"     A N15\n" + 
				"     AO 32 33 34                          47TIME";
		helper.parse(src);
	}

}
