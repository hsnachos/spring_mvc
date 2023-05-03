package com.hsnachos.handler;

import com.google.gson.Gson;
import com.hsnachos.domain.MemberVO_old;
import com.hsnachos.domain.NoteVO;
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
    List<WebSocketSession> sessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 접속 시작시
        // super.afterConnectionEstablished(session);
        sessions.add(session);
        log.warn("현재 접속자 수 " + sessions.size());
        log.warn("현재 접속자 정보 : ");
        sessions.forEach(log::warn);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // super.handleTextMessage(session, message);
        String msg = message.getPayload();

        Gson gson = new Gson();
//        Map<?, ?> map = gson.fromJson(msg, Map.class);
        NoteVO vo = gson.fromJson(msg, NoteVO.class);
        MemberVO_old memberVO = (MemberVO_old) session.getAttributes().get("member");
        log.warn(msg);
        log.warn(vo);
        log.warn(memberVO);

        log.warn(msg);
        for(WebSocketSession s : sessions) {
            s.sendMessage(new TextMessage(memberVO.getId() + " :: " + vo.getMessage()));
        }


    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 접속 종료시
        log.warn(session.getId() + " 로그아웃");
        sessions.remove(session);
    }
}
