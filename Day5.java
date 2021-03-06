// Day 5 solution

/** Frontend driver for day 5
 */
class Day5
{
	// Op stream from day 2

	static int s_aNOp1[] = { 1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,1,6,19,1,5,19,23,1,23,6,27,1,5,27,31,1,31,6,35,1,
				9,35,39,2,10,39,43,1,43,6,47,2,6,47,51,1,5,51,55,1,55,13,59,1,59,10,63,2,10,63,67,1,
				9,67,71,2,6,71,75,1,5,75,79,2,79,13,83,1,83,5,87,1,87,9,91,1,5,91,95,1,5,95,99,1,99,
				13,103,1,10,103,107,1,107,9,111,1,6,111,115,2,115,13,119,1,10,119,123,2,123,6,127,1,
				5,127,131,1,5,131,135,1,135,6,139,2,139,10,143,2,143,9,147,1,147,6,151,1,151,13,155,
				2,155,9,159,1,6,159,163,1,5,163,167,1,5,167,171,1,10,171,175,1,13,175,179,1,179,2,183,
				1,9,183,0,99,2,14,0,0 };

	public static void main(String[] args)
	{
		// Part1();
		// Part2();

		Part3();
	}

	static void Part1()
	{
		Interp interp = new Interp();
		int[] aNOp1 = { 1002, 4, 3, 4, 33, };
		interp.SetOps(aNOp1);
		interp.RunOps();
		System.out.println("1: done [4] = " + interp.GetOp(4));

		int[] aNOp2 = { 1101,100,-1,4,0 };
		interp.SetOps(aNOp2);
		interp.RunOps();
		System.out.println("2: done [4] = " + interp.GetOp(4));

		Interp interpReal = new Interp();

		int[] aNInput = { 1 };
		interpReal.SetInput(aNInput);

		int[] aNOp = { 3,225, 1,225,6,6, 1100,1,238,225,104,0,1002,114,19,224,1001,224,-646,224,4,224,102,8,223,223,1001,224,7,224,1,223,224,223,1101,40,62,225,1101,60,38,225,1101,30,29,225,2,195,148,224,1001,224,-40,224,4,224,1002,223,8,223,101,2,224,224,1,224,223,223,1001,143,40,224,101,-125,224,224,4,224,1002,223,8,223,1001,224,3,224,1,224,223,223,101,29,139,224,1001,224,-99,224,4,224,1002,223,8,223,1001,224,2,224,1,224,223,223,1101,14,34,225,102,57,39,224,101,-3420,224,224,4,224,102,8,223,223,1001,224,7,224,1,223,224,223,1101,70,40,225,1102,85,69,225,1102,94,5,225,1,36,43,224,101,-92,224,224,4,224,1002,223,8,223,101,1,224,224,1,224,223,223,1102,94,24,224,1001,224,-2256,224,4,224,102,8,223,223,1001,224,1,224,1,223,224,223,1102,8,13,225,1101,36,65,224,1001,224,-101,224,4,224,102,8,223,223,101,3,224,224,1,223,224,223,4,223,99,0,0,0,677,0,0,0,0,0,0,0,0,0,0,0,1105,0,99999,1105,227,247,1105,1,99999,1005,227,99999,1005,0,256,1105,1,99999,1106,227,99999,1106,0,265,1105,1,99999,1006,0,99999,1006,227,274,1105,1,99999,1105,1,280,1105,1,99999,1,225,225,225,1101,294,0,0,105,1,0,1105,1,99999,1106,0,300,1105,1,99999,1,225,225,225,1101,314,0,0,106,0,0,1105,1,99999,8,677,226,224,1002,223,2,223,1006,224,329,1001,223,1,223,1108,226,226,224,1002,223,2,223,1005,224,344,101,1,223,223,1108,226,677,224,1002,223,2,223,1006,224,359,101,1,223,223,107,226,226,224,1002,223,2,223,1005,224,374,101,1,223,223,1107,226,226,224,1002,223,2,223,1005,224,389,101,1,223,223,107,677,677,224,102,2,223,223,1006,224,404,101,1,223,223,1008,226,226,224,1002,223,2,223,1006,224,419,101,1,223,223,108,677,226,224,1002,223,2,223,1006,224,434,101,1,223,223,1108,677,226,224,102,2,223,223,1005,224,449,101,1,223,223,1008,677,226,224,102,2,223,223,1006,224,464,1001,223,1,223,108,677,677,224,102,2,223,223,1005,224,479,101,1,223,223,7,677,677,224,102,2,223,223,1005,224,494,1001,223,1,223,8,226,677,224,102,2,223,223,1006,224,509,101,1,223,223,107,677,226,224,1002,223,2,223,1005,224,524,1001,223,1,223,7,677,226,224,1002,223,2,223,1005,224,539,1001,223,1,223,1007,226,677,224,1002,223,2,223,1005,224,554,1001,223,1,223,8,677,677,224,102,2,223,223,1006,224,569,101,1,223,223,7,226,677,224,102,2,223,223,1006,224,584,1001,223,1,223,1008,677,677,224,102,2,223,223,1005,224,599,101,1,223,223,1007,677,677,224,1002,223,2,223,1006,224,614,101,1,223,223,1107,677,226,224,1002,223,2,223,1006,224,629,101,1,223,223,1107,226,677,224,1002,223,2,223,1006,224,644,101,1,223,223,1007,226,226,224,102,2,223,223,1005,224,659,1001,223,1,223,108,226,226,224,102,2,223,223,1006,224,674,101,1,223,223,4,223,99,226};
		interpReal.SetOps(aNOp);
		interpReal.RunOps();
	}

	static void Part2()
	{
		System.out.println("Test 1");

		Interp interp1 = new Interp();
		int[] aNInput = { 9 };
		interp1.SetInput(aNInput);
		int[] aNOp = { 3,9,8,9,10,9,4,9,99,-1,8 };
		interp1.SetOps(aNOp);
		interp1.RunOps();

		System.out.println("Test 2");

		Interp interp2 = new Interp();
		int[] aNInput2 = { 6 };
		interp2.SetInput(aNInput2);
		int[] aNOp2 = { 3,9,7,9,10,9,4,9,99,-1,8 };
		interp2.SetOps(aNOp2);
		interp2.RunOps();

		System.out.println("Test 3");

		Interp interp3 = new Interp();
		int[] aNInput3 = { 8 };
		interp3.SetInput(aNInput3);
		int[] aNOp3 = { 3,3,1108,-1,8,3,4,3,99 };
		interp3.SetOps(aNOp3);
		interp3.RunOps();

		System.out.println("Test 4");

		Interp interp4 = new Interp();
		int[] aNInput4 = { 0 };
		interp4.SetInput(aNInput4);
		int[] aNOp4 = { 3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9 };
		interp4.SetOps(aNOp4);
		interp4.RunOps();

		System.out.println("Test 5");

		Interp interp5 = new Interp();
		interp5.SetDebug(true);
		int[] aNInput5 = { 9 };
		interp5.SetInput(aNInput5);
		int[] aNOp5 = { 3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31, 1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104, 999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99 };
		interp5.SetOps(aNOp5);
		interp5.RunOps();
	}

	static void Part3()
	{
		Interp interp = new Interp();
		int[] aNInput = { 5 };
		interp.SetInput(aNInput);
		int[] aNOp = { 3,225, 1,225,6,6, 1100,1,238,225,104,0,1002,114,19,224,1001,224,-646,224,4,224,102,8,223,223,1001,224,7,224,1,223,224,223,1101,40,62,225,1101,60,38,225,1101,30,29,225,2,195,148,224,1001,224,-40,224,4,224,1002,223,8,223,101,2,224,224,1,224,223,223,1001,143,40,224,101,-125,224,224,4,224,1002,223,8,223,1001,224,3,224,1,224,223,223,101,29,139,224,1001,224,-99,224,4,224,1002,223,8,223,1001,224,2,224,1,224,223,223,1101,14,34,225,102,57,39,224,101,-3420,224,224,4,224,102,8,223,223,1001,224,7,224,1,223,224,223,1101,70,40,225,1102,85,69,225,1102,94,5,225,1,36,43,224,101,-92,224,224,4,224,1002,223,8,223,101,1,224,224,1,224,223,223,1102,94,24,224,1001,224,-2256,224,4,224,102,8,223,223,1001,224,1,224,1,223,224,223,1102,8,13,225,1101,36,65,224,1001,224,-101,224,4,224,102,8,223,223,101,3,224,224,1,223,224,223,4,223,99,0,0,0,677,0,0,0,0,0,0,0,0,0,0,0,1105,0,99999,1105,227,247,1105,1,99999,1005,227,99999,1005,0,256,1105,1,99999,1106,227,99999,1106,0,265,1105,1,99999,1006,0,99999,1006,227,274,1105,1,99999,1105,1,280,1105,1,99999,1,225,225,225,1101,294,0,0,105,1,0,1105,1,99999,1106,0,300,1105,1,99999,1,225,225,225,1101,314,0,0,106,0,0,1105,1,99999,8,677,226,224,1002,223,2,223,1006,224,329,1001,223,1,223,1108,226,226,224,1002,223,2,223,1005,224,344,101,1,223,223,1108,226,677,224,1002,223,2,223,1006,224,359,101,1,223,223,107,226,226,224,1002,223,2,223,1005,224,374,101,1,223,223,1107,226,226,224,1002,223,2,223,1005,224,389,101,1,223,223,107,677,677,224,102,2,223,223,1006,224,404,101,1,223,223,1008,226,226,224,1002,223,2,223,1006,224,419,101,1,223,223,108,677,226,224,1002,223,2,223,1006,224,434,101,1,223,223,1108,677,226,224,102,2,223,223,1005,224,449,101,1,223,223,1008,677,226,224,102,2,223,223,1006,224,464,1001,223,1,223,108,677,677,224,102,2,223,223,1005,224,479,101,1,223,223,7,677,677,224,102,2,223,223,1005,224,494,1001,223,1,223,8,226,677,224,102,2,223,223,1006,224,509,101,1,223,223,107,677,226,224,1002,223,2,223,1005,224,524,1001,223,1,223,7,677,226,224,1002,223,2,223,1005,224,539,1001,223,1,223,1007,226,677,224,1002,223,2,223,1005,224,554,1001,223,1,223,8,677,677,224,102,2,223,223,1006,224,569,101,1,223,223,7,226,677,224,102,2,223,223,1006,224,584,1001,223,1,223,1008,677,677,224,102,2,223,223,1005,224,599,101,1,223,223,1007,677,677,224,1002,223,2,223,1006,224,614,101,1,223,223,1107,677,226,224,1002,223,2,223,1006,224,629,101,1,223,223,1107,226,677,224,1002,223,2,223,1006,224,644,101,1,223,223,1007,226,226,224,102,2,223,223,1005,224,659,1001,223,1,223,108,226,226,224,102,2,223,223,1006,224,674,101,1,223,223,4,223,99,226};
		interp.SetOps(aNOp);
		interp.RunOps();
	}

	static void Part1Day2()
	{
		int[] aNOp = java.util.Arrays.copyOf(s_aNOp1, s_aNOp1.length);

		aNOp[1] = 12;
		aNOp[2] = 2;

		Interp interp = new Interp();
		interp.SetOps(aNOp);
		interp.RunOps();
		
		System.out.println("Result: " + interp.GetOp(0));
	}

	static void Part2Day2()
	{
		int goal = 19690720;

	nouns:
		for (int noun = 0; noun < 100; ++noun)
		{
			for (int verb = 0; verb < 100; ++verb)
			{
				int[] aNOp = java.util.Arrays.copyOf(s_aNOp1, s_aNOp1.length);

				aNOp[1] = noun;
				aNOp[2] = verb;

				Interp interp = new Interp();
				interp.SetOps(aNOp);
				interp.RunOps();

				if (interp.GetOp(0) == goal)
				{
					System.out.println("Found goal with noun " + noun + " and verb " + verb + " for total " + (100 * noun + verb));
					break nouns;
				}
			}
		}

		System.out.println("If we got here without a result, too bad!");
	}

	static class Interp
	{
		int m_iOp = 0;
		int[] m_aNOp = null;

		int m_iNInput = 0;
		int[] m_aNInput = null;

		boolean m_fDebug = false;

		public void SetInput(int[] aNInput)
		{
			m_aNInput = aNInput;
		}

		public void SetOps(int[] aNOp)
		{
			m_aNOp = aNOp;
		}

		public void SetDebug(boolean fDebug)
		{
			m_fDebug = fDebug;
		}

		public int GetOp(int iOp)
		{
			return m_aNOp[iOp];
		}

		public void RunOps()
		{
			m_iOp = 0;
			m_iNInput = 0;

			while (m_iOp < m_aNOp.length)
			{
				switch (m_aNOp[m_iOp] % 100)
				{
				case 1:
					RunAdd();
					break;
				case 2:
					RunMultiply();
					break;
				case 3:
					RunInput();
					break;
				case 4:
					RunOutput();
					break;
				case 5:
					RunJumpIfTrue();
					break;
				case 6:
					RunJumpIfFalse();
					break;
				case 7:
					RunLessThan();
					break;
				case 8:
					RunEquals();
					break;
				case 99:
					return;
				default:
					System.out.println("Oops!");
					return;
				}
			}

			System.out.println("Also oops!");
		}

		int[] ANMode(int op, int cMode)
		{
			int[] aNMode = new int[cMode];

			op /= 100;

			for (int iMode = 0; iMode < cMode; ++iMode)
			{
				aNMode[iMode] = op % 10;
				op /= 10;
			}

			return aNMode;
		}

		int[] ANParam(int cParam)
		{
			int[] aNMode = ANMode(m_aNOp[m_iOp], cParam);

			int[] aNParam = new int[cParam];

			for (int iParam = 0; iParam < cParam; ++iParam)
			{
				int nOp = m_aNOp[m_iOp + iParam + 1];
				aNParam[iParam] = (aNMode[iParam] == 0) ? m_aNOp[nOp] : nOp;

				if (m_fDebug)
				{
					System.out.println("Parameter " + iParam + " : " + aNParam[iParam] + " (mode " + aNMode[iParam] + ")");
				}
			}

			return aNParam;
		}

		void RunAdd()
		{
			int[] aNParam = ANParam(2);

			int a = aNParam[0];
			int b = aNParam[1];
			int iOpDst = m_aNOp[m_iOp + 3];

			if (m_fDebug)
			{
				System.out.println("Adding: " + a + " + " + b + " -> " + iOpDst);
			}

			m_aNOp[iOpDst] = a + b;

			m_iOp += 4;
		}

		void RunMultiply()
		{
			int[] aNParam = ANParam(2);

			int a = aNParam[0];
			int b = aNParam[1];
			int iOpDst = m_aNOp[m_iOp + 3];

			if (m_fDebug)
			{
				System.out.println("Multiplying: " + a + " * " + b + " -> " + iOpDst);
			}

			m_aNOp[iOpDst] = a * b;

			m_iOp += 4;
		}

		void GetInput(int iAddr)
		{
			m_aNOp[iAddr] = m_aNInput[m_iNInput++];

			if (m_fDebug)
			{
				System.out.println("Input value " + m_aNOp[iAddr] + " to addr " + iAddr + "; input index now " + m_iNInput);
			}
		}

		void RunInput()
		{
			int iAddr = m_aNOp[m_iOp + 1];
			GetInput(iAddr);

			m_iOp += 2;
		}

		void RunOutput()
		{
			int[] aNParam = ANParam(1);

			int nValue = aNParam[0];

			System.out.println("OUTPUT: op " + m_iOp + " output = " + nValue);

			m_iOp += 2;
		}

		void RunJumpIfTrue()
		{
			int[] aNParam = ANParam(2);
			int test = aNParam[0];
			int iOp = aNParam[1];

			if (test != 0)
			{
				if (m_fDebug) { System.out.println("Test " + test + " != 0 => true; jump to " + iOp); }

				m_iOp = iOp;
				return;
			}

			if (m_fDebug) { System.out.println("Test " + test + " != 0 => false; continue executing"); }

			m_iOp += 3;
		}

		void RunJumpIfFalse()
		{
			int[] aNParam = ANParam(2);
			int test = aNParam[0];
			int iOp = aNParam[1];

			if (test == 0)
			{
				if (m_fDebug) { System.out.println("Test " + test + " == 0 => true; jump to " + iOp); }

				m_iOp = iOp;
				return;
			}

			if (m_fDebug) { System.out.println("Test " + test + " == 0 => false; continue executing"); }

			m_iOp += 3;
		}

		void RunLessThan()
		{
			int[] aNParam = ANParam(2);
			int a = aNParam[0];
			int b = aNParam[1];
			int iOpDst = m_aNOp[m_iOp + 3];

			if (a < b)
			{
				if (m_fDebug) { System.out.println("Test " + a + " < " + b + " => true; 1 to " + iOpDst); }

				m_aNOp[iOpDst] = 1;
			}
			else
			{
				if (m_fDebug) { System.out.println("Test " + a + " < " + b + " => false; 0 to " + iOpDst); }

				m_aNOp[iOpDst] = 0;
			}

			m_iOp += 4;
		}

		void RunEquals()
		{
			int[] aNParam = ANParam(2);
			int a = aNParam[0];
			int b = aNParam[1];
			int iOpDst = m_aNOp[m_iOp + 3];

			if (a == b)
			{
				if (m_fDebug) { System.out.println("Test " + a + " == " + b + " => true; 1 to " + iOpDst); }

				m_aNOp[iOpDst] = 1;
			}
			else
			{
				if (m_fDebug) { System.out.println("Test " + a + " == " + b + " => false; 0 to " + iOpDst); }

				m_aNOp[iOpDst] = 0;
			}

			m_iOp += 4;
		}
	}
}
