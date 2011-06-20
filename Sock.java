import java.io.*;
import java.net.*;

public class Sock
{
	Socket socket;
	BufferedReader plec;
	PrintWriter pred;
	static final int port = 6667;

	public Sock(String server)
		throws Exception
	{
		this.socket = new Socket(server,this.port);
		System.out.println("Creation de la socket en cours ...");
		System.out.println(socket);
		this.plec = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.pred = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
	}

	public void initConnection(String nick)
	{
		this.pred.println("NICK "+nick);
		this.pred.println("USER superbotte 8 x : Nacos Slave");
	}

	public String receive()
		throws Exception
	{
		return this.plec.readLine(); 
	}

	public void send(String message)
	{
		this.pred.println(message);
	}

	public static void main(String[] args)
		throws Exception
	{
		Sock c = new Sock("irc.rezosup.org");
		c.initConnection("SuperBotte");
		String mess;
		while(true)
		{
			mess = c.receive();
			System.out.println(mess);
			if (mess.contains("PING"))
			{
				System.out.println("je vais répondre");
				c.send("PONG");
			}
		}
	}
}
