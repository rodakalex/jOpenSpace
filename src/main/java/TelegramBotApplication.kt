import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import java.io.File


fun main() {

    ApiContextInitializer.init()
    TelegramBotsApi().registerBot(Bot())

}

class Bot : TelegramLongPollingBot() {
    private var parser: Parser = Parser.default()
    private var stringBuilder: StringBuilder = StringBuilder(File("./package.json").readText())
    private var json: JsonObject = parser.parse(stringBuilder) as JsonObject

    override fun getBotToken(): String {
        return "1357410487:AAG3jRz52vIIaARRdwr-4IShYqHCfTI1ZC8"
    }

    override fun onUpdateReceived(p0: Update?) {

        when (p0!!.message.text) {
            "/start" -> {
            execute(SendMessage()
                    .setChatId(p0.message.chatId)
                    .setText(json.string("greeting"))

            )
        }
        "Вернуться" ->

            execute(SendMessage()
                    .setReplyMarkup(ReplyKeyboardRemove())
                    .setChatId(p0.message.chatId)
                    .setText(json.string("comeback"))
            )

        else ->
            try {
                val keyboard = ReplyKeyboardMarkup()

                keyboard.keyboard = listOf(
                        KeyboardRow().apply {
                            add(KeyboardButton("Первый вопрос"))
                            add(KeyboardButton("Второй вопрос"))
                        },
                        KeyboardRow().apply{
                            add(KeyboardButton("Вернуться"))
                        }
                )

                keyboard.resizeKeyboard = true

                execute(SendMessage()
                        .setReplyMarkup(keyboard)
                        .setChatId(p0.message.chatId)
                        .setText(json.string("ifelse")
                                ?.replace("***", "\n1. Первый вопрос\n2. Второй вопрос\n")))
            } catch (e: Exception) {
                execute(SendMessage()
                        .setChatId(p0.message.chatId)
                        .setText(json.string("error")))
            }
        }
    }

    override fun getBotUsername(): String {
        return "test_osph_bot"
    }
}