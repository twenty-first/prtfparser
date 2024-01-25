package it.twenfir.prtfparser;

import java.util.ArrayList;
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
import it.twenfir.antlr.parser.ErrorListenerBase;
import it.twenfir.prtfparser.PrtfParser.ConditionContext;
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
import it.twenfir.prtfparser.PrtfParser.HighlightContext;
import it.twenfir.prtfparser.PrtfParser.LabelContext;
import it.twenfir.prtfparser.PrtfParser.LocValueContext;
import it.twenfir.prtfparser.PrtfParser.LocationContext;
import it.twenfir.prtfparser.PrtfParser.OpTermContext;
import it.twenfir.prtfparser.PrtfParser.PageNumberContext;
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
import it.twenfir.prtfparser.ast.CondOp;
import it.twenfir.prtfparser.ast.Condition;
import it.twenfir.prtfparser.ast.DataType;
import it.twenfir.prtfparser.ast.Date;
import it.twenfir.prtfparser.ast.Description;
import it.twenfir.prtfparser.ast.DescriptionElement;
import it.twenfir.prtfparser.ast.EditCode;
import it.twenfir.prtfparser.ast.EditWord;
import it.twenfir.prtfparser.ast.Default;
import it.twenfir.prtfparser.ast.Entry;
import it.twenfir.prtfparser.ast.EntryKeywords;
import it.twenfir.prtfparser.ast.Field;
import it.twenfir.prtfparser.ast.FileKeywords;
import it.twenfir.prtfparser.ast.Highlight;
import it.twenfir.prtfparser.ast.Indicator;
import it.twenfir.prtfparser.ast.Label;
import it.twenfir.prtfparser.ast.LocValue;
import it.twenfir.prtfparser.ast.OpTerm;
import it.twenfir.prtfparser.ast.PageNumber;
import it.twenfir.prtfparser.ast.Prtf;
import it.twenfir.prtfparser.ast.RecordKeywords;
import it.twenfir.prtfparser.ast.Ref;
import it.twenfir.prtfparser.ast.RefField;
import it.twenfir.prtfparser.ast.Term;
import it.twenfir.prtfparser.ast.Text;
import it.twenfir.prtfparser.ast.Time;
import it.twenfir.prtfparser.ast.Underline;
import it.twenfir.prtfparser.ast.Usage;

public class AstBuilder extends PrtfParserBaseVisitor<AstNode>{

	private Pattern endDescRe = Pattern.compile("\\+|-");
	private Pattern eolRe = Pattern.compile("\\r|\\n");
	
	private ErrorListener listener;

	public AstBuilder(ErrorListener listener) {
		this.listener = listener != null ? listener : new ErrorListenerBase();
	}

	private LocValue extractLocValue(LocValueContext ctx) {
		boolean increment = ctx.PLUS() != null;
		Integer value = null;
		if ( ctx.NUMBER() != null ) {
			value = Integer.decode(ctx.NUMBER().getText());
		}
		return new LocValue(increment, value);
	}
	
	private Integer extractSkipa(List<SkipaContext> l) {
		return l.size() > 0 ? Integer.decode(l.get(0).NUMBER().getText()) : null;
	}
	
	private Integer extractSkipb(List<SkipbContext> l) {
		return l.size() > 0 ? Integer.decode(l.get(0).NUMBER().getText()) : null;
	}
	
	private Integer extractSpacea(List<SpaceaContext> l) {
		return l.size() > 0 ? Integer.decode(l.get(0).NUMBER().getText()) : null;
	}
	
	private Integer extractSpaceb(List<SpacebContext> l) {
		return l.size() > 0 ? Integer.decode(l.get(0).NUMBER().getText()) : null;
	}

	@Override
	public AstNode visitChildren(RuleNode node) {
		return AstHelper.visit(this, (ParserRuleContext)node);
	}

	@Override
	public Condition visitCondition(ConditionContext ctx) {
        Location location = AstHelper.location(ctx);
        Condition node = new Condition(location);
        AstHelper.visitChildren(this, ctx, node);
        return node;
	}

	@Override
	public DataType visitDataType(DataTypeContext ctx) {
		Location location = AstHelper.location(ctx);
		String type = ctx.TYPE() != null ? ctx.TYPE().getText() : null;
		Integer size = ctx.NUMBER(0) != null ? Integer.parseInt(ctx.NUMBER(0).getText()) : null;
		Integer precision = ctx.NUMBER(1) != null ? Integer.parseInt(ctx.NUMBER(1).getText()) : null;
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
		Date node = new Date(location);
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
        Condition c = node.getChild(Condition.class);
        Field f = node.getChild(Field.class);
        if ( f != null ) {
        	f.setCondition(c);
        	return f;
        }
        Label l = node.getChild(Label.class);
        if ( l != null ) {
        	l.setCondition(c);
        	return l;
        }
        listener.astError(node, "Unhandled Entry: " + node);
        return null;
	}
	
	@Override
	public EntryKeywords visitEntryKeywords(EntryKeywordsContext ctx) {
		Location location = AstHelper.location(ctx);
        Integer skipa = extractSkipa(ctx.skipa());
        Integer skipb = extractSkipb(ctx.skipb());
        Integer spacea = extractSpacea(ctx.spacea());
        Integer spaceb = extractSpaceb(ctx.spaceb());
		EntryKeywords node = new EntryKeywords(location, skipa, skipb, spacea, spaceb);
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
        FileKeywords node = new FileKeywords(location, ctx.indara().size() > 0);
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
    public Prtf visitPrtf(PrtfContext ctx) {
        Location location = AstHelper.location(ctx);
        Prtf node = new Prtf(location);
        AstHelper.visitChildren(this, ctx, node);
        return node;
    }

    @Override
    public it.twenfir.prtfparser.ast.Record visitRecord(RecordContext ctx) {
        Location location = AstHelper.location(ctx);
        it.twenfir.prtfparser.ast.Record node = new it.twenfir.prtfparser.ast.Record(
        		location, ctx.recordName.getText());
        AstHelper.visitChildren(this, ctx, node);
        return node;
    }

    @Override
    public RecordKeywords visitRecordKeywords(RecordKeywordsContext ctx) {
		Location location = AstHelper.location(ctx);
        Integer skipa = extractSkipa(ctx.skipa());
        Integer skipb = extractSkipb(ctx.skipb());
        Integer spacea = extractSpacea(ctx.spacea());
        Integer spaceb = extractSpaceb(ctx.spaceb());
		RecordKeywords node = new RecordKeywords(location, skipa, skipb, spacea, spaceb);
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
		RefField node = new RefField(location, name, library, file);
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
