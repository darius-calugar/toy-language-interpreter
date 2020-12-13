package api;

import api.model.expressions.*;
import api.model.statements.*;
import api.model.types.BoolType;
import api.model.types.IntType;
import api.model.types.RefType;
import api.model.types.StringType;
import api.model.values.BoolValue;
import api.model.values.IntValue;
import api.model.values.StringValue;

public enum BuiltInExamples {
    /**
     int v;
     <br/>v = 2;
     <br/>print(v);
     */
    EXAMPLE1(new IStatement[]{
            new DeclareStatement("v", new IntType()),
            new AssignStatement("v", new ValueExpression(new IntValue(2))),
            new PrintStatement(new VariableExpression("v")),
    }),

    /**
     int a;
     <br/>int b;
     <br/>a = 2 + 3 * 5;
     <br/>b = a + 1;
     <br/>print(b);
     */
    EXAMPLE2(new IStatement[]{
            new DeclareStatement("a", new IntType()),
            new DeclareStatement("b", new IntType()),
            new AssignStatement("a", new ArithmeticExpression(
                    new ValueExpression(new IntValue(2)),
                    new ArithmeticExpression(
                            new ValueExpression(new IntValue(3)),
                            new ValueExpression(new IntValue(5)),
                            ArithmeticOperation.multiply
                    ),
                    ArithmeticOperation.add
            )),
            new AssignStatement("b", new ArithmeticExpression(
                    new VariableExpression("a"),
                    new ValueExpression(new IntValue(1)),
                    ArithmeticOperation.add
            )),
            new PrintStatement(new VariableExpression("b")),
    }),

    /**
     bool a;
     <br/>int v;
     <br/>a=true;
     <br/>if(a) v = 2;
     <br/>else  v = 3;
     <br/>print(v);
     */
    EXAMPLE3(new IStatement[]{
            new DeclareStatement("a", new BoolType()),
            new DeclareStatement("v", new IntType()),
            new AssignStatement("a", new ValueExpression(new BoolValue(true))),
            new IfStatement(new VariableExpression("a"),
                    new AssignStatement("v", new ValueExpression(new IntValue(2))),
                    new AssignStatement("v", new ValueExpression(new IntValue(3)))
            ),
            new PrintStatement(new VariableExpression("v"))
    }),

    /**
     string varf;
     <br/>varf="test.in";
     <br/>openRFile(varf);
     <br/>int varc;
     <br/>readFile(varf,varc);
     <br/>print(varc);
     <br/>readFile(varf,varc);
     <br/>print(varc);
     <br/>closeRFile(varf)
     */
    EXAMPLE4(new IStatement[]{
            new DeclareStatement("varf", new StringType()),
            new AssignStatement("varf", new ValueExpression(new StringValue("resources\\test.in"))),
            new OpenReadFileStatement(new VariableExpression("varf")),
            new DeclareStatement("varc", new IntType()),
            new ReadFileStatement(new VariableExpression("varf"), "varc"),
            new PrintStatement(new VariableExpression("varc")),
            new ReadFileStatement(new VariableExpression("varf"), "varc"),
            new PrintStatement(new VariableExpression("varc")),
            new CloseReadFileStatement(new VariableExpression("varf"))

    }),

    /**
     int v;
     <br/>v=4;
     <br/>while (v>0) {
     <br/>print(v);
     <br/>v=v-1;
     <br/>}
     <br/>print(v);
     */
    EXAMPLE5(new IStatement[]{
            new DeclareStatement("v", new IntType()),
            new AssignStatement("v", new ValueExpression(new IntValue(4))),
            new WhileStatement(
                    new RelationalExpression(
                            new VariableExpression("v"),
                            new ValueExpression(new IntValue(0)),
                            RelationalOperation.greater),
                    IStatement.foldStatements(new IStatement[]{
                            new PrintStatement(new VariableExpression("v")),
                            new AssignStatement("v", new ArithmeticExpression(
                                    new VariableExpression("v"),
                                    new ValueExpression(new IntValue(1)),
                                    ArithmeticOperation.subtract)),
                    })
            ),
            new PrintStatement(new VariableExpression("v"))
    }),

    /**
     Ref int v;
     <br\>new(v,20);
     <br\>Ref Ref int a;
     <br\>new(a,v);
     <br\>print(v);
     <br\>print(a)
     */
    EXAMPLE6(new IStatement[]{
            new DeclareStatement("v", new RefType(new IntType())),
            new HeapAllocateStatement("v", new ValueExpression(new IntValue(20))),
            new DeclareStatement("a", new RefType(new RefType(new IntType()))),
            new HeapAllocateStatement("a", new VariableExpression("v")),
            new PrintStatement(new VariableExpression("v")),
            new PrintStatement(new VariableExpression("a")),
    }),

    /**
     Ref int v;
     <br\>new(v,20);
     <br\>Ref Ref int a;
     <br\>new(a,v);
     <br\>print(rH(v));
     <br\>print(rH(rH(a))+5);
     */
    EXAMPLE7(new IStatement[]{
            new DeclareStatement("v", new RefType(new IntType())),
            new HeapAllocateStatement("v", new ValueExpression(new IntValue(20))),
            new DeclareStatement("a", new RefType(new RefType(new IntType()))),
            new HeapAllocateStatement("a", new VariableExpression("v")),
            new PrintStatement(new HeapReadExpression(new VariableExpression("v"))),
            new PrintStatement(new ArithmeticExpression(
                    new HeapReadExpression(new HeapReadExpression(new VariableExpression("a"))),
                    new ValueExpression(new IntValue(5)),
                    ArithmeticOperation.add
            ))
    }),

    /**
     Ref int v;
     <br\>new(v,20);
     <br\>Ref Ref int a;
     <br\>new(a,v);
     <br\>new(v,30);
     <br\>print(rH(rH(a)))
     */
    EXAMPLE8(new IStatement[]{
            new DeclareStatement("v", new RefType(new IntType())),
            new HeapAllocateStatement("v", new ValueExpression(new IntValue(20))),
            new DeclareStatement("a", new RefType(new RefType(new IntType()))),
            new HeapAllocateStatement("a", new VariableExpression("v")),
            new HeapAllocateStatement("v", new ValueExpression(new IntValue(30))),
            new PrintStatement(new HeapReadExpression(new HeapReadExpression(new VariableExpression("a")))),
    }),

    /**
     int v;
     <br/>Ref int a;
     <br/>v=10;
     <br/>new(a,22);
     <br/>fork {
     <br/>   wH(a,30);
     <br/>   v=32;print(v);
     <br/>   print(rH(a));
     <br/>}
     <br/>print(v);print(rH(a))
     */
    EXAMPLE9(new IStatement[]{
            new DeclareStatement("v", new IntType()),
            new DeclareStatement("a", new RefType(new IntType())),
            new AssignStatement("v", new ValueExpression(new IntValue(10))),
            new HeapAllocateStatement("a", new ValueExpression(new IntValue(22))),
            new ForkStatement(IStatement.foldStatements(new IStatement[]{
                    new HeapWriteStatement("a", new ValueExpression(new IntValue(30))),
                    new AssignStatement("v", new ValueExpression(new IntValue(32))),
                    new PrintStatement(new VariableExpression("v")),
                    new PrintStatement(new HeapReadExpression(new VariableExpression("a"))),
            })),
            new PrintStatement(new VariableExpression("v")),
            new PrintStatement(new HeapReadExpression(new VariableExpression("a"))),
    }),

    EXAMPLE9BROKEN(new IStatement[]{
            new DeclareStatement("v", new IntType()),
            new DeclareStatement("a", new RefType(new IntType())),
            new AssignStatement("v", new ValueExpression(new StringValue("10"))), // <-- Broken Here
            new HeapAllocateStatement("a", new ValueExpression(new IntValue(22))),
            new ForkStatement(IStatement.foldStatements(new IStatement[]{
                    new HeapWriteStatement("a", new ValueExpression(new IntValue(30))),
                    new AssignStatement("v", new ValueExpression(new IntValue(32))),
                    new PrintStatement(new VariableExpression("v")),
                    new PrintStatement(new HeapReadExpression(new VariableExpression("a"))),
            })),
            new PrintStatement(new VariableExpression("v")),
            new PrintStatement(new HeapReadExpression(new VariableExpression("a"))),
    }),
    ;

    private final IStatement[] statements;

    BuiltInExamples(IStatement[] statements) {this.statements = statements;}

    public IStatement[] getStatements()      {return statements;}

    public IStatement getStatementTree()     {return IStatement.foldStatements(statements);}
}
