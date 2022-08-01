package com.mateuszziomek.issuestracker.shared.ui;

public enum ApplicationErrorCode {
    GENERIC_UNNAMED_ERROR(Module.GENERIC, 1),
    GENERIC_VALIDATION_FAILED(Module.GENERIC, 2),
    GENERIC_EMAIL_UNAVAILABLE(Module.GENERIC, 3),

    AUTH_ACCESS_DENIED(Module.AUTH, 1),
    AUTH_INVALID_CREDENTIALS(Module.AUTH, 2),
    AUTH_INVALID_JWT(Module.AUTH, 3),

    USER_NOT_FOUND(Module.USER, 3),

    ORGANIZATION_NOT_FOUND(Module.ORGANIZATION, 1),
    ORGANIZATION_OWNER_INVALID(Module.ORGANIZATION, 2),
    ORGANIZATION_INVITATION_NOT_FOUND(Module.ORGANIZATION, 3),
    ORGANIZATION_INVITATION_ALREADY_PRESENT(Module.ORGANIZATION, 4),
    ORGANIZATION_MEMBER_ALREADY_PRESENT(Module.ORGANIZATION, 5),
    ORGANIZATION_ACCESS_DENIED(Module.ORGANIZATION, 6),
    ORGANIZATION_PROJECT_NOT_FOUND(Module.ORGANIZATION, 7),

    ISSUE_NOT_FOUND(Module.ISSUE, 1),
    ISSUE_CLOSED(Module.ISSUE, 2),
    ISSUE_TYPE_SET(Module.ISSUE, 3),
    ISSUE_CONTENT_SET(Module.ISSUE, 4),
    ISSUE_NAME_SET(Module.ISSUE, 5),
    ISSUE_VOTE_ALREADY_EXISTS(Module.ISSUE, 6),
    ISSUE_COMMENT_NOT_FOUND(Module.ISSUE, 8),
    ISSUE_COMMENT_HIDDEN(Module.ISSUE, 9),
    ISSUE_COMMENT_CONTENT_SET(Module.ISSUE, 10);

    private final String code;
    ApplicationErrorCode(Module module, int code) {
        this.code = module.toString() + "_" + code;
    }

    public String code() {
        return code;
    }

    private enum Module {
        GENERIC, AUTH, USER, ORGANIZATION, ISSUE
    }
}
