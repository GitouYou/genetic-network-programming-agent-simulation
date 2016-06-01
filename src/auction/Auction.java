package auction;
import java.util.ArrayList;
import java.util.Random;

public class Auction {
	private final int totalTimeSteps; // Maximum number of time steps *for each good* in this auction. From 30 to 100
	private int currentTimeStep; // The current time step *for the whole auction*
	private int currentGoodNumber; // The current good
	private ArrayList<Good> goods; // The goods and their current highest bid
	private double highestBid;
	//private ArrayList<BiddingAgent> bidders; // All the bidders who compete in this auction

	public Auction() {
		this(new Random().nextInt(71) + 30, 0, 0, 0);
	}

	public Auction(int totalTimeSteps, int currentTimeStep, int currentGoodNumber, double highestBid) {
		this.totalTimeSteps = totalTimeSteps;
		this.currentTimeStep = currentGoodNumber;
		this.currentGoodNumber = currentGoodNumber;
		goods = new ArrayList<Good>();
		for (int i = 0; i < 10; ++i) goods.add(new Good());
		this.highestBid = highestBid;
		//bidders = new ArrayList<BiddingAgent>();
	}

	/*public void addBidder(BiddingAgent agent) {
		bidders.add(agent);
	}*/

	public int getTotalTimeSteps() {
		return totalTimeSteps;
	}

	public int getCurrentTimeStep() {
		return currentTimeStep;
	}

	public void advanceTimeStep() {
		currentTimeStep += 1;
		if (currentTimeStep >= totalTimeSteps) {
			currentTimeStep = 0;
			currentGoodNumber += 1;
			highestBid = 0;
		}
	}

	public int getCurrentGoodNumber() {
		return currentGoodNumber;
	}

	public double getHighestBid(/*Good good*/) {
		//return goods.get(good).getHighestBid();
		return highestBid;
	}

	/*public void addGood(Good good) {
		goods.put(good, new HighestBid(0., new AID()));
	}*/

	public void setHighestBid(/*Good good, double highestBid, AID aid*/ double highestBid) {
		//goods.put(good, new HighestBid(highestBid, aid));
		this.highestBid = highestBid;
	}

	public Good getCurrentGood() {
		return goods.get(currentGoodNumber);
	}

	/*public ArrayList<BiddingAgent> getBidders() {
		return bidders;
	}*/
}
