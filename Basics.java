// Super simple basic set of stuff to help refresh my memory on how to write Java code

/** Comments that start with double star are documentation ones.
 *
 * This class hold some of the basics for making things go.
 */

class Basics
{
	public static void main(String[] args)
	{
		System.out.println("Testing, 1, 2, 3...");

		int nValue = 12345;
		int nValueHex = 0x12345;
		int nValueBin = 0b10101;

		long nLong = 1234567890L;
		long nLong2 = 1234_5678_90L;	// underscores are allowed between groups just for kicks

		boolean fValue = true;

		int[] aN;
		aN = new int[2];
		aN[0] = 12;
		aN[1] = 34;

		int[] aNAlt = { 0, 1, 2, 3, };

		System.out.println("Array has " + aNAlt.length + " elements");

		String str = "a b c d";

		System.out.println("String is a string? " + (str instanceof String));
	}
}
