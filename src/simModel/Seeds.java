package simModel;

import cern.jet.random.engine.RandomSeedGenerator;

public class Seeds 
{
    int rRandom[] = new int[12];   
    int cRandom[] = new int[12];   
    int typeSeed;   
    int transactionTime;   
    int aftm[] = new int[3]; 
    int requestSeed;
    int uSrvTm[] = new int[3];
    int rTolTime;
    int cTolTime;
    
    public Seeds(RandomSeedGenerator rsg)
    {
    	for(int i = 0; i < 12; i ++){
    		rRandom[i] = rsg.nextSeed();
    		cRandom[i] = rsg.nextSeed();
    	}
        typeSeed = rsg.nextSeed();
        transactionTime = rsg.nextSeed();
        aftm[0] = rsg.nextSeed();
        aftm[1] = rsg.nextSeed();
        aftm[2] = rsg.nextSeed();
        requestSeed = rsg.nextSeed();
        uSrvTm[0] = rsg.nextSeed();
        uSrvTm[1] = rsg.nextSeed();
        uSrvTm[2] = rsg.nextSeed();
        rTolTime = rsg.nextSeed();
        cTolTime = rsg.nextSeed();
    }
}