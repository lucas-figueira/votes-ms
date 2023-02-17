package com.sicredi.votesms.domain.constants;

public final class ValidationConstraints {

    private ValidationConstraints(){

    }

    public static final String QUESTION_ALREADY_EXISTS = "Question with description {} already been created";
    public static final String INVALID_CPF = "cpf {} invalid";
    public static final String QUESTION_DOES_NOT_EXISTS = "Question with description {} does not exists";
    public static final String ASSOCIATE_ALREADY_EXISTS = "associate with cpf {} already been created";
    public static final String ASSOCIATE_DOES_NOT_EXISTS = "associate with cpf {} does not exists";
    public static final String ASSOCIATE_ALREADY_VOTED = "associate with cpf {} already voted for this question";
    public static final String VOTING_SESSION_ALREADY_CLOSED = "Voting session with question {} already closed";
    public static final String VOTING_SESSION_ALREADY_EXISTS = "Voting session with question {} already exists";
    public static final String VOTING_SESSION_WITH_QUESTION_DOES_NOT_EXISTS = "Voting session with question {} does not exists";
    public static final String VOTING_SESSION_IS_STILL_OPEN = "Voting session with question {} is still open";

}
