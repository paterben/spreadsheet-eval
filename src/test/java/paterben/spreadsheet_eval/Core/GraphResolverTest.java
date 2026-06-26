package paterben.spreadsheet_eval.Core;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import paterben.spreadsheet_eval.Model.BinaryOperatorToken;
import paterben.spreadsheet_eval.Model.BinaryOperatorTokenType;
import paterben.spreadsheet_eval.Model.Cell;
import paterben.spreadsheet_eval.Model.CellId;
import paterben.spreadsheet_eval.Model.CellReferenceToken;
import paterben.spreadsheet_eval.Model.NumberToken;

// These tests use the real expression resolver.
public class GraphResolverTest {
    private ExpressionResolver expressionResolver = new ExpressionResolver();
    private GraphResolver graphResolver = new GraphResolver(expressionResolver);

    @Test
    void testEmpty() {
        // Arrange
        List<List<Cell>> cells = List.of();

        // Act
        List<List<Optional<Double>>> rows = graphResolver.resolve(cells);

        // Assert
        assertThat(rows).isEmpty();
    }

    @Test
    void testSimpleCellReferences() {
        // Arrange
        Cell a1 = new Cell(new CellId("A", 1), List.of(new NumberToken(3)));
        Cell b1 = new Cell(new CellId("B", 1), List.of(
                new CellReferenceToken(new CellId("A", 2)),
                new CellReferenceToken(new CellId("B", 2)),
                new BinaryOperatorToken(BinaryOperatorTokenType.MULT)));
        Cell a2 = new Cell(new CellId("A", 2), List.of(new NumberToken(4)));
        Cell b2 = new Cell(new CellId("B", 2), List.of(new NumberToken(5)));
        List<List<Cell>> cells = List.of(List.of(a1, b1), List.of(a2, b2));

        // Act
        List<List<Optional<Double>>> rows = graphResolver.resolve(cells);

        // Assert
        assertThat(rows).hasSize(2);
        assertThat(rows.get(0)).containsExactly(Optional.of(3.0), Optional.of(20.0));
        assertThat(rows.get(1)).containsExactly(Optional.of(4.0), Optional.of(5.0));
    }

    @Test
    void testInvalidCellReference() {
        // Arrange
        Cell a1 = new Cell(new CellId("A", 1), List.of(new CellReferenceToken(new CellId("A", 3))));
        Cell b1 = new Cell(new CellId("B", 1), List.of(new NumberToken(3)));
        List<List<Cell>> cells = List.of(List.of(a1, b1));

        // Act
        List<List<Optional<Double>>> rows = graphResolver.resolve(cells);

        // Assert
        assertThat(rows).hasSize(1);
        assertThat(rows.get(0)).containsExactly(Optional.empty(), Optional.of(3.0));
    }

    @Test
    void testSelfCellReference() {
        // Arrange
        Cell a1 = new Cell(new CellId("A", 1), List.of(new CellReferenceToken(new CellId("A", 1))));
        Cell b1 = new Cell(new CellId("B", 1), List.of(new NumberToken(3)));
        List<List<Cell>> cells = List.of(List.of(a1, b1));

        // Act
        List<List<Optional<Double>>> rows = graphResolver.resolve(cells);

        // Assert
        assertThat(rows).hasSize(1);
        assertThat(rows.get(0)).containsExactly(Optional.empty(), Optional.of(3.0));
    }

    @Test
    void testCellReferenceCycle() {
        // Arrange
        Cell a1 = new Cell(new CellId("A", 1), List.of(new CellReferenceToken(new CellId("B", 1))));
        Cell b1 = new Cell(new CellId("B", 1), List.of(new CellReferenceToken(new CellId("C", 1))));
        Cell c1 = new Cell(new CellId("C", 1), List.of(new CellReferenceToken(new CellId("A", 1))));
        Cell d1 = new Cell(new CellId("D", 1), List.of(new NumberToken(3)));
        Cell e1 = new Cell(new CellId("E", 1), List.of(new CellReferenceToken(new CellId("D", 1))));
        List<List<Cell>> cells = List.of(List.of(a1, b1, c1, d1, e1));

        // Act
        List<List<Optional<Double>>> rows = graphResolver.resolve(cells);

        // Assert
        assertThat(rows).hasSize(1);
        assertThat(rows.get(0)).containsExactly(Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(3.0),
                Optional.of(3.0));
    }

    @Test
    void testCellReferenceToInvalid() {
        // Arrange
        Cell a1 = new Cell(new CellId("A", 1), List.of(new BinaryOperatorToken(BinaryOperatorTokenType.ADD)));
        Cell b1 = new Cell(new CellId("B", 1), List.of(new CellReferenceToken(new CellId("A", 1))));
        Cell c1 = new Cell(new CellId("C", 1), List.of(new NumberToken(3)));
        List<List<Cell>> cells = List.of(List.of(a1, b1, c1));

        // Act
        List<List<Optional<Double>>> rows = graphResolver.resolve(cells);

        // Assert
        assertThat(rows).hasSize(1);
        assertThat(rows.get(0)).containsExactly(Optional.empty(), Optional.empty(), Optional.of(3.0));
    }

    @Test
    void testComplex() {
        // Arrange
        Cell a1 = new Cell(new CellId("A", 1), List.of(new NumberToken(10)));
        Cell b1 = new Cell(new CellId("B", 1), List.of(
                new NumberToken(1),
                new NumberToken(3),
                new BinaryOperatorToken(BinaryOperatorTokenType.ADD)));
        Cell c1 = new Cell(new CellId("C", 1), List.of(
                new NumberToken(2),
                new NumberToken(3),
                new BinaryOperatorToken(BinaryOperatorTokenType.SUB)));
        Cell a2 = new Cell(new CellId("A", 2), List.of(
                new CellReferenceToken(new CellId("B", 1)),
                new CellReferenceToken(new CellId("B", 2)),
                new BinaryOperatorToken(BinaryOperatorTokenType.MULT)));
        Cell b2 = new Cell(new CellId("B", 2), List.of(
                new CellReferenceToken(new CellId("A", 1))));
        Cell c2 = new Cell(new CellId("C", 2), List.of(
                new CellReferenceToken(new CellId("B", 1)),
                new CellReferenceToken(new CellId("A", 2)),
                new BinaryOperatorToken(BinaryOperatorTokenType.DIV),
                new CellReferenceToken(new CellId("C", 1)),
                new BinaryOperatorToken(BinaryOperatorTokenType.ADD)));
        Cell a3 = new Cell(new CellId("A", 3), List.of(new BinaryOperatorToken(BinaryOperatorTokenType.ADD)));
        Cell b3 = new Cell(new CellId("B", 3), List.of(
                new NumberToken(1),
                new NumberToken(2),
                new NumberToken(3)));
        Cell c3 = new Cell(new CellId("C", 3), List.of(new CellReferenceToken(new CellId("C", 3))));
        Cell a4 = new Cell(new CellId("A", 4), List.of(
                new NumberToken(1),
                new NumberToken(7),
                new BinaryOperatorToken(BinaryOperatorTokenType.DIV)));
        Cell b4 = new Cell(new CellId("B", 4), List.of(
                new NumberToken(1),
                new NumberToken(10),
                new BinaryOperatorToken(BinaryOperatorTokenType.DIV)));
        Cell c4 = new Cell(new CellId("C", 4), List.of(
                new CellReferenceToken(new CellId("A", 4)),
                new CellReferenceToken(new CellId("B", 4)),
                new BinaryOperatorToken(BinaryOperatorTokenType.DIV)));
        List<List<Cell>> cells = List.of(
                List.of(a1, b1, c1),
                List.of(a2, b2, c2),
                List.of(a3, b3, c3),
                List.of(a4, b4, c4));

        // Act
        List<List<Optional<Double>>> rows = graphResolver.resolve(cells);

        // Assert
        assertThat(rows).hasSize(4);
        assertThat(rows.get(0)).containsExactly(Optional.of(10.0), Optional.of(4.0), Optional.of(-1.0));
        assertThat(rows.get(1)).containsExactly(Optional.of(40.0), Optional.of(10.0), Optional.of(-0.9));
        assertThat(rows.get(2)).containsExactly(Optional.empty(), Optional.empty(), Optional.empty());
        assertThat(rows.get(3)).containsExactly(Optional.of(0.14285714285714285), Optional.of(0.1), Optional.of(1.4285714285714284));
    }
}
