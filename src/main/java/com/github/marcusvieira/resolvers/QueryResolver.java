package com.github.marcusvieira.resolvers;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.github.marcusvieira.dtos.Link;
import com.github.marcusvieira.filter.LinkFilter;
import com.github.marcusvieira.repositories.LinkRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.util.List;

//public class QueryResolver implements GraphQLRootResolver {

public class QueryResolver {

    private final LinkRepository linkRepository;

    public QueryResolver(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @GraphQLQuery
    public List<Link> allLinks(LinkFilter filter, @GraphQLArgument(name = "skip", defaultValue = "0") Number skip,
                               @GraphQLArgument(name = "first", defaultValue = "0") Number first) {
        return linkRepository.getAllLinks(filter, skip.intValue(), first.intValue());
    }
}
