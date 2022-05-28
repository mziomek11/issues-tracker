package com.mateuszziomek.cqrs.query.dispatcher;

import com.mateuszziomek.cqrs.query.Query;
import com.mateuszziomek.cqrs.query.QueryHandler;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class QueryDispatcherTest {
    @Test
    void queryCanHaveOnlyOneHandler() {
        // Arrange
        var firstHandler = new TestQueryHandler();
        var secondHandler = new TestQueryHandler();
        var sut = new QueryDispatcher();
        sut.registerHandler(TestQuery.class, firstHandler);

        // Assert
        assertThatExceptionOfType(TooManyQueryHandlersException.class)
                .isThrownBy(() -> sut.registerHandler(TestQuery.class, secondHandler));
    }

    @Test
    void notRegisteredQueryCanNotBeDispatched() {
        // Arrange
        var query = new TestQuery();
        var sut = new QueryDispatcher();

        // Assert
        assertThatExceptionOfType(QueryHandlerNotFoundException.class)
                .isThrownBy(() -> sut.dispatch(query));
    }

    @Test
    void queryIsPassedToRegisteredHandler() {
        // Arrange
        var query = new TestQuery();
        var handler = new TestQueryHandler();
        var sut = new QueryDispatcher();
        sut.registerHandler(TestQuery.class, handler);

        // Act
        var result = sut.dispatch(query);

        // Assert
        assertThat(result).isEqualTo("Example result");
    }

    private static class TestQuery extends Query<String> { }

    private static class TestQueryHandler implements QueryHandler<String, TestQuery> {
        @Override
        public String handle(TestQuery query) {
            return "Example result";
        }
    }
}
