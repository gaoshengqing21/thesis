package JavaExtractor.Common;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;

public enum ContainingNode {
    FOR(ForStmt.class, "For loop"),
    WHILE(WhileStmt.class, "While loop"),
    TERNARY(ConditionalExpr.class, "Ternary"),
    IF(IfStmt.class, "If statement"),
    RETURN(ReturnStmt.class, "Return statement"),
    METHOD(MethodCallExpr.class, "Method call"),
    DO(DoStmt.class, "Do statement"),
    ASSIGN(AssignExpr.class, "Value assignment"),
    ASSERT(AssertStmt.class, "Assertion"),
    VARDECL(VariableDeclarator.class, "Boolean declaration"), //boolean isLeaving = position < EXIT_THRESHOLD;
    OBJCREATION(ObjectCreationExpr.class, "Object creation"), //operation = new RemoveFamily(r < 12);
    EXPRESSION(ExpressionStmt.class, "Lambda expression"), //nodes.stream().filter( it -> it.getComments().size() > 0).count() >= 0;
    FOREACH(ForEachStmt.class, "For Each statement"),
    MEMBERVALUEPAIR(MemberValuePair.class, "Member Value Pair"),
    SINGLEMEMBERANNOTATION(SingleMemberAnnotationExpr.class, "Single Member Annotation"),
    SWITCHENTERY(SwitchEntry.class, "Switch Entry Statement"),
    ARRAYACCESS(ArrayAccessExpr.class, "Array Access Expression"),
    CAST(CastExpr.class, "Cast expression"),
    ARRINIT(ArrayInitializerExpr.class, "Array initialization");


    private Class<? extends Node> nodeClass;
    private String name;

    ContainingNode(Class<? extends Node> nodeClass, String name) {
        this.nodeClass = nodeClass;
        this.name = name;
    }

    public Class<? extends Node> getNodeClass() {
        return nodeClass;
    }

    public String getName() {
        return name;
    }
}
