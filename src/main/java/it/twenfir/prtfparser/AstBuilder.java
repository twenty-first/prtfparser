package it.twenfir.prtfparser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.RuleNode;

import it.twenfir.antlr.api.ErrorListener;
import it.twenfir.antlr.ast.AstHelper;
import it.twenfir.antlr.ast.AstNode;
import it.twenfir.antlr.parser.ErrorListenerBase;
import it.twenfir.prtfparser.PrtfParser.PrtfContext;
import it.twenfir.prtfparser.ast.Prtf;

public class AstBuilder extends PrtfParserBaseVisitor<AstNode>{

	
	private ErrorListener listener;

	public AstBuilder(ErrorListener listener) {
		this.listener = listener != null ? listener : new ErrorListenerBase();
	}
	
	@Override
	public AstNode visitChildren(RuleNode node) {
		return AstHelper.visit(this, (ParserRuleContext)node);
	}

    @Override
    public Prtf visitPrtf(PrtfContext ctx) {
        // TODO Auto-generated method stub
        return (Prtf)super.visitPrtf(ctx);
    }

    
}
