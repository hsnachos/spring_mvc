package com.hsnachos.handler;

import lombok.extern.log4j.Log4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : com.hsnachos.handler
 * fileName       : NoteHandler
 * author         : banghansol
 * date           : 2023/04/12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/04/12        banghansol       최초 생성
 */
@Log4j
public class ChatHandler extends TextWebSocketHandler {
    // 접속자 관리 객체
    List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 접속 시작시
        // super.afterConnectionEstablished(session);
        sessions.add(session);
        log.warn("현재 접속자 수 " + sessions.size());
        log.warn("현재 접속자 정보 : ");
        sessions.forEach(log::info);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // super.handleTextMessage(session, message);
        log.warn(session.getId() + " 로그아웃");
        sessions.remove(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 접속 종료시

        //super.afterConnectionClosed(session, status);

    }
}
