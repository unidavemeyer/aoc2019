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
		sim.SetTrackEnergy(true);
		sim.Run(1000000);
		sim.ShowEnergy();
	}

	static void Part2()
	{
		// axis 0 (x): cycles 0 -> 83810 = 83811 -> 167621 = 0, 167622 = 167623, 167264 = 0 -> X = Y = 83810 -> 335245 = 0, 335246 = 335247 = 167622, etc.
		// axis 1 (y): cycles 0 -> 115805 = 115806 -> 231611 = 0, 231612 = 231613, 231614 = 0 -> 347419 = 347420 -> 463255 = 0, 463266 = 463267 = 231612, etc.
		// axis 2 (z): cycles 0 -> 51176 = 51177 -> 102353 = 0, 102354 = 102355, 102356 = 0 -> 153532 = 153533 = 51176 -> 204709 = 0, then 204710 = 204711 = 102354, etc.

		// Check to see if these numbers reduce by anything interesting; if so, print the GCD, the reduced bits, and the resulting product
		//  (mathematically, I'm guaranteed to hit a repeat after the product of all of these cycles, but there might be an earlier one if they
		//  have other factors in common)

		long[] aN = { 167622, 231612, 102354 };

		if (true)
		{
			int factor = 0;
		reduce:
			for (int n = 102354; n > 1; --n)
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
		}

		System.out.println("operands: " + aN[0] + ", " + aN[1] + "," + aN[2] + "; product: " + (aN[0] * aN[1] * aN[2]));

		// OK, super frustrating. I feel like I'm right on the edge of it, but I don't have it somehow. If I just straight multiply through
		//  the factors together, then I end up with a product that's too big. That sort of makes sense to me -- I think half way through
		//  (since these are a->b, b->a patterns) we should have a match...maybe? Maybe I also need to check velocity? Gah! I haven't checked
		//  that at all. :/ I have only been checking position. Grr. Anyway, I've apparently now inputted too many guesses, so it's no longer
		//  telling me if I'm over or under. I tried reducing everything by a factor of 6 and using that product, and one more than that, and
		//  those said they were too small. So I'm missing something in there somehow, which is...annoying.
		//
		//  I'm going to try displaying the positions *and* velocities of the different axes at the known end-ish or repeat-ish points and
		//  see if that sheds any light on what's going on. Grr.

		int[][] aaPos = {
			{ -10, -10, -13 },
			{ 5, 5, -9 },
			{ 3, 8, -16 },
			{ 1, 3, -3 },
		};

		Sim sim = new Sim();
		sim.SetPlanets(aaPos);
		sim.SetDebug(false);
		sim.SetTrackEnergy(false);
		sim.Run(231615);
		sim.ShowEnergy();

		// OK, yeah, the velocities are key here. What a drag. So, for axis 0, step 0 and step 167624 (or maybe step 0 and step 167623 by their counting?) are the same.
		// Rewrote my test slightly, so now I see that "after step 0" (which matches the example) and "after step 167624" are the same pos *and* velocity. So my 167622 is off by 2.
		// I think that would apply through the rest, so my numbers should actually be these:

		long[] aNTry2 = { 167624, 231614, 102356 };

		long factor = 0;
		if (true)
		{
			factor = Reduce(aNTry2);
		}

		System.out.println("Better attempt, reduced product (by " + factor + "): " + (aNTry2[0] * aNTry2[1] * aNTry2[2]));

		// Bleah. I am reduced to tracking what I have submitted so that I don't keep submitting the same thing over and over again.
		// I tried 3973876011060416 but that was wrong. That is the straight up product of the three sizes.
		// The sizes do all have a factor of two in common
		//
		// Gah. What if the problem is that there's a sub-part in there someplace that *also* overlaps? If those things occur on boundaries
		// between the three patterns that work out evenly, then I could have something earlier than the cycle that I calculated?
		// Oh. Hmm. Or, since things would have matched going 0 -> min(cycle), they should also match min(cycle)-> 0 as we approach the full
		// zero match case...right? Oh, but I don't know about velocities. Rats. I forgot that data only tracks pos. I guess I need to redo
		// my checking to check the full per-axis 8-value tuple. I'll try that tomorrow and see what happens.
		//
		// I discussed a bit with RJ, and what I missed (which is clear in retrospect) is that I need the LCM of the values for the
		// individual cycles. I'm going to take a step back and try again, with a full calculate-it-all system, and make sure it
		// does the right thing for the example cases and my case. Er, when I get back to trying this again, that is.
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

		ArrayList<HashMap<Integer, Integer>> m_listMapAxisStep = null;

		boolean m_fDebug = false;

		boolean m_fTrackEnergy = false;

		public void SetDebug(boolean fDebug)
		{
			m_fDebug = fDebug;
		}

		public void SetTrackEnergy(boolean fTrack)
		{
			m_fTrackEnergy = fTrack;
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
			if (m_fTrackEnergy)
			{
				m_listMapAxisStep = new ArrayList<HashMap<Integer, Integer>>();
				m_listMapAxisStep.add(new HashMap<Integer, Integer>());
				m_listMapAxisStep.add(new HashMap<Integer, Integer>());
				m_listMapAxisStep.add(new HashMap<Integer, Integer>());
			}

			for (int iStep = 0; iStep < cStep; ++iStep)
			{
				long[] aN = { 167624, 231614, 102356 };

				for (int i = 0; i < 3; ++i)
				{
					if (Math.abs(aN[i] - iStep) < 3 ||
							Math.abs(aN[i] / 2 - iStep) < 3 ||
							iStep == 0 || iStep == 1 || iStep == 2)
					{
						System.out.println("Axis " + i + ": after " + iStep + " steps, pos: " +
								m_aaPosPlanet[0][i] + ", " +
								m_aaPosPlanet[1][i] + ", " +
								m_aaPosPlanet[2][i] + ", " +
								m_aaPosPlanet[3][i] + "; " +
								"velocity: " +
								m_aaVPlanet[0][i] + ", " +
								m_aaVPlanet[1][i] + ", " +
								m_aaVPlanet[2][i] + ", " +
								m_aaVPlanet[3][i]);
					}
				}

				UpdateVelocity();
				UpdatePosition();

				if (m_fTrackEnergy)
				{
					for (int axis = 0; axis < 3; ++axis)
					{
						int n = m_aaPosPlanet[0][axis] + 500 * m_aaPosPlanet[1][axis] + 250000 * m_aaPosPlanet[2][axis] + 125000000 * m_aaPosPlanet[3][axis];

						if (m_listMapAxisStep.get(axis).containsKey(n))
						{
							System.out.println("Axis " + axis + " same at " + iStep + " as " + m_listMapAxisStep.get(axis).get(n));
						}
						else
						{
							m_listMapAxisStep.get(axis).put(n, iStep);
						}
					}
				}

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
