package simModel;

import simulationModelling.SequelActivity;

public class AfterCall extends SequelActivity{

	int requestType;
	int operatorType;
	SMTravel model;
	public AfterCall(int requestType, int operatorType, SMTravel model){
		
		this.requestType = requestType;
		this.operatorType = operatorType;
		this.model = model;
	}
	
	@Override
	protected double duration() {
		 return model.rvp.uAfterCallTime(requestType);
		
	}

	@Override
	public void startingEvent() {
		; // Do nothing
	}

	@Override
	protected void terminatingEvent() {
		model.rgOperators[operatorType].numOfBusy --;
		//model.output.finished++; // debug only, do remember to remove it
		//System.out.println(model.rgOperators[operatorType].numOfBusy);
	}

}
