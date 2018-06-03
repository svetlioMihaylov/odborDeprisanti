package com.fmi.patokas.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.fmi.patokas.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.fmi.patokas.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.fmi.patokas.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.fmi.patokas.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.fmi.patokas.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.fmi.patokas.domain.Employee.class.getName(), jcacheConfiguration);
            cm.createCache(com.fmi.patokas.domain.Employee.class.getName() + ".benefits", jcacheConfiguration);
            cm.createCache(com.fmi.patokas.domain.Employee.class.getName() + ".documents", jcacheConfiguration);
            cm.createCache(com.fmi.patokas.domain.Employee.class.getName() + ".externalPeople", jcacheConfiguration);
            cm.createCache(com.fmi.patokas.domain.Employee.class.getName() + ".notes", jcacheConfiguration);
            cm.createCache(com.fmi.patokas.domain.Employee.class.getName() + ".vacationRequests", jcacheConfiguration);
            cm.createCache(com.fmi.patokas.domain.ContactInformation.class.getName(), jcacheConfiguration);
            cm.createCache(com.fmi.patokas.domain.EmployeePhoto.class.getName(), jcacheConfiguration);
            cm.createCache(com.fmi.patokas.domain.EmployeePossition.class.getName(), jcacheConfiguration);
            cm.createCache(com.fmi.patokas.domain.EmergancyContact.class.getName(), jcacheConfiguration);
            cm.createCache(com.fmi.patokas.domain.FinancialDetails.class.getName(), jcacheConfiguration);
            cm.createCache(com.fmi.patokas.domain.WorkDetails.class.getName(), jcacheConfiguration);
            cm.createCache(com.fmi.patokas.domain.VacationRequests.class.getName(), jcacheConfiguration);
            cm.createCache(com.fmi.patokas.domain.IDCard.class.getName(), jcacheConfiguration);
            cm.createCache(com.fmi.patokas.domain.ExternalPerson.class.getName(), jcacheConfiguration);
            cm.createCache(com.fmi.patokas.domain.ExternalPerson.class.getName() + ".owners", jcacheConfiguration);
            cm.createCache(com.fmi.patokas.domain.Benefit.class.getName(), jcacheConfiguration);
            cm.createCache(com.fmi.patokas.domain.Document.class.getName(), jcacheConfiguration);
            cm.createCache(com.fmi.patokas.domain.EmployeeNote.class.getName(), jcacheConfiguration);
            cm.createCache(com.fmi.patokas.domain.DocumentTemplates.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
