lexer grammar PrtfLexer;

tokens { A_SPEC, AND, IDENTIFIER, INDICATOR, NUMBER, OR, PLUS, 
        CONSTANT, LPAR, MINUS, QUOTE, RPAR, SLASH, STRING, STRING_START }

PREFIX      : PREFIX_F -> channel(HIDDEN), pushMode(FormType);
PART_PREF   : ( ANY_F
              | ( ANY_F ANY_F )
              | ( ANY_F ANY_F ANY_F )
              | ( ANY_F ANY_F ANY_F ANY_F ) 
              ) 
              EOL_F -> channel(HIDDEN)
              ;
              
ST_EOL      : EOL_F+ -> channel(HIDDEN);

mode FormType;

SP_A_SPEC   : A_F -> type(A_SPEC), mode(ConditionOperator);
SP_SPACE    : ' ' -> channel(HIDDEN), mode(ConditionOperator);
COMMENT     : ANY_F? '*' ANY_F* -> channel(HIDDEN);
SP_EOL      : EOL_F+ -> channel(HIDDEN), popMode;

mode ConditionOperator;

CO_AND      : A_F -> type(AND), mode(Condition);
CO_OR       : O_F -> type(OR), mode(Condition);
CO_SPACE    : ' ' -> channel(HIDDEN), mode(Condition);
CO_EOL      : EOL_F+ -> channel(HIDDEN), popMode;

mode Condition;

CN_SPACE    : '          ' -> channel(HIDDEN), mode(NameType);
CN_EOL      : EOL_F+ -> channel(HIDDEN), popMode;
CN_IND      : IND_F -> type(INDICATOR), mode(Cond1);

mode Cond1;

CN1_SPACE   : '       ' -> channel(HIDDEN), mode(NameType);
CN1_EOL     : EOL_F+ -> channel(HIDDEN), popMode;
CN1_IND     : IND_F -> type(INDICATOR), mode(Cond2);

mode Cond2;

CN2_SPACE   : '    ' -> channel(HIDDEN), mode(NameType);
CN2_EOL     : EOL_F+ -> channel(HIDDEN), popMode;
CN2_IND     : IND_F -> type(INDICATOR), mode(Cond3);

mode Cond3;

CN3_EOL     : EOL_F+ -> channel(HIDDEN), popMode;
CN3_IND     : IND_F -> type(INDICATOR), mode(NameType);

mode NameType;

RECORD      : 'R' -> mode(Reserved);
NT_SPACE    : ' ' -> channel(HIDDEN), mode(Reserved);

mode Reserved;

RS_SPACE    : ' ' -> channel(HIDDEN), mode(Name);

mode Name;

NAME1       : IDS_F -> type(IDENTIFIER), mode(Ns9);
NAME2       : IDS_F IDC_F -> type(IDENTIFIER), mode(Ns8);
NAME3       : IDS_F IDC_F IDC_F -> type(IDENTIFIER), mode(Ns7);
NAME4       : IDS_F IDC_F IDC_F IDC_F -> type(IDENTIFIER), mode(Ns6);
NAME5       : IDS_F IDC_F IDC_F IDC_F IDC_F -> type(IDENTIFIER), mode(Ns5);
NAME6       : IDS_F IDC_F IDC_F IDC_F IDC_F IDC_F -> type(IDENTIFIER), mode(Ns4);
NAME7       : IDS_F IDC_F IDC_F IDC_F IDC_F IDC_F IDC_F -> type(IDENTIFIER), mode(Ns3);
NAME8       : IDS_F IDC_F IDC_F IDC_F IDC_F IDC_F IDC_F IDC_F -> type(IDENTIFIER), mode(Ns2);
NAME9       : IDS_F IDC_F IDC_F IDC_F IDC_F IDC_F IDC_F IDC_F IDC_F -> type(IDENTIFIER), mode(Ns1);
NAME10      : IDS_F IDC_F IDC_F IDC_F IDC_F IDC_F IDC_F IDC_F IDC_F IDC_F -> type(IDENTIFIER), mode(Reference);
NM_SPACE    : '          ' -> channel(HIDDEN), mode(Reference);
NM_EOL      : EOL_F+ -> channel(HIDDEN), popMode;

mode Ns9;

NS9_SPACE   : '         ' -> channel(HIDDEN), mode(Reference);
NS9_EOL     : EOL_F+ -> channel(HIDDEN), popMode;

mode Ns8;

NS8_SPACE   : '        ' -> channel(HIDDEN), mode(Reference);
NS8_EOL     : EOL_F+ -> channel(HIDDEN), popMode;

mode Ns7;

NS7_SPACE   : '       ' -> channel(HIDDEN), mode(Reference);
NS7_EOL     : EOL_F+ -> channel(HIDDEN), popMode;

mode Ns6;

NS6_SPACE   : '      ' -> channel(HIDDEN), mode(Reference);
NS6_EOL     : EOL_F+ -> channel(HIDDEN), popMode;

mode Ns5;

NS5_SPACE   : '     ' -> channel(HIDDEN), mode(Reference);
NS5_EOL     : EOL_F+ -> channel(HIDDEN), popMode;

mode Ns4;

NS4_SPACE   : '    ' -> channel(HIDDEN), mode(Reference);
NS4_EOL     : EOL_F+ -> channel(HIDDEN), popMode;

mode Ns3;

NS3_SPACE   : '   ' -> channel(HIDDEN), mode(Reference);
NS3_EOL     : EOL_F+ -> channel(HIDDEN), popMode;

mode Ns2;

NS2_SPACE   : '  ' -> channel(HIDDEN), mode(Reference);
NS2_EOL     : EOL_F+ -> channel(HIDDEN), popMode;

mode Ns1;

NS1_SPACE   : ' ' -> channel(HIDDEN), mode(Reference);
NS1_EOL     : EOL_F+ -> channel(HIDDEN), popMode;

mode Reference;

REFERENCE   : 'R' -> mode(Length);
RF_SPACE    : ' ' -> channel(HIDDEN), mode(Length); 
RF_EOL      : EOL_F+ -> channel(HIDDEN), popMode;

mode Length;

LEN5        : [0-9+-] [0-9] [0-9] [0-9] [0-9] -> type(NUMBER), mode(DataType);
LN1_SPACE   : ' ' -> channel(HIDDEN), mode(Len4);
LN2_SPACE   : '  ' -> channel(HIDDEN), mode(Len3);
LN2_PLUS    : '+ ' -> type(PLUS), mode(Len3);
LN3_SPACE   : '   ' -> channel(HIDDEN), mode(Len2);
LN3_PLUS    : ( '+  ' | ' + ' ) -> type(PLUS), mode(Len2);
LN4_SPACE   : '    ' -> channel(HIDDEN), mode(Len1);
LN4_PLUS    : ( '+   ' | ' +  ' | '  + ' ) -> type(PLUS), mode(Len1);
LN5_SPACE   : '     ' -> channel(HIDDEN), mode(DataType);
LN5_PLUS    : ( '+    ' | ' +   ' | '  +  ' | '   + ' ) -> type(PLUS), mode(DataType);
LN_EOL      : EOL_F+ -> channel(HIDDEN), popMode;

mode Len4;

LEN4        : [0-9+-] [0-9] [0-9] [0-9] -> type(NUMBER), mode(DataType);
LN4_EOL     : EOL_F+ -> channel(HIDDEN), popMode;

mode Len3;

LEN3        : [0-9+-] [0-9] [0-9] -> type(NUMBER), mode(DataType);
LN3_EOL     : EOL_F+ -> channel(HIDDEN), popMode;

mode Len2;

LEN2        : [0-9+-] [0-9] -> type(NUMBER), mode(DataType);
LN2_EOL     : EOL_F+ -> channel(HIDDEN), popMode;

mode Len1;

LEN1        : [0-9] -> type(NUMBER), mode(DataType);
LN1_EOL     : EOL_F+ -> channel(HIDDEN), popMode;

mode DataType;

TYPE        : [AFLSTZ] -> mode(Precision);
DT_SPACE    : ' ' -> channel(HIDDEN), mode(Precision);
DT_EOL      : EOL_F+ -> channel(HIDDEN), popMode;

mode Precision;

PREC2       : [0-9] [0-9] -> type(NUMBER), mode(Usage);
PREC1       : [0-9] -> type(NUMBER), mode(Usage);
PR_SPACE2   : '  ' -> channel(HIDDEN), mode(Usage);
PR_SPACE1   : ' ' -> channel(HIDDEN);
PR_EOL      : EOL_F+ -> channel(HIDDEN), popMode;

mode Usage;

USAGE       : [OP] -> mode(Location);
US_SPACE    : ' ' -> channel(HIDDEN), mode(Location);
US_EOL      : EOL_F+ -> channel(HIDDEN), popMode;

mode Location;

LC_SPACE    : '      ' -> channel(HIDDEN), mode(Keyword);
// To be completed

mode Keyword;

ALIAS       : 'ALIAS';
KW_EOL      : EOL_F+ -> channel(HIDDEN), popMode;
// To be completed

// Common fragments

fragment ANY_F              : ~[\r\n] ;
fragment EOL_F              : '\r'? '\n' ;
fragment PREFIX_F           : ANY_F ANY_F ANY_F ANY_F ANY_F ;
fragment A_F                : [Aa] ;
fragment N_F                : [Nn] ;
fragment O_F                : [Oo] ;
fragment IND_F              : ( N_F | ' ' ) [0-9][0-9] ;
fragment IDS_F              : [A-Z$\u00a3\u00a7] ;
fragment IDC_F              : [A-Z0-9$_\u00a3\u00a7] ;
fragment IDENTIFIER_F       : IDS_F IDC_F* ;
