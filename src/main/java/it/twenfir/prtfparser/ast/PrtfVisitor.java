package it.twenfir.prtfparser.ast;

import it.twenfir.antlr.ast.AstVisitor;

public interface PrtfVisitor<ValueT> extends AstVisitor<ValueT> {
	ValueT visitCondition(Condition node);
    ValueT visitDate(Date node);
    ValueT visitDescription(Description node);
    ValueT visitDescriptionElement(DescriptionElement node);
    ValueT visitDft(Dft node);
	ValueT visitEntryKeywords(EntryKeywords node);
	ValueT visitField(Field node);
	ValueT visitFileKeywords(FileKeywords node);
	ValueT visitHighlight(Highlight node);
	ValueT visitLabel(Label node);
	ValueT visitLocation(Location node);
    ValueT visitOpTerm(OpTerm node);
	ValueT visitPrtf(Prtf node);
	ValueT visitRecord(Record node);
	ValueT visitRecordKeywords(RecordKeywords node);
	ValueT visitRef(Ref node);
    ValueT visitTerm(Term node);
    ValueT visitText(Text node);
}
