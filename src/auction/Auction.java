package auction;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import bidders.BiddingAgent;
import jade.core.AID;

public class Auction {
	private final int totalTimeSteps; // Maximum number of time steps *for each good* in this auction. From 30 to 100
	private int currentTimeStep; // The current time step *for the whole auction*
	private int currentGoodNumber; // The current good
	private Map<Good, HighestBid> goods; // The goods and their current highest bid
	private ArrayList<BiddingAgent> bidders; // All the bidders who compete in this auction
	
	public Auction() {
		totalTimeSteps = new Random(71).nextInt() + 30;
		currentTimeStep = 0;
		currentGoodNumber = 0;
		goods = new LinkedHashMap<Good, HighestBid>();
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
	
	/*public void advanceTimeStep() {
		currentTimeStep += 1;
		if (currentTimeStep >= totalTimeSteps) {
			currentTimeStep = 0;
			currentGoodNum
		}
	}*/
	
	public int getCurrentGoodNumber() {
		return currentGoodNumber;
	}
	
	public double getHighestBid(Good good) {
		return goods.get(good).getHighestBid();
	}
	
	public void addGood(Good good) {
		goods.put(good, new HighestBid(0., new AID()));
	}
	
	public void setHighestBid(Good good, double highestBid, AID aid) {
		goods.put(good, new HighestBid(highestBid, aid));
	}
	
	public ArrayList<BiddingAgent> getBidders() {
		return bidders;
	}
}
