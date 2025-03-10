package tasks

import contributors.User
import java.util.Comparator
import java.util.Comparator.comparing

/*
TODO: Write aggregation code.

 In the initial list each user is present several times, once for each
 repository he or she contributed to.
 Merge duplications: each user should be present only once in the resulting list
 with the total value of contributions for all the repositories.
 Users should be sorted in a descending order by their contributions.

 The corresponding test can be found in test/tasks/AggregationKtTest.kt.
 You can use 'Navigate | Test' menu action (note the shortcut) to navigate to the test.
*/
fun List<User>.aggregate(): List<User> =
    groupingBy { it.login }
        .aggregate { _, accumulator:User?, element, _ ->
            element.copy(contributions = element.contributions+(accumulator?.contributions?:0))
        }
        .values
        .sortedWith(compareByDescending<User> {it.contributions}
        .thenByDescending {it.login})
