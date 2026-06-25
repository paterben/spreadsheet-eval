package paterben.spreadsheet_eval.Model;

import java.util.List;

/**
 * A cell at a given row and column, containing an expression.
 */
public class Cell {
    private CellId id;
    private List<Token> expression;

    public Cell(CellId id, List<Token> expression) {
        this.expression = expression;
    }

    public CellId getId() {
        return id;
    }

    public void setId(CellId id) {
        this.id = id;
    }

    public List<Token> getExpression() {
        return expression;
    }

    public void setExpression(List<Token> expression) {
        this.expression = expression;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((expression == null) ? 0 : expression.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cell other = (Cell) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (expression == null) {
            if (other.expression != null)
                return false;
        } else if (!expression.equals(other.expression))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Cell [id=" + id + ", expression=" + expression + "]";
    }
}
