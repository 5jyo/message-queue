import com.example.messagequeue.core.OffsetManager
import com.example.messagequeue.core.OffsetRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class OffsetManagerTest {
    @InjectMockKs
    private lateinit var offsetManager: OffsetManager

    @MockK
    private lateinit var offsetRepository: OffsetRepository

    @Test
    fun `increment should increase offset`() {
        val consumerId = "consumer1"
        val topicId = "topic1"
        val initialOffset = 0
        every { offsetRepository.increment(Pair(consumerId, topicId)) } returns (initialOffset + 1)

        val incrementedOffset = offsetManager.increment(consumerId, topicId)

        assertEquals(initialOffset + 1, incrementedOffset)
        verify { offsetRepository.increment(Pair(consumerId, topicId)) }
    }

    @Test
    fun `getOffset should return zero for new key`() {
        val consumerId = "newConsumer"
        val topicId = "newTopic"
        every { offsetRepository.create(Pair(consumerId, topicId)) } returns (0)

        val offset = offsetManager.getOffset(consumerId, topicId)

        assertEquals(0, offset)
        verify { offsetRepository.create(Pair(consumerId, topicId)) }
    }

    @Test
    fun `getOffset should return current offset for existing key`() {
        val consumerId = "consumer1"
        val topicId = "topic1"
        val initialOffset = 0
        every { offsetRepository.increment(Pair(consumerId, topicId)) } returns (initialOffset + 1)
        every { offsetRepository.get(Pair(consumerId, topicId)) } returns (initialOffset + 1)

        val expectedOffset = offsetManager.increment(consumerId, topicId)
        val actualOffset = offsetManager.getOffset(consumerId, topicId)

        assertEquals(expectedOffset, actualOffset)
        verify { offsetRepository.increment(Pair(consumerId, topicId)) }
        verify { offsetRepository.get(Pair(consumerId, topicId)) }
    }
}
