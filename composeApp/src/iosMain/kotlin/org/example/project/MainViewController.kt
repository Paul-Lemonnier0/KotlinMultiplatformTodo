import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import io.ktor.client.engine.darwin.Darwin
import org.example.project.App
import org.example.project.createHttpClient
import org.example.project.models.TodoListClient

fun MainViewController() = ComposeUIViewController {
    App(
        client = remember { TodoListClient(createHttpClient(Darwin.create())) }
) }