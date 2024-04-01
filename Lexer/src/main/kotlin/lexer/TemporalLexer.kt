package org.example.lexer

import org.example.lexer.token.MethodCallRule
import token.DataType
import token.Token

/**
 * The main lexer class responsible for breaking down the source code into tokens. It applies a series of token
 * generation rules to the input source code and compiles a list of tokens for further processing by a parser.
 */
class TemporalLexer {
    /**
     * Tokenizes the given input string into a list of lexical tokens.
     *
     * @return A list of tokens identified in the input string.
     */
    val lexer: Lexer =
        Lexer(
            listOf(
                RegexTokenGenerator("\"(?:\\\\.|[^\"])*\"", DataType.STRING_VALUE, false),
                RegexTokenGenerator("\\blet\\b", DataType.DECLARATION_VARIABLE, true),
                RegexTokenGenerator("\\+", DataType.OPERATOR_PLUS, true),
                RegexTokenGenerator("-", DataType.OPERATOR_MINUS, true),
                RegexTokenGenerator("\\*", DataType.OPERATOR_MULTIPLY, true),
                RegexTokenGenerator("/", DataType.OPERATOR_DIVIDE, true),
                RegexTokenGenerator(":", DataType.DOUBLE_DOTS, true),
                RegexTokenGenerator(";", DataType.SEMICOLON, true),
                RegexTokenGenerator("=", DataType.ASSIGNATION, true),
                RegexTokenGenerator("\\(", DataType.LEFT_PARENTHESIS, true),
                RegexTokenGenerator("\\)", DataType.RIGHT_PARENTHESIS, true),
                RegexTokenGenerator("\\b\\w+\\s*\\((?:[^()]*|\\([^()]*\\))*\\)", DataType.METHOD_CALL, false, MethodCallRule()),
                RegexTokenGenerator(",", DataType.COMA, true),
                RegexTokenGenerator("\\bnumber\\b", DataType.NUMBER_TYPE, true),
                RegexTokenGenerator("\\bstring\\b", DataType.STRING_TYPE, true),
                RegexTokenGenerator("\\b\\d+\\.?\\d*\\b", DataType.NUMBER_VALUE, false),
                RegexTokenGenerator("(?<!\")\\b[a-zA-Z_][a-zA-Z0-9_]*\\b(?!\")", DataType.VARIABLE_NAME, false),
            ),
        )

    fun lex(
        line: String,
        numberLine: Int,
    ): List<Token> {
        val list = ListTokenManager.orderTokens(lexer.lex(line, numberLine))
        return ListTokenManager.removeDuplicates(list)
    }
}
