package AlgoPars.Metier;

import iut.algo.Console;
import iut.algo.CouleurConsole;

import org.jdom2.Element;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.io.File;


public class ColorationSyntaxique
{
	private static HashMap<String, ArrayList<String>> couleurs;
	private static HashMap<String, Pattern> regPatterns;


	/**
	 * Méthode qui charge la couleur des textes via un fichier xml
	 */
	public static void chargerCouleurs()
	{
		couleurs    = new HashMap<String, ArrayList<String>>();
		regPatterns = new HashMap<String, Pattern>();

		Element racine = null;

		try {
			Document doc = new SAXBuilder().build( new File( "../utilisateur/coloration.xml" ) );
			racine = doc.getRootElement();
		}
		catch( Exception e ) { e.printStackTrace(); }


		ArrayList<String> alTmp = null;
		for( Element e: racine.getChildren() )
		{
			for( Element child: e.getChildren() )
			{
				alTmp = new ArrayList<String>();
				alTmp.add( e.getAttribute( "idCoul" ).getValue() );
				alTmp.add( e.getAttribute( "poids" ).getValue() );

				couleurs.put( child.getText(), alTmp );
				regPatterns.put( child.getText(), Pattern.compile( "\\b" +child.getText() + "\\b" ) );
			}
		}
	}


	/**
	 * Méthode qui colorie la ligne passée en paramètre
	 * @param ligne
	 * @return
	 */
	public static String colorierLigne( String ligne )
	{
		int ligneLengthDebut = ligne.length();
		String debutLigne = ""; // Utilisée pour éviter de colorer des keywords dans les commentaires.
		String finLigne = "";

		if ( ligne.contains( "//" ) )
		{
			int indexDebutCom = ligne.indexOf( "//" );

			if ( indexDebutCom == 0 ) ligne = CouleurConsole.VERT.getFont() + ligne + "\033[0m";
			else
			{
				debutLigne = ligne.substring( 0, indexDebutCom );
				finLigne = CouleurConsole.VERT.getFont() + ligne.substring( indexDebutCom, ligne.length() ) + "\033[0m";
			}
		}
		else
			debutLigne = ligne;

		Matcher matcher = null;
		for ( String mot : couleurs.keySet() )
		{
			matcher = regPatterns.get( mot ).matcher( ligne );
			if ( matcher.find() )
			{
				if ( mot.equals( "a" ) && ligne.indexOf( "a" ) < ligne.indexOf( "faire" ) )
					debutLigne = debutLigne.replaceFirst( mot, colorierMot( mot ) );
				else
					debutLigne = debutLigne.replace( mot, colorierMot( mot ) );
			}
		}
		
		System.out.println( );
		return debutLigne + finLigne + " ".repeat( 75 - ligneLengthDebut );
	}


	/**
	 * Méthode qui colorie le mot passé en paramètre
	 * @param mot
	 * @return
	 */
	private static String colorierMot( String mot )
	{
		if ( !couleurs.containsKey( mot ) ) return mot;

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
			System.out.println( key + " " + colorierMot( key ) );
	}
}
