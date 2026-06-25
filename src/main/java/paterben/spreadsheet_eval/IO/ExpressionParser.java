package paterben.spreadsheet_eval.IO;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import paterben.spreadsheet_eval.Model.Token;

public class ExpressionParser {
    // Matches any sequence of characters that don't contain spaces.
    private static Pattern ExprPattern = Pattern.compile("[^ ]+");

    /**
     * Parses an expression into a list of tokens.
     * 
     * @param expr The expression, e.g. "1 2 + 3 /".
     * @return The list of tokens.
     */
    public static List<Token> parseExpressionIntoTokens(String expr) {
        Matcher matcher = ExprPattern.matcher(expr);

        List<Token> tokens = new ArrayList<Token>();

        while (matcher.find()) {
            String tokenStr = matcher.group();
            Token token = TokenParser.parseTokenFromString(tokenStr);
            tokens.add(token);
        }

        return tokens;
    }
}
