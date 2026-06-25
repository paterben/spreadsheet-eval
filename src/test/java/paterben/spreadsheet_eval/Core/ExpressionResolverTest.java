package paterben.spreadsheet_eval.Core;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import paterben.spreadsheet_eval.Model.BinaryOperatorToken;
import paterben.spreadsheet_eval.Model.BinaryOperatorTokenType;
import paterben.spreadsheet_eval.Model.NumberToken;
import paterben.spreadsheet_eval.Model.Token;

public class ExpressionResolverTest {
    private ExpressionResolver resolver = new ExpressionResolver();

    @Test
    void valueTest() {
        List<Token> tokens = List.of(
                new NumberToken(1.5));
        assertThat(resolver.computeValue(tokens)).hasValue(1.5);
    }

    @Test
    void simpleOperatorTest() {
        List<Token> tokens = List.of(
                new NumberToken(1),
                new NumberToken(2),
                new BinaryOperatorToken(BinaryOperatorTokenType.ADD));
        assertThat(resolver.computeValue(tokens)).hasValue(3.0);
    }

    @Test
    void complexTest() {
        List<Token> tokens = List.of(
                new NumberToken(1),
                new NumberToken(2),
                new NumberToken(3),
                new NumberToken(4.5),
                new BinaryOperatorToken(BinaryOperatorTokenType.ADD),
                new BinaryOperatorToken(BinaryOperatorTokenType.SUB),
                new NumberToken(5),
                new BinaryOperatorToken(BinaryOperatorTokenType.DIV),
                new BinaryOperatorToken(BinaryOperatorTokenType.MULT));
        assertThat(resolver.computeValue(tokens)).hasValue(-1.1);
    }

}
