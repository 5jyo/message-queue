import com.example.messagequeue.core.OffsetManager
import com.example.messagequeue.core.TopicManager
import com.example.messagequeue.model.Event
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito

class TopicManagerTest {

    private val offsetManager = Mockito.mock(OffsetManager::class.java)
    private val topicManager = TopicManager(offsetManager)

    @Test
    fun `produce should add event to existing topic`() {
        val event = Event.create("topic1", "data")
        topicManager.addTopic("topic1")

        topicManager.produce(event)

        assertEquals(1, topicManager.topicSize("topic1"))
    }

    @Test
    fun `produce should throw exception when adding event to non-existent topic`() {
        val event = Event.create("nonExistentTopic", "data")

        assertThrows<IllegalArgumentException> {
            topicManager.produce(event)
        }
    }

    @Test
    fun `consume should return event at current offset`() {
        val event = Event.create("topic1", "data")
        topicManager.addTopic("topic1")
        topicManager.produce(event)
        Mockito.`when`(offsetManager.getOffset("consumer1", "topic1")).thenReturn(0)

        val consumedEvent = topicManager.consume("topic1", "consumer1")

        assertEquals(event, consumedEvent)
    }

    @Test
    fun `consume should throw exception when consuming from non-existent topic`() {
        assertThrows<IllegalArgumentException> {
            topicManager.consume("nonExistentTopic", "consumer1")
        }
    }

    @Test
    fun `consume should throw exception when no more messages to consume`() {
        topicManager.addTopic("topic1")
        Mockito.`when`(offsetManager.getOffset("consumer1", "topic1")).thenReturn(0)

        assertThrows<IllegalStateException> {
            topicManager.consume("topic1", "consumer1")
        }
    }

    @Test
    fun `consume should increment offset after successful consumption`() {
        val event1 = Event.create("topic1", "data1")
        val event2 = Event.create("topic1", "data2")
        topicManager.addTopic("topic1")
        topicManager.produce(event1)
        topicManager.produce(event2)
        Mockito.`when`(offsetManager.getOffset("consumer1", "topic1")).thenReturn(0, 1)

        val consumedEvent1 = topicManager.consume("topic1", "consumer1")
        val consumedEvent2 = topicManager.consume("topic1", "consumer1")

        assertEquals(event1, consumedEvent1)
        assertEquals(event2, consumedEvent2)
    }
}