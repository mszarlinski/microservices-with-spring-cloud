package pl.mszarlinski.udemy.microservices.discovery;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.JdkFutureAdapters;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * @author mszarlinski on 2016-05-25.
 */
@RestController
public class GreetingController {

    /**
     * Separate bean is needed for a Hystrix aspect to work.
     */
    private final NameProvider nameProvider;

    private final ConsumerProperties consumerProperties;

    @Autowired
    public GreetingController(final NameProvider nameProvider, final ConsumerProperties consumerProperties) {
        this.nameProvider = nameProvider;
        this.consumerProperties = consumerProperties;
    }

    @RequestMapping("/greeting")
    public DeferredResult<String> greeting() {
        final DeferredResult<String> deffered = new DeferredResult<>();

        final ListenableFuture<String> listenableFuture = JdkFutureAdapters.listenInPoolThread(nameProvider.tryFetchName());

        Futures.addCallback(listenableFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                deffered.setResult(Optional.ofNullable(result)
                    .map(GreetingController.this::createGreeting)
                    .orElse(consumerProperties.getDefaultGreeting()));
            }

            @Override
            public void onFailure(final Throwable t) {
                deffered.setResult(consumerProperties.getDefaultGreeting());
            }
        });

        return deffered;
    }

    private String createGreeting(final String name) {
        return "Hi, " + name;
    }
}