public class Itinerary {
    public Itinerary(Flight firstLeg) {
        // TODO Write test cases first
    	
    	Itinerary itinerary = new Itinerary(firstLeg);
    }

    public void appendCity(City city) {
    	
        // TODO Write test cases first
    }

    public double cost() {
        // TODO Write test cases first
        return 0;
    }

    public double duration() {
        // TODO Write test cases first
        return 0;
    }

    public int distance() {
        // TODO Write test cases first
    City source =	createCity();
    City destination =	createCity();
    Flight firstLeg = new Flight(source , destination , (long) 1234);
    Flight secondLeg = new Flight(source , destination , (long) 0);
    	
        return (int) (firstLeg.distance+secondLeg.distance);
    }
    
    public City createCity(){
    	City source = new City("S","SRC", "Saleno", "Europe", 1,12345,2, "N 41", "E 43");
		return source;
    }
}