package simModel;

import simulationModelling.ExtSequelActivity;

public class Hangup extends ExtSequelActivity {

	Call icCall;
	SMTravel model;
	public Hangup(Call icCall,SMTravel model) { 
		this.icCall = icCall;
	    this.model = model;	
	}
	
	@Override
	protected double duration() {
		return model.rvp.uHangupTime(icCall.uType);
	}

	@Override
	public void startingEvent() {
		switch(icCall.uType) {
		case Constants.REGULAR:
			super.name = "from Regular call";
			break;
		case Constants.SILVER:
			super.name = "from Silver call";
			break;
		case Constants.GOLD:
			super.name = "from Gold call";
			break;
		default:
			super.name = "Invalid type of call"; // This should not happen... like ever.
		}
		icCall.uStartWaitTime = model.getClock();
		model.qPhoneSystem.list.add(icCall);
		//System.out.println("insertion time: " + model.getClock());
		//System.out.print(model.rgOperators[0].schedule[0]);
	}

	@Override
	protected void terminatingEvent() {
		model.qPhoneSystem.list.remove(icCall);
		if(model.udp.userWaitedTooLong(icCall.uType, model.getClock()-icCall.uStartWaitTime)){
			model.udp.incLongWait(icCall);
		}
				
		model.qPhoneSystem.numOfCalls--; 
		
	}

	@Override
	public int interruptionPreCond() {
		if(model.udp.canServiceCall(icCall)){
			//System.err.println("BYE");
			return 1;
		} else {
		return 0;
		}
	}

	@Override
	public void interruptionSCS(int arg0) {
		if(arg0 == 1) {
			model.qPhoneSystem.list.remove(icCall);
			//model.output.beingSed++;  
			if(model.udp.userWaitedTooLong(icCall.uType, model.getClock()-icCall.uStartWaitTime)){
				model.udp.incLongWait(icCall);
				
			}
			ServiceCall serviceCall = new ServiceCall(icCall,model);
			model.spStart(serviceCall);
		}
	}

}

