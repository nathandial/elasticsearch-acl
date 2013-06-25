package com.searchly.rest.security

/**
 @author ferhat
 */
public enum Role {
    SYSTEM(3), ADMIN(2), MEMBER(1)

    def rank

    Role(rank) {
        this.rank = rank
    }

    def can(role) {
        return this.rank >= role.rank
    }
}