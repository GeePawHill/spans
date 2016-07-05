package spans;

import java.util.Arrays;

public class SpanComparator
{

	public boolean isEmpty(Span span)
	{
		if(span.from.value != span.to.value) return false;
		if( span.from.type==Type.GT || span.to.type==Type.LT) return true;
		return false;
	}

	public boolean contains(Span span, int d)
	{
		if (d < span.from.value) return false;
		if (d > span.to.value) return false;
		if (d == span.from.value && span.from.type == Type.GT) return false;
		if (d == span.to.value && span.to.type == Type.LT) return false;
		return true;
	}
	
	public boolean isEqual(Span left, Span right)
	{
		PointComparator comparator = new PointComparator();
		return comparator.compare(left.from,right.from)==0 && comparator.compare(left.to,right.to)==0;
	}

	public boolean intersects(Span first, Span second)
	{
		if(first.from.includes(second.from)&& first.to.includes(second.from) ) return true;
		if(first.from.includes(second.to) && first.to.includes(second.to)) return true;
		if(second.from.includes(first.from) && second.to.includes(first.from)) return true;
		if(second.from.includes(first.to)&& second.to.includes(first.to)) return true;
		return false;
	}

	public Span intersection(Span span, Span other)
	{
		Point[] sorted = new Point[4];
		sorted[0]=span.from;
		sorted[1]=span.to;
		sorted[2]=other.from;
		sorted[3]=other.to;
		Arrays.sort(sorted,new PointComparator());
		return new Span(sorted[1],sorted[2]);
	}

}
