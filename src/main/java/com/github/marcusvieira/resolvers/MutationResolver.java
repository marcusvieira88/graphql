package com.github.marcusvieira.resolvers;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.github.marcusvieira.dtos.*;
import com.github.marcusvieira.endpoints.AuthContext;
import com.github.marcusvieira.repositories.LinkRepository;
import com.github.marcusvieira.repositories.UserRepository;
import com.github.marcusvieira.repositories.VoteRepository;
import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLRootContext;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

//public class MutationResolver implements GraphQLRootResolver {

public class MutationResolver {

    private final LinkRepository linkRepository;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;

    public MutationResolver(LinkRepository linkRepository, UserRepository userRepository, VoteRepository voteRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }

    //The way to inject the context is via DataFetchingEnvironment
    @GraphQLMutation
    public Link createLink(String url, String description, @GraphQLRootContext AuthContext context) {
        Link newLink = new Link(url, description, context.getUser().getId());
        linkRepository.saveLink(newLink);
        return newLink;
    }

    @GraphQLMutation
    public User createUser(String name,  @GraphQLRootContext AuthData auth) {
        User newUser = new User(name, auth.getEmail(), auth.getPassword());
        return userRepository.saveUser(newUser);
    }

    @GraphQLMutation
    public SigninPayload signinUser( @GraphQLRootContext AuthData auth) throws IllegalAccessException {
        User user = userRepository.findByEmail(auth.getEmail());
        if (user.getPassword().equals(auth.getPassword())) {
            return new SigninPayload(user.getId(), user);
        }
        throw new GraphQLException("Invalid credentials");
    }

    @GraphQLMutation
    public Vote createVote(String linkId, String userId) {
        ZonedDateTime now = Instant.now().atZone(ZoneOffset.UTC);
        return voteRepository.saveVote(new Vote(now, userId, linkId));
    }
}