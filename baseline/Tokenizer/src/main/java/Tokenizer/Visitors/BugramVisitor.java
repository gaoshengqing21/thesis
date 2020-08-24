package Tokenizer.Visitors;

import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.UnionType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;

/**
 * A quote from Deep Semantic Feature Learning for Software
 * Defect Prediction by Song Wang et al. Section 3.1.1.
 * This visitor tries to follow those guidelines
 * <p>
 * Specifically, three types of AST
 * node are extracted: 1) nodes of method invocations
 * and class instance creations, e.g., in Figure 3, method
 * createOutput() and openInput() are recorded as
 * their method names, 2) declaration nodes, i.e., method
 * declarations, type declarations, and enum declarations, and
 * 3) control-flow nodes such as while statements, catch
 * clauses, if statements, throw statements, etc. Control-flow
 * nodes are recorded as their statement types, e.g., an if
 * statement is simply recorded as if.
 *
 * In summary, for each
 * file, we obtain a vector of tokens of the three categories. We
 * exclude AST nodes that are not one of these three categories,
 * such as assignment and intrinsic type declaration, because
 * they are often method-specific or class-specific, which may
 * not be generalizable to the whole project
 *
 * ADD Binary expression nodes
 */
public class BugramVisitor extends VoidVisitorAdapter<Object> {
    private final ArrayList<String> tokens;
    private final String END_PREDIX = "END_";

    public BugramVisitor(ArrayList<String> tokens) {
        this.tokens = tokens;
    }

    @Override
    public void visit(MethodCallExpr n, Object arg) {
        tokens.add(n.getNameAsString() + "()");
        //System.out.println(n.getNameAsString());
        super.visit(n, arg);
    }

    @Override
    public void visit(ConstructorDeclaration n, Object arg) {
        tokens.add(n.getClass().getSimpleName());

        //System.out.println(n.getClass().getSimpleName());
        super.visit(n, arg);
    }

    @Override
    public void visit(ObjectCreationExpr n, Object arg) {
        tokens.add(n.getClass().getSimpleName());

        //System.out.println(n.getClass().getSimpleName());
        super.visit(n, arg);
    }

    @Override
    public void visit(ThrowStmt n, Object arg) {
        tokens.add(n.getClass().getSimpleName());

        //System.out.println(n.getClass().getSimpleName());
        super.visit(n, arg);
    }

    @Override
    public void visit(ArrayInitializerExpr n, Object arg) {
        tokens.add(n.getClass().getSimpleName());
        //System.out.println(n.getClass().getSimpleName());
        super.visit(n, arg);
    }

    // This handles "if" and "else if", but not "else"
    @Override
    public void visit(IfStmt n, Object arg) {
        tokens.add(n.getClass().getSimpleName());
        //System.out.println(n.getClass().getSimpleName());
        super.visit(n, arg);
    }

    @Override
    public void visit(ForStmt n, Object arg) {
        tokens.add(n.getClass().getSimpleName());
        //System.out.println(n.getClass().getSimpleName());
        super.visit(n, arg);
        tokens.add(END_PREDIX + n.getClass().getSimpleName());
        //System.out.println(END_PREDIX + n.getClass().getSimpleName());
    }

    @Override
    public void visit(DoStmt n, Object arg) {
        tokens.add(n.getClass().getSimpleName());
        //System.out.println(n.getClass().getSimpleName());
        super.visit(n, arg);
    }

    @Override
    public void visit(WhileStmt n, Object arg) {
        tokens.add(n.getClass().getSimpleName());
        //System.out.println(n.getClass().getSimpleName());
        super.visit(n, arg);
    }

    @Override
    public void visit(ForEachStmt n, Object arg) {
        tokens.add(n.getClass().getSimpleName());
        //System.out.println(n.getClass().getSimpleName());
        super.visit(n, arg);
        tokens.add(END_PREDIX + n.getClass().getSimpleName());
        //System.out.println(END_PREDIX + n.getClass().getSimpleName());
    }

    @Override
    public void visit(BreakStmt n, Object arg) {
        tokens.add(n.getClass().getSimpleName());
        //System.out.println(n.getClass().getSimpleName());
        super.visit(n, arg);
    }

    @Override
    public void visit(ContinueStmt n, Object arg) {
        tokens.add(n.getClass().getSimpleName());
        //System.out.println(n.getClass().getSimpleName());
        super.visit(n, arg);
    }

    @Override
    public void visit(TryStmt n, Object arg) {
        tokens.add(n.getClass().getSimpleName());
        //System.out.println(n.getClass().getSimpleName());
        super.visit(n, arg);
        tokens.add(END_PREDIX + n.getClass().getSimpleName());
        //System.out.println(END_PREDIX + n.getClass().getSimpleName());
    }

    @Override
    public void visit(CatchClause n, Object arg) {
        Parameter parameter = n.getParameter();
        ArrayList<String> exceptionsCaught = new ArrayList<>();

        if (parameter.getType() instanceof ClassOrInterfaceType) {
            ClassOrInterfaceType type = (ClassOrInterfaceType) parameter.getType();
            exceptionsCaught.add(type.getNameAsString());
        } else {
            UnionType parameterType = (UnionType) parameter.getType();
            for (ReferenceType element : parameterType.getElements()) {
                ClassOrInterfaceType castedElement = (ClassOrInterfaceType) element;
                exceptionsCaught.add(castedElement.getNameAsString());
            }
        }
        exceptionsCaught.sort(String.CASE_INSENSITIVE_ORDER);

        for (String exception : exceptionsCaught) {
            tokens.add(n.getClass().getSimpleName() + "." + exception);
            //System.out.println(n.getClass().getSimpleName() + "." + exception);
        }

        super.visit(n, arg);
    }

    //finally block skipped, no default visitor found in JavaParser

    // Made for detecting "else" statements in control flow
    @Override
    public void visit(BlockStmt n, Object arg) {
        if (n.getParentNode().isPresent() && n.getParentNode().get() instanceof IfStmt) {
            IfStmt parentIfStmnt = (IfStmt) n.getParentNode().get();
            Statement parentElseStmnt = parentIfStmnt.getElseStmt().isPresent() ? parentIfStmnt.getElseStmt().get() : null;
            if (n.equals(parentElseStmnt)) {
                tokens.add("elseStmt");
                //System.out.println("elseStmt");
            }
        }
        super.visit(n, arg);
    }

    @Override
    public void visit(ReturnStmt n, Object arg) {
        tokens.add(n.getClass().getSimpleName());
        //System.out.println(n.getClass().getSimpleName());
        super.visit(n, arg);
    }

    @Override
    public void visit(SynchronizedStmt n, Object arg) {
        tokens.add(n.getClass().getSimpleName());
        //System.out.println(n.getClass().getSimpleName());
        super.visit(n, arg);
        tokens.add(END_PREDIX + n.getClass().getSimpleName());
        //System.out.println(END_PREDIX + n.getClass().getSimpleName());
    }

    @Override
    public void visit(SwitchStmt n, Object arg) {
        tokens.add(n.getClass().getSimpleName());
        //System.out.println(n.getClass().getSimpleName());
        super.visit(n, arg);
        tokens.add(END_PREDIX + n.getClass().getSimpleName());
        //System.out.println(END_PREDIX + n.getClass().getSimpleName());
    }

    // Case/default branch skipped, no default visitor found in JavaParser

    @Override
    public void visit(AssertStmt n, Object arg) {
        tokens.add(n.getClass().getSimpleName());
        //System.out.println(n.getClass().getSimpleName());
        super.visit(n, arg);
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
        tokens.add(n.getNameAsString());
        super.visit(n, arg);
    }

    @Override
    public void visit(EnumConstantDeclaration n, Object arg) {
        tokens.add(n.getNameAsString());
        super.visit(n, arg);
    }

    @Override
    public void visit(EnumDeclaration n, Object arg) {
        tokens.add(n.getNameAsString());
        super.visit(n, arg);
    }

    @Override
    public void visit(FieldDeclaration n, Object arg) {
        tokens.add(n.getVariables().get(0).getTypeAsString());
        super.visit(n, arg);
    }

    @Override
    public void visit(VariableDeclarationExpr n, Object arg) {
        tokens.add(n.getVariables().get(0).getTypeAsString());
        super.visit(n, arg);
    }

    @Override
    public void visit(FieldAccessExpr n, Object arg) {
        tokens.add(n.toString());
        super.visit(n, arg);
    }

    @Override
    public void visit(BinaryExpr n, Object arg) {
        tokens.add(n.getLeft().toString());
        tokens.add(n.getOperator().asString());
        tokens.add(n.getRight().toString());
    }
}
