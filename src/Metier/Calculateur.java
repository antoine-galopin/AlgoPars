package AlgoPars.Metier;

import java.util.ArrayList;
import AlgoPars.Metier.Types.*;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Calculateur {
	/**
	 * Méthode générale de calcul qui appelle ensuite les méthodes appropriées aux types utilisés
	 * @param expression
	 * @return résultat
	 */
	public static String calculer(String expression) {
		if (expression.isEmpty() || expression.isBlank()) return "";

		switch (getType(expression)) {
			case "chaine":
				return "\"" + calculerChaine(expression) + "\"";
			case "caractere":
				return expression;
			case "booleen":
				switch (Double.toString(calculerMath(expression))) {
					case "0.0":
						return "faux";
					case "1.0":
						return "vrai";
					default:
						throw new RuntimeException(
							"cannot implicit convert to Boolean:" + expression + "->" + calculerMath(expression)
						);
				}
			case "reel":
				return String.valueOf(calculerMath(expression));
			case "entier":
				return String.valueOf((int) calculerMath(expression));
			// si c'est un tableau pas d'operations
			default:
				return calculerTableau(expression);
		}
	}

	/**
	 * Méthode de calcul liée aux expressions arythmétiques
	 * @param expr
	 * @return résultat sous forme de double ( pour assurer les calculs pour les Doubles et les Entiers )
	 */
	private static double calculerMath(String expr) {
		expr = Calculateur.nettoyer(expr); // nettoyage de l'expression

		int index = 0;

		// traitement des parenthèses
		if ((index = expr.indexOf("(")) != -1) {
			return calculerMath(
					groupeParenthese(expr, index)[0] +
							String.valueOf(calculerMath(groupeParenthese(expr, index)[1])) +
							groupeParenthese(expr, index)[2]);
		}

		// traitement des valeurs absolues ( ordre de priorité similaire aux parenthèses
		// )
		if ((index = expr.indexOf("|")) != -1) { // s'il y a un pipe
			int index2 = Calculateur.trouverDeuxiemePipe(expr, index  + 1);
			index      = Calculateur.trouverPremierePipe(expr, index2 - 1);

			String premierePartie = expr.substring(0         , index);
			String milieu         = expr.substring(index + 1 , index2);
			String deuxiemePartie = expr.substring(index2 + 1        );

			return calculerMath(premierePartie + String.valueOf(Math.abs(calculerMath(milieu))) + deuxiemePartie);
		}

		
		// Opérateurs unaires
		char[] tabCar = new char[] { '+', '-' };
		int[]  tabOpe = new int [] { 1  , -1  };

		for (int i = 0; i < tabCar.length; i++) {
			if ((index = expr.indexOf(tabCar[i])) != -1) {
				if (expr.charAt(0) != tabCar[i])
					return calculerMath(expr.substring(0, index))
							+ tabOpe[i] * calculerMath(expr.substring(index + 1, expr.length()));
				else
					return tabOpe[i] * calculerMath(expr.substring(1, expr.length()));
			}
		}

		// traitement des racines carrées ( on considère que tout est dans la racine
		// jusqu'au prochain symbole )
		if ((index = expr.indexOf("\\/¯")) != -1) {
			int index2 = index + 3;

			while (expr.charAt(index2) != '+' &&
					expr.charAt(index2) != '-' &&
					expr.charAt(index2) != '(' &&
					expr.charAt(index2) != '×' &&
					expr.charAt(index2) != '/' &&
					expr.charAt(index2) != '^' &&
					expr.charAt(index2) != ' ' && // c'est un modulo ou un div
					index2 < expr.length() - 1) {
				index2++;
			}

			String premierePartie = expr.substring(0, index);
			String milieu = expr.substring(index + 3, index2);
			String deuxiemePartie = expr.substring(index2);

			return calculerMath(premierePartie + String.valueOf(Math.sqrt(calculerMath(milieu))) + deuxiemePartie);
		}

		// Opérateurs binaires
		if ((index = expr.indexOf("×")) != -1)
			return calculerMath(expr.substring(0, index)) * calculerMath(expr.substring(index + 1, expr.length()));

		if ((index = expr.indexOf("/")) != -1)
			return calculerMath(expr.substring(0, index)) / calculerMath(expr.substring(index + 1, expr.length()));

		if ((index = expr.indexOf("div")) != -1)
			return (int) (calculerMath(expr.substring(0, index))
					/ calculerMath(expr.substring(index + 3, expr.length())));

		if ((index = expr.indexOf("mod")) != -1)
			return calculerMath(expr.substring(0, index)) % calculerMath(expr.substring(index + 3, expr.length()));

		if ((index = expr.indexOf("^")) != -1)
			return Math.pow(calculerMath(expr.substring(0, index)),
					calculerMath(expr.substring(index + 1, expr.length())));

		/*-----------------------------booleenne--------------------------------*/

		// faux = 0
		// vrai = 1
		if ((index = expr.indexOf(" xou ")) != -1)
			return (calculerMath(expr.substring(0, index)) + calculerMath(expr.substring(index + 4))) % 2;

		if ((index = expr.indexOf(" ou ")) != -1)
			return calculerMath(expr.substring(0, index)) + calculerMath(expr.substring(index + 4));

		if ((index = expr.indexOf(" et ")) != -1)
			return calculerMath(expr.substring(0, index)) * calculerMath(expr.substring(index + 4));

		if ((index = expr.indexOf("=")) != -1)
			return (Math
					.abs((calculerMath(expr.substring(0, index))
							- calculerMath(expr.substring(index + 1)))/* - 1 */) >= 1)
									? 0
									: 1;

		if ((index = expr.indexOf("/=")) != -1)
			return calculerMath(expr.substring(0, index)) - calculerMath(expr.substring(index + 2));

		if ((index = expr.indexOf(" non ")) != -1)
			return Math.abs(calculerMath(expr.substring(index + 5)) - 1);

		// non remplassable
		if ((index = expr.indexOf(">=")) != -1)
			return (calculerMath(expr.substring(0, index)) >= calculerMath(expr.substring(index + 2))) ? 1 : 0;

		if ((index = expr.indexOf("<=")) != -1)
			return (calculerMath(expr.substring(0, index)) <= calculerMath(expr.substring(index + 2))) ? 1 : 0;

		if ((index = expr.indexOf("<")) != -1)
			return (calculerMath(expr.substring(0, index)) < calculerMath(expr.substring(index + 1))) ? 1 : 0;

		if ((index = expr.indexOf(">")) != -1)
			return (calculerMath(expr.substring(0, index)) > calculerMath(expr.substring(index + 1))) ? 1 : 0;

		return Double.parseDouble(expr);
	}

	/**
	 * 
	 * @param expr
	 * @return
	 */
	public static String calculerTableau(String expr) {
		return expr;
	}

	/**
	 * Méthode de calcul liée aux chaines et caractères
	 * @param expr
	 * @return
	 */
	public static String calculerChaine(String expr) {
		String retour = "";

		Pattern pattern = Pattern.compile("( */= *)(?=[\"'].*[\"'])");
		Matcher matcher = pattern.matcher(expr);

		// si c'est un =
		if (matcher.find()) {
			String[] tab = expr.split("( */= *)(?=[\"'].*[\"'])");

			retour = (!calculerChaine(tab[0]).equals(calculerChaine(tab[1]))) ? "vrai" : "faux";

			return retour;
		} else {
			// si c'est un /=
			pattern = Pattern.compile("( *= *)(?=[\"'].*[\"'])");
			matcher = pattern.matcher(expr);

			if (matcher.find()) {
				String[] tab = expr.split("( *= *)(?=[\"'].*[\"'])");

				retour = (calculerChaine(tab[0]).equals(calculerChaine(tab[1]))) ? "vrai" : "faux";

				return retour;
			} else {
				pattern = Pattern.compile("(( *© *)|( *\\(c\\) *))(?=[\"'].*[\"'])");
				matcher = pattern.matcher(expr);

				if (matcher.find()) {
					for (String s : expr.split("(( *© *)|( *\\(c\\) *))(?=[\"'].*[\"'])")) {
						retour += traiterChaine(s);
						System.out.println(retour);
					}
					return retour;
				}
			}

		}

		return traiterChaine(expr);
	}

	/**
	 * Méthode qui supprime les espaces d'avant et d'après la chaine
	 * @param expr
	 * @return chaine sans espaces au début ni à la fin
	 */
	public static String traiterChaine(String expr) {
		expr = expr.replaceAll("^ *", "").replaceAll(" *$", "");

		if (expr.charAt(0) == '"' || expr.charAt(0) == '\'')
			return expr.substring(1, expr.length() - 1);

		return expr;
	}


	/**
	 * Méthode qui renvoit le groupe de parenthèse le plus profond dans l'expression
	 * passée en paramètre
	 * 
	 * @param expr
	 * @param index
	 * @return String[]
	 */
	public static String[] groupeParenthese(String expr, int index) {
		int premiereParenthese = index;
		int deuxiemeParenthese = expr.indexOf(")"); // fin du groupe le plus encapsulé

		while (expr.indexOf("(", premiereParenthese + 1) != -1) {
			premiereParenthese = expr.indexOf("(", premiereParenthese + 1); // recherche de la prochaine parenthèse
																			// ouvrante
		}

		return new String[] {
				expr.substring(0, premiereParenthese), // avant la parenthèse
				expr.substring(premiereParenthese + 1, deuxiemeParenthese), // dans la parenthèse
				expr.substring(deuxiemeParenthese + 1) // après la parenthèse
		};
	}

	/**
	 * Méthode qui renvoit l'indice de la pipe ouvrante la plus encapsuler d'un
	 * chaine passée en paramètre
	 * 
	 * @param s
	 * @param fin
	 * @return indice de a première pipe
	 */
	private static int trouverPremierePipe(String s, int fin) {
		s = s.substring(0, fin);

		int index = fin - 1;

		while (s.charAt(index) != '|')
			index--;

		return index;
	}

	/**
	 * Méthode qui renvoit l'indice de la pipe fermante la plus encapsulée d'un
	 * chaine passée en paramètre
	 * 
	 * @param s
	 * @param debut
	 * @return indice de la deuxieme pipe
	 */
	private static int trouverDeuxiemePipe(String s, int debut) {
		int index = s.indexOf("|", debut);

		while (!(Character.isDigit(s.charAt(index - 1)) && s.charAt(index) == '|')) {
			index++;
		}

		return index;
	}

	/**
	 * Fonction qui nettoie une chaine avant de la traiter plus simplement
	 * 
	 * @param s
	 * @return Chaine sans espaces superflus
	 */
	private static String nettoyer(String s) {
		return s.replaceAll(" *\\+ *", "+") // opérateurs
				.replaceAll(" *- *", "-")
				.replaceAll(" *× *", "×")
				.replaceAll(" *\\/ *", "/")
				.replaceAll(" *\\^ *", "^")
				.replaceAll(" +mod +", " mod ")
				.replaceAll(" +div +", " div ")
				.replaceAll(" +$", "")
				.replaceAll("^ +", "")
				.replaceAll(" *\\| *", "|")

				.replaceAll(" +ou +", " ou ") // comparateurs
				.replaceAll(" +xou +", " xou ")
				.replaceAll(" +et +", " et ")
				.replaceAll(" *non +", " non ")
				.replaceAll(" += +", " = ")
				.replaceAll(" +< +", " < ")
				.replaceAll(" +> +", " > ")
				.replaceAll(" +>= +", " >= ")
				.replaceAll(" +=> +", " >= ")
				.replaceAll(" *vrai *", " 1 ")
				.replaceAll(" *faux *", " 0 ");
	}

	/**
	 * Méthode renvoyant le type de l'expression passée en paramètre
	 * @param expression
	 * @return Type de la chaine
	 */
	public static String getType(String expression) {
		if (expression.replaceAll("^ *", "").charAt(0) == '{')
			return "tableau de " + getType(expression.substring(expression.indexOf("{") + 1));

		if (expression.startsWith("'")) return "caractere";

		if (expression.contains("(c)") ||
			expression.contains("©"  ) ||
			expression.contains("\"")) return "chaine";

		if (expression.contains("<"   ) ||
			expression.contains(">"   ) ||
			expression.contains("="   ) ||
			expression.contains("/="  ) ||
			expression.contains(" et ") ||
			expression.contains(" ou ") ||
			expression.contains("non" ) ||
			expression.contains("vrai") ||
			expression.contains("faux")) return "booleen";

		if (expression.contains(".")) return "reel";

		return "entier";
	}
}
