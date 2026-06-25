package paterben.spreadsheet_eval.Model;

/**
 * A binary operator token, e.g. add, subtract, multiply, divide.
 */
public class BinaryOperatorToken extends Token {

    private BinaryOperatorTokenType binaryOperatorType;

    public BinaryOperatorToken(BinaryOperatorTokenType binaryOperatorType) {
        this.binaryOperatorType = binaryOperatorType;
    }

    public BinaryOperatorTokenType getBinaryOperatorType() {
        return binaryOperatorType;
    }

    public void setBinaryOperatorType(BinaryOperatorTokenType binaryOperatorType) {
        this.binaryOperatorType = binaryOperatorType;
    }

    @Override
    public TokenType getType() {
        return TokenType.BINARY_OPERATOR;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((binaryOperatorType == null) ? 0 : binaryOperatorType.hashCode());
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
        BinaryOperatorToken other = (BinaryOperatorToken) obj;
        if (binaryOperatorType != other.binaryOperatorType)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "BinaryOperatorToken [binaryOperatorType=" + binaryOperatorType + "]";
    }
}
