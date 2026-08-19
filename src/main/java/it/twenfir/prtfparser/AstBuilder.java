package it.twenfir.prtfparser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import it.twenfir.antlr.api.ErrorListener;
import it.twenfir.antlr.ast.AstHelper;
import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.Location;
import it.twenfir.antlr.ast.Node;
import it.twenfir.antlr.exception.AstException;
import it.twenfir.antlr.parser.DefaultErrorListener;
import it.twenfir.prtfparser.PrtfParser.BarcodeContext;
import it.twenfir.prtfparser.PrtfParser.ChridContext;
import it.twenfir.prtfparser.PrtfParser.ConditionContext;
import it.twenfir.prtfparser.PrtfParser.CpiContext;
import it.twenfir.prtfparser.PrtfParser.DataTypeContext;
import it.twenfir.prtfparser.PrtfParser.DateContext;
import it.twenfir.prtfparser.PrtfParser.DescriptionContext;
import it.twenfir.prtfparser.PrtfParser.DescriptionElementContext;
import it.twenfir.prtfparser.PrtfParser.DftContext;
import it.twenfir.prtfparser.PrtfParser.EditCodeContext;
import it.twenfir.prtfparser.PrtfParser.EditWordContext;
import it.twenfir.prtfparser.PrtfParser.EntryContext;
import it.twenfir.prtfparser.PrtfParser.EntryKeywordsContext;
import it.twenfir.prtfparser.PrtfParser.FieldContext;
import it.twenfir.prtfparser.PrtfParser.FileKeywordsContext;
import it.twenfir.prtfparser.PrtfParser.FontContext;
import it.twenfir.prtfparser.PrtfParser.HexStringContext;
import it.twenfir.prtfparser.PrtfParser.HexStringElementContext;
import it.twenfir.prtfparser.PrtfParser.HighlightContext;
import it.twenfir.prtfparser.PrtfParser.LabelContext;
import it.twenfir.prtfparser.PrtfParser.LocValueContext;
import it.twenfir.prtfparser.PrtfParser.LocationContext;
import it.twenfir.prtfparser.PrtfParser.NumberContext;
import it.twenfir.prtfparser.PrtfParser.OpTermContext;
import it.twenfir.prtfparser.PrtfParser.PageNumberContext;
import it.twenfir.prtfparser.PrtfParser.PagsegContext;
import it.twenfir.prtfparser.PrtfParser.PrtfContext;
import it.twenfir.prtfparser.PrtfParser.RecordContext;
import it.twenfir.prtfparser.PrtfParser.RecordKeywordsContext;
import it.twenfir.prtfparser.PrtfParser.RefContext;
import it.twenfir.prtfparser.PrtfParser.RefFieldContext;
import it.twenfir.prtfparser.PrtfParser.SkipaContext;
import it.twenfir.prtfparser.PrtfParser.SkipbContext;
import it.twenfir.prtfparser.PrtfParser.SpaceaContext;
import it.twenfir.prtfparser.PrtfParser.SpacebContext;
import it.twenfir.prtfparser.PrtfParser.TermContext;
import it.twenfir.prtfparser.PrtfParser.TextContext;
import it.twenfir.prtfparser.PrtfParser.TimeContext;
import it.twenfir.prtfparser.PrtfParser.UnderlineContext;
import it.twenfir.prtfparser.ast.Barcode;
import it.twenfir.prtfparser.ast.Chrid;
import it.twenfir.prtfparser.ast.CondOp;
import it.twenfir.prtfparser.ast.Condition;
import it.twenfir.prtfparser.ast.Cpi;
import it.twenfir.prtfparser.ast.DataType;
import it.twenfir.prtfparser.ast.Date;
import it.twenfir.prtfparser.ast.Default;
import it.twenfir.prtfparser.ast.Description;
import it.twenfir.prtfparser.ast.DescriptionElement;
import it.twenfir.prtfparser.ast.EditCode;
import it.twenfir.prtfparser.ast.EditWord;
import it.twenfir.prtfparser.ast.Entry;
import it.twenfir.prtfparser.ast.EntryKeywords;
import it.twenfir.prtfparser.ast.Field;
import it.twenfir.prtfparser.ast.FileKeywords;
import it.twenfir.prtfparser.ast.Font;
import it.twenfir.prtfparser.ast.HexString;
import it.twenfir.prtfparser.ast.HexStringElement;
import it.twenfir.prtfparser.ast.Highlight;
import it.twenfir.prtfparser.ast.Indicator;
import it.twenfir.prtfparser.ast.Label;
import it.twenfir.prtfparser.ast.LocValue;
import it.twenfir.prtfparser.ast.OpTerm;
import it.twenfir.prtfparser.ast.PageNumber;
import it.twenfir.prtfparser.ast.Pagseg;
import it.twenfir.prtfparser.ast.Prtf;
import it.twenfir.prtfparser.ast.Record;
import it.twenfir.prtfparser.ast.RecordKeywords;
import it.twenfir.prtfparser.ast.Ref;
import it.twenfir.prtfparser.ast.RefField;
import it.twenfir.prtfparser.ast.Term;
import it.twenfir.prtfparser.ast.Text;
import it.twenfir.prtfparser.ast.Time;
import it.twenfir.prtfparser.ast.Underline;
import it.twenfir.prtfparser.ast.Usage;

public class AstBuilder extends PrtfParserBaseVisitor<AstNode> {

	private Pattern endDescRe = Pattern.compile("\\+|-");
	private Pattern eolRe = Pattern.compile("\\r|\\n");
	
	private ErrorListener listener;

	public AstBuilder(ErrorListener listener) {
		this.listener = listener != null ? listener : new DefaultErrorListener();
	}

	private int findFirstNotZero(String s) {
		int start = 0;
		while ( s.charAt(start) == '0' ) {
			start++;
			if ( start == s.length() ) {
				return -1;
			}
		}
		return start;
	}
	
	private BigDecimal extractDecimal(NumberContext ctx) {
		if ( ctx == null ) {
			return null;
		}
		String value = ctx.getText();
		int start = findFirstNotZero(value);
		if ( start == -1 ) {
			return BigDecimal.ZERO;
		}
		if ( start > 0 ) {
			value = value.substring(start);
		}
		return new BigDecimal(value);
	}

	private Integer extractInteger(ParserRuleContext ctx) {
		if ( ctx == null ) {
			return null;
		}
		String value = ctx.getText();
		if ( value.indexOf('.') != -1 ) {
			throw new AstException("Integer value expected");
		}
		int start = findFirstNotZero(value);
		if ( start == -1 ) {
			return 0;
		}
		if ( start > 0 ) {
			value = value.substring(start);
		}
		return Integer.decode(value);
	}

	private LocValue extractLocValue(LocValueContext ctx) {
		boolean increment = ctx.PLUS() != null;
		BigDecimal value = extractDecimal(ctx.number());
		return new LocValue(increment, value);
	}
	
	private Integer extractSkipa(List<SkipaContext> l) {
		return l.size() > 0 ? extractInteger(l.get(0).number()) : null;
	}
	
	private Integer extractSkipb(List<SkipbContext> l) {
		return l.size() > 0 ? extractInteger(l.get(0).number()) : null;
	}
	
	private Integer extractSpacea(List<SpaceaContext> l) {
		return l.size() > 0 ? extractInteger(l.get(0).number()) : null;
	}
	
	private Integer extractSpaceb(List<SpacebContext> l) {
		return l.size() > 0 ? extractInteger(l.get(0).number()) : null;
	}

	@Override
	public AstNode visitChildren(RuleNode node) {
		return AstHelper.visit(this, (ParserRuleContext)node);
	}

	@Override
	public Chrid visitChrid(ChridContext ctx) {
        Location location = AstHelper.location(ctx);
        Chrid node = new Chrid(location);
        AstHelper.visitChildren(this, ctx, node);
        return node;
	}

	@Override
	public AstNode visitBarcode(BarcodeContext ctx) {
        Location location = AstHelper.location(ctx);
		String name = ctx.IDENTIFIER().getText();
		BigDecimal height = extractDecimal(ctx.number(0));
		BigDecimal width = extractDecimal(ctx.number(1));
		BigDecimal ratio = extractDecimal(ctx.number(2));
		Barcode node = new Barcode(location, name, height, width, ratio);
        AstHelper.visitChildren(this, ctx, node);
        return node;
	}
	
	@Override
	public Condition visitCondition(ConditionContext ctx) {
        Location location = AstHelper.location(ctx);
        Condition node = new Condition(location);
        AstHelper.visitChildren(this, ctx, node);
        return node;
	}

	@Override
	public Cpi visitCpi(CpiContext ctx) {
        Location location = AstHelper.location(ctx);
        Integer value = extractInteger(ctx.number());
        Cpi node = new Cpi(location, value);
        AstHelper.visitChildren(this, ctx, node);
        return node;
	}
	
	@Override
	public DataType visitDataType(DataTypeContext ctx) {
		Location location = AstHelper.location(ctx);
		String type = ctx.TYPE() != null ? ctx.TYPE().getText() : null;
		Integer size = extractInteger(ctx.length());
		Integer precision = extractInteger(ctx.precision());
		if ( precision == null && type != null && type.charAt(0) == 'S' ) {
			precision = 0;
		}
		DataType node = new DataType(location, type, size, precision);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public AstNode visitDate(DateContext ctx) {
		Location location = AstHelper.location(ctx);
		boolean job = ctx.dateType() == null || ctx.dateType().FC_JOB() != null;
		int digits = ctx.dateSize() == null || ctx.dateSize().FC_Y() != null ? 2 : 4;
		Date node = new Date(location, job, digits);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}
	
	@Override
	public Description visitDescription(DescriptionContext ctx) {
		Location location = AstHelper.location(ctx);
		Description node = new Description(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public DescriptionElement visitDescriptionElement(DescriptionElementContext ctx) {
		Location location = AstHelper.location(ctx);
		StringBuilder sb = new StringBuilder();
		for ( TerminalNode ds : ctx.STRING_START() ) {
			Matcher m = endDescRe.matcher(ds.getText());
			int i = -1;
			while ( m.find() ) {
				i = m.start();
			}
			if ( i != -1 ) {
				String s = ds.getText().charAt(i) == '-' && ds.getText().charAt(i-1) == ' ' ?
						ds.getText().substring(0, i - 1) : ds.getText().substring(0, i);
				sb.append(s);
			}
			else {
				m = eolRe.matcher(ds.getText());
				m.find();
				i = m.start();
				sb.append(ds.getText().substring(0, i));
			}
		}
		if ( ctx.STRING() != null ) {
			sb.append(ctx.STRING().getText());
		}
		DescriptionElement node = new DescriptionElement(location, sb.toString());
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public Default visitDft(DftContext ctx) {
        Location location = AstHelper.location(ctx);
        Default node = new Default(location);
        AstHelper.visitChildren(this, ctx, node);
        return node;
	}

	@Override
	public EditCode visitEditCode(EditCodeContext ctx) {
		Location location = AstHelper.location(ctx);
		String editCode = ctx.EDITCODE().getText();
		EditCode node = new EditCode(location, editCode);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public EditWord visitEditWord(EditWordContext ctx) {
		Location location = AstHelper.location(ctx);
		EditWord node = new EditWord(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}
	
	@Override
	public Entry visitEntry(EntryContext ctx) {
        Location location = AstHelper.location(ctx);
		Node node = new Node(location);
        AstHelper.visitChildren(this, ctx, node);
        Iterator<Condition> ci = node.getChildren(Condition.class);
        Field f = node.getChild(Field.class);
        if ( f != null ) {
        	f.setConditions(ci);
        	return f;
        }
        Label l = node.getChild(Label.class);
        if ( l != null ) {
        	l.setConditions(ci);
        	return l;
        }
        listener.astError(node, "Unhandled Entry: " + node);
        return null;
	}
	
	@Override
	public EntryKeywords visitEntryKeywords(EntryKeywordsContext ctx) {
		Location location = AstHelper.location(ctx);
		String dateFormat = ctx.datfmt().size() > 0 ? ctx.datfmt().get(0).FC_EUR().getText() : null;
		Integer skipa = null;
		Integer skipb = null;
		Integer spacea = null;
		Integer spaceb = null;
		String error = null;
		try {
			skipa = extractSkipa(ctx.skipa());
			skipb = extractSkipb(ctx.skipb());
			spacea = extractSpacea(ctx.spacea());
			spaceb = extractSpaceb(ctx.spaceb());
		}
		catch ( AstException e ) {
			error = e.getMessage();
		}
		EntryKeywords node = new EntryKeywords(location, dateFormat, skipa, skipb, spacea, spaceb);
		if ( error != null ) {
			listener.astError(node, error);
		}
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}
	
	@Override
	public Field visitField(FieldContext ctx) {
        Location location = AstHelper.location(ctx);
        String name = ctx.IDENTIFIER().getText();
        boolean reference = ctx.REFERENCE() != null;
        Usage usage = ctx.PROGRAM() != null ? Usage.PROGRAM : Usage.OUTPUT;
        Field node = new Field(location, name, reference, usage);
        AstHelper.visitChildren(this, ctx, node);
        return node;
	}
	
	@Override
	public FileKeywords visitFileKeywords(FileKeywordsContext ctx) {
        Location location = AstHelper.location(ctx);
        FileKeywords node = new FileKeywords(location, ctx.indara().size() > 0, 
        		ctx.relpos().size() > 0);
        AstHelper.visitChildren(this, ctx, node);
        return node;
	}

	@Override
	public AstNode visitFont(FontContext ctx) {
        Location location = AstHelper.location(ctx);
        Integer identifier = extractInteger(ctx.number(0));
        Integer pointsize = extractInteger(ctx.number(1));
        Font node = new Font(location, identifier, pointsize);
        AstHelper.visitChildren(this, ctx, node);
        return node;
	}
	
	@Override
	public HexString visitHexString(HexStringContext ctx) {
		Location location = AstHelper.location(ctx);
		HexString node = new HexString(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public HexStringElement visitHexStringElement(HexStringElementContext ctx) {
		Location location = AstHelper.location(ctx);
		StringBuilder sb = new StringBuilder();
		for ( TerminalNode ds : ctx.XSTRING_START() ) {
			Matcher m = endDescRe.matcher(ds.getText());
			int i = -1;
			while ( m.find() ) {
				i = m.start();
			}
			if ( i != -1 ) {
				String s = ds.getText().charAt(i) == '-' && ds.getText().charAt(i-1) == ' ' ?
						ds.getText().substring(0, i - 1) : ds.getText().substring(0, i);
				sb.append(s);
			}
			else {
				m = eolRe.matcher(ds.getText());
				m.find();
				i = m.start();
				sb.append(ds.getText().substring(0, i));
			}
		}
		if ( ctx.XSTRING() != null ) {
			sb.append(ctx.XSTRING().getText());
		}
		HexStringElement node = new HexStringElement(location, sb.toString());
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}
	
	@Override
	public Highlight visitHighlight(HighlightContext ctx) {
        Location location = AstHelper.location(ctx);
        Highlight node = new Highlight(location);
        AstHelper.visitChildren(this, ctx, node);
        return node;
	}
	
	@Override
	public Label visitLabel(LabelContext ctx) {
        Location location = AstHelper.location(ctx);
        Label node = new Label(location);
        AstHelper.visitChildren(this, ctx, node);
        return node;
	}

	@Override
	public it.twenfir.prtfparser.ast.Location visitLocation(LocationContext ctx) {
        Location location = AstHelper.location(ctx);
        LocValue line = null;
        LocValue pos = null;
        if ( ctx.locValue().size() == 2 ) {
        	line = extractLocValue(ctx.locValue().get(0));
        }
        if ( ctx.locValue().size() > 0 ) {
        	pos = extractLocValue(ctx.locValue().get(ctx.locValue().size() - 1));
        }
        it.twenfir.prtfparser.ast.Location node = new it.twenfir.prtfparser.ast.Location(location, line, pos);
        AstHelper.visitChildren(this, ctx, node);
        return node;
	}
	
	@Override
	public OpTerm visitOpTerm(OpTermContext ctx) {
		Location location = AstHelper.location(ctx);
		CondOp co = ctx.OR() != null ? CondOp.OR : CondOp.AND;
		OpTerm node = new OpTerm(location, co);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}
	
    @Override
    public PageNumber visitPageNumber(PageNumberContext ctx) {
        Location location = AstHelper.location(ctx);
        PageNumber node = new PageNumber(location);
        AstHelper.visitChildren(this, ctx, node);
        return node;
    }
	
    @Override
    public Pagseg visitPagseg(PagsegContext ctx) {
        Location location = AstHelper.location(ctx);
        String library = ctx.IDENTIFIER().getText();
        BigDecimal down = extractDecimal(ctx.number(0));
        BigDecimal across = extractDecimal(ctx.number(1));
        Pagseg node = new Pagseg(location, library, down, across);
        AstHelper.visitChildren(this, ctx, node);
        return node;
    }
    
    @Override
    public Prtf visitPrtf(PrtfContext ctx) {
        Location location = AstHelper.location(ctx);
        Prtf node = new Prtf(location);
        AstHelper.visitChildren(this, ctx, node);
        return node;
    }

    @Override
    public Record visitRecord(RecordContext ctx) {
        Location location = AstHelper.location(ctx);
        Record node = new Record(
        		location, ctx.recordName.getText());
        AstHelper.visitChildren(this, ctx, node);
        return node;
    }

    @Override
    public RecordKeywords visitRecordKeywords(RecordKeywordsContext ctx) {
		Location location = AstHelper.location(ctx);
		Integer skipa = null;
		Integer skipb = null;
		Integer spacea = null;
		Integer spaceb = null;
		String error = null;
		try {
			skipa = extractSkipa(ctx.skipa());
			skipb = extractSkipb(ctx.skipb());
			spacea = extractSpacea(ctx.spacea());
			spaceb = extractSpaceb(ctx.spaceb());
		}
		catch ( AstException e ) {
			error = e.getMessage();
		}
		RecordKeywords node = new RecordKeywords(location, skipa, skipb, spacea, spaceb);
		if ( error != null ) {
			listener.astError(node, error);
		}
		AstHelper.visitChildren(this, ctx, node);
		return node;
    }
    
	@Override
	public Ref visitRef(RefContext ctx) {
		Location location = AstHelper.location(ctx);
		String reference = ctx.refFile.getText();
		String library = null;
		if ( ctx.refLib != null ) {
			library = ctx.refLib.getText();
		}
		else if ( ctx.CONSTANT() != null ) {
			library = ctx.CONSTANT().getText();
		}
		Ref node = new Ref(location, library, reference);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public RefField visitRefField(RefFieldContext ctx) {
		Location location = AstHelper.location(ctx);
		String format = null;
		if ( ctx.ref_format != null ) {
			format = ctx.ref_format.getText();
		}
		String name = ctx.ref_field.getText();
		String library = null;
		if ( ctx.ref_lib != null ) {
			library = ctx.ref_lib.getText();
		}
		else if ( ctx.con_lib != null ) {
			library = ctx.con_lib.getText();
		}
		String file = null;
		if ( ctx.ref_file != null ) {
			file = ctx.ref_file.getText();
		}
		else if ( ctx.con_file != null ) {
			file = ctx.con_file.getText();
		}
		RefField node = new RefField(location, format, name, library, file);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public Term visitTerm(TermContext ctx) {
		Location location = AstHelper.location(ctx);
		List<Indicator> inds = new ArrayList<Indicator>();
		for ( TerminalNode i : ctx.INDICATOR() ) {
			boolean n = i.getText().charAt(0) == 'N';
			StringBuffer sb = new StringBuffer();
			sb.append(i.getText().charAt(1));
			sb.append(i.getText().charAt(2));
			int v = Integer.parseInt(sb.toString());
			inds.add(new Indicator(n, v));
		}
		Term node = new Term(location, inds);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public Text visitText(TextContext ctx) {
		Location location = AstHelper.location(ctx);
		Text node = new Text(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public Time visitTime(TimeContext ctx) {
		Location location = AstHelper.location(ctx);
		Time node = new Time(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

	@Override
	public Underline visitUnderline(UnderlineContext ctx) {
		Location location = AstHelper.location(ctx);
		Underline node = new Underline(location);
		AstHelper.visitChildren(this, ctx, node);
		return node;
	}

}
