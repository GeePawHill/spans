package spans;

public class Span {

	public final Point from;
	public final Point to;
	
	
	public Span(String text)
	{
		String[] sides = text.split(",");
		int fromValue = Integer.parseInt(sides[0].substring(1));
		Type fromCompare = sides[0].charAt(0)=='(' ? Type.GT : Type.GE;
		from = new Point(fromValue,fromCompare);
		int toValue = Integer.parseInt(sides[1].substring(0,sides[1].length()-1));
		Type toCompare = sides[1].charAt(sides[1].length()-1)==')' ? Type.LT : Type.LE;
		to = new Point(toValue,toCompare);
	}
	
	public Span(Point from, Point to)
	{
		this.from = from;
		this.to = to;
	}

	@Override
	public String toString()
	{
		return from.toString()+","+to.toString();
	}
	
}
