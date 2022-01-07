package AlgoPars.Metier;

public class Calculateur
{
	public static double calculer(String expr)
	{
		expr = Calculateur.nettoyer(expr); // nettoyage de l'expression

		/*-----------Remplasser les variables nommée par leur valeurs------------*/

		int index = 0;

		// traitement des parenthèses
		if( (index = expr.indexOf( "(" )) != -1 ) {
			return calculer(
										groupeParenthese(expr, index)[0]   +
				String.valueOf(calculer(groupeParenthese(expr, index)[1])) +
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

			return calculer( premierePartie + String.valueOf( Math.abs( calculer(milieu) ) ) + deuxiemePartie );
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
					return calculer( expr.substring( 0, index ) ) + tabOpe[i] * calculer( expr.substring( index + 1, expr.length() ) );
				else
					return tabOpe[i] * calculer( expr.substring( 1, expr.length() ) );
			}
		}

		// traitement des racines carrées ( on considère que tout est dans la racine jusqu'au prochain symbole )
		if( (index = expr.indexOf( "\\/¯" )) != -1 ) {
			int index2 = index + 3;

			while( expr.charAt(index2) != '+' &&
				   expr.charAt(index2) != '-' &&
				   expr.charAt(index2) != '(' &&
				   expr.charAt(index2) != '/' &&
				   expr.charAt(index2) != '^' &&
				   expr.charAt(index2) != ' ' && //c'est un modulo ou un div
				   index2 < expr.length() - 1 ) { index2++; }

			String premierePartie = expr.substring( 0         , index      );
			String milieu         = expr.substring( index  + 3, index2 + 1 );
			String deuxiemePartie = expr.substring( index2 + 1             );

			return calculer( premierePartie + String.valueOf( Math.sqrt( calculer(milieu) ) ) + deuxiemePartie );
		}

		// Opérateurs binaires
		if( ( index = expr.indexOf("×"  ) ) != -1 )
			return           calculer( expr.substring( 0, index ) ) * calculer( expr.substring( index + 1, expr.length() ) );

		if( ( index = expr.indexOf("/"  ) ) != -1 )
			return           calculer( expr.substring( 0, index ) ) / calculer( expr.substring( index + 1, expr.length() ) );

		if( ( index = expr.indexOf("div") ) != -1 )
			return (int)(    calculer( expr.substring( 0, index ) ) / calculer( expr.substring( index + 3, expr.length() ) ) );

		if( ( index = expr.indexOf("mod") ) != -1 )
			return           calculer( expr.substring( 0, index ) ) % calculer( expr.substring( index + 3, expr.length() ) );

		if( ( index = expr.indexOf("^"  ) ) != -1 )
			return Math.pow( calculer( expr.substring( 0, index ) ) , calculer( expr.substring( index + 1, expr.length() ) ) );

/*-----------------------------booleenne--------------------------------*/
		
		// faux = 0
		// vrai = 1

		if( ( index = expr.indexOf(" ou " ) ) != -1 )
			return             calculer( expr.substring(0, index ) ) +  calculer( expr.substring(index + 4) );

		if( ( index = expr.indexOf(" et " ) ) != -1 )
			return             calculer( expr.substring(0, index ) ) *  calculer( expr.substring(index + 4) );

		if( ( index = expr.indexOf("="    ) ) != -1 )
			return Math.abs( ( calculer( expr.substring(0, index ) ) -  calculer( expr.substring(index + 1) ) ) -1 );

		if( ( index = expr.indexOf("/="   ) ) != -1 )
			return             calculer( expr.substring(0, index ) ) -  calculer( expr.substring(index + 2) );

		if( ( index = expr.indexOf(" non ") ) != -1 )
			return Math.abs(   calculer( expr.substring(index + 5) ) -1 );

		if( ( index = expr.indexOf(">="   ) ) != -1 )
			return         (   calculer( expr.substring(0, index ) ) >= calculer( expr.substring(index + 2) ) ) ? 1 : 0;

		if( ( index = expr.indexOf("<="   ) ) != -1 )
			return         (   calculer( expr.substring(0, index ) ) <= calculer( expr.substring(index + 2) ) ) ? 1 : 0;

		if( ( index = expr.indexOf("<"    ) ) != -1 )
			return         (   calculer( expr.substring(0, index ) ) <  calculer( expr.substring(index + 1) ) ) ? 1 : 0;

		if( ( index = expr.indexOf(">"    ) ) != -1 )
			return         (   calculer( expr.substring(0, index ) ) >  calculer( expr.substring(index + 1) ) ) ? 1 : 0;

		System.out.println("out:" + expr);
		return Double.parseDouble( expr );
	}

	
	/**
	 * Méthode qui renvoitle groupe de parenthèse le plus profond dans l'expression passée en paramètre
	 * @param expr
	 * @param index
	 * @return String[]
	 */
	private static String[] groupeParenthese(String expr, int index)
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
	 * Méthode qui renvoit l'indice du dernière pipe d'une chaine passée en paramètre
	 * @param s
	 * @param fin
	 * @return int
	 */
	private static int trouverPremierePipe(String s, int fin)
	{
		s = s.substring( 0, fin );

		int index = fin - 1;

		while( s.charAt(index) !='|' ) { index--; }

		return index;
	}


	/**
	 * Méthode qui renvoit l'indice du premier pipe d'un chaine passée en paramètre
	 * @param s
	 * @param debut
	 * @return int
	 */
	private static int trouverDeuxiemePipe(String s, int debut)
	{
		int index = s.indexOf( "|", debut );

		while( !(Character.isDigit(s.charAt(index-1)) && s.charAt(index) == '|' ) ) { index++; }

		return index;
	}


	/**
	 * Fonction qui nettoie une chaine avant de la traiter plus simplement
	 * @param s
	 * @return String
	 */
	private static String nettoyer(String s)
	{
		return  s.replaceAll( " *\\+ *" ,"+"     ) // opérateurs
				 .replaceAll( " *- *"   ,"-"     )
				 .replaceAll( " *× *"   ,"×"     )
				 .replaceAll( " *\\/ *" ,"/"     )
				 .replaceAll( " *\\^ *" ,"^"     )
				 .replaceAll( " +mod +" ," mod " )
				 .replaceAll( " +div +" ," div " )
				 .replaceAll( " +$"     ,""      )
				 .replaceAll( "^ +"     ,""      )
				
				 .replaceAll( " +ou +"  ," ou "  ) // comparateurs
				 .replaceAll( " +xou +" ," xou " )
				 .replaceAll( " +et +"  ," et "  )
				 .replaceAll( " *non +" ," non " )
				 .replaceAll( " += +"   ," = "   )
				 .replaceAll( " +< +"   ," < "   )
				 .replaceAll( " +> +"   ," > "   )
				 .replaceAll( " +>= +"  ," >= "  )
				 .replaceAll( " +=> +"  ," >= "  )
				 .replaceAll( " +vrai *"," 0"    )
				 .replaceAll( " +faux *"," 1"    );
	}

	public static void main(String[] args)
	{
		/*System.out.println( calculer( "5 × 4 + 3"         ) + " = 23 ?"  );
		System.out.println( calculer( "5×4+3"             ) + " = 23 ?"  );
		System.out.println( calculer( "+21"               ) + " = 21 ?"  );
		System.out.println( calculer( "-21"               ) + " = -21 ?" );
		System.out.println( calculer( "20 / 8"            ) + " = 2.5 ?" );
		System.out.println( calculer( "20 div 8"          ) + " = 2 ?"   );
		System.out.println( calculer( "13 mod 5"          ) + " = 3 ?"   );
		System.out.println( calculer( "5 ^ 2 + 3 × 10"    ) + " = 55 ?"  );
		System.out.println( calculer( "(8/(45-(2))+5)"    ) + " = 30?"   );
		System.out.println( calculer( "5-\\/¯(25)+5"      )              );
		System.out.println( calculer( "|-||9-5+|-5+9||||" )              );*/
		System.out.println( calculer( "non 5<6"           )              );
	}
}