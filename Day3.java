/** Day three solution
 */

import java.util.ArrayList;
import java.util.HashSet;

class Day3
{
	static class Pair
	{
		int x;
		int y;

		public Pair(int x, int y)
		{
			this.x = x;
			this.y = y;
		}

		public int X()
		{
			return x;
		}

		public int Y()
		{
			return y;
		}

		public int Offset()
		{
			return Math.abs(x) + Math.abs(y);
		}

		public Pair Sum(Pair pairOther)
		{
			return new Pair(this.x + pairOther.x, this.y + pairOther.y);
		}

		@Override
		public int hashCode()
		{
			// BB (davidm) poor hashing here...?

			return 7 * this.x + 13 * this.y;
		}

		@Override
		public boolean equals(Object objOther)
		{
			if (!(objOther instanceof Pair))
				return false;

			Pair pairOther = (Pair) objOther;

			return this.x == pairOther.x && this.y == pairOther.y;
		}
	}

	public static void main(String[] args)
	{
		String str0 = "R8,U5,L5,D3";
		String str1 = "U7,R6,D4,L4";

		FindIntersection(str0, str1);
	}

	static void FindIntersection(String str0, String str1)
	{
		// Convert to coordinates

		ArrayList<Pair> listPair0 = new ArrayList<>();
		ArrayList<Pair> listPair1 = new ArrayList<>();

		FillCoords(str0, listPair0);
		FillCoords(str1, listPair1);

		System.out.println("list0 has " + listPair0.size() + " elements");
		System.out.println("list1 has " + listPair1.size() + " elements");

		// Convert to sets

		HashSet<Pair> set0 = new HashSet<>(listPair0);
		HashSet<Pair> set1 = new HashSet<>(listPair1);

		System.out.println("Set0 has " + set0.size() + " elements");
		System.out.println("Set1 has " + set1.size() + " elements");

		// Keep just the intersection between the two sets of coordinates

		set0.retainAll(set1);

		System.out.println("Set0 now has " + set0.size() + " elements");

		// Calculate the shortest manhattan distance retained coordinate

		Pair pairBest = null;

		for (Pair pairCur : set0)
		{
			if (pairBest == null)
			{
				pairBest = pairCur;
			}

			int sCur = pairCur.Offset();
			int sBest = pairBest.Offset();

			if (sCur < sBest)
			{
				pairBest = pairCur;
			}
		}

		System.out.println("Best pair: " + pairBest.X() + "," + pairBest.Y() + " at distance " + (pairBest.Offset()));
	}

	static void FillCoords(String strIn, ArrayList<Pair> listPair)
	{
		// Loop:
		// 		Scan to comma
		// 		Interpret direction (UDLR)
		// 		Interpret number
		// 		Fill coordinate pairs

		Pair pairCur = new Pair(0, 0);

		for (int iCh = 0; iCh < strIn.length(); ++iCh)
		{
			// Determine what delta to use

			Pair dPair = null;

			switch (strIn.charAt(iCh))
			{
			case 'U':
				dPair = new Pair(0, -1);
				break;

			case 'D':
				dPair = new Pair(0, 1);
				break;

			case 'L':
				dPair = new Pair(-1, 0);
				break;

			case 'R':
				dPair = new Pair(1, 0);
				break;

			default:
				System.out.println("Invalid direction encountered: " + strIn.charAt(iCh));
				return;
			}

			// Determine how far to run

			int iCh0 = iCh + 1;
			iCh = iCh0;
			while (iCh < strIn.length() && strIn.charAt(iCh) != ',')
			{
				++iCh;
			}

			String strRun = strIn.substring(iCh0, iCh);
			int cStep = Integer.parseInt(strRun);

			// Add in coordinate pairs

			for (int iStep = 0; iStep < cStep; ++iStep)
			{
				pairCur = pairCur.Sum(dPair);
				listPair.add(pairCur);
			}
		}
	}
}
