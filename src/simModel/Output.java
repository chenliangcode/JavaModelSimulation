package simModel;

public class Output 
{
    // SSOVs
	public double[] percentExceedWait = new double[3];
	public double[] percentBusySignal = new double[2];
	public int[][] longWaitPerHourMatrix = new int[3][12];
	public int[][] busyPerHourMatrix = new int[2][12];
	public int[][] arrivalPerHourMatrix = new int[3][12];
	public double[][] percentWaitingHourMatrix = new double[3][12];
	public double[][] percentBusyHourMatrix = new double[2][12];
	//public int finished = 0; // Debug only, remove
	//public int beingSed = 0; // Debug only, remove
	
	public void updateOutput() {
		int sumLongWait[] = new int[3];
		int sumArrival[] = new int[3];
		int sumBusy[] = new int[2];
		for (int i = 0; i < 12; i++ ){
			try {
				percentWaitingHourMatrix[Constants.REGULAR][i] = (double)longWaitPerHourMatrix[Constants.REGULAR][i] / (double)arrivalPerHourMatrix[Constants.REGULAR][i];
			} catch (Exception e){
				System.err.println("/ by zero R");
				percentWaitingHourMatrix[Constants.REGULAR][i] = 0;
			}
			try {
				percentWaitingHourMatrix[Constants.SILVER][i] = (double)longWaitPerHourMatrix[Constants.SILVER][i] / (double)arrivalPerHourMatrix[Constants.SILVER][i];
			} catch (Exception e){
				System.err.println("/ by zero S");
				percentWaitingHourMatrix[Constants.SILVER][i] = 0;
			}
			try {
				percentWaitingHourMatrix[Constants.GOLD][i] = (double)longWaitPerHourMatrix[Constants.GOLD][i] / (double)arrivalPerHourMatrix[Constants.GOLD][i];
			} catch (Exception e){
				System.err.println("/ by zero G");
				percentWaitingHourMatrix[Constants.GOLD][i] = 0;
			}
			percentBusyHourMatrix[Constants.REGULAR][i] = (double)busyPerHourMatrix[Constants.REGULAR][i] / (double)(arrivalPerHourMatrix[Constants.REGULAR][i] + busyPerHourMatrix[Constants.REGULAR][i]);
			percentBusyHourMatrix[Constants.CARDHOLDER][i] = (double)busyPerHourMatrix[Constants.CARDHOLDER][i] / (double)(arrivalPerHourMatrix[Constants.SILVER][i] + arrivalPerHourMatrix[Constants.SILVER][i] + busyPerHourMatrix[Constants.CARDHOLDER][i]);
		
			sumLongWait[Constants.REGULAR] += longWaitPerHourMatrix[Constants.REGULAR][i];
			sumLongWait[Constants.SILVER] += longWaitPerHourMatrix[Constants.SILVER][i];
			sumLongWait[Constants.GOLD] += longWaitPerHourMatrix[Constants.GOLD][i];

			sumArrival[Constants.REGULAR] += arrivalPerHourMatrix[Constants.REGULAR][i];
			sumArrival[Constants.SILVER] += arrivalPerHourMatrix[Constants.SILVER][i];
			sumArrival[Constants.GOLD] += arrivalPerHourMatrix[Constants.GOLD][i];
			
			sumBusy[Constants.REGULAR] +=  busyPerHourMatrix[Constants.REGULAR][i];
			sumBusy[Constants.CARDHOLDER] +=  busyPerHourMatrix[Constants.CARDHOLDER][i];
		}

		percentExceedWait[Constants.REGULAR] = (double)sumLongWait[Constants.REGULAR] / sumArrival[Constants.REGULAR];
		percentExceedWait[Constants.SILVER] = (double) sumLongWait[Constants.SILVER] / sumArrival[Constants.SILVER];
		percentExceedWait[Constants.GOLD] = (double)sumLongWait[Constants.GOLD] / sumArrival[Constants.GOLD];
		
		percentBusySignal[Constants.REGULAR] = (double)sumBusy[Constants.REGULAR] / (double)(sumArrival[Constants.REGULAR] + sumBusy[Constants.REGULAR]);
		percentBusySignal[Constants.CARDHOLDER] = (double)sumBusy[Constants.CARDHOLDER] / (double)(sumArrival[Constants.SILVER] + sumArrival[Constants.GOLD] + sumBusy[Constants.CARDHOLDER]);
		
		
	}
	
}
