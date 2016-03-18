// File: Experiment.java
// Description:

import simModel.*;
import cern.jet.random.engine.*;

// Main Method: Experiments
// 
class Experiment1
{
   public static void main(String[] args)
   {

       int i, NUMRUNS = 1; 
       double startTime=0.0, endTime=720.0;
       Seeds[] sds = new Seeds[NUMRUNS];
       SMTravel smmodel;  // Simulation object

       // Lets get a set of uncorrelated seeds
       RandomSeedGenerator rsg = new RandomSeedGenerator();
       for(i=0 ; i<NUMRUNS ; i++) sds[i] = new Seeds(rsg);
       
       // Loop for NUMRUN simulation runs for each case
       // Case 1
       for(i=0 ; i < NUMRUNS ; i++)
       {
    	   int[] regSchedule = {7,7,12,5,5};
    	   int[] silSchedule = {5,8,6,4,4};
    	   int[] golSchedule = {2,4,2,2,2};
    	   int trunkLinesNum = 50;
    	   int reservedLines = 3; 

           smmodel = new SMTravel(startTime,endTime,regSchedule,silSchedule,golSchedule, trunkLinesNum, reservedLines, sds[i], true);
           smmodel.runSimulation();
           
           
           // See examples for hints on collecting output
           // and developing code for analysis
          
           smmodel.calculateOutput();  
           System.out.println("Terminated "+(i+1)+":");
           System.out.println("Percentage of customers that meets our satisfaction level for wait time:");
           System.out.println("Gold Cardholder:    " + (1-smmodel.getPercentExceedWait()[Constants.GOLD]));
           System.out.println("Silver Cardholder:  " + (1-smmodel.getPercentExceedWait()[Constants.SILVER]));
           System.out.println("Regular Customer:   " + (1-smmodel.getPercentExceedWait()[Constants.REGULAR]));
           System.out.println("Percentage of customers that receive busy signal:");
           System.out.println("Cardholder Customer: " + (smmodel.getPercentBusySignal()[Constants.CARDHOLDER]));
           System.out.println("Regular Customer:   " + (smmodel.getPercentBusySignal()[Constants.REGULAR]));
           System.out.println("Total Cost: $" + smmodel.calcCost());
           System.out.println("-----------------------------------------------------------------------");
           char[] cust = {'R', 'S', 'G'};
           System.out.println("Percent of customers that waited too long per hour");
           for(int cus = 0; cus < 3; ++cus){
        	   System.out.print(cust[cus] + " ");
        	   for(int hour = 0; hour <12; ++hour){
        		   System.out.format("%.6f ", smmodel.getPercentWaitingHourMatrix()[cus][hour]);
        	   }
        	   System.out.println();
           }
    	   System.out.println();
           System.out.println("Percent of customers that received a busy signal");
           for(int cus = 0; cus < 2; ++cus){
        	   System.out.print(' ');
        	   for(int hour = 0; hour <12; ++hour){
        		   System.out.format("%.6f ", smmodel.getPercentBusyHour()[cus][hour]);
        	   }
        	   System.out.println();
           }
       }

   }
   
   
   
}
