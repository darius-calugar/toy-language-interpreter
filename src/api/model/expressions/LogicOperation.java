package api.model.expressions;

public enum LogicOperation {
    and,        // lhs && rhs
    or,         // lhs || rhs
    xor,        // (lhs || rhs) && !(lhs && rhs)
    nand,       // !(lhs && rhs)
    nor,        // !(lhs || rhs)
    nxor        // !((lhs || rhs) && !(lhs && rhs))
}
