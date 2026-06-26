package paterben.spreadsheet_eval.Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import paterben.spreadsheet_eval.Model.Cell;
import paterben.spreadsheet_eval.Model.CellId;
import paterben.spreadsheet_eval.Model.CellReferenceToken;
import paterben.spreadsheet_eval.Model.NumberToken;
import paterben.spreadsheet_eval.Model.Token;
import paterben.spreadsheet_eval.Model.TokenType;

public class GraphResolver {
    private ExpressionResolver expressionResolver;

    public GraphResolver(ExpressionResolver expressionResolver) {
        this.expressionResolver = expressionResolver;
    }

    /**
     * Resolves the values of all expressions in all cells, following cell
     * references.
     * 
     * @param cells The list of input cells, with one list of cells per row.
     * @return The values of the expressions in the same order as the input cells,
     *         with empty values for invalid expressions.
     */
    public List<List<Optional<Double>>> resolve(List<List<Cell>> cells) {
        Map<CellId, Cell> cellsMap = new HashMap<CellId, Cell>();
        for (List<Cell> row : cells) {
            for (Cell cell : row) {
                cellsMap.put(cell.getId(), cell);
            }
        }
        Map<CellId, Optional<Double>> values = new HashMap<CellId, Optional<Double>>();
        List<List<Optional<Double>>> output = new ArrayList<>();
        for (List<Cell> row : cells) {
            List<Optional<Double>> outputRow = new ArrayList<>();
            for (Cell cell : row) {
                Optional<Double> value = resolveValueDfs(cell, cellsMap, values,
                        new HashSet<CellId>(Set.of(cell.getId())));
                outputRow.add(value);
            }
            output.add(outputRow);
        }

        return output;
    }

    // Depth-first search with memoization and cycle detection.
    private Optional<Double> resolveValueDfs(Cell cell, Map<CellId, Cell> cells, Map<CellId, Optional<Double>> values,
            HashSet<CellId> path) {
        if (values.containsKey(cell.getId()))
            return values.get(cell.getId());

        List<Token> resolvedExpression = new ArrayList<Token>();
        for (Token token : cell.getExpression()) {
            if (token.getType() == TokenType.CELL_REFERENCE) {
                CellReferenceToken crt = (CellReferenceToken) token;
                CellId refId = crt.getCellId();
                if (!cells.containsKey(refId)) {
                    // Invalid cell reference.
                    values.put(cell.getId(), Optional.empty());
                    return Optional.empty();
                }
                if (path.contains(refId)) {
                    // Cycle detected.
                    values.put(cell.getId(), Optional.empty());
                    return Optional.empty();
                }
                path.add(refId);
                resolveValueDfs(cells.get(refId), cells, values, path);
                path.remove(refId);
                if (!values.containsKey(refId)) {
                    throw new InternalError("unexpected unresolved value for cell ID" + refId);
                }
                Optional<Double> value = values.get(refId);
                if (value.isEmpty()) {
                    // Referenced cell is error value, therefore this one is too.
                    values.put(cell.getId(), Optional.empty());
                    return Optional.empty();
                }
                NumberToken nt = new NumberToken(value.get());
                resolvedExpression.add(nt);
            } else {
                resolvedExpression.add(token);
            }
        }

        Optional<Double> resolvedValue = expressionResolver.computeValue(resolvedExpression);
        values.put(cell.getId(), resolvedValue);
        return resolvedValue;
    }
}
