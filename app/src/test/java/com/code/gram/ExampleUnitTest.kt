package com.code.gram

import com.code.gram.domain.entity.home.CodeResponseEntity
import com.code.gram.domain.repository.HomeRepository
import com.code.gram.domain.repository.WebSocketRepository
import com.code.gram.presentation.home.HomeViewModel
import com.code.gram.presentation.home.model.SortType
import com.example.makersassignment.core.common.util.UiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var homeRepository: HomeRepository
    private lateinit var webSocketRepository: WebSocketRepository
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        homeRepository = mockk()
        webSocketRepository = mockk(relaxed = true)
        viewModel = HomeViewModel(homeRepository, webSocketRepository)
    }

    @Test
    fun `fetchData updates state with posts`() = runTest {
        // given
        val feedList = listOf(
            CodeResponseEntity(
                id = "1",
                code = "print('Hi')",
                language = "python",
                authorNickname = "test",
                authorProfileImageUrl = "",
                comments = emptyList(),
                createdAt = "",
                likesCount = 0,
                authorId = "",
                title = "",
                description = "",
                tags = emptyList(),
                vibeEmojis = emptyList()
            ),
            CodeResponseEntity(
                id = "1",
                code = "print('Hi')",
                language = "python",
                authorNickname = "test",
                authorProfileImageUrl = "",
                comments = emptyList(),
                createdAt = "",
                likesCount = 0,
                authorId = "",
                title = "",
                description = "",
                tags = emptyList(),
                vibeEmojis = emptyList()
            ),
            CodeResponseEntity(
                id = "1",
                code = "print('Hi')",
                language = "python",
                authorNickname = "test",
                authorProfileImageUrl = "",
                comments = emptyList(),
                createdAt = "",
                likesCount = 0,
                authorId = "",
                title = "",
                description = "",
                tags = emptyList(),
                vibeEmojis = emptyList()
            ),
            CodeResponseEntity(
                id = "1",
                code = "print('Hi')",
                language = "python",
                authorNickname = "test",
                authorProfileImageUrl = "",
                comments = emptyList(),
                createdAt = "",
                likesCount = 0,
                authorId = "",
                title = "",
                description = "",
                tags = emptyList(),
                vibeEmojis = emptyList()
            ),
            CodeResponseEntity(
                id = "1",
                code = "print('Hi')",
                language = "python",
                authorNickname = "test",
                authorProfileImageUrl = "",
                comments = emptyList(),
                createdAt = "",
                likesCount = 0,
                authorId = "",
                title = "",
                description = "",
                tags = emptyList(),
                vibeEmojis = emptyList()
            ),
            CodeResponseEntity(
                id = "1",
                code = "print('Hi')",
                language = "python",
                authorNickname = "test",
                authorProfileImageUrl = "",
                comments = emptyList(),
                createdAt = "",
                likesCount = 0,
                authorId = "",
                title = "",
                description = "",
                tags = emptyList(),
                vibeEmojis = emptyList()
            ),
            CodeResponseEntity(
                id = "1",
                code = "print('Hi')",
                language = "python",
                authorNickname = "test",
                authorProfileImageUrl = "",
                comments = emptyList(),
                createdAt = "",
                likesCount = 0,
                authorId = "",
                title = "",
                description = "",
                tags = emptyList(),
                vibeEmojis = emptyList()
            ),
            CodeResponseEntity(
                id = "1",
                code = "print('Hi')",
                language = "python",
                authorNickname = "test",
                authorProfileImageUrl = "",
                comments = emptyList(),
                createdAt = "",
                likesCount = 0,
                authorId = "",
                title = "",
                description = "",
                tags = emptyList(),
                vibeEmojis = emptyList()
            ),
            CodeResponseEntity(
                id = "1",
                code = "print('Hi')",
                language = "python",
                authorNickname = "test",
                authorProfileImageUrl = "",
                comments = emptyList(),
                createdAt = "",
                likesCount = 0,
                authorId = "",
                title = "",
                description = "",
                tags = emptyList(),
                vibeEmojis = emptyList()
            ),
            CodeResponseEntity(
                id = "1",
                code = "print('Hi')",
                language = "python",
                authorNickname = "test",
                authorProfileImageUrl = "",
                comments = emptyList(),
                createdAt = "",
                likesCount = 0,
                authorId = "",
                title = "",
                description = "",
                tags = emptyList(),
                vibeEmojis = emptyList()
            ),
        )
        coEvery { homeRepository.getPosts(any(), any()) } returns Result.success(feedList)

        // when
        viewModel.fetchData()
        advanceUntilIdle() // 모든 코루틴 완료 대기

        val state = viewModel.state.value
        assert(state.feedItem is UiState.Success)
        assertEquals(feedList.size, (state.feedItem as UiState.Success).data.size)

        coVerify { homeRepository.getPosts(0) }
    }

    @Test
    fun `updateSortType updates sortType`() = runTest {
        viewModel.updateSortType(SortType.Latest)
        val state = viewModel.state.first()
        assertEquals(SortType.Latest, state.sortType)
    }
}
