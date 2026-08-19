package it.twenfir.prtfparser.ast;

import it.twenfir.antlr.ast.AstVisitor;

public interface PrtfVisitor<ValueT> extends AstVisitor<ValueT> {
	ValueT visitBarcode(Barcode node);
	ValueT visitChrid(Chrid node);
	ValueT visitCondition(Condition node);
    ValueT visitCpi(Cpi node);
    ValueT visitDataType(DataType node);
    ValueT visitDate(Date node);
    ValueT visitDefault(Default node);
    ValueT visitDescription(Description node);
    ValueT visitDescriptionElement(DescriptionElement node);
    ValueT visitEditCode(EditCode node);
    ValueT visitEditWord(EditWord node);
	ValueT visitEntryKeywords(EntryKeywords node);
	ValueT visitField(Field node);
	ValueT visitFileKeywords(FileKeywords node);
	ValueT visitFont(Font node);
	ValueT visitHexString(HexString node);
	ValueT visitHexStringElement(HexStringElement node);
	ValueT visitHighlight(Highlight node);
	ValueT visitLabel(Label node);
	ValueT visitLocation(Location node);
    ValueT visitOpTerm(OpTerm node);
	ValueT visitPageNumber(PageNumber node);
	ValueT visitPagseg(Pagseg node);
	ValueT visitPrtf(Prtf node);
	ValueT visitRecord(Record node);
	ValueT visitRecordKeywords(RecordKeywords node);
	ValueT visitRef(Ref node);
	ValueT visitRefField(RefField node);
    ValueT visitTerm(Term node);
    ValueT visitText(Text node);
    ValueT visitTime(Time node);
    ValueT visitUnderline(Underline node);
}
