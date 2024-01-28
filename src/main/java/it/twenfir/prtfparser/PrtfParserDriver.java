package it.twenfir.prtfparser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.twenfir.antlr.api.ErrorListener;
import it.twenfir.antlr.exception.ParseException;
import it.twenfir.antlr.parser.LoggingTokenSource;
import it.twenfir.antlr.parser.ParserDriverBase;
import it.twenfir.prtfparser.PrtfParser.PrtfContext;
import it.twenfir.prtfparser.ast.Prtf;

public class PrtfParserDriver extends ParserDriverBase {

	private static Logger log = LoggerFactory.getLogger(PrtfParserDriver.class);
	
	private CommonTokenStream tokenStream;
	private PrtfParser parser;
	private ErrorListener listener;
	private PrtfContext parseTree;
	
	public PrtfParserDriver(String prtfSource) {
		this(prtfSource, "input", null);
	}

	public PrtfParserDriver(String prtfSource, String fileName) {
		this(prtfSource, fileName, null);
	}

	public PrtfParserDriver(String prtfSource, String fileName, ErrorListener listener) {
		super("prtfparser", fileName, false, log);
		this.listener = listener != null ? listener : this;
		CodePointCharStream inputStream = CharStreams.fromString(prtfSource, fileName);
        PrtfLexer lexer = new PrtfLexer(inputStream);
    	lexer.removeErrorListeners();
    	lexer.addErrorListener(this.listener);
        LoggingTokenSource source = new LoggingTokenSource(lexer);
        tokenStream = new CommonTokenStream(source);
        parser = new PrtfParser(tokenStream);
    	parser.removeErrorListeners();
    	parser.addErrorListener(this.listener);
	}
	
    public PrtfContext parse() {
		if ( parseTree == null ) {
			parseTree = parser.prtf();
		}
		if ( listener.isErrors() ) {
			throw new ParseException("Parse failed");
		}
		return parseTree;
    }

    public Prtf makeAst() {
        PrtfContext tree = parse();
        AstBuilder builder = new AstBuilder(listener);
		Prtf prtf = builder.visitPrtf(tree);
		return prtf;
    }
    
    public TokenStream getTokenStream() {
    	return tokenStream;
    }
}
