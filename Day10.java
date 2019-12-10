import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/** Day 10 driver and solution
 */
class Day10
{
	public static void main(String[] args)
	{
		// Yes: Example1();
		// Yes: Example2();
		// Yes: Example3();
		// Yes: Example4();

		Example5();
	}

	static void Example5()
	{
		String[] aStr = {
			".#..##.###...#######",
			"##.############..##.",
			".#.######.########.#",
			".###.#######.####.#.",
			"#####.##.#.##.###.##",
			"..#####..#.#########",
			"####################",
			"#.####....###.#.#.##",
			"##.#################",
			"#####.##.###..####..",
			"..######..##.#######",
			"####.##.####...##..#",
			".#####..#.######.###",
			"##...#.##########...",
			"#.##########.#######",
			".####.#.###.###.#.##",
			"....##.##.###..#####",
			".#.#.###########.###",
			"#.#.#.#####.####.###",
			"###.##.####.##.#..##",
		};

		RunExample(aStr);
	}

	static void Example4()
	{
		// BB (davidm) bug - we found the right coords, but have one too many things viewed...?
		String[] aStr = {
			".#..#..###",
			"####.###.#",
			"....###.#.",
			"..###.##.#",
			"##.##.#.#.",
			"....###..#",
			"..#.#..#.#",
			"#..#.#.###",
			".##...##.#",
			".....#.#..",
		};

		RunExample(aStr);
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

		RunExample(aStr);
	}

	static void Example2()
	{
		// May have a bug; 5,8 is supposed to be the 33, which we find, but we also find 9,5 as 33
		// Pair 9,5 vs 6,2: [-3, -3]
		// Pair 9,5 vs 8,6: 7,7 6,8 5,9
		// Pair 9,5 vs 5,3: 1,1
		// Pair 9,5 vs 7,2:
		// Pair 9,5 vs 5,8:
		// Pair 9,5 vs 7,7: 5,9
		// Pair 9,5 vs 0,1:
		// Pair 9,5 vs 0,6:
		// Pair 9,5 vs 2,5: 1,5 0,5
		// Pair 9,5 vs 4,4:
		// Pair 9,5 vs 6,3: 3,1
		// Pair 9,5 vs 8,2:
		// Pair 9,5 vs 8,7: 7,9
		// Pair 9,5 vs 7,3: 5,1
		// Pair 9,5 vs 9,7: 9,8 9,9
		// Pair 9,5 vs 6,9:
		// Pair 9,5 vs 8,8:
		// Pair 9,5 vs 3,1:
		// Pair 9,5 vs 1,7:
		// Pair 9,5 vs 3,6:
		// Pair 9,5 vs 7,9:
		// Pair 9,5 vs 2,2:
		// Pair 9,5 vs 0,8:
		// Pair 9,5 vs 2,7:
		// Pair 9,5 vs 6,0:
		// Pair 9,5 vs 8,9:
		// Pair 9,5 vs 1,3:
		// Pair 9,5 vs 3,2:
		// Pair 9,5 vs 1,8:
		// Pair 9,5 vs 5,1:
		// Pair 9,5 vs 7,5: 6,5 5,5 4,5 3,5 2,5 1,5 0,5
		// Pair 9,5 vs 9,9:
		// Pair 9,5 vs 4,2:
		// Pair 9,5 vs 4,7:
		// Pair 9,5 vs 8,0:
		// Pair 9,5 vs 1,4:
		// Pair 9,5 vs 3,3:
		// Pair 9,5 vs 1,9:
		// Pair 9,5 vs 5,2:

		String[] aStr = {
			"......#.#.",
			"#..#.#....",
			"..#######.",
			".#.#.###..",
			".#..#.....",
			"..#....#.#",
			"#..#....#.",
			".##.#..###",
			"##...#..#.",
			".#....####",
		};

		RunExample(aStr);
	}

	static void Example3()
	{
		String[] aStr = {
			"#.#...#.#.",
			".###....#.",
			".#....#...",
			"##.#.#.#.#",
			"....#.#.#.",
			".##..###.#",
			"..#...##..",
			"..##....##",
			"......#...",
			".####.###.",
		};

		RunExample(aStr);
	}

	static void RunExample(String[] aStr)
	{
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

				for (int test = Math.min(Math.abs(m_x), Math.abs(m_y)); test > 1; --test)
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

					if (m_fDebug) { System.out.print("Pair " + pair.m_x + "," + pair.m_y + " vs " + pairOther.m_x + "," + pairOther.m_y + ":"); }

					Pair pairWalk = pairOther.Plus(dPair);
					while (FIsInRange(pairWalk))
					{
						if (m_fDebug) { System.out.print(" " + pairWalk.m_x + "," + pairWalk.m_y); }

						setPairBlocked.add(pairWalk);
						pairWalk = pairWalk.Plus(dPair);
					}

					if (m_fDebug) { System.out.println(); }
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
						// BB (davidm) values > 999?

						int c = mapPairC.get(pair);
						if (c < 99)
							System.out.print(" ");

						System.out.print(c);

						if (c < 9)
							System.out.print(" ");
					}
					else
					{
						System.out.print(" . ");
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
