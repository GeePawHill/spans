package spans;

public class Point
{

	public final int value;
	public final Type type;

	public Point(int value, Type type)
	{
		this.value = value;
		this.type = type;
	}

	public boolean includes(Point rhs)
	{
		switch (type)
		{
		case GE:
			if (rhs.value > value) return true;
			if (rhs.value < value) return false;
			return rhs.type == Type.GE || rhs.type == Type.LE;
		case GT:
			if (rhs.value > value) return true;
			return false;
		case LE:
			if (rhs.value < value) return true;
			if (rhs.value > value) return false;
			return rhs.type == Type.GE || rhs.type == Type.LE;
		case LT:
			if (rhs.value < value) return true;
			return false;
		default:
			throw new RuntimeException("Non-existent type.");
		}
	}

	@Override
	public String toString()
	{
		switch (type)
		{
		case GE:
			return "[" + value;
		case GT:
			return "(" + value;
		case LE:
			return value + "]";
		case LT:
			return value + ")";
		default:
			return "ERROR";
		}
	}

}