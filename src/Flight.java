

public class Flight {


	
	City source;
	City destination;
	Long distance;
	
	public Flight(City source , City destination, Long distance){
		this.source =source;
		this.destination=destination;
		this.distance = distance;
	}
	
	public String toString(){
		return source+ " " +destination+ " " +distance;
				
	}

	



}
