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

}
