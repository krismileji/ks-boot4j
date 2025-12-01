package host.springboot.framework3.core.model;

/**
 * Pair
 *
 * @param left  Left object
 * @param right Right object
 * @param <L>   Left object type
 * @param <R>   Right object type
 * @author JiYinchuan
 * @since 0.1.0
 */
public record Pair<L, R>(L left, R right) {

    /**
     * <p>Creates an immutable pair of two objects inferring the generic types.</p>
     *
     * <p>This factory allows the pair to be created using inference to
     * obtain the generic types.</p>
     *
     * @param <L>   the left element type
     * @param <R>   the right element type
     * @param left  the left element, may be null
     * @param right the right element, may be null
     * @return a pair formed from the two parameters, not null
     */
    public static <L, R> Pair<L, R> of(final L left, final R right) {
        return new Pair<>(left, right);
    }
}
