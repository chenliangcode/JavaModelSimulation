import simModel.Constants;
import simModel.SMTravel;
import simModel.Seeds;
import outputAnalysis.ConfidenceInterval;
import cern.jet.random.engine.RandomSeedGenerator;


public class Experiment3 
{
	   // Experimentation constants
		   static final int NUMRUNS = 1000;
		   static final double CONF_LEVEL = 0.9;
		   static final int [] NUM_RUNS_ARRAY = {20, 30, 40, 60, 80, 100, 1000};
	  

public static void main(String[] args) 
{
	   // Local variables
       int i; 
       double startTime=0.0, endTime=720.0;
       Seeds[] sds = new Seeds[NUMRUNS];
       SMTravel model;  // Simulation object
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
       System.out.println("Loading... This could take some seconds...");
       int[] regSchedule = {7,7,12,5,5};
	   int[] silSchedule = {5,8,6,4,4};
	   int[] golSchedule = {2,4,2,2,2};
	   int trunkLinesNum = 50;
	   int reservedLines = 3; 
       for(i=0 ; i < NUMRUNS ; i++)
       {
    	   model = new SMTravel(startTime,endTime,regSchedule,silSchedule,golSchedule, trunkLinesNum, reservedLines, sds[i], false);
    	   model.runSimulation();
    	   model.calculateOutput(); 
	          //System.out.println("ahh " + (1-model.getPercentExceedWait()[Constants.GOLD]));
	          valuesCase1[i] = 1-model.getPercentExceedWait()[Constants.GOLD];
	          valuesCase2[i] = 1-model.getPercentExceedWait()[Constants.SILVER];
	          valuesCase3[i] = 1-model.getPercentExceedWait()[Constants.REGULAR];
	          valuesCase4[i] = model.getPercentBusySignal()[Constants.CARDHOLDER];
			  valuesCase5[i] = model.getPercentBusySignal()[Constants.REGULAR];
			  valuesCase6[i] = model.calcCost();
       }
    // Case 2
       int[] regSchedule1 = {7,11,11,7,7};
	   int[] silSchedule1 = {4,3,0,7,3};
	   int[] golSchedule1 = {3,1,0,1,3};
	   int trunkLinesNum1 = 50;
	   int reservedLines1 = 3; 
       for(i=0 ; i < NUMRUNS ; i++)
       {
    	  model = new SMTravel(startTime,endTime,regSchedule1,silSchedule1,golSchedule1,trunkLinesNum1, reservedLines1, sds[i], false);
          model.runSimulation();
          model.calculateOutput(); 
          valuesCase7[i] = 1-model.getPercentExceedWait()[Constants.GOLD];
          valuesCase8[i] = 1-model.getPercentExceedWait()[Constants.SILVER];
          valuesCase9[i] = 1-model.getPercentExceedWait()[Constants.REGULAR];
          valuesCase10[i] = model.getPercentBusySignal()[Constants.CARDHOLDER];
		  valuesCase11[i] = model.getPercentBusySignal()[Constants.REGULAR];
		  valuesCase12[i]= model.calcCost();
       }	       
       

       System.out.println("Case 1 - The maximum operators needed to satisfy all satisfaction level");
       displayTable(valuesCase1,valuesCase2,valuesCase3,valuesCase4,valuesCase5,valuesCase6,1);

       System.out.println("Case 2 - The optimal operators needed to satisfy all satisfaction level");
       displayTable(valuesCase7,valuesCase8,valuesCase9,valuesCase10,valuesCase11,valuesCase12,2);	       
}

/*------------ Display the confidence intervals for various number of simulations --------------*/
private static void displayTable(double [] value1, double [] value2, double [] value3, double [] value4, double [] value5, double [] value6, int caseNum)
{
	
	// Get the conficence intervals

//    System.out.printf("-------------------------------------------------------------------------------------------------------\n");
//    System.out.printf("    PE | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.1f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.1f\n", cfIntCase1.getPointEstimate(), cfIntCase2.getPointEstimate(),cfIntCase3.getPointEstimate(),cfIntCase4.getPointEstimate(),cfIntCase5.getPointEstimate(),cfIntCase6.getPointEstimate(),cfIntCase7.getPointEstimate(),cfIntCase8.getPointEstimate(),cfIntCase9.getPointEstimate(),cfIntCase10.getPointEstimate(),cfIntCase11.getPointEstimate(),cfIntCase12.getPointEstimate());
//    System.out.printf("  S(n) | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f\n", cfIntCase1.getStdDev(), cfIntCase2.getStdDev(), cfIntCase3.getStdDev(), cfIntCase4.getStdDev(), cfIntCase5.getStdDev(), cfIntCase6.getStdDev(), cfIntCase7.getStdDev(), cfIntCase8.getStdDev(), cfIntCase9.getStdDev(), cfIntCase10.getStdDev(), cfIntCase11.getStdDev(), cfIntCase12.getStdDev());
//    System.out.printf("  zeta | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f\n", cfIntCase1.getZeta(), cfIntCase2.getZeta(), cfIntCase3.getZeta(), cfIntCase4.getZeta(), cfIntCase5.getZeta(), cfIntCase6.getZeta(), cfIntCase7.getZeta(), cfIntCase8.getZeta(), cfIntCase9.getZeta(), cfIntCase10.getZeta(), cfIntCase11.getZeta(), cfIntCase12.getZeta());
//    System.out.printf("CI Min | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.1f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.1f\n", cfIntCase1.getCfMin(), cfIntCase2.getCfMin(), cfIntCase3.getCfMin(), cfIntCase4.getCfMin(), cfIntCase5.getCfMin(), cfIntCase6.getCfMin(), cfIntCase7.getCfMin(), cfIntCase8.getCfMin(), cfIntCase9.getCfMin(), cfIntCase10.getCfMin(), cfIntCase11.getCfMin(), cfIntCase12.getCfMin());
//    System.out.printf("CI Max | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.1f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.1f\n", cfIntCase1.getCfMax(), cfIntCase2.getCfMax(), cfIntCase3.getCfMax(), cfIntCase4.getCfMax(), cfIntCase5.getCfMax(), cfIntCase6.getCfMax(), cfIntCase7.getCfMax(), cfIntCase8.getCfMax(), cfIntCase9.getCfMax(), cfIntCase10.getCfMax(), cfIntCase11.getCfMax(), cfIntCase12.getCfMax());
//    System.out.printf("zeta/PE | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f | %1.5f\n",  cfIntCase1.getZeta()/cfIntCase1.getPointEstimate(),
// 		                                                                                                                           cfIntCase2.getZeta()/cfIntCase2.getPointEstimate(),
// 		                                                                                                                           cfIntCase3.getZeta()/cfIntCase3.getPointEstimate(),
// 		                                                                                                                           cfIntCase4.getZeta()/cfIntCase4.getPointEstimate(),
// 		                                                                                                                           cfIntCase5.getZeta()/cfIntCase5.getPointEstimate(),
// 		                                                                                                                           cfIntCase6.getZeta()/cfIntCase6.getPointEstimate());
// 
//    System.out.printf("----------------------------------------------------------------------------------------------------------------------------------\n");}

    
    System.out.println("PEWG - Percentage of gold customers that meets our satisfaction level for wait time");
    System.out.println("PEWS - Percentage of silver customers that meets our satisfaction level for wait time");
    System.out.println("PEWR - Percentage of regular customers that meets our satisfaction level for wait time");
    System.out.println("PBSC - Percentage of busy signal for Cardholder Customer");
    System.out.println("PBSR - Percentage of busy signal for Regual Customer");
    System.out.println("TC - The total cost");
	
       System.out.printf("---------------------------------------------------------------------\n");
       System.out.printf("                             Case %d\n", caseNum);
       System.out.printf("---------------------------------------------------------------------\n");
       System.out.printf("n           y(n)     s(n)     zeta(n)  CI Min   CI Max   zeta(n)/y(n)\n");
       System.out.printf("---------------------------------------------------------------------\n");
       for(int ix1 = 0; ix1 < NUM_RUNS_ARRAY.length; ix1++)
       {
    	   int numruns = NUM_RUNS_ARRAY[ix1];
    	   double [] pewr = new double[numruns];
    	   double [] pews = new double[numruns];
    	   double [] pewg = new double[numruns];
    	   double [] pbsr = new double[numruns];
    	   double [] pbsc = new double[numruns];
    	   double [] tc = new double[numruns];
    	   
    	   for(int ix2 = 0 ; ix2 < numruns; ix2++){
    		   pewr[ix2] = value1[ix2];
    		   pews[ix2] = value2[ix2];
    		   pewg[ix2] = value3[ix2];
    		   pbsr[ix2] = value4[ix2];
    		   pbsc[ix2] = value5[ix2];
    		   tc[ix2] = value6[ix2];
    	   }
    	   ConfidenceInterval pewr_ci = new ConfidenceInterval(pewr, CONF_LEVEL);
    	   ConfidenceInterval pews_ci = new ConfidenceInterval(pews, CONF_LEVEL);
    	   ConfidenceInterval pewg_ci = new ConfidenceInterval(pewg, CONF_LEVEL);
    	   ConfidenceInterval pbsr_ci = new ConfidenceInterval(pbsr, CONF_LEVEL);
    	   ConfidenceInterval pbsc_ci = new ConfidenceInterval(pbsc, CONF_LEVEL);
    	   ConfidenceInterval tc_ci = new ConfidenceInterval(tc, CONF_LEVEL);
    	   System.out.printf("%4d PEWR %8.4f %8.4f %8.4f %8.4f %8.4f %8.4f\n",
    			               numruns, pewr_ci.getPointEstimate(), pewr_ci.getStdDev(), pewr_ci.getZeta(),
    			               pewr_ci.getCfMin(), pewr_ci.getCfMax(), pewr_ci.getZeta()/pewr_ci.getPointEstimate());
    	   System.out.printf("     PEWS %8.4f %8.4f %8.4f %8.4f %8.4f %8.4f\n",
	               pews_ci.getPointEstimate(), pews_ci.getStdDev(), pews_ci.getZeta(),
	               pews_ci.getCfMin(), pews_ci.getCfMax(), pews_ci.getZeta()/pews_ci.getPointEstimate());
    	   System.out.printf("     PEWG %8.4f %8.4f %8.4f %8.4f %8.4f %8.4f\n",
	               pewg_ci.getPointEstimate(), pewg_ci.getStdDev(), pewg_ci.getZeta(),
	               pewg_ci.getCfMin(), pewg_ci.getCfMax(), pewg_ci.getZeta()/pewg_ci.getPointEstimate());
    	   System.out.printf("     PBSR %8.4f %8.4f %8.4f %8.4f %8.4f %8.4f\n",
    			   pbsr_ci.getPointEstimate(), pbsr_ci.getStdDev(), pbsr_ci.getZeta(),
    			   pbsr_ci.getCfMin(), pbsr_ci.getCfMax(), pbsr_ci.getZeta()/pbsr_ci.getPointEstimate());
    	   System.out.printf("     PBSC %8.4f %8.4f %8.4f %8.4f %8.4f %8.4f\n",
    			   pbsc_ci.getPointEstimate(), pbsc_ci.getStdDev(), pbsc_ci.getZeta(),
    			   pbsc_ci.getCfMin(), pbsc_ci.getCfMax(), pbsc_ci.getZeta()/pbsc_ci.getPointEstimate());
    	   System.out.printf("     TC   %8.1f %8.4f %8.4f %8.1f %8.1f %8.4f\n",
    			   tc_ci.getPointEstimate(), tc_ci.getStdDev(), tc_ci.getZeta(),
    			   tc_ci.getCfMin(), tc_ci.getCfMax(), tc_ci.getZeta()/tc_ci.getPointEstimate());
       }
       System.out.printf("---------------------------------------------------------------------\n");
}

}
