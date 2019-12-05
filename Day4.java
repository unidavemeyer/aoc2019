/** Fourth day problem solutions
 */

class Day4
{
	public static void main(String[] args)
	{
		int cPass = CountPasswords(367479, 893698);

		System.out.println("Found " + cPass);
	}

	static int CountPasswords(int nMic, int nMac)
	{
		int cPass = 0;

		// Handle each digit individually -- we know that legal passwords never have
		//  decreasing digits, so ensure that each digit is at least as big as the one
		//  before it.

		int[] aN = new int[6];

		for (int iN = 5; iN >= 0; --iN)
		{
			aN[iN] = nMic % 10;
			nMic /= 10;
		}

		System.out.println("Values: " + aN[0] + aN[1] + aN[2] + aN[3] + aN[4] + aN[5]);

		for (;;)
		{
			// Latch to legal (non-decreasing) digits

			int iNMatch = 0;
			boolean fLatch = false;
			for (int iN = 1; iN < 6; ++iN)
			{
				if (fLatch)
				{
					aN[iN] = aN[iNMatch];
				}
				else if (aN[iN] < aN[iNMatch] || aN[iN] > 9)
				{
					aN[iN] = aN[iNMatch];
					fLatch = true;
				}
				else
				{
					iNMatch = iN;
				}
			}

			System.out.println("Values: " + aN[0] + aN[1] + aN[2] + aN[3] + aN[4] + aN[5]);

			// Verify we have not gone past the end

			if (aN[0] * 100000 + aN[1] * 10000 + aN[2] * 1000 + aN[3] * 100 + aN[4] * 10 + aN[5] > nMac)
				return cPass;

			while (aN[5] < 10)
			{
				// Check for legal password (needs doubles, since we've guaranteed the rest)

				for (int iN = 1; iN < 6; ++iN)
				{
					if (aN[iN] == aN[iN - 1])
					{
						// Found a double, so this could be a legal value

						// For part 2, we must have exactly a pair somewhere (not a triple or more), although
						//  the presence of longer runs doesn't invalidate the password if there is elsewhere
						//  exactly a pair. So, check for exactly a pair.

						if ((iN < 2 || aN[iN - 2] != aN[iN]) &&
								(iN > 4 || aN[iN + 1] != aN[iN]))
						{
							++cPass;
							break;
						}
					}
				}

				aN[5]++;
			}

			// Overflowed last, so roll results forward

			for (int iN = 4; iN >= 0; --iN)
			{
				aN[iN]++;
				if (aN[iN] < 10)
					break;
			}
		}
	}

	static void CountPasswordsV0(int nMic, int nMac)
	{
		// Brute force through these -- there aren't that many to check...?

		// WRONG! Too many to check, left it running for a bit, and it didn't finish. :(

		int cAccept = 0;

	check:
		for (int nCheck = nMic; nCheck < nMac; ++nCheck)
		{
			int nPrev = nCheck % 10;
			nCheck /= 10;

			boolean fHasDouble = false;

			while (nCheck > 0)
			{
				int nNext = nCheck % 10;
				nCheck /= 10;

				if (nNext > nPrev)
					continue check;
				if (nNext == nPrev)
					fHasDouble = true;
			}

			if (!fHasDouble)
				continue check;

			++cAccept;
		}

		System.out.println("Found " + cAccept + " passwords that work");
	}
}
