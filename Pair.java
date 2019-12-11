/** Pair of integer class
 */

class Pair
{
	public int m_x = 0;
	public int m_y = 0;

	Pair()
	{
	}

	Pair(int x, int y)
	{
		m_x = x;
		m_y = y;
	}

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
