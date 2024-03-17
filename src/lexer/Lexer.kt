package lexer

import lexer.ListTokenManager.Companion.removeTokenFromString
import token.Token


class Lexer(private val TokenGenerator : List<RegexTokenGenerator>) : LexerInterface {
    override fun lex(line: String, numberLine : Int): List<Token> {
        var  analyzeLine = line
        val tokens = mutableListOf<Token>()
        TokenGenerator.forEach { tokenGenerator ->
            val token = tokenGenerator.generateToken(analyzeLine, numberLine)
            if (token != null) {
                tokens.add(token)
                analyzeLine = removeTokenFromString(analyzeLine, token)
            }
        }
        return tokens
    }
}