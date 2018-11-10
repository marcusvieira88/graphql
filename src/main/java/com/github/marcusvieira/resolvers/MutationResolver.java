package com.github.marcusvieira.resolvers;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.github.marcusvieira.dtos.AuthData;
import com.github.marcusvieira.dtos.Link;
import com.github.marcusvieira.dtos.SigninPayload;
import com.github.marcusvieira.dtos.User;
import com.github.marcusvieira.repositories.LinkRepository;
import com.github.marcusvieira.repositories.UserRepository;
import graphql.GraphQLException;

public class MutationResolver implements GraphQLRootResolver {

    private final LinkRepository linkRepository;
    private final UserRepository userRepository;

    public MutationResolver(LinkRepository linkRepository, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
    }

    public Link createLink(String url, String description) {
        Link newLink = new Link(url, description);
        linkRepository.saveLink(newLink);
        return newLink;
    }

    public User createUser(String name, AuthData auth) {
        User newUser = new User(name, auth.getEmail(), auth.getPassword());
        return userRepository.saveUser(newUser);
    }

    public SigninPayload signinUser(AuthData auth) throws IllegalAccessException {
        User user = userRepository.findByEmail(auth.getEmail());
        if (user.getPassword().equals(auth.getPassword())) {
            return new SigninPayload(user.getId(), user);
        }
        throw new GraphQLException("Invalid credentials");
    }
}