import java.util.HashSet;

/** Day7 solutions
 */
class Day7
{
	public static void main(String[] args)
	{
		// Example1();
		// Example2();
		// Example3();

		Part1();
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

	static void Part1()
	{
		int[] aNProgram = {3,8,1001,8,10,8,105,1,0,0,21,46,67,76,97,118,199,280,361,442,99999,3,9,1002,9,3,9,101,4,9,9,102,3,9,9,1001,9,3,9,1002,9,2,9,4,9,99,3,9,102,2,9,9,101,5,9,9,1002,9,2,9,101,2,9,9,4,9,99,3,9,101,4,9,9,4,9,99,3,9,1001,9,4,9,102,2,9,9,1001,9,4,9,1002,9,5,9,4,9,99,3,9,102,3,9,9,1001,9,2,9,1002,9,3,9,1001,9,3,9,4,9,99,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,99,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,2,9,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,99,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,99};
		int[] aNPhaseAll = { 0,1,2,3,4 };
		int[] aNPhase = new int[5];

		HashSet<Integer> setPhase = new HashSet<>();
		for (int nPhase : aNPhaseAll)
		{
			setPhase.add(nPhase);
		}

		System.out.println("Maximal output: " + NMaximalAmps(aNProgram, setPhase, aNPhase, 0, 0));
	}

	static int NMaximalAmps(int[] aNProgram, HashSet<Integer> setPhase, int[] aNPhase, int iPhase, int nInit)
	{
		int nBest = -1;	// BB (davidm) sufficient?

		for (int nPhaseCur : setPhase)
		{
			aNPhase[iPhase] = nPhaseCur;

			HashSet<Integer> setPhaseCur = new HashSet<>();
			setPhaseCur.addAll(setPhase);
			setPhaseCur.remove(nPhaseCur);

			int nCur;
			if (iPhase < aNPhase.length - 1)
			{
				nCur = NMaximalAmps(aNProgram, setPhaseCur, aNPhase, iPhase + 1, nInit);
			}
			else
			{
				nCur = NRunAmps(aNProgram, aNPhase, nInit);
			}

			if (nCur > nBest)
			{
				nBest = nCur;
			}
		}

		return nBest;
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
