/** Day 9 driver and such
 */
class Day9
{
	public static void main(String[] args)
	{
		// Example1();
		// Example2();
		Example3();
	}

	static void Example1()
	{
		int[] aNOps = { 109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99 };
		Intcode ic = new Intcode();
		ic.SetOps(aNOps);
		ic.RunOps();

		long[] aNOut = ic.ANOutput();
		String strOut = "";
		for (long n : aNOut)
		{
			strOut = strOut.concat(" ");
			strOut = strOut.concat(String.valueOf(n));
		}
		System.out.println("Output: " + strOut);
	}

	static void Example2()
	{
		int[] aNOps = { 1102,34915192,34915192,7,4,7,99,0 };
		Intcode ic = new Intcode();
		ic.SetOps(aNOps);
		ic.RunOps();

		long[] aNOut = ic.ANOutput();
		String strOut = "";
		for (long n : aNOut)
		{
			strOut = strOut.concat(" ");
			strOut = strOut.concat(String.valueOf(n));
		}
		System.out.println("Output: " + strOut);
	}

	static void Example3()
	{
		long[] aNOps = { 104L,1125899906842624L,99L };
		Intcode ic = new Intcode();
		ic.SetOps(aNOps);
		ic.RunOps();

		long[] aNOut = ic.ANOutput();
		String strOut = "";
		for (long n : aNOut)
		{
			strOut = strOut.concat(" ");
			strOut = strOut.concat(String.valueOf(n));
		}
		System.out.println("Output: " + strOut);
	}

}
