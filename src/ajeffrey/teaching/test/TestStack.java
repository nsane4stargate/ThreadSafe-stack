package ajeffrey.teaching.test;

import ajeffrey.teaching.util.stack.SafeStack;
import ajeffrey.teaching.util.stack.SafeStackImpl;
import ajeffrey.teaching.util.stack.UnsafeStack;
import java.util.Iterator;
/*
Implemented SafeStackImpl and SafeStack
 */
public class TestStack {

	public static void main(String[] args) throws NoSuchFieldException {

		final SafeStackImpl stack = new SafeStack().createStack();
		stack.push("fred");
		stack.push("wilma");
		stack.push("barney");
		stack.push("betty");

		for (Iterator i = stack.iterator(); i.hasNext(); ) {
			System.out.println(i.next());
		}
	}
}
