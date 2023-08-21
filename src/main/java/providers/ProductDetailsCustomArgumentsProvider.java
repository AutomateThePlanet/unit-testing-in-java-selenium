package providers;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class ProductDetailsCustomArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of("iPod Touch",32,"$194.00","Product 5","Apple","5.00kg"),
                Arguments.of("iPod Shuffle",34,"$182.00","Product 7","Apple","5.00kg"),
                Arguments.of("iPod Nano",36,"$122.00","Product 9","Apple","5.00kg")
        );
    }
}
