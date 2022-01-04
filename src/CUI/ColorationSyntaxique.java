package AlgoPars.CUI;

import iut.algo.Console;
import iut.algo.CouleurConsole;

import org.jdom2.Element;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

import java.util.ArrayList;
import java.util.HashMap;

import java.io.File;


public class ColorationSyntaxique
{
	public static HashMap<String, ArrayList<String>> couleurs;


	public static void chargerCouleurs()
	{
		couleurs = new HashMap<String, ArrayList<String>>();
		Element racine = null;

		try
		{
			Document doc = new SAXBuilder().build( new File( "../src/CUI/coloration.xml" ) );
			racine = doc.getRootElement();
		}
		catch( Exception e ) { e.printStackTrace(); }


		ArrayList<String> alTmp = null;
		for ( Element e: racine.getChildren() )
		{
			for ( Element child: e.getChildren() )
			{
				alTmp = new ArrayList<String>();
				alTmp.add( e.getAttribute( "idCoul" ).getValue() );
				alTmp.add( e.getAttribute( "poids" ).getValue() );

				couleurs.put( child.getText(), alTmp );
			}
		}
	}


	public static String getCouleurMot( String mot )
	{
		String couleur;
		String id = couleurs.get( mot ).get( 0 );
		switch ( id ) 
		{
			case "0": 
				couleur = CouleurConsole.BLANC.getFont();
				break;
			case "1": 
				couleur = CouleurConsole.BLEU.getFont();
				break;
			case "2": 
				couleur = CouleurConsole.CYAN.getFont();
				break;
			case "3": 
				couleur = CouleurConsole.JAUNE.getFont();
				break;
			case "4": 
				couleur = CouleurConsole.VERT.getFont();
				break;
			case "5":
				couleur = CouleurConsole.ROUGE.getFont();
				break;
			case "6":
				couleur = CouleurConsole.MAUVE.getFont();
				break;
			default: 
				couleur = CouleurConsole.BLANC.getFont();
		}

		if ( couleurs.get( mot ).get( 1 ).equals( "true" ) ) 
			couleur += "\033[1m";

		return couleur + mot + "\033[0m";
	}


	public static void main( String[] a)
	{
		ColorationSyntaxique.chargerCouleurs();
		for( String key : couleurs.keySet() )
			System.out.println( key + " " + getCouleurMot( key ) );
	}
}
