/**
 * Copyright (c) 2025 Sami Menik, PhD. All rights reserved.
 * 
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * This software is provided "as is," without warranty of any kind.
 */
package cs2725.impl.df;

import cs2725.api.List;
import cs2725.api.Map;
import cs2725.api.df.Series;
import cs2725.api.df.SeriesGroupBy;
import cs2725.api.functional.AggregateFunction;
import cs2725.impl.ArrayList;
import cs2725.impl.HashMap;
import cs2725.impl.ImmutableList;

/**
 * Implementation of SeriesGroupBy.
 *
 * @param <T> the type of elements in the original Series
 */
public class SeriesGroupByImpl<T> implements SeriesGroupBy<T> {

    /*
     * The original Series that is being grouped.
     */
    private final Series<T> series;

    /*
     * A map from unique group values to lists of indices. Each key is a unique
     * group value, and the associated list contains the position indices of items
     * belonging to that group.
     */
    private final Map<T, List<Integer>> groups;

    /*
     * A list of unique values from the group-by operation. This maintains the group
     * order of the groups.
     */
    private final List<T> index;

    /**
     * Constructs a grouped Series.
     *
     * @param series the original Series
     * @throws IllegalArgumentException if series is null
     */
    public SeriesGroupByImpl(Series<T> series) {
        if (series == null) {
            throw new IllegalArgumentException("Series cannot be null.");
        }

        // The 'series' is the original ungrouped Series.
        this.series = series;

        // 'groups' maps each unique value in the Series to the list of index positions
        // where that value appears. For example, if series = [a, b, a], then:
        // groups = { a → [0, 2], b → [1] }
        this.groups = groupByValues();

        // 'index' holds the list of unique values in the Series (group labels),
        // and defines the group ordering in aggregate output.
        this.index = buildIndex();
    }

    /**
     * Groups elements by their values, creating a map from unique values to index
     * positions.
     *
     * @return A map where each unique value from the series is a key, and the
     *         associated value is a list of indices indicating the positions in the
     *         series where this unique value appears.
     */
    private Map<T, List<Integer>> groupByValues() {
        // Todo: Project 2: To be implemented.
        throw new UnsupportedOperationException("To be implemented...");

        // Create a map where each key is a unique value from the Series.
        // The value for each key is a list of integer positions (indices)
        // indicating where that value occurs in the Series.

        // Example:
        // If the Series is ["cat", "dog", "cat", "bird"], then:
        // - "cat" appears at indices 0 and 2 → "cat" → [0, 2]
        // - "dog" appears at index 1 → "dog" → [1]
        // - "bird" appears at index 3 → "bird" → [3]
        //
        // Resulting map:
        // {
        // "cat" -> [0, 2],
        // "dog" -> [1],
        // "bird" -> [3]
        // }
    }

    /**
     * Builds the index of unique group values.
     *
     * @return a list of unique values from the group-by operation
     */
    private List<T> buildIndex() {
        List<T> uniqueValues = new ArrayList<>(groups.size());
        for (T key : groups.keySet()) {
            uniqueValues.insertItem(key);
        }
        return uniqueValues;
    }

    @Override
    public Series<T> series() {
        return series;
    }

    @Override
    public Map<T, List<Integer>> groups() {
        // Return a copy to ensure immutability.
        // Note: Map.copy does not copy the values.
        Map<T, List<Integer>> copy = new HashMap<>(groups.size(), 0.75);
        for (T key : groups.keySet()) {
            List<Integer> indicesImmutable = ImmutableList.of(groups.get(key));
            copy.put(key, indicesImmutable);
        }
        return copy;
    }

    @Override
    public List<T> index() {
        List<T> indexImmutable = ImmutableList.of(index);
        return indexImmutable;
    }

    @Override
    public <R> Series<R> aggregate(AggregateFunction<T, R> aggregator) {
        // Todo: Project 2: To be implemented.
        throw new UnsupportedOperationException("To be implemented...");

        // Applies the given aggregate function to each group in the grouped Series.
        //
        // For each group key in the index list:
        // - Retrieve the list of index positions for that key from the groups map.
        // - Use those indices to construct a sub-Series from the original Series.
        // - Apply the aggregator to the sub-Series.
        // - Store the result in the order of the index list.
        //
        // The result is a new Series of the same length as the number of groups.
        // Its values are the results of the aggregator, and it uses a default index.
        //
        // Example (using a count aggregator):
        // Original Series: ["apple", "banana", "apple", "banana", "apple"]
        // Grouping:
        // "apple" → [0, 2, 4] → Series(["apple", "apple", "apple"]) → count = 3
        // "banana" → [1, 3] → Series(["banana", "banana"]) → count = 2
        // Aggregated result: Series([3, 2])
        //
        // If the aggregator is null, throw IllegalArgumentException.
    }
}
