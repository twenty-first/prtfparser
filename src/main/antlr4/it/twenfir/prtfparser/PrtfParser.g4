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

recordKeywords	: ( highlight
                  | pagseg
                  | skipa
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

dataType 	: ( length | length? TYPE ) precision? ;

location	: locValue? locValue ;

locValue	: PLUS? number ;

entryKeywords	: ( barcode
                  | chrid
                  | cpi
				  | date
				  | datfmt
				  | dft
				  | editCode
				  | editWord
				  | font
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

barcode     : A_SPEC* BARCODE LPAR IDENTIFIER number FC_HRI A_SPEC* LPAR FC_WIDTH number RPAR A_SPEC* LPAR FC_RATIO number RPAR hexString RPAR ;

chrid       : A_SPEC* CHRID ;

cpi			: A_SPEC* CPI LPAR number RPAR ;

date		: A_SPEC* DATE ( LPAR ( dateType | dateType? dateSize ) RPAR )?;

datfmt		: A_SPEC* DATFMT LPAR FC_EUR RPAR ;

dateType	: FC_JOB | FC_SYS ;

dateSize	: FC_Y | FC_YY ;

dft			: A_SPEC* ( DFT LPAR description RPAR | description ) ;

editCode	: A_SPEC* EDTCDE LPAR EDITCODE RPAR ;

editWord    : A_SPEC* EDTWRD LPAR description RPAR ;

font        : A_SPEC* FONT LPAR number LPAR FC_POINTSIZE number RPAR RPAR ;

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

pagseg      : A_SPEC* PAGSEG LPAR IDENTIFIER number number RPAR ;

skipa		: A_SPEC* SKIPA LPAR number RPAR ;

skipb		: A_SPEC* SKIPB LPAR number RPAR ;

spacea		: A_SPEC* SPACEA LPAR number RPAR ;

spaceb		: A_SPEC* SPACEB LPAR number RPAR ;

text		: A_SPEC* TEXT LPAR description RPAR ;

time		: A_SPEC* TIME ;

underline	: A_SPEC* UNDERLINE ;

description : descriptionElement ( ( MINUS | PLUS )? A_SPEC* descriptionElement )* ;

descriptionElement : QUOTE ( STRING_START A_SPEC* )* STRING? QUOTE ;

hexString : hexStringElement ( ( MINUS | PLUS )? A_SPEC* hexStringElement )* ;

hexStringElement : XQUOTE ( XSTRING_START A_SPEC* )* XSTRING? QUOTE ;

number : NUMBER ;

length : LENGTH ;

precision : PRECISION ;
