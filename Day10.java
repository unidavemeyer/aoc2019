import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/** Day 10 driver and solution
 */
class Day10
{
	public static void main(String[] args)
	{
		Example1();
	}

	static void Example1()
	{
		String[] aStr = {
			".#..#",
			".....",
			"#####",
			"....#",
			"...##",
		};

		AsterMap astermap = new AsterMap();
		astermap.SetDebug(true);
		astermap.Load(aStr);
		astermap.PrintSight();
	}

	static class Pair
	{
		public int m_x = 0;
		public int m_y = 0;

		public Pair Minus(Pair pairOther)
		{
			Pair pair = new Pair();
			pair.m_x = m_x - pairOther.m_x;
			pair.m_y = m_y - pairOther.m_y;

			return pair;
		}

		public Pair Plus(Pair pairOther)
		{
			Pair pair = new Pair();
			pair.m_x = m_x + pairOther.m_x;
			pair.m_y = m_y + pairOther.m_y;

			return pair;
		}

		public void Reduce()
		{
			if (m_x == 0)
			{
				m_y = (m_y > 0) ? 1 : -1;
			}
			else if (m_y == 0)
			{
				m_x = (m_x > 0) ? 1 : -1;
			}
			else
			{
				// BB (davidm) can I do better here?

				for (int test = Math.min(m_x, m_y); test > 1; --test)
				{
					if ((m_x / test) * test != m_x)
						continue;

					if ((m_y / test) * test != m_y)
						continue;

					m_x /= test;
					m_y /= test;
					break;
				}
			}
		}

		@Override
		public int hashCode()
		{
			return 7 * m_x + 13 * m_y;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (obj instanceof Pair)
			{
				Pair pair = (Pair) obj;

				return m_x == pair.m_x && m_y == pair.m_y;
			}

			return false;
		}
	}

	static class AsterMap
	{
		HashSet<Pair> m_setPair = null;	// Asteroid coordinates
		int m_xMax = 0;
		int m_yMax = 0;
		boolean m_fDebug = false;

		public void SetDebug(boolean fDebug)
		{
			m_fDebug = fDebug;
		}

		public void Load(String[] aStr)
		{
			m_yMax = aStr.length;
			m_xMax = aStr[0].length();
			m_setPair = new HashSet<>();

			for (int y = 0; y < m_yMax; ++y)
			{
				for (int x = 0; x < m_xMax; ++x)
				{
					if (aStr[y].charAt(x) == '#')
					{
						Pair pair = new Pair();
						pair.m_x = x;
						pair.m_y = y;

						m_setPair.add(pair);
					}
				}
			}
		}

		public void PrintSight()
		{
			// For each pair, calculate its "sight" count
			
			HashMap<Pair, Integer> mapPairC = new HashMap<>();
			FillSight(mapPairC);

			if (m_fDebug) { PrintMap(mapPairC); }

			// Print out the maximal sight count and its coordinate

			Pair pairMost = null;
			int cMost = -1;

			for (Map.Entry<Pair, Integer> entry : mapPairC.entrySet())
			{
				if (entry.getValue() > cMost)
				{
					pairMost = entry.getKey();
					cMost = entry.getValue();
				}
			}

			System.out.println("Most: " + cMost + " at " + pairMost.m_x + "," + pairMost.m_y);
		}

		boolean FIsInRange(Pair pair)
		{
			return	pair.m_x >= 0 &&
					pair.m_x < m_xMax &&
					pair.m_y >= 0 &&
					pair.m_y < m_yMax;
		}

		void FillSight(HashMap<Pair, Integer> mapPairC)
		{
			// Horrible N^2 algorithm here...can we do better somehow? bounding box? hmm...

			for (Pair pair : m_setPair)
			{
				// Calculate blocked pairs

				HashSet<Pair> setPairBlocked = new HashSet<>();

				for (Pair pairOther : m_setPair)
				{
					if (pairOther.equals(pair))
						continue;

					Pair dPair = pairOther.Minus(pair);
					dPair.Reduce();

					Pair pairWalk = pairOther.Plus(dPair);
					while (FIsInRange(pairWalk))
					{
						setPairBlocked.add(pairWalk);
						pairWalk = pairWalk.Plus(dPair);
					}
				}

				// Calculate visible pairs

				// BB (davidm) can an asteroid see itself?

				int cPair = 0;

				for (Pair pairOther : m_setPair)
				{
					if (pairOther.equals(pair))
						continue;

					if (setPairBlocked.contains(pairOther))
						continue;

					++cPair;
				}

				mapPairC.put(pair, cPair);
			}
		}

		void PrintMap(HashMap<Pair, Integer> mapPairC)
		{
			for (int y = 0; y < m_yMax; ++y)
			{
				for (int x = 0; x < m_xMax; ++x)
				{
					Pair pair = new Pair();
					pair.m_x = x;
					pair.m_y = y;

					if (mapPairC.containsKey(pair))
					{
						// BB (davidm) values > 9?

						System.out.print(mapPairC.get(pair));
					}
					else
					{
						System.out.print(".");
					}
				}

				System.out.println();
			}
		}
	}

	static String[] s_aStrIn = {
		"...",
		"...",
	};
}
