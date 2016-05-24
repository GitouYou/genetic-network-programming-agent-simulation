package auction;
import java.util.ArrayList;

public class Auction {
	private final int totalTimeSteps; // Maximum number of time steps *for each good* in this auction. From 30 to 100
	private int currentTimeStep; // The current time step *for the whole auction*
	private int currentGoodNumber; // The current good
	private int[] commonPrices; // These items' common prices. From 50 to 200
	private ArrayList<BiddingAgent> bidders; // All the bidders who compete in this auction
	
	public Auction(int timeSteps, int[] commonPrices) {
		this.totalTimeSteps = timeSteps;
		this.commonPrices = commonPrices;
		
		currentGoodNumber = 0;
		currentTimeStep = 0;
		bidders = new ArrayList<BiddingAgent>();
	}
	
	public void addBidder(BiddingAgent agent) {
		bidders.add(agent);
	}
	
	public int getTotalTimeSteps() {
		return totalTimeSteps;
	}
	
	public int getCurrentTimeStep() {
		return currentTimeStep;
	}
	
	public int getCurrentGoodNumber() {
		return currentGoodNumber;
	}
	
	public int[] getCommonPrices() {
		return commonPrices;
	}
	
	public ArrayList<BiddingAgent> getBidders() {
		return bidders;
	}
}
