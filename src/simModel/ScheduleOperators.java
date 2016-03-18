package simModel;

import simulationModelling.ScheduledAction;

class ScheduleOperators extends ScheduledAction
{

	SMTravel model;

	// Constructor
	protected ScheduleOperators(SMTravel model) { this.model = model; }

	double [] ts = { 0.0, 60.0, 120.0, 180.0, 240.0, 480.0, 540.0, 600.0, 660.0, -1.0}; // 7a 8a 9a 10a 11a 3p 4p 5p 6p
	int tsix = 0;  

	@Override
	protected double timeSequence() {
		return ts[tsix++];
	}

	@Override
	protected void actionEvent() {
		model.rgOperators[Constants.REGULAR].uNumOperators = 0;
		model.rgOperators[Constants.SILVER].uNumOperators = 0;
		model.rgOperators[Constants.GOLD].uNumOperators = 0;
		if(model.getClock() >= 0 && model.getClock() <480) {
			model.rgOperators[Constants.REGULAR].uNumOperators = model.rgOperators[Constants.REGULAR].schedule[0];
			model.rgOperators[Constants.SILVER].uNumOperators = model.rgOperators[Constants.SILVER].schedule[0];
			model.rgOperators[Constants.GOLD].uNumOperators = model.rgOperators[Constants.GOLD].schedule[0];
		}
		if(model.getClock() >= 60 && model.getClock() <540) {
			model.rgOperators[Constants.REGULAR].uNumOperators += model.rgOperators[Constants.REGULAR].schedule[1];
			model.rgOperators[Constants.SILVER].uNumOperators += model.rgOperators[Constants.SILVER].schedule[1];
			model.rgOperators[Constants.GOLD].uNumOperators += model.rgOperators[Constants.GOLD].schedule[1];
		}
		if(model.getClock() >= 120 && model.getClock() <600) {
			model.rgOperators[Constants.REGULAR].uNumOperators += model.rgOperators[Constants.REGULAR].schedule[2];
			model.rgOperators[Constants.SILVER].uNumOperators += model.rgOperators[Constants.SILVER].schedule[2];
			model.rgOperators[Constants.GOLD].uNumOperators += model.rgOperators[Constants.GOLD].schedule[2];
		}
		if(model.getClock() >= 180 && model.getClock() <660) {
			model.rgOperators[Constants.REGULAR].uNumOperators += model.rgOperators[Constants.REGULAR].schedule[3];
			model.rgOperators[Constants.SILVER].uNumOperators += model.rgOperators[Constants.SILVER].schedule[3];
			model.rgOperators[Constants.GOLD].uNumOperators += model.rgOperators[Constants.GOLD].schedule[3];
		}
		if(model.getClock() >= 240 && model.getClock() <720) {
			model.rgOperators[Constants.REGULAR].uNumOperators += model.rgOperators[Constants.REGULAR].schedule[4];
			model.rgOperators[Constants.SILVER].uNumOperators += model.rgOperators[Constants.SILVER].schedule[4];
			model.rgOperators[Constants.GOLD].uNumOperators += model.rgOperators[Constants.GOLD].schedule[4];
		}

	}


}