
import java.util.Arrays;
import java.util.HashMap;



/** "Intcode" computer as specified for use in at least days 2, 5, and 7 of
 * Advent of Code 2019.
 */
class Intcode
{
	// Op stream from day 2

	static int s_aNOp1[] = { 1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,1,6,19,1,5,19,23,1,23,6,27,1,5,27,31,1,31,6,35,1,
				9,35,39,2,10,39,43,1,43,6,47,2,6,47,51,1,5,51,55,1,55,13,59,1,59,10,63,2,10,63,67,1,
				9,67,71,2,6,71,75,1,5,75,79,2,79,13,83,1,83,5,87,1,87,9,91,1,5,91,95,1,5,95,99,1,99,
				13,103,1,10,103,107,1,107,9,111,1,6,111,115,2,115,13,119,1,10,119,123,2,123,6,127,1,
				5,127,131,1,5,131,135,1,135,6,139,2,139,10,143,2,143,9,147,1,147,6,151,1,151,13,155,
				2,155,9,159,1,6,159,163,1,5,163,167,1,5,167,171,1,10,171,175,1,13,175,179,1,179,2,183,
				1,9,183,0,99,2,14,0,0 };

	static void Examples()
	{
		Intcode interp = new Intcode();
		int[] aNOp1 = { 1002, 4, 3, 4, 33, };
		interp.SetOps(aNOp1);
		interp.RunOps();
		System.out.println("1: done [4] = " + interp.GetOp(4));

		int[] aNOp2 = { 1101,100,-1,4,0 };
		interp.SetOps(aNOp2);
		interp.RunOps();
		System.out.println("2: done [4] = " + interp.GetOp(4));

		Intcode interpReal = new Intcode();

		int[] aNInput = { 1 };
		interpReal.SetInput(aNInput);

		int[] aNOp = { 3,225, 1,225,6,6, 1100,1,238,225,104,0,1002,114,19,224,1001,224,-646,224,4,224,102,8,223,223,1001,224,7,224,1,223,224,223,1101,40,62,225,1101,60,38,225,1101,30,29,225,2,195,148,224,1001,224,-40,224,4,224,1002,223,8,223,101,2,224,224,1,224,223,223,1001,143,40,224,101,-125,224,224,4,224,1002,223,8,223,1001,224,3,224,1,224,223,223,101,29,139,224,1001,224,-99,224,4,224,1002,223,8,223,1001,224,2,224,1,224,223,223,1101,14,34,225,102,57,39,224,101,-3420,224,224,4,224,102,8,223,223,1001,224,7,224,1,223,224,223,1101,70,40,225,1102,85,69,225,1102,94,5,225,1,36,43,224,101,-92,224,224,4,224,1002,223,8,223,101,1,224,224,1,224,223,223,1102,94,24,224,1001,224,-2256,224,4,224,102,8,223,223,1001,224,1,224,1,223,224,223,1102,8,13,225,1101,36,65,224,1001,224,-101,224,4,224,102,8,223,223,101,3,224,224,1,223,224,223,4,223,99,0,0,0,677,0,0,0,0,0,0,0,0,0,0,0,1105,0,99999,1105,227,247,1105,1,99999,1005,227,99999,1005,0,256,1105,1,99999,1106,227,99999,1106,0,265,1105,1,99999,1006,0,99999,1006,227,274,1105,1,99999,1105,1,280,1105,1,99999,1,225,225,225,1101,294,0,0,105,1,0,1105,1,99999,1106,0,300,1105,1,99999,1,225,225,225,1101,314,0,0,106,0,0,1105,1,99999,8,677,226,224,1002,223,2,223,1006,224,329,1001,223,1,223,1108,226,226,224,1002,223,2,223,1005,224,344,101,1,223,223,1108,226,677,224,1002,223,2,223,1006,224,359,101,1,223,223,107,226,226,224,1002,223,2,223,1005,224,374,101,1,223,223,1107,226,226,224,1002,223,2,223,1005,224,389,101,1,223,223,107,677,677,224,102,2,223,223,1006,224,404,101,1,223,223,1008,226,226,224,1002,223,2,223,1006,224,419,101,1,223,223,108,677,226,224,1002,223,2,223,1006,224,434,101,1,223,223,1108,677,226,224,102,2,223,223,1005,224,449,101,1,223,223,1008,677,226,224,102,2,223,223,1006,224,464,1001,223,1,223,108,677,677,224,102,2,223,223,1005,224,479,101,1,223,223,7,677,677,224,102,2,223,223,1005,224,494,1001,223,1,223,8,226,677,224,102,2,223,223,1006,224,509,101,1,223,223,107,677,226,224,1002,223,2,223,1005,224,524,1001,223,1,223,7,677,226,224,1002,223,2,223,1005,224,539,1001,223,1,223,1007,226,677,224,1002,223,2,223,1005,224,554,1001,223,1,223,8,677,677,224,102,2,223,223,1006,224,569,101,1,223,223,7,226,677,224,102,2,223,223,1006,224,584,1001,223,1,223,1008,677,677,224,102,2,223,223,1005,224,599,101,1,223,223,1007,677,677,224,1002,223,2,223,1006,224,614,101,1,223,223,1107,677,226,224,1002,223,2,223,1006,224,629,101,1,223,223,1107,226,677,224,1002,223,2,223,1006,224,644,101,1,223,223,1007,226,226,224,102,2,223,223,1005,224,659,1001,223,1,223,108,226,226,224,102,2,223,223,1006,224,674,101,1,223,223,4,223,99,226};
		interpReal.SetOps(aNOp);
		interpReal.RunOps();
	}

	// Ops and current op stream locations (instruction pointer, relative base)

	// NOTE: ops are in a hash to support arbitrary addressing modes (whee!)

	int m_iOp = 0;
	int m_iOpRelBase = 0;
	HashMap<Long, Long> m_mapIopOp = null;

	// Input stream and location

	int m_iNInput = 0;
	int m_cNInput = 0;
	long[] m_aNInput = null;

	// Output stream

	int m_iNOutput = 0;
	long[] m_aNOutput = null;

	boolean m_fDebug = false;

	public void SetInput(int[] aNInput)
	{
		long[] aNInputLong = new long[aNInput.length];
		for (int i = 0; i < aNInput.length; ++i)
		{
			aNInputLong[i] = aNInput[i];
		}

		SetInput(aNInputLong);
	}

	public void SetInput(long[] aNInput)
	{
		m_aNInput = Arrays.copyOf(aNInput, aNInput.length);
		m_iNInput = 0;
		m_cNInput = m_aNInput.length;
	}

	public static class InputFullError
			extends Error
	{
	}

	public void AddInput(long nInput)
	{
		if (m_aNInput == null)
		{
			m_aNInput = new long[8];	// arbitrary size
		}

		if (m_cNInput >= m_aNInput.length)
		{
			throw new InputFullError();
		}

		m_aNInput[(m_iNInput + m_cNInput) % m_aNInput.length] = nInput;
		m_cNInput++;
	}

	public boolean FIsRunning()
	{
		return (int)GetOp(m_iOp) % 100 != 99;
	}

	public static class OutputEmptyError
			extends Error
	{
	}

	public long NOutputLast()
	{
		if (m_iNOutput >= m_aNOutput.length)
		{
			throw new OutputEmptyError();
		}

		return m_aNOutput[m_iNOutput++];
	}

	public void SetOps(int[] aNOp)
	{
		long[] aNOpLong = new long[aNOp.length];
		for (int i = 0; i < aNOp.length; ++i)
		{
			aNOpLong[i] = aNOp[i];
		}

		SetOps(aNOpLong);
	}

	public void SetOps(long[] aNOp)
	{
		m_iOp = 0;

		m_mapIopOp = new HashMap<>();

		for (int iOp = 0; iOp < aNOp.length; ++iOp)
		{
			m_mapIopOp.put((long)iOp, aNOp[iOp]);
		}
	}

	public void SetDebug(boolean fDebug)
	{
		m_fDebug = fDebug;
	}

	public long GetOp(long iOp)
	{
		// NOTE: memory is to be assumed as zero-filled until written

		return m_mapIopOp.getOrDefault(iOp, 0L);
	}

	public void SetOp(long iOp, long n)
	{
		m_mapIopOp.put(iOp, n);
	}

	public long[] ANOutput()
	{
		return m_aNOutput;
	}

	public static class IllegalOpError
			extends Error
	{
	}

	public void RunOps()
	{
		for (;;)
		{
			switch ((int)GetOp(m_iOp) % 100)
			{
			case 1:
				RunAdd();
				break;
			case 2:
				RunMultiply();
				break;
			case 3:
				if (!FTryRunInput())
				{
					if (m_fDebug) { System.out.println("Stalled on input"); }
					return;
				}
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
			case 9:
				RunAddRelBase();
				break;
			case 99:
				if (m_fDebug) { System.out.println("HALTING"); }
				return;
			default:
				throw new IllegalOpError();
			}
		}
	}

	long[] ANMode(long op, int cMode)
	{
		long[] aNMode = new long[cMode];

		op /= 100L;

		for (int iMode = 0; iMode < cMode; ++iMode)
		{
			aNMode[iMode] = op % 10L;
			op /= 10L;
		}

		return aNMode;
	}

	public static class IllegalModeError
			extends Error
	{
	}

	long[] ANParam(int cParam)
	{
		long[] aNMode = ANMode(GetOp(m_iOp), cParam);

		long[] aNParam = new long[cParam];

		for (int iParam = 0; iParam < cParam; ++iParam)
		{
			long nOp = GetOp(m_iOp + iParam + 1);
			long iOp = -1;
			switch ((int)aNMode[iParam])
			{
			case 0:
				// Position mode
				aNParam[iParam] = GetOp(nOp);
				iOp = nOp;
				break;

			case 1:
				// Immediate mode
				aNParam[iParam] = nOp;
				iOp = m_iOp + iParam + 1;
				break;

			case 2:
				// Relative mode
				aNParam[iParam] = GetOp(m_iOpRelBase + nOp);
				iOp = m_iOpRelBase + nOp;
				break;

			default:
				throw new IllegalModeError();
			}

			if (m_fDebug) { System.out.println("Parameter " + iParam + " : " + aNParam[iParam] + " (mode " + aNMode[iParam] + ", addr " + iOp + ")"); }
		}

		return aNParam;
	}

	long GetOpTarget(int iOp, int diOp)
	{
		// NOTE: Similar to parameters, output targets can also support both positional and relative modes (but not immediate)
		//  but they don't do the second layer of unpacking -- they just report which address to use

		long[] aNMode = ANMode(GetOp(iOp), diOp);

		long nOp = GetOp(iOp + diOp);
		long iOpOut = -1;

		switch ((int)aNMode[diOp - 1])
		{
		case 0:
			// Position mode
			iOpOut = nOp;
			break;

		case 2:
			// Relative mode
			iOpOut = m_iOpRelBase + nOp;
			break;

		default:
			throw new IllegalModeError();
		}

		if (m_fDebug) { System.out.println("Get Op Target: addr " + iOpOut + ", mode " + aNMode[diOp - 1] + ", orig op " + nOp); }

		return iOpOut;
	}

	String StrOpsDebug(int iOp, int cOp)
	{
		String strOut = String.valueOf(GetOp(iOp));

		for (int diOp = 1; diOp < cOp; diOp++)
		{
			strOut = strOut.concat(",");
			strOut = strOut.concat(String.valueOf(GetOp(iOp + diOp)));
		}

		return strOut;
	}

	void RunAdd()
	{
		if (m_fDebug) { System.out.println("  Add: " + StrOpsDebug(m_iOp, 4)); }

		long[] aNParam = ANParam(2);

		long a = aNParam[0];
		long b = aNParam[1];
		long iOpDst = GetOpTarget(m_iOp, 3);

		if (m_fDebug) { System.out.println("Adding: " + a + " + " + b + " -> " + iOpDst); }

		SetOp(iOpDst, a + b);

		m_iOp += 4;
	}

	void RunMultiply()
	{
		if (m_fDebug) { System.out.println("  Multiply: " + StrOpsDebug(m_iOp, 4)); }

		long[] aNParam = ANParam(2);

		long a = aNParam[0];
		long b = aNParam[1];
		long iOpDst = GetOpTarget(m_iOp, 3);

		if (m_fDebug) { System.out.println("Multiplying: " + a + " * " + b + " -> " + iOpDst); }

		SetOp(iOpDst, a * b);

		m_iOp += 4;
	}

	boolean FTryGetInput(long iAddr)
	{
		if (m_cNInput < 1)
			return false;

		SetOp(iAddr, m_aNInput[m_iNInput]);
		m_iNInput = (m_iNInput + 1) % m_aNInput.length;
		m_cNInput--;

		if (m_fDebug) { System.out.println("Input value " + GetOp(iAddr) + " to addr " + iAddr + "; input index now " + m_iNInput + " with count " + m_cNInput); }

		return true;
	}

	boolean FTryRunInput()
	{
		if (m_fDebug) { System.out.println("  Input: " + StrOpsDebug(m_iOp, 2)); }

		long iAddr = GetOpTarget(m_iOp, 1);

		if (!FTryGetInput(iAddr))
		{
			return false;
		}

		m_iOp += 2;
		return true;
	}

	void RunOutput()
	{
		if (m_fDebug) { System.out.println("  Output: " + StrOpsDebug(m_iOp, 2)); }

		long[] aNParam = ANParam(1);

		long nValue = aNParam[0];

		if (m_fDebug) { System.out.println("OUTPUT: op " + m_iOp + " output = " + nValue); }

		// BB (davidm) gross way to manage output -- should consider smarter "resizeable" array

		int cNCur = (m_aNOutput == null) ? 0 : m_aNOutput.length;
		cNCur++;

		m_aNOutput = (m_aNOutput == null) ? new long[cNCur] : Arrays.copyOf(m_aNOutput, cNCur);
		m_aNOutput[cNCur - 1] = nValue;

		m_iOp += 2;
	}

	void RunJumpIfTrue()
	{
		if (m_fDebug) { System.out.println("  JumpTrue: " + StrOpsDebug(m_iOp, 3)); }

		long[] aNParam = ANParam(2);
		long test = aNParam[0];
		long iOp = aNParam[1];

		if (test != 0)
		{
			if (m_fDebug) { System.out.println("Test " + test + " != 0 => true; jump to " + iOp); }

			m_iOp = (int)iOp;
			return;
		}

		if (m_fDebug) { System.out.println("Test " + test + " != 0 => false; continue executing"); }

		m_iOp += 3;
	}

	void RunJumpIfFalse()
	{
		if (m_fDebug) { System.out.println("  JumpFalse: " + StrOpsDebug(m_iOp, 3)); }

		long[] aNParam = ANParam(2);
		long test = aNParam[0];
		long iOp = aNParam[1];

		if (test == 0)
		{
			if (m_fDebug) { System.out.println("Test " + test + " == 0 => true; jump to " + iOp); }

			m_iOp = (int)iOp;
			return;
		}

		if (m_fDebug) { System.out.println("Test " + test + " == 0 => false; continue executing"); }

		m_iOp += 3;
	}

	void RunLessThan()
	{
		if (m_fDebug) { System.out.println("  LessThan: " + StrOpsDebug(m_iOp, 4)); }

		long[] aNParam = ANParam(2);
		long a = aNParam[0];
		long b = aNParam[1];
		long iOpDst = GetOpTarget(m_iOp, 3);

		if (a < b)
		{
			if (m_fDebug) { System.out.println("Test " + a + " < " + b + " => true; 1 to " + iOpDst); }

			SetOp(iOpDst, 1);
		}
		else
		{
			if (m_fDebug) { System.out.println("Test " + a + " < " + b + " => false; 0 to " + iOpDst); }

			SetOp(iOpDst, 0);
		}

		m_iOp += 4;
	}

	void RunEquals()
	{
		if (m_fDebug) { System.out.println("  Equals: " + StrOpsDebug(m_iOp, 4)); }

		long[] aNParam = ANParam(2);
		long a = aNParam[0];
		long b = aNParam[1];
		long iOpDst = GetOpTarget(m_iOp, 3);

		if (a == b)
		{
			if (m_fDebug) { System.out.println("Test " + a + " == " + b + " => true; 1 to " + iOpDst); }

			SetOp(iOpDst, 1);
		}
		else
		{
			if (m_fDebug) { System.out.println("Test " + a + " == " + b + " => false; 0 to " + iOpDst); }

			SetOp(iOpDst, 0);
		}

		m_iOp += 4;
	}

	void RunAddRelBase()
	{
		if (m_fDebug) { System.out.println("  AddRelBase: " + StrOpsDebug(m_iOp, 2)); }

		long[] aNParam = ANParam(1);
		long dRel = aNParam[0];

		if (m_fDebug) { System.out.println("Rel base delta: " + dRel + " on existing " + m_iOpRelBase); }

		m_iOpRelBase += dRel;

		m_iOp += 2;
	}
}
