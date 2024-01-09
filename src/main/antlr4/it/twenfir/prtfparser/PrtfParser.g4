parser grammar PrtfParser;

options
{
	tokenVocab = PrtfLexer ;
}

prtf	: record+ EOF ;

record	: A_SPEC* RECORD ( recordName = IDENTIFIER ) 
		  ( skipb
		  )* 
		  ( field
		  )*
		  A_SPEC*
		  ;

field : A_SPEC* condition? ( namedField | unnamedField ) ;

condition	: ( AND | OR )? INDICATOR ( INDICATOR INDICATOR? )? ;

namedField	: IDENTIFIER dataType? position? keywords? ;

unnamedField	: position? keywords ;

dataType 	: ( NUMBER | NUMBER? TYPE ) NUMBER? ;

position	: NUMBER? NUMBER ;

keywords	: ( skipa
			  | skipb
			  | spacea
			  | spaceb
			  | A_SPEC* UNDERLINE
			  )+ ;

skipa	: A_SPEC* SKIPA LPAR NUMBER RPAR ;

skipb	: A_SPEC* SKIPB LPAR NUMBER RPAR ;

spacea	: A_SPEC* SPACEA LPAR NUMBER RPAR ;

spaceb	: A_SPEC* SPACEB LPAR NUMBER RPAR ;
