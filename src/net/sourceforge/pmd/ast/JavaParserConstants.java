/* Generated By:JJTree&JavaCC: Do not edit this line. JavaParserConstants.java */
package net.sourceforge.pmd.ast;

public interface JavaParserConstants {

  int EOF = 0;
  int SINGLE_LINE_COMMENT = 6;
  int FORMAL_COMMENT = 9;
  int MULTI_LINE_COMMENT = 10;
  int ABSTRACT = 12;
  int BOOLEAN = 13;
  int BREAK = 14;
  int BYTE = 15;
  int CASE = 16;
  int CATCH = 17;
  int CHAR = 18;
  int CLASS = 19;
  int CONST = 20;
  int CONTINUE = 21;
  int _DEFAULT = 22;
  int DO = 23;
  int DOUBLE = 24;
  int ELSE = 25;
  int EXTENDS = 26;
  int FALSE = 27;
  int FINAL = 28;
  int FINALLY = 29;
  int FLOAT = 30;
  int FOR = 31;
  int GOTO = 32;
  int IF = 33;
  int IMPLEMENTS = 34;
  int IMPORT = 35;
  int INSTANCEOF = 36;
  int INT = 37;
  int INTERFACE = 38;
  int LONG = 39;
  int NATIVE = 40;
  int NEW = 41;
  int NULL = 42;
  int PACKAGE = 43;
  int PRIVATE = 44;
  int PROTECTED = 45;
  int PUBLIC = 46;
  int RETURN = 47;
  int SHORT = 48;
  int STATIC = 49;
  int SUPER = 50;
  int SWITCH = 51;
  int SYNCHRONIZED = 52;
  int THIS = 53;
  int THROW = 54;
  int THROWS = 55;
  int TRANSIENT = 56;
  int TRUE = 57;
  int TRY = 58;
  int VOID = 59;
  int VOLATILE = 60;
  int WHILE = 61;
  int STRICTFP = 62;
  int INTEGER_LITERAL = 63;
  int DECIMAL_LITERAL = 64;
  int HEX_LITERAL = 65;
  int OCTAL_LITERAL = 66;
  int FLOATING_POINT_LITERAL = 67;
  int EXPONENT = 68;
  int CHARACTER_LITERAL = 69;
  int STRING_LITERAL = 70;
  int IDENTIFIER = 71;
  int LETTER = 72;
  int DIGIT = 73;
  int LPAREN = 74;
  int RPAREN = 75;
  int LBRACE = 76;
  int RBRACE = 77;
  int LBRACKET = 78;
  int RBRACKET = 79;
  int SEMICOLON = 80;
  int COMMA = 81;
  int DOT = 82;
  int ASSIGN = 83;
  int GT = 84;
  int LT = 85;
  int BANG = 86;
  int TILDE = 87;
  int HOOK = 88;
  int COLON = 89;
  int EQ = 90;
  int LE = 91;
  int GE = 92;
  int NE = 93;
  int SC_OR = 94;
  int SC_AND = 95;
  int INCR = 96;
  int DECR = 97;
  int PLUS = 98;
  int MINUS = 99;
  int STAR = 100;
  int SLASH = 101;
  int BIT_AND = 102;
  int BIT_OR = 103;
  int XOR = 104;
  int REM = 105;
  int LSHIFT = 106;
  int RSIGNEDSHIFT = 107;
  int RUNSIGNEDSHIFT = 108;
  int PLUSASSIGN = 109;
  int MINUSASSIGN = 110;
  int STARASSIGN = 111;
  int SLASHASSIGN = 112;
  int ANDASSIGN = 113;
  int ORASSIGN = 114;
  int XORASSIGN = 115;
  int REMASSIGN = 116;
  int LSHIFTASSIGN = 117;
  int RSIGNEDSHIFTASSIGN = 118;
  int RUNSIGNEDSHIFTASSIGN = 119;

  int DEFAULT = 0;
  int IN_FORMAL_COMMENT = 1;
  int IN_MULTI_LINE_COMMENT = 2;

  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "\"\\f\"",
    "<SINGLE_LINE_COMMENT>",
    "<token of kind 7>",
    "\"/*\"",
    "\"*/\"",
    "\"*/\"",
    "<token of kind 11>",
    "\"abstract\"",
    "\"boolean\"",
    "\"break\"",
    "\"byte\"",
    "\"case\"",
    "\"catch\"",
    "\"char\"",
    "\"class\"",
    "\"const\"",
    "\"continue\"",
    "\"default\"",
    "\"do\"",
    "\"double\"",
    "\"else\"",
    "\"extends\"",
    "\"false\"",
    "\"final\"",
    "\"finally\"",
    "\"float\"",
    "\"for\"",
    "\"goto\"",
    "\"if\"",
    "\"implements\"",
    "\"import\"",
    "\"instanceof\"",
    "\"int\"",
    "\"interface\"",
    "\"long\"",
    "\"native\"",
    "\"new\"",
    "\"null\"",
    "\"package\"",
    "\"private\"",
    "\"protected\"",
    "\"public\"",
    "\"return\"",
    "\"short\"",
    "\"static\"",
    "\"super\"",
    "\"switch\"",
    "\"synchronized\"",
    "\"this\"",
    "\"throw\"",
    "\"throws\"",
    "\"transient\"",
    "\"true\"",
    "\"try\"",
    "\"void\"",
    "\"volatile\"",
    "\"while\"",
    "\"strictfp\"",
    "<INTEGER_LITERAL>",
    "<DECIMAL_LITERAL>",
    "<HEX_LITERAL>",
    "<OCTAL_LITERAL>",
    "<FLOATING_POINT_LITERAL>",
    "<EXPONENT>",
    "<CHARACTER_LITERAL>",
    "<STRING_LITERAL>",
    "<IDENTIFIER>",
    "<LETTER>",
    "<DIGIT>",
    "\"(\"",
    "\")\"",
    "\"{\"",
    "\"}\"",
    "\"[\"",
    "\"]\"",
    "\";\"",
    "\",\"",
    "\".\"",
    "\"=\"",
    "\">\"",
    "\"<\"",
    "\"!\"",
    "\"~\"",
    "\"?\"",
    "\":\"",
    "\"==\"",
    "\"<=\"",
    "\">=\"",
    "\"!=\"",
    "\"||\"",
    "\"&&\"",
    "\"++\"",
    "\"--\"",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"&\"",
    "\"|\"",
    "\"^\"",
    "\"%\"",
    "\"<<\"",
    "\">>\"",
    "\">>>\"",
    "\"+=\"",
    "\"-=\"",
    "\"*=\"",
    "\"/=\"",
    "\"&=\"",
    "\"|=\"",
    "\"^=\"",
    "\"%=\"",
    "\"<<=\"",
    "\">>=\"",
    "\">>>=\"",
    "\"\\u001a\"",
    "\"~[]\"",
    "\"...\"",
  };

}
