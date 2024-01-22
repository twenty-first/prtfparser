package it.twenfir.prtfparser.ast;

import it.twenfir.antlr.ast.BaseAstVisitor;

public class PrtfBaseVisitor<ValueT> extends BaseAstVisitor<ValueT> implements PrtfVisitor<ValueT> {

	@Override
	public ValueT visitPrtf(Prtf node) {
        return visitChildren(node);
	}

	@Override
	public ValueT visitRecord(Record node) {
        return visitChildren(node);
	}
}
