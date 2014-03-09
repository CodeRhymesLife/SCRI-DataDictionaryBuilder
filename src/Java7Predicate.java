
/**
 * Java 7 version to the Java 8 Predicate interface.
 * @author Ryan
 *
 * @param <T> Type of object to test
 */
public interface Java7Predicate<T> {
	/**
	 * Tests whether the given object meets a condition
	 * @param t
	 * @return True if t meets the condition. False otherwise.
	 */
	boolean test(T t);
}
