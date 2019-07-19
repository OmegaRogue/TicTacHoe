package b;

import java.util.ArrayList;

/**
 * b.TicTacToe-Agent für Übungen mit Rekursion
 * Übungsaufgabe in Zeile 73.
 *  
 * @author LorenzUw
 * @version 1.1. (10.2.2019)
 */
public class TicTacToe_Agent 
{
	int tiefe = 0;
	char[] feld;
	
	/**
	 * Liefert eine Liste mit den Aktionsmöglichkeiten für einen bestimmten Feldzustand zurück.
	 *  
	 * @param spielfeld
	 * @return
	 */
	public ArrayList <Integer> holeAlleMoeglichkeiten(char[] spielfeld)
	{
		ArrayList <Integer> moeglichkeiten = new ArrayList<Integer> ();
		for (int i=0;i<9;i++)
		{
			if (spielfeld[i]=='-') 
			{
				moeglichkeiten.add(i);
			}
		}
		return moeglichkeiten;
	}
	
	/**
	 * Gibt zurück, ob der Spieler c bei dem gegebenen Spielfeld eine Belohnung erhalten würde. 
	 * @param feld
	 * @param c
	 * @return
	 */
	public double holeBelohnung(char[] feld, char c)
	{
		if (TicTacToe.pruefeMatrixGewonnen(feld)==c)
			return 100;
		else {
			return 0;
		}
	}
	
	/**
	 * Diese Funktion bewertet den gegebenen Feldzustand für einen Spieler.
	 * @param spieler
	 * @return
	 */
	double feldbewertung(char spieler) 
	{
		// A ist eine Liste mit den möglichen Aktionen.
		ArrayList <Integer> A = holeAlleMoeglichkeiten(feld);
		
		// Habe ich einen Gewinnzustand mit Belohnung erreicht?
		double reward = holeBelohnung(feld, spieler);
	    if ((reward!=0)||(A.size()==0)) // Wenn Gewinnzustand erreicht ist oder Feld voll, dann Rückgabe der Belohnung.
	    {
	         return reward;
	    }
	   
	    // Spielerwechsel, um Gegnerzüge zu bewerten
	    if (spieler=='o') {
	    	spieler='x';
	    }else {
	    	spieler='o';
		}
	    
	    /* Aufgabe: Implementieren Sie hier einen Programmabschnitt, der
	       unter Anwendung der Rekursion alle Möglichkeiten des Gegners 
	       bewertet und davon ausgeht, dass dieser den Worst-Case setzt,
	       also minimalen Gewinn, bzw. einen "maximalen Negativ-Wert", für 
	       den Spieler produziert.  
	     */
	    double wmin = 999999;
	    double w=0;
		for (int i = 0; i < A.size(); i++) {
			feld[A.get(i)] = spieler;
			w= - feldbewertung(spieler);
			feld[A.get(i)] = '-';
			if(w<wmin)
				wmin=w;
		}
	    
	    
	    
	    
	    return wmin;
	 }

	/**
	 * Liefert die Aktionsmöglichkeit mit der besten Bewertung für den Spieler c zurück.
	 *
	 * @param spielfeld Gegebener Feldzustand.
	 * @param c Spieler
	 * @return
	 */
	public int gibBesteAktion(char[] spielfeld, char c)
	{
		this.feld =spielfeld;
		ArrayList <Integer> A = holeAlleMoeglichkeiten(feld);
		ArrayList <Integer> lstI = new ArrayList <Integer> ();
		double wmax=0;
		int amax=0;
		for (int i=0;i<A.size();i++)
		{
			feld[A.get(i)]= c;  // Zug testweise durchführen.
			double w = feldbewertung(c);
			feld[A.get(i)]= '-';  // Zug wieder rückgängig machen.
			if (w>=wmax) {
				wmax=w;
				amax=A.get(i);
			}
			lstI.add((int)Math.round(w)); // Liste mit den Zugbewertungen für die Ausgabe.
		}
		int i=0;
		for (int wi: lstI)
		{
			System.out.println("Bewertung:"+wi+" für Aktion:"+A.get(i));
			i++;
		}
		return amax;
	}

}
