import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.Scanner;
public class sclient extends Thread
{
	static int type,not,size,f;
	static Scanner s;
	static long start,end;
	static double total=0,avg=0;
	Socket so;
	DatagramSocket cs;
	DatagramPacket pack;
	OutputStream os;
	PrintStream ps;
	InetAddress ip;
	byte sd[];
	Random r = new Random();
	static String txt="1234567890qwertyuiopasd fghjklzxcvbn m[];',.{}|:<>?!@#$ %^&*()_+QWERTYUIO PASDFGHJ KLZXCVBNM";
	public static void main(String ar[])throws IOException,UnknownHostException, InterruptedException
	{
		s=new Scanner(System.in);
		System.out.println("Enter Any Combination \nEnter type same as Server\n1.TCP\n2.UDP\n\nEnter Size\n1. 1 Byte\n2. 1 Kbytes\n3. 64 Kbytes\n\nNumber of Threads\n1. 1\n2. 2\n\nEnter 5 to exit");
		type=s.nextInt();
		if (type==5)
			System.exit(0);
		else if(type>2)
			type=1;
		size=s.nextInt();
		if(size==1)
			size=1;
		else if(size==2)
			size=1024;
		else
			{size=1024; f=1;}
		not=s.nextInt();
		if(not>2)
			not=2;
		System.out.println("For 1000 packets/thread");
		for(int i=0;i<not;i++)
		{
			Thread t=new Thread(new sclient());
			t.start();
			t.join();
		}
		printavg(not);
	}
	public void run()
	{
		try{
		if(type==1)
		{	
			ip = InetAddress.getByName("");
			so= new Socket(ip,1111);
			os=so.getOutputStream();
			ps=new PrintStream(os);
			if(f==1)
				size=64*1024;
			start=System.currentTimeMillis();
			for(int i=0;i<size*1000;i++)
			{
				ps.println(txt.charAt(r.nextInt(txt.length())));
			}
			end=System.currentTimeMillis();
			avg=avg+(end-start);
			ps.println("exit");
		}
		if(type==2)
		{	
			cs = new DatagramSocket();
			ip = InetAddress.getByName("localhost");  
			sd= new byte[size];
			pack = new DatagramPacket(sd,sd.length,ip,2222);
			start=System.currentTimeMillis();
			for(int j=0;j<1000;j++)
			if(f==1)
				for(int i=0;i<64;i++)
					cs.send(pack);
			else
				cs.send(pack);
			end=System.currentTimeMillis();
			avg=avg+(end-start);
		}
		}
		catch(Exception e){System.out.println(e);}	
	}
	static void printavg(int i)
	{	avg=avg/(i*1000);
		System.out.println(avg+" milliseconds average");
		avg=avg/1000.0;
		System.out.println(size/(avg*1024*1024) +" MB/sec\n");
		avg=0;
	}
	
}