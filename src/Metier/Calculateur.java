package AlgoPars.Metier;


public class Calculateur
{
	public static double calculer( String expr )
	{
		if (expr.isEmpty()) {
			return 0.0 ;
		}
		/*
		if ( (index = expr.indexOf( "|" )) != -1 )
			{System.out.println(expr);
		return calculer( expr.substring( index + 1, expr.indexOf( "|", index + 1 ) ) );} */

		int index = 0;
/*
		if (expr.matches("\\|.+\\|")) {
			index = expr.indexOf( "|" );

			System.out.println(expr.substring( index + 1, expr.indexOf( "|", index + 1 ) ));
			System.out.println(expr.substring( expr.indexOf( "|", index + 1 )));


	return calculer(
					String.valueOf(           calculer( expr.substring(         0,expr.indexOf( "|"))))             +
					String.valueOf( Math.abs( calculer( expr.substring( index + 1,expr.indexOf( "|", index + 1 )))))+ 
					String.valueOf(			  calculer( expr.substring( expr.indexOf( "|"+1))))
					);
		}*/

		/*if (expr.matches("\w+(\w*)"))
			primitives.find(la foncion)
		*/


		// Opérateurs unaires.
		if ( (index = expr.indexOf( "+" )) != -1 )
		{
			if ( expr.charAt( 0 ) != '+' )
				return calculer( expr.substring( 0, index ) ) + calculer( expr.substring( index + 1, expr.length() ) );
			else
				return +calculer( expr.substring( 1, expr.length() ) );
		}
		if ( (index = expr.indexOf( "-" )) != -1 )
		{
			if ( expr.charAt( 0 ) != '-' )
				return Math.abs( calculer( expr.substring( 0, index ) ) + calculer( expr.substring( index + 1, expr.length() ) ) );
			else
				return -calculer( expr.substring( 1, expr.length() ) );
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

		return Double.parseDouble( expr );
	}

	public static void main(String[] args) 
	{
		System.out.println( calculer( "5 × 4 + 3" ) + " = 23 ?" );
		System.out.println( calculer( "5×4+3" ) + " = 23 ?" );
		System.out.println( calculer( "+21" ) + " = 21 ?");
		System.out.println( calculer( "-21" ) + " = -21 ?");
		System.out.println( calculer( "20 / 8" ) + " = 2.5 ?" );
		System.out.println( calculer( "20 div 8" ) + " = 2 ?" );
		System.out.println( calculer( "13 mod 5" ) + " = 3 ?" );
		System.out.println( calculer( "5 ^ 2 + 3 × 10" ) + " = 55 ?" );
		System.out.println( calculer( "-5 ^ 2 - 3 × 10" ) + " = 55 ?" );

	}
}