package simModel;

import simulationModelling.ScheduledAction;

class Initialise extends ScheduledAction
{
    SMTravel model;
    
    // Constructor
    protected Initialise(SMTravel model) { this.model = model; }

    double [] ts = { 0.0, -1.0 }; // -1.0 ends scheduling
    int tsix = 0;  // set index to first entry.
    protected double timeSequence() 
    {
        return ts[tsix++];  // only invoked at t=0
    }

    protected void actionEvent() 
    {
        // System Initialisation
    	for(int id = Constants.REGULAR; id <= Constants.GOLD; id++){
    		model.rgOperators[id].numOfBusy = 0;
    	}
    	
        for(int id = Constants.REGULAR; id <= Constants.GOLD; id++){
            model.output.percentExceedWait[id] = 0;
            
        }
        model.output.percentBusySignal[Constants.REGULAR] = 0;
        model.output.percentBusySignal[Constants.CARDHOLDER] = 0;
        for(int index = 0; index < 12; ++index){
            for(int id = Constants.REGULAR; id <= Constants.GOLD; id++){
                model.output.arrivalPerHourMatrix[id][index] = 0;
                model.output.longWaitPerHourMatrix[id][index] = 0;
            }
            for(int id = Constants.REGULAR; id <= Constants.CARDHOLDER; id++){
                model.output.busyPerHourMatrix[id][index] = 0;
            }
        }
        //System.out.println("INIT: "+ model.rgOperators[Constants.REGULAR].schedule[0]);
        //System.out.println("Initialization finished!");
    }
    

}