package paterben.spreadsheet_eval.IO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import paterben.spreadsheet_eval.Model.BinaryOperatorToken;
import paterben.spreadsheet_eval.Model.BinaryOperatorTokenType;
import paterben.spreadsheet_eval.Model.CellReferenceToken;
import paterben.spreadsheet_eval.Model.Column;
import paterben.spreadsheet_eval.Model.NumberToken;
import paterben.spreadsheet_eval.Model.Token;

public class TokenParser {
    // Group 1: column name, group 2: row num.
    private static Pattern CellRefPattern = Pattern.compile("^([a-zA-Z]+)(\\d+)$");

    // Matches decimals and integers, e.g. 12, -3.14, .5.
    private static Pattern NumberPattern = Pattern.compile("^[+-]?\\d*\\.?\\d+$");

    /**
     * Parses a string into a token.
     * @param s The string representing an individual token:
     *   - A number e.g. "10" or "3.14"
     *   - A binary operator e.g. "+", "-", "*" or "/"
     *   - A cell reference e.g. "A3"
     * @return The token.
     */
    public static Token parseTokenFromString(String s) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("null or empty input string");
        }

        if (s.equals("+")) {
            return new BinaryOperatorToken(BinaryOperatorTokenType.ADD);
        }
        if (s.equals("-")) {
            return new BinaryOperatorToken(BinaryOperatorTokenType.SUB);
        }
        if (s.equals("*")) {
            return new BinaryOperatorToken(BinaryOperatorTokenType.MULT);
        }
        if (s.equals("/")) {
            return new BinaryOperatorToken(BinaryOperatorTokenType.DIV);
        }

        Matcher cellRefMatcher = CellRefPattern.matcher(s);
        if (cellRefMatcher.matches()) {
            String columnStr = cellRefMatcher.group(1);
            Column column = Column.fromString(columnStr);
            int row = Integer.parseInt(cellRefMatcher.group(2));
            return new CellReferenceToken(column, row);
        }

        Matcher numberMatcher = NumberPattern.matcher(s);
        if (numberMatcher.matches()) {
            double value = Double.parseDouble(numberMatcher.group());
            return new NumberToken(value);
        }

        throw new IllegalArgumentException("invalid token: " + s);
    }
}
