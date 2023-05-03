package com.hsnachos.handler;

import com.google.gson.Gson;
import com.hsnachos.domain.MemberVO_old;
import com.hsnachos.domain.NoteVO;
import com.hsnachos.service.NoteService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * packageName    : com.hsnachos.handler
 * fileName       : NoteHandler
 * author         : banghansol
 * date           : 2023/04/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/04/13        banghansol       최초 생성
 */
@Log4j
public class NoteHandler extends TextWebSocketHandler {
    // 접속자 관리 객체
    private List<WebSocketSession> sessions = new ArrayList<>();
    @Autowired
    private NoteService noteService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 접속 시작시
        // super.afterConnectionEstablished(session);
        log.warn("입장한 사람 : " + getIdBySession(session));
        sessions.add(session);
        log.warn("현재 접속자 수 " + sessions.size());
        log.warn("현재 접속자 정보 : ");
        sessions.forEach(log::warn);

        log.warn(sessions.stream().map(s -> getIdBySession(s)).collect(Collectors.toList()));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.warn(noteService);

        String receiver = message.getPayload();// js, ws.send() 수신자의 정보
        String sender = getIdBySession(session);
        List<NoteVO> list0 = noteService.getSendList(getIdBySession(session));
        List<NoteVO> list1 = noteService.getReceiveList(receiver);
        List<NoteVO> list2 = noteService.getReceiveUnCheckedList(receiver);

        Map<String, Object> map = new HashMap<>();
        map.put("sendList", list0);
        map.put("receiveList", list1);
        map.put("receiveUncheckedList", list2);
        map.put("sender", sender);

        Gson gson = new Gson();

        for(WebSocketSession s : sessions){
            MemberVO_old member = (MemberVO_old) s.getAttributes().get("member");
            if(member.getId().equals(receiver) || session == s){
                s.sendMessage(new TextMessage(gson.toJson(map)));
            }
        }
        // A > B
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 접속 종료시


        log.warn(session.getId() + " 로그아웃");
        sessions.remove(session);
        //super.afterConnectionClosed(session, status);

    }

    public String getIdBySession(WebSocketSession session){
        Object obj = session.getAttributes().get("member");
        String id = null;
        if(obj != null && obj instanceof MemberVO_old){
            id = ((MemberVO_old) obj).getId();
        }

        return id;
    }
}
