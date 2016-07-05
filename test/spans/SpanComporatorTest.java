package spans;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SpanComporatorTest
{

	private SpanComparator comparator;

	private final int beforeFrom = 9;
	private final int atFrom = 10;
	private final int afterFrom = 11;
	private final int beforeTo = 19;
	private final int atTo = 20;
	private final int afterTo = 21;

	@Before
	public void before()
	{
		comparator = new SpanComparator();
	}

	@Test
	public void isEmpty()
	{
		assertFalse(comparator.isEmpty(new Span("[10,10]")));
		assertFalse(comparator.isEmpty(new Span("[10,11]")));
		assertTrue(comparator.isEmpty(new Span("[10,10)")));
	}

	@Test
	public void closedContains()
	{
		Span closedUnit = new Span("[10,20]");
		assertFalse(comparator.contains(closedUnit, beforeFrom));
		assertTrue(comparator.contains(closedUnit, atFrom));
		assertTrue(comparator.contains(closedUnit, afterFrom));
		assertTrue(comparator.contains(closedUnit, beforeTo));
		assertTrue(comparator.contains(closedUnit, atTo));
		assertFalse(comparator.contains(closedUnit, afterTo));
	}

	@Test
	public void fromOpenContains()
	{
		Span fromOpenUnit = new Span("(10,20]");
		assertFalse(comparator.contains(fromOpenUnit, beforeFrom));
		assertFalse(comparator.contains(fromOpenUnit, atFrom));
		assertTrue(comparator.contains(fromOpenUnit, afterFrom));
		assertTrue(comparator.contains(fromOpenUnit, beforeTo));
		assertTrue(comparator.contains(fromOpenUnit, atTo));
		assertFalse(comparator.contains(fromOpenUnit, afterTo));
	}

	@Test
	public void toOpenContains()
	{
		Span toOpenUnit = new Span("[10,20)");
		assertFalse(comparator.contains(toOpenUnit, beforeFrom));
		assertTrue(comparator.contains(toOpenUnit, atFrom));
		assertTrue(comparator.contains(toOpenUnit, afterFrom));
		assertTrue(comparator.contains(toOpenUnit, beforeTo));
		assertFalse(comparator.contains(toOpenUnit, atTo));
		assertFalse(comparator.contains(toOpenUnit, afterTo));
	}

	@Test
	public void inclusiveIntersects()
	{
		Span span = new Span("[10,20]");
		noIntersection(span, "[5,9]");
		assertIntersection(span, "[5,15]", "[10,15]");
		assertIntersection(span, "[10,20]","[10,20]");
		assertIntersection(span, "[11,19]","[11,19]");
		assertIntersection(span,"[15,25]","[15,20]");
		noIntersection(span, "[25,30]");
	}


	private void assertSpans(Span expected, Span actual)
	{
		assertEquals(expected.toString(), actual.toString());
	}

	@Test
	public void exclusiveLeftIntersects()
	{
		Span span = new Span("(10,20]");
		noIntersection(span, "[5,9]");
		assertIntersection(span,"[5,15]","(10,15]");
		assertIntersection(span,"(10,20]","(10,20]");
		assertIntersection(span,"[10,20]","(10,20]");
		assertIntersection(span,"[11,19]","[11,19]");
		assertIntersection(span,"[15,25]","[15,20]");
		noIntersection(span, "[25,30]");

		noIntersection(span, "[5,10]");
		noIntersection(span, "[5,10)");
	}
	
	@Test
	public void mixedEndpointIntersection()
	{
		Span span = new Span("(10,20)");
		assertIntersection(span,"[10,20]","(10,20)");
	}

	@Test
	public void exclusiveRightIntersects()
	{
		Span span = new Span("[10,20)");
		noIntersection(span, "[5,9]");
		assertIntersection(span, "[5,15]", "[10,15]");
		assertIntersection(span,"[10,20)","[10,20)");
		assertIntersection(span,"[11,19]","[11,19]");
		assertIntersection(span,"[15,25]","[15,20)");
		noIntersection(span, "[25,30]");

		noIntersection(span, "[20,30]");
		noIntersection(span, "(20,30]");
	}

	private void noIntersection(Span span, String other)
	{
		assertFalse(comparator.intersects(span, new Span(other)));
	}
	
	private void assertIntersection(Span span, String other, String expected)
	{
		assertTrue(comparator.intersects(span, new Span(other)));
		assertSpans(new Span(expected),comparator.intersection(span, new Span(other)));
	}


}
