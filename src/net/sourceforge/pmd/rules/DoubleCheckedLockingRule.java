/*
 * DoubleCheckedLockingRule.java
 *
 * Created on February 17, 2003, 12:39 PM
 */

package net.sourceforge.pmd.rules;
import net.sourceforge.pmd.Rule;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.AbstractRule;
import net.sourceforge.pmd.ast.ASTAssignmentOperator;
import net.sourceforge.pmd.ast.ASTIfStatement;
import net.sourceforge.pmd.ast.ASTInterfaceDeclaration;
import net.sourceforge.pmd.ast.ASTLiteral;
import net.sourceforge.pmd.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.ast.ASTName;
import net.sourceforge.pmd.ast.ASTNullLiteral;
import net.sourceforge.pmd.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.ast.ASTPrimaryPrefix;
import net.sourceforge.pmd.ast.ASTResultType;
import net.sourceforge.pmd.ast.ASTReturnStatement;
import net.sourceforge.pmd.ast.ASTStatementExpression;
import net.sourceforge.pmd.ast.ASTSynchronizedStatement;
import net.sourceforge.pmd.ast.ASTType;
import net.sourceforge.pmd.ast.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Very very specific check for double checked locking.
 *
 * @author  CL Gilbert (dnoyeb@users.sourceforge.net)
 */
public class DoubleCheckedLockingRule extends AbstractRule implements Rule {

    public Object visit(ASTInterfaceDeclaration node, Object data){
        return data; // skip interfaces
    }

    public Object visit(ASTMethodDeclaration node, Object data) {
		ASTResultType rt = (ASTResultType) node.jjtGetChild(0);
		if(rt.isVoid() == true){
			return super.visit(node,data);
		}

		ASTType t = (ASTType) rt.jjtGetChild(0);
		if(t.jjtGetNumChildren() == 0 || !(t.jjtGetChild(0) instanceof ASTName)){
			return super.visit(node,data);
		}

		String returnVariableName = null;
		List finder = new ArrayList();

		GET_RETURN_VARIABLE_NAME:{
			node.findChildrenOfType(ASTReturnStatement.class,finder,true);
			if(finder.size() != 1){
				return super.visit(node,data);
			}
			ASTReturnStatement rs = (ASTReturnStatement) finder.get(0);
			finder.clear();
			rs.findChildrenOfType(ASTPrimaryExpression.class,finder,true);
			ASTPrimaryExpression ape = (ASTPrimaryExpression) finder.get(0);
			Node lastChild = ape.jjtGetChild( ape.jjtGetNumChildren()-1 );
			if(lastChild instanceof ASTPrimaryPrefix){
				returnVariableName = getNameFromPrimaryPrefix((ASTPrimaryPrefix)lastChild);
			}
			if(returnVariableName == null){
				return super.visit(node,data);
			}
		}

		CHECK_OUTER_IF:{
			finder.clear();
			node.findChildrenOfType(ASTIfStatement.class,finder,true);
			if(finder.size() == 2){
				ASTIfStatement is = (ASTIfStatement) finder.get(0);
				if(ifVerify(is,returnVariableName)){
					//find synchronize
					finder.clear();
					is.findChildrenOfType(ASTSynchronizedStatement.class,finder,true);
					if(finder.size() == 1){
						ASTSynchronizedStatement ss = (ASTSynchronizedStatement) finder.get(0);
						finder.clear();
						ss.findChildrenOfType(ASTIfStatement.class,finder,true);
						if(finder.size() == 1){
							ASTIfStatement is2 = (ASTIfStatement) finder.get(0);
							if(ifVerify(is2,returnVariableName)){
								finder.clear();
								is2.findChildrenOfType(ASTStatementExpression.class,finder,true);
								if(finder.size() == 1){
									ASTStatementExpression se = (ASTStatementExpression) finder.get(0);
									if(se.jjtGetNumChildren() == 3){ //primaryExpression, AssignmentOperator, Expression
										if(se.jjtGetChild(0) instanceof ASTPrimaryExpression){
											ASTPrimaryExpression pe = (ASTPrimaryExpression) se.jjtGetChild(0);
											if(matchName(pe, returnVariableName)){
												if(se.jjtGetChild(1) instanceof ASTAssignmentOperator){
													RuleContext ctx = (RuleContext)data;
													ctx.getReport().addRuleViolation(createRuleViolation(ctx, node.getBeginLine()));
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return super.visit(node,data);
	}
	
	private boolean ifVerify(ASTIfStatement is, String varname){
		List finder = new ArrayList();
		is.findChildrenOfType(ASTPrimaryExpression.class,finder,true);
		if(finder.size() >1){
			ASTPrimaryExpression apeLeft = (ASTPrimaryExpression) finder.get(0);
			if(matchName(apeLeft, varname)){
				ASTPrimaryExpression apeRight = (ASTPrimaryExpression) finder.get(1);
				if((apeRight.jjtGetNumChildren() == 1) && (apeRight.jjtGetChild(0) instanceof ASTPrimaryPrefix)){
					ASTPrimaryPrefix pp2 = (ASTPrimaryPrefix) apeRight.jjtGetChild(0);
					if((pp2.jjtGetNumChildren() == 1) && (pp2.jjtGetChild(0) instanceof ASTLiteral)){
						ASTLiteral lit = (ASTLiteral) pp2.jjtGetChild(0) ;
						if((lit.jjtGetNumChildren() == 1) && (lit.jjtGetChild(0) instanceof ASTNullLiteral)){
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public boolean matchName(ASTPrimaryExpression ape, String name){
		if((ape.jjtGetNumChildren() == 1) && (ape.jjtGetChild(0) instanceof ASTPrimaryPrefix)){
			ASTPrimaryPrefix pp = (ASTPrimaryPrefix) ape.jjtGetChild(0);
			String name2 = getNameFromPrimaryPrefix(pp);
			if(name2 != null && name2.equals(name)){
				return true;
			}
		}
		return false;
	}
	public String getNameFromPrimaryPrefix(ASTPrimaryPrefix pp){
		if((pp.jjtGetNumChildren() == 1) && (pp.jjtGetChild(0) instanceof ASTName)){
			return ((ASTName)pp.jjtGetChild(0)).getImage();
		}
		return null;
	}
	

	//Testing Section
//    public Object visit(ASTCompilationUnit node, Object data) {
//		interfaceSkipper = false; //safety
//		try {
//			return super.visit(node,data);
//		}
//		catch(Exception e){
//			e.printStackTrace();
//			throw new RuntimeException(e.getMessage());
//		}
//    }
//	public Object visit(ASTMethodDeclarator node, Object data) {
//		System.out.println("method = " + node.getImage());
//		return super.visit(node,data);
//	}
//
//	public Object visit(ASTPackageDeclaration node, Object data){
//		String name = ((ASTName)node.jjtGetChild(0)).getImage();
//		System.out.println("package = " + name);
//		return super.visit(node, data);
//	}
}