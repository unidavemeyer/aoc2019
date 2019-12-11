import java.util.HashMap;

/** Day 11 driver
 */
class Day11
{
	public static void main(String[] args)
	{
		long[] aNProg = {3,8,1005,8,361,1106,0,11,0,0,0,104,1,104,0,3,8,102,-1,8,10,101,1,10,10,4,10,108,0,8,10,4,10,1001,8,0,28,2,1104,18,10,1006,0,65,3,8,102,-1,8,10,1001,10,1,10,4,10,108,1,8,10,4,10,1001,8,0,57,1,1101,5,10,2,108,15,10,2,102,12,10,3,8,1002,8,-1,10,101,1,10,10,4,10,108,0,8,10,4,10,102,1,8,91,2,1005,4,10,2,1107,10,10,1006,0,16,2,109,19,10,3,8,1002,8,-1,10,1001,10,1,10,4,10,1008,8,1,10,4,10,101,0,8,129,1,104,3,10,1,1008,9,10,1006,0,65,1,104,5,10,3,8,1002,8,-1,10,101,1,10,10,4,10,108,1,8,10,4,10,102,1,8,165,1,1106,11,10,1,1106,18,10,1,8,11,10,1,4,11,10,3,8,1002,8,-1,10,101,1,10,10,4,10,108,1,8,10,4,10,1001,8,0,203,2,1003,11,10,1,1105,13,10,1,101,13,10,3,8,102,-1,8,10,101,1,10,10,4,10,108,0,8,10,4,10,101,0,8,237,2,7,4,10,1006,0,73,1,1003,7,10,1006,0,44,3,8,102,-1,8,10,1001,10,1,10,4,10,108,1,8,10,4,10,101,0,8,273,2,108,14,10,3,8,102,-1,8,10,101,1,10,10,4,10,108,0,8,10,4,10,102,1,8,299,1,1107,6,10,1006,0,85,1,1107,20,10,1,1008,18,10,3,8,1002,8,-1,10,1001,10,1,10,4,10,1008,8,0,10,4,10,1001,8,0,337,2,107,18,10,101,1,9,9,1007,9,951,10,1005,10,15,99,109,683,104,0,104,1,21102,1,825594852248L,1,21101,378,0,0,1105,1,482,21101,0,387240006552L,1,21101,0,389,0,1106,0,482,3,10,104,0,104,1,3,10,104,0,104,0,3,10,104,0,104,1,3,10,104,0,104,1,3,10,104,0,104,0,3,10,104,0,104,1,21101,0,29032025091L,1,21101,436,0,0,1106,0,482,21101,29033143299L,0,1,21102,1,447,0,1105,1,482,3,10,104,0,104,0,3,10,104,0,104,0,21101,988669698916L,0,1,21101,0,470,0,1106,0,482,21101,0,709052072804L,1,21102,1,481,0,1106,0,482,99,109,2,21202,-1,1,1,21101,0,40,2,21101,0,513,3,21101,503,0,0,1106,0,546,109,-2,2105,1,0,0,1,0,0,1,109,2,3,10,204,-1,1001,508,509,524,4,0,1001,508,1,508,108,4,508,10,1006,10,540,1101,0,0,508,109,-2,2105,1,0,0,109,4,1202,-1,1,545,1207,-3,0,10,1006,10,563,21102,0,1,-3,21202,-3,1,1,22101,0,-2,2,21102,1,1,3,21101,582,0,0,1105,1,587,109,-4,2106,0,0,109,5,1207,-3,1,10,1006,10,610,2207,-4,-2,10,1006,10,610,21202,-4,1,-4,1106,0,678,22102,1,-4,1,21201,-3,-1,2,21202,-2,2,3,21102,629,1,0,1106,0,587,22102,1,1,-4,21101,0,1,-1,2207,-4,-2,10,1006,10,648,21102,0,1,-1,22202,-2,-1,-2,2107,0,-3,10,1006,10,670,21202,-1,1,1,21101,670,0,0,105,1,545,21202,-2,-1,-2,22201,-4,-2,-4,109,-5,2106,0,0};

		HullRobot hr = new HullRobot();
		hr.Load(aNProg);
		hr.SetPanel(new Pair(0,0), HullRobot.Color.WHITE);	// NOTE: only for part 2
		hr.Run();

		System.out.println("Colored " + hr.CPanelColored() + " panels");

		hr.PrintPanels();
	}

	static class HullRobot
	{
		public enum Dir
		{
			UP,
			LEFT,
			DOWN,
			RIGHT;
		}

		public enum Color
		{
			BLACK (0),
			WHITE (1);

			public int m_n;

			Color(int n)
			{
				m_n = n;
			}
		}

		Intcode m_ic = null;
		Dir m_dir = Dir.UP;
		Pair m_pos = null;
		HashMap<Pair, Color> m_mapPairColor = null;

		HullRobot()
		{
			m_ic = new Intcode();
			m_pos = new Pair(0, 0);
			m_mapPairColor = new HashMap<>();
		}

		public void Load(long[] aNProg)
		{
			m_ic.SetOps(aNProg);
			m_ic.SetDebug(false);
		}

		public void SetPanel(Pair pair, Color color)
		{
			m_mapPairColor.put(pair, color);
		}

		public void Run()
		{
			for (;;)
			{
				// Input current color and let the brain run

				Color colorCur = m_mapPairColor.getOrDefault(m_pos, Color.BLACK);
				m_ic.AddInput(colorCur.m_n);
				m_ic.RunOps();

				// Get output color and place into map
				
				long[] aNOutput = m_ic.ANOutput();
				Color colorNew = (aNOutput[aNOutput.length - 2] == 0L) ? Color.BLACK : Color.WHITE;
				m_mapPairColor.put(m_pos, colorNew);

				// Rotate as appropriate
				
				int nDir = (int)aNOutput[aNOutput.length - 1];
				if (nDir == 0)
				{
					RotateLeft();
				}
				else
				{
					RotateRight();
				}

				// Move along

				Advance();

				// System.out.println("At " + m_pos.m_x + "," + m_pos.m_y + " heading " + m_dir + " color " + colorNew + ", running: " + m_ic.FIsRunning() + ", outputs: " + aNOutput.length);

				// Check if we're done

				if (!m_ic.FIsRunning())
					break;
			}
		}

		public int CPanelColored()
		{
			return m_mapPairColor.size();
		}

		public void PrintPanels()
		{
			// Calculate extents of visited panels
			
			Pair pairMin = new Pair(0,0);
			Pair pairMax = new Pair(0,0);

			for (Pair pair : m_mapPairColor.keySet())
			{
				pairMin = pairMin.Min(pair);
				pairMax = pairMax.Max(pair);
			}

			// Iterate over extents printing out . values for black and # values for white
			
			for (int y = pairMin.m_y; y <= pairMax.m_y; ++y)
			{
				for (int x = pairMin.m_x; x <= pairMax.m_x; ++x)
				{
					Color color = m_mapPairColor.getOrDefault(new Pair(x,y), Color.BLACK);
					System.out.print((color == Color.BLACK) ? '.' : '#');
				}

				System.out.println();
			}
		}

		void RotateLeft()
		{
			// BB (davidm) dumb way to do this

			switch (m_dir)
			{
			case UP:
				m_dir = Dir.LEFT;
				break;

			case LEFT:
				m_dir = Dir.DOWN;
				break;

			case DOWN:
				m_dir = Dir.RIGHT;
				break;

			case RIGHT:
				m_dir = Dir.UP;
				break;
			}
		}
		
		void RotateRight()
		{
			// BB (davidm) dumb way to do this

			switch (m_dir)
			{
			case UP:
				m_dir = Dir.RIGHT;
				break;

			case LEFT:
				m_dir = Dir.UP;
				break;

			case DOWN:
				m_dir = Dir.LEFT;
				break;

			case RIGHT:
				m_dir = Dir.DOWN;
			}
		}
		
		void Advance()
		{
			Pair dPair = new Pair(0, 0);

			switch (m_dir)
			{
			case UP:
				dPair.m_y = -1;
				break;

			case DOWN:
				dPair.m_y = 1;
				break;

			case LEFT:
				dPair.m_x = -1;
				break;

			case RIGHT:
				dPair.m_x = 1;
				break;
			}

			m_pos = m_pos.Plus(dPair);
		}
	}
}
