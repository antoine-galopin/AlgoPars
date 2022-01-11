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
	private static String couleurCommentaire;
	private static boolean commMultiLignes = false;


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
				alTmp.add( e.getAttribute( "poids"  ).getValue() );

				couleurs.put( child.getText(), alTmp );
				regPatterns.put( child.getText(), Pattern.compile(
					"\\b" + child.getText() + "\\b(?![^\"]*\"[^\"]*(?:\"[^\"]*\"[^\"]*)*$)"
				));
			}
		}

		couleurCommentaire = getCouleurFromId( couleurs.get( "//" ).get( 0 ) );
	}


	public static String colorierLigne( String ligne )
	{
		return colorierLigne( ligne, true );
	}


	/**
	 * Méthode qui colorie la ligne passée en paramètre
	 * @param ligne
	 * @return String
	 */
	public static String colorierLigne( String ligne, boolean ajouterBlanc )
	{
		if ( ligne.isBlank() ) return ligne;

		int ligneLengthDebut = ligne.length();
		String debutLigne = ""; // Utilisée pour éviter de colorer des keywords dans les commentaires.
		String finLigne = "";

		// Gestion des commentaires multilignes.
		if ( ligne.contains( "/*" ) )
		{
			debutLigne = colorierLigne( ligne.substring( 0, ligne.indexOf( "/*" ) ), false );
			if ( ligne.contains( "*/" ) )
			{
				ligne = debutLigne + couleurCommentaire + 
					ligne.substring( ligne.indexOf( "/*" ), ligne.indexOf( "*/" ) ) + "\033[0m" +
					colorierLigne( ligne.substring( ligne.indexOf( "*/") + 3, ligne.length() ) );
				return ligne + " ".repeat( 75 - ligneLengthDebut );
			}
			commMultiLignes = true;
			ligne = debutLigne + couleurCommentaire +
				ligne.substring( ligne.indexOf( "/*" ), ligne.length() ) + "\033[0m";
			return ligne + " ".repeat( 75 - ligneLengthDebut );
		}

		if ( commMultiLignes )
		{
			if ( ligne.contains( "*/" ) )
			{
				commMultiLignes = false;
				ligne = couleurCommentaire + ligne.substring( 0, ligne.indexOf( "*/" ) + 2 ) +
					"\033[0m" + colorierLigne( ligne.substring( ligne.indexOf( "*/" ) + 2, ligne.length() ) );
				return ligne + " ".repeat( 75 - ligneLengthDebut );
			}
			ligne = couleurCommentaire + ligne + "\033[0m";

			return ligne + " ".repeat( 75 - ligneLengthDebut );
		}

		// Gestion des commentaires simples.
		if( ligne.contains( "//" ) )
		{
			int indexDebutCom = ligne.indexOf( "//" );

			if( indexDebutCom == 0 ) ligne = couleurs + ligne + "\033[0m";
			else
			{
				debutLigne = ligne.substring( 0, indexDebutCom );
				finLigne = couleurCommentaire + ligne.substring( indexDebutCom, ligne.length() ) + "\033[0m";
			}
		}
		else debutLigne = ligne;

		// Coloration des mots clés d'une ligne.
		Matcher matcher = null;
		for( String mot : couleurs.keySet() )
		{
			matcher = regPatterns.get( mot ).matcher( ligne );
			if( matcher.find() )
			{
				if( mot.equals( "a" ) && ligne.indexOf( "a" ) < ligne.indexOf( "faire" ) )
					debutLigne = debutLigne.replaceFirst( mot, colorierMot( mot ) );
				else
					debutLigne = debutLigne.replace( mot, colorierMot( mot ) );
			}
		}

		if ( ajouterBlanc )
			return debutLigne + finLigne + 
				" ".repeat( (75 - ligneLengthDebut == 75) ? 0 : 75 - ligneLengthDebut );
		else
			return debutLigne + finLigne;
	}


	/**
	 * Méthode qui colorie le mot passé en paramètre
	 * @param mot
	 * @return String
	 */
	private static String colorierMot( String mot )
	{
		if( !couleurs.containsKey( mot ) ) return mot;

		String couleur;
		String id = couleurs.get( mot ).get( 0 );
		couleur = getCouleurFromId( id );

		if( couleurs.get( mot ).get( 1 ).equals( "true" ) ) couleur += "\033[1m";

		return couleur + mot + "\033[0m";
	}


	public static String getCouleurFromId( String id )
	{
		switch( id ) 
		{
			case "0": 
				return CouleurConsole.BLANC.getFont();
			case "1": 
				return  CouleurConsole.BLEU.getFont();
			case "2": 
				return  CouleurConsole.CYAN.getFont();
			case "3": 
				return  CouleurConsole.JAUNE.getFont();
			case "4": 
				return  CouleurConsole.VERT.getFont();
			case "5":
				return  CouleurConsole.ROUGE.getFont();
			case "6":
				return  CouleurConsole.MAUVE.getFont();
			default: 
				return  CouleurConsole.BLANC.getFont();
		}
	}
}