package com.github.marcusvieira.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.github.marcusvieira.dtos.Link;
import com.github.marcusvieira.dtos.User;
import com.github.marcusvieira.repositories.UserRepository;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;

//public class LinkResolver implements GraphQLResolver<Link> {

public class LinkResolver {

    private final UserRepository userRepository;

    public LinkResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GraphQLQuery
    public User postedBy(@GraphQLContext Link link) {
        if (link.getUserId() == null) {
            return null;
        }
        return userRepository.findById(link.getUserId());
    }
}
