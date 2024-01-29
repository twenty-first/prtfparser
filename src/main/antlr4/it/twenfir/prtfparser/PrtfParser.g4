parser grammar PrtfParser;

options
{
	tokenVocab = PrtfLexer ;
}

prtf	: fileKeywords? record+ EOF ;

fileKeywords	: ( indara
				  | ref
				  | relpos
				  )+
				  ;

record	: A_SPEC* RECORD ( recordName = IDENTIFIER ) 
		  recordKeywords? 
		  entry*
		  A_SPEC*
		  ;

recordKeywords	: ( skipa
				  | skipb
				  | spacea
				  | spaceb
				  | text
				  )+
				  ;

entry		: A_SPEC* condition* ( field | label ) ;

condition	: term opTerm* ;

opTerm		: A_SPEC* ( AND | OR ) term ;

term		: A_SPEC* INDICATOR ( INDICATOR INDICATOR? )? ;

field		: IDENTIFIER REFERENCE? dataType? ( OUTPUT | PROGRAM )? location? entryKeywords? ;

label		: location? entryKeywords ;

dataType 	: ( NUMBER | NUMBER? TYPE ) NUMBER? ;

location	: locValue? locValue ;

locValue	: PLUS? NUMBER ;

entryKeywords	: ( cpi
				  | date
				  | datfmt
				  | dft
				  | editCode
				  | editWord
				  | highlight
				  | pageNumber
				  | refField
				  | skipa
				  | skipb
				  | spacea
				  | spaceb
				  | text
				  | time
				  | underline
				  )+ ;

cpi			: A_SPEC* CPI LPAR NUMBER RPAR ;

date		: A_SPEC* DATE ( LPAR ( dateType | dateType? dateSize ) RPAR )?;

datfmt		: A_SPEC* DATFMT LPAR FC_EUR RPAR ;

dateType	: FC_JOB | FC_SYS ;

dateSize	: FC_Y | FC_YY ;

dft			: A_SPEC* ( DFT LPAR description RPAR | description ) ;

editCode	: A_SPEC* EDTCDE LPAR EDITCODE RPAR ;

editWord : A_SPEC* EDTWRD LPAR description RPAR ;

highlight	: A_SPEC* HIGHLIGHT ;

indara		: A_SPEC* INDARA ;

pageNumber	: A_SPEC* PAGNBR ;

ref			: A_SPEC* REF LPAR ( ( refLib = IDENTIFIER | CONSTANT ) SLASH )? refFile = IDENTIFIER RPAR ;

refField 	: A_SPEC* REFFLD LPAR 
	        ( ref_format = IDENTIFIER SLASH )? ref_field = IDENTIFIER 
	        ( ( ( ref_lib = IDENTIFIER | con_lib = CONSTANT ) SLASH )?
	          ( ref_file = IDENTIFIER | con_file = CONSTANT )
	        )?
	        RPAR
	        ;

relpos      : A_SPEC* RELPOS ;

skipa		: A_SPEC* SKIPA LPAR NUMBER RPAR ;

skipb		: A_SPEC* SKIPB LPAR NUMBER RPAR ;

spacea		: A_SPEC* SPACEA LPAR NUMBER RPAR ;

spaceb		: A_SPEC* SPACEB LPAR NUMBER RPAR ;

text		: A_SPEC* TEXT LPAR description RPAR ;

time		: A_SPEC* TIME ;

underline	: A_SPEC* UNDERLINE ;

description : descriptionElement ( ( MINUS | PLUS )? A_SPEC* descriptionElement )* ;

descriptionElement : QUOTE ( STRING_START A_SPEC* )* STRING? QUOTE ;
