package com.example.gitaplication.mocks

import com.example.gitaplication.models.Repo
import com.example.gitaplication.models.User
import com.example.gitaplication.models.UserList

val testingUser = User(
    "test",
    "test_avatar",
    "test_bio",
    "test_location",
    0,
    0,
    0
)

val testingRepos = listOf(
    Repo("repo_1", "descr", 0, 0),
    Repo("repo_2", "descr", 0, 0),
    Repo("repo_4", "descr", 1, 2),
    Repo("repo_3", "descr", 2, 2),
    Repo("repo_5", "descr", 0, 4)
)



val testingReposSorted = listOf(
    Repo("repo_5", "descr", 0, 4),
    Repo("repo_3", "descr", 2, 2),
    Repo("repo_4", "descr", 1, 2),
    Repo("repo_1", "descr", 0, 0),
    Repo("repo_2", "descr", 0, 0)
)

val testingUserListItems = listOf(
    UserList("user_1", "user_1_avatar"),
    UserList("user_2", "user_2_avatar")
)