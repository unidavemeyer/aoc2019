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
		Part1();

		Part2();
	}

	static void Part1()
	{
	}

	static void Part2()
	{
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

		public void SetInput(int[] aNInput)
		{
			m_aNInput = aNInput;
		}

		public void SetOps(int[] aNOp)
		{
			m_aNOp = aNOp;
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

			while (cMode > 0)
			{
				--cMode;
				aNMode[cMode] = op % 10;
				op /= 10;
			}

			return aNMode;
		}

		void RunAdd()
		{
			int[] aNMode = ANMode(m_aNOp[m_iOp], 2);

			int iA = m_aNOp[m_iOp + 1];
			int a = (aNMode[0] == 0) ? m_aNOp[iA] : iA;
			int iB = m_aNOp[m_iOp + 2];
			int b = (aNMode[1] == 0) ? m_aNOp[iB] : iB;

			int iOpDst = m_aNOp[m_iOp + 3];

			// System.out.println("Adding: " + a + " + " + b + " -> " + iOpDst);
			m_aNOp[iOpDst] = a + b;

			m_iOp += 4;
		}

		void RunMultiply()
		{
			int[] aNMode = ANMode(m_aNOp[m_iOp], 2);

			int iA = m_aNOp[m_iOp + 1];
			int a = (aNMode[0] == 0) ? m_aNOp[iA] : iA;
			int iB = m_aNOp[m_iOp + 2];
			int b = (aNMode[1] == 0) ? m_aNOp[iB] : iB;

			int iOpDst = m_aNOp[m_iOp + 3];

			// System.out.println("Multiplying: " + a + " * " + b + " -> " + iOpDst);
			m_aNOp[iOpDst] = a * b;

			m_iOp += 4;
		}

		void GetInput(int iAddr)
		{
			m_aNOp[iAddr] = m_aNInput[m_iNInput++];
		}

		void RunInput()
		{
			int[] aNMode = ANMode(m_aNOp[m_iOp], 1);
			assert(aNMode[0] == 0);

			int iAddr = m_aNOp[m_iOp + 1];
			GetInput(iAddr);

			m_iOp += 2;
		}

		void RunOutput()
		{
			int[] aNMode = ANMode(m_aNOp[m_iOp], 1);

			int iAddr = m_aNOp[m_iOp + 1];
			int nValue = (aNMode[0] == 0) ? m_aNOp[iAddr] : iAddr;

			System.out.println("op " + m_iOp + " [" + iAddr + "] = " + nValue);

			m_iOp += 2;
		}
	}
}
