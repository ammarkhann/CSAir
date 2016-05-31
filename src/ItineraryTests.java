import static org.junit.Assert.*;

import org.junit.Test;


public class ItineraryTests {

	@Test
	public void testSingleFlightDistance() {
		City source = new City("S","SRC", "Saleno", "Europe", 1,12345,2, "N 41", "E 43");
		City destination = new City("D","DST", "Daseno", "Europe", 1,12345,2, "N 41", "E 43");
		Flight firstLeg = new Flight(source, destination, (long) 1234);
		Itinerary itinerary = new Itinerary(firstLeg);
		assertEquals(1234,itinerary.distance());
	}

}
