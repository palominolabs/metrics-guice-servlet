package com.palominolabs.metrics.guice.servlet;

import com.codahale.metrics.servlets.AdminServlet;
import com.google.inject.servlet.ServletModule;

import java.util.HashMap;
import java.util.Map;

/**
 * A guice servlet module that registers the {@link AdminServlet} via guice and also configures all healthchecks bound
 * via guice to it.
 *
 * To use, install this module in your servlet module (or add as a separate module), and bind the health checks via a
 * multi binder:
 * <pre>
 * <code>install(new AdminServletModule());
 *
 * Multibinder&lt;HealthCheck&gt; healthChecksBinder = Multibinder.newSetBinder(binder(), HealthCheck.class);
 *
 * healthChecksBinder.addBinding().to(MyCoolHealthCheck.class);
 * healthChecksBinder.addBinding().to(MyOtherCoolHealthCheck.class);
 * </code>
 * </pre>
 * The module offers the same overloaded constructors to specify the uris for the healthcheck, metrics, etc. E.g.
 * <pre>
 * <code>install(new AdminServletModule("/1.0/healthcheck", "/1.0/metrics", "/1.0/ping", "/1.0/threads"));
 * </code>
 * </pre>
 * In order to use this module, you need the <code>guice-servlet</code> and <code>guice-multibindings</code>
 * dependencies in addition to the normal <code>guice</code> dependency:
 * <pre>
 * {@code <dependency>
 *   <groupId>com.google.inject</groupId>
 *   <artifactId>guice</artifactId>
 *   <version>3.0</version>
 * </dependency>
 * <dependency>
 *   <groupId>com.google.inject.extensions</groupId>
 *   <artifactId>guice-servlet</artifactId>
 *   <version>3.0</version>
 * </dependency>
 * <dependency>
 *   <groupId>com.google.inject.extensions</groupId>
 *   <artifactId>guice-multibindings</artifactId>
 *   <version>3.0</version>
 * </dependency>
 * }
 * </pre>
 */
public class AdminServletModule extends ServletModule {
    private final String healthcheckUri;
    private final String metricsUri;
    private final String pingUri;
    private final String threadsUri;

    public AdminServletModule() {
        this(AdminServlet.DEFAULT_HEALTHCHECK_URI, AdminServlet.DEFAULT_METRICS_URI,
            AdminServlet.DEFAULT_PING_URI, AdminServlet.DEFAULT_THREADS_URI);
    }

    public AdminServletModule(String healthcheckUri, String metricsUri, String pingUri, String threadsUri) {
        this.healthcheckUri = healthcheckUri;
        this.metricsUri = metricsUri;
        this.pingUri = pingUri;
        this.threadsUri = threadsUri;
    }

    @Override
    protected void configureServlets() {
        bind(AdminServlet.class).asEagerSingleton();

        Map<String, String> initParams = new HashMap<String, String>();
        initParams.put("metrics-uri", metricsUri);
        initParams.put("ping-uri", pingUri);
        initParams.put("threads-uri", threadsUri);
        initParams.put("healthcheck-uri", healthcheckUri);

        serve(healthcheckUri, metricsUri, pingUri, threadsUri).with(AdminServlet.class, initParams);
    }
}
