package AlgoPars.Metier;


public class Calculateur
{
	public static double calculer( String expr )
	{
		expr=Calculateur.nettoyer(expr);

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

			System.out.println("grp:<"+group+">");

			String debut = expr.substring(0,firstParenthese );
			// <- "("
			String fin   = expr.substring(secondParenthese+1);
			// <- "+5)"

			return calculer(debut + String.valueOf(calculer(group)) + fin );
		}

		/*---------------valeur absolue-----------------*/
		/*prioritaire car considéré comme des parenthese*/
		/*----------------------------------------------*/
		if ( (index = expr.indexOf( "|" )) != -1 )
		{
			int index2 =0 ;

			System.out.println("valeur absolue trouvé");

			String firstPart  = expr.substring(  0,index);//ouvrante

			index2=expr.indexOf( "|" , index+1);

			while (	!Character.isDigit(expr.charAt(index2)) && expr.charAt(index2) != ')'   ) {
				index2++ ;
				System.out.println(index2);
			}
			
			String secondPart = expr.substring(index2);

			System.out.println(secondPart);

			String milieu = expr.substring(index+1,index2);

			System.out.println(milieu);
			
			return calculer(
							firstPart+
							String.valueOf(
									Math.abs( 
											calculer(milieu)
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
				return calculer( expr.substring( 0, index ) ) + calculer( expr.substring( index + 1, expr.length() ) );
			else
				return calculer( expr.substring( 1, expr.length() ) );
		}
		if ( (index = expr.indexOf( "-" )) != -1 )
		{
			if ( expr.charAt( 0 ) != '-' )
	        	return calculer( expr.substring( 0, index ) ) - calculer( expr.substring( index + 1, expr.length() ) );
			else
				return -calculer( expr.substring( 1, expr.length() ) );
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
						index2<expr.length()-1)
			{
				if (expr.substring(expr.length())>=5) {
					if (!expr.substring(index2,index2+5).equals(" mod ") &&
						!expr.substring(index2,index2+5).equals(" div ") )
						break ;
				}
				index2++ ;
			}

			String firstPart = expr.substring(  0,index);

			System.out.println("!"+firstPart+"!");
			
			String secondPart = expr.substring( index2+1);

			System.out.println("!"+secondPart+"!");

			String milieu = expr.substring( index+3,index2+1);

			System.out.println("!"+milieu+"!");

			return calculer(
							firstPart+
							String.valueOf(
									Math.sqrt( 
											calculer(milieu)
											)
										  )
							+secondPart
							);
		}

		// Opérateurs binaires.
		if ( (index = expr.indexOf( "×" )) != -1 )
			return calculer( expr.substring( 0, index ) ) * calculer( expr.substring( index + 1, expr.length() ) );
		if ( (index = expr.indexOf( "/" )) != -1 )
			return calculer( expr.substring( 0, index ) ) / calculer( expr.substring( index + 1, expr.length() ) );
		if ( (index = expr.indexOf( "div" )) != -1 )
			return (int)( calculer( expr.substring( 0, index ) ) / calculer( expr.substring( index + 3, expr.length() ) ) );
		if ( (index = expr.indexOf( "mod" )) != -1 )
			return calculer( expr.substring( 0, index ) ) % calculer( expr.substring( index + 3, expr.length() ) );
		if ( (index = expr.indexOf( "^" )) != -1 )
			return Math.pow( calculer( expr.substring( 0, index ) ), calculer( expr.substring( index + 1, expr.length() ) ) );

		System.out.println("out:"+expr);
		return Double.parseDouble( expr );
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
				 .replaceAll(" *\\^ *","^");
	}

	public static void main(String[] args) 
	{/*
		System.out.println( calculer( "5 × 4 + 3" ) + " = 23 ?" );
		System.out.println( calculer( "5×4+3" ) + " = 23 ?" );
		System.out.println( calculer( "+21" ) + " = 21 ?");
		System.out.println( calculer( "-21" ) + " = -21 ?");
		System.out.println( calculer( "20 / 8" ) + " = 2.5 ?" );
		System.out.println( calculer( "20 div 8" ) + " = 2 ?" );
		System.out.println( calculer( "13 mod 5" ) + " = 3 ?" );
		System.out.println( calculer( "5 ^ 2 + 3 × 10" ) + " = 55 ?" );*/
		System.out.println( calculer( "((45-(2))+5)" ) + " = 30?" );
		System.out.println( calculer( "5-\\/¯(25)+5"));
		System.out.println( calculer( "|5|"));

	}
}