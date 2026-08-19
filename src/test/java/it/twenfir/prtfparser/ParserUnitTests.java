package it.twenfir.prtfparser;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.twenfir.antlr.exception.ParseException;
import it.twenfir.prtfparser.PrtfParser.DataTypeContext;
import it.twenfir.prtfparser.PrtfParser.PrtfContext;

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

	@Test
	public void fileKeywordTest() throws ParseException {
		String src =
				"     A                                      REF(FILE)\n" + 
				"     A                                      INDARA\n" +
				"     A          R TESTPRTF";
		helper.parse(src);
	}

//     A                                      TEXT('Etichetta')
//     A                                      SKIPB(001)
//     A                                     1
//     A                                      'N'

	@Test
	public void recordKeywordTest() throws ParseException {
		String src =
				"     A          R TESTPRTF\n" +
				"     A                                      TEXT('Text')\n" +
				"     A                                      SKIPB(001)\n" +
				"     A                                     1\n" +
				"     A                                      'N'";
		helper.parse(src);
	}
	
	@Test
	public void testStringRecognizedAsPacked() {
		String src =
				"     A          R TESTPRTF\n" +
				"     A            FLGSTP         2         1";
		PrtfContext pc = helper.parse(src);
		DataTypeContext dt = pc.record(0).entry(0).field().dataType();
		assertNull(dt.precision());
	}
}
