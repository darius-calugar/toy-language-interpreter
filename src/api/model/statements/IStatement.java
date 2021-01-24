package api.model.statements;

import api.model.ProgramState;
import api.model.collections.IMap;
import api.model.exceptions.ExpectedRefTypeException;
import api.model.exceptions.InvalidTypeException;
import api.model.exceptions.MyException;
import api.model.types.IType;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public interface IStatement {
    /**
     Execute the statement.
     The program state is updated accordingly.

     @param state Reference to the program state
     @return Reference to updated program state
     @throws MyException Exception occurred when executing the statement
     */
    ProgramState execute(ProgramState state) throws MyException;

    IMap<String, IType> typeCheck(IMap<String, IType> typeEnvironment) throws InvalidTypeException, ExpectedRefTypeException;

    /**
     @return Generated deep copy of the statement.
     */
    IStatement deepCopy();

    static IStatement foldStatements(IStatement[] statements) {
        var statementList = Arrays.asList(statements.clone());
        Collections.reverse(statementList);
        return statementList.stream()
                .reduce(new NullStatement(), (subtotal, element) -> new CompoundStatement(element, subtotal));
    }

    static List<IStatement> unfoldStatement(IStatement statement) {
        var statementList = new LinkedList<IStatement>();
        while (statement instanceof CompoundStatement) {
            statementList.add(((CompoundStatement) statement).lhs);
            statement = ((CompoundStatement) statement).rhs;
        }
        if (statement != null)
            statementList.add(statement);
        return statementList;
    }
}
