package com.facens.booklist.config;

import com.couchbase.client.core.env.SecurityConfig;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.ClusterOptions;
import com.couchbase.client.java.env.ClusterEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

@Configuration
@EnableCouchbaseRepositories(basePackages = "com.facens.booklist.repository")
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {
    @Override
    public String getConnectionString() {
        return "couchbases://cb.h0nqukwpzzhyr5a.cloud.couchbase.com";
    }

    @Override
    public String getUserName() {
        return "admin";
    }

    @Override
    public String getPassword() {
        return "BookListAdm@1";
    }

    @Override
    public String getBucketName() {
        return "booklist";
    }

    @Bean
    public ClusterEnvironment couchbaseClusterEnvironment() {
        return ClusterEnvironment.builder()
                .securityConfig(SecurityConfig.enableTls(true)) // Habilita o TLS
                .build();
    }

    @Bean
    public Cluster couchbaseCluster(ClusterEnvironment env) {
        return Cluster.connect("couchbases://cb.h0nqukwpzzhyr5a.cloud.couchbase.com",
                ClusterOptions.clusterOptions("admin", "BookListAdm@1").environment(env));
    }

}
