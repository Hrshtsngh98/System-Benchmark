import java.io.*;
import java.net.*;
import java.util.Scanner;
public class sserver extends Thread
{
	static int type,not;
	static Scanner s;
	String x;
	ServerSocket ss;
	DatagramSocket ds;
	InputStream is;
	Socket so;
	BufferedReader br;
	static byte rcv[];
	public static void main(String ar[])throws IOException, InterruptedException
	{
		rcv=new byte[1024];
		s=new Scanner(System.in);
		int i=0;
		while(i<2)
		{	i++;
			Thread t=new Thread(new sserver());
			t.start();
			t.join();
		}
	}
	public void run()
	{
		try {
				System.out.println("TCP and UDP server running");
				ss=new ServerSocket(1111);
				so=ss.accept();
				is=so.getInputStream();
				br=new BufferedReader(new InputStreamReader(is));
				while((x=br.readLine())!=null)
					x=x+"ABC";
				ds=new DatagramSocket(2222);
				DatagramPacket pack = new DatagramPacket(rcv, rcv.length);
				ds.receive(pack);
				x = new String( pack.getData());
				x=x+"ABC";
		}catch (IOException e) {}
	}
}