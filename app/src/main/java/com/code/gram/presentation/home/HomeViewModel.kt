package com.code.gram.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.gram.presentation.home.model.FeedModel
import com.wakaztahir.codeeditor.highlight.model.CodeLang
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {
    private val _state : MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state : StateFlow<HomeState> = _state.asStateFlow()

    private val dummyData = listOf(
        FeedModel(
            id = 1,
            title = "Hello World",
            code = "print('Hello World')",
            language = CodeLang.Python,
            tags = listOf("Python", "Programming", "Tutorial").toPersistentList()
        ),
        FeedModel(
            id = 2,
            title = "Hello World",
            code =  """
            package com.example;

            import java.util.List;

            public class HelloWorld {
                /**
                 * Main method.
                 * @param args Command line arguments.
                 */
                public static void main(String[] args) {
                    // Prints "Hello, World!" to the console.
                    String message = "Hello, World!";
                    System.out.println(message);
                    int number = 100;
                }
            }
        """,
            language = CodeLang.Java,
            tags = listOf("Java", "Programming", "Tutorial").toPersistentList()
        ),
        FeedModel(
            id = 3,
            title = "Hello World",
            code = "print('Hello World')",
            language = CodeLang.C,
        ),
        FeedModel(
            id = 4,
            title = "Hello World",
            code = "print('Hello World')",
            language = CodeLang.CPP,
        ),
        FeedModel(
            id = 5,
            title = "Hello World",
            code = "print('Hello World')",
            language = CodeLang.CSS,
            tags = listOf("CSS", "Programming", "Tutorial").toPersistentList()
        ),
        FeedModel(
            id = 6,
            title = "Hello World",
            code = "print('Hello World')",
            language = CodeLang.JavaScript,
        ),
        FeedModel(
            id = 7,
            title = "Hello World",
            code = "print('Hello World')",
            language = CodeLang.JSON,
            tags = listOf("JSON", "Programming", "Tutorial").toPersistentList()
        ),
        FeedModel(
            id = 8,
            title = "Hello World",
            code = "print('Hello World')",
            language = CodeLang.Dart,
        ),

    )

    init {
        initFakeData()
    }

    fun initFakeData() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    fakeItem = dummyData.toImmutableList()
                )
            }
        }
    }
}