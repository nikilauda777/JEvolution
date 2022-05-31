/*
 * JEvolution Version 0.9.8 at 1/25/13 5:05 PM
 */

import evSOLve.JEvolution.gp.ProgramNode;

public class BooleanDouble extends ProgramNode {

/** Constructs the casting function. */
    public BooleanDouble() {
		children = new ProgramNode[1];
	}


/** Returns the operand type Boolean for the child.
*
* @param index		a child index
*
* @return				TYPE_BOOLEAN
*
*/
    public int getOperandType(int index) {
		return TYPE_BOOLEAN;
	}


/** Returns the result type Double.
*
* @return			TYPE_DOUBLE
*
*/
    public int getResultType() {
		return TYPE_DOUBLE;
	}
    
     
/** Returns the double value of the boolean operand.
*
* @return				the value as Double
*
*/
    public Object eval() {

		Object o = children[0].eval();

		if (o != null) {
			if ((Boolean)o)
				return (Double)1.0;
		}
		return (Double)0.0;
    }

   
/** Returns the string signature of the node.
*
* @return			the node's signature
*
*/
    public String getSignature() {
		return "bd";
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
