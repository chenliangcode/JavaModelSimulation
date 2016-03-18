package simModel;

class UDPs 
{
    SMTravel model;  // for accessing the clock

    // Constructor
    protected UDPs(SMTravel model) { this.model = model; }

    // User Defined Procedures

    // Check the time waited of each type of users  if they waited too

    protected boolean userWaitedTooLong(int typeOfUser, double timeWaited){  
        boolean result = true;
        if(typeOfUser == Constants.REGULAR) {
            if(timeWaited > 15){
                result= true;
            }
            else {
                result = false;
            }
        }
        if(typeOfUser == Constants.SILVER) {
            if(timeWaited > 3){
                result= true;
            }
            else {
                result = false;
            }
        }
        if(typeOfUser == Constants.GOLD) {
            if(timeWaited > 1.5){
                result = true;
            }
            else {
                result = false;;
            }
        }
        return result;
    }

    //Get total Cost
    protected int computeTotalCost(){
        int tn[] = {0,0,0};
        for(int id = Constants.REGULAR; id <= Constants.GOLD; id++){
            for(int i = 0; i < 5; i++){
                tn[id] += model.rgOperators[id].schedule[i];
            }
        }
        return ((tn[Constants.GOLD] * 8 * 23) + (tn[Constants.SILVER] * 8 * 20) +
                (tn[Constants.REGULAR] * 8 * 16) + (model.qPhoneSystem.trunklinesNum - 50) * 170);
    }

    // One of the three calls is ready for processing  

    protected boolean canServiceCall(Call icCall)
    {
    	boolean first = (model.qPhoneSystem.list.peek() == icCall);   
        boolean regOpAvail = model.rgOperators[Constants.REGULAR].numOfBusy < model.rgOperators[Constants.REGULAR].uNumOperators;
        boolean silvOpAvail = model.rgOperators[Constants.SILVER].numOfBusy < model.rgOperators[Constants.SILVER].uNumOperators;
        boolean goldOpAvail = model.rgOperators[Constants.GOLD].numOfBusy < model.rgOperators[Constants.GOLD].uNumOperators;

        //System.err.println(regOpAvail + " : " +silvOpAvail + " : " +goldOpAvail );
       
        if(icCall.uType == Constants.REGULAR) {
            return first && regOpAvail;
        }
        else if(icCall.uType == Constants.SILVER) {
            return first && (regOpAvail || silvOpAvail);
        }
        else if(icCall.uType == Constants.GOLD) {
            return first && (regOpAvail || silvOpAvail || goldOpAvail);

        } else {
            return false;
        }
    }

    protected int assignOperator(Call icCall) {
        int res = -1;   // If there are no operator that can serve return -1
        if(icCall.uType == Constants.GOLD &&
                model.rgOperators[Constants.GOLD].numOfBusy < model.rgOperators[Constants.GOLD].uNumOperators) {
            //model.rgOperators[Constants.GOLD].numOfBusy++;
            res = Constants.GOLD;
        }
        else if(icCall.uType != Constants.REGULAR &&
                model.rgOperators[Constants.SILVER].numOfBusy < model.rgOperators[Constants.SILVER].uNumOperators) {
            //model.rgOperators[Constants.SILVER].numOfBusy++;
            res = Constants.SILVER;
        }
        else if(model.rgOperators[Constants.REGULAR].numOfBusy < model.rgOperators[Constants.REGULAR].uNumOperators){
            //model.rgOperators[Constants.REGULAR].numOfBusy++;
            res = Constants.REGULAR;
        }
        return res;
    }

    //Increments the number of customers who got a busy signal in each hour
    protected void incBusySignal(int customerType)
    {
        model.output.busyPerHourMatrix[customerType][(int) (model.getClock()/60)] ++;

    }

    //Increments longwait matrix --Ma 
    protected void incLongWait(Call icCall){
        int t = (int) icCall.uStartWaitTime / 60; 
        model.output.longWaitPerHourMatrix[icCall.uType][t] ++;

    }


    //Increments the number of customer that arrived in each hour
    protected void incNumberArrival(int customerType)
    {
    	//System.out.println("Arrival Time: " + (model.getClock()/60));
        model.output.arrivalPerHourMatrix[customerType][(int) (model.getClock()/60)] ++;

    }


   
    
//    protected void insertArray(Call icCall){
    	//if(icCall.uType == Constants.REGULAR){
//    		model.qPhoneSystem.list.add(icCall);
    		//System.out.println("Insertion attemped at "+ model.getClock());
    		//System.out.println(model.qPhoneSystem.list);
//    	} else if(icCall.uType == Constants.SILVER){
//    		model.qPhoneSystem.list.add(icCall);
//    	} else {
//    		model.qPhoneSystem.list.add(icCall);
//    	}
//    }
    
//    protected void removeArray(Call icCall){
//    	if(icCall.uType == Constants.REGULAR){
//    		model.qPhoneSystem.list.remove(icCall);
//    	} else if(icCall.uType == Constants.SILVER){
//    		model.qPhoneSystem.list.remove(icCall);
//    	} else {
//    		model.qPhoneSystem.list.remove(icCall);
//    	}
//    }

}