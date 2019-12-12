/** Day 12 driver and solution
 */

class Day12
{
	public static void main(String[] args)
	{
		Example1();
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

		boolean m_fDebug = false;

		public void SetDebug(boolean fDebug)
		{
			m_fDebug = fDebug;
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

		public void Run(int cStep)
		{
			for (int iStep = 0; iStep < cStep; ++iStep)
			{
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
	}
}
