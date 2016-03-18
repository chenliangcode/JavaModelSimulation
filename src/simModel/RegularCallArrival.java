package simModel;

import simulationModelling.ScheduledAction;

public class RegularCallArrival extends ScheduledAction {

	SMTravel model;
	public RegularCallArrival(SMTravel model) { this.model = model; }
	
	protected double timeSequence() {
		return model.rvp.duRegularCall();
	}

	protected void actionEvent() {
		Call icCall = new Call();
		icCall.uType = Constants.REGULAR;
		icCall.uRequestType = model.rvp.uRequestType();
		icCall.uStartWaitTime = model.getClock();
		
		if (model.qPhoneSystem.trunklinesNum > model.qPhoneSystem.numOfCalls + model.qPhoneSystem.reservedLines) {
			model.qPhoneSystem.numOfCalls++;     
			model.udp.incNumberArrival(icCall.uType);
			//System.out.println("IncrNumArr");
			Hangup hangup = new Hangup(icCall, model);
			model.spStart(hangup);
			//SP.Start(Hangup, iC.Call)
		} else {
		    model.udp.incBusySignal(Constants.REGULAR);
			//System.out.println("IncBusySignal");
		    // SP.terminate();
		}
		
		//RegularCallArrival regularArrival = new RegularCallArrival(model);
		//model.scheduleAction(regularArrival);
		
	}

}
