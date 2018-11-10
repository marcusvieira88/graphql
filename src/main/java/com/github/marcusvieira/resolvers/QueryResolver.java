package com.github.marcusvieira.resolvers;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.github.marcusvieira.dtos.Link;
import com.github.marcusvieira.repositories.LinkRepository;

import java.util.List;

public class QueryResolver implements GraphQLRootResolver {

    private final LinkRepository linkRepository;

    public QueryResolver(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public List<Link> allLinks() {
        return linkRepository.getAllLinks();
    }
}
