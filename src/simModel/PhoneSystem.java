package simModel;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PhoneSystem {
    protected PriorityQueue<Call> list;
    protected int trunklinesNum;
    protected int reservedLines;
    protected int numOfCalls;
    
    public PhoneSystem(int trunkLinesNum, int reservedLines) {
        Comparator<Call> comp = new CallComparator();
        this.trunklinesNum = trunkLinesNum;
        this.reservedLines = reservedLines;
        list = new PriorityQueue<Call>(trunkLinesNum, comp);
    }

    public class CallComparator implements Comparator<Call> {
        public int compare(Call a, Call b) {
            if(a.uType > b.uType) {
                return -1;
            } else if(a.uType < b.uType) {
                return 1;
            } else if(a.uType == b.uType && a.uStartWaitTime < b.uStartWaitTime) {
            	return -1;
            } else if(a.uType == b.uType && a.uStartWaitTime > b.uStartWaitTime) {
            	return 1;
            }
            return 0; // Never going to happen
        }
    }
}
