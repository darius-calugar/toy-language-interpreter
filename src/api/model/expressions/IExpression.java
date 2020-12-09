package api.model.expressions;

import api.model.collections.IMap;
import api.model.collections.IHeap;
import api.model.exceptions.MyException;
import api.model.types.IType;
import api.model.values.IValue;

/**
 Interface for evaluable expressions
 */
public interface IExpression {
    /**
     Evaluates the value of the expression accordingly.

     @param symTable Reference to the symbol table of the program state
     @return evaluated value of the expression
     @throws api.model.exceptions.MyException Fatal exception encountered when evaluating the expression.
     @see api.model.ProgramState ProgramState
     */
    IValue evaluate(IMap<String, IValue> symTable, IHeap heap) throws MyException;

    IType typeCheck(IMap<String, IType> typeEnvironment) throws MyException;
}
