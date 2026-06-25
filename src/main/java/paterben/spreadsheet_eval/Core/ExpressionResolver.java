package paterben.spreadsheet_eval.Core;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

import paterben.spreadsheet_eval.Model.BinaryOperatorToken;
import paterben.spreadsheet_eval.Model.NumberToken;
import paterben.spreadsheet_eval.Model.Token;

public class ExpressionResolver {
    /**
     * Resolves the value of an expression in postfix notation. The expression
     * mustn't contain any cell references.
     * 
     * @param expression The expression in postfix notation, e.g. 1 3 5 + *.
     * @return The value of the expression, or empty if the expression is invalid.
     */
    public Optional<Double> computeValue(List<Token> postfixExpression) {
        Deque<Double> stack = new ArrayDeque<Double>();
        for (Token token : postfixExpression) {
            switch (token.getType()) {
                case BINARY_OPERATOR:
                    if (stack.size() < 2) {
                        // Invalid expression.
                        return Optional.empty();
                    }
                    double right = stack.pop();
                    double left = stack.pop();
                    BinaryOperatorToken bot = (BinaryOperatorToken) token;
                    switch (bot.getBinaryOperatorType()) {
                        case ADD:
                            stack.push(left + right);
                            break;
                        case SUB:
                            stack.push(left - right);
                            break;
                        case MULT:
                            stack.push(left * right);
                            break;
                        case DIV:
                            if (right == 0) {
                                // Divide-by-zero error.
                                return Optional.empty();
                            }
                            stack.push(left / right);
                            break;
                        default:
                            throw new InternalError("unexpected binary operator type: " + bot.getBinaryOperatorType());

                    }
                    break;
                case CELL_REFERENCE:
                    throw new IllegalArgumentException("resolveValue does not support cell references: " + token);
                case NUMBER:
                    NumberToken nt = (NumberToken) token;
                    stack.push(nt.getValue());
                    break;
                default:
                    throw new InternalError("unexpected token type: " + token.getType());

            }
        }
        if (stack.size() != 1) {
            // Invalid expression.
            return Optional.empty();
        }
        return Optional.of(stack.pop());
    }
}
