/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csmacd;

/**
 *
 * @author Moon
 */
public class VirtualHost extends Thread {

    /**
     * < Private integer for count for number of collisions
     */
    private int count = 0;

    /**
     * < status indicator of bus whether that is occupied or not 0= bus not
     * idle, 1= sent, 2=collission
     */
    public static int bus = 0;

    /**
     * < Private String variable for Hostname
     */
    private String name;

    /**
     * < Private integer for total number of transmissions
     */
    private int num;

    /**
     * < Private integer for sending delay
     */
    private int propDelay;

    public VirtualHost(String name, int num, int propDelay) {
        this.name = name;
        this.num = num;
        this.propDelay = propDelay;
    }

    public void run() {
		// TODO Auto-generated method stub
		
		int time;
		//Transmission cycle starts here..
		for (int n = 1; n <= num; n++) {
			//Each host before sending sleep for random time
			try {
				Thread.sleep((int) (Math.random() * propDelay) / 2); 
			} catch (InterruptedException e) { 
				System.err.println("Inturrupted: Interrupt exception ");
			}
			//Starts sending output host ID starting time of transmission
			count = 0;
			//try to send: 10 times..
			while (count < 10) {
				//According to the situation of the bus, do transmission-retransmission
				//bus = 0 means:: bus is not idle
				if(bus==0) {
					try {
						Thread.sleep(propDelay);
					} catch (InterruptedException e) {
						System.err.println("Inturrupted: Interrupt exception");
					}
					bus++;       
				}
				else {
					System.out.println("Shred bus is non-idle");
					continue;
				}    
				
				try {
					Thread.sleep(propDelay);
				} catch (InterruptedException e) {
					System.err.println("Inturrupted: Interrupt exception");
				}
				
				//bus = 1 means:: bus empty, packet successfully sent to destination
				if(bus==1) {
					System.out.println("Host["+name+"]Packet " +n+ " Sent successfully!");
					bus=0;
					break;
				}
				
				//bus = 2 means:: collision between packets while trying to sent them
				if(bus==2) {
					count++;
					System.out.println("Host computer["+name+"]Packet " +count+ " Collision!");
					bus=0;
					//increase idle time after each failed transmission
					try {
						BackoffTimer timer = new BackoffTimer();
						Thread.sleep(2*propDelay*timer.BackoffTimer(count));
					} catch (InterruptedException e) {
						System.err.println("Inturrupted: Interrupt exception");
					}
					continue;
				}
			}
			//Retransmission failed for last 10 times? Transmission failure.
			if (count >= 10){
				System.out.println("Host computer[" + name + "]Packet " +n+ " transmission failure.");
			}
		}
	}
}
