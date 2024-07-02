import com.example.messagequeue.core.OffsetManager
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OffsetManagerTest {

    private val offsetManager = OffsetManager()

    @Test
    fun `increment should increase offset by one`() {
        val consumerId = "consumer1"
        val topicId = "topic1"

        val initialOffset = offsetManager.getOffset(topicId, consumerId)
        offsetManager.increment(topicId, consumerId)
        val incrementedOffset = offsetManager.getOffset(topicId, consumerId)

        assertEquals(initialOffset + 1, incrementedOffset)
    }

    @Test
    fun `increment should throw exception for invalid key`() {
        val consumerId = "invalidConsumer"
        val topicId = "invalidTopic"

        assertThrows<RuntimeException> {
            offsetManager.increment(consumerId, topicId)
        }
    }

    @Test
    fun `getOffset should return zero for new key`() {
        val consumerId = "newConsumer"
        val topicId = "newTopic"

        val offset = offsetManager.getOffset(topicId, consumerId)

        assertEquals(0, offset)
    }

    @Test
    fun `getOffset should return current offset for existing key`() {
        val consumerId = "consumer1"
        val topicId = "topic1"
        offsetManager.getOffset(topicId, consumerId)

        val expectedOffset = offsetManager.increment(topicId, consumerId)
        val actualOffset = offsetManager.getOffset(topicId, consumerId)

        assertEquals(expectedOffset, actualOffset)
    }
}