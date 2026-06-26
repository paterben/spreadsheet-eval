package paterben.spreadsheet_eval.IO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import paterben.spreadsheet_eval.Model.BinaryOperatorToken;
import paterben.spreadsheet_eval.Model.BinaryOperatorTokenType;
import paterben.spreadsheet_eval.Model.CellId;
import paterben.spreadsheet_eval.Model.CellReferenceToken;
import paterben.spreadsheet_eval.Model.NumberToken;
import paterben.spreadsheet_eval.Model.Token;

public class TokenParser {
    // Matches decimals and integers, e.g. 12, -3.14, .5.
    private static final Pattern NumberPattern = Pattern.compile("^[+-]?\\d*\\.?\\d+$");

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

        if (CellId.isValidCellIdFormat(s)) {
            CellId cellId = CellId.parseFromString(s);
            return new CellReferenceToken(cellId);
        }

        Matcher numberMatcher = NumberPattern.matcher(s);
        if (numberMatcher.matches()) {
            double value = Double.parseDouble(numberMatcher.group());
            return new NumberToken(value);
        }

        throw new IllegalArgumentException("invalid token: " + s);
    }
}
