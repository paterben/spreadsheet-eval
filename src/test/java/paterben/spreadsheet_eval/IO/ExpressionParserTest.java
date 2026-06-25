package paterben.spreadsheet_eval.IO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import paterben.spreadsheet_eval.Model.BinaryOperatorToken;
import paterben.spreadsheet_eval.Model.BinaryOperatorTokenType;
import paterben.spreadsheet_eval.Model.CellId;
import paterben.spreadsheet_eval.Model.CellReferenceToken;
import paterben.spreadsheet_eval.Model.NumberToken;

public class ExpressionParserTest {
    @Test
    void testParseExpression() {
        assertThat(
                ExpressionParser.parseExpressionIntoTokens(" a1   b1 + 2 / "))
                .containsExactly(
                        new CellReferenceToken(new CellId("A", 1)),
                        new CellReferenceToken(new CellId("B", 1)),
                        new BinaryOperatorToken(BinaryOperatorTokenType.ADD),
                        new NumberToken(2),
                        new BinaryOperatorToken(BinaryOperatorTokenType.DIV));
    }

    @Test
    void testParseInvalidExpression() {
        try {
            ExpressionParser.parseExpressionIntoTokens(" a1   b1 + 2 / ,");
            fail();
        } catch (IllegalArgumentException e) {
            // Assert
            assertThat(e.getMessage()).contains("invalid token");
        }
    }
}
