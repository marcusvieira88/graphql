package com.github.marcusvieira.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.github.marcusvieira.dtos.Link;
import com.github.marcusvieira.dtos.User;
import com.github.marcusvieira.dtos.Vote;
import com.github.marcusvieira.repositories.LinkRepository;
import com.github.marcusvieira.repositories.UserRepository;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;

//public class VoteResolver implements GraphQLResolver<Vote> {

public class VoteResolver {

    private final LinkRepository linkRepository;
    private final UserRepository userRepository;

    public VoteResolver(LinkRepository linkRepository, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
    }

    @GraphQLQuery
    public User user(@GraphQLContext Vote vote) {
        if (vote.getUserId() == null) {
            return null;
        }
        return userRepository.findById(vote.getUserId());
    }

    @GraphQLQuery
    public Link link(@GraphQLContext Vote vote) {
        if (vote.getLinkId() == null) {
            return null;
        }
        return linkRepository.findById(vote.getLinkId());
    }
}
