import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow


class TelegramBotApplication

fun main(args: Array<String>) {
    ApiContextInitializer.init()
    TelegramBotsApi().registerBot(Bot())
}

class Bot : TelegramLongPollingBot() {
    override fun getBotToken(): String {
        return "1357410487:AAG3jRz52vIIaARRdwr-4IShYqHCfTI1ZC8"
    }

    override fun onUpdateReceived(p0: Update?) {
        if (p0!!.message.text == "/start") {
            val keyboard = ReplyKeyboardMarkup()
            val row = KeyboardRow()
            keyboard.resizeKeyboard = true

            row.add("Задать вопрос")
            row.add("Попрощаться")

            keyboard.keyboard.add(row)

            execute(SendMessage()
                    .setChatId(p0.message.chatId)
                    .setReplyMarkup(keyboard)
                    .setText("Приветствие и объяснение функционала действий бота")
            )
        }
        else if (p0!!.message.text == "Задать вопрос") {
            val keyboard = ReplyKeyboardMarkup()
            val row = KeyboardRow()
            row.add("Меню")

            keyboard.keyboard.add(row)
            keyboard.resizeKeyboard = true

            val msg: SendMessage = SendMessage()
                    .setChatId(p0.message.chatId)
                    .setText("Задавайте вопрос")
                    .setReplyMarkup(keyboard)

            execute(msg)
        }

    }

    override fun getBotUsername(): String {
        return "test_osph_bot"
    }
}