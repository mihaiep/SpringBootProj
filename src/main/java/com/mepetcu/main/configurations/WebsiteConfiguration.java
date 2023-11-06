package com.mepetcu.main.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.annotation.Qualifier;
import com.mepetcu.main.elements.Tip;
import com.mepetcu.main.services.ThymeleafService;
import com.mepetcu.main.interfaces.ThymeleafRepository;
import com.mepetcu.main.repositories.ResultRepository;
import com.mepetcu.main.repositories.InMemoryTipsRepository;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;


@Configuration
public class WebsiteConfiguration {

    @Bean
    @Qualifier("tipsRepository")
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ThymeleafRepository tipsRepository() {
        ThymeleafRepository repository = new InMemoryTipsRepository();
        repository.add(new Tip("Learn by doing. Always play with the code while learning."));
        repository.add(new Tip("Grasp the fundamentals for long-term benefits."));
        repository.add(new Tip("Code by hand. It's sharpens proficiency."));
        repository.add(new Tip("Never Get Tired of Practicing."));
        repository.add(new Tip("Ask for help when you need it."));
        repository.add(new Tip("Seek out more online resources. There’s a wealth of content."));
        repository.add(new Tip("Learn From the Bugs or error."));
        repository.add(new Tip("Don’t just read the sample code. Thinker with it."));
        repository.add(new Tip("Take breaks when debugging."));
        repository.add(new Tip("Be curious! If you are interested in the topic, you will search for additional resources, like articles or YouTube videos, to develop your knowledge."));
        return repository;
    }

    @Bean
    @Qualifier("tipsService")
    public ThymeleafService tipsService(@Qualifier("tipsRepository") ThymeleafRepository repository) {
        return new ThymeleafService(repository);
    }

    @Bean
    @Qualifier("resultService")
    public ThymeleafService endpointsService(ResultRepository repository){
        return new ThymeleafService(repository);
    }

}
