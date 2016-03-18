package simModel;

import simulationModelling.AOSimulationModel;
import simulationModelling.Behaviour;
import simulationModelling.ExtConditionalActivity;
import simulationModelling.ExtScheduledActivity;
import simulationModelling.SBNotice;
import simulationModelling.SequelActivity;
import simulationModelling.ExtSequelActivity;
//
// The Simulation model Class
public class SMTravel extends AOSimulationModel
{

	// Constants available from Constants class
	/* Parameter */
	// Implemented as attributes of qPhoneSystem.trunklinesNum, qPhoneSystem.reservedLines,
	// rgOperators[REGULAR].schedule, rgOperators[SILVER].schedule, rgOperators[GOLD].schedule
	protected int totalCost;

	/*-------------Entity Data Structures-------------------*/
	/* Group and Queue Entities */
	// Define the reference variables to the various
	// entities with scope Set and Unary
	// Objects can be created here or in the Initialize Action

	protected Operators[] rgOperators = new Operators[3];
	protected PhoneSystem qPhoneSystem;

	/* Input Variables */
	// Define any Independent Input Variables here

	// References to RVP and DVP objects
	protected RVPs rvp;  // Reference to RVP object - object created in constructor
	//protected DVPs dvp = new DVPs(this);  // Reference to DVP object
	protected UDPs udp = new UDPs(this);

	// Output object
	public Output output;
    private boolean traceFlag;
	// Output values - define the public methods that return values
	// required for experimentation.


	// Constructor
	public SMTravel(double t0time, double tftime,
					int[] regularOperatorSchedule, int[] silverOperatorSchedule, int[] goldOperatorSchedule,
					int trunkLinesNum, int reservedLines, Seeds sd, boolean traceFlag)
	{

		rgOperators[Constants.REGULAR] = new Operators();
		rgOperators[Constants.SILVER] = new Operators();
		rgOperators[Constants.GOLD] = new Operators();
		qPhoneSystem = new PhoneSystem(trunkLinesNum, reservedLines);

		rgOperators[Constants.REGULAR].schedule = regularOperatorSchedule;
		rgOperators[Constants.SILVER].schedule = silverOperatorSchedule;
		rgOperators[Constants.GOLD].schedule = goldOperatorSchedule;
		this.traceFlag = traceFlag;
		
		output = new Output();
		// Initialize parameters here

		// Create RVP object with given seed
		rvp = new RVPs(this,sd);

		// rgCounter and qCustLine objects created in Initialize Action

		// Initialize the simulation model
		initAOSimulModel(t0time,tftime);

		     // Schedule the first arrivals and employee scheduling
		Initialise init = new Initialise(this);
		
		scheduleAction(init);  // Should always be first one scheduled.
		ScheduleOperators schedule = new ScheduleOperators(this);
		scheduleAction(schedule);  
		
		// Schedule other scheduled actions and activities here
		RegularCallArrival regularArrival = new RegularCallArrival(this);
		scheduleAction(regularArrival);
		// Schedule other scheduled actions and activities here
		CardholderCallArrival cardholderArrival = new CardholderCallArrival(this);
		scheduleAction(cardholderArrival);
	}

	/************  Implementation of Data Modules***********/
	/*
	 * Testing preconditions
	 */
	protected void testPreconditions(Behaviour behObj)
	{
		reschedule (behObj);
		// Check preconditions of Conditional Activities

		// Check preconditions of Interruptions in Extended Activities
		while(scanInterruptPreconditions() == true) /*repeat*/ ;
	}


	private boolean scanInterruptPreconditions() {
		int num; // number of entries in list
		int interruptionNum;  // interruption number
		SBNotice nt;
		Behaviour obj;
		boolean statusChanged = false;

		num = esbl.size();
		for(int i = 0; i < num ; i++)
		{
			nt = esbl.get(i);
			obj = (esbl.get(i)).behaviourInstance;
			if(ExtConditionalActivity.class.isInstance(obj)) {
				ExtConditionalActivity extCondObj = (ExtConditionalActivity) nt.behaviourInstance;
				interruptionNum = extCondObj.interruptionPreCond();
				if(interruptionNum != 0)
				{
					extCondObj.interruptionSCS(interruptionNum);
					statusChanged = true;
					unscheduleBehaviour(nt);
					i--; num--; // removed an entry, num decremented by 1 and need to look at same index for next loop
				}
			}
			else if(ExtSequelActivity.class.isInstance(obj))
			{
				ExtSequelActivity extSeqObj = (ExtSequelActivity) nt.behaviourInstance;
				interruptionNum = extSeqObj.interruptionPreCond();
				if(interruptionNum != 0)
				{
					extSeqObj.interruptionSCS(interruptionNum);
					statusChanged = true;
					unscheduleBehaviour(nt);
					i--; num--;
				}
			}
			else if(ExtScheduledActivity.class.isInstance(obj))
			{
				ExtScheduledActivity extSchedObj = (ExtScheduledActivity) nt.behaviourInstance;
				interruptionNum = extSchedObj.interruptionPreCond();
				statusChanged = true;
				if(interruptionNum != 0)
				{
					extSchedObj.interruptionSCS(interruptionNum);
					unscheduleBehaviour(nt);
					i--; num--;
				}
			}
		}
		return(statusChanged);

	}

	
	public void calculateOutput() {  
		output.updateOutput();
	}
	
	public double[][] getPercentBusyHour(){
		return output.percentBusyHourMatrix;
	}
	
	public double[] getPercentBusySignal(){
		return output.percentBusySignal;
	}
    
	public double[] getPercentExceedWait(){
		return output.percentExceedWait;
	}
	
	public double[][] getPercentWaitingHourMatrix(){
		return output.percentWaitingHourMatrix;
	}
	
	public void eventOccured()
	{
		
		//this.showSBL();
		// Can add other debug code to monitor the status of the system
		// See examples for suggestions on setup logging
		if(traceFlag)
		{
			int g=0,s=0,r=0;
			for(Call c : qPhoneSystem.list) {
				switch(c.uType) {
					case Constants.REGULAR:
						r++;
						break;
					case Constants.SILVER:
						s++;
						break;
					case Constants.GOLD:
						g++;
						break;
				}
			}
			
			
			System.out.println("Clock: "+getClock());
			System.out.println("# of Calls:" + qPhoneSystem.numOfCalls + ",");
			System.out.println("# of busy operators(Regular, Silver, Gold): " + rgOperators[Constants.REGULAR].numOfBusy +
					" " + rgOperators[Constants.SILVER].numOfBusy + " " + rgOperators[Constants.GOLD].numOfBusy);
            System.out.println("Number of Calls in the Queue(Gold, Silver, Regular)" + g + " " + s + " " + r );
            System.out.println("Number of trunk lines are being used: " + qPhoneSystem.numOfCalls);
            System.out.println("Total number of trunk lines: " + qPhoneSystem.trunklinesNum);
            System.out.println("Number of reserved lines: " + qPhoneSystem.reservedLines);
            int reservedUnused = 0;
            	if (qPhoneSystem.numOfCalls <= (qPhoneSystem.trunklinesNum - qPhoneSystem.reservedLines))
            		System.out.println("Number of reserved lines are been used: " + reservedUnused);
            	else
            		System.out.println("Number of reserved lines are been used: " + (qPhoneSystem.reservedLines - qPhoneSystem.trunklinesNum + qPhoneSystem.numOfCalls));
            

            showSBL();			
		}

	}

	public double calcCost() {
		return udp.computeTotalCost();
	}
	// Standard Procedure to start Sequel Activities with no parameters
	protected void spStart(SequelActivity seqAct)
	{
		seqAct.startingEvent();
		scheduleActivity(seqAct);
	}
	
}
