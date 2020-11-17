package api.model.statements;

import api.model.ProgramState;
import api.model.exceptions.MyException;

import java.util.Arrays;
import java.util.Collections;

public interface IStatement {
    /**
     Execute the statement.
     The program state is updated accordingly.

     @param state Reference to the program state
     @return Reference to updated program state
     @throws MyException Exception occurred when executing the statement
     */
    ProgramState execute(ProgramState state) throws MyException;

    /**
     @return Generated deep copy of the statement.
     */
    IStatement deepCopy();

    static IStatement foldStatements(IStatement[] statements) {
        var statementList = Arrays.asList(statements);
        Collections.reverse(statementList);
        return statementList.stream()
                .reduce(new NullStatement(), (subtotal, element) -> new CompoundStatement(element, subtotal));
    }
}
