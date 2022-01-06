package AlgoPars.Metier;

public class Calculateur
{
	public static double calculerMath( String expr )
	{
		expr=Calculateur.nettoyer(expr);
		System.out.println(expr);

		/*-----------Remplasser les variables nommée par leur valeurs------------*/

		int index = 0;

		/*---------------Parenthese-----------------*/
		if ( (index = expr.indexOf( "(" )) != -1 )
		{
			//Ex: ((45*2)+5)
			int firstParenthese   = index ;
			// <- 0
			int secondParenthese  = expr.indexOf(")");//fin du premier groupe le plus encapsuler
			// <- 9
			while (expr.indexOf( "(" ,firstParenthese+1) != -1)
			{
				firstParenthese = expr.indexOf( "(" ,firstParenthese+1);
			}
			String group = expr.substring(firstParenthese+1,secondParenthese);

			String debut = expr.substring(0,firstParenthese );
			// <- "("
			String fin   = expr.substring(secondParenthese+1);
			// <- "+5)"

			return calculerMath(debut + String.valueOf(calculerMath(group)) + fin );
		}

		/*---------------valeur absolue-----------------*/
		/*prioritaire car considéré comme des parenthese*/
		/*----------------------------------------------*/
		if ( (index = expr.indexOf( "|" )) != -1 )
		{

			int index2 = Calculateur.trouver2ndPipe(expr,index+1);

			//si on est pas du coté fermant de l'expressions

			index = Calculateur.trouver1erePipe(expr,index2-1);

			String firstPart = expr.substring(0,index);

			System.out.println("premiere partie"+firstPart);

			String milieu    = expr.substring(index+1,index2);

			System.out.println("milieu"+milieu);

			String secondPart= expr.substring(index2+1);

			System.out.println("seconde partie"+secondPart);
			
			return calculerMath(
							firstPart+
							String.valueOf(
									Math.abs( 
											calculerMath(milieu)
											)
										  )
							+secondPart
							);
		}

		/*if (expr.matches("\w+(\w*)"))
			primitives.find(la foncion)
		*/


		// Opérateurs unaires.
		if ( (index = expr.indexOf( "+" )) != -1 )
		{
			if ( expr.charAt( 0 ) != '+' )
				return calculerMath( expr.substring( 0, index ) ) + calculerMath( expr.substring( index + 1, expr.length() ) );
			else
				return calculerMath( expr.substring( 1, expr.length() ) );
		}
		if ( (index = expr.indexOf( "-" )) != -1 )
		{
			if ( expr.charAt( 0 ) != '-' )
	        	return calculerMath( expr.substring( 0, index ) ) - calculerMath( expr.substring( index + 1, expr.length() ) );
			else
				return -calculerMath( expr.substring( 1, expr.length() ) );
		}

		/*---------------racine carre---------------*/
		/*le symbole racine si sans parenthese 		*/
		/*s'arrete ou prochain symbole				*/
		/*------------------------------------------*/

		if ( (index = expr.indexOf( "\\/¯" ) ) != -1 )
		{
			int index2 = index+3 ;

			while(		expr.charAt(index2) != '+'   &&
						expr.charAt(index2) != '-'   &&
						expr.charAt(index2) != '('   &&
						expr.charAt(index2) != '/'   &&
						expr.charAt(index2) != '^'   &&
						expr.charAt(index2) != ' '   && //c'est un modulo ou un div
						index2<expr.length()-1)
			{
				index2++ ;
			}

			String firstPart = expr.substring(  0,index);
			
			String secondPart = expr.substring( index2+1);

			String milieu = expr.substring( index+3,index2+1);

			return calculerMath(
							firstPart+
							String.valueOf(
									Math.sqrt( 
											calculerMath(milieu)
											)
										  )
							+secondPart
							);
		}

		// Opérateurs binaires.
		if ( (index = expr.indexOf( "×" )) != -1 )
			return calculerMath( expr.substring( 0, index ) ) * calculerMath( expr.substring( index + 1, expr.length() ) );
		if ( (index = expr.indexOf( "/" )) != -1 )
			return calculerMath( expr.substring( 0, index ) ) / calculerMath( expr.substring( index + 1, expr.length() ) );
		if ( (index = expr.indexOf( "div" )) != -1 )
			return (int)( calculerMath( expr.substring( 0, index ) ) / calculerMath( expr.substring( index + 3, expr.length() ) ) );
		if ( (index = expr.indexOf( "mod" )) != -1 )
			return calculerMath( expr.substring( 0, index ) ) % calculerMath( expr.substring( index + 3, expr.length() ) );
		if ( (index = expr.indexOf( "^" )) != -1 )
			return Math.pow( calculerMath( expr.substring( 0, index ) ), calculerMath( expr.substring( index + 1, expr.length() ) ) );

		System.out.println("out:"+expr);
		return Double.parseDouble( expr );
	}

	private static int trouver1erePipe(String s,int fin)
	{
		s=s.substring(0,fin);

		int index = fin-1;

		while (	s.charAt(index) !='|' ) {
			index-- ;
		}

		return index ;
	}


	/**
	 * methode qui trouve la pipe la plus ouvrante a partir de debut 
	 *
	 */
	private static int trouver2ndPipe(String s,int debut)
	{
		int index = s.indexOf( "|" , debut);

		//on arrette si "5|"
		while (	!(Character.isDigit(s.charAt(index-1)) && s.charAt(index) == '|' )  ) {
			index++ ;
		}

		return index ;
	}

	/**
	 *Fonction qui nettoie la chaine pour eviter d'avoir a traité les chaine vide
	 */
	private static String nettoyer(String s)
	{
		return  s.replaceAll(" *\\+ *","+")
				 .replaceAll(" *- *"  ,"-")
				 .replaceAll(" *× *"  ,"×")
				 .replaceAll(" *\\/ *","/")
				 .replaceAll(" *\\^ *","^")
				 .replaceAll(" +mod +"," mod ")
				 .replaceAll(" +div +"," div ")
				 .replaceAll(" +$","")
				 .replaceAll("^ +","")

				 .replaceAll(" +ou +"," ou ")
				 .replaceAll(" +xou +"," xou ")
				 .replaceAll(" +et +"," et ")
				 .replaceAll(" +non +"," non ")
				 .replaceAll(" += +"," = ")
				 .replaceAll(" +< +"," < ")
				 .replaceAll(" +> +"," > ")
				 .replaceAll(" +>= +"," >= ")
				 .replaceAll(" +=> +"," >= ");
	}

	public static void main(String[] args) 
	{
		System.out.println( calculerMath( "5 × 4 + 3" ) + " = 23 ?" );
		System.out.println( calculerMath( "5×4+3" ) + " = 23 ?" );
		System.out.println( calculerMath( "+21" ) + " = 21 ?");
		System.out.println( calculerMath( "-21" ) + " = -21 ?");
		System.out.println( calculerMath( "20 / 8" ) + " = 2.5 ?" );
		System.out.println( calculerMath( "20 div 8" ) + " = 2 ?" );
		System.out.println( calculerMath( "13 mod 5" ) + " = 3 ?" );
		System.out.println( calculerMath( "5 ^ 2 + 3 × 10" ) + " = 55 ?" );
		System.out.println( calculerMath( "(8/(45-(2))+5)" ) + " = 30?" );
		System.out.println( calculerMath( "5-\\/¯(25)+5"));
		System.out.println( calculerMath( "|-||9-5+|-5+9||||"));
	}
}