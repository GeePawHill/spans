package spans;

import java.util.Comparator;

public class PointComparator implements Comparator<Point>
{

	@Override
	public int compare(Point lhs, Point rhs)
	{
		if (lhs.value > rhs.value) return 1;
		if (lhs.value < rhs.value) return -1;
		if (lhs.type == rhs.type) return 0;
		switch (lhs.type)
		{
		case LT:
		case LE:
			// with lessers the includer is the smallest
			if (lhs.includes(rhs)) return 1;
			return -1;
		case GT:
		case GE:
			// with greaters the includer is the largest 
			if (lhs.includes(rhs)) return -1;
			return 1;
		}
		return 0;
	}
}
