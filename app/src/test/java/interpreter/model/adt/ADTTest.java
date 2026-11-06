package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ADTTest {
    @Test
    void testStackThrowsOnEmpty() {
        IStack<Integer> stack = new MyStack<>();
        stack.push(10);
        assertDoesNotThrow(stack::pop);
        assertThrows(ADTException.class, stack::pop);
    }

    @Test
    void testDictionaryThrowsOnMissingKey() {
        IDictionary<String, Integer> dict = new MyDictionary<>();
        dict.put("a", 1);
        assertDoesNotThrow(() -> dict.lookup("a"));
        assertThrows(ADTException.class, () -> dict.lookup("b"));
    }
}