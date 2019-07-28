package tasks

import contributors.*
import java.util.concurrent.atomic.AtomicInteger

suspend fun loadContributorsProgress(
    service: GitHubService,
    req: RequestData,
    updateResults: suspend (List<User>, completed: Boolean) -> Unit
) {
    val repos = service
        .getOrgRepos(req.org)
        .also { logRepos(req, it) }
        .bodyList()

    val contributors = mutableListOf<User>()

    repos.forEachIndexed { i,repo ->
        contributors += service
            .getRepoContributors(req.org, repo.name)
            .also { logUsers(repo, it) }
            .bodyList()
        updateResults(
            contributors.aggregate(),
            i == repos.lastIndex)
    }
}
