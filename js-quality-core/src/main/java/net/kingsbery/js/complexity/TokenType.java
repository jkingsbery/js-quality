package net.kingsbery.js.complexity;

public enum TokenType {
	ERROR(-1), // well-known as the only code < EOF
	EOF(0), // end of file token - (not EOF_CHAR)
	EOL(1), // end of line

	// Interpreter reuses the following as bytecodes
	FIRST_BYTECODE_TOKEN(2),

	ENTERWITH(2), LEAVEWITH(3), RETURN(4), GOTO(5), IFEQ(6), IFNE(7), SETNAME(8), BITOR(
			9), BITXOR(10), BITAND(11), EQ(12), NE(13), LT(14), LE(15), GT(16), GE(
			17), LSH(18), RSH(19), URSH(20), ADD(21), SUB(22), MUL(23), DIV(24), MOD(
			25), NOT(26), BITNOT(27), POS(28), NEG(29), NEW(30), DELPROP(31), TYPEOF(
			32), GETPROP(33), GETPROPNOWARN(34), SETPROP(35), GETELEM(36), SETELEM(
			37), CALL(38), NAME(39), NUMBER(40), STRING(41), NULL(42), THIS(43), FALSE(
			44), TRUE(45), SHEQ(46), // shallow equality (===)
	SHNE(47), // shallow inequality (!==)
	REGEXP(48), BINDNAME(49), THROW(50), RETHROW(51), // rethrow caught
														// exception: catch (e
														// if ) use it
	IN(52), INSTANCEOF(53), LOCAL_LOAD(54), GETVAR(55), SETVAR(56), CATCH_SCOPE(
			57), ENUM_INIT_KEYS(58), ENUM_INIT_VALUES(59), ENUM_INIT_ARRAY(60), ENUM_NEXT(
			61), ENUM_ID(62), THISFN(63), RETURN_RESULT(64), // to return
																// previously
																// stored
																// return result
	ARRAYLIT(65), // array literal
	OBJECTLIT(66), // object literal
	GET_REF(67), // *reference
	SET_REF(68), // *reference (something
	DEL_REF(69), // delete reference
	REF_CALL(70), // f(args) (something or f(args)++
	REF_SPECIAL(71), // reference for special properties like __proto
	YIELD(72), // JS 1.7 yield pseudo keyword

	// For XML support:
	DEFAULTNAMESPACE(73), // default xml namespace =
	ESCXMLATTR(74), ESCXMLTEXT(75), REF_MEMBER(76), // Reference for
													// x.@y), x..y
													// etc.
	REF_NS_MEMBER(77), // Reference for x.ns::y), x..ns::y etc.
	REF_NAME(78), // Reference for @y), @[y] etc.
	REF_NS_NAME(79), // Reference for ns::y), @ns::y@[y] etc.

	TRY(80), SEMI(81), // semicolon
	LB(82), // left and right brackets
	RB(83), LC(84), // left and right curlies (braces)
	RC(85), LP(86), // left and right parentheses
	RP(87), COMMA(88), // comma operator

	ASSIGN(89), // simple assignment (=)
	ASSIGN_BITOR(90), // |=
	ASSIGN_BITXOR(91), // ^=
	ASSIGN_BITAND(92), // |=
	ASSIGN_LSH(93), // <<=
	ASSIGN_RSH(94), // >>=
	ASSIGN_URSH(95), // >>>=
	ASSIGN_ADD(96), // +=
	ASSIGN_SUB(97), // -=
	ASSIGN_MUL(98), // *=
	ASSIGN_DIV(99), // /=
	ASSIGN_MOD(100), // %=

	HOOK(101), // conditional (?:)
	COLON(102), OR(103), // logical or (||)
	AND(104), // logical and (&&)
	INC(105), // increment/decrement (++ --)
	DEC(106), DOT(107), // member operator (.)
	FUNCTION(108), // function keyword
	EXPORT(109), // export keyword
	IMPORT(110), // import keyword
	IF(111), // if keyword
	ELSE(112), // else keyword
	SWITCH(113), // switch keyword
	CASE(114), // case keyword
	DEFAULT(115), // default keyword
	WHILE(116), // while keyword
	DO(117), // do keyword
	FOR(118), // for keyword
	BREAK(119), // break keyword
	CONTINUE(120), // continue keyword
	VAR(121), // var keyword
	WITH(122), // with keyword
	CATCH(123), // catch keyword
	FINALLY(124), // finally keyword
	VOID(125), // void keyword
	RESERVED(126), // reserved keywords

	EMPTY(127),

	/*
	 * types used for the parse tree - these never get returned by the scanner.
	 */

	BLOCK(128), // statement block
	LABEL(129), // label
	TARGET(130), LOOP(131), EXPR_VOID(132), // expression statement
											// in functions
	EXPR_RESULT(133), // expression statement in scripts
	JSR(134), SCRIPT(135), // top-level node for entire script
	TYPEOFNAME(136), // for typeof(simple-name)
	USE_STACK(137), SETPROP_OP(138), // x.y op(something
	SETELEM_OP(139), // x[y] op(something
	LOCAL_BLOCK(140), SET_REF_OP(141), // *reference op= something

	// For XML support:
	DOTDOT(142), // member operator (..)
	COLONCOLON(143), // namespace::name
	XML(144), // XML type
	DOTQUERY(145), // .() -- e.g.), x.emps.emp.(name =("terry")
	XMLATTR(146), // @
	XMLEND(147),
	// Optimizer-only-tokens
	TO_OBJECT(148), TO_DOUBLE(149),

	GET(150), // JS 1.5 get pseudo keyword
	SET(151), // JS 1.5 set pseudo keyword
	LET(152), // JS 1.7 let pseudo keyword
	CONST(153), SETCONST(154), SETCONSTVAR(155), ARRAYCOMP(156), // array
																	// comprehension
	LETEXPR(157), WITHEXPR(158), DEBUGGER(159), LAST_TOKEN(159);

	int val;

	TokenType(int val) {
		this.val = val;
	}

	public static TokenType fromVal(int val) {
		for (TokenType type : TokenType.values()) {
			if (type.val == val) {
				return type;
			}
		}
		return null;
	}

	public static TokenType getValue(int typeCode) {
		for(TokenType type : TokenType.values()){
			if(type.val==typeCode){
				return type;
			}
		}
		return null;
	}
}
