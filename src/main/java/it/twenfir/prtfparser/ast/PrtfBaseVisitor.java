package it.twenfir.prtfparser.ast;

import it.twenfir.antlr.ast.BaseAstVisitor;

public class PrtfBaseVisitor<ValueT> extends BaseAstVisitor<ValueT> implements PrtfVisitor<ValueT> {

	@Override
	public ValueT visitCondition(Condition node) {
        return visitChildren(node);
	}

    @Override
    public ValueT visitDescription(Description node) {
        return visitChildren(node);
    }

    @Override
    public ValueT visitDescriptionElement(DescriptionElement node) {
        return visitChildren(node);
    }
	
	@Override
	public ValueT visitField(Field node) {
        return visitChildren(node);
	}

	@Override
	public ValueT visitLabel(Label node) {
        return visitChildren(node);
	}
	
	@Override
	public ValueT visitPrtf(Prtf node) {
        return visitChildren(node);
	}

	@Override
	public ValueT visitRecord(Record node) {
        return visitChildren(node);
	}
	
	@Override
	public ValueT visitRef(Ref node) {
        return visitChildren(node);
	}

    @Override
    public ValueT visitText(Text node) {
        return visitChildren(node);
    }
}
