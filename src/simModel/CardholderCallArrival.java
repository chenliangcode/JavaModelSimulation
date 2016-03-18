package simModel;

import simModel.Call;
import simModel.SMTravel;
import simulationModelling.ScheduledAction;

public class CardholderCallArrival extends ScheduledAction 
{
	SMTravel model; 
	public CardholderCallArrival(SMTravel model) { this.model = model; }

	public double timeSequence() 
	{
		return model.rvp.duCardholderCall();
	}

	public void actionEvent() 
	{

		Call icCall = new Call();
		icCall.uRequestType = model.rvp.uRequestType();
		icCall.uStartWaitTime = model.getClock(); 
		//model.udp.incNumberArrival(icCall.uType);
		if(model.qPhoneSystem.trunklinesNum > model.qPhoneSystem.numOfCalls){
			model.qPhoneSystem.numOfCalls ++;   
			IdentifyType identifyType = new IdentifyType(icCall, model);
			model.spStart(identifyType);    
		}
		// SP.Start(IdentifyType, icCall);
		else {
			model.udp.incBusySignal(Constants.CARDHOLDER);
			// SP.Terminate();
		}


	}


}