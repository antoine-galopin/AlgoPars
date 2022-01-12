package AlgoPars.Metier;

import java.util.ArrayList;
import AlgoPars.Metier.Types.*;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Calculateur
{
	public static String calculer(String expression)
	{
		System.out.println(getType(expression));
		switch( getType(expression) ) {
			case "chaine"   : return calculerChaine(expression);
			case "caractere": return String.valueOf(calculerChaine(expression).charAt(0));
			case "booleen"  :
				switch( Double.toString(calculerMath(expression)) ) {
					case "0.0": return "faux";
					case "1.0": return "vrai";
					default   : throw new RuntimeException("cannot implicit convert to Boolean:" + expression + "->" + calculerMath(expression));
				}
			case "reel"     : return String.valueOf(      calculerMath(expression));
			case "entier"   : return String.valueOf((int) calculerMath(expression));
			//si c'est un tableau pas d'operations
			default         : 
				return calculerTableau(expression);
		}
	}

	private static double calculerMath(String expr)
	{
		expr = Calculateur.nettoyer(expr); // nettoyage de l'expression
		System.out.println(expr);

		/*-----------Remplacer les variables nommées par leur valeurs------------*/

		int index = 0;

		// traitement des parenthèses
		if( (index = expr.indexOf("(")) != -1 ) {
			return calculerMath(
											groupeParenthese(expr, index)[0]   +
				String.valueOf(calculerMath(groupeParenthese(expr, index)[1])) +
											groupeParenthese(expr, index)[2]
			);
		}

		// traitement des valeurs absolues ( ordre de priorité similaire aux parenthèses )
		if( (index = expr.indexOf( "|" )) != -1 ) { // s'il y a un pipe
			int index2 = Calculateur.trouverDeuxiemePipe(expr, index  + 1);
			    index  = Calculateur.trouverPremierePipe(expr, index2 - 1);

			String premierePartie = expr.substring( 0         , index  );
			String milieu         = expr.substring( index  + 1, index2 );
			String deuxiemePartie = expr.substring( index2 + 1         );

			return calculerMath( premierePartie + String.valueOf( Math.abs( calculerMath(milieu) ) ) + deuxiemePartie );
		}

		/*if (expr.matches("\w+(\w*)"))
			primitives.find(la foncion)
		*/


		// Opérateurs unaires
		char[] tabCar = new char[] {'+', '-'};
		int [] tabOpe = new int [] { 1 , -1 };

		for( int i = 0; i < tabCar.length; i++ ) {
			if( ( index = expr.indexOf(tabCar[i]) ) != -1 ) {
				if( expr.charAt(0) != tabCar[i] )
					return calculerMath( expr.substring( 0, index ) ) + tabOpe[i] * calculerMath( expr.substring( index + 1, expr.length() ) );
				else
					return tabOpe[i] * calculerMath( expr.substring( 1, expr.length() ) );
			}
		}

		// traitement des racines carrées ( on considère que tout est dans la racine jusqu'au prochain symbole )
		if( (index = expr.indexOf( "\\/¯" )) != -1 ) {
			int index2 = index + 3;

			while( expr.charAt(index2) != '+' &&
				   expr.charAt(index2) != '-' &&
				   expr.charAt(index2) != '(' &&
				   expr.charAt(index2) != '×' &&
				   expr.charAt(index2) != '/' &&
				   expr.charAt(index2) != '^' &&
				   expr.charAt(index2) != ' ' && // c'est un modulo ou un div
				   index2 < expr.length() - 1 ) { index2++; }

			String premierePartie = expr.substring( 0         , index      );
			String milieu         = expr.substring( index  + 3, index2     );
			String deuxiemePartie = expr.substring( index2                 );

			return calculerMath( premierePartie + String.valueOf( Math.sqrt( calculerMath(milieu) ) ) + deuxiemePartie );
		}

		// Opérateurs binaires
		if( ( index = expr.indexOf("×"  ) ) != -1 )
			return           calculerMath( expr.substring( 0, index ) ) * calculerMath( expr.substring( index + 1, expr.length() ) );

		if( ( index = expr.indexOf("/"  ) ) != -1 )
			return           calculerMath( expr.substring( 0, index ) ) / calculerMath( expr.substring( index + 1, expr.length() ) );

		if( ( index = expr.indexOf("div") ) != -1 )
			return (int)(    calculerMath( expr.substring( 0, index ) ) / calculerMath( expr.substring( index + 3, expr.length() ) ) );

		if( ( index = expr.indexOf("mod") ) != -1 )
			return           calculerMath( expr.substring( 0, index ) ) % calculerMath( expr.substring( index + 3, expr.length() ) );

		if( ( index = expr.indexOf("^"  ) ) != -1 )
			return Math.pow( calculerMath( expr.substring( 0, index ) ) , calculerMath( expr.substring( index + 1, expr.length() ) ) );

/*-----------------------------booleenne--------------------------------*/
		
		// faux = 0
		// vrai = 1
		if( ( index = expr.indexOf(" xou " ) ) != -1 )
			return           ( calculerMath( expr.substring(0, index ) ) +  calculerMath( expr.substring(index + 4) ) ) % 2;

		if( ( index = expr.indexOf(" ou " ) ) != -1 )
			return             calculerMath( expr.substring(0, index ) ) +  calculerMath( expr.substring(index + 4) );

		if( ( index = expr.indexOf(" et " ) ) != -1 )
			return             calculerMath( expr.substring(0, index ) ) *  calculerMath( expr.substring(index + 4) );

		if( ( index = expr.indexOf("="    ) ) != -1 )
			return (Math.abs( ( calculerMath( expr.substring(0, index ) ) -  calculerMath( expr.substring(index + 1) ) ) -1 )>=1)?1:0;

		if( ( index = expr.indexOf("/="   ) ) != -1 )
			return             calculerMath( expr.substring(0, index ) ) -  calculerMath( expr.substring(index + 2) );

		if( ( index = expr.indexOf(" non ") ) != -1 )
			return Math.abs(   calculerMath( expr.substring(index + 5) ) -1 );

		//non remplassable
		if( ( index = expr.indexOf(">="   ) ) != -1 )
			return         (   calculerMath( expr.substring(0, index ) ) >= calculerMath( expr.substring(index + 2) ) ) ? 1 : 0;

		if( ( index = expr.indexOf("<="   ) ) != -1 )
			return         (   calculerMath( expr.substring(0, index ) ) <= calculerMath( expr.substring(index + 2) ) ) ? 1 : 0;

		if( ( index = expr.indexOf("<"    ) ) != -1 )
			return         (   calculerMath( expr.substring(0, index ) ) <  calculerMath( expr.substring(index + 1) ) ) ? 1 : 0;

		if( ( index = expr.indexOf(">"    ) ) != -1 )
			return         (   calculerMath( expr.substring(0, index ) ) >  calculerMath( expr.substring(index + 1) ) ) ? 1 : 0;

		return Double.parseDouble( expr );
	}

	public static String calculerTableau(String expr)
	{
		//String retour = expression.substring(indexOf) ;

		/*for (String s : expression.split(",+(?![^\"]*\"[^\"]*(?:\"[^\"]*\"[^\"]*)*$)")) {
			retour=retour+calculer(s)+",";
		}*/

		return expr;
	}

	public static String calculerChaine(String expr)
	{		
		String retour = "" ;

		System.out.println(expr);

        Pattern pattern = Pattern.compile("( */= *)(?=[\"'].*[\"'])");
        Matcher matcher = pattern.matcher(expr);

        //si c'est un =
		if (matcher.find()) 
		{
			String[] tab = expr.split("( */= *)(?=[\"'].*[\"'])");

			retour = (!calculerChaine(tab[0]).equals(calculerChaine(tab[1])))?"vrai":"faux";
			
			return retour;
		}
		else
		{
			//si c'est un /=
			pattern = Pattern.compile("( *= *)(?=[\"'].*[\"'])");
   			matcher = pattern.matcher(expr);

			if (matcher.find())
			{
				String[] tab = expr.split("( *= *)(?=[\"'].*[\"'])");

				retour = (calculerChaine(tab[0]).equals(calculerChaine(tab[1])))?"vrai":"faux";
				
				return retour;
			}
			else 
			{
				pattern = Pattern.compile("(( *© *)|( *\\(c\\) *))(?=[\"'].*[\"'])");
   				matcher = pattern.matcher(expr);

				if (matcher.find())
				{
					for( String s : expr.split("(( *© *)|( *\\(c\\) *))(?=[\"'].*[\"'])") ) {
						retour += traiterChaine(s);
					}
					return retour;
				}
			}

		}

		return traiterChaine(expr) ;
	}

	public static String traiterChaine(String expr)
	{
		expr = expr.replaceAll("^ *", "").replaceAll(" *$", "");

		if( expr.charAt(0) == '"' || expr.charAt(0) == '\'' )
			return expr.substring( 1, expr.length() - 1 );

		return expr;
	}
	
/*--------------------------------------------------------------------------*/

	/**
	 * Méthode qui renvoit le groupe de parenthèse le plus profond dans l'expression passée en paramètre
	 * @param expr
	 * @param index
	 * @return String[]
	 */
	public static String[] groupeParenthese(String expr, int index)
	{
		int premiereParenthese = index;
		int deuxiemeParenthese = expr.indexOf(")"); //fin du groupe le plus encapsulé

		while( expr.indexOf( "(", premiereParenthese + 1 ) != -1 ) {
			premiereParenthese = expr.indexOf( "(", premiereParenthese + 1 ); // recherche de la prochaine parenthèse ouvrante
		}

		return new String[] {
			expr.substring( 0                      , premiereParenthese ), // avant la parenthèse
			expr.substring( premiereParenthese + 1 , deuxiemeParenthese ), // dans la parenthèse
			expr.substring( deuxiemeParenthese + 1                      )  // après la parenthèse
		};
	}


	/**
	 * Méthode qui renvoit l'indice de la pipe ouvrante la plus encapsuler d'un chaine passée en paramètre
	 * @param s
	 * @param fin
	 * @return int
	 */
	private static int trouverPremierePipe(String s, int fin)
	{
		s = s.substring( 0, fin );

		int index = fin - 1;

		while( s.charAt(index) != '|' ) index--;

		return index;
	}


	/**
	 * Méthode qui renvoit l'indice de la pipe fermante la plus encapsulée d'un chaine passée en paramètre
	 * @param s
	 * @param debut
	 * @return int
	 */
	private static int trouverDeuxiemePipe(String s, int debut)
	{
		int index = s.indexOf( "|", debut );

		while( !(Character.isDigit(s.charAt(index - 1)) && s.charAt(index) == '|' ) ) { index++; }

		return index;
	}


	/**
	 * Fonction qui nettoie une chaine avant de la traiter plus simplement
	 * @param s
	 * @return String
	 */
	private static String nettoyer(String s)
	{
		return s.replaceAll( " *\\+ *" , "+"     ) // opérateurs
				.replaceAll( " *- *"   , "-"     )
				.replaceAll( " *× *"   , "×"     )
				.replaceAll( " *\\/ *" , "/"     )
				.replaceAll( " *\\^ *" , "^"     )
				.replaceAll( " +mod +" , " mod " )
				.replaceAll( " +div +" , " div " )
				.replaceAll( " +$"     , ""      )
				.replaceAll( "^ +"     , ""      )
				.replaceAll( " *\\| *" , "|"     )

				.replaceAll( " +ou +"  , " ou "  ) // comparateurs
				.replaceAll( " +xou +" , " xou " )
				.replaceAll( " +et +"  , " et "  )
				.replaceAll( " *non +" , " non " )
				.replaceAll( " += +"   , " = "   )
				.replaceAll( " +< +"   , " < "   )
				.replaceAll( " +> +"   , " > "   )
				.replaceAll( " +>= +"  , " >= "  )
				.replaceAll( " +=> +"  , " >= "  )
				.replaceAll( " *vrai *", " 1 "   )
				.replaceAll( " *faux *", " 0 "   );
	}

	public static String getType(String expression)
	{
		if( expression.replaceAll("^ *", "").charAt(0) == '{' )
			return "tableau de " + getType( expression.substring( expression.indexOf("{") + 1 ) );

		if( expression.contains("(c)"    ) ||
			expression.contains("©"      ) ||
			expression.contains("\"" )   ) return "chaine";
		
		if( expression.matches ("'.*'"   ) ) return "caractere";

		if( expression.contains("<"      ) ||
			expression.contains(">"      ) ||
			expression.contains("="      ) ||
			expression.contains("/="     ) ||
			expression.contains(" et "   ) ||
			expression.contains(" ou "   ) ||
			expression.contains("non"    ) ||
			expression.contains("vrai"   ) ||
			expression.contains("faux"   ) ) return "booleen";

		if( expression.contains("."      ) ) return "reel";

		return "entier";
	}

	public static void main(String[] args)
	{
		//System.out.println( calculer( "5 × 4 + 3"         ) + " = 23 ?"  );
		//System.out.println( calculer( "5×4+3"             ) + " = 23 ?"  );
		//System.out.println( calculer( "+21"               ) + " = 21 ?"  );
		//System.out.println( calculer( "-21"               ) + " = -21 ?" );
		//System.out.println( calculer( "20 / 8"            ) + " = 2.5 ?" );
		//System.out.println( calculer( "20 div 8"          ) + " = 2 ?"   );
		//System.out.println( calculer( "13 mod 5"          ) + " = 3 ?"   );
		//System.out.println( calculer( "5 ^ 2 + 3 × 10"    ) + " = 55 ?"  );
		//System.out.println( calculer( "(8/(45-(2))+5)"    ) + " = 30?"   );
		//System.out.println( calculer( "5-\\/¯(25)+5"      )              );
		//System.out.println( calculer( "|-||9-5+|-5+9||||" )              );
		//System.out.println( calculer( "non 5<6"           )              );
		//System.out.println( calculer( "vrai xou vrai"     )              );
		//System.out.println( calculer( "\"(c)\\\"cpoa\" © \"n1\"  (c) 'vim\"' ") );*/

		/*  #~~~~# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~ #~~~~#  */
		/*  #~~~~#  Les petits tests d'Antoine  #~~~~#  */
		/*  #~~~~# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~ #~~~~#  */

		System.out.println( calculer( "faux xou 5- 4 < 2" ) );
		System.out.println( calculer( "5+\\/¯100^5-1" ) );
		System.out.println("----------");
		System.out.println( calculer( "5+\\/¯100^5+|-10|-1" ) );
		System.out.println( calculer( "\"janvierc\"  /= \"fevrier\""));

		//System.out.println( calculer(  ) );
		//System.out.println( calculer(  ) );
		//System.out.println( calculer(  ) );
	}
}
