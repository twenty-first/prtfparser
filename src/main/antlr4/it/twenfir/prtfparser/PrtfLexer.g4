lexer grammar PrtfLexer;

PREFIX      : PREFIX_F -> channel(HIDDEN);

// Common fragments

fragment ANY_F              : ~[\r\n] ;
fragment EOL_F              : '\r'? '\n' ;
fragment PREFIX_F           : ANY_F ANY_F ANY_F ANY_F ANY_F ;
