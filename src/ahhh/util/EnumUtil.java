package ahhh.util;

public class EnumUtil {

	/**
	 * List all enum constants which are in the specified enum class.
	 *
	 * @param enumType the enum class.
	 * @param separator a string which will be used as a separator
	 * @return a string representation of the enum class.
	 */
	public static <T extends Enum<T>> String enumList(Class<T> enumType, String separator) {
		T[] elements = enumType.getEnumConstants();

		String out = "";
		boolean first = true;
		for (T el : elements) {
			if (!first) out += separator;
			first = false;
			out += el.name();

		}
		return out;
	}

	/**
	 * List all enum constants which are in the specified enum class. It is
	 * equivalent to call
	 * {@link #enumList(Class, String)} with the second parameter
	 * <code>", "</code>
	 *
	 * @param enumType the enum class.
	 * @return a string representation of the enum class.
	 */
	public static <T extends Enum<T>> String enumList(Class<T> enumType) {
		return enumList(enumType, ", ");
	}

	/**
	 * Permet de rechercher l'existance d'un élément dans un enum, de façon
	 * insensible à la casse
	 *
	 * @param enumType la classe correpondant à l'enum à lister
	 * @param search l'élément à rechercher, insensible à la casse
	 * @return l'élément de l'énumarétion, si elle a été trouvée, null sinon
	 */
	public static <T extends Enum<T>> T searchEnum(Class<T> enumType, String search) {
		T[] elements = enumType.getEnumConstants();

		for (T el : elements)
			if (el.name().equalsIgnoreCase(search)) return el;
		return null;
	}

	/**
	 * Permet de rechercher l'existance d'un élément dans un enum, de façon
	 * insensible à la casse
	 * La validité de la classe passé en premier paramètre est vérifiée
	 * dynamiquement et non
	 * statiquement. Préférez l'utilisation de
	 * {@link #searchEnum(Class, String)} quand c'est possible.
	 *
	 * @param enumType la classe correpondant à l'enum à lister
	 * @param search l'élément à rechercher, insensible à la casse
	 * @return l'élément de l'énumération, si elle a été trouvée et si la classe
	 *         passée en paramètre est un enum, null dans les autres cas
	 */
	public static Enum<?> searchUncheckedEnum(Class<?> enumType, String search) {
		if (!enumType.isEnum()) return null;
		Enum<?>[] elements = (Enum<?>[]) enumType.getEnumConstants();

		for (Enum<?> el : elements)
			if (el.name().equalsIgnoreCase(search)) return el;
		return null;
	}

	/**
	 * Retourne une valeur aléatoire parmis les élément de l'Enum spécifié en
	 * paramètre.
	 *
	 * @param enumType l'enum dans lequel piocher la valeur
	 * @return une des valeurs de l'enum
	 */
	public static <T extends Enum<T>> T randomValue(Class<T> enumType) {
		T[] elements = enumType.getEnumConstants();

		return elements[RandomUtil.rand.nextInt(elements.length)];
	}

}
