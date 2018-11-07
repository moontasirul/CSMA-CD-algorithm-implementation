/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csmacd;

import java.util.Scanner;

/**
 *
 * @author Moon
 */
public class CsmaCDImplementation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        int hostNumber;					/**< Integer containing number of hosts */
		int transNumber;				/**< Integer containing transmission number*/
		int delayTime;					/**< Integer containing propagation delay*/
		
		Scanner scan = new Scanner(System.in);
		//Number of Clients
		System.out.println("Enter the number of hosts:");
		hostNumber=scan.nextInt();
		 
		// Number of transmissions
		System.out.println("Enter the number of transmissions:(per host)");
		transNumber=scan.nextInt();
		       
		//Propagation delay
		System.out.println("Enter the propagation delay time:");
		delayTime=scan.nextInt();
		 
		//Array container of virtual hosts
		VirtualHost[] virtualHosts=new VirtualHost[hostNumber];
		//Assign each virtual host thread to each of the virtual hosts
		for(int j=0;j<hostNumber;j++){
			virtualHosts[j]=new VirtualHost("EndHost"+j,transNumber,delayTime);
		}
	 	//Starting host threads (start sending)
		for (int i = 0; i < hostNumber; i++) {
			virtualHosts[i].start();
		}
    }
    
}
