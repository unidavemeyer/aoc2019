
/** Day7 solutions
 */
class Day7
{
	public static void main(String[] args)
	{
		// Example1();
		// Example2();
		
		Example3();
	}

	static void Example1()
	{
		int[] aNPhase = { 4,3,2,1,0 };
		int[] aNProgram = { 3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0 };

		System.out.println("Example1 output: " + NRunAmps(aNProgram, aNPhase, 0));
	}

	static void Example2()
	{
		int[] aNPhase = { 0,1,2,3,4 };
		int[] aNProgram = { 3,23,3,24,1002,24,10,24,1002,23,-1,23, 101,5,23,23,1,24,23,23,4,23,99,0,0 };

		System.out.println("Example2 output: " + NRunAmps(aNProgram, aNPhase, 0));
	}

	static void Example3()
	{
		int[] aNPhase = { 1,0,4,3,2 };
		int[] aNProgram = { 3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0 };

		System.out.println("Example1 output: " + NRunAmps(aNProgram, aNPhase, 0));
	}

	static int NRunAmps(int[] aNProgram, int[] aNPhase, int nInit)
	{
		int nCur = nInit;

		for (int nPhase : aNPhase)
		{
			int[] aNInput = { nPhase, nCur, };

			Intcode ic = new Intcode();

			ic.SetInput(aNInput);
			ic.SetOps(aNProgram);
			ic.RunOps();

			int[] aNOutput = ic.ANOutput();
			nCur = aNOutput[0];
		}

		return nCur;
	}
}
