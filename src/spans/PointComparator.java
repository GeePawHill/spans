package spans;

import java.util.Comparator;

public class PointComparator implements Comparator<Point>
{

	@Override
	public int compare(Point lhs, Point rhs)
	{
		if (lhs.value > rhs.value) return 1;
		if (lhs.value < rhs.value) return -1;
		if (lhs.includes(rhs)) return -1;
		if (rhs.includes(lhs)) return 1;
		return 0;
	}
}
