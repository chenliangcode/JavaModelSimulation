package simModel;

import simulationModelling.SequelActivity;

public class IdentifyType extends SequelActivity{
	
	Call icCall;
	SMTravel model;
	public IdentifyType(Call icCall, SMTravel model) { 
		this.icCall = icCall;
		this.model = model;
	}

	@Override
	protected double duration() {
		return model.rvp.uTransactionTime();
	}

	@Override
	public void startingEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void terminatingEvent() {
		icCall.uType = model.rvp.uCallType();
		model.udp.incNumberArrival(icCall.uType);
		Hangup hangup = new Hangup(icCall,model);
		model.spStart(hangup);
	}

}
