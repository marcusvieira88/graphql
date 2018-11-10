package com.github.marcusvieira;

import com.coxautodev.graphql.tools.GraphQLRootResolver;

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
