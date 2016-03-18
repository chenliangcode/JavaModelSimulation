package simModel;

import simulationModelling.SequelActivity;

public class ServiceCall extends SequelActivity{
	Call icCall;
	SMTravel model;
	int operatorType;
	public ServiceCall(Call icCall, SMTravel model){
		this.icCall = icCall;
		this.model = model;
	}
	@Override
	protected double duration() {
		return model.rvp.uSrvTm(icCall.uRequestType, operatorType);
	}
	@Override
	public void startingEvent() {
		operatorType = model.udp.assignOperator(icCall);
		switch(operatorType) {
		case Constants.REGULAR:
			super.name = "Regular Operator";
			break;
		case Constants.SILVER:
			super.name = "Silver Operator";
			break;
		case Constants.GOLD:
			super.name = "Gold Operator";
			break;
		default:
			super.name = "Invalid"; // This should not happen... like ever.
		}
		switch(icCall.uType) {
		case Constants.REGULAR:
			super.name += " serves Regular Customer";
			break;
		case Constants.SILVER:
			super.name += " serves Silver Customer";
			break;
		case Constants.GOLD:
			super.name += " serves Gold Customer";
			break;
		default:
			super.name += " serves Invalid"; // This should not happen... like ever.
		}
		
		
		
		
		model.rgOperators[operatorType].numOfBusy ++;
	}
	@Override
	protected void terminatingEvent() {
		model.qPhoneSystem.numOfCalls--;
		//model.output.beingSed--; 
		AfterCall afterCall = new AfterCall(icCall.uRequestType, operatorType, model);
		model.spStart(afterCall);
	}
	

}
