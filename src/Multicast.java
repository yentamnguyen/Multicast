//Truong Nguyen Yen Tam - AT11E - AT110541

	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.net.*;

	public class Multicast {

	    private static class Recieve extends Thread{
	        private final int port= 2000;
	        private final String group = "225.4.5.6";
	        @Override
	        public void run(){
	            try {
	                MulticastSocket ms = new MulticastSocket(port);
	                ms.joinGroup(InetAddress.getByName(group));
	                while(true){
	                    byte[] buffer = new byte[1024];
	                    DatagramPacket packet =new DatagramPacket(buffer, buffer.length);                
	                    ms.receive(packet);
	                    System.out.println(packet.getAddress().toString() + " > "+ packet.getPort() + " with length: "+ packet.getLength());
	                    System.out.write(packet.getData(), 0,packet.getLength());
	                    System.out.println();                
	                }                
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }
	    
	    private static class send extends Thread{
	        private final int port= 2000;
	        private final String group = "225.4.5.6";
	        byte TTL = 3;
	        BufferedReader in;
	        
	        @Override
	        public void run(){
	            try {
	                MulticastSocket ms = new MulticastSocket();
	                in = new BufferedReader(new InputStreamReader(System.in));
	                while(true){                    
	                    byte buffer[] = new byte[1024];
	                    String mess =in.readLine();
	                    buffer = mess.getBytes();
	                    DatagramPacket packet =new DatagramPacket(buffer, buffer.length,InetAddress.getByName(group), port);
	                    ms.send(packet, TTL);
	                }
	               
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }
	    public static void main(String[] args) {
	        System.out.println("Starting.....");
	        Recieve nhan = new Recieve();
	        nhan.start();
	        send gui = new send();
	        gui.start();
	    }
	    
	}

