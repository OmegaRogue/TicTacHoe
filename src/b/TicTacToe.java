package b;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

/**
 * b.TicTacToe Umgebung für einen b.TicTacToe-Agenten.
 * @author LorenzUw
 * @version 1.1 (10.2.2019)
 */

@SuppressWarnings("serial")
public class  TicTacToe  extends JFrame implements ActionListener
{
	private int zaehler = 0;

	private ImageIcon x_bild = new ImageIcon("images/x.png");
	private ImageIcon o_bild = new ImageIcon("images/o.png");
	private JButton[] btFeld = new JButton[9];

	char[] spielfeld;

	private final static char winCaseList[][] =
		{
				{2,4,6},
				{0,4,8},
				{0,3,6},
				{1,4,7},
				{2,5,8},
				{0,1,2},
				{3,4,5},
				{6,7,8}};
	private Scanner sc = new Scanner(System.in);
	
	private TicTacToe_Agent ttt_agent = new TicTacToe_Agent();

	public static void main(String[] args)
	{

		if(args[0].equals("gui")) {
			TicTacToe mlw = new TicTacToe();
			mlw.setVisible(true);
		}
		else if(args[0].equals("cmd")) {
			System.out.println("Starting a new game.");
			System.out.println(
					"- - -\n" +
					"- - -\n" +
					"- - -"
			);
		}

	}

	/**
	 * Konstruktor für das b.TicTacToe-Feld
	 */
	public TicTacToe()
	{
		setSize(400, 400);
		setResizable(false);
		setLocation(450, 450);
		setTitle("b.TicTacToe");
		getContentPane().setLayout(new GridLayout(3,3));
		for(int z=0; z<=8; z=z+1)
		{
			btFeld[z] = new JButton ("");	
			btFeld[z].addActionListener(this);			
			getContentPane().add(btFeld[z]);
		}
	}
	
	/**
	 * Verarbeitet Mausereignisse von den Buttons.
	 * @param e Mouseevent
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		System.out.println("Zug " +zaehler);
		JButton knopf = (JButton) e.getSource();

		if(knopf.getIcon()!=null)
		{
			return;
		}

		if (zaehler%2 ==0)
		{
			knopf.setIcon(x_bild);
		}else{
			knopf.setIcon(o_bild);
		}
		zaehler = zaehler+1;

		if(zaehler>=9)
		{
			System.exit(0);
		}
		
		char c = pruefeMatrixGewonnen(produziereSpielfeldMatrix());
		if (c!='-')
		{
			System.out.println("Es hat "+c+" gewonnen!");
			return;
		}
		
		//KI:
		spielfeld =  produziereSpielfeldMatrix() ;
		int a = ttt_agent.gibBesteAktion(spielfeld,'o');		
		ausfuehren(a,'o');
	    c = pruefeMatrixGewonnen(produziereSpielfeldMatrix());
		if (c!='-')
		{
			System.out.println("Es hat "+c+" gewonnen!");
		}
		zaehler = zaehler+1;
		
	}
	
	/** 
	 * Führt einen Spielzug aus.
	 * @param fnr Feldnummer
	 * @param c Spieler 'x' oder 'o'
	 */
	public void ausfuehren(int fnr, char c)
	{
		if (c == 'x' )
		{
			btFeld[fnr].setIcon(x_bild);
		}else{
			btFeld[fnr].setIcon(o_bild);
		}
	}
	
	/**
	 * Testet den Inhalt eines Feldes.
	 * @param nr
	 * @return
	 */
	public char pruefeFeld(int nr)
	{
		if (btFeld[nr].getIcon()==null)
		{
			return '-';	
		}else {
			if (btFeld[nr].getIcon()==x_bild)
			{
				return 'x';
			}else{
				return 'o';
			}
		}
	}	
	
	/**
	 * Prüft, ob das Spielfeld einen Gewinnzustand hat.
	 * @param matrix
	 * @return Zeichen des Spielers der gewonnen hat. Rückgabe sonst ist '-'.
	 */
	public static char pruefeMatrixGewonnen(char[] matrix)
	{
		for (int i=0;i<winCaseList.length;i++)
		{
			char[]f =winCaseList[i];
			if (matrix[f[1]]==(matrix[f[0]]) && (matrix[f[2]]==matrix[f[0]])) return matrix[f[0]];
		}
		return '-';
	}

	/** 
	 * Setzt das Buttonfeld in eine char[] Matrix um.
	 * @return
	 */
	public char[] produziereSpielfeldMatrix()
	{
		char[] sfmatrix = new char[9];
		for (int i=0; i<btFeld.length;i++)
		{
			sfmatrix[i] = pruefeFeld(i);
		}
		return sfmatrix;
	}
	public void printBoard() {

	}
	
}