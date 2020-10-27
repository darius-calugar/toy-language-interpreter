package api.model.expressions;
import api.model.exceptions.MyException;
import api.model.collections.IDictionary;
import api.model.values.IValue;

public interface IExpression {
    IValue evaluate(IDictionary<String,IValue> symTable) throws MyException;
}
