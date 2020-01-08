import java.util.ArrayList;
import java.util.HashMap;

/** Day 12 driver and solution
 */

class Day12
{
	public static void main(String[] args)
	{
		// Example1();
		// Part1();
		Part2();
	}

	static void Part1()
	{
		int[][] aaPos = {
			{ -10, -10, -13 },
			{ 5, 5, -9 },
			{ 3, 8, -16 },
			{ 1, 3, -3 },
		};

		Sim sim = new Sim();
		sim.SetPlanets(aaPos);
		sim.SetDebug(false);
		sim.SetTrackCycles(true);
		sim.Run(1000000);
		sim.ShowEnergy();
	}

	static void Part2()
	{
		// Calculate, per axis, what the frequency of repetition of the pattern is

		int[][] aaPos = {
			{ -10, -10, -13 },
			{ 5, 5, -9 },
			{ 3, 8, -16 },
			{ 1, 3, -3 },
		};

		Sim sim = new Sim();
		sim.SetPlanets(aaPos);
		sim.SetDebug(false);
		sim.SetTrackCycles(true);
		sim.Run(1000000);

		int[] mpAxisCycle = sim.MpAxisCycle();

		// Calculate the LCM of the axes. Instead of prime factoring (which sounds cool but hard) I'm going to instead just brute force my way there.
		//  The smallest the LCM can be is the largest of the cycle values, and the largest it can be is the product of them.

		long lcm = mpAxisCycle[0];
		lcm = Long.max(lcm, mpAxisCycle[1]);
		lcm = Long.max(lcm, mpAxisCycle[2]);

		while (true)
		{
			int axis;
			for (axis = 0; axis < 3; ++axis)
			{
				long rem = lcm % mpAxisCycle[axis];
				if (rem != 0)
					break;
			}

			if (axis == 3)
			{
				// Found the LCM

				break;
			}
			else
			{
				// Try the next value

				lcm++;
			}
		}

		System.out.println("LCM: " + lcm);
	}

	static long Reduce(long[] aN)
	{
		long factor = 0;
	reduce:
		for (long n = aN[0]; n > 1; --n)
		{
			for (int i = 0; i < 3; ++i)
			{
				if ((aN[i] / n) * n != aN[i])
					continue reduce;
			}

			// Found a factor!

			factor = n;
			break;
		}

		if (factor != 0)
		{
			for (int i = 0; i < 3; ++i)
			{
				aN[i] = aN[i] / factor;
			}
		}

		long product = aN[0] * aN[1] * aN[2];
		assert (product / (aN[0] * factor) * (aN[0] * factor) == product);
		assert (product / (aN[1] * factor) * (aN[1] * factor) == product);
		assert (product / (aN[2] * factor) * (aN[2] * factor) == product);

		return factor;
	}

	static void Example1()
	{
		int[][] aaPos = {
			{ -1, 0, 2 },
			{ 2, -10, -7 },
			{ 4, -8, 8 },
			{ 3, 5, -1 },
		};

		Sim sim = new Sim();
		sim.SetPlanets(aaPos);
		sim.SetDebug(true);
		sim.Run(10);
	}

	static class Sim
	{
		int[][] m_aaPosPlanet = null;

		int[][] m_aaVPlanet = null;

		int[] m_mpAxisDi = null;

		ArrayList<HashMap<String, Integer>> m_mpAxisMapStateStep = null;

		boolean m_fDebug = false;

		boolean m_fTrackCycles = false;

		public void SetDebug(boolean fDebug)
		{
			m_fDebug = fDebug;
		}

		public void SetTrackCycles(boolean fTrack)
		{
			m_fTrackCycles = fTrack;
		}

		public void SetPlanets(int[][] aaPos)
		{
			m_aaPosPlanet = aaPos;

			// Initialize all velocities

			m_aaVPlanet = new int[aaPos.length][];
			for (int iP = 0; iP < aaPos.length; ++iP)
			{
				m_aaVPlanet[iP] = new int[3];
			}
		}

		public int[] MpAxisCycle()
		{
			return m_mpAxisDi;
		}

		public void Run(int cStep)
		{
			boolean[] mpAxisFFound = new boolean[3];
			mpAxisFFound[0] = false;
			mpAxisFFound[1] = false;
			mpAxisFFound[2] = false;

			if (m_fTrackCycles)
			{
				m_mpAxisMapStateStep = new ArrayList<HashMap<String, Integer>>();
				for (int axis = 0; axis < 3; ++axis)
				{
					m_mpAxisMapStateStep.add(new HashMap<String, Integer>());
				}

				m_mpAxisDi = new int[3];
				m_mpAxisDi[0] = 0;
				m_mpAxisDi[1] = 0;
				m_mpAxisDi[2] = 0;
			}

			for (int iStep = 0; iStep < cStep; ++iStep)
			{
				if (m_fTrackCycles)
				{
					for (int axis = 0; axis < 3; ++axis)
					{
						if (mpAxisFFound[axis])
							continue;

						// Construct a string representing the state for this axis at this cycle

						String strAxis = "<" + m_aaPosPlanet[0][axis] + "," + m_aaPosPlanet[1][axis] + "," + m_aaPosPlanet[2][axis] + "><" +
										m_aaVPlanet[0][axis] + "," + m_aaVPlanet[1][axis] + "," + m_aaVPlanet[2][axis] + ">";

						// Check for the string in the map

						if (m_mpAxisMapStateStep.get(axis).containsKey(strAxis))
						{
							mpAxisFFound[axis] = true;

							int iStepOther = m_mpAxisMapStateStep.get(axis).get(strAxis);

							System.out.println("Step " + iStep + " is the same as step " + iStepOther + " for axis " + axis + " (period " + (iStep - iStepOther) + ")");

							m_mpAxisDi[axis] = iStep - iStepOther;
						}
						else
						{
							m_mpAxisMapStateStep.get(axis).put(strAxis, iStep);
						}
					}
				}

				UpdateVelocity();
				UpdatePosition();

				if (m_fDebug)
				{
					System.out.println("Step = " + iStep);
					for (int iP = 0; iP < m_aaPosPlanet.length; ++iP)
					{
						int[] pos = m_aaPosPlanet[iP];
						int[] v = m_aaVPlanet[iP];

						System.out.println("(" + pos[0] + "," + pos[1] + "," + pos[2] + ") (" + v[0] + "," + v[1] + "," + v[2] + ") (" + NEnergy(iP) + ")");
					}
				}
			}
		}

		public void ShowEnergy()
		{
			int total = 0;
			for (int iP = 0; iP < m_aaPosPlanet.length; ++iP)
			{
				total += NEnergy(iP);
			}

			System.out.println("Total energy: " + total);
		}

		void UpdateVelocity()
		{
			for (int iP0 = 0; iP0 < m_aaPosPlanet.length; ++iP0)
			{
				for (int iP1 = iP0 + 1; iP1 < m_aaPosPlanet.length; ++iP1)
				{
					// Adjust velocities for this pair (applies to both sides of the pairing)

					int[] pos0 = m_aaPosPlanet[iP0];
					int[] pos1 = m_aaPosPlanet[iP1];
					int[] v0 = m_aaVPlanet[iP0];
					int[] v1 = m_aaVPlanet[iP1];

					for (int i = 0; i < 3; ++i)
					{
						int delta = pos1[i] - pos0[i];

						if (delta < 0)
						{
							v0[i]--;
							v1[i]++;
						}
						else if (delta > 0)
						{
							v0[i]++;
							v1[i]--;
						}
					}
				}
			}
		}

		void UpdatePosition()
		{
			for (int iP = 0; iP < m_aaPosPlanet.length; ++iP)
			{
				for (int i = 0; i < 3; ++i)
				{
					m_aaPosPlanet[iP][i] += m_aaVPlanet[iP][i];
				}
			}
		}

		int NEnergy(int iPlanet)
		{
			int potential = 0;
			int kinetic = 0;

			int[] pos = m_aaPosPlanet[iPlanet];
			int[] v = m_aaVPlanet[iPlanet];

			for (int i = 0; i < 3; ++i)
			{
				potential += Math.abs(pos[i]);
				kinetic += Math.abs(v[i]);
			}

			return potential * kinetic;
		}

		int NEnergyTotal()
		{
			int nEnergy = 0;

			for (int iP = 0; iP < m_aaPosPlanet.length; ++iP)
			{
				nEnergy += NEnergy(iP);
			}

			return nEnergy;
		}

		int NKineticTotal()
		{
			int kinetic = 0;

			for (int iPlanet = 0; iPlanet < m_aaVPlanet.length; ++iPlanet)
			{
				int[] v = m_aaVPlanet[iPlanet];

				for (int i = 0; i < 3; ++i)
				{
					kinetic += Math.abs(v[i]);
				}
			}

			return kinetic;
		}
	}
}
