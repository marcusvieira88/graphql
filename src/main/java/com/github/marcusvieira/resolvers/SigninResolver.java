package com.github.marcusvieira.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.github.marcusvieira.dtos.SigninPayload;
import com.github.marcusvieira.dtos.User;

public class SigninResolver implements GraphQLResolver<SigninPayload> {

    public User user(SigninPayload payload) {
        return payload.getUser();
    }
}
