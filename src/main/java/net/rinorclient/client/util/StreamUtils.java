package net.rinorclient.client.util;

import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Stream;

public class StreamUtils {
    public static <T, U extends Comparable<? super U>> Stream<T> sortCached(Stream<T> stream, Function<? super T, ? extends U> keyExtractor) {
        return stream
                .map(t -> {
                    U key = keyExtractor.apply(t);
                    return new Intermediary<>(t, key);
                })
                .sorted(Comparator.comparing(Intermediary::key))
                .map(Intermediary::value);
    }

    private record Intermediary<T, U extends Comparable<? super U>>(T value, U key) {}
}