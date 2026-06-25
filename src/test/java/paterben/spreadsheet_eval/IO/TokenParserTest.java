package paterben.spreadsheet_eval.IO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import paterben.spreadsheet_eval.Model.BinaryOperatorToken;
import paterben.spreadsheet_eval.Model.BinaryOperatorTokenType;
import paterben.spreadsheet_eval.Model.CellReferenceToken;
import paterben.spreadsheet_eval.Model.Column;
import paterben.spreadsheet_eval.Model.NumberToken;

public class TokenParserTest {
    @Test
    void testParseCellReference() {
        assertEquals(
                new CellReferenceToken(new Column("AA"), 11),
                TokenParser.parseTokenFromString("aa11"));
        assertEquals(
                new CellReferenceToken(new Column("Z"), 3),
                TokenParser.parseTokenFromString("Z3"));
    }

    @Test
    void testParseNumber() {
        assertEquals(new NumberToken(11), TokenParser.parseTokenFromString("11"));
        assertEquals(new NumberToken(1.34), TokenParser.parseTokenFromString("1.34"));
    }

    @Test
    void testParseBinaryOperators() {
        assertEquals(new BinaryOperatorToken(BinaryOperatorTokenType.ADD), TokenParser.parseTokenFromString("+"));
        assertEquals(new BinaryOperatorToken(BinaryOperatorTokenType.SUB), TokenParser.parseTokenFromString("-"));
        assertEquals(new BinaryOperatorToken(BinaryOperatorTokenType.MULT), TokenParser.parseTokenFromString("*"));
        assertEquals(new BinaryOperatorToken(BinaryOperatorTokenType.DIV), TokenParser.parseTokenFromString("/"));
    }

    @Test
    void testParseEmptyString() {
        try {
            TokenParser.parseTokenFromString("");
            fail();
        } catch (IllegalArgumentException e) {
            // Assert
            assertThat(e.getMessage()).contains("empty input string");
        }
    }

    @Test
    void testParseInvalidToken() {
        try {
            TokenParser.parseTokenFromString("abc");
            fail();
        } catch (IllegalArgumentException e) {
            // Assert
            assertThat(e.getMessage()).contains("invalid token");
        }
    }
}
