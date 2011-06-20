import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Botte
{
	public Sock c;
	ArrayList<String> admins = new ArrayList<String>();

	public Botte(String server, String nick)
		throws Exception
	{
		this.c = new Sock(server);
		this.c.initConnection(nick);
	}


	public void answerPM(String mess)
	{
		//recupere nom de la personne
		String[] commande = mess.split("!");
		String user = commande[0].substring(1);
		
		String text;

		if(mess.contains("!adminpasswd") && mess.contains("root"))
		{
			this.admins.add(user);
			text = ":Mot de passe reconnu !";
		}
		else if(mess.contains("!adminlist"))
		{
			text = ":";
			for (String pers : this.admins)
			{
				text = text + " " + pers;
			}
		}
		else
		{
			text = ":commande non reconnu";
		}
		
		//envoyer la reponse
		this.envoie("PRIVMSG "+ user + " "+text);	

	}

	public void answerChan(String mess)
	{
		if (mess.contains("choucroute"))
		{
			// recupere le nom de la personne qui a parle
			String[] us = mess.split("!");
			String user = us[0].substring(1);
			
			//recupere le nom du chan en cours
			String chanstr = mess.substring(mess.indexOf("#"));
			char[] str = chanstr.toCharArray();
			int i = 0;
			String channel = "";
			while(str[i]!=' ')
			{
				channel = channel+str[i];
				i++;
			}

			//message a envoyer 
			this.envoie("PRIVMSG "+user + " :tu veux une choucroute ?");
			this.envoie("PRIVMSG "+channel+ " :j'aime la choucroute !");

		}

	}

	public void envoie(String mess)
	{
		this.c.send(mess);
	}

	public String recoie()
		throws Exception
	{
		return this.c.receive();
	}
	
	
	public static void main(String[] args)
		throws Exception
	{
		
		 /*
		Sock c = new Sock("irc.rezosup.org");
		c.initConnection("SuperBotte");
		String mess;
		while (true)
		{
			mess = c.receive();
			System.out.println(mess);
			if (mess.contains("PING"))
			{
				System.out.println("je vais répondre PONG !");
				c.send("PONG");
			}
		}
		*/

		Botte moteur = new Botte("irc.rezosup.org","SuperBotte");
		moteur.envoie("JOIN #tt");
		//moteur.envoie("JOIN #anticore");
		String mess;
		while(true)
		{
			mess = moteur.recoie();
			System.out.println(mess);
			if (mess.contains("PING"))
			{
				System.out.println("je vais repondre PONG !");
				moteur.envoie("PONG");
			}
			else if (mess.contains("PRIVMSG"+" SuperBotte"))
			{
				moteur.answerPM(mess);
			}
			else if (mess.contains("PRIVMSG"+" #"))
			{
				moteur.answerChan(mess);
			}

		}
	}
}
