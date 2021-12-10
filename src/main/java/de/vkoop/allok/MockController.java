package de.vkoop.allok;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;

@Controller
public class MockController {

    private static final Random random = new Random();


    @ResponseBody()
    @RequestMapping(value = "/**", produces = "text/html")
    public Mono<String> answerAll(@RequestParam(defaultValue = "${expectation}") Double expectedValue, @RequestParam(defaultValue = "${derivation}") Double derivation) {
        final long delay = getDelay(expectedValue, derivation);
        return Mono.delay(Duration.ofMillis(delay))
                .map(x -> "<html><head></head><body>ok</body></html>");
    }

    @ResponseBody
    @RequestMapping(value="/**", produces = "application/json")
    public Mono<String> answerJson(@RequestParam(defaultValue = "${expectation}") Double expectedValue, @RequestParam(defaultValue = "${derivation}") Double derivation ){
        final long delay = getDelay(expectedValue, derivation);

        return Mono.delay(Duration.ofMillis(delay))
                .map(x -> "{}");
    }

    private long getDelay(Double expectedValue, Double derivation) {
        var gaussian = random.nextGaussian();

        var delay = (expectedValue + derivation * gaussian);
        delay = Math.max(delay, 0);
        return (long) delay;
    }

}
