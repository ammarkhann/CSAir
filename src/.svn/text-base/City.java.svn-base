import java.util.LinkedList;
import java.util.List;


public class City {
	
	String name;
	String code;
	String country;
	String continent;
	String latitude;
	String longitude;
	double timezone;
	int region;
    int population;
     List<Flight> flights = new LinkedList<Flight>();
	
	public City(String name, String code ,String country, String continent, int region,
			int population, double timezone,String latitude,String longitude    ){
		this.name = name;
		this.code=code;
		this.country=country;
		this.continent=continent;
     	this.timezone=timezone;
		this.region=region;
		this.population= population;
		this.latitude=latitude;
		this.longitude=longitude;
		
	}
	
	public List<Flight> getFlights() {
		return flights;
	}

	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}

	public String getName() {
		return name;
	}

	void addFlight(Flight flight){
		flights.add(flight);
	
	}
	void removeFlight(Flight flight){
		flights.remove(flight);
		
	
	}
		
	public String toString(){
		return name+ " " +code+ " " +country+ " " +continent+ " " +region
				+ " " +population+ " " +timezone;
	}




}
