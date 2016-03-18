package simModel;

import cern.jet.random.Exponential;
import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import dataModelling.TriangularVariate;

class RVPs
{
    SMTravel model; // for accessing the clock
    // Data Models - i.e. random veriate generators for distributions
    // are created using Colt classes, define
    // reference variables here and create the objects in the
    // constructor with seeds


    // Constructor
    protected RVPs(SMTravel model, Seeds sd)
    {
        this.model = model;
        // Set up distribution functions
        for(int i = 0 ; i < 12; i++){
            uRegularCalls[i] = new Exponential(1/RMEAN[i],
                                        new MersenneTwister(sd.rRandom[i]));  

            uCardholderCalls[i] = new Exponential(1/CMEAN[i], 
                                        new MersenneTwister(sd.cRandom[i]));
        }
        typeRandGen1 = new MersenneTwister(sd.typeSeed);
        typeRandGen2 = new MersenneTwister(sd.requestSeed);
        afterCallTime[0] = new Uniform(0.05,0.10,new MersenneTwister(sd.aftm[0]));  
        afterCallTime[1] = new Uniform(0.5,0.8,new MersenneTwister(sd.aftm[1]));
        afterCallTime[2] = new Uniform(0.4,0.6,new MersenneTwister(sd.aftm[2]));
        uSrvTm[0] = new TriangularVariate(1.2,2.05,3.75, new MersenneTwister(sd.uSrvTm[0]));  
        uSrvTm[1] = new TriangularVariate(2.25,2.95,8.6, new MersenneTwister(sd.uSrvTm[1]));
        uSrvTm[2] = new TriangularVariate(1.2,1.9,5.8, new MersenneTwister(sd.uSrvTm[2]));
        transactionTime = new Uniform(0.12,0.27,new MersenneTwister(sd.transactionTime));
        regTolTime= new Uniform(12,30,sd.rTolTime);
        cardholderTolTime= new Uniform(8,17,sd.cTolTime);


    }

    /* Random Variate Procedure for Regular Arrivals */
    private Exponential[] uRegularCalls = new Exponential[12];  // Exponential distribution for interarrival times
    private final double[] RMEAN = {0.69,0.36,0.25,0.18,0.21,0.14,0.22,0.17,0.34,0.22,0.52,1.07};
    protected double duRegularCall()  // for getting next value of duInput
    {
        double nxtInterArr;
        int time = (int) (model.getClock()/60);
        nxtInterArr = uRegularCalls[time].nextDouble();  
        // Note that interarrival time is added to current
        // clock value to get the next arrival time.
        return(nxtInterArr+model.getClock());
    }

    /* Random Variate Procedure for Cardholder Arrivals */
    private Exponential[] uCardholderCalls = new Exponential[12];  // Exponential distribution for interarrival times
    private final double[] CMEAN = {0.67, 0.25, 0.27, 0.33, 0.20, 0.12, 0.15, 0.17, 0.25, 0.22, 0.41, 0.87};
    protected double duCardholderCall()  // for getting next value of duInput
    {
        double nxtInterArr;
        int time = (int) (model.getClock()/60);
        nxtInterArr = uCardholderCalls[time].nextDouble();  
        // Note that interarrival time is added to current
        // clock value to get the next arrival time.
        return(nxtInterArr+model.getClock());
    }

     // Random Variate Procedure:  uCallType - type of cardholder customer
    private final double PROPS =  0.68;
    @SuppressWarnings("unused")
    private final double PROPG = 0.32;
    MersenneTwister typeRandGen1;
    // Method
    protected int uCallType()
    {
        double randNum = typeRandGen1.nextDouble();
        int type;
        if(randNum < PROPS) type = Constants.SILVER;
        else type = Constants.GOLD;
        return(type);
    }
 // uRequestType()
 private final double CHANGES =  0.08;
   private final double INFORMATION = 0.16;
   @SuppressWarnings("unused")
private final double RESERVATION = 0.76;
   MersenneTwister typeRandGen2;

   protected int uRequestType()
    {
        double randNum = typeRandGen2.nextDouble();
        int type;
        if(randNum < CHANGES) type = Constants.CHANGES;
           {if(randNum > CHANGES && randNum < INFORMATION + CHANGES)type = Constants.INFORMATION;
           else type = Constants.RESERVATION;}

        return(type);
    }
// uSrvTm
    protected TriangularVariate[] uSrvTm = new TriangularVariate[3];
    protected double uSrvTm(int requestType, int operatorType)
    {
        double result = 0.0;
        if(operatorType ==Constants.REGULAR){
            if(requestType == Constants.INFORMATION){
                result = uSrvTm[0].next();
            }
            else if(requestType == Constants.RESERVATION){
                result = uSrvTm[1].next();
            }
            else if (requestType == Constants.RESERVATION){
                result = uSrvTm[2].next();
            }
        }
        else if(operatorType ==Constants.SILVER){
            if(requestType == Constants.INFORMATION){
                    result = uSrvTm[0].next() * 0.95;
                }
                else if(requestType == Constants.RESERVATION){
                    result = uSrvTm[1].next() * 0.95;
                }
                else if (requestType == Constants.RESERVATION){
                    result = uSrvTm[2].next() * 0.95;
                }
        }

        else if(operatorType ==Constants.GOLD){
            if(requestType == Constants.INFORMATION){
                    result = uSrvTm[0].next()* 0.88;
                }
                else if(requestType == Constants.RESERVATION){
                    result = uSrvTm[1].next()* 0.88;
                }
                else if (requestType == Constants.RESERVATION){
                    result = uSrvTm[2].next()* 0.88;
                }
        }
        return result;
    }

    private Uniform[] afterCallTime = new Uniform[3];
    protected double uAfterCallTime(int requestType)
    {
        if (requestType == Constants.INFORMATION){
            return afterCallTime[0].nextDouble();
        } else if (requestType == Constants.RESERVATION){
            return afterCallTime[1].nextDouble();
        } else {
            return afterCallTime[2].nextDouble();
        }
    }

    private Uniform transactionTime;
    protected int uTransactionTime(){
        int type = transactionTime.nextInt();
        return (type);
    }

    Uniform regTolTime;
    Uniform cardholderTolTime;

     public double uHangupTime(int customerType) {
        if (customerType== (Constants.REGULAR)){
            return regTolTime.nextDouble();
        } else {
            return cardholderTolTime.nextDouble();
        }
     }

}
