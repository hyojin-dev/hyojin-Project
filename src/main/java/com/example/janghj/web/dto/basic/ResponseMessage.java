package com.example.janghj.web.dto.basic;

public class ResponseMessage {
    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGIN_FAIL = "로그인 실패";
    public static final String CREATED_USER = "회원 가입 성공";
    public static final String UPDATE_USER = "회원 정보 수정 성공";
    public static final String DELETE_USER = "회원 탈퇴 성공";
    public static final String ALREADY_EXIST_ID = "이미 존재하는 아이디입니다.";
    public static final String NOT_FOUND_USER = "회원을 찾을 수 없습니다.";

    public static final String SEND_MESSAGE_FAIL = "문자발송에 실패했습니다.";

    public static final String ALREADY_EXIST_NAME = "이미 존재하는 상품 이름입니다. 다른 이름을 사용해주세요.";
    public static final String NOT_FOUND_ROUTE = "상품 정보를 찾을 수 없습니다.";

    public static final String UNAUTHORIZED = "권한없음";
    public static final String DB_ERROR = "데이터베이스 에러";
    public static final String NOT_FOUND_INFORMATION = "해당 정보 없음";
    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
    public static final String UNDEFINED_ENUM_TYPE = "정의되지 않은 enum 타입입니다.";

    public static final String SAVE = "저장 완료";
    public static final String UNSAVED = "저장 실패";
}
