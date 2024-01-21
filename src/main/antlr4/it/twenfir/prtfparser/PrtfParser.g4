parser grammar PrtfParser;

options
{
	tokenVocab = PrtfLexer ;
}

prtf	: fileKeywords? record+ EOF ;

fileKeywords	: ( ref
				  )+
				  ;

record	: A_SPEC* RECORD ( recordName = IDENTIFIER ) 
		  recordKeywords? 
		  field*
		  A_SPEC*
		  ;

recordKeywords	: ( skipb
				  )+
				  ;

field : A_SPEC* condition? ( namedField | unnamedField ) ;

condition	: ( AND | OR )? INDICATOR ( INDICATOR INDICATOR? )? ;

namedField	: IDENTIFIER dataType? position? fieldKeywords? ;

unnamedField	: position? fieldKeywords ;

dataType 	: ( NUMBER | NUMBER? TYPE ) NUMBER? ;

position	: NUMBER? NUMBER ;

fieldKeywords	: ( dft
				  | date
				  | edtcde
				  | skipa
				  | skipb
				  | spacea
				  | spaceb
				  | A_SPEC* UNDERLINE
				  )+ ;

date	: A_SPEC* DATE ;

dft		: A_SPEC* DFT? description ;

edtcde	: A_SPEC* EDTCDE LPAR EDITCODE RPAR ;

ref		: REF LPAR ( ( refLib = IDENTIFIER | CONSTANT ) SLASH )? refFile = IDENTIFIER RPAR ;

skipa	: A_SPEC* SKIPA LPAR NUMBER RPAR ;

skipb	: A_SPEC* SKIPB LPAR NUMBER RPAR ;

spacea	: A_SPEC* SPACEA LPAR NUMBER RPAR ;

spaceb	: A_SPEC* SPACEB LPAR NUMBER RPAR ;

underline:	A_SPEC* UNDERLINE ;

description : LPAR descriptionElement ( ( MINUS | PLUS )? A_SPEC* descriptionElement )* RPAR ;

descriptionElement : QUOTE ( STRING_START A_SPEC* )* STRING? QUOTE ;
