package com.Alif.ChatAppX.messageConfig;

import lombok.AllArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
public class MyHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws IOException {
        System.out.println("\n\nstarted!");

        // Если токен передается через параметры запроса
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            // Получение токена из параметра запроса
            String tokenParam = servletRequest.getServletRequest().getParameter("Authorization");


            System.out.println("the token: "+tokenParam);

            if (tokenParam != null && tokenParam.startsWith("Bearer ")) {

                attributes.put("token", tokenParam); // Добавление токена в атрибуты сессии
                System.out.println("Token received from query param: " + tokenParam);
            } else {
                System.out.println("Invalid or missing Authorization parameter in query");
            }
        }


        return true;
    }



    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        System.out.println("\n\nafterHandshake");
    }
}
