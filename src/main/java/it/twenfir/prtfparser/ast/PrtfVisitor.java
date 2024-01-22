package it.twenfir.prtfparser.ast;

import it.twenfir.antlr.ast.AstVisitor;

public interface PrtfVisitor<ValueT> extends AstVisitor<ValueT> {
	ValueT visitCondition(Condition node);
    ValueT visitDescription(Description node);
    ValueT visitDescriptionElement(DescriptionElement node);
	ValueT visitField(Field node);
	ValueT visitLabel(Label node);
	ValueT visitPrtf(Prtf node);
	ValueT visitRecord(Record node);
	ValueT visitRef(Ref node);
    ValueT visitText(Text node);
}
