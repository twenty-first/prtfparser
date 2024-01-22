package it.twenfir.prtfparser;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.RuleNode;

import it.twenfir.antlr.api.ErrorListener;
import it.twenfir.antlr.ast.AstHelper;
import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.ast.Location;
import it.twenfir.antlr.ast.Node;
import it.twenfir.antlr.parser.ErrorListenerBase;
import it.twenfir.prtfparser.PrtfParser.ConditionContext;
import it.twenfir.prtfparser.PrtfParser.EntryContext;
import it.twenfir.prtfparser.PrtfParser.FieldContext;
import it.twenfir.prtfparser.PrtfParser.LabelContext;
import it.twenfir.prtfparser.PrtfParser.PrtfContext;
import it.twenfir.prtfparser.PrtfParser.RecordContext;
import it.twenfir.prtfparser.PrtfParser.RecordKeywordsContext;
import it.twenfir.prtfparser.PrtfParser.RefContext;
import it.twenfir.prtfparser.PrtfParser.SkipaContext;
import it.twenfir.prtfparser.PrtfParser.SkipbContext;
import it.twenfir.prtfparser.PrtfParser.SpaceaContext;
import it.twenfir.prtfparser.PrtfParser.SpacebContext;
import it.twenfir.prtfparser.ast.Condition;
import it.twenfir.prtfparser.ast.Entry;
import it.twenfir.prtfparser.ast.Field;
import it.twenfir.prtfparser.ast.Label;
import it.twenfir.prtfparser.ast.Prtf;
import it.twenfir.prtfparser.ast.Ref;

public class AstBuilder extends PrtfParserBaseVisitor<AstNode>{

	
	private ErrorListener listener;

	public AstBuilder(ErrorListener listener) {
		this.listener = listener != null ? listener : new ErrorListenerBase();
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
	public Field visitField(FieldContext ctx) {
        Location location = AstHelper.location(ctx);
        Field node = new Field(location);
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
    public Prtf visitPrtf(PrtfContext ctx) {
        Location location = AstHelper.location(ctx);
        Prtf node = new Prtf(location, ctx.fileKeywords() != null ? ctx.fileKeywords().indara().size() > 0 : false);
        AstHelper.visitChildren(this, ctx, node);
        return node;
    }

    @Override
    public it.twenfir.prtfparser.ast.Record visitRecord(RecordContext ctx) {
        Location location = AstHelper.location(ctx);
        Integer skipa = null;
        Integer skipb = null;
        Integer spacea = null;
        Integer spaceb = null;
        RecordKeywordsContext rk = ctx.recordKeywords();
        if ( rk != null ) {
        	skipa = extractSkipa(rk.skipa());
        	skipb = extractSkipb(rk.skipb());
        	spacea = extractSpacea(rk.spacea());
        	spaceb = extractSpaceb(rk.spaceb());
        }
        it.twenfir.prtfparser.ast.Record node = new it.twenfir.prtfparser.ast.Record(location, ctx.recordName.getText(), skipa, skipb, spacea, spaceb);
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

}
