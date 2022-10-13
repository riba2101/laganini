package test.app;

import io.micrometer.core.annotation.Timed;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Timed
@RestController
@RequestMapping(path = TestController.PATH)
record TestController() implements TestEndpoint {

    @Validated
    @Override
    public List<TestBody> getAll() {
        return List.of(
                new TestBody(1L, "name1"),
                new TestBody(2L, "name2")
        );
    }

    @Validated
    @Override
    public Optional<TestBody> getById(@PathVariable @NotNull Long id) {
        return Optional.of(new TestBody(id, "name" + id));
    }

    @Validated
    @Override
    public TestBody create(@RequestBody @Valid @NotNull TestBody testBody) {
        return testBody;
    }

    @Validated
    @Override
    public TestBody update(@PathVariable @NotNull Long id, @RequestBody @Valid @NotNull TestBody testBody) {
        return testBody;
    }

}
