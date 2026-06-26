package paterben.spreadsheet_eval.Core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

import paterben.spreadsheet_eval.Model.BinaryOperatorToken;
import paterben.spreadsheet_eval.Model.BinaryOperatorTokenType;
import paterben.spreadsheet_eval.Model.CellId;
import paterben.spreadsheet_eval.Model.CellReferenceToken;
import paterben.spreadsheet_eval.Model.NumberToken;
import paterben.spreadsheet_eval.Model.Token;

public class ExpressionResolverTest {
    private ExpressionResolver resolver = new ExpressionResolver();

    @Test
    void testValue() {
        List<Token> tokens = List.of(
                new NumberToken(1.5));
        assertThat(resolver.computeValue(tokens)).hasValue(1.5);
    }

    @Test
    void testOneOperator() {
        List<Token> tokens = List.of(
                new NumberToken(1),
                new NumberToken(2),
                new BinaryOperatorToken(BinaryOperatorTokenType.ADD));
        assertThat(resolver.computeValue(tokens)).hasValue(3.0);
    }

    @Test
    void testMultipleOperators() {
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

    @Test
    void testEmpty() {
        List<Token> tokens = List.of();
        assertThat(resolver.computeValue(tokens)).isEmpty();
    }

    @Test
    void testTooManyNumbers() {
        List<Token> tokens = List.of(
                new NumberToken(1),
                new NumberToken(2),
                new NumberToken(3),
                new BinaryOperatorToken(BinaryOperatorTokenType.ADD));
        assertThat(resolver.computeValue(tokens)).isEmpty();
    }

    @Test
    void testNotEnoughOperatorArguments() {
        List<Token> tokens = List.of(
                new NumberToken(1),
                new NumberToken(2),
                new BinaryOperatorToken(BinaryOperatorTokenType.ADD),
                new BinaryOperatorToken(BinaryOperatorTokenType.ADD));
        assertThat(resolver.computeValue(tokens)).isEmpty();
    }

    @Test
    void testDivideByZero() {
        List<Token> tokens = List.of(
                new NumberToken(1),
                new NumberToken(2),
                new NumberToken(2),
                new BinaryOperatorToken(BinaryOperatorTokenType.SUB),
                new BinaryOperatorToken(BinaryOperatorTokenType.DIV));
        assertThat(resolver.computeValue(tokens)).isEmpty();
    }

    @Test
    void testCellReferenceNotAllowed() {
        List<Token> tokens = List.of(
                new NumberToken(1),
                new CellReferenceToken(new CellId("A", 1)),
                new BinaryOperatorToken(BinaryOperatorTokenType.ADD));
        try {
            resolver.computeValue(tokens);
            fail();
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).contains("does not support cell references");
        }
    }
}
