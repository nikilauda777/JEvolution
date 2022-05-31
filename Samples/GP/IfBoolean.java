/*
 * JEvolution Version 0.9.8 at 1/25/13 5:04 PM
 */

/*
 * Copyright (c) 2013.
 */

import evSOLve.JEvolution.gp.ProgramNode;

/** This function returns the value of one of the two paths of an if statement. If the condition is true then the value of the true path is returned.
* If the condition is false, the value of the false (else) path is returned.
*
* @author Helmut A. Mayer
* $Id: IfBoolean.java 532 2013-03-20 18:55:18Z helmut $
* @since March, 2012
*
*/ 
public class IfBoolean extends ProgramNode {

/** Constructing the function with three operands. */
    public IfBoolean() {
		children = new ProgramNode[3];
    }
 
 
/** Returns the operand type Boolean for all children.
*
* @param index		a child index
*
* @return				TYPE_BOOLEAN
*
*/
    public int getOperandType(int index) {
		return TYPE_BOOLEAN;
	}


/** Returns the result type.
*
* @return			TYPE_BOOLEAN
*
*/
    public int getResultType() {
		return TYPE_BOOLEAN;
	}
    
     
/** Evaluates the if condition and returns the value of the true or false path.
*
* @return		boolean value of the path selected by the condition
*
*/
    public Object eval() {

		Object ante = children[0].eval();					// condition
		Object tCons = children[1].eval();					// true path
		Object fCons = children[2].eval();					// false path

		boolean a = (ante == null ? false : (Boolean)ante);
		if (a)
			return (Boolean)(tCons == null ? false : tCons);
		else
			return (Boolean)(fCons == null ? false : fCons);
    }

   
/** Exchanges true and false path. */
    public void mutate() {
    
		ProgramNode n = children[2];
		children[2] = children[1];
		children[1] = n;
	}
   

/** Returns the string signature of the node.
*
* @return			the node's signature
*
*/
    public String getSignature() {
		return "ifb";
    }
    
    
/** Returns the string representation of the node.
*
* @return			the node's string representation
*
*/
    public String toString() {
        return getSignature();
    }
//
}
