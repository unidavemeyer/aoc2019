/** Day 9 driver and such
 */
class Day9
{
	public static void main(String[] args)
	{
		Example1();
	}

	static void Example1()
	{
		int[] aNOps = { 109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99 };
		Intcode ic = new Intcode();
		ic.SetOps(aNOps);
		ic.RunOps();

		int[] aNOut = ic.ANOutput();
		String strOut = "";
		for (int n : aNOut)
		{
			strOut = strOut.concat(" ");
			strOut = strOut.concat(String.valueOf(n));
		}
		System.out.println("Output: " + strOut);
	}
}
