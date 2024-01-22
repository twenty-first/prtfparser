package it.twenfir.prtfparser.ast;

import it.twenfir.antlr.ast.AstVisitor;

public interface PrtfVisitor<ValueT> extends AstVisitor<ValueT> {
	ValueT visitPrtf(Prtf node);
	ValueT visitRecord(Record node);
}
