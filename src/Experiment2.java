import simModel.Constants;
import simModel.SMTravel;
import simModel.Seeds;
import outputAnalysis.ConfidenceInterval;
import cern.jet.random.engine.*;


// Main Method: Experiment 1, used to run a single simulation generate 
// The results of 20 simulation runs to show CI's similar to Table 5.2
// from the text book

class Experiment2
{
	   // Create matrix of schedules
	   // Experimentation constants
	   public static final int NUMRUNS = 20;
	   public static final double CONF_LEVEL = 0.9;
	   
	   public static void main(String[] args)
	   {
	       int i; 
	       double startTime=0.0, endTime=720.0;
	       Seeds[] sds = new Seeds[NUMRUNS];
	       SMTravel smmodel;  // Simulation object
	       double [] valuesCase1 = new double[NUMRUNS];
	       double [] valuesCase2 = new double[NUMRUNS];
	       double [] valuesCase3 = new double[NUMRUNS];
	       double [] valuesCase4 = new double[NUMRUNS];
	       double [] valuesCase5 = new double[NUMRUNS];
	       double [] valuesCase6 = new double[NUMRUNS];
	       double [] valuesCase7 = new double[NUMRUNS];
	       double [] valuesCase8 = new double[NUMRUNS];
	       double [] valuesCase9= new double[NUMRUNS];
	       double [] valuesCase10 = new double[NUMRUNS];
	       double [] valuesCase11 = new double[NUMRUNS];
	       double [] valuesCase12 = new double[NUMRUNS];


	       // Lets get a set of uncorrelated seeds
	       RandomSeedGenerator rsg = new RandomSeedGenerator();
	       for(i=0 ; i<NUMRUNS ; i++) sds[i] = new Seeds(rsg);
	       
	       // Loop for NUMRUN simulation runs for each case
	       // Case 1
	       int[] regSchedule = {7,7,12,5,5};
    	   int[] silSchedule = {5,8,6,4,4};
    	   int[] golSchedule = {2,4,2,2,2};
    	   int trunkLinesNum = 50;
    	   int reservedLines = 3; 
	       for(i=0 ; i < NUMRUNS ; i++)
	       {
	    	  
	    	  smmodel = new SMTravel(startTime,endTime,regSchedule,silSchedule,golSchedule, trunkLinesNum, reservedLines, sds[i], false);
	    	  smmodel.runSimulation();
	    	  smmodel.calculateOutput(); 
	          //System.out.println("ahh " + (1-smmodel.getPercentExceedWait()[Constants.GOLD]));
	          valuesCase1[i] = 1-smmodel.getPercentExceedWait()[Constants.GOLD];
	          valuesCase2[i] = 1-smmodel.getPercentExceedWait()[Constants.SILVER];
	          valuesCase3[i] = 1-smmodel.getPercentExceedWait()[Constants.REGULAR];
	          valuesCase4[i] = smmodel.getPercentBusySignal()[Constants.CARDHOLDER];
			  valuesCase5[i] = smmodel.getPercentBusySignal()[Constants.REGULAR];
			  valuesCase6[i] = smmodel.calcCost();
	       }
	       // Case 2
	       int[] regSchedule1 = {7,11,11,7,7};
    	   int[] silSchedule1 = {4,3,0,7,3};
    	   int[] golSchedule1 = {3,1,0,1,3};
    	   int trunkLinesNum1 = 50;
    	   int reservedLines1 = 3; 
	       for(i=0 ; i < NUMRUNS ; i++)
	       {
	    	  smmodel = new SMTravel(startTime,endTime,regSchedule1,silSchedule1,golSchedule1,trunkLinesNum1, reservedLines1, sds[i], false);
	          smmodel.runSimulation();
	          smmodel.calculateOutput(); 
	          valuesCase7[i] = 1-smmodel.getPercentExceedWait()[Constants.GOLD];
	          valuesCase8[i] = 1-smmodel.getPercentExceedWait()[Constants.SILVER];
	          valuesCase9[i] = 1-smmodel.getPercentExceedWait()[Constants.REGULAR];
	          valuesCase10[i] = smmodel.getPercentBusySignal()[Constants.CARDHOLDER];
			  valuesCase11[i] = smmodel.getPercentBusySignal()[Constants.REGULAR];
			  valuesCase12[i]= smmodel.calcCost();
	       }	       
	       
	       
	       // Get the confidence intervals
	       ConfidenceInterval cfIntCase1 = new ConfidenceInterval(valuesCase1, CONF_LEVEL);
	       ConfidenceInterval cfIntCase2 = new ConfidenceInterval(valuesCase2, CONF_LEVEL);
	       ConfidenceInterval cfIntCase3 = new ConfidenceInterval(valuesCase3, CONF_LEVEL);
	       ConfidenceInterval cfIntCase4 = new ConfidenceInterval(valuesCase4, CONF_LEVEL);
	       ConfidenceInterval cfIntCase5 = new ConfidenceInterval(valuesCase5, CONF_LEVEL);
	       ConfidenceInterval cfIntCase6 = new ConfidenceInterval(valuesCase6, CONF_LEVEL);
	       ConfidenceInterval cfIntCase7 = new ConfidenceInterval(valuesCase7, CONF_LEVEL);
	       ConfidenceInterval cfIntCase8 = new ConfidenceInterval(valuesCase8, CONF_LEVEL);
	       ConfidenceInterval cfIntCase9 = new ConfidenceInterval(valuesCase9, CONF_LEVEL);
	       ConfidenceInterval cfIntCase10 = new ConfidenceInterval(valuesCase10, CONF_LEVEL);
	       ConfidenceInterval cfIntCase11 = new ConfidenceInterval(valuesCase11, CONF_LEVEL);
	       ConfidenceInterval cfIntCase12 = new ConfidenceInterval(valuesCase12, CONF_LEVEL);
	       System.out.println("Case 1 - The maximum operators needed to satisfy all satisfaction level");
	       System.out.println("Case 2 - The optimal operators needed to satisfy all satisfaction level");
	       System.out.println("PEWG - Percentage of gold customers that meets our satisfaction level for wait time");
	       System.out.println("PEWS - Percentage of silver customers that meets our satisfaction level for wait time");
	       System.out.println("PEWR - Percentage of regular customers that meets our satisfaction level for wait time");
	       System.out.println("PBSC - Percentage of busy signal for Cardholder Customer");
	       System.out.println("PBSR - Percentage of busy signal for Regual Customer");
	       System.out.println("TC - The total cost");
	       // Create the table
	       System.out.printf("Run|               Case 1                            |           Case 2\n");
	       System.out.printf("---+-------+-------+-------+-------+-------+---------+-------+-------+-------+-------+-------+---------+\n");
	       System.out.printf("   | PEWG  | PEWS  | PEWR  | PBSC  | PBSR  |   TC    | PEWG  | PEWS  | PEWR  | PBSC  | PBSR  |   TC    |\n");
	       System.out.printf("---+-------+-------+-------+-------+-------+---------+-------+-------+-------+-------+-------+---------+\n");
	       // Simulation values
	       //System.out.println(valuesCase1[0]);
	       for(i = 0; i < valuesCase1.length; i++)
	           System.out.printf("%2d | %1.3f | %1.3f | %1.3f | %1.3f | %1.3f | %5.1f | %1.3f | %1.3f | %1.3f | %1.3f | %1.3f | %5.1f | \n",i+1, valuesCase1[i], valuesCase2[i],valuesCase3[i], valuesCase4[i],valuesCase5[i], valuesCase6[i],valuesCase7[i], valuesCase8[i], valuesCase9[i], valuesCase10[i], valuesCase11[i], valuesCase12[i]);
	       // Confidence intervals
	       System.out.printf("-------------------------------------------------------------------------------------------------------\n");
	       System.out.printf("    PE | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.1f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.1f\n", cfIntCase1.getPointEstimate(), cfIntCase2.getPointEstimate(),cfIntCase3.getPointEstimate(),cfIntCase4.getPointEstimate(),cfIntCase5.getPointEstimate(),cfIntCase6.getPointEstimate(),cfIntCase7.getPointEstimate(),cfIntCase8.getPointEstimate(),cfIntCase9.getPointEstimate(),cfIntCase10.getPointEstimate(),cfIntCase11.getPointEstimate(),cfIntCase12.getPointEstimate());
	       System.out.printf("  S(n) | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f\n", cfIntCase1.getStdDev(), cfIntCase2.getStdDev(), cfIntCase3.getStdDev(), cfIntCase4.getStdDev(), cfIntCase5.getStdDev(), cfIntCase6.getStdDev(), cfIntCase7.getStdDev(), cfIntCase8.getStdDev(), cfIntCase9.getStdDev(), cfIntCase10.getStdDev(), cfIntCase11.getStdDev(), cfIntCase12.getStdDev());
	       System.out.printf("  zeta | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f\n", cfIntCase1.getZeta(), cfIntCase2.getZeta(), cfIntCase3.getZeta(), cfIntCase4.getZeta(), cfIntCase5.getZeta(), cfIntCase6.getZeta(), cfIntCase7.getZeta(), cfIntCase8.getZeta(), cfIntCase9.getZeta(), cfIntCase10.getZeta(), cfIntCase11.getZeta(), cfIntCase12.getZeta());
	       System.out.printf("CI Min | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.1f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.1f\n", cfIntCase1.getCfMin(), cfIntCase2.getCfMin(), cfIntCase3.getCfMin(), cfIntCase4.getCfMin(), cfIntCase5.getCfMin(), cfIntCase6.getCfMin(), cfIntCase7.getCfMin(), cfIntCase8.getCfMin(), cfIntCase9.getCfMin(), cfIntCase10.getCfMin(), cfIntCase11.getCfMin(), cfIntCase12.getCfMin());
	       System.out.printf("CI Max | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.1f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.1f\n", cfIntCase1.getCfMax(), cfIntCase2.getCfMax(), cfIntCase3.getCfMax(), cfIntCase4.getCfMax(), cfIntCase5.getCfMax(), cfIntCase6.getCfMax(), cfIntCase7.getCfMax(), cfIntCase8.getCfMax(), cfIntCase9.getCfMax(), cfIntCase10.getCfMax(), cfIntCase11.getCfMax(), cfIntCase12.getCfMax());
	       System.out.printf("zeta/PE | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f\n",  cfIntCase1.getZeta()/cfIntCase1.getPointEstimate(),
	    		                                                                                                                           cfIntCase2.getZeta()/cfIntCase2.getPointEstimate(),
	    		                                                                                                                           cfIntCase3.getZeta()/cfIntCase3.getPointEstimate(),
	    		                                                                                                                           cfIntCase4.getZeta()/cfIntCase4.getPointEstimate(),
	    		                                                                                                                           cfIntCase5.getZeta()/cfIntCase5.getPointEstimate(),
	    		                                                                                                                           cfIntCase6.getZeta()/cfIntCase6.getPointEstimate(),
	    		                                                                                                                           cfIntCase7.getZeta()/cfIntCase7.getPointEstimate(),
	    		                                                                                                                           cfIntCase8.getZeta()/cfIntCase8.getPointEstimate(),
	    		                                                                                                                           cfIntCase9.getZeta()/cfIntCase9.getPointEstimate(),
	    		                                                                                                                           cfIntCase10.getZeta()/cfIntCase10.getPointEstimate(),
	    		                                                                                                                           cfIntCase11.getZeta()/cfIntCase11.getPointEstimate(),
	    		                                                                                                                           cfIntCase12.getZeta()/cfIntCase12.getPointEstimate()); 
	       System.out.printf("----------------------------------------------------------------------------------------------------------------------------------\n");}
	   
  
}